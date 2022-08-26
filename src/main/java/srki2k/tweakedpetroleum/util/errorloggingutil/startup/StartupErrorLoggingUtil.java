package srki2k.tweakedpetroleum.util.errorloggingutil.startup;

import srki2k.tweakedpetroleum.TweakedPetroleum;
import srki2k.tweakedpetroleum.api.crafting.IReservoirType;
import srki2k.tweakedpetroleum.util.errorloggingutil.ErrorLoggingUtil;

import java.util.ArrayList;
import java.util.List;

import static flaxbeard.immersivepetroleum.api.crafting.PumpjackHandler.reservoirList;
import static srki2k.tweakedpetroleum.api.crafting.TweakedPumpjackHandler.rftTier;
import static srki2k.tweakedpetroleum.common.Configs.TPConfig.StartupScriptChecks.missingPowerTierCheck;
import static srki2k.tweakedpetroleum.common.Configs.TPConfig.StartupScriptChecks.scriptsErrorCheck;

public class StartupErrorLoggingUtil extends ErrorLoggingUtil {

    protected final List<String> errors = new ArrayList<>();

    public void addErrors(List<String> error) {
        errors.addAll(error);
    }

    public void validateScripts() {
        if (scriptsErrorCheck) {
            scriptsErrorCheck();
        }

        if (missingPowerTierCheck) {
            missingPowerTierCheck();
        }

        if (!errors.isEmpty()) {
            logSetting();
            logContentErrors(errors);
        }

    }


    protected void scriptsErrorCheck() {
        if (reservoirList.isEmpty()) {
            String error = "No reservoirs are registered";
            errors.add(error);
        }

        if (rftTier.isEmpty()) {
            String error = "No power tiers are registered";
            errors.add(error);
        }

    }

    protected void missingPowerTierCheck() {
        reservoirList.keySet().
                stream().
                map(reservoirType -> (IReservoirType) reservoirType).
                forEach(tweakedReservoirType -> {
                    if (rftTier.get(tweakedReservoirType.getPowerTier()) == null) {
                        String error = "Reservoir with the ID (name)" + tweakedReservoirType.getName() + "has no valid Power tier";
                        errors.add(error);
                        TweakedPetroleum.LOGGER.fatal(error);
                    }
                });
    }

}
