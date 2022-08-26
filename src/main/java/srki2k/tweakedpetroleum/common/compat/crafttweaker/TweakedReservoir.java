package srki2k.tweakedpetroleum.common.compat.crafttweaker;

import srki2k.tweakedpetroleum.api.crafting.TweakedPumpjackHandler;
import crafttweaker.CraftTweakerAPI;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.liquid.ILiquidStack;
import srki2k.tweakedpetroleum.api.crafting.IReservoirType;
import srki2k.tweakedpetroleum.util.errorloggingutil.ErrorLoggingUtil;
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

        if (name.isEmpty()) {
            errors.add("Reservoir name can not be a empty string!");
        }
        if (minSize <= 0) {
            errors.add("Reservoir(" + name + ") minSize has to be at least 1mb!");
        }
        if (maxSize < minSize) {
            errors.add("Reservoir(" + name + ") maxSize can not be smaller than minSize!");
        }
        if (weight < 1) {
            errors.add("Reservoir(" + name + ") weight has to be greater than or equal to 1!");
        }
        if (pumpSpeed <= 0) {
            errors.add("Reservoir(" + name + ") Pump Speed has to be at least 1mb/t");
        }
        if (pumpSpeed < replenishRate) {
            errors.add("Reservoir(" + name + ") Pump Speed can not be smaller than Replenish Rate!");
        }
        if (powerTier < 0) {
            errors.add("Reservoir(" + name + ") powerTier can not be smaller than 0!");
        }

        for (String string : biomeBlacklist) {
            if (string == null || string.isEmpty()) {
                errors.add("Reservoir(" + name + ") String '" + string + "' in biomeBlacklist is either Empty or Null");
                continue;
            }

            biomeBlacklistList.add(string);
        }

        for (String string : biomeWhitelist) {
            if (string == null || string.isEmpty()) {
                errors.add("Reservoir(" + name + ") String '" + string + "' in biomeBlacklist is either Empty or Null");
                continue;
            }

            biomeWhitelistList.add(string);
        }

        if (errors.isEmpty()) {
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

        ErrorLoggingUtil.getStartupInstance().addErrors(errors);
    }

    @ZenMethod
    public static void registerPowerUsage(int tier, int capacity, int rft) {
        List<String> errors = new ArrayList<>();

        if (tier < 0) {
            errors.add("PowerUsage tier can not be smaller than 0!");
        }
        if (capacity < 1) {
            errors.add("PowerUsage capacity can not be smaller than 1!");
        }
        if (capacity == Integer.MAX_VALUE) {
            errors.add("PowerUsage capacity should not be MAX_INT!");
        }
        if (capacity < rft) {
            errors.add("PowerUsage capacity can not be smaller than rft!");
        }

        if (errors.isEmpty()) {
            TweakedPumpjackHandler.registerPowerUsage(tier, capacity, rft);
            CraftTweakerAPI.logInfo("Added power tier: " + tier + "with capacity:" + capacity + "and" + rft + "RF/t");
            return;
        }

        ErrorLoggingUtil.getStartupInstance().addErrors(errors);

    }

}