package srki2k.tweakedpetroleum.mixin;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.api.liquid.ILiquidStack;
import flaxbeard.immersivepetroleum.common.compat.crafttweaker.ReservoirTweaker;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import srki2k.tweakedpetroleum.util.errorloggingutil.startup.StartupCTLogger;

@Mixin(value = ReservoirTweaker.class, remap = false)
public abstract class MixinReservoirTweaker {

    @Inject(method = "registerReservoir", at = @At("HEAD"), remap = false, cancellable = true)
    private static void onAddConfigReservoirs(String name, ILiquidStack fluid, int minSize, int maxSize, int replenishRate, int weight, int[] dimBlacklist, int[] dimWhitelist, String[] biomeBlacklist, String[] biomeWhitelist, CallbackInfo ci) {
        CraftTweakerAPI.logError("You are trying to add a reservoir (" + name + ") with the default IP zen method, this is not supported", new StartupCTLogger.TPRntimeExeption());
        ci.cancel();
    }

}
