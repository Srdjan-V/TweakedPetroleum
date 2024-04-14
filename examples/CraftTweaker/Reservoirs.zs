import mods.TweakedPetroleum.TweakedReservoir;
import mods.TweakedLib.TweakedPowerTier;

/*
    By default TweakedPetroleum is registering all of the default Reservoirs(aquifer, oil, lava), but registering them again through CT will overwrite them.
    This can be disabled in the TweakedPetroleum configs, and you should if you plan to use CT with it.

    This is the mane way of registering a reservoir, the name should be unique.
    TweakedReservoir.registerReservoir(String name, ILiquidStack fluid, int minSize, int maxSize, int replenishRate, int pumpSpeed, int weight, int powerTier,
                                        @Optional int[] dimBlacklist, @Optional int[] dimWhitelist, @Optional String[] biomeBlacklist, @Optional String[] biomeWhitelist)

    This is the way or registering a powerTier. The returned value will be a unique int value corresponding the the capacity and rft of the reservoir
    TweakedPowerTier.registerPowerTier(int capacity, int rft)
*/

    var powerTier = TweakedPowerTier.registerPowerTier(16000, 1024);

    // Vanilla Reservoirs
    TweakedReservoir.registerReservoir("aquifer", <liquid:water>, 5000000, 10000000, 6, 25, 30, powerTier,
        [], [0]);

    TweakedReservoir.registerReservoir("oil", <liquid:oil>, 2500000, 15000000, 6, 25, 40, powerTier,
        [1], [], []);

    TweakedReservoir.registerReservoir("lava", <liquid:lava>, 250000, 1000000, 0, 25, 30, powerTier,
        [1]);

/*
    This method will register a reservoir with the chance to drain fluid from the chunk.
    If drainChance is set to 0.25 it will have a 25% chance to drain from the chunk
    If drainChance is set to 0.5 it will have a 50% chance to drain from the chunk
    If drainChance is set to 1 it will have a 100% chance to drain from the chunk, its the same as registering it normally

    TweakedReservoir.registerReservoirWithDrainChance(String name, ILiquidStack fluid, int minSize, int maxSize, int replenishRate, int pumpSpeed, float drainChance, int weight, int powerTier,
                                                    @Optional int[] dimBlacklist, @Optional int[] dimWhitelist, @Optional String[] biomeBlacklist, @Optional String[] biomeWhitelist) {

*/
    var powerTier2 = TweakedPowerTier.registerPowerTier(160000, 10240);

    TweakedReservoir.registerReservoirWithDrainChance("drainChanceOil", <liquid:oil>, 2500000, 15000000, 6, 25, 0.5, 40, powerTier2,
        [], [0], [], []);