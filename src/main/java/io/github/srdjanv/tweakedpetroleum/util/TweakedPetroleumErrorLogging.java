package io.github.srdjanv.tweakedpetroleum.util;

import io.github.srdjanv.tweakedpetroleum.TweakedPetroleum;
import io.github.srdjanv.tweakedpetroleum.common.Configs;
import org.apache.logging.log4j.Logger;
import io.github.srdjanv.tweakedlib.api.logging.errorlogginglib.ICustomLogger;
import io.github.srdjanv.tweakedlib.api.powertier.PowerTierHandler;
import io.github.srdjanv.tweakedpetroleum.api.mixins.ITweakedPetReservoirType;

import java.util.ArrayList;
import java.util.List;

import static flaxbeard.immersivepetroleum.api.crafting.PumpjackHandler.reservoirList;

public final class TweakedPetroleumErrorLogging implements ICustomLogger {
    private TweakedPetroleumErrorLogging() {
    }

    private final List<String> errors = new ArrayList<>();

    @Override
    public boolean startupChecks() {
        return reservoirList.isEmpty();
    }

    @Override
    public boolean runtimeChecks() {
        reservoirList.keySet().
                forEach(tweakedReservoirType -> {
                    if (!PowerTierHandler.powerTierExists(((ITweakedPetReservoirType) tweakedReservoirType).getPowerTier())) {
                        errors.add("Reservoir with the ID (name) " + tweakedReservoirType.name + "has no valid Power tier");
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
        strings[1] = "Register Reservoirs: " + Configs.TPConfig.DefaultReservoirs.enableIPConfigReservoirRegistration;
        strings[2] = "Default Pumpjack Power Tiers:";
        strings[3] = "Default capacity:" + Configs.TPConfig.DefaultReservoirs.DefaultPumpjackPowerTiers.capacity;
        strings[4] = "Default consumption:" + Configs.TPConfig.DefaultReservoirs.DefaultPumpjackPowerTiers.rft;

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
