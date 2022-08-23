package srki2k.tweakedpetroleum.common.mixin;

import flaxbeard.immersivepetroleum.api.crafting.PumpjackHandler;
import net.minecraft.nbt.NBTTagCompound;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import srki2k.tweakedpetroleum.api.crafting.IReservoirType;

import java.util.Objects;

@Mixin(PumpjackHandler.ReservoirType.class)
public abstract class MixinReservoirType implements IReservoirType {


    @Shadow
    public String name;
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
    public String getName() {
        return name;
    }

    @Unique
    public int getMinSize() {
        return minSize;
    }

    @Unique
    public int getMaxSize() {
        return maxSize;
    }

    @Unique
    public int getReplenishRate() {
        return replenishRate;
    }

    @Unique
    public int[] getDimensionWhitelist() {
        return dimensionWhitelist;
    }

    @Unique
    public int[] getDimensionBlacklist() {
        return dimensionBlacklist;
    }

    @Unique
    public String[] getBiomeWhitelist() {
        return biomeWhitelist;
    }

    @Unique
    public String[] getBiomeBlacklist() {
        return biomeBlacklist;
    }

    //setter
    @Unique
    public void setDimensionWhitelist(int[] dimensionWhitelist) {
        this.dimensionWhitelist = dimensionWhitelist;
    }

    @Unique
    public void setDimensionBlacklist(int[] dimensionBlacklist) {
        this.dimensionBlacklist = dimensionBlacklist;
    }

    @Unique
    public void setBiomeWhitelist(String[] biomeWhitelist) {
        this.biomeWhitelist = biomeWhitelist;
    }

    @Unique
    public void setBiomeBlacklist(String[] biomeBlacklist) {
        this.biomeBlacklist = biomeBlacklist;
    }


    //ReservoirType Addons

    @Unique
    public int powerTier;
    @Unique
    public int pumpSpeed;

    @Unique
    public void setPowerTier(int powerTier) {
        this.powerTier = powerTier;
    }

    @Unique
    public void setPumpSpeed(int pumpSpeed) {
        this.pumpSpeed = pumpSpeed;
    }

    @Unique
    @Override
    public int getPowerTier() {
        return powerTier;
    }

    @Unique
    @Override
    public int getPumpSpeed() {
        return pumpSpeed;
    }


    @Inject(method = "readFromNBT", at = @At("RETURN"))
    private static void onReadFromNBT(NBTTagCompound tag, CallbackInfoReturnable<PumpjackHandler.ReservoirType> cir) {
        int pumpSpeed = tag.getInteger("pumpSpeed");
        int powerTier = tag.getInteger("powerTier");
        IReservoirType mix = (IReservoirType) cir.getReturnValue();
        mix.setPumpSpeed(pumpSpeed);
        mix.setPowerTier(powerTier);
    }


    @Inject(method = "writeToNBT", at = @At("RETURN"))
    private void onWriteToNBT(CallbackInfoReturnable<NBTTagCompound> cir) {
        NBTTagCompound tag = cir.getReturnValue();
        tag.setInteger("pumpSpeed", this.pumpSpeed);
        tag.setInteger("powerTier", this.powerTier);
    }


    //Equals and hashCode methods
    @Unique
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MixinReservoirType that = (MixinReservoirType) o;
        return name.equals(that.name);
    }

    @Unique
    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

}
