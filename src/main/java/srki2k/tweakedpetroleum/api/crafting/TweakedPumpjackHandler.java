package srki2k.tweakedpetroleum.api.crafting;

import blusunrize.immersiveengineering.api.DimensionChunkCoords;
import flaxbeard.immersivepetroleum.api.crafting.PumpjackHandler;
import net.minecraft.world.World;
import srki2k.tweakedpetroleum.util.ErrorLoggingUtil;

import java.util.HashMap;

import static flaxbeard.immersivepetroleum.api.crafting.PumpjackHandler.*;

public class TweakedPumpjackHandler {
    private static final int depositSize = 1;

    public static final HashMap<Integer, PowerTier> rftTier = new HashMap<>();


    /**
     * Sets the PowerTier object associated with the fluid of a given chunk
     *
     * @param tier      The tier of the power, must start from 0
     * @param capacity  The capacity
     * @param rft       The RF/t
     */
    public static void registerPowerUsage(int tier, int capacity, int rft) {
        rftTier.put(tier, new PowerTier(capacity, rft));
    }

    /**
     * Gets the PowerTier object associated with the fluid of a given chunk
     *
     * @param world      World whose chunk to drain
     * @param chunkX     Chunk x
     * @param chunkZ     Chunk z
     * @return Returns PowerTier
     */
    public static PowerTier getPowerTier(World world, int chunkX, int chunkZ) {
        PumpjackHandler.OilWorldInfo info = getOilWorldInfo(world, chunkX, chunkZ);

        if (info == null || info.getType() == null) {
            PowerTier powerTier =rftTier.get(0);
            if (powerTier == null) {
                ErrorLoggingUtil.Runtime.missingPowerTiersLog();
            }
            return powerTier;
        }

        IReservoirType tweakedReservoirType = (IReservoirType) info.getType();

        if (rftTier.get(tweakedReservoirType.getPowerTier()) == null){
            ErrorLoggingUtil.Runtime.missingPowerTiersLog();
        }

        return rftTier.get(tweakedReservoirType.getPowerTier());
    }


    /**
     * Adds a tweaked reservoir type to the pool of valid reservoirs
     *
     * @param name          The name of the reservoir type
     * @param fluid         The String fluid of the fluid for this reservoir
     * @param minSize       The minimum reservoir size, in mB
     * @param maxSize       The maximum reservoir size, in mB
     * @param replenishRate The rate at which fluid can be drained from this reservoir when empty, in mB/tick
     * @param pumpSpeed     The rate at with fluid will be pumped, in mB/tick
     * @param weight        The weight for this reservoir to spawn
     * @param powerTier     The tier of power usage
     * @return The created TweakedReservoirType
     */
    public static IReservoirType addTweakedReservoir(String name, String fluid, int minSize, int maxSize, int replenishRate, int pumpSpeed, int weight, int powerTier) {
        ReservoirType mix = new ReservoirType(name, fluid, minSize, maxSize, replenishRate);
        reservoirList.put(mix, weight);

        IReservoirType iMix =(IReservoirType) mix;
        iMix.setPumpSpeed(pumpSpeed);
        iMix.setPowerTier(powerTier);

        return iMix;
    }


    /**
     * Gets the replenish rate and pump speed from a given chunk
     *
     * @param world  World whose chunk to drain
     * @param chunkX Chunk x
     * @param chunkZ Chunk z
     * @return Returns int[0] as replenishRate int [1] as pumpSpeed from the given chunk
     */
    public static int[] getReplenishRateAndPumpSpeed(World world, int chunkX, int chunkZ) {
        PumpjackHandler.OilWorldInfo info = getOilWorldInfo(world, chunkX, chunkZ);
        int[] replenishRateAndPumpSpeed = new int[2];

        if (info == null || info.getType() == null || info.getType().fluid == null || (info.capacity == 0) || (info.current == 0))
            return replenishRateAndPumpSpeed;

        IReservoirType tweakedReservoirType = (IReservoirType) info.getType();

        if (tweakedReservoirType.getPumpSpeed() == 0)
            return replenishRateAndPumpSpeed;

        DimensionChunkCoords coords = new DimensionChunkCoords(world.provider.getDimension(), chunkX / depositSize, chunkZ / depositSize);

        Long l = timeCache.get(coords);

        if (l == null) {
            timeCache.put(coords, world.getTotalWorldTime());
            replenishRateAndPumpSpeed[0] = tweakedReservoirType.getReplenishRate();
            replenishRateAndPumpSpeed[1] = tweakedReservoirType.getPumpSpeed();

            return replenishRateAndPumpSpeed;
        }

        long lastTime = world.getTotalWorldTime();
        timeCache.put(coords, world.getTotalWorldTime());

        if (lastTime != l) {
            replenishRateAndPumpSpeed[0] = tweakedReservoirType.getReplenishRate();
            replenishRateAndPumpSpeed[1] = tweakedReservoirType.getPumpSpeed();

            return replenishRateAndPumpSpeed;
        }

        return replenishRateAndPumpSpeed;
    }

    public static class PowerTier {
        public int capacity;
        public int rft;

        public PowerTier(int capacity, int rft) {
            this.capacity = capacity;
            this.rft = rft;
        }
    }

}