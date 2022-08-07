package srki2k.tweakedpetroleum.common.mixin;

import crafttweaker.api.liquid.ILiquidStack;
import flaxbeard.immersivepetroleum.common.compat.crafttweaker.ReservoirTweaker;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import srki2k.tweakedpetroleum.TweakedPetroleum;
import srki2k.tweakedpetroleum.common.Configs;

@Mixin(ReservoirTweaker.class)
public abstract class MixinReservoirTweaker {

    @Inject(method = "registerReservoir", at = @At("HEAD"), remap = false, cancellable = true)
    private static void onAddConfigReservoirs(String name, ILiquidStack fluid, int minSize, int maxSize, int replenishRate, int weight, int[] dimBlacklist, int[] dimWhitelist, String[] biomeBlacklist, String[] biomeWhitelist, CallbackInfo ci) {
        TweakedPetroleum.LOGGER.info("You are trying to add a reservoir (" + name + ") with the default IP zen method, this is not recommend");

        if (Configs.TPConfig.ImmersivePetroleumOverwrites.disableDefaultRFTZenScriptLoading) {
            ci.cancel();
        }
    }

}
