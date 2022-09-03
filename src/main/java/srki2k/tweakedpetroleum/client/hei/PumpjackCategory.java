package srki2k.tweakedpetroleum.client.hei;

import flaxbeard.immersivepetroleum.common.Config;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiFluidStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.util.Translator;
import net.minecraft.client.Minecraft;
import srki2k.tweakedpetroleum.TweakedPetroleum;
import srki2k.tweakedpetroleum.util.HEIUtil;

@SuppressWarnings("NullableProblems")
public class PumpjackCategory implements IRecipeCategory<PumpjackWrapper> {
    public static final String UID = "immersivepetroleum.pumpjack";

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
        return HEIUtil.getPumpjackBackground();
    }

    @Override
    public void drawExtras(Minecraft minecraft) {
        if (Config.IPConfig.Extraction.req_pipes) {
            HEIUtil.getPumpjackWarning().draw(minecraft, 58, 8);
        }
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, PumpjackWrapper recipeWrapper, IIngredients ingredients) {

        IGuiFluidStackGroup outputGuiFluids = recipeLayout.getFluidStacks();

        outputGuiFluids.init(0, false, 12, 10, 16, 47, recipeWrapper.getMaxFluid(), false, null);
        outputGuiFluids.set(0, recipeWrapper.getAverageFluid());

        outputGuiFluids.init(1, false, 36, 10, 16, 47, recipeWrapper.getPumpSpeed(), false, null);
        outputGuiFluids.set(1, recipeWrapper.getReplenishRate());

    }

}

