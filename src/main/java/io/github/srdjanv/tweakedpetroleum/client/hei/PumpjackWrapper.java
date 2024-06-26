package io.github.srdjanv.tweakedpetroleum.client.hei;

import com.google.common.collect.Lists;
import flaxbeard.immersivepetroleum.api.crafting.PumpjackHandler;
import flaxbeard.immersivepetroleum.common.Config;
import io.github.srdjanv.tweakedpetroleum.api.mixins.ITweakedPetReservoirType;
import io.github.srdjanv.tweakedpetroleum.common.Configs;
import io.github.srdjanv.tweakedpetroleum.util.HEIPumpjackUtil;
import mezz.jei.api.gui.ITooltipCallback;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

import java.util.List;
import java.util.function.Consumer;

import static flaxbeard.immersivepetroleum.api.crafting.PumpjackHandler.reservoirList;

public class PumpjackWrapper implements IRecipeWrapper, ITooltipCallback<FluidStack> {
    private final ITweakedPetReservoirType reservoir;
    private final Fluid reservoirFluid;

    public PumpjackWrapper(PumpjackHandler.ReservoirType reservoir) {
        this.reservoir = (ITweakedPetReservoirType) reservoir;
        reservoirFluid = reservoir.getFluid();
    }

    public FluidStack getReplenishRateFluid() {
        return new FluidStack(reservoirFluid, reservoir.getReplenishRate());
    }

    public int getPumpSpeed() {
        return reservoir.getPumpSpeed();
    }

    public int getMaxFluid() {
        return reservoir.getMaxSize();
    }

    public FluidStack getAverageFluid() {
        return new FluidStack(reservoirFluid, getAverage());
    }

    public int getAverage() {
        return (int) (((long) reservoir.getMaxSize() + reservoir.getMinSize()) / 2);
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        ingredients.setOutputs(VanillaTypes.FLUID, Lists.newArrayList(getAverageFluid()));
    }

    private int getStringWidth() {
        return Math.min(77, Minecraft.getMinecraft().fontRenderer.getStringWidth(reservoir.getName()) + 6);
    }

    @Override
    public List<String> getTooltipStrings(int mouseX, int mouseY) {
        Consumer<List<String>> customWarnings = null;
        if (reservoir.getDrainChance() != 1f) {
            customWarnings = list-> list.add(HEIPumpjackUtil.translateToLocal("tweakedpetroleum.jei.reservoir.draw_chance1") + " " + reservoir.getDrainChance() * 100 +
                    HEIPumpjackUtil.translateToLocal("tweakedpetroleum.jei.reservoir.draw_chance2") + " " + (100f - (reservoir.getDrainChance() * 100)) +
                    HEIPumpjackUtil.translateToLocal("tweakedpetroleum.jei.reservoir.draw_chance3")
            );
        }
        if (Config.IPConfig.Extraction.req_pipes) {
            customWarnings = list -> list.add(HEIPumpjackUtil.translateToLocal("tweakedpetroleum.jei.reservoir.req_pipes"));
        }

        return HEIPumpjackUtil.tooltipStrings(mouseX, mouseY, customWarnings, reservoir, this::getAverage, this::getStringWidth);
    }

    @Override
    @SuppressWarnings("NullableProblems")
    public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
        if (Configs.TPConfig.HEIConfig.drawPowerTier) HEIPumpjackUtil.drawPowerTier(minecraft,57, 50, reservoir.getPowerTier());
        if (Configs.TPConfig.HEIConfig.drawSpawnWeight) HEIPumpjackUtil.drawSpawnWeight(minecraft,57, 70, reservoirList.get((PumpjackHandler.ReservoirType) reservoir));

        int warningCount = 0;
        if (Config.IPConfig.Extraction.req_pipes) {
            warningCount++;
        }

        if (reservoir.getDrainChance() != 1f) {
            warningCount++;
        }

        if (warningCount > 0) {
            HEIPumpjackUtil.getPumpjackWarning().draw(minecraft, 56, 24);
            minecraft.fontRenderer.drawString(String.valueOf(warningCount), 56, 22, 16696077);
        }

        if (getStringWidth() >= 77) {
            minecraft.fontRenderer.drawString(minecraft.fontRenderer.trimStringToWidth(
                    HEIPumpjackUtil.formatString(reservoir.getName()), 68).concat("..."), 6, 6, 15658734);
            return;
        }
        minecraft.fontRenderer.drawString(HEIPumpjackUtil.formatString(reservoir.getName()), 6, 6, 15658734);
    }

    @Override
    public void onTooltip(int slotIndex, boolean input, FluidStack ingredient, List<String> tooltip) {
        HEIPumpjackUtil.onTooltip(slotIndex, reservoir, ingredient.amount, ingredient.getLocalizedName(), tooltip);
    }
}
