package srki2k.tweakedpetroleum.util.groovy;

import com.cleanroommc.groovyscript.api.GroovyBlacklist;
import com.cleanroommc.groovyscript.api.GroovyLog;
import com.cleanroommc.groovyscript.registry.VirtualizedRegistry;
import flaxbeard.immersivepetroleum.api.crafting.PumpjackHandler;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unused")
public abstract class AbstractVirtualizedReservoirRegistry<T extends AbstractVirtualizedReservoirRegistry<?, ?>, B> extends VirtualizedRegistry<GroovyReservoirWrapper> {

    @GroovyBlacklist
    public abstract T getInstance();

    public AbstractVirtualizedReservoirRegistry(String name, String... aliases) {
        super(name, aliases);
    }

    @Override
    @GroovyBlacklist
    protected void initBackup() {
        this.backup = new HashSet<>();
    }

    @Override
    @GroovyBlacklist
    protected void initScripted() {
        this.scripted = new HashSet<>();
    }

    @Override
    @GroovyBlacklist
    public void onReload() {
        removeScripted().forEach((recipe) -> PumpjackHandler.reservoirList.remove(recipe.getReservoirType()));
        restoreFromBackup().forEach((recipe) -> PumpjackHandler.reservoirList.put(recipe.getReservoirType(), recipe.getWeight()));
    }

    public void removeAll() {
        PumpjackHandler.reservoirList.forEach((reservoirType, integer) -> addBackup(new GroovyReservoirWrapper(reservoirType, integer)));
        PumpjackHandler.reservoirList.clear();
    }

    public void remove(String name) {
        PumpjackHandler.ReservoirType removedReservoirType = null;
        for (Map.Entry<PumpjackHandler.ReservoirType, Integer> reservoir : PumpjackHandler.reservoirList.entrySet()) {
            if (reservoir.getKey().name.equalsIgnoreCase(name)) {
                addBackup(new GroovyReservoirWrapper(reservoir.getKey(), reservoir.getValue()));
                removedReservoirType = reservoir.getKey();
                break;
            }
        }

        if (removedReservoirType == null) {
            GroovyLog.msg("Error removing custom reservoir with name: {}", name).error()
                    .add("No reservoirs exist").post();
            return;
        }

        PumpjackHandler.reservoirList.remove(removedReservoirType);
    }

    public void remove(GroovyReservoirWrapper reservoir) {
        if (PumpjackHandler.reservoirList.containsKey(reservoir.getReservoirType())) {
            PumpjackHandler.reservoirList.remove(reservoir.getReservoirType());
            addBackup(reservoir);
            GroovyLog.msg("Removed custom reservoir with name: {}", reservoir.getName()).info().post();
            return;
        }
        GroovyLog.msg("Error removing custom reservoir with name: {}", reservoir.getName()).error()
                .add("No reservoirs exist").post();
    }

    public List<GroovyReservoirWrapper> getAll() {
        List<GroovyReservoirWrapper> reservoirs = PumpjackHandler.reservoirList.entrySet()
                .stream().collect(ArrayList::new,
                        (list, map) -> list.add(new GroovyReservoirWrapper(map.getKey(), map.getValue())),
                        ArrayList::addAll);

        if (reservoirs.isEmpty()) {
            GroovyLog.msg("Error getting all custom reservoirs").error()
                    .add("No reservoirs exist").post();
        }

        return reservoirs;
    }

    public GroovyReservoirWrapper get(String name) {
        for (Map.Entry<PumpjackHandler.ReservoirType, Integer> reservoir : PumpjackHandler.reservoirList.entrySet()) {
            if (reservoir.getKey().name.equalsIgnoreCase(name)) {
                return new GroovyReservoirWrapper(reservoir.getKey(), reservoir.getValue());
            }
        }

        GroovyLog.msg("Error getting custom reservoir with name: {}", name).error().post();
        return null;
    }

    public void add(GroovyReservoirWrapper reservoir) {
        if (reservoir != null) {
            if (scripted.add(reservoir)) {
                PumpjackHandler.reservoirList.put(reservoir.getReservoirType(), reservoir.getWeight());
                return;
            }
            GroovyLog.msg("Registered custom reservoir with name: {} is already registered", reservoir.getName()).error().post();
        }
    }

    public abstract B recipeBuilder();

}
