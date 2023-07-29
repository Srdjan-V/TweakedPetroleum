package io.github.srdjanv.tweakedpetroleum.client.hei;

import flaxbeard.immersivepetroleum.api.crafting.PumpjackHandler;
import flaxbeard.immersivepetroleum.common.IPContent;
import flaxbeard.immersivepetroleum.common.blocks.metal.BlockTypes_IPMetalMultiblock;
import io.github.srdjanv.tweakedlib.api.hei.BaseHEIUtil;
import io.github.srdjanv.tweakedlib.common.Constants;
import io.github.srdjanv.tweakedpetroleum.TweakedPetroleum;
import io.github.srdjanv.tweakedpetroleum.api.crafting.TweakedPumpjackHandler;
import io.github.srdjanv.tweakedpetroleum.api.mixins.IReservoirType;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import net.minecraft.item.ItemStack;

import java.util.Collection;
import java.util.stream.Collectors;

@mezz.jei.api.JEIPlugin
public class HEIPlugin implements IModPlugin {

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry) {
        BaseHEIUtil.initPumpjackGui(registry.getJeiHelpers().getGuiHelper(),
                new ItemStack(IPContent.blockMetalMultiblock, 1, BlockTypes_IPMetalMultiblock.PUMPJACK_PARENT.getMeta()),
                TweakedPetroleum.MODID);
        registry.addRecipeCategories(new PumpjackCategory());
    }

    @Override
    public void register(IModRegistry registry) {
        registry.handleRecipes(PumpjackHandler.ReservoirType.class, PumpjackWrapper::new, PumpjackCategory.UID);
        registry.addRecipes(getRecipes(), PumpjackCategory.UID);
        registry.addRecipeCatalyst(BaseHEIUtil.getPumpjackCatalyst(), PumpjackCategory.UID);
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
