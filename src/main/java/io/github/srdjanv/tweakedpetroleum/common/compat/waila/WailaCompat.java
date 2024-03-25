package io.github.srdjanv.tweakedpetroleum.common.compat.waila;

import blusunrize.immersiveengineering.api.energy.immersiveflux.IFluxReceiver;
import flaxbeard.immersivepetroleum.api.crafting.PumpjackHandler;
import flaxbeard.immersivepetroleum.common.Config;
import flaxbeard.immersivepetroleum.common.blocks.metal.TileEntityPumpjack;
import io.github.srdjanv.tweakedlib.api.hei.BaseHEIUtil;
import io.github.srdjanv.tweakedlib.api.powertier.PowerTierHandler;
import io.github.srdjanv.tweakedlib.api.waila.WallaOverwriteManager;
import io.github.srdjanv.tweakedlib.common.Constants;
import io.github.srdjanv.tweakedpetroleum.api.mixins.IReservoirType;
import io.github.srdjanv.tweakedpetroleum.util.TweakedPetroleumInitializer;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static io.github.srdjanv.tweakedlib.api.hei.BaseHEIUtil.translateToLocalFormatted;

public final class WailaCompat implements TweakedPetroleumInitializer {
    private static final String nbtTag = "tweakedPetrTag";
    private static final String statusKey = "status";
    private static final String statusUnknown = "unknown";
    private static final String statusNeedsPipes = "needs_pipes";
    private static final String statusEmpty = "empty";

    private static final String NAME = "name";
    private static final String REPLENISH_RATE = "replenishRate";
    private static final String SPEED = "speed";
    private static final String CURRENT_LEVEL = "current_level";

    private static final String POWER_TIER = "power_tier";
    private static final String POWER_CAPACITY = "power_capacity";
    private static final String POWER_USAGE = "power_usage";
    private static final String CURRENT_RFPOWER = "current_rfpower";

    @Override public boolean shouldRun() {
        return Constants.isWailaLoaded();
    }

    @Override public void preInit(FMLPreInitializationEvent event) {
        WallaOverwriteManager manager = WallaOverwriteManager.getInstance();
        manager.registerBodyOverwrite(TileEntityPumpjack.class, WailaCompat::getWailaBody);
        manager.registerNBTDataOverwrite(TileEntityPumpjack.class, WailaCompat::getNBTData);
    }

    private static List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
        if (accessor.getNBTData().hasKey(nbtTag)) {
            var toolTip = new ArrayList<String>();
            var tweakedPetBaseTag = accessor.getNBTData().getTag(nbtTag);
            var tweakedPetTag = (NBTTagCompound) tweakedPetBaseTag;
            switch (tweakedPetTag.getString(statusKey)) {
                case statusUnknown: {
                    toolTip.add(translateToLocalFormatted("tweakedpetroleum.hud.unknown"));
                    break;
                }
                case statusNeedsPipes: {
                    toolTip.add(translateToLocalFormatted("tweakedpetroleum.jei.reservoir.req_pipes"));
                    break;
                }
                case statusEmpty: {
                    toolTip.add(translateToLocalFormatted("tweakedpetroleum.hud.empty"));
                    break;
                }

                default: {
                    toolTip.add(translateToLocalFormatted("tweakedpetroleum.hud.reservoir.name", tweakedPetTag.getString(NAME)));
                    toolTip.add(" §7" + translateToLocalFormatted("tweakedpetroleum.jei.reservoir.replenishRate", tweakedPetTag.getInteger(REPLENISH_RATE)));
                    toolTip.add(" §7" + translateToLocalFormatted("tweakedpetroleum.jei.reservoir.speed", tweakedPetTag.getInteger(SPEED)));
                    toolTip.add(" §7" + translateToLocalFormatted("tweakedpetroleum.hud.current_level", BaseHEIUtil.numberFormat.format(tweakedPetTag.getInteger(CURRENT_LEVEL))));
                    toolTip.add("");

                    toolTip.add(translateToLocalFormatted("tweakedlib.jei.power_tier", BaseHEIUtil.numberFormat.format(tweakedPetTag.getInteger(POWER_TIER))));
                    toolTip.add(" §7" + translateToLocalFormatted("tweakedlib.jei.power_capacity", BaseHEIUtil.numberFormat.format(tweakedPetTag.getInteger(POWER_CAPACITY))));
                    toolTip.add(" §7" + translateToLocalFormatted("tweakedlib.jei.power_usage", BaseHEIUtil.numberFormat.format(tweakedPetTag.getInteger(POWER_USAGE))));
                    toolTip.add(" §7" + translateToLocalFormatted("tweakedlib.hud.current_power", BaseHEIUtil.numberFormat.format(tweakedPetTag.getInteger(CURRENT_RFPOWER))));
                }
            }

            //The tooltip function can get called 2 times in one tick
            if (!new HashSet<>(currenttip).containsAll(toolTip)) {
                currenttip.addAll(toolTip);
            }
        }
        return currenttip;
    }

    private static NBTTagCompound getNBTData(EntityPlayerMP player, TileEntity te, NBTTagCompound tag, World world, BlockPos pos) {
        TileEntityPumpjack master = ((TileEntityPumpjack) te).master();
        if (master == null) {
            return tag;
        }
        var info = PumpjackHandler.getOilWorldInfo(master.getWorld(), master.getPos().getX() >> 4, master.getPos().getZ() >> 4);
        var tweakedPetrTag = new NBTTagCompound();
        tag.setTag(nbtTag, tweakedPetrTag);

        if (info == null) {
            tweakedPetrTag.setString(statusKey, statusEmpty);
            return tag;
        }

        if (info.capacity == info.current) {
            if (Config.IPConfig.Extraction.req_pipes) {
                tweakedPetrTag.setString(statusKey, statusNeedsPipes);
                return tag;
            }

            tweakedPetrTag.setString(statusKey, statusUnknown);
            return tag;
        }

        IReservoirType reservoir = (IReservoirType) info.getType();
        tweakedPetrTag.setString(NAME, reservoir.getName());
        tweakedPetrTag.setInteger(REPLENISH_RATE, reservoir.getReplenishRate());
        tweakedPetrTag.setInteger(SPEED, reservoir.getPumpSpeed());
        tweakedPetrTag.setInteger(CURRENT_LEVEL, info.current);

        var powerTier = PowerTierHandler.getPowerTier(reservoir.getPowerTier());
        tweakedPetrTag.setInteger(POWER_TIER, PowerTierHandler.getTierOfSpecifiedPowerTier(reservoir.getPowerTier()));
        tweakedPetrTag.setInteger(POWER_CAPACITY, powerTier.getCapacity());
        tweakedPetrTag.setInteger(POWER_USAGE, powerTier.getRft());
        tweakedPetrTag.setInteger(CURRENT_RFPOWER, ((IFluxReceiver) te).getEnergyStored(null));

        return tag;
    }
}
