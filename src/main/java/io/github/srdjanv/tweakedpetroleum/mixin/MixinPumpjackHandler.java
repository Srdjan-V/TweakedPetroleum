package io.github.srdjanv.tweakedpetroleum.mixin;

import flaxbeard.immersivepetroleum.api.crafting.PumpjackHandler;
import flaxbeard.immersivepetroleum.common.IPSaveData;
import io.github.srdjanv.tweakedpetroleum.api.mixins.ITweakedPetReservoirType;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import static flaxbeard.immersivepetroleum.api.crafting.PumpjackHandler.getOilWorldInfo;


@Mixin(value = PumpjackHandler.class, remap = false)
public class MixinPumpjackHandler {

    /**
     * @author Srki_2k
     * @reason Option to extend the capacity of the reservoir
     */
    @Overwrite
    public static void depleteFluid(World world, int chunkX, int chunkZ, int amount) {
        PumpjackHandler.OilWorldInfo info = getOilWorldInfo(world, chunkX, chunkZ);
        if (info.current == 0) {
            return;
        }
        if (Math.random() < ((ITweakedPetReservoirType) info.getType()).getDrainChance()) {
            info.current = Math.max(0, info.current - amount);
            IPSaveData.setDirty(world.provider.getDimension());
        }
    }

}
