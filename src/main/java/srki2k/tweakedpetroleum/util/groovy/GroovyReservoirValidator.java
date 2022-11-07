package srki2k.tweakedpetroleum.util.groovy;

import com.cleanroommc.groovyscript.api.GroovyLog;
import com.cleanroommc.groovyscript.api.IIngredient;
import mekanism.api.gas.GasStack;
import net.minecraftforge.fluids.FluidStack;
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
            msg.add(!(ingredient instanceof FluidStack),
                    "Reservoir({}): Has no valid FluidStack", name);
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
            msg.add(!(ingredient instanceof GasStack),
                    "Reservoir({}): Has no valid GasStack", name);
        }
        validateGroovyReservoir(msg, name, minSize, maxSize, replenishRate, pumpSpeed, weight, powerTier, drainChance,
                dimBlacklist, dimWhitelist, biomeBlacklist, biomeWhitelist);
    }

    private static void validateGroovyReservoir(GroovyLog.Msg msg, String name, int minSize, int maxSize, int replenishRate, int pumpSpeed, int weight, int powerTier, Float drainChance,
                                                List<Integer> dimBlacklist, List<Integer> dimWhitelist, List<String> biomeBlacklist, List<String> biomeWhitelist) {

        msg.add(minSize <= 0,
                "Reservoir({}): minSize has to be at least 1mb!", name);

        msg.add(maxSize < minSize,
                "Reservoir({}): maxSize can not be smaller than minSize!", name);

        msg.add(weight < 1,
                "Reservoir({}): weight has to be greater than or equal to 1!", name);

        msg.add(pumpSpeed <= 0,
                "Reservoir({}): Pump Speed has to be at least 1mb/t", name);

        msg.add(pumpSpeed < replenishRate,
                "Reservoir({}): Pump Speed can not be smaller than Replenish Rate!", name);

        msg.add(powerTier < 0,
                "Reservoir({}): powerTier can not be smaller than 0!", name);

        if (!drainChance.isNaN()) {
            msg.add(drainChance < 0 || 1 < drainChance,
                    "Reservoir({}): drainChance must be between 0 and 1!", name);
        }

        msg.add(!PowerTierHandler.powerTierExists(powerTier),
                "Reservoir({}): supplied powerTier is not valid", name);


        if (dimBlacklist != null) {
            dimBlacklist.forEach(id -> {
                msg.add(id == null,
                        "Reservoir({}): Dim id: '{}' in dimBlacklist is Null", name, id);
            });
        }


        if (dimWhitelist != null) {
            dimWhitelist.forEach(id -> {
                msg.add(id == null,
                        "Reservoir({}): Dim id: '{}' in dimWhitelist is Null", name, id);
            });
        }

        if (biomeBlacklist != null) {
            biomeBlacklist.forEach(string -> {
                msg.add(string == null || string.isEmpty(),
                        "Reservoir({}): String '{}' in biomeBlacklist is either Empty or Null", string);
            });

        }

        if (biomeWhitelist != null) {
            biomeWhitelist.forEach(string -> {
                msg.add(string == null || string.isEmpty(),
                        "Reservoir({}): String '{}' in biomeWhitelist is either Empty or Null", string);
            });
        }

    }


}
