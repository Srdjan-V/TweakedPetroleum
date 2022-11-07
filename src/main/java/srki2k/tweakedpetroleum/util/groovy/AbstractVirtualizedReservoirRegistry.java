package srki2k.tweakedpetroleum.util.groovy;

import com.cleanroommc.groovyscript.api.GroovyBlacklist;
import com.cleanroommc.groovyscript.registry.VirtualizedRegistry;
import flaxbeard.immersivepetroleum.api.crafting.PumpjackHandler;

import java.util.Map;

@SuppressWarnings("unused")
public abstract class AbstractVirtualizedReservoirRegistry<T extends AbstractVirtualizedReservoirRegistry<?,?>,B> extends VirtualizedRegistry<GroovyReservoirWrapper> {

    @GroovyBlacklist
    public abstract T getInstance();


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
        PumpjackHandler.reservoirList.forEach((reservoirType, integer) -> addBackup(new GroovyReservoirWrapper(reservoirType, integer)));
        PumpjackHandler.reservoirList.clear();
    }

    public boolean remove(String name) {
        PumpjackHandler.ReservoirType removedReservoirType = null;
        for (Map.Entry<PumpjackHandler.ReservoirType, Integer> reservoir : PumpjackHandler.reservoirList.entrySet()) {
            if (reservoir.getKey().name.equalsIgnoreCase(name)) {
                addBackup(new GroovyReservoirWrapper(reservoir.getKey(), reservoir.getValue()));
                removedReservoirType = reservoir.getKey();
                break;
            }
        }

        if (removedReservoirType == null) {
            return false;
        }

        PumpjackHandler.reservoirList.remove(removedReservoirType);
        return true;
    }

    public boolean remove(GroovyReservoirWrapper reservoir) {
        if (PumpjackHandler.reservoirList.containsKey(reservoir.getReservoirType())) {
            PumpjackHandler.reservoirList.remove(reservoir.getReservoirType());
            addBackup(reservoir);
            return true;
        }
        return false;
    }

    public void add(GroovyReservoirWrapper reservoir) {
        if (reservoir != null) {
            addScripted(reservoir);
            PumpjackHandler.reservoirList.put(reservoir.getReservoirType(), reservoir.getWeight());
        }
    }

    public abstract B recipeBuilder();

}
