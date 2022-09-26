package srki2k.tweakedpetroleum.common.compat.crafttweaker;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.liquid.ILiquidStack;
import srki2k.tweakedlib.api.powertier.PowerTierHandler;
import srki2k.tweakedpetroleum.api.crafting.TweakedPumpjackHandler;
import srki2k.tweakedpetroleum.api.ihelpers.IReservoirType;
import srki2k.tweakedpetroleum.util.ReservoirValidation;
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
        //This exists to keep compatibility with old projects
        CraftTweakerAPI.logInfo("You are using 'mods.TweakedPetroleum.TweakedReservoir.registerPowerUsage()' to register power tiers but you should ideally be using 'mods.TweakedLib.TweakedPowerTier.registerPowerUsage()'");
        PowerTierHandler.registerPowerUsage(tier, capacity, rft);
    }

}