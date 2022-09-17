package srki2k.tweakedpetroleum.util.errorloggingutil.startup;

import srki2k.tweakedpetroleum.api.ihelpers.IReservoirType;
import srki2k.tweakedpetroleum.util.errorloggingutil.ErrorLoggingUtil;

import java.util.ArrayList;
import java.util.List;

import static flaxbeard.immersivepetroleum.api.crafting.PumpjackHandler.reservoirList;
import static srki2k.tweakedpetroleum.api.crafting.TweakedPumpjackHandler.rftTier;
import static srki2k.tweakedpetroleum.common.Configs.TPConfig.StartupScriptChecks.*;

public abstract class StartupErrorLoggingUtil extends ErrorLoggingUtil {

    private StartupCTLogger startupCTLogger;

    public StartupCTLogger getStartupCTLogger() {
        if (startupCTLogger == null) {
            startupCTLogger = new StartupCTLogger();
        }
        return startupCTLogger;
    }

    protected final List<String> errors = new ArrayList<>();

    protected StartupErrorLoggingUtil() {
    }

    public void validateScripts() {
        if (missingContentCheck) {
            missingContentCheck();
        }

        if (missingPowerTierCheck) {
            missingPowerTierCheck();
        }

        if (!errors.isEmpty()) {
            logContentErrors(errors);
            customErrorImplementation();
        }
    }

    protected abstract void customErrorImplementation();

    private void missingContentCheck() {
        if (reservoirList.isEmpty()) {
            errors.add("No reservoirs are registered");
        }

        if (rftTier.size() == 1) {
            errors.add("No power tiers are registered");
        }

    }

    private void missingPowerTierCheck() {
        reservoirList.keySet().
                forEach(tweakedReservoirType -> {
                    if (rftTier.get(((IReservoirType) tweakedReservoirType).getPowerTier()) == null) {
                        errors.add("Reservoir with the ID (name)" + tweakedReservoirType.name + "has no valid Power tier");
                    }
                });
    }

}
