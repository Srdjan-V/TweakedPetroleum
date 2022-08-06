package srki2k.tweakedpetroleum.common.compat.hei;


import blusunrize.immersiveengineering.common.util.compat.jei.IEFluidTooltipCallback;
import com.google.common.collect.Lists;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiFluidStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.ITooltipCallback;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.util.Translator;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import srki2k.tweakedpetroleum.TweakedPetroleum;
import srki2k.tweakedpetroleum.api.crafting.TweakedPumpjackHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.OptionalInt;

@SuppressWarnings("NullableProblems")
public class PumpjackCategory implements IRecipeCategory<PumpjackWrapper> {
    public static final String UID = "immersivepetroleum.pumpjack";
    private final IDrawable background;

    public PumpjackCategory(IGuiHelper guiHelper) {
        ResourceLocation location = new ResourceLocation(TweakedPetroleum.MODID, "textures/gui/pumpjack.png");
        background = guiHelper.createDrawable(location, 0, 0, 84, 80);
    }

    @Override
    public String getUid() {
        return UID;
    }

    @Override
    public String getTitle() {
        return Translator.translateToLocal("tile.immersivepetroleum.metal_multiblock.pumpjack.name");
    }

    @Override
    public String getModName() {
        return TweakedPetroleum.MODID;
    }

    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, PumpjackWrapper recipeWrapper, IIngredients ingredients) {

        IGuiFluidStackGroup outputGuiFluids = recipeLayout.getFluidStacks();

        TweakedPumpjackHandler.TweakedReservoirType reservoir = recipeWrapper.getReservoir();
        Fluid reservoirFluid = recipeWrapper.getReservoirFluid();

        FluidStack averageFluid = recipeWrapper.getAverageFluid();
        OptionalInt maxAverageFluid = recipeWrapper.getOptionalMaxAverageFluid();
        OptionalInt maxFluidReplenishRate = recipeWrapper.getOptionalMaxFluidReplenishRate();

        if (maxAverageFluid.isPresent()) {
            outputGuiFluids.init(0, false, 12, 10, 16, 47, maxAverageFluid.getAsInt(), false, null);
            outputGuiFluids.set(0, averageFluid);
        }

        if (maxFluidReplenishRate.isPresent()) {
            outputGuiFluids.init(1, false, 36, 10, 16, 47, maxFluidReplenishRate.getAsInt(), false, null);
            outputGuiFluids.set(1, new FluidStack(reservoirFluid, reservoir.replenishRate));
        }
    }

}

