package srki2k.tweakedpetroleum.common.compat.crafttweaker;


import com.google.common.collect.Lists;
import srki2k.tweakedpetroleum.api.crafting.TweakedPumpjackHandler;
import crafttweaker.CraftTweakerAPI;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.liquid.ILiquidStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.List;

@ZenClass("mods.TweakedPetroleum.TweakedReservoir")
@ZenRegister
public class TweakedReservoir {

    @ZenMethod
    public static void registerReservoir(String name, ILiquidStack fluid, int minSize, int maxSize, int replenishRate, int pumpSpeed, int weight, int powerTier,
                                         int[] dimBlacklist, int[] dimWhitelist, String[] biomeBlacklist, String[] biomeWhitelist) {
        List<String> biomeBlacklistList = Lists.newArrayList();
        List<String> biomeWhitelistList = Lists.newArrayList();

        if (name.isEmpty()) {
            CraftTweakerAPI.logError("Reservoir name can not be empty string!");
        } else if (minSize <= 0) {
            CraftTweakerAPI.logError("Reservoir minSize has to be at least 1mb!");
        } else if (maxSize < minSize) {
            CraftTweakerAPI.logError("Reservoir maxSize can not be smaller than minSize!");
        } else if (weight < 1) {
            CraftTweakerAPI.logError("Reservoir weight has to be greater than or equal to 1!");
        } else if (pumpSpeed <= 0) {
            CraftTweakerAPI.logError("Reservoir Pump Speed has to be at least 1mb/t");
        } else if (pumpSpeed < replenishRate) {
            CraftTweakerAPI.logError("Reservoir Pump Speed can not be smaller than Replenish Rate!");
        } else if (powerTier < 0) {

            CraftTweakerAPI.logError("Reservoir powerTier can not be smaller than 0!");
        }

        String rFluid = fluid.getName();

        TweakedPumpjackHandler.TweakedReservoirType res = TweakedPumpjackHandler.addTweakedReservoir(name, rFluid, minSize, maxSize, replenishRate, pumpSpeed, weight, powerTier);

        for (String string : biomeBlacklist) {
            if (string == null || string.isEmpty()) {
                CraftTweakerAPI.logError("String '" + string + "' in biomeBlacklist is either Empty or Null");
            } else {
                biomeBlacklistList.add(string);
            }
        }

        for (String string : biomeWhitelist) {
            if (string == null || string.isEmpty()) {
                CraftTweakerAPI.logError("String '" + string + "' in biomeBlacklist is either Empty or Null");
            } else {
                biomeWhitelistList.add(string);
            }
        }

        res.dimensionBlacklist = dimBlacklist;
        res.dimensionWhitelist = dimWhitelist;
        res.biomeBlacklist = biomeBlacklistList.toArray(new String[0]);
        res.biomeWhitelist = biomeWhitelistList.toArray(new String[0]);

        CraftTweakerAPI.logInfo("Added Reservoir Type: " + name);
    }

    @ZenMethod
    public static void registerPowerUsage(int tier, int capacity, int rft) {
        if (tier < 0) {
            CraftTweakerAPI.logError("PowerUsage tier can not be smaller than 0!");
        } else if (capacity < 1) {
            CraftTweakerAPI.logError("PowerUsage capacity can not be smaller than 1!");
        } else if (capacity == Integer.MAX_VALUE) {
            CraftTweakerAPI.logError("PowerUsage capacity should not be MAX_INT!");
        } else if (capacity < rft) {
            CraftTweakerAPI.logError("PowerUsage capacity can not be smaller than rft!");
        }

        TweakedPumpjackHandler.registerPowerUsage(tier, capacity, rft);
    }

}