package srki2k.tweakedpetroleum.util;

import flaxbeard.immersivepetroleum.api.crafting.PumpjackHandler;
import net.minecraftforge.fluids.FluidStack;
import srki2k.tweakedlib.api.hei.BaseHEIUtil;
import srki2k.tweakedpetroleum.api.ihelpers.IReservoirType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static flaxbeard.immersivepetroleum.api.crafting.PumpjackHandler.reservoirList;

public class HEIPumpjackUtil {

    public static void onTooltip(int slotIndex, IReservoirType reservoir, FluidStack ingredient, List<String> tooltip) {
        tooltip.clear();
        tooltip.add(BaseHEIUtil.formatString(reservoir.getName()));
        tooltip.add(BaseHEIUtil.translateToLocalFormatted("tweakedpetroleum.jei.reservoir.contents", ingredient.getLocalizedName()));

        if (slotIndex == 0) {
            tooltip.add(BaseHEIUtil.translateToLocalFormatted("tweakedpetroleum.jei.reservoir.max_size", BaseHEIUtil.numberFormat.format(reservoir.getMaxSize())));
            tooltip.add(BaseHEIUtil.translateToLocalFormatted("tweakedpetroleum.jei.reservoir.min_size", BaseHEIUtil.numberFormat.format(reservoir.getMinSize())));

            if (reservoir.getDrainChance() != 1f) {
                tooltip.add(BaseHEIUtil.translateToLocalFormatted("tweakedpetroleum.jei.reservoir.average",
                        (BaseHEIUtil.numberFormat.format(ingredient.amount) + " * " + (100f - (reservoir.getDrainChance() * 100)) + "%")));
                return;
            }

            tooltip.add(BaseHEIUtil.translateToLocalFormatted("tweakedpetroleum.jei.reservoir.average",
                    BaseHEIUtil.numberFormat.format(ingredient.amount)));
            return;
        }

        tooltip.add(BaseHEIUtil.translateToLocalFormatted("tweakedpetroleum.jei.reservoir.replenishRate", BaseHEIUtil.numberFormat.format(reservoir.getReplenishRate())));
        tooltip.add(BaseHEIUtil.translateToLocalFormatted("tweakedpetroleum.jei.reservoir.speed", BaseHEIUtil.numberFormat.format(reservoir.getPumpSpeed())));

        if (reservoir.getDrainChance() != 1f) {
            tooltip.add(BaseHEIUtil.translateToLocalFormatted("tweakedpetroleum.jei.reservoir.average.time",
                    (((long) ((reservoir.getMaxSize() + reservoir.getMinSize()) * (100f - (reservoir.getDrainChance() * 100))) / 2) / (reservoir.getPumpSpeed() * 24000L))));
            return;
        }

        tooltip.add(BaseHEIUtil.translateToLocalFormatted("tweakedpetroleum.jei.reservoir.average.time",
                (((long) (reservoir.getMaxSize() + reservoir.getMinSize()) / 2) / (reservoir.getPumpSpeed() * 24000L))));
    }


    public static List<String> tooltipStrings(int mouseX, int mouseY, String[][] customWarnings, IReservoirType reservoir) {

        if (mouseX > 57 && mouseX < 67) {
            if (mouseY > 41 && mouseY < 60) {
                List<String> list = new ArrayList<>();
                BaseHEIUtil.powerTierListData(list, reservoir.getPowerTier());

                return list;

            }

            if (mouseY > 55 && mouseY < 75) {
                List<String> list = new ArrayList<>();

                list.add(BaseHEIUtil.translateToLocalFormatted("tweakedpetroleum.jei.reservoir.weight", reservoirList.get((PumpjackHandler.ReservoirType) reservoir)));
                list.add("");

                list.add(BaseHEIUtil.translateToLocalFormatted("tweakedpetroleum.jei.reservoir.biomes"));
                list.add(BaseHEIUtil.translateToLocalFormatted("tweakedpetroleum.jei.reservoir.biome_whitelist", Arrays.toString(reservoir.getBiomeWhitelist())));
                list.add(BaseHEIUtil.translateToLocalFormatted("tweakedpetroleum.jei.reservoir.biome_blacklist", Arrays.toString(reservoir.getBiomeBlacklist())));

                list.add("");

                BaseHEIUtil.dimensionListData(list, reservoir.getDimensionWhitelist(), reservoir.getDimensionBlacklist());

                return list;

            }

        }

        if (customWarnings != null && mouseX > 55 && mouseX < 71 && mouseY > 6 && mouseY < 23) {
            List<String> list = new ArrayList<>();

            for (String[] s : customWarnings) {
                if (s == null) {
                    continue;
                }

                if (s.length == 3) {
                    list.add(BaseHEIUtil.translateToLocalFormatted(s[0], s[1], s[2]));
                    continue;
                }

                if (s.length == 2) {
                    list.add(BaseHEIUtil.translateToLocalFormatted(s[0], s[1]));
                    continue;
                }

                list.add(BaseHEIUtil.translateToLocal(s[0]));

            }

            return list;
        }

        return null;
    }

}
