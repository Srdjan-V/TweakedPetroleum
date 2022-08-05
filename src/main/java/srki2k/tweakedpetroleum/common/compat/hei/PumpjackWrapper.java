package srki2k.tweakedpetroleum.common.compat.hei;

import com.google.common.collect.Lists;
import flaxbeard.immersivepetroleum.api.crafting.PumpjackHandler;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import mezz.jei.util.Translator;
import net.minecraftforge.fluids.FluidStack;
import srki2k.tweakedpetroleum.api.crafting.TweakedPumpjackHandler;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;

import static flaxbeard.immersivepetroleum.api.crafting.PumpjackHandler.reservoirList;
import static srki2k.tweakedpetroleum.api.crafting.TweakedPumpjackHandler.rftTier;

@SuppressWarnings("NullableProblems")
public class PumpjackWrapper implements IRecipeWrapper {

    public TweakedPumpjackHandler.TweakedReservoirType reservoir;
    public static NumberFormat numberFormat = new DecimalFormat("#,###,###,###.#");

    public PumpjackWrapper(TweakedPumpjackHandler.TweakedReservoirType reservoir) {
        this.reservoir = reservoir;
    }

    public List<FluidStack> getFluidOutputs() {
        return Lists.newArrayList(new FluidStack(reservoir.getFluid(), reservoir.maxSize));
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        ingredients.setOutputs(VanillaTypes.FLUID, getFluidOutputs());
    }

    @Override
    public List<String> getTooltipStrings(int mouseX, int mouseY) {
        List<String> list = new ArrayList<>();

        list.add(Character.toUpperCase(reservoir.name.charAt(0)) + reservoir.name.substring(1));
        list.add(Translator.translateToLocalFormatted("jei.pumpjack.reservoir.max_size", numberFormat.format(reservoir.maxSize)));
        list.add(Translator.translateToLocalFormatted("jei.pumpjack.reservoir.min_size", numberFormat.format(reservoir.minSize)));
        list.add(Translator.translateToLocalFormatted("jei.pumpjack.reservoir.replenishRate", numberFormat.format(reservoir.replenishRate)));

        Optional<PumpjackHandler.ReservoirType> res = reservoirList.keySet().stream().
                filter(reservoirType -> reservoirType.name.equals(reservoir.name)).
                findFirst();
        res.ifPresent(reservoirType -> list.add(Translator.translateToLocalFormatted("jei.pumpjack.reservoir.weight", reservoirList.get(reservoirType))));

        list.add(Translator.translateToLocalFormatted("jei.pumpjack.reservoir.speed", numberFormat.format(reservoir.pumpSpeed)));

        list.add(Translator.translateToLocalFormatted("jei.pumpjack.reservoir.biomes"));
        list.add(Translator.translateToLocalFormatted("jei.pumpjack.reservoir.biome_whitelist", Arrays.toString(reservoir.biomeWhitelist)));
        list.add(Translator.translateToLocalFormatted("jei.pumpjack.reservoir.biome_blacklist", Arrays.toString(reservoir.biomeBlacklist)));

        list.add(Translator.translateToLocalFormatted("jei.pumpjack.reservoir.dimensions"));
        list.add(Translator.translateToLocalFormatted("jei.pumpjack.reservoir.dimension_whitelist", Arrays.toString(reservoir.dimensionWhitelist)));
        list.add(Translator.translateToLocalFormatted("jei.pumpjack.reservoir.dimension_blacklist", Arrays.toString(reservoir.dimensionBlacklist)));

        list.add(Translator.translateToLocalFormatted("jei.pumpjack.reservoir.power_tier", reservoir.powerTier));
        list.add(Translator.translateToLocalFormatted("jei.pumpjack.reservoir.power_capacity", numberFormat.format(rftTier.get(reservoir.powerTier).capacity)));
        list.add(Translator.translateToLocalFormatted("jei.pumpjack.reservoir.power_usage", numberFormat.format(rftTier.get(reservoir.powerTier).rft)));

        return list;
    }

}
