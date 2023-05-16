package io.github.srdjanv.tweakedpetroleum.common.compat.waila;

import blusunrize.immersiveengineering.api.energy.immersiveflux.IFluxReceiver;
import flaxbeard.immersivepetroleum.common.Config;
import flaxbeard.immersivepetroleum.common.blocks.metal.TileEntityPumpjack;
import io.github.srdjanv.tweakedlib.api.hei.BaseHEIUtil;
import io.github.srdjanv.tweakedlib.api.powertier.PowerTierHandler;
import io.github.srdjanv.tweakedlib.api.waila.WallaOverwriteManager;
import io.github.srdjanv.tweakedpetroleum.api.crafting.TweakedPumpjackHandler;
import io.github.srdjanv.tweakedpetroleum.api.ihelpers.IReservoirType;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.Loader;

import java.util.ArrayList;
import java.util.HashSet;

import static io.github.srdjanv.tweakedlib.api.hei.BaseHEIUtil.translateToLocalFormatted;

public final class WailaCompat {
    private WailaCompat() {
    }

    public static void init() {
        if (!Loader.isModLoaded("waila")) return;

        WallaOverwriteManager manager = WallaOverwriteManager.getInstance();
        manager.registerBodyOverwrite(TileEntityPumpjack.class, (itemStack, currenttip, accessor, config) -> {
            if (accessor.getNBTData().hasKey("tweakedPetrTag")) {
                var toolTip = new ArrayList<String>();
                var tweakedPetBaseTag = accessor.getNBTData().getTag("tweakedPetrTag");
                var tweakedPetTag = (NBTTagCompound) tweakedPetBaseTag;
                switch (tweakedPetTag.getString("status")) {
                    case "unknown": {
                        toolTip.add(translateToLocalFormatted("tweakedpetroleum.waila.unknown"));
                        return currenttip;
                    }
                    case "needs_pipes": {
                        toolTip.add(translateToLocalFormatted("tweakedpetroleum.jei.reservoir.req_pipes"));
                        return currenttip;
                    }
                    case "empty": {
                        toolTip.add(translateToLocalFormatted("tweakedpetroleum.waila.empty"));
                        return currenttip;
                    }
                }

                toolTip.add(translateToLocalFormatted("tweakedpetroleum.jei.reservoir.contents", tweakedPetTag.getString("contents")));
                toolTip.add(" §7" + translateToLocalFormatted("tweakedpetroleum.jei.reservoir.replenishRate", tweakedPetTag.getInteger("replenishRate")));
                toolTip.add(" §7" + translateToLocalFormatted("tweakedpetroleum.jei.reservoir.speed", tweakedPetTag.getInteger("speed")));
                toolTip.add(" §7" + translateToLocalFormatted("tweakedpetroleum.waila.current_level", BaseHEIUtil.numberFormat.format(tweakedPetTag.getInteger("current_level"))));
                toolTip.add("");

                toolTip.add(translateToLocalFormatted("tweakedlib.jei.power_tier", BaseHEIUtil.numberFormat.format(tweakedPetTag.getInteger("power_tier"))));
                toolTip.add(" §7" + translateToLocalFormatted("tweakedlib.jei.power_capacity", BaseHEIUtil.numberFormat.format(tweakedPetTag.getInteger("power_capacity"))));
                toolTip.add(" §7" + translateToLocalFormatted("tweakedlib.jei.power_usage", BaseHEIUtil.numberFormat.format(tweakedPetTag.getInteger("power_usage"))));
                toolTip.add(" §7" + translateToLocalFormatted("tweakedpetroleum.waila.current_rfpower", BaseHEIUtil.numberFormat.format(tweakedPetTag.getInteger("current_rfpower"))));

                if (!new HashSet<>(currenttip).containsAll(toolTip)) {
                    currenttip.addAll(toolTip);
                }
            }
            return currenttip;
        });


        manager.registerNBTDataOverwrite(TileEntityPumpjack.class, (player, te, tag, world, pos) -> {
            TileEntityPumpjack master = ((TileEntityPumpjack) te).master();
            if (master == null) {
                return tag;
            }
            var info = TweakedPumpjackHandler.getOilWorldInfoWithoutCreation(master.getWorld(), master.getPos().getX() >> 4, master.getPos().getZ() >> 4);
            var tweakedPetrTag = new NBTTagCompound();
            tag.setTag("tweakedPetrTag", tweakedPetrTag);
            if (info == null) {
                tweakedPetrTag.setString("status", "unknown");
                return tag;
            }

            IReservoirType reservoir;
            {
                var type = info.getType();
                if (info.getType() == null) {
                    tweakedPetrTag.setString("status", "empty");
                    return tag;
                }

                if (Config.IPConfig.Extraction.req_pipes) {
                    tweakedPetrTag.setString("status", "needs_pipes");
                    return tag;
                }

                if (info.current == info.capacity) {
                    tweakedPetrTag.setString("status", "unknown");
                    return tag;
                }

                if (info.current == 0) {
                    tweakedPetrTag.setString("status", "empty");
                    return tag;
                }

                reservoir = (IReservoirType) type;
            }

            tweakedPetrTag.setString("contents", reservoir.getName());
            tweakedPetrTag.setInteger("replenishRate", reservoir.getReplenishRate());
            tweakedPetrTag.setInteger("speed", reservoir.getPumpSpeed());
            tweakedPetrTag.setInteger("current_level", info.current);

            var powerTier = PowerTierHandler.getPowerTier(reservoir.getPowerTier());
            tweakedPetrTag.setInteger("power_tier", PowerTierHandler.getTierOfSpecifiedPowerTier(reservoir.getPowerTier()));
            tweakedPetrTag.setInteger("power_capacity", powerTier.getCapacity());
            tweakedPetrTag.setInteger("power_usage", powerTier.getRft());
            tweakedPetrTag.setInteger("current_rfpower", ((IFluxReceiver) te).getEnergyStored(null));

            return tag;
        });

    }
}
