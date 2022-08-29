package srki2k.tweakedpetroleum.util.errorloggingutil.startup;

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
