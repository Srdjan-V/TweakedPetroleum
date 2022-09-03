package srki2k.tweakedpetroleum.client.hei;

import flaxbeard.immersivepetroleum.api.crafting.PumpjackHandler;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import srki2k.tweakedpetroleum.api.crafting.TweakedPumpjackHandler;
import srki2k.tweakedpetroleum.api.ihelpers.IReservoirType;
import srki2k.tweakedpetroleum.util.HEIUtil;

import java.util.stream.Collectors;

@mezz.jei.api.JEIPlugin
public class HEIPlugin implements IModPlugin {

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry) {
        HEIUtil.initGuiHelper(registry.getJeiHelpers().getGuiHelper());
        registry.addRecipeCategories(new PumpjackCategory());
    }


    @Override
    public void register(IModRegistry registry) {
        registry.handleRecipes(PumpjackHandler.ReservoirType.class, PumpjackWrapper::new, PumpjackCategory.UID);
        registry.addRecipes(PumpjackHandler.reservoirList.keySet().stream()
                .filter(reservoirType -> ((IReservoirType)reservoirType).getReservoirContent() == TweakedPumpjackHandler.ReservoirContent.LIQUID).
                collect(Collectors.toList()), PumpjackCategory.UID);
        registry.addRecipeCatalyst(HEIUtil.pumpjackCatalyst, PumpjackCategory.UID);
    }

}
