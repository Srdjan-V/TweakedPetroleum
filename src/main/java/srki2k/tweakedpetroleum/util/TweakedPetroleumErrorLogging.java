package srki2k.tweakedpetroleum.util;

import srki2k.tweakedlib.api.logging.errorlogginglib.ErrorLoggingLib;
import srki2k.tweakedlib.api.logging.errorlogginglib.ICustomLogger;
import srki2k.tweakedlib.api.powertier.PowerTierHandler;
import srki2k.tweakedpetroleum.TweakedPetroleum;
import srki2k.tweakedpetroleum.api.ihelpers.IReservoirType;

import java.util.ArrayList;
import java.util.List;

import static flaxbeard.immersivepetroleum.api.crafting.PumpjackHandler.reservoirList;
import static srki2k.tweakedpetroleum.common.Configs.TPConfig.DefaultReservoirs.defaultPowerTier;
import static srki2k.tweakedpetroleum.common.Configs.TPConfig.Logging.logMissingPowerTier;
import static srki2k.tweakedpetroleum.common.Configs.TPConfig.Logging.logToPlayers;
import static srki2k.tweakedpetroleum.common.Configs.TPConfig.defaultReservoirs;

public final class TweakedPetroleumErrorLogging implements ICustomLogger {

    private static ICustomLogger customLogger;

    public static void register() {
        if (customLogger == null) {
            customLogger = new TweakedPetroleumErrorLogging();
            ErrorLoggingLib.addCustomLogger(customLogger);
        }
    }

    private TweakedPetroleumErrorLogging() {
    }

    List<String> errors = new ArrayList<>();

    @Override
    public boolean doCustomCheck() {
        if (logMissingPowerTier) {
            missingPowerTiers();
            return true;
        }

        return false;
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
    public String getMODID() {
        return TweakedPetroleum.MODID;
    }

    @Override
    public String[] getConfigs() {
        String[] strings = new String[7];

        strings[0] = "Logging:";
        strings[1] = "Log missing reservoirs to players: " + logToPlayers;
        strings[2] = "Log Missing PowerTiers on startup: " + logMissingPowerTier;

        strings[3] = "DefaultReservoirs:";
        strings[4] = "Register Reservoirs: " + defaultReservoirs;
        strings[6] = "Default Reservoirs PowerTier: " + defaultPowerTier;

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
