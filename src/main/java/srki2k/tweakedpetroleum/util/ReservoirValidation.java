package srki2k.tweakedpetroleum.util;

import com.cleanroommc.groovyscript.api.GroovyLog;
import com.cleanroommc.groovyscript.api.IIngredient;
import com.cleanroommc.groovyscript.helper.ingredient.IngredientHelper;
import crafttweaker.CraftTweakerAPI;
import srki2k.tweakedlib.api.powertier.PowerTierHandler;

import java.util.List;

public class ReservoirValidation {

    public static void validateFluidGrooveReservoir(GroovyLog.Msg msg, String name, IIngredient ingredient, int minSize, int maxSize, int replenishRate, int pumpSpeed, int weight, int powerTier, Float drainChance,
                                                    List<Integer> dimBlacklist, List<Integer> dimWhitelist, List<String> biomeBlacklist, List<String> biomeWhitelist) {
        msg.add(name == null || name.isEmpty(),
                () -> "Reservoir name can not be a empty string!");

        if (ingredient == null) {
            msg.add("FluidStack can't be null");
        } else {
            msg.add(!IngredientHelper.isFluid(ingredient),
                    () -> "Reservoir(" + name + "): Has no valid FluidStack");
        }

        validateGrooveReservoir(msg, name, minSize, maxSize, replenishRate, pumpSpeed, weight, powerTier, drainChance,
                dimBlacklist, dimWhitelist, biomeBlacklist, biomeWhitelist);
    }

    public static void validateGasGrooveReservoir(GroovyLog.Msg msg, String name, IIngredient ingredient, int minSize, int maxSize, int replenishRate, int pumpSpeed, int weight, int powerTier, Float drainChance,
                                                  List<Integer> dimBlacklist, List<Integer> dimWhitelist, List<String> biomeBlacklist, List<String> biomeWhitelist) {
        msg.add(name == null || name.isEmpty(),
                () -> "Reservoir name can not be a empty string!");

        if (ingredient == null) {
            msg.add("GasStack can't be null");
        } else {
            msg.add(IngredientHelper.isGas(ingredient),
                    () -> "Reservoir(" + name + "): Has no valid GasStack");
        }
        validateGrooveReservoir(msg, name, minSize, maxSize, replenishRate, pumpSpeed, weight, powerTier, drainChance,
                dimBlacklist, dimWhitelist, biomeBlacklist, biomeWhitelist);
    }

    private static void validateGrooveReservoir(GroovyLog.Msg msg, String name, int minSize, int maxSize, int replenishRate, int pumpSpeed, int weight, int powerTier, Float drainChance,
                                                List<Integer> dimBlacklist, List<Integer> dimWhitelist, List<String> biomeBlacklist, List<String> biomeWhitelist) {

        msg.add(minSize <= 0,
                () -> "Reservoir(" + name + "): minSize has to be at least 1mb!");

        msg.add(maxSize < minSize,
                () -> "Reservoir(" + name + "): maxSize can not be smaller than minSize!");

        msg.add(weight < 1,
                () -> "Reservoir(" + name + "): weight has to be greater than or equal to 1!");

        msg.add(pumpSpeed <= 0,
                () -> "Reservoir(" + name + "): Pump Speed has to be at least 1mb/t");

        msg.add(pumpSpeed < replenishRate,
                () -> "Reservoir(" + name + "): Pump Speed can not be smaller than Replenish Rate!");

        msg.add(powerTier < 0,
                () -> "Reservoir(" + name + "): powerTier can not be smaller than 0!");

        if (!drainChance.isNaN()) {
            msg.add(drainChance < 0 || 1 < drainChance,
                    () -> "Reservoir(" + name + "): drainChance must be between 0 and 1!");
        }

        msg.add(!PowerTierHandler.powerTierExists(powerTier),
                () -> "Reservoir(" + name + "): supplied powerTier is not valid");


        if (dimBlacklist != null) {
            dimBlacklist.forEach(id -> {
                if (id == null) {
                    msg.add("Reservoir(" + name + "): Dim id '" + id + "' in dimBlacklist is Null");
                }
            });
        }


        if (dimWhitelist != null) {
            dimWhitelist.forEach(id -> {
                msg.add(id == null,
                        () -> "Reservoir(" + name + "): Dim id '" + id + "' in dimBlacklist is Null");
            });
        }

        if (biomeBlacklist != null) {
            biomeBlacklist.forEach(string -> {
                msg.add(string == null || string.isEmpty(),
                        () -> "Reservoir(" + name + "): String '" + string + "' in biomeBlacklist is either Empty or Null, removing from list");
            });

        }

        if (biomeWhitelist != null) {
            biomeWhitelist.forEach(string -> {
                msg.add(string == null || string.isEmpty(),
                        () -> "Reservoir(" + name + "): String '" + string + "' in biomeWhitelist is either Empty or Null, removing from list");
            });
        }
    }

    public static void validateReservoir(String name, int minSize, int maxSize, int replenishRate, int pumpSpeed, int weight, int powerTier, float drainChance,
                                         String[] biomeBlacklist, String[] biomeWhitelist,
                                         List<String> biomeBlacklistList, List<String> biomeWhitelistList) {

        if (drainChance < 0 || 1 < drainChance) {
            CraftTweakerAPI.logError("Reservoir drainChance must be between 0 and 1!");
        }

        validateReservoir(name, minSize, maxSize, replenishRate, pumpSpeed, weight, powerTier,
                biomeBlacklist, biomeWhitelist,
                biomeBlacklistList, biomeWhitelistList);

    }

    public static void validateReservoir(String name, int minSize, int maxSize, int replenishRate, int pumpSpeed, int weight, int powerTier,
                                         String[] biomeBlacklist, String[] biomeWhitelist,
                                         List<String> biomeBlacklistList, List<String> biomeWhitelistList) {

        if (name.isEmpty()) {
            CraftTweakerAPI.logError("Reservoir name can not be a empty string!");
        }
        if (minSize <= 0) {
            CraftTweakerAPI.logError("Reservoir(" + name + ") minSize has to be at least 1mb!");
        }
        if (maxSize < minSize) {
            CraftTweakerAPI.logError("Reservoir(" + name + ":) maxSize can not be smaller than minSize!");
        }
        if (weight < 1) {
            CraftTweakerAPI.logError("Reservoir(" + name + ") weight has to be greater than or equal to 1!");
        }
        if (pumpSpeed <= 0) {
            CraftTweakerAPI.logError("Reservoir(" + name + ") Pump Speed has to be at least 1mb/t");
        }
        if (pumpSpeed < replenishRate) {
            CraftTweakerAPI.logError("Reservoir(" + name + ") Pump Speed can not be smaller than Replenish Rate!");
        }
        if (powerTier < 0) {
            CraftTweakerAPI.logError("Reservoir(" + name + ") powerTier can not be smaller than 0!");
        }

        if (biomeBlacklist != null) {
            for (String string : biomeBlacklist) {
                if (string == null || string.isEmpty()) {
                    CraftTweakerAPI.logError("Reservoir(" + name + ") String '" + string + "' in biomeBlacklist is either Empty or Null");
                    continue;
                }

                biomeBlacklistList.add(string);
            }
        }

        if (biomeWhitelist != null) {
            for (String string : biomeWhitelist) {
                if (string == null || string.isEmpty()) {
                    CraftTweakerAPI.logError("Reservoir(" + name + ") String '" + string + "' in biomeWhitelist is either Empty or Null");
                    continue;
                }

                biomeWhitelistList.add(string);
            }
        }

    }

}

