package io.github.srdjanv.tweakedpetroleum.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = flaxbeard.immersivepetroleum.common.Config.class, remap = false)
public abstract class MixinConfig {

    @Inject(method = "addConfigReservoirs", at = @At("HEAD"), remap = false, cancellable = true)
    private static void onAddConfigReservoirs(String[] reservoirs, CallbackInfo ci) {
        ci.cancel();
    }
}
