import mods.TweakedPetroleum.TweakedReservoir;

/*
 By default TweakedLib is registering a powerTier(default id: 0) if it detects that IP is loaded(and if its configuration is enabled see the configs for TweakedLib)
TweakedPowerTier.registerPowerTier(int tier, int capacity, int rft)
*/

    TweakedPowerTier.registerPowerTier(0, 16000, 1024);

/*
TweakedReservoir.registerReservoirWithDrainChance(String name, ILiquidStack fluid, int minSize, int maxSize, int replenishRate, int pumpSpeed, float drainChance, int weight, int powerTier,
    int[] dimBlacklist, int[] dimWhitelist, String[] biomeBlacklist, String[] biomeWhitelist)
*/

    TweakedReservoir.registerReservoirWithDrainChance("oil123123", <liquid:oil>, 2500000, 15000000, 6, 25, 0.5, 40, 0,
        [], [0], [], []);