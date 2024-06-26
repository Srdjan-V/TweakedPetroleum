package io.github.srdjanv.tweakedpetroleum.api.mixins;

import io.github.srdjanv.tweakedpetroleum.api.crafting.TweakedPumpjackHandler;

public interface ITweakedPetPumpjackAddons {

    void initEnergyStorage();

    int[] getReplenishRateAndPumpSpeed();

    TweakedPumpjackHandler.ReservoirContent getChunkContains();

    boolean caseLiquid(int consumed, int pumpSpeed, int oilAmnt);

    boolean caseGas(int consumed, int pumpSpeed, int oilAmnt);
}
