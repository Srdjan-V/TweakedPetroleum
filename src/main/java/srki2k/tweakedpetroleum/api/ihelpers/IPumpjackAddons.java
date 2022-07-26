package srki2k.tweakedpetroleum.api.ihelpers;

import srki2k.tweakedpetroleum.api.crafting.TweakedPumpjackHandler;

public interface IPumpjackAddons {

    void initEnergyStorage();

    int[] getReplenishRateAndPumpSpeed();

    TweakedPumpjackHandler.ReservoirContent getChunkContains();

    boolean caseLiquid(int consumed, int pumpSpeed, int oilAmnt);

    boolean caseGas(int consumed, int pumpSpeed, int oilAmnt);
}
