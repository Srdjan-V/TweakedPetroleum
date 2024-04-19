package io.github.srdjanv.tweakedpetroleum.common;

import flaxbeard.immersivepetroleum.api.crafting.PumpjackHandler;
import io.github.srdjanv.tweakedlib.api.powertier.PowerTierHandler;
import io.github.srdjanv.tweakedpetroleum.api.crafting.TweakedPumpjackHandler;
import io.github.srdjanv.tweakedpetroleum.api.mixins.ITweakedPetReservoirType;
import io.github.srdjanv.tweakedpetroleum.util.TweakedPetroleumInitializer;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.util.Objects;

import static flaxbeard.immersivepetroleum.api.crafting.PumpjackHandler.reservoirList;

public class DefaultReservoirs implements TweakedPetroleumInitializer {
    private static DefaultReservoirs instance;

    public static DefaultReservoirs getInstance() {
        return Objects.requireNonNull(instance);
    }

    public DefaultReservoirs() {
        instance = this;
    }

    @Override public boolean shouldRun() {
        return Configs.TPConfig.DefaultReservoirs.enableIPConfigReservoirRegistration;
    }

    private int powerTier;

    @Override public void preInit(FMLPreInitializationEvent event) {
        powerTier = PowerTierHandler.registerPowerTier(
                Configs.TPConfig.DefaultReservoirs.DefaultPumpjackPowerTiers.capacity,
                Configs.TPConfig.DefaultReservoirs.DefaultPumpjackPowerTiers.rft);
    }

    public int defaultPowerTier() {
        return powerTier;
    }

    public int defaultPumpSpeed() {
        return Configs.TPConfig.DefaultReservoirs.DefaultPumpjackPowerTiers.pump_speed;
    }
}
