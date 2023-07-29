package io.github.srdjanv.tweakedpetroleum.common.compat.top;

import blusunrize.immersiveengineering.api.energy.immersiveflux.IFluxReceiver;
import flaxbeard.immersivepetroleum.api.crafting.PumpjackHandler;
import flaxbeard.immersivepetroleum.common.Config;
import flaxbeard.immersivepetroleum.common.blocks.metal.TileEntityPumpjack;
import io.github.srdjanv.tweakedlib.api.hei.BaseHEIUtil;
import io.github.srdjanv.tweakedlib.api.powertier.PowerTierHandler;
import io.github.srdjanv.tweakedlib.api.top.TopOverwriteManager;
import io.github.srdjanv.tweakedpetroleum.api.mixins.IReservoirType;
import mcjty.theoneprobe.api.IProbeHitData;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.ProbeMode;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Loader;

import static io.github.srdjanv.tweakedlib.api.hei.BaseHEIUtil.translateToLocalFormatted;

public final class TopCompat {
    private TopCompat() {
    }

    public static void init() {
        if (!Loader.isModLoaded("theoneprobe")) return;

        TopOverwriteManager manager = TopOverwriteManager.getInstance();
        manager.registerOverwrite(TileEntityPumpjack.class, TopCompat::addProbeInfo);
    }

    private static void addProbeInfo(TileEntity te, ProbeMode mode, IProbeInfo probeInfo, EntityPlayer player, World world, IBlockState blockState, IProbeHitData data) {
        TileEntityPumpjack master = ((TileEntityPumpjack) te).master();
        if (master == null) {
            return;
        }
        var info = PumpjackHandler.getOilWorldInfo(master.getWorld(), master.getPos().getX() >> 4, master.getPos().getZ() >> 4);
        if (info == null) {
            probeInfo.text(translateToLocalFormatted("tweakedpetroleum.hud.empty"));
            return;
        }

        if (info.capacity == info.current) {
            if (Config.IPConfig.Extraction.req_pipes) {
                probeInfo.text(translateToLocalFormatted("tweakedpetroleum.jei.reservoir.req_pipes"));
                return;
            }

            probeInfo.text(translateToLocalFormatted("tweakedpetroleum.hud.unknown"));
            return;
        }

        IReservoirType reservoir = (IReservoirType) info.getType();
        probeInfo.text(translateToLocalFormatted("tweakedpetroleum.hud.reservoir.name", reservoir.getName()));
        probeInfo.text(" §7" + translateToLocalFormatted("tweakedpetroleum.jei.reservoir.replenishRate", reservoir.getReplenishRate()));
        probeInfo.text(" §7" + translateToLocalFormatted("tweakedpetroleum.jei.reservoir.speed", reservoir.getPumpSpeed()));
        probeInfo.text(" §7" + translateToLocalFormatted("tweakedpetroleum.hud.current_level", BaseHEIUtil.numberFormat.format(info.current)));
        probeInfo.text("");

        var powerTier = PowerTierHandler.getPowerTier(reservoir.getPowerTier());
        probeInfo.text(translateToLocalFormatted("tweakedlib.jei.power_tier", BaseHEIUtil.numberFormat.format(PowerTierHandler.getTierOfSpecifiedPowerTier(reservoir.getPowerTier()))));
        probeInfo.text(" §7" + translateToLocalFormatted("tweakedlib.jei.power_capacity", BaseHEIUtil.numberFormat.format(powerTier.getCapacity())));
        probeInfo.text(" §7" + translateToLocalFormatted("tweakedlib.jei.power_usage", BaseHEIUtil.numberFormat.format(powerTier.getRft())));
        probeInfo.text(" §7" + translateToLocalFormatted("tweakedlib.hud.current_power", BaseHEIUtil.numberFormat.format(((IFluxReceiver) te).getEnergyStored(null))));
    }
}
