package srki2k.tweakedpetroleum.util;

import flaxbeard.immersivepetroleum.api.crafting.PumpjackHandler;
import srki2k.tweakedlib.api.hei.BaseHEIUtil;
import srki2k.tweakedpetroleum.api.ihelpers.IReservoirType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static flaxbeard.immersivepetroleum.api.crafting.PumpjackHandler.reservoirList;

public class HEIPumpjackUtil {
    public static List<String> tooltipStrings(int mouseX, int mouseY, String[][] customWarnings, IReservoirType reservoir) {

        if (mouseY > 60 && mouseY < 74) {
            if (mouseX > 14 && mouseX < 25) {
                List<String> list = new ArrayList<>();

                list.add(BaseHEIUtil.formatString(reservoir.getName()));
                list.add(BaseHEIUtil.translateToLocalFormatted("tweakedpetroleum.jei.reservoir.max_size", BaseHEIUtil.numberFormat.format(reservoir.getMaxSize())));
                list.add(BaseHEIUtil.translateToLocalFormatted("tweakedpetroleum.jei.reservoir.min_size", BaseHEIUtil.numberFormat.format(reservoir.getMinSize())));

                return list;
            }

            if (mouseX > 37 && mouseX < 50) {
                List<String> list = new ArrayList<>();

                list.add(BaseHEIUtil.translateToLocalFormatted("tweakedpetroleum.jei.reservoir.replenishRate", BaseHEIUtil.numberFormat.format(reservoir.getReplenishRate())));
                list.add(BaseHEIUtil.translateToLocalFormatted("tweakedpetroleum.jei.reservoir.speed", BaseHEIUtil.numberFormat.format(reservoir.getPumpSpeed())));

                return list;
            }

            if (mouseX > 61 && mouseX < 74) {
                List<String> list = new ArrayList<>();

                list.add(BaseHEIUtil.translateToLocalFormatted("tweakedpetroleum.jei.reservoir.weight", reservoirList.get((PumpjackHandler.ReservoirType) reservoir)));
                list.add("");

                list.add(BaseHEIUtil.translateToLocalFormatted("tweakedpetroleum.jei.reservoir.biomes"));
                list.add(BaseHEIUtil.translateToLocalFormatted("tweakedpetroleum.jei.reservoir.biome_whitelist", Arrays.toString(reservoir.getBiomeWhitelist())));
                list.add(BaseHEIUtil.translateToLocalFormatted("tweakedpetroleum.jei.reservoir.biome_blacklist", Arrays.toString(reservoir.getBiomeBlacklist())));

                BaseHEIUtil.dimensionListData(list, reservoir.getDimensionWhitelist(), reservoir.getDimensionBlacklist());

                return list;
            }
        }

        if (mouseX > 61 && mouseX < 73 && mouseY > 44 && mouseY < 57) {
            List<String> list = new ArrayList<>();
            BaseHEIUtil.powerTierListData(list, reservoir.getPowerTier());

            return list;
        }

        if (customWarnings != null && mouseX > 58 && mouseX < 75 && mouseY > 8 && mouseY < 25) {
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
