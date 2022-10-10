import mods.TweakedPetroleum.TweakedReservoir;
import mods.TweakedLib.TweakedPowerTier;

/*
By default TweakedLib is registering a powerTier(default id: 0) if it detects that IP is loaded(and if its configuration is enabled see the configs for TweakedLib)
TweakedPowerTier.registerPowerTier(int tier, int capacity, int rft)
*/

    // Power usage
    TweakedPowerTier.registerPowerTier(0, 16000, 1024);

/*
By default TweakedPetroleum is registering all of the default Reservoirs(aquifer, oil, lava), but registering them again through CT will overwrite them.
This can be disabled in the TweakedPetroleum configs, and you should if you plan to use CT with it.

TweakedReservoir.registerReservoir(String name, ILiquidStack fluid, int minSize, int maxSize, int replenishRate, int pumpSpeed, int weight, int powerTier,
    int[] dimBlacklist, int[] dimWhitelist, String[] biomeBlacklist, String[] biomeWhitelist)
*/

    // Vanilla Reservoirs
    TweakedReservoir.registerReservoir("aquifer", <liquid:water>, 5000000, 10000000, 6, 25, 30, 0,
        [], [0], [], []);

    TweakedReservoir.registerReservoir("oil", <liquid:oil>, 2500000, 15000000, 6, 25, 40, 0,
        [1], [], [], []);

    TweakedReservoir.registerReservoir("lava", <liquid:lava>, 250000, 1000000, 0, 25, 30, 0,
        [1], [], [], []);
