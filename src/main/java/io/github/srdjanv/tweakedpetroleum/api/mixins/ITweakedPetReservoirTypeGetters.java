package io.github.srdjanv.tweakedpetroleum.api.mixins;

import io.github.srdjanv.tweakedpetroleum.api.crafting.TweakedPumpjackHandler;

public interface ITweakedPetReservoirTypeGetters {
    String getName();

    String getStringFluid();

    int getMinSize();

    int getMaxSize();

    int getReplenishRate();

    int getPowerTier();

    int getPumpSpeed();

    float getDrainChance();

    int[] getDimensionWhitelist();

    int[] getDimensionBlacklist();

    String[] getBiomeWhitelist();

    String[] getBiomeBlacklist();

    TweakedPumpjackHandler.ReservoirContent getReservoirContent();
}
