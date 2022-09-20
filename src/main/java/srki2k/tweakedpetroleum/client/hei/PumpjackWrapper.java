package srki2k.tweakedpetroleum.client.hei;

import com.google.common.collect.Lists;
import flaxbeard.immersivepetroleum.api.crafting.PumpjackHandler;
import flaxbeard.immersivepetroleum.common.Config;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import srki2k.tweakedlib.api.hei.BaseHEIUtil;
import srki2k.tweakedpetroleum.api.ihelpers.IReservoirType;
import srki2k.tweakedpetroleum.util.HEIPumpjackUtil;

import java.util.List;

public class PumpjackWrapper implements IRecipeWrapper {
    private final IReservoirType reservoir;
    private final Fluid reservoirFluid;

    public PumpjackWrapper(PumpjackHandler.ReservoirType reservoir) {
        this.reservoir = (IReservoirType) reservoir;
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
        return new FluidStack(reservoirFluid, (int) (((long) reservoir.getMaxSize() + (long) reservoir.getMinSize()) / 2));
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        ingredients.setOutputs(VanillaTypes.FLUID, Lists.newArrayList(getAverageFluid()));
    }

    @Override
    @SuppressWarnings("NullableProblems")
    public List<String> getTooltipStrings(int mouseX, int mouseY) {
        String[][] strings = new String[2][];

        if (reservoir.getDrainChance() != 1f) {
            strings[0] = new String[]{"jei.pumpjack.reservoir.draw_chance",
                    String.valueOf(reservoir.getDrainChance() * 100),
                    String.valueOf(100f - (reservoir.getDrainChance() * 100))};
        }
        if (Config.IPConfig.Extraction.req_pipes) {
            strings[1] = new String[]{"jei.pumpjack.reservoir.req_pipes"};
        }

        return HEIPumpjackUtil.tooltipStrings(mouseX, mouseY, strings, reservoir);
    }

    @Override
    @SuppressWarnings("NullableProblems")
    public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
        int warningCount = 0;

        if (Config.IPConfig.Extraction.req_pipes) {
            warningCount++;
        }

        if (reservoir.getDrainChance() != 1f) {
            warningCount++;
        }

        if (warningCount > 0) {
            BaseHEIUtil.getPumpjackWarning().draw(minecraft, 58, 8);
            minecraft.fontRenderer.drawString(String.valueOf(warningCount), 58, 8, 16696077);
        }
    }

}
