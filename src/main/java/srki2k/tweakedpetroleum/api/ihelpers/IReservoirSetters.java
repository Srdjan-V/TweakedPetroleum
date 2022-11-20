package srki2k.tweakedpetroleum.api.ihelpers;

import net.minecraftforge.fluids.Fluid;
import srki2k.tweakedpetroleum.api.crafting.TweakedPumpjackHandler;

public interface IReservoirSetters {

    void setName(String name);

    void setStringFluid(String fluid);

    void setFluid(Fluid fluid);

    void setMinSize(int minSize);

    void setMaxSize(int maxSize);

    void setReplenishRate(int replenishRate);

    void setPowerTier(int powerTier);

    void setPumpSpeed(int pumpSpeed);

    void setDrainChance(float drainChance);

    void setDimensionWhitelist(int[] dimWhitelist);

    void setDimensionBlacklist(int[] biomeBlacklistList);

    void setBiomeBlacklist(String[] dimBlacklist);

    void setBiomeWhitelist(String[] biomeWhitelistList);

    void setReservoirContent(TweakedPumpjackHandler.ReservoirContent reservoirContents);

}


