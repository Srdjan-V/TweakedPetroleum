package srki2k.tweakedpetroleum.util.groovy.abstractclass;

import com.cleanroommc.groovyscript.api.GroovyBlacklist;
import com.cleanroommc.groovyscript.api.GroovyLog;
import com.cleanroommc.groovyscript.registry.VirtualizedRegistry;
import flaxbeard.immersivepetroleum.api.crafting.PumpjackHandler;
import srki2k.tweakedpetroleum.util.groovy.GroovyFluidReservoirWrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unused")
public abstract class AbstractVirtualizedReservoirRegistry<T extends AbstractVirtualizedReservoirRegistry<?, ?>, B> extends VirtualizedRegistry<GroovyFluidReservoirWrapper.InnerWrapper> {

    public AbstractVirtualizedReservoirRegistry(String name, String... aliases) {
        super(name, aliases);
    }

    @Override
    @GroovyBlacklist
    public void onReload() {
        removeScripted().forEach((recipe) -> PumpjackHandler.reservoirList.remove(recipe.getReservoirType()));
        restoreFromBackup().forEach((recipe) -> PumpjackHandler.reservoirList.put(recipe.getReservoirType(), recipe.getWeight()));
    }

    public void removeAll() {
        PumpjackHandler.reservoirList.forEach((reservoirType, integer) -> addBackup(new GroovyFluidReservoirWrapper.InnerWrapper(reservoirType, integer)));
        PumpjackHandler.reservoirList.clear();
    }

    public void remove(String name) {
        PumpjackHandler.ReservoirType removedReservoirType = null;
        for (Map.Entry<PumpjackHandler.ReservoirType, Integer> reservoir : PumpjackHandler.reservoirList.entrySet()) {
            if (reservoir.getKey().name.equalsIgnoreCase(name)) {
                GroovyFluidReservoirWrapper.InnerWrapper innerWrapper = new GroovyFluidReservoirWrapper.InnerWrapper(reservoir.getKey(), reservoir.getValue());
                if (!scripted.contains(innerWrapper)) {
                    addBackup(innerWrapper);
                    removedReservoirType = reservoir.getKey();
                    break;
                }
            }
        }

        if (removedReservoirType == null) {
            GroovyLog.msg("Error removing custom reservoir with name: {}", name).error()
                    .add("No base reservoir exist with that name exists").post();
            return;
        }

        PumpjackHandler.reservoirList.remove(removedReservoirType);
    }

    public List<GroovyFluidReservoirWrapper> getAll() {
        List<GroovyFluidReservoirWrapper> reservoirs = PumpjackHandler.reservoirList.entrySet()
                .stream().collect(ArrayList::new,
                        (list, map) -> list.add(new GroovyFluidReservoirWrapper(map.getKey(), map.getValue())),
                        ArrayList::addAll);

        if (reservoirs.isEmpty()) {
            GroovyLog.msg("Error getting all custom reservoirs").error()
                    .add("No reservoirs exist").post();
        }

        return reservoirs;
    }

    public GroovyFluidReservoirWrapper get(String name) {
        for (Map.Entry<PumpjackHandler.ReservoirType, Integer> reservoir : PumpjackHandler.reservoirList.entrySet()) {
            if (reservoir.getKey().name.equalsIgnoreCase(name)) {
                return new GroovyFluidReservoirWrapper(reservoir.getKey(), reservoir.getValue());
            }
        }

        GroovyLog.msg("Error getting custom reservoir with name: {}", name).error().post();
        return null;
    }

    @GroovyBlacklist
    public void remove(GroovyFluidReservoirWrapper.InnerWrapper reservoir) {
        if (PumpjackHandler.reservoirList.containsKey(reservoir.getReservoirType())) {
            PumpjackHandler.reservoirList.remove(reservoir.getReservoirType());
            if (!scripted.contains(reservoir)) {
                addBackup(reservoir);
            }
        }
    }

    @GroovyBlacklist
    public void add(GroovyFluidReservoirWrapper.InnerWrapper reservoir) {
        if (reservoir == null) {
            return;
        }

        if (!scripted.contains(reservoir)) {
            addScripted(reservoir);
            PumpjackHandler.reservoirList.put(reservoir.getReservoirType(), reservoir.getWeight());
            return;
        }

        GroovyLog.msg("Registered custom reservoir with name: {} is already registered", reservoir.getReservoirType().name).error().post();
    }

    public abstract B recipeBuilder();

}
