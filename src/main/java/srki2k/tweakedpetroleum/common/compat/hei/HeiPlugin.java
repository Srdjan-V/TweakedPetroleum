package srki2k.tweakedpetroleum.common.compat.hei;

import flaxbeard.immersivepetroleum.api.crafting.PumpjackHandler;
import flaxbeard.immersivepetroleum.common.IPContent;
import flaxbeard.immersivepetroleum.common.blocks.metal.BlockTypes_IPMetalMultiblock;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import net.minecraft.item.ItemStack;
import srki2k.tweakedpetroleum.api.crafting.TweakedPumpjackHandler;

@mezz.jei.api.JEIPlugin
public class HeiPlugin implements IModPlugin {

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry) {
        registry.addRecipeCategories(new PumpjackCategory(registry.getJeiHelpers().getGuiHelper()));
    }


    @Override
    public void register(IModRegistry registry) {
        registry.handleRecipes(TweakedPumpjackHandler.TweakedReservoirType.class, PumpjackWrapper::new, PumpjackCategory.UID);
        registry.addRecipes(PumpjackHandler.reservoirList.keySet(), PumpjackCategory.UID);
        registry.addRecipeCatalyst(new ItemStack(IPContent.blockMetalMultiblock, 1, BlockTypes_IPMetalMultiblock.PUMPJACK_PARENT.getMeta()), PumpjackCategory.UID);
    }

}
