package srki2k.tweakedpetroleum.util;

import flaxbeard.immersivepetroleum.api.crafting.PumpjackHandler;
import mezz.jei.util.Translator;
import net.minecraft.world.DimensionType;
import org.lwjgl.input.Keyboard;
import srki2k.tweakedlib.api.hei.BaseHEIUtil;
import srki2k.tweakedlib.api.powertier.PowerTierHandler;
import srki2k.tweakedlib.util.Constants;
import srki2k.tweakedpetroleum.api.ihelpers.IReservoirType;
import zmaster587.advancedRocketry.dimension.DimensionManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static flaxbeard.immersivepetroleum.api.crafting.PumpjackHandler.reservoirList;

public class HEIPumpjackUtil {

    public static String formatString(String s) {
        return (Character.toUpperCase(s.charAt(0)) + s.substring(1)).replace("_", " ");
    }

    public static String detailedDimension(int[] dim) {
        StringBuilder stringBuilder = new StringBuilder();

        int dimLength = dim.length;
        if (dimLength == 0) {
            return stringBuilder.append("[]").toString();
        }

        int elements = 1;
        foundDim:
        for (int id : dim) {
            //Vanilla dims and dims registered with DimensionType.register()
            for (DimensionType dimensionType : DimensionType.values()) {
                if (dimensionType.getId() == id) {
                    stringBuilder.append(formatString(dimensionType.getName())).
                            append(" [").
                            append(id).
                            append("]");

                    if (elements < dimLength) {
                        stringBuilder.append(", ");
                        elements++;
                        continue foundDim;
                    }
                    return stringBuilder.toString();
                }
            }

            //Advanced Rocketry planets
            if (Constants.isAdvancedRocketryLoaded()) {
                DimensionManager dimensionManager = DimensionManager.getInstance();
                Integer[] dims = dimensionManager.getRegisteredDimensions();

                for (Integer integer : dims) {
                    if (integer == id) {
                        stringBuilder.append(formatString(dimensionManager.getDimensionProperties(id).getName())).
                                append(" [").
                                append(id).
                                append("]");

                        if (elements < dimLength) {
                            stringBuilder.append(", ");
                            elements++;
                            continue foundDim;
                        }
                        return stringBuilder.toString();
                    }
                }
            }
        }

        return stringBuilder.append("Wrong dimension id or Missing integration").toString();
    }

    public static List<String> tooltipStrings(int mouseX, int mouseY, String[][] customWarnings, IReservoirType reservoir) {
        if (mouseX > 14 && mouseX < 25 && mouseY > 60 && mouseY < 74) {
            List<String> list = new ArrayList<>();

            list.add(HEIPumpjackUtil.formatString(reservoir.getName()));
            list.add(Translator.translateToLocalFormatted("jei.pumpjack.reservoir.max_size", BaseHEIUtil.numberFormat.format(reservoir.getMaxSize())));
            list.add(Translator.translateToLocalFormatted("jei.pumpjack.reservoir.min_size", BaseHEIUtil.numberFormat.format(reservoir.getMinSize())));

            return list;
        }

        if (mouseX > 37 && mouseX < 50 && mouseY > 60 && mouseY < 74) {
            List<String> list = new ArrayList<>();

            list.add(Translator.translateToLocalFormatted("jei.pumpjack.reservoir.replenishRate", BaseHEIUtil.numberFormat.format(reservoir.getReplenishRate())));
            list.add(Translator.translateToLocalFormatted("jei.pumpjack.reservoir.speed", BaseHEIUtil.numberFormat.format(reservoir.getPumpSpeed())));

            return list;
        }

        if (mouseX > 61 && mouseX < 74 && mouseY > 60 && mouseY < 74) {
            List<String> list = new ArrayList<>();

            list.add(Translator.translateToLocalFormatted("jei.pumpjack.reservoir.weight", reservoirList.get((PumpjackHandler.ReservoirType) reservoir)));
            list.add("");

            list.add(Translator.translateToLocalFormatted("jei.pumpjack.reservoir.biomes"));
            list.add(Translator.translateToLocalFormatted("jei.pumpjack.reservoir.biome_whitelist", Arrays.toString(reservoir.getBiomeWhitelist())));
            list.add(Translator.translateToLocalFormatted("jei.pumpjack.reservoir.biome_blacklist", Arrays.toString(reservoir.getBiomeBlacklist())));

            list.add(Translator.translateToLocalFormatted("jei.pumpjack.reservoir.dimensions"));
            if (Keyboard.isKeyDown(0x2A)) {
                list.add(Translator.translateToLocalFormatted("jei.pumpjack.reservoir.dimension_whitelist", HEIPumpjackUtil.detailedDimension(reservoir.getDimensionWhitelist())));
                list.add(Translator.translateToLocalFormatted("jei.pumpjack.reservoir.dimension_blacklist", HEIPumpjackUtil.detailedDimension(reservoir.getDimensionBlacklist())));

                return list;
            }

            list.add(Translator.translateToLocalFormatted("jei.pumpjack.reservoir.dimension_whitelist", Arrays.toString(reservoir.getDimensionWhitelist())));
            list.add(Translator.translateToLocalFormatted("jei.pumpjack.reservoir.dimension_blacklist", Arrays.toString(reservoir.getDimensionBlacklist())));


            list.add("");
            list.add(Translator.translateToLocalFormatted("jei.pumpjack.reservoir.lshift"));
            return list;
        }

        if (mouseX > 61 && mouseX < 73 && mouseY > 44 && mouseY < 57) {
            List<String> list = new ArrayList<>();

            list.add(Translator.translateToLocalFormatted("jei.pumpjack.reservoir.power_tier", reservoir.getPowerTier()));
            list.add(Translator.translateToLocalFormatted("jei.pumpjack.reservoir.power_capacity", BaseHEIUtil.numberFormat.format(PowerTierHandler.getPowerTier(reservoir.getPowerTier()).getCapacity())));
            list.add(Translator.translateToLocalFormatted("jei.pumpjack.reservoir.power_usage", BaseHEIUtil.numberFormat.format(PowerTierHandler.getPowerTier(reservoir.getPowerTier()).getRft())));

            return list;
        }

        if (customWarnings != null && mouseX > 58 && mouseX < 75 && mouseY > 8 && mouseY < 25) {
            List<String> list = new ArrayList<>();

            for (String[] s : customWarnings) {
                if (s == null) {
                    continue;
                }

                if (s.length == 3) {
                    list.add(Translator.translateToLocalFormatted(s[0], s[1], s[2]));
                    continue;
                }

                if (s.length == 2) {
                    list.add(Translator.translateToLocalFormatted(s[0], s[1]));
                    continue;
                }

                list.add(Translator.translateToLocal(s[0]));

            }

            return list;
        }

        return null;
    }

}
