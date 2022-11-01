package srki2k.tweakedpetroleum.api.ihelpers;

import srki2k.tweakedpetroleum.api.crafting.TweakedPumpjackHandler;

public interface IReservoirGetters {

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


