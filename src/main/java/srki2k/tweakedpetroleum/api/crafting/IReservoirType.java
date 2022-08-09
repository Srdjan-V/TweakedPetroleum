package srki2k.tweakedpetroleum.api.crafting;

public interface IReservoirType {

    String getName();

    int getMinSize();

    int getMaxSize();

    int getReplenishRate();

    int getPowerTier();

    int getPumpSpeed();

    int[] getDimensionWhitelist();

    int[] getDimensionBlacklist();

    String[] getBiomeWhitelist();

    String[] getBiomeBlacklist();



    void setDimensionWhitelist(int[] dimWhitelist);

    void setDimensionBlacklist(int[] biomeBlacklistList);

    void setBiomeBlacklist(String[] dimBlacklist);

    void setBiomeWhitelist(String[] biomeWhitelistList);

    void setPowerTier(int i);

    void setPumpSpeed(int i);

}


