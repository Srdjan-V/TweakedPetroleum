package io.github.srdjanv.tweakedpetroleum.common.compat.crafttweaker;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.liquid.ILiquidStack;
import io.github.srdjanv.tweakedlib.api.powertier.PowerTierHandler;
import io.github.srdjanv.tweakedpetroleum.api.crafting.TweakedPumpjackHandler;
import io.github.srdjanv.tweakedpetroleum.api.mixins.IReservoirType;
import io.github.srdjanv.tweakedpetroleum.util.ReservoirValidation;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@SuppressWarnings("unused")
@ZenClass("mods.TweakedPetroleum.TweakedReservoir")
@ZenRegister
public class TweakedReservoir {

    @ZenMethod
    public static void registerReservoir(String name, ILiquidStack fluid, int minSize, int maxSize, int replenishRate, int pumpSpeed, int weight, int powerTier,
                                         @Optional int[] dimBlacklist, @Optional int[] dimWhitelist, @Optional String[] biomeBlacklist, @Optional String[] biomeWhitelist) {
        IReservoirType res;
        if (ReservoirValidation.validateReservoir(name, TweakedPumpjackHandler.ReservoirContent.LIQUID, fluid,
                minSize, maxSize, replenishRate, pumpSpeed, weight, powerTier,
                biomeBlacklist, biomeWhitelist)) {
            res = TweakedPumpjackHandler.addTweakedReservoir(name, fluid.getName(), minSize, maxSize, replenishRate, pumpSpeed, weight, powerTier);
        } else {
            if (name != null && !name.isEmpty()) {
                CraftTweakerAPI.logError(String.format("Added dummy reservoir: %s", name));
                TweakedPumpjackHandler.addTweakedReservoir(name, "water", 0, 10, 0, 10, 0, PowerTierHandler.getFallbackPowerTier().getId());
            }
            return;
        }

        res.setReservoirContent(TweakedPumpjackHandler.ReservoirContent.LIQUID);
        if (dimBlacklist != null) res.setDimensionBlacklist(dimBlacklist);
        if (dimWhitelist != null) res.setDimensionWhitelist(dimWhitelist);
        if (biomeBlacklist != null) res.setBiomeBlacklist(biomeBlacklist);
        if (biomeWhitelist != null) res.setBiomeWhitelist(biomeWhitelist);

        CraftTweakerAPI.logInfo("Added Reservoir Type: " + name);
    }

    @ZenMethod
    public static void registerReservoirWithDrainChance(String name, ILiquidStack fluid, int minSize, int maxSize, int replenishRate, int pumpSpeed, float drainChance, int weight, int powerTier,
                                                        @Optional int[] dimBlacklist, @Optional int[] dimWhitelist, @Optional String[] biomeBlacklist, @Optional String[] biomeWhitelist) {

        IReservoirType res;
        if (ReservoirValidation.validateReservoir(name, TweakedPumpjackHandler.ReservoirContent.LIQUID, fluid,
                minSize, maxSize, replenishRate, pumpSpeed, weight, powerTier, drainChance,
                biomeBlacklist, biomeWhitelist)) {
            res = TweakedPumpjackHandler.addTweakedReservoir(name, fluid.getName(), minSize, maxSize, replenishRate, pumpSpeed, weight, powerTier);
        } else {
            if (name != null && !name.isEmpty()) {
                CraftTweakerAPI.logError(String.format("Added dummy reservoir: %s", name));
                TweakedPumpjackHandler.addTweakedReservoir(name, "water", 0, 10, 0, 10, 0, PowerTierHandler.getFallbackPowerTier().getId());
            }
            return;
        }

        res.setReservoirContent(TweakedPumpjackHandler.ReservoirContent.LIQUID);
        res.setDrainChance(drainChance);
        if (dimBlacklist != null) res.setDimensionBlacklist(dimBlacklist);
        if (dimWhitelist != null) res.setDimensionWhitelist(dimWhitelist);
        if (biomeBlacklist != null) res.setBiomeBlacklist(biomeBlacklist);
        if (biomeWhitelist != null) res.setBiomeWhitelist(biomeWhitelist);

        CraftTweakerAPI.logInfo("Added Reservoir Type: " + name);
    }

    @ZenMethod
    @Deprecated
    public static void registerPowerUsage(int tier, int capacity, int rft) {
        CraftTweakerAPI.logError("This method is deprecated 'mods.TweakedPetroleum.TweakedReservoir.registerPowerUsage()");
        CraftTweakerAPI.logError("Go see https://github.com/Srdjan-V/TweakedPetroleum/blob/master/examples/examples.zs for the new way of adding Powertiers");
    }

}