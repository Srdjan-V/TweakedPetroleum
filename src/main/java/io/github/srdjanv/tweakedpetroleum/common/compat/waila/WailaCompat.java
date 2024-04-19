package io.github.srdjanv.tweakedpetroleum.common.compat.waila;

import blusunrize.immersiveengineering.api.energy.immersiveflux.IFluxReceiver;
import flaxbeard.immersivepetroleum.api.crafting.PumpjackHandler;
import flaxbeard.immersivepetroleum.common.Config;
import flaxbeard.immersivepetroleum.common.blocks.metal.TileEntityPumpjack;
import io.github.srdjanv.tweakedlib.api.hei.BaseHEIUtil;
import io.github.srdjanv.tweakedlib.api.powertier.PowerTierHandler;
import io.github.srdjanv.tweakedlib.api.waila.DuplicateFilter;
import io.github.srdjanv.tweakedlib.api.waila.FEFilter;
import io.github.srdjanv.tweakedlib.api.waila.IEWallaOverwriteManager;
import io.github.srdjanv.tweakedlib.api.waila.Styling;
import io.github.srdjanv.tweakedlib.common.Constants;
import io.github.srdjanv.tweakedpetroleum.api.mixins.ITweakedPetReservoirType;
import io.github.srdjanv.tweakedpetroleum.util.TweakedPetroleumInitializer;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.util.List;

import static mcjty.theoneprobe.api.IProbeInfo.ENDLOC;
import static mcjty.theoneprobe.api.IProbeInfo.STARTLOC;

public final class WailaCompat implements TweakedPetroleumInitializer {

    private static final String NBT_TAG = "tweakedPetrTag";

    @Override public boolean shouldRun() {
        return Constants.isWailaLoaded();
    }

    @Override public void preInit(FMLPreInitializationEvent event) {
        IEWallaOverwriteManager manager = IEWallaOverwriteManager.getInstance();
        manager.registerIEBodyOverwrite(TileEntityPumpjack.class, WailaCompat::getWailaBody);
        manager.registerIENBTDataOverwrite(TileEntityPumpjack.class, WailaCompat::getNBTData);
    }

    private static List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
        if (accessor.getNBTData().hasKey(NBT_TAG)) {
            final var toolTip = new ObjectArrayList<String>();
            final var nbtList = (NBTTagList) accessor.getNBTData().getTag(NBT_TAG);

            for (NBTBase nbtBase : nbtList) {
                if (nbtBase instanceof NBTTagString string) {
                    toolTip.add(Styling.stylifyString(string.getString()));
                }
            }
            DuplicateFilter.add(currenttip, toolTip);
        }

        FEFilter.filter(currenttip);
        return currenttip;
    }

    private static NBTTagCompound getNBTData(EntityPlayerMP player, TileEntity te, NBTTagCompound tag, World world, BlockPos pos) {
        TileEntityPumpjack master = ((TileEntityPumpjack) te).master();
        if (master == null) return tag;
        var info = PumpjackHandler.getOilWorldInfo(master.getWorld(), master.getPos().getX() >> 4, master.getPos().getZ() >> 4);

        var tipList = new ObjectArrayList<String>();
        listBuild:
        {
            if (info == null) {
                tipList.add(STARTLOC + "tweakedpetroleum.hud.reservoir.empty" + ENDLOC);
                break listBuild;
            }

            if (master.wasActive && (info.current == info.capacity)) {
                tipList.add(STARTLOC + "tweakedpetroleum.hud.reservoir.unknown" + ENDLOC);
            } else if (!master.wasActive) {
                if (Config.IPConfig.Extraction.req_pipes) {
                    tipList.add(STARTLOC + "tweakedpetroleum.hud.reservoir.req_pipes" + ENDLOC);
                } else tipList.add(STARTLOC + "tweakedpetroleum.hud.reservoir.req_power" + ENDLOC);
            }

            ITweakedPetReservoirType reservoir = (ITweakedPetReservoirType) info.getType();
            if (reservoir == null) break listBuild;
            if (master.wasActive && (info.current != info.capacity)) {
                tipList.add(STARTLOC + "tweakedpetroleum.hud.reservoir.name" + ENDLOC + " " + reservoir.getName());
                tipList.add(" §7" + STARTLOC + "tweakedpetroleum.jei.reservoir.replenishRate" + ENDLOC + " " + reservoir.getReplenishRate() + " mb/t");
                tipList.add(" §7" + STARTLOC + "tweakedpetroleum.jei.reservoir.speed" + ENDLOC + " " + reservoir.getPumpSpeed() + " mb/t");
                tipList.add(" §7" + STARTLOC + "tweakedpetroleum.jei.reservoir.speed" + ENDLOC + " " + reservoir.getPumpSpeed() + " mb/t");
            }

            var powerTier = PowerTierHandler.getPowerTier(reservoir.getPowerTier());
            tipList.add(STARTLOC + "tweakedlib.jei.power_tier" + ENDLOC + " " +
                    BaseHEIUtil.numberFormat.format(PowerTierHandler.getTierOfSpecifiedPowerTier(reservoir.getPowerTier())));
            tipList.add(STARTLOC + "tweakedlib.jei.power_usage" + ENDLOC + " " +
                    BaseHEIUtil.numberFormat.format(powerTier.getRft()) + " IF/t");

            tipList.add(String.format("§7 %s / %s IF",
                    BaseHEIUtil.numberFormat.format(((IFluxReceiver) te).getEnergyStored(null)),
                    BaseHEIUtil.numberFormat.format(powerTier.getCapacity())));
        }

        if (!tipList.isEmpty()) {
            var tweakedPetrTag = new NBTTagList();
            for (String tip : tipList) tweakedPetrTag.appendTag(new NBTTagString(tip));
            tag.setTag(NBT_TAG, tweakedPetrTag);
        }

        return tag;
    }


}
