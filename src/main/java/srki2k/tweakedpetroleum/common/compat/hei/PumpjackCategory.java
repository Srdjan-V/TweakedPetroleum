package srki2k.tweakedpetroleum.common.compat.hei;


import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiFluidStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.util.Translator;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;
import srki2k.tweakedpetroleum.TweakedPetroleum;

import java.util.OptionalInt;

import static flaxbeard.immersivepetroleum.api.crafting.PumpjackHandler.reservoirList;

@SuppressWarnings("NullableProblems")
public class PumpjackCategory implements IRecipeCategory<PumpjackWrapper> {
    public static final String UID = "immersivepetroleum.pumpjack";
    private final IDrawable background;

    public PumpjackCategory(IGuiHelper guiHelper) {
        ResourceLocation location = new ResourceLocation(TweakedPetroleum.MODID, "textures/gui/pumpjack.png");
        background = guiHelper.createDrawable(location, 0, 0, 39, 77);
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

        IGuiFluidStackGroup fluids = recipeLayout.getFluidStacks();
        FluidStack fluid = recipeWrapper.getFluidOutputs().get(0);

        OptionalInt optionalInt = reservoirList.keySet().
                stream().
                filter(reservoirType -> reservoirType.getFluid().equals(fluid.getFluid())).
                mapToInt(value -> value.maxSize).
                max();

        if (optionalInt.isPresent()) {
            fluids.init(0, false, 12, 9, 16, 47, optionalInt.getAsInt(), false, null);
            fluids.set(0, fluid);
        }

    }

}

