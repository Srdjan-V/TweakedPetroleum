package srki2k.tweakedpetroleum.util.errorloggingutil.startup;

import crafttweaker.socket.SingleError;
import crafttweaker.zenscript.CrtStoringErrorLogger;
import crafttweaker.zenscript.GlobalRegistry;
import srki2k.tweakedpetroleum.api.crafting.IReservoirType;
import srki2k.tweakedpetroleum.util.errorloggingutil.ErrorLoggingUtil;

import java.util.ArrayList;
import java.util.List;

import static flaxbeard.immersivepetroleum.api.crafting.PumpjackHandler.reservoirList;
import static srki2k.tweakedpetroleum.api.crafting.TweakedPumpjackHandler.rftTier;
import static srki2k.tweakedpetroleum.common.Configs.TPConfig.StartupScriptChecks.missingPowerTierCheck;
import static srki2k.tweakedpetroleum.common.Configs.TPConfig.StartupScriptChecks.scriptsErrorCheck;

public abstract class StartupErrorLoggingUtil extends ErrorLoggingUtil {

    protected final List<String> errors = new ArrayList<>();

    public void addErrorToList(List<String> error) {
        errors.addAll(error);
    }

    public void addErrorToList(String error) {
        errors.add(error);
    }

    protected StartupErrorLoggingUtil() {
    }

    public void validateScripts() {
        if (!((CrtStoringErrorLogger) GlobalRegistry.getErrors()).getErrors().isEmpty()) {
            errors.add("=========== Everything below this is highlighting errors with zen script(after TP is loaded) ===========");
            for (SingleError e : ((CrtStoringErrorLogger) GlobalRegistry.getErrors()).getErrors()) {
                errors.add("fileName='" + e.fileName + '\'' + ", line=" + e.line + ", offset=" + e.offset + ", explanation='" + e.explanation + '\'' + ", level=" + e.level);
            }
            errors.add("====================== Everything below this line may or may not be a error ======================");
        }

        if (scriptsErrorCheck) {
            scriptsErrorCheck();
        }

        if (missingPowerTierCheck) {
            missingPowerTierCheck();
        }

        if (!errors.isEmpty()) {
            logContentErrors(errors);
            customErrorImplementation();
        }

        markStartupInstanceNull();
    }

    protected abstract void customErrorImplementation();

    private void scriptsErrorCheck() {
        if (reservoirList.isEmpty()) {
            errors.add("No reservoirs are registered");
        }

        if (rftTier.size() == 1) {
            errors.add("No power tiers are registered");
        }

    }

    private void missingPowerTierCheck() {
        reservoirList.keySet().
                stream().
                map(reservoirType -> (IReservoirType) reservoirType).
                forEach(tweakedReservoirType -> {
                    if (rftTier.get(tweakedReservoirType.getPowerTier()) == null) {
                        errors.add("Reservoir with the ID (name)" + tweakedReservoirType.getName() + "has no valid Power tier");
                    }
                });
    }

}
