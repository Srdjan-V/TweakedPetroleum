package io.github.srdjanv.tweakedpetroleum.mixin;

import flaxbeard.immersivepetroleum.api.crafting.PumpjackHandler;
import io.github.srdjanv.tweakedpetroleum.api.crafting.TweakedPumpjackHandler;
import io.github.srdjanv.tweakedpetroleum.api.mixins.ITweakedPetReservoirType;
import io.github.srdjanv.tweakedpetroleum.common.Configs;
import io.github.srdjanv.tweakedpetroleum.common.DefaultReservoirs;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(value = flaxbeard.immersivepetroleum.common.Config.class, remap = false)
public abstract class MixinConfig {

    @Inject(method = "addConfigReservoirs", at = @At("HEAD"), cancellable = true)
    private static void onAddConfigReservoirs(String[] reservoirs, CallbackInfo ci) {
        if (!Configs.TPConfig.DefaultReservoirs.enableIPConfigReservoirRegistration) ci.cancel();
    }

    @ModifyVariable(method = "addConfigReservoirs",
            at = @At(value = "STORE"),
            name = "res")
    private static PumpjackHandler.ReservoirType onAddConfigReservoirs(PumpjackHandler.ReservoirType res) {
        final ITweakedPetReservoirType tweakedPetReservoirType = (ITweakedPetReservoirType) res;
        final DefaultReservoirs instance = DefaultReservoirs.getInstance();
        tweakedPetReservoirType.setPowerTier(instance.defaultPowerTier());
        tweakedPetReservoirType.setPumpSpeed(instance.defaultPumpSpeed());
        tweakedPetReservoirType.setReservoirContent(TweakedPumpjackHandler.ReservoirContent.LIQUID);
        return res;
    }
}
