package srki2k.tweakedpetroleum.client.hei;

import flaxbeard.immersivepetroleum.api.crafting.PumpjackHandler;
import flaxbeard.immersivepetroleum.common.IPContent;
import flaxbeard.immersivepetroleum.common.blocks.metal.BlockTypes_IPMetalMultiblock;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import net.minecraft.item.ItemStack;
import srki2k.tweakedlib.util.Constants;
import srki2k.tweakedpetroleum.TweakedPetroleum;
import srki2k.tweakedpetroleum.api.crafting.TweakedPumpjackHandler;
import srki2k.tweakedpetroleum.api.ihelpers.IReservoirType;
import srki2k.tweakedpetroleum.util.hei.HEIPumpjackUtil;

import java.util.Collection;
import java.util.stream.Collectors;

@mezz.jei.api.JEIPlugin
public class HEIPlugin implements IModPlugin {

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry) {
        HEIPumpjackUtil.initPumpjackGui(registry.getJeiHelpers().getGuiHelper(),
                new ItemStack(IPContent.blockMetalMultiblock, 1, BlockTypes_IPMetalMultiblock.PUMPJACK_PARENT.getMeta()),
                TweakedPetroleum.MODID);
        registry.addRecipeCategories(new PumpjackCategory());
    }


    @Override
    public void register(IModRegistry registry) {
        registry.handleRecipes(PumpjackHandler.ReservoirType.class, PumpjackWrapper::new, PumpjackCategory.UID);
        registry.addRecipes(getRecipes(), PumpjackCategory.UID);
        registry.addRecipeCatalyst(HEIPumpjackUtil.getPumpjackCatalyst(), PumpjackCategory.UID);
    }

    private Collection<PumpjackHandler.ReservoirType> getRecipes() {
        if (Constants.isTweakedPetroleumGasLoaded()) {
            return PumpjackHandler.reservoirList.keySet().stream().
                    filter(reservoirType -> ((IReservoirType) reservoirType).getReservoirContent() == TweakedPumpjackHandler.ReservoirContent.LIQUID).
                    collect(Collectors.toList());
        }

        return PumpjackHandler.reservoirList.keySet();
    }

}
