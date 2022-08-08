package srki2k.tweakedpetroleum.common.compat.hei;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiFluidStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.util.Translator;
import net.minecraft.util.ResourceLocation;
import srki2k.tweakedpetroleum.TweakedPetroleum;

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

        outputGuiFluids.init(0, false, 12, 10, 16, 47, recipeWrapper.getMaxFluid(), false, null);
        outputGuiFluids.set(0, recipeWrapper.getAverageFluid());

        outputGuiFluids.init(1, false, 36, 10, 16, 47, recipeWrapper.getMaxFluidReplenishRate(), false, null);
        outputGuiFluids.set(1, recipeWrapper.getAverageReplenishFluid());

    }

}

