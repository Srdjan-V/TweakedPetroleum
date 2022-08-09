package srki2k.tweakedpetroleum.common.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import srki2k.tweakedpetroleum.TweakedPetroleum;
import srki2k.tweakedpetroleum.common.Configs;

@Mixin(flaxbeard.immersivepetroleum.common.Config.class)
public abstract class MixinConfig {

    @Inject(method = "addConfigReservoirs", at = @At("HEAD"), remap = false, cancellable = true)
    private static void onAddConfigReservoirs(String[] reservoirs, CallbackInfo ci) {

        if (Configs.TPConfig.ImmersivePetroleumOverwrites.disableIPReservoirLoading) {
            ci.cancel();
        }
    }
}
