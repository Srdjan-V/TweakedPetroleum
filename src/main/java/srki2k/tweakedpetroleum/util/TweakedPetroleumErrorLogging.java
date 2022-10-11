package srki2k.tweakedpetroleum.util;

import org.apache.logging.log4j.Logger;
import srki2k.tweakedlib.api.logging.errorlogginglib.ErrorLoggingLib;
import srki2k.tweakedlib.api.logging.errorlogginglib.ICustomLogger;
import srki2k.tweakedlib.api.powertier.PowerTierHandler;
import srki2k.tweakedpetroleum.TweakedPetroleum;
import srki2k.tweakedpetroleum.api.ihelpers.IReservoirType;

import java.util.ArrayList;
import java.util.List;

import static flaxbeard.immersivepetroleum.api.crafting.PumpjackHandler.reservoirList;
import static srki2k.tweakedpetroleum.common.Configs.TPConfig.DefaultReservoirs.DefaultPumpjackPowerTiers.capacity;
import static srki2k.tweakedpetroleum.common.Configs.TPConfig.DefaultReservoirs.DefaultPumpjackPowerTiers.rft;
import static srki2k.tweakedpetroleum.common.Configs.TPConfig.DefaultReservoirs.defaultReservoirs;

public final class TweakedPetroleumErrorLogging implements ICustomLogger {
    private TweakedPetroleumErrorLogging() {
    }

    public static void register() {
        ErrorLoggingLib.addCustomLogger(new TweakedPetroleumErrorLogging());
    }

    List<String> errors = new ArrayList<>();

    @Override
    public boolean startupChecks() {
        return reservoirList.isEmpty();
    }

    @Override
    public boolean runtimeChecks() {
        reservoirList.keySet().
                forEach(tweakedReservoirType -> {
                    if (!PowerTierHandler.powerTierExists(((IReservoirType) tweakedReservoirType).getPowerTier())) {
                        errors.add("Reservoir with the ID (name)" + tweakedReservoirType.name + "has no valid Power tier");
                    }
                });

        return !errors.isEmpty();
    }

    @Override
    public boolean discardLoggerAfterStartup() {
        return false;
    }

    @Override
    public Logger getModLogger() {
        return TweakedPetroleum.LOGGER;
    }

    @Override
    public String[] getConfigs() {
        String[] strings = new String[5];

        strings[0] = "DefaultReservoirs:";
        strings[1] = "Register Reservoirs: " + defaultReservoirs;
        strings[2] = "Default Pumpjack Power Tiers:";
        strings[3] = "Default capacity:" + capacity;
        strings[4] = "Default consumption:" + rft;

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
