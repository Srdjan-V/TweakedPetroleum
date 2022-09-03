package srki2k.tweakedpetroleum.common.compat.crafttweaker;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.liquid.ILiquidStack;
import srki2k.tweakedpetroleum.api.crafting.TweakedPumpjackHandler;
import srki2k.tweakedpetroleum.api.ihelpers.IReservoirType;
import srki2k.tweakedpetroleum.util.ReservoirValidation;
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

        ReservoirValidation.validateReservoir(name, minSize, maxSize, replenishRate, pumpSpeed, weight, powerTier,
                dimBlacklist, dimWhitelist, biomeBlacklist, biomeWhitelist,
                biomeBlacklistList, biomeWhitelistList, errors);

        if (errors.isEmpty()) {
            IReservoirType res = TweakedPumpjackHandler.addTweakedReservoir(name, fluid.getName(), minSize, maxSize, replenishRate, pumpSpeed, weight, powerTier);

            res.setReservoirContent(TweakedPumpjackHandler.ReservoirContent.LIQUID);
            res.setDimensionBlacklist(dimBlacklist);
            res.setDimensionWhitelist(dimWhitelist);
            res.setBiomeBlacklist(biomeBlacklistList.toArray(new String[0]));
            res.setBiomeWhitelist(biomeWhitelistList.toArray(new String[0]));

            CraftTweakerAPI.logInfo("Added Reservoir Type: " + name);
        }

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

        ErrorLoggingUtil.getStartupInstance().addErrorToList(errors);
    }

}