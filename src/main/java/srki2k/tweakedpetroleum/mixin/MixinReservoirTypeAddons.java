package srki2k.tweakedpetroleum.mixin;

import flaxbeard.immersivepetroleum.api.crafting.PumpjackHandler;
import net.minecraft.nbt.NBTTagCompound;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import srki2k.tweakedpetroleum.api.crafting.TweakedPumpjackHandler;
import srki2k.tweakedpetroleum.api.ihelpers.IReservoirType;

@Mixin(PumpjackHandler.ReservoirType.class)
public abstract class MixinReservoirTypeAddons implements IReservoirType {

    @Unique
    TweakedPumpjackHandler.ReservoirContent reservoirContent;

    @Unique
    public int powerTier;
    @Unique
    public int pumpSpeed;

    @Unique
    public float drainChance;

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
    public float getDrainChance() {
        return drainChance;
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

    @Unique
    @Override
    public void setDrainChance(float drainChance) {
        this.drainChance = drainChance;
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


}
