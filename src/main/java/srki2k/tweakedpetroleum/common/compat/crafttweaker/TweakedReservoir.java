package srki2k.tweakedpetroleum.common.compat.crafttweaker;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.liquid.ILiquidStack;
import srki2k.tweakedpetroleum.api.crafting.TweakedPumpjackHandler;
import srki2k.tweakedpetroleum.api.ihelpers.IReservoirType;
import srki2k.tweakedpetroleum.util.ReservoirValidation;
import srki2k.tweakedpetroleum.util.errorloggingutil.startup.StartupCTLogger;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.ArrayList;
import java.util.List;

@ZenClass("mods.TweakedPetroleum.TweakedReservoir")
@ZenRegister
public class TweakedReservoir {

    @ZenMethod
    public static void registerReservoir(String name, ILiquidStack fluid, int minSize, int maxSize, int replenishRate, int pumpSpeed, int weight, int powerTier,
                                         int[] dimBlacklist, int[] dimWhitelist, String[] biomeBlacklist, String[] biomeWhitelist) {

        List<String> biomeBlacklistList = new ArrayList<>();
        List<String> biomeWhitelistList = new ArrayList<>();

        ReservoirValidation.validateReservoir(name, minSize, maxSize, replenishRate, pumpSpeed, weight, powerTier,
                biomeBlacklist, biomeWhitelist,
                biomeBlacklistList, biomeWhitelistList);


        IReservoirType res = TweakedPumpjackHandler.addTweakedReservoir(name, fluid.getName(), minSize, maxSize, replenishRate, pumpSpeed, weight, powerTier);

        res.setReservoirContent(TweakedPumpjackHandler.ReservoirContent.LIQUID);
        res.setDimensionBlacklist(dimBlacklist);
        res.setDimensionWhitelist(dimWhitelist);
        res.setBiomeBlacklist(biomeBlacklistList.toArray(new String[0]));
        res.setBiomeWhitelist(biomeWhitelistList.toArray(new String[0]));

        CraftTweakerAPI.logInfo("Added Reservoir Type: " + name);

    }

    @ZenMethod
    public static void registerReservoirWithDrainChance(String name, ILiquidStack fluid, int minSize, int maxSize, int replenishRate, int pumpSpeed, float drainChance, int weight, int powerTier,
                                                        int[] dimBlacklist, int[] dimWhitelist, String[] biomeBlacklist, String[] biomeWhitelist) {

        List<String> biomeBlacklistList = new ArrayList<>();
        List<String> biomeWhitelistList = new ArrayList<>();

        ReservoirValidation.validateReservoir(name, minSize, maxSize, replenishRate, pumpSpeed, weight, powerTier, drainChance,
                biomeBlacklist, biomeWhitelist,
                biomeBlacklistList, biomeWhitelistList);


        IReservoirType res = TweakedPumpjackHandler.addTweakedReservoir(name, fluid.getName(), minSize, maxSize, replenishRate, pumpSpeed, weight, powerTier);

        res.setReservoirContent(TweakedPumpjackHandler.ReservoirContent.LIQUID);
        res.setDrainChance(drainChance);
        res.setDimensionBlacklist(dimBlacklist);
        res.setDimensionWhitelist(dimWhitelist);
        res.setBiomeBlacklist(biomeBlacklistList.toArray(new String[0]));
        res.setBiomeWhitelist(biomeWhitelistList.toArray(new String[0]));

        CraftTweakerAPI.logInfo("Added Reservoir Type: " + name);

    }

    @ZenMethod
    public static void registerPowerUsage(int tier, int capacity, int rft) {

        if (tier < 0) {
            CraftTweakerAPI.logError("PowerUsage tier can not be smaller than 0!", new StartupCTLogger.TPRntimeExeption());
        }
        if (capacity < 1) {
            CraftTweakerAPI.logError("PowerUsage capacity can not be smaller than 1!", new StartupCTLogger.TPRntimeExeption());
        }
        if (capacity == Integer.MAX_VALUE) {
            CraftTweakerAPI.logError("PowerUsage capacity should not be MAX_INT!", new StartupCTLogger.TPRntimeExeption());
        }
        if (capacity < rft) {
            CraftTweakerAPI.logError("PowerUsage capacity can not be smaller than rft!", new StartupCTLogger.TPRntimeExeption());
        }


        TweakedPumpjackHandler.registerPowerUsage(tier, capacity, rft);
        CraftTweakerAPI.logInfo("Added power tier: " + tier + " with capacity: " + capacity + " and " + rft + " RF/t");

    }

}