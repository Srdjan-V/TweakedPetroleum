package io.github.srdjanv.tweakedpetroleum.api.mixins;

import io.github.srdjanv.tweakedpetroleum.api.crafting.TweakedPumpjackHandler;

public interface IReservoirTypeSetters {
    void setDimensionWhitelist(int[] dimWhitelist);

    void setDimensionBlacklist(int[] biomeBlacklistList);

    void setBiomeBlacklist(String[] dimBlacklist);

    void setBiomeWhitelist(String[] biomeWhitelistList);

    void setReservoirContent(TweakedPumpjackHandler.ReservoirContent reservoirContents);

    void setPowerTier(int i);

    void setPumpSpeed(int i);

    void setDrainChance(float f);
}
