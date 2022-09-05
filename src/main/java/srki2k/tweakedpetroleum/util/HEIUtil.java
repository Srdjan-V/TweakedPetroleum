package srki2k.tweakedpetroleum.util;

import flaxbeard.immersivepetroleum.api.crafting.PumpjackHandler;
import flaxbeard.immersivepetroleum.common.IPContent;
import flaxbeard.immersivepetroleum.common.blocks.metal.BlockTypes_IPMetalMultiblock;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.util.Translator;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.DimensionType;
import org.lwjgl.input.Keyboard;
import srki2k.tweakedpetroleum.TweakedPetroleum;
import srki2k.tweakedpetroleum.api.ihelpers.IReservoirType;
import zmaster587.advancedRocketry.dimension.DimensionManager;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static flaxbeard.immersivepetroleum.api.crafting.PumpjackHandler.reservoirList;
import static srki2k.tweakedpetroleum.api.crafting.TweakedPumpjackHandler.rftTier;

public class HEIUtil {

    public static NumberFormat numberFormat = new DecimalFormat("#,###,###,###.#");

    public static ItemStack pumpjackCatalyst = new ItemStack(IPContent.blockMetalMultiblock, 1, BlockTypes_IPMetalMultiblock.PUMPJACK_PARENT.getMeta());

    private static IDrawable pumpjackBackground;

    private static IDrawable pumpjackWarning;

    public static void initGuiHelper(IGuiHelper guiHelper) {
        ResourceLocation location = new ResourceLocation(TweakedPetroleum.MODID, "textures/gui/pumpjack.png");
        pumpjackBackground = guiHelper.createDrawable(location, 0, 0, 84, 80);
        pumpjackWarning = guiHelper.createDrawable(location, 85, 0, 102, 17);
    }

    public static IDrawable getPumpjackBackground() {
        return pumpjackBackground;
    }

    public static IDrawable getPumpjackWarning() {
        return pumpjackWarning;
    }

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

            list.add(HEIUtil.formatString(reservoir.getName()));
            list.add(Translator.translateToLocalFormatted("jei.pumpjack.reservoir.max_size", HEIUtil.numberFormat.format(reservoir.getMaxSize())));
            list.add(Translator.translateToLocalFormatted("jei.pumpjack.reservoir.min_size", HEIUtil.numberFormat.format(reservoir.getMinSize())));

            return list;
        }

        if (mouseX > 37 && mouseX < 50 && mouseY > 60 && mouseY < 74) {
            List<String> list = new ArrayList<>();

            list.add(Translator.translateToLocalFormatted("jei.pumpjack.reservoir.replenishRate", HEIUtil.numberFormat.format(reservoir.getReplenishRate())));
            list.add(Translator.translateToLocalFormatted("jei.pumpjack.reservoir.speed", HEIUtil.numberFormat.format(reservoir.getPumpSpeed())));

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
                list.add(Translator.translateToLocalFormatted("jei.pumpjack.reservoir.dimension_whitelist", HEIUtil.detailedDimension(reservoir.getDimensionWhitelist())));
                list.add(Translator.translateToLocalFormatted("jei.pumpjack.reservoir.dimension_blacklist", HEIUtil.detailedDimension(reservoir.getDimensionBlacklist())));

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
            list.add(Translator.translateToLocalFormatted("jei.pumpjack.reservoir.power_capacity", HEIUtil.numberFormat.format(rftTier.get(reservoir.getPowerTier()).capacity)));
            list.add(Translator.translateToLocalFormatted("jei.pumpjack.reservoir.power_usage", HEIUtil.numberFormat.format(rftTier.get(reservoir.getPowerTier()).rft)));

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

                list.add(Translator.translateToLocal(s[1]));

            }

            return list;
        }

        return null;
    }

}
