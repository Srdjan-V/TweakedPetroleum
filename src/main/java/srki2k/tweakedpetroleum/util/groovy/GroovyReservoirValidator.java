package srki2k.tweakedpetroleum.util.groovy;

import com.cleanroommc.groovyscript.api.GroovyLog;
import com.cleanroommc.groovyscript.api.IIngredient;
import com.cleanroommc.groovyscript.helper.ingredient.IngredientHelper;
import mekanism.api.gas.GasStack;
import srki2k.tweakedlib.api.powertier.PowerTierHandler;

import java.util.List;

public class GroovyReservoirValidator {

    public static void validateFluidGroovyReservoir(GroovyLog.Msg msg, String name, IIngredient ingredient, int minSize, int maxSize, int replenishRate, int pumpSpeed, int weight, int powerTier, Float drainChance,
                                                    List<Integer> dimBlacklist, List<Integer> dimWhitelist, List<String> biomeBlacklist, List<String> biomeWhitelist) {
        msg.add(name == null || name.isEmpty(),
                () -> "Reservoir name can not be a empty string!");

        if (ingredient == null) {
            msg.add("FluidStack can't be null");
        } else {
            msg.add(!IngredientHelper.isFluid(ingredient),
                    () -> "Reservoir(" + name + "): Has no valid FluidStack");
        }

        validateGroovyReservoir(msg, name, minSize, maxSize, replenishRate, pumpSpeed, weight, powerTier, drainChance,
                dimBlacklist, dimWhitelist, biomeBlacklist, biomeWhitelist);
    }

    public static void validateGasGroovyReservoir(GroovyLog.Msg msg, String name, IIngredient ingredient, int minSize, int maxSize, int replenishRate, int pumpSpeed, int weight, int powerTier, Float drainChance,
                                                  List<Integer> dimBlacklist, List<Integer> dimWhitelist, List<String> biomeBlacklist, List<String> biomeWhitelist) {
        msg.add(name == null || name.isEmpty(),
                () -> "Reservoir name can not be a empty string!");

        if (ingredient == null) {
            msg.add("GasStack can't be null");
        } else {
            msg.add(ingredient instanceof GasStack,
                    () -> "Reservoir(" + name + "): Has no valid GasStack");
        }
        validateGroovyReservoir(msg, name, minSize, maxSize, replenishRate, pumpSpeed, weight, powerTier, drainChance,
                dimBlacklist, dimWhitelist, biomeBlacklist, biomeWhitelist);
    }

    private static void validateGroovyReservoir(GroovyLog.Msg msg, String name, int minSize, int maxSize, int replenishRate, int pumpSpeed, int weight, int powerTier, Float drainChance,
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
                msg.add(id == null,
                        () -> "Reservoir(" + name + "): Dim id: '" + id + "' in dimBlacklist is Null");
            });
        }


        if (dimWhitelist != null) {
            dimWhitelist.forEach(id -> {
                msg.add(id == null,
                        () -> "Reservoir(" + name + "): Dim id: '" + id + "' in dimWhitelist is Null");
            });
        }

        if (biomeBlacklist != null) {
            biomeBlacklist.forEach(string -> {
                msg.add(string == null || string.isEmpty(),
                        () -> "Reservoir(" + name + "): String '" + string + "' in biomeBlacklist is either Empty or Null");
            });

        }

        if (biomeWhitelist != null) {
            biomeWhitelist.forEach(string -> {
                msg.add(string == null || string.isEmpty(),
                        () -> "Reservoir(" + name + "): String '" + string + "' in biomeWhitelist is either Empty or Null");
            });
        }
    }


}
