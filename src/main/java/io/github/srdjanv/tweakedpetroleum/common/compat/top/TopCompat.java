package io.github.srdjanv.tweakedpetroleum.common.compat.top;

import blusunrize.immersiveengineering.api.energy.immersiveflux.IFluxReceiver;
import flaxbeard.immersivepetroleum.common.Config;
import flaxbeard.immersivepetroleum.common.blocks.metal.TileEntityPumpjack;
import io.github.srdjanv.tweakedlib.api.hei.BaseHEIUtil;
import io.github.srdjanv.tweakedlib.api.powertier.PowerTierHandler;
import io.github.srdjanv.tweakedlib.api.top.TopOverwriteManager;
import io.github.srdjanv.tweakedpetroleum.api.crafting.TweakedPumpjackHandler;
import io.github.srdjanv.tweakedpetroleum.api.mixins.IReservoirType;
import net.minecraftforge.fml.common.Loader;

import static io.github.srdjanv.tweakedlib.api.hei.BaseHEIUtil.translateToLocalFormatted;

public final class TopCompat {
    private TopCompat() {
    }

    public static void init() {
        if (!Loader.isModLoaded("theoneprobe")) return;

        TopOverwriteManager manager = TopOverwriteManager.getInstance();
        manager.registerOverwrite(TileEntityPumpjack.class, (te, mode, probeInfo, player, world, blockState, data) -> {
            TileEntityPumpjack master = ((TileEntityPumpjack) te).master();
            if (master == null) {
                return;
            }
            var info = TweakedPumpjackHandler.getOilWorldInfoWithoutCreation(master.getWorld(), master.getPos().getX() >> 4, master.getPos().getZ() >> 4);
            if (info == null) {
                probeInfo.text(translateToLocalFormatted("tweakedpetroleum.waila.unknown"));
                return;
            }

            IReservoirType reservoir;
            if (Config.IPConfig.Extraction.req_pipes) {
                probeInfo.text(translateToLocalFormatted("tweakedpetroleum.jei.reservoir.req_pipes"));
                return;
            }

            reservoir = (IReservoirType) info.getType();
            if (reservoir == null) {
                probeInfo.text(translateToLocalFormatted("tweakedpetroleum.waila.empty"));
                return;
            }

            if (info.current == info.capacity) {
                probeInfo.text(translateToLocalFormatted("tweakedpetroleum.waila.unknown"));
                return;
            }

            probeInfo.text(translateToLocalFormatted("tweakedpetroleum.jei.reservoir.contents", reservoir.getName()));
            probeInfo.text(" §7" + translateToLocalFormatted("tweakedpetroleum.jei.reservoir.replenishRate", reservoir.getReplenishRate()));
            probeInfo.text(" §7" + translateToLocalFormatted("tweakedpetroleum.jei.reservoir.speed", reservoir.getPumpSpeed()));
            probeInfo.text(" §7" + translateToLocalFormatted("tweakedpetroleum.waila.current_level", BaseHEIUtil.numberFormat.format(info.current)));
            probeInfo.text("");

            var powerTier = PowerTierHandler.getPowerTier(reservoir.getPowerTier());
            probeInfo.text(translateToLocalFormatted("tweakedlib.jei.power_tier", BaseHEIUtil.numberFormat.format(PowerTierHandler.getTierOfSpecifiedPowerTier(reservoir.getPowerTier()))));
            probeInfo.text(" §7" + translateToLocalFormatted("tweakedlib.jei.power_capacity", BaseHEIUtil.numberFormat.format(powerTier.getCapacity())));
            probeInfo.text(" §7" + translateToLocalFormatted("tweakedlib.jei.power_usage", BaseHEIUtil.numberFormat.format(powerTier.getRft())));
            probeInfo.text(" §7" + translateToLocalFormatted("tweakedpetroleum.waila.current_rfpower", BaseHEIUtil.numberFormat.format(((IFluxReceiver) te).getEnergyStored(null))));

        });
    }
}
