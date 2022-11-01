package srki2k.tweakedpetroleum.api.ihelpers;

import srki2k.tweakedpetroleum.api.crafting.TweakedPumpjackHandler;

public interface IReservoirSetters {

    void setDimensionWhitelist(int[] dimWhitelist);

    void setDimensionBlacklist(int[] biomeBlacklistList);

    void setBiomeBlacklist(String[] dimBlacklist);

    void setBiomeWhitelist(String[] biomeWhitelistList);

    void setReservoirContent(TweakedPumpjackHandler.ReservoirContent reservoirContents);

    void setPowerTier(int i);

    void setPumpSpeed(int i);

    void setDrainChance(float f);

}


