package io.github.srdjanv.tweakedpetroleum.mixin;

import blusunrize.immersiveengineering.api.MultiblockHandler;
import blusunrize.immersiveengineering.api.crafting.IMultiblockRecipe;
import blusunrize.immersiveengineering.common.blocks.metal.TileEntityMultiblockMetal;
import blusunrize.immersiveengineering.common.util.Utils;
import flaxbeard.immersivepetroleum.common.Config;
import flaxbeard.immersivepetroleum.common.blocks.metal.TileEntityPumpjack;
import io.github.srdjanv.tweakedlib.api.powertier.PowerTierHandler;
import io.github.srdjanv.tweakedpetroleum.api.crafting.TweakedPumpjackHandler;
import io.github.srdjanv.tweakedpetroleum.api.mixins.ITweakedPetPumpjackAddons;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.IFluidHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.concurrent.ThreadLocalRandom;

@Mixin(value = TileEntityPumpjack.class, remap = false)
public abstract class MixinTileEntityPumpjack extends TileEntityMultiblockMetal<TileEntityPumpjack, IMultiblockRecipe> implements ITweakedPetPumpjackAddons {

    //Shadow Variables
    @Shadow
    public boolean wasActive;
    @Shadow
    public float activeTicks;
    @Shadow
    private int pipeTicks;
    @Shadow
    private boolean lastHadPipes;
    @Shadow
    public IBlockState state;

    //Shadow Methods
    @Shadow
    public abstract boolean isDummy();

    @Shadow
    public abstract boolean canExtract();

    @Shadow
    protected abstract boolean hasPipes();

    @Shadow
    public abstract Fluid availableFluid();

    @Shadow
    public abstract int availableOil();

    @Shadow
    public abstract void extractOil(int amount);

    @Shadow
    public abstract TileEntityPumpjack master();

    public MixinTileEntityPumpjack(MultiblockHandler.IMultiblock mutliblockInstance, int[] structureDimensions, int energyCapacity, boolean redstoneControl) {
        super(mutliblockInstance, structureDimensions, energyCapacity, redstoneControl);
    }
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Unique
    private TweakedPumpjackHandler.ReservoirContent chunkContains = TweakedPumpjackHandler.ReservoirContent.DEFAULT;

    @Unique
    @Override
    public void initEnergyStorage() {
        PowerTierHandler.initFluxStorageWithPowerTier(energyStorage,
                TweakedPumpjackHandler.getPowerTier(
                        this.getWorld(), this.getPos().getX() >> 4, this.getPos().getZ() >> 4));
    }

    @Unique
    @Override
    public int[] getReplenishRateAndPumpSpeed() {
        return TweakedPumpjackHandler.getReplenishRateAndPumpSpeed(
                this.getWorld(), this.getPos().getX() >> 4, this.getPos().getZ() >> 4);

    }

    @Unique
    @Override
    public TweakedPumpjackHandler.ReservoirContent getChunkContains() {
        return TweakedPumpjackHandler.getReservoirContent(
                this.getWorld(), this.getPos().getX() >> 4, this.getPos().getZ() >> 4);

    }

    @Inject(method = "<init>", at = @At("RETURN"))
    private void onConstructed(CallbackInfo ci) {
        energyStorage.setCapacity(Integer.MAX_VALUE);
        energyStorage.setLimitTransfer(0);
        pipeTicks = ThreadLocalRandom.current().nextInt(90);
    }

    /**
     * @author Srki_2K
     * @reason Multiple small adjustment
     */
    @Overwrite
    public void update(boolean consumePower) {
        super.update();

        //Client side
        if (world.isRemote || isDummy()) {
            if (world.isRemote && !isDummy() && state != null && wasActive) {
                BlockPos particlePos = this.getPos().offset(facing, 4);

                float r1 = (world.rand.nextFloat() - .5F) * 2F;
                float r2 = (world.rand.nextFloat() - .5F) * 2F;

                world.spawnParticle(EnumParticleTypes.BLOCK_DUST, particlePos.getX() + 0.5F, particlePos.getY(),
                        particlePos.getZ() + 0.5F, r1 * 0.04F, 0.25F, r2 * 0.025F, Block.getStateId(state));
            }
            if (wasActive) {
                activeTicks++;
            }
            return;
        }

        //Tile initialization
        if (energyStorage.getMaxEnergyStored() == Integer.MAX_VALUE) {
            initEnergyStorage();
        }

        boolean active = false;
        int consumed = energyStorage.getLimitExtract();

        if (energyStorage.extractEnergy(consumed, true) >= consumed && canExtract() && !this.isRSDisabled()) {
            if (Config.IPConfig.Extraction.pipe_check_ticks == pipeTicks) lastHadPipes = hasPipes();
            if (lastHadPipes) {
                int[] replenishRateAndPumpSpeed = getReplenishRateAndPumpSpeed();
                int availableOil = availableOil();

                if (availableOil > 0 || replenishRateAndPumpSpeed[0] > 0) {
                    int oilAmnt = availableOil <= 0 ? replenishRateAndPumpSpeed[0] : availableOil;

                    if (chunkContains == TweakedPumpjackHandler.ReservoirContent.DEFAULT) {
                        chunkContains = getChunkContains();
                    }

                    switch (chunkContains) {
                        case LIQUID: {
                            active = caseLiquid(consumed, replenishRateAndPumpSpeed[1], oilAmnt);
                            break;
                        }
                        case GAS: {
                            active = caseGas(consumed, replenishRateAndPumpSpeed[1], oilAmnt);
                        }

                    }

                    activeTicks++;
                }
            }
            pipeTicks = (pipeTicks + 1) % (Config.IPConfig.Extraction.pipe_check_ticks + 1);
        }

        if (active != wasActive) {
            this.markDirty();
            this.markContainingBlockForUpdate(null);
        }

        wasActive = active;
    }

    @Unique
    @Override
    public boolean caseLiquid(int consumed, int pumpSpeed, int oilAmnt) {
        energyStorage.extractEnergy(consumed, false);
        FluidStack out = new FluidStack(availableFluid(), Math.min(pumpSpeed, oilAmnt));

        BlockPos outputPos = this.getPos().offset(facing, 2).offset(facing.rotateY().getOpposite(), 2).offset(EnumFacing.DOWN, 1);

        IFluidHandler output = FluidUtil.getFluidHandler(world, outputPos, facing.rotateY());
        if (output != null) {
            int accepted = output.fill(out, false);
            if (accepted > 0) {
                int drained = output.fill(Utils.copyFluidStackWithAmount(out, accepted, false), true);
                extractOil(drained);
                out = Utils.copyFluidStackWithAmount(out, out.amount - drained, false);
            }
        }

        outputPos = this.getPos().offset(facing, 2).offset(facing.rotateY(), 2).offset(EnumFacing.DOWN, 1);
        output = FluidUtil.getFluidHandler(world, outputPos, facing.rotateYCCW());
        if (output != null) {
            int accepted = output.fill(out, false);
            if (accepted > 0) {
                int drained = output.fill(Utils.copyFluidStackWithAmount(out, accepted, false), true);
                extractOil(drained);
            }
        }
        return true;
    }

    @Unique(silent = true)
    @Override
    public boolean caseGas(int consumed, int pumpSpeed, int oilAmnt) {
        return false;
    }

}

