package srki2k.tweakedpetroleum.mixin;

import flaxbeard.immersivepetroleum.api.crafting.PumpjackHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import srki2k.tweakedpetroleum.api.ihelpers.IReservoirType;

import java.util.Objects;


@Mixin(value = PumpjackHandler.ReservoirType.class, remap = false)
public abstract class MixinReservoirTypeBase implements IReservoirType {

    @Shadow
    public String name;

    @Shadow
    public String fluid;
    @Shadow
    public int minSize;
    @Shadow
    public int maxSize;
    @Shadow
    public int replenishRate;
    @Shadow
    public int[] dimensionWhitelist;
    @Shadow
    public int[] dimensionBlacklist;
    @Shadow
    public String[] biomeWhitelist;
    @Shadow
    public String[] biomeBlacklist;


    //getters

    @Unique
    @Override
    public String getName() {
        return name;
    }

    @Unique
    @Override
    public String getStringFluid() {
        return fluid;
    }

    @Unique
    @Override
    public int getMinSize() {
        return minSize;
    }

    @Unique
    @Override
    public int getMaxSize() {
        return maxSize;
    }

    @Unique
    @Override
    public int getReplenishRate() {
        return replenishRate;
    }

    @Unique
    @Override
    public int[] getDimensionWhitelist() {
        return dimensionWhitelist;
    }

    @Unique
    @Override
    public int[] getDimensionBlacklist() {
        return dimensionBlacklist;
    }

    @Unique
    @Override
    public String[] getBiomeWhitelist() {
        return biomeWhitelist;
    }

    @Unique
    @Override
    public String[] getBiomeBlacklist() {
        return biomeBlacklist;
    }


    //setter
    @Unique
    @Override
    public void setDimensionWhitelist(int[] dimensionWhitelist) {
        this.dimensionWhitelist = dimensionWhitelist;
    }

    @Unique
    @Override
    public void setDimensionBlacklist(int[] dimensionBlacklist) {
        this.dimensionBlacklist = dimensionBlacklist;
    }

    @Unique
    @Override
    public void setBiomeWhitelist(String[] biomeWhitelist) {
        this.biomeWhitelist = biomeWhitelist;
    }

    @Unique
    @Override
    public void setBiomeBlacklist(String[] biomeBlacklist) {
        this.biomeBlacklist = biomeBlacklist;
    }


    //Equals and hashCode methods
    @Unique
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IReservoirType that = (IReservoirType) o;
        return name.equals(that.getName());
    }

    @Unique
    @Override
    public int hashCode() {
        return Objects.hash(name);
    }


}
