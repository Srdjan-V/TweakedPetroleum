package io.github.srdjanv.tweakedpetroleum.util;

import flaxbeard.immersivepetroleum.api.crafting.PumpjackHandler;
import io.github.srdjanv.tweakedlib.api.hei.BaseHEIUtil;
import io.github.srdjanv.tweakedpetroleum.api.mixins.ITweakedPetReservoirType;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.IntSupplier;
import java.util.function.LongSupplier;

import static flaxbeard.immersivepetroleum.api.crafting.PumpjackHandler.reservoirList;

public class HEIPumpjackUtil extends BaseHEIUtil {

    public static void onTooltip(int slotIndex, ITweakedPetReservoirType reservoir, long ingredientAmount, String ingredientLocalizedName, List<String> tooltip) {
        tooltip.clear();
        tooltip.add("§7" + translateToLocal("tweakedpetroleum.jei.reservoir.contents") + " " +ingredientLocalizedName);

        if (slotIndex == 0) {
            sizeData(reservoir, ingredientAmount, tooltip, true);
            return;
        }
        speedData(reservoir, tooltip, true);
    }

    public static List<String> tooltipStrings(int mouseX, int mouseY, @Nullable Consumer<List<String>> customWarnings, ITweakedPetReservoirType reservoir,
                                              LongSupplier ingredientAmountSupplier, IntSupplier getStringWidthSupplier) {

        if (mouseY >= 6 & mouseY <= 12 && mouseX >= 6 && mouseX <= getStringWidthSupplier.getAsInt()) {
            var list = new ObjectArrayList<String>();
            list.add(formatString(reservoir.getName()));
            return list;
        }

        if (mouseX > 60 && mouseX < 71 && mouseY > 55 && mouseY < 70) {
            List<String> list = new ObjectArrayList<>();
            powerTierListData(list, reservoir.getPowerTier());

            return list;
        }

        if (mouseX > 60 && mouseX < 72 && mouseY > 72 && mouseY < 86) {
            List<String> list = new ObjectArrayList<>();

            list.add(translateToLocal("tweakedpetroleum.jei.reservoir.weight") + " " + reservoirList.get((PumpjackHandler.ReservoirType) reservoir));
            list.add("");

            list.add(translateToLocal("tweakedpetroleum.jei.reservoir.biomes"));
            list.add(" §7" + translateToLocal("tweakedpetroleum.jei.reservoir.biome_whitelist") + " " + Arrays.toString(reservoir.getBiomeWhitelist()));
            list.add(" §7" + translateToLocal("tweakedpetroleum.jei.reservoir.biome_blacklist") + " " + Arrays.toString(reservoir.getBiomeBlacklist()));

            list.add("");

            dimensionListData(list, reservoir.getDimensionWhitelist(), reservoir.getDimensionBlacklist());
            return list;
        }

        if (mouseX >= 10 && mouseX <= 29 && mouseY >= 70 && mouseY <= 86) {
            List<String> list = new ObjectArrayList<>();
            sizeData(reservoir, ingredientAmountSupplier.getAsLong(), list, false);
            return list;
        }

        if (mouseX >= 34 && mouseX <= 53 && mouseY >= 70 && mouseY <= 85) {
            List<String> list = new ObjectArrayList<>();
            speedData(reservoir, list, false);
            return list;
        }

        if (customWarnings != null && mouseX > 56 && mouseX < 73 && mouseY > 23 && mouseY < 39) {
            List<String> list = new ObjectArrayList<>();
            customWarnings.accept(list);
            return list;
        }
        return null;
    }


    private static void sizeData(ITweakedPetReservoirType reservoir, long ingredientAmount, List<String> tooltip, boolean nested) {
        final String prefix;
        if (nested) {
            prefix = " §7";
        } else {
            prefix = "§7";
        }

        tooltip.add(prefix + translateToLocal("tweakedpetroleum.jei.reservoir.max_size") + " " + numberFormat.format(reservoir.getMaxSize()));
        tooltip.add(prefix + translateToLocal("tweakedpetroleum.jei.reservoir.min_size") + " " + numberFormat.format(reservoir.getMinSize()));

        if (reservoir.getDrainChance() != 1f) {
            tooltip.add(prefix + translateToLocal("tweakedpetroleum.jei.reservoir.average") + " " +
                    (numberFormat.format(ingredientAmount) + " * " + (100f + (reservoir.getDrainChance() * 100)) + "%"));
            return;
        }

        tooltip.add(prefix + translateToLocal("tweakedpetroleum.jei.reservoir.average") + " " +
                numberFormat.format(ingredientAmount));
    }

    private static void speedData(ITweakedPetReservoirType reservoir, List<String> tooltip, boolean nested) {
        final String prefix;
        if (nested) {
            prefix = " §7";
        } else {
            prefix = "§7";
        }

        tooltip.add(prefix + translateToLocal("tweakedpetroleum.jei.reservoir.replenishRate") + " " + numberFormat.format(reservoir.getReplenishRate()));
        tooltip.add(prefix + translateToLocal("tweakedpetroleum.jei.reservoir.speed") + " " + numberFormat.format(reservoir.getPumpSpeed()));

        if (reservoir.getDrainChance() != 1f) {
            tooltip.add(prefix + translateToLocal("tweakedpetroleum.jei.reservoir.average.time") + " " +
                    Math.max(0, ((long) (((long) reservoir.getMaxSize() + reservoir.getMinSize()) * (100f - (reservoir.getDrainChance() * 100))) / 2)
                            / (reservoir.getPumpSpeed() * 24000L)));
            return;
        }

        tooltip.add(prefix + translateToLocal("tweakedpetroleum.jei.reservoir.average.time") + " " +
                Math.max(0, (((long) reservoir.getMaxSize() + reservoir.getMinSize()) / 2)
                        / (reservoir.getPumpSpeed() * 24000L)));
    }
}
