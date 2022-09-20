package srki2k.tweakedpetroleum.util;

import srki2k.tweakedlib.api.powertier.PowerTierHandler;
import srki2k.tweakedlib.util.errorlogging.ICustomLogger;
import srki2k.tweakedpetroleum.TweakedPetroleum;
import srki2k.tweakedpetroleum.api.ihelpers.IReservoirType;

import java.util.ArrayList;
import java.util.List;

import static flaxbeard.immersivepetroleum.api.crafting.PumpjackHandler.reservoirList;
import static srki2k.tweakedpetroleum.common.Configs.TPConfig.ScriptChecks.*;

public class ErrorLogging implements ICustomLogger {

    List<String> errors = new ArrayList<>();

    @Override
    public boolean doCustomCheck() {
        boolean mark = false;

        if (disableLogging) {
            return false;
        }

        if (logMissingContent) {
            if (reservoirList.isEmpty()) {
                errors.add("No reservoirs are registered");
                mark = true;
            }

        }

        if (logMissingPowerTier) {
            missingPowerTiers();
            mark = true;
        }

        return mark;
    }

    @Override
    public boolean handleRuntimeErrors() {
        //missingPowerTiersLog()
        missingPowerTiers();
        return !errors.isEmpty();
    }

    @Override
    public boolean discardLoggerAfterStartup() {
        return false;
    }

    @Override
    public boolean logErrorToUsersInGameWithCT() {
        return logToPlayers;
    }

    private void missingPowerTiers() {
        reservoirList.keySet().
                forEach(tweakedReservoirType -> {
                    if (PowerTierHandler.getPowerTier(((IReservoirType) tweakedReservoirType).getPowerTier()) == null) {
                        errors.add("Reservoir with the ID (name)" + tweakedReservoirType.name + "has no valid Power tier");
                    }
                });
    }


    @Override
    public String modid() {
        return TweakedPetroleum.MODID;
    }

    @Override
    public String[] getConfigs() {
        String[] strings = new String[4];

        strings[0] = "Disable all checks: " + disableLogging;
        strings[1] = "Log missing reservoirs: " + logMissingContent;
        strings[2] = "Log missing reservoirs to players: " + logToPlayers;
        strings[3] = "Log Missing PowerTiers for on startup: " + logMissingPowerTier;

        return strings;
    }

    @Override
    public List<String> getErrors() {
        return errors;
    }

    @Override
    public void clean() {
        errors.clear();
    }
}
