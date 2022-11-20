package srki2k.tweakedpetroleum.util.groovy;

import com.cleanroommc.groovyscript.api.GroovyLog;
import mekanism.api.gas.GasStack;
import net.minecraftforge.fluids.FluidStack;
import srki2k.tweakedlib.api.powertier.PowerTierHandler;

import java.util.List;

public class GroovyReservoirValidator {

    public static void validateFluidGroovyReservoir(GroovyLog.Msg msg, String name, FluidStack ingredient, int minSize, int maxSize, int replenishRate, int pumpSpeed, int weight, int powerTier, Float drainChance,
                                                    List<Integer> dimBlacklist, List<Integer> dimWhitelist, List<String> biomeBlacklist, List<String> biomeWhitelist) {
        validateName(msg, name);
        validateFluidStack(msg, name, ingredient);

        validateGroovyReservoir(msg, name, minSize, maxSize, replenishRate, pumpSpeed, weight, powerTier, drainChance,
                dimBlacklist, dimWhitelist, biomeBlacklist, biomeWhitelist);
    }

    public static void validateGasGroovyReservoir(GroovyLog.Msg msg, String name, GasStack ingredient, int minSize, int maxSize, int replenishRate, int pumpSpeed, int weight, int powerTier, Float drainChance,
                                                  List<Integer> dimBlacklist, List<Integer> dimWhitelist, List<String> biomeBlacklist, List<String> biomeWhitelist) {
        validateName(msg, name);
        validateGasStack(msg, name, ingredient);

        validateGroovyReservoir(msg, name, minSize, maxSize, replenishRate, pumpSpeed, weight, powerTier, drainChance,
                dimBlacklist, dimWhitelist, biomeBlacklist, biomeWhitelist);
    }

    public static void validateGroovyReservoir(GroovyLog.Msg msg, String name, int minSize, int maxSize, int replenishRate, int pumpSpeed, int weight, int powerTier, Float drainChance,
                                               List<Integer> dimBlacklist, List<Integer> dimWhitelist, List<String> biomeBlacklist, List<String> biomeWhitelist) {

        validateMinSize(msg, name, minSize);
        validateMaxSize(msg, name, minSize, maxSize);
        validatePumpSpeed(msg, name, pumpSpeed);
        validateReplenishRate(msg, name, replenishRate, pumpSpeed);

        validateWeight(msg, name, weight);

        validateDrainChance(msg, name, drainChance);
        validatePowerTier(msg, name, powerTier);

        validateDimBlacklist(msg, name, dimBlacklist);
        validateDimWhitelist(msg, name, dimWhitelist);

        validateBiomeBlocklist(msg, name, biomeBlacklist);
        validateBiomeWhitelist(msg, name, biomeWhitelist);

    }

    public static void validateName(GroovyLog.Msg msg, String name) {
        msg.add(name == null || name.isEmpty(),
                () -> "Reservoir name can not be a empty string!");
    }

    public static void validateFluidStack(GroovyLog.Msg msg, String name, FluidStack ingredient) {
        msg.add(ingredient == null,
                "Reservoir({}): FluidStack can't be null", name);
    }

    public static void validateGasStack(GroovyLog.Msg msg, String name, GasStack ingredient) {
        msg.add(ingredient == null,
                "Reservoir({}): GasStack can't be null", name);
    }

    public static void validatePowerTier(GroovyLog.Msg msg, String name, int powerTier) {
        msg.add(!PowerTierHandler.powerTierExists(powerTier),
                "Reservoir({}): supplied powerTier is not valid", name);
    }

    public static void validateReplenishRate(GroovyLog.Msg msg, String name, int replenishRate, int pumpSpeed) {
        msg.add(pumpSpeed < replenishRate,
                "Reservoir({}): Pump Speed can not be smaller than Replenish Rate!", name);
    }

    public static void validatePumpSpeed(GroovyLog.Msg msg, String name, int pumpSpeed) {
        msg.add(pumpSpeed <= 0,
                "Reservoir({}): Pump Speed has to be at least 1mb/t", name);
    }

    public static void validateWeight(GroovyLog.Msg msg, String name, int weight) {
        msg.add(weight < 1,
                "Reservoir({}): weight has to be greater than or equal to 1!", name);
    }

    public static void validateMaxSize(GroovyLog.Msg msg, String name, int minSize, int maxSize) {
        msg.add(maxSize < minSize,
                "Reservoir({}): maxSize can not be smaller than minSize!", name);
    }

    public static void validateMinSize(GroovyLog.Msg msg, String name, int minSize) {
        msg.add(minSize <= 0,
                "Reservoir({}): minSize has to be at least 1mb!", name);
    }

    public static void validateDrainChance(GroovyLog.Msg msg, String name, Float drainChance) {
        if (!drainChance.isNaN()) {
            msg.add(drainChance < 0 || 1 < drainChance,
                    "Reservoir({}): drainChance must be between 0 and 1!", name);
        }
    }

    public static void validateDimBlacklist(GroovyLog.Msg msg, String name, List<Integer> dimBlacklist) {
        if (dimBlacklist != null) {
            dimBlacklist.forEach(id -> {
                msg.add(id == null,
                        "Reservoir({}): Dim id: '{}' in dimBlacklist is Null", name, id);
            });
        }
    }

    public static void validateDimWhitelist(GroovyLog.Msg msg, String name, List<Integer> dimWhitelist) {
        if (dimWhitelist != null) {
            dimWhitelist.forEach(id -> {
                msg.add(id == null,
                        "Reservoir({}): Dim id: '{}' in dimWhitelist is Null", name, id);
            });
        }
    }


    public static void validateBiomeBlocklist(GroovyLog.Msg msg, String name, List<String> biomeBlacklist) {
        if (biomeBlacklist != null) {
            biomeBlacklist.forEach(string -> {
                msg.add(string == null || string.isEmpty(),
                        "Reservoir({}): String '{}' in biomeBlacklist is either Empty or Null", name, string);
            });

        }
    }

    public static void validateBiomeWhitelist(GroovyLog.Msg msg, String name, List<String> biomeWhitelist) {
        if (biomeWhitelist != null) {
            biomeWhitelist.forEach(string -> {
                msg.add(string == null || string.isEmpty(),
                        "Reservoir({}): String '{}' in biomeWhitelist is either Empty or Null", name, string);
            });
        }
    }

}
