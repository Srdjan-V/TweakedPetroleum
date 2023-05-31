package io.github.srdjanv.tweakedpetroleum.common;

import flaxbeard.immersivepetroleum.api.crafting.PumpjackHandler;
import io.github.srdjanv.tweakedlib.api.powertier.PowerTierHandler;
import io.github.srdjanv.tweakedpetroleum.api.crafting.TweakedPumpjackHandler;
import io.github.srdjanv.tweakedpetroleum.api.mixins.IReservoirType;

import static flaxbeard.immersivepetroleum.api.crafting.PumpjackHandler.reservoirList;

public class DefaultReservoirs {

    public static void init() {
        if (Configs.TPConfig.DefaultReservoirs.defaultReservoirs) {
            int powerTier = PowerTierHandler.registerPowerTier(
                            Configs.TPConfig.DefaultReservoirs.DefaultPumpjackPowerTiers.capacity,
                            Configs.TPConfig.DefaultReservoirs.DefaultPumpjackPowerTiers.rft);

            makeReservoirType("aquifer", "water", 5000000, 10000000, 6, 25, 30, powerTier, new int[]{}, new int[]{0});
            makeReservoirType("oil", "oil", 2500000, 15000000, 6, 25, 40, powerTier, new int[]{1}, new int[]{});
            makeReservoirType("lava", "lava", 250000, 1000000, 0, 25, 30, powerTier, new int[]{1}, new int[]{});

        }

    }

    private static void makeReservoirType(String name, String fluid, int minSize, int maxSize, int replenishRate, int pumpSpeed, int weight, int powerTier, int[] dimBlacklist, int[] dimWhitelist) {
        PumpjackHandler.ReservoirType mix = new PumpjackHandler.ReservoirType(name, fluid, minSize, maxSize, replenishRate);

        if (reservoirList.containsKey(mix)) {
            return;
        }

        reservoirList.put(mix, weight);

        IReservoirType iMix = (IReservoirType) mix;
        iMix.setReservoirContent(TweakedPumpjackHandler.ReservoirContent.LIQUID);
        iMix.setPumpSpeed(pumpSpeed);
        iMix.setPowerTier(powerTier);
        iMix.setDimensionBlacklist(dimBlacklist);
        iMix.setDimensionWhitelist(dimWhitelist);

    }

}
