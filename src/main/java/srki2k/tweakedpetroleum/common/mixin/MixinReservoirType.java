package srki2k.tweakedpetroleum.common.mixin;

import flaxbeard.immersivepetroleum.api.crafting.PumpjackHandler;
import net.minecraft.nbt.NBTTagCompound;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import srki2k.tweakedpetroleum.api.crafting.TweakedPumpjackHandler;
import srki2k.tweakedpetroleum.api.ihelpers.IReservoirType;

import java.util.Objects;

@Mixin(PumpjackHandler.ReservoirType.class)
public abstract class MixinReservoirType implements IReservoirType {


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
    public String getFluid() {
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


    //ReservoirType Addons

    @Unique
    TweakedPumpjackHandler.ReservoirContent reservoirContent;

    @Unique
    public int powerTier;
    @Unique
    public int pumpSpeed;

    @Unique
    @Override
    public TweakedPumpjackHandler.ReservoirContent getReservoirContent() {
        return reservoirContent;
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


    @Unique
    @Override
    public void setReservoirContent(TweakedPumpjackHandler.ReservoirContent reservoirContent) {
        this.reservoirContent = reservoirContent;
    }

    @Unique
    @Override
    public void setPowerTier(int powerTier) {
        this.powerTier = powerTier;
    }

    @Unique
    @Override
    public void setPumpSpeed(int pumpSpeed) {
        this.pumpSpeed = pumpSpeed;
    }


    @Inject(method = "readFromNBT", at = @At("RETURN"))
    private static void onReadFromNBT(NBTTagCompound tag, CallbackInfoReturnable<PumpjackHandler.ReservoirType> cir) {
        IReservoirType mix = (IReservoirType) cir.getReturnValue();
        mix.setReservoirContent(TweakedPumpjackHandler.ReservoirContent.values()[tag.getByte("reservoirContent")]);
        mix.setPumpSpeed(tag.getInteger("pumpSpeed"));
        mix.setPowerTier(tag.getInteger("powerTier"));
    }


    @Inject(method = "writeToNBT", at = @At("RETURN"))
    private void onWriteToNBT(CallbackInfoReturnable<NBTTagCompound> cir) {
        NBTTagCompound tag = cir.getReturnValue();
        tag.setByte("reservoirContent", (byte) this.reservoirContent.ordinal());
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
