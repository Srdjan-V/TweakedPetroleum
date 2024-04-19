package io.github.srdjanv.tweakedpetroleum.common.compat.top;

import blusunrize.immersiveengineering.api.energy.immersiveflux.IFluxReceiver;
import flaxbeard.immersivepetroleum.api.crafting.PumpjackHandler;
import flaxbeard.immersivepetroleum.common.Config;
import flaxbeard.immersivepetroleum.common.blocks.metal.TileEntityPumpjack;
import io.github.srdjanv.tweakedlib.api.hei.BaseHEIUtil;
import io.github.srdjanv.tweakedlib.api.powertier.PowerTierHandler;
import io.github.srdjanv.tweakedlib.api.top.IETopOverwriteManager;
import io.github.srdjanv.tweakedlib.common.Constants;
import io.github.srdjanv.tweakedpetroleum.api.mixins.ITweakedPetReservoirType;
import io.github.srdjanv.tweakedpetroleum.util.TweakedPetroleumInitializer;
import mcjty.theoneprobe.api.*;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import static mcjty.theoneprobe.api.IProbeInfo.ENDLOC;
import static mcjty.theoneprobe.api.IProbeInfo.STARTLOC;

public final class TopCompat implements TweakedPetroleumInitializer {
    @Override public boolean shouldRun() {
        return Constants.isTheOneProbeLoaded();
    }

    @Override public void preInit(FMLPreInitializationEvent event) {
        IETopOverwriteManager manager = IETopOverwriteManager.getInstance();
        manager.registerEnergyInfoOverwrite(TileEntityPumpjack.class, TopCompat::addProbeInfo);
    }

    private static void addProbeInfo(TileEntity te, ProbeMode mode, IProbeInfo probeInfo, EntityPlayer player, World world, IBlockState blockState, IProbeHitData data) {
        TileEntityPumpjack master = ((TileEntityPumpjack) te).master();
        if (master == null) return;
        var info = PumpjackHandler.getOilWorldInfo(master.getWorld(), master.getPos().getX() >> 4, master.getPos().getZ() >> 4);
        if (info == null) {
            probeInfo.text(STARTLOC + "tweakedpetroleum.hud.reservoir.empty" + ENDLOC);
            return;
        }

        if (master.wasActive && (info.current == info.capacity)) {
            probeInfo.text(TextStyleClass.INFO + STARTLOC + "tweakedpetroleum.hud.reservoir.unknown" + ENDLOC);
        } else if (!master.wasActive) {
            if (Config.IPConfig.Extraction.req_pipes) {
                probeInfo.text(TextStyleClass.ERROR + STARTLOC + "tweakedpetroleum.hud.reservoir.req_pipes" + ENDLOC);
            } else probeInfo.text(TextStyleClass.ERROR + STARTLOC + "tweakedpetroleum.hud.reservoir.req_power" + ENDLOC);
        }

        ITweakedPetReservoirType reservoir = (ITweakedPetReservoirType) info.getType();
        if (reservoir == null) return;
        if (master.wasActive && (info.current != info.capacity)) {
            probeInfo.text(TextStyleClass.INFO + STARTLOC + "tweakedpetroleum.hud.reservoir.name" + ENDLOC + " " + reservoir.getName());
            probeInfo.text(" " + TextStyleClass.INFO + STARTLOC + "tweakedpetroleum.jei.reservoir.replenishRate" + ENDLOC + " " + reservoir.getReplenishRate() + " mb/t");
            probeInfo.text(" " + TextStyleClass.INFO + STARTLOC + "tweakedpetroleum.jei.reservoir.speed" + ENDLOC + " " + reservoir.getPumpSpeed() + " mb/t");

            probeInfo.progress(info.current, info.capacity, probeInfo.defaultProgressStyle().suffix(" mb")
                    .filledColor(0xff0000ff). alternateFilledColor(0xff000099).borderColor(0xff3333ff).numberFormat(NumberFormat.COMMAS));

            //   probeInfo.text(" " + TextStyleClass.INFO + STARTLOC + "tweakedpetroleum.hud.current_level" + ENDLOC + " " + BaseHEIUtil.numberFormat.format(info.current) +"mb");
        }

        var powerTier = PowerTierHandler.getPowerTier(reservoir.getPowerTier());
        probeInfo.text(TextStyleClass.INFO + STARTLOC + "tweakedlib.jei.power_tier" + ENDLOC + " " +
                BaseHEIUtil.numberFormat.format(PowerTierHandler.getTierOfSpecifiedPowerTier(reservoir.getPowerTier())));
        probeInfo.text(TextStyleClass.INFO + STARTLOC + "tweakedlib.jei.power_usage" + ENDLOC + " " +
                BaseHEIUtil.numberFormat.format(powerTier.getRft()) + " IF/t");
        probeInfo.progress(((IFluxReceiver) te).getEnergyStored(null), powerTier.getCapacity(),
                probeInfo.defaultProgressStyle().suffix(" IF").filledColor(-557004).alternateFilledColor(-6729952).borderColor(-12705779).numberFormat(NumberFormat.COMMAS));
    }
}
