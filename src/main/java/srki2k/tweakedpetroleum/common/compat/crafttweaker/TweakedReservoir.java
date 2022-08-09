package srki2k.tweakedpetroleum.common.compat.crafttweaker;


import com.google.common.collect.Lists;
import srki2k.tweakedpetroleum.api.crafting.TweakedPumpjackHandler;
import crafttweaker.CraftTweakerAPI;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.liquid.ILiquidStack;
import srki2k.tweakedpetroleum.api.crafting.IReservoirType;
import srki2k.tweakedpetroleum.util.ErrorLoggingUtil;
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
        List<String> errors = new ArrayList<>();
        boolean validState = true;

        if (name.isEmpty()) {
            String error = "Reservoir name can not be empty string!";
            CraftTweakerAPI.logError(error);
            errors.add(error);
            validState = false;
        }
        if (minSize <= 0) {
            String error = "Reservoir minSize has to be at least 1mb!";
            CraftTweakerAPI.logError(error);
            errors.add(error);
            validState = false;
        }
        if (maxSize < minSize) {
            String error = "Reservoir maxSize can not be smaller than minSize!";
            CraftTweakerAPI.logError(error);
            errors.add(error);
            validState = false;
        }
        if (weight < 1) {
            String error = "Reservoir weight has to be greater than or equal to 1!";
            CraftTweakerAPI.logError(error);
            errors.add(error);
            validState = false;
        }
        if (pumpSpeed <= 0) {
            String error = "Reservoir Pump Speed has to be at least 1mb/t";
            CraftTweakerAPI.logError(error);
            errors.add(error);
            validState = false;
        }
        if (pumpSpeed < replenishRate) {
            String error = "Reservoir Pump Speed can not be smaller than Replenish Rate!";
            CraftTweakerAPI.logError(error);
            errors.add(error);
            validState = false;
        }
        if (powerTier < 0) {
            String error = "Reservoir powerTier can not be smaller than 0!";
            CraftTweakerAPI.logError(error);
            errors.add(error);
            validState = false;
        }

        for (String string : biomeBlacklist) {
            if (string == null || string.isEmpty()) {
                String error = "String '" + string + "' in biomeBlacklist is either Empty or Null";
                CraftTweakerAPI.logError(error);
                errors.add(error);
                validState = false;
                continue;
            }

            biomeBlacklistList.add(string);
        }

        for (String string : biomeWhitelist) {
            if (string == null || string.isEmpty()) {
                String error = "String '" + string + "' in biomeBlacklist is either Empty or Null";
                CraftTweakerAPI.logError(error);
                errors.add(error);
                validState = false;
                continue;
            }

            biomeWhitelistList.add(string);
        }

        if (validState) {
            String rFluid = fluid.getName();
            IReservoirType res =
                    TweakedPumpjackHandler.addTweakedReservoir(name, rFluid, minSize, maxSize, replenishRate, pumpSpeed, weight, powerTier);

            res.setDimensionBlacklist(dimBlacklist);
            res.setDimensionWhitelist(dimWhitelist);
            res.setBiomeBlacklist(biomeBlacklistList.toArray(new String[0]));
            res.setBiomeWhitelist(biomeWhitelistList.toArray(new String[0]));

            CraftTweakerAPI.logInfo("Added Reservoir Type: " + name);
            return;
        }

        ErrorLoggingUtil.Startup.setErrors(errors);
    }

    @ZenMethod
    public static void registerPowerUsage(int tier, int capacity, int rft) {
        List<String> errors = new ArrayList<>();
        boolean validState = true;
        if (tier < 0) {
            String error = "PowerUsage tier can not be smaller than 0!";
            CraftTweakerAPI.logError(error);
            errors.add(error);
            validState = false;
        }
        if (capacity < 1) {
            String error = "PowerUsage capacity can not be smaller than 1!";
            CraftTweakerAPI.logError(error);
            errors.add(error);
            validState = false;
        }
        if (capacity == Integer.MAX_VALUE) {
            String error = "PowerUsage capacity should not be MAX_INT!";
            CraftTweakerAPI.logError(error);
            errors.add(error);
            validState = false;
        }
        if (capacity < rft) {
            String error = "PowerUsage capacity can not be smaller than rft!";
            CraftTweakerAPI.logError(error);
            errors.add(error);
            validState = false;
        }

        if (validState) {
            TweakedPumpjackHandler.registerPowerUsage(tier, capacity, rft);
            CraftTweakerAPI.logInfo("Added power tier: " + tier + "with capacity:" + capacity + "and" + rft + "RF/t");
            return;
        }

        ErrorLoggingUtil.Startup.setErrors(errors);

    }

}