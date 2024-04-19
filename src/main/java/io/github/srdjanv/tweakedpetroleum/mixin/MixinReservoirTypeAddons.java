package io.github.srdjanv.tweakedpetroleum.mixin;

import flaxbeard.immersivepetroleum.api.crafting.PumpjackHandler;
import io.github.srdjanv.tweakedpetroleum.api.crafting.TweakedPumpjackHandler;
import io.github.srdjanv.tweakedpetroleum.api.mixins.ITweakedPetReservoirType;
import net.minecraft.nbt.NBTTagCompound;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = PumpjackHandler.ReservoirType.class, remap = false)
public abstract class MixinReservoirTypeAddons implements ITweakedPetReservoirType {

    @Unique
    TweakedPumpjackHandler.ReservoirContent reservoirContent = TweakedPumpjackHandler.ReservoirContent.DEFAULT;

    @Unique
    public int powerTier;
    @Unique
    public int pumpSpeed;
    @Unique
    public float drainChance = 1f;

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
        ITweakedPetReservoirType mix = (ITweakedPetReservoirType) cir.getReturnValue();
        mix.setReservoirContent(TweakedPumpjackHandler.ReservoirContent.values()[tag.getByte("reservoirContent")]);
        mix.setPumpSpeed(tag.getInteger("pumpSpeed"));
        mix.setPowerTier(tag.getInteger("powerTier"));
        mix.setDrainChance(tag.getFloat("drainChance"));
    }


    @Inject(method = "writeToNBT", at = @At("RETURN"))
    private void onWriteToNBT(CallbackInfoReturnable<NBTTagCompound> cir) {
        NBTTagCompound tag = cir.getReturnValue();
        tag.setByte("reservoirContent", (byte) this.reservoirContent.ordinal());
        tag.setInteger("pumpSpeed", this.pumpSpeed);
        tag.setInteger("powerTier", this.powerTier);
        tag.setFloat("drainChance", this.drainChance);
    }


}
