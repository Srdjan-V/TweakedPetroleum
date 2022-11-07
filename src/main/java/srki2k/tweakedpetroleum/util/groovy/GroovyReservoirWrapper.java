package srki2k.tweakedpetroleum.util.groovy;

import com.cleanroommc.groovyscript.api.GroovyBlacklist;
import flaxbeard.immersivepetroleum.api.crafting.PumpjackHandler;
import srki2k.tweakedpetroleum.api.crafting.TweakedPumpjackHandler;
import srki2k.tweakedpetroleum.api.ihelpers.IReservoirGetters;
import srki2k.tweakedpetroleum.api.ihelpers.IReservoirType;

import java.util.Objects;

public class GroovyReservoirWrapper implements IReservoirGetters {

    private final IReservoirType reservoirType;

    private final int weight;

    @GroovyBlacklist
    public GroovyReservoirWrapper(IReservoirType reservoirType, int weight) {
        this.reservoirType = reservoirType;
        this.weight = weight;
    }

    @GroovyBlacklist
    public GroovyReservoirWrapper(PumpjackHandler.ReservoirType reservoirType, int weight) {
        this.reservoirType = (IReservoirType) reservoirType;
        this.weight = weight;
    }


    public PumpjackHandler.ReservoirType getReservoirType() {
        return (PumpjackHandler.ReservoirType) reservoirType;
    }

    public int getWeight() {
        return weight;
    }

    @Override
    public String getName() {
        return reservoirType.getName();
    }

    @Override
    public String getStringFluid() {
        return reservoirType.getStringFluid();
    }

    @Override
    public int getMinSize() {
        return reservoirType.getMinSize();
    }

    @Override
    public int getMaxSize() {
        return reservoirType.getMaxSize();
    }

    @Override
    public int getReplenishRate() {
        return reservoirType.getReplenishRate();
    }

    @Override
    public int getPowerTier() {
        return reservoirType.getPowerTier();
    }

    @Override
    public int getPumpSpeed() {
        return reservoirType.getPumpSpeed();
    }

    @Override
    public float getDrainChance() {
        return reservoirType.getDrainChance();
    }

    @Override
    public int[] getDimensionWhitelist() {
        return reservoirType.getDimensionWhitelist();
    }

    @Override
    public int[] getDimensionBlacklist() {
        return reservoirType.getDimensionBlacklist();
    }

    @Override
    public String[] getBiomeWhitelist() {
        return reservoirType.getBiomeWhitelist();
    }

    @Override
    public String[] getBiomeBlacklist() {
        return reservoirType.getBiomeBlacklist();
    }

    @Override
    public TweakedPumpjackHandler.ReservoirContent getReservoirContent() {
        return reservoirType.getReservoirContent();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GroovyReservoirWrapper that = (GroovyReservoirWrapper) o;
        return reservoirType.getName().equals(that.reservoirType.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(reservoirType.getName());
    }
}
