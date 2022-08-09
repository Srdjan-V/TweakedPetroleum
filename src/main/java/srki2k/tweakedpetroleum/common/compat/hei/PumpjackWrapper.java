package srki2k.tweakedpetroleum.common.compat.hei;

import com.google.common.collect.Lists;
import flaxbeard.immersivepetroleum.api.crafting.PumpjackHandler;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import mezz.jei.util.Translator;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import srki2k.tweakedpetroleum.api.crafting.IReservoirType;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;

import static srki2k.tweakedpetroleum.api.crafting.TweakedPumpjackHandler.rftTier;

@SuppressWarnings("NullableProblems")
public class PumpjackWrapper implements IRecipeWrapper {
    public static NumberFormat numberFormat = new DecimalFormat("#,###,###,###.#");
    private final IReservoirType reservoir;
    private final Fluid reservoirFluid;

    public PumpjackWrapper(PumpjackHandler.ReservoirType reservoir) {
        this.reservoir = (IReservoirType) reservoir;
        reservoirFluid = reservoir.getFluid();
    }


    public Fluid getReservoirFluid() {
        return reservoirFluid;
    }

    public FluidStack getAverageFluid() {
        return new FluidStack(reservoirFluid, (reservoir.getMaxSize() + reservoir.getMinSize()) / 2);
    }

    public FluidStack getAverageReplenishFluid() {
        return new FluidStack(reservoirFluid, reservoir.getReplenishRate());
    }

    public IReservoirType getReservoir() {
        return reservoir;
    }

    public int getMaxFluidReplenishRate() {
        return reservoir.getReplenishRate();
    }

    public int getMinFluid() {
        return reservoir.getMinSize();
    }

    public int getMaxFluid() {
        return reservoir.getMaxSize();
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        ingredients.setOutputs(VanillaTypes.FLUID, Lists.newArrayList(getAverageFluid()));
    }

    @Override
    public List<String> getTooltipStrings(int mouseX, int mouseY) {
        List<String> list = new ArrayList<>();

        if (mouseX > 14 && mouseX < 25 && mouseY > 60 && mouseY < 74) {

            list.add(Character.toUpperCase(reservoir.getName().charAt(0)) + reservoir.getName().substring(1));
            list.add(Translator.translateToLocalFormatted("jei.pumpjack.reservoir.max_size", numberFormat.format(reservoir.getMaxSize())));
            list.add(Translator.translateToLocalFormatted("jei.pumpjack.reservoir.min_size", numberFormat.format(reservoir.getMaxSize())));

            return list;
        }

        if (mouseX > 37 && mouseX < 50 && mouseY > 60 && mouseY < 74) {
            list.add(Translator.translateToLocalFormatted("jei.pumpjack.reservoir.replenishRate", numberFormat.format(reservoir.getReplenishRate())));
            list.add(Translator.translateToLocalFormatted("jei.pumpjack.reservoir.speed", numberFormat.format(reservoir.getPumpSpeed())));

            return list;
        }

        if (mouseX > 61 && mouseX < 74 && mouseY > 60 && mouseY < 74) {

            //will probably add, this at some point
/*            list.add(Translator.translateToLocalFormatted("jei.pumpjack.reservoir.weight", reservoirList.get((PumpjackHandler.ReservoirType) reservoir)));
            list.add("");*/

            list.add(Translator.translateToLocalFormatted("jei.pumpjack.reservoir.biomes"));
            list.add(Translator.translateToLocalFormatted("jei.pumpjack.reservoir.biome_whitelist", Arrays.toString(reservoir.getBiomeWhitelist())));
            list.add(Translator.translateToLocalFormatted("jei.pumpjack.reservoir.biome_blacklist", Arrays.toString(reservoir.getBiomeBlacklist())));

            list.add(Translator.translateToLocalFormatted("jei.pumpjack.reservoir.dimensions"));
            list.add(Translator.translateToLocalFormatted("jei.pumpjack.reservoir.dimension_whitelist", Arrays.toString(reservoir.getDimensionWhitelist())));
            list.add(Translator.translateToLocalFormatted("jei.pumpjack.reservoir.dimension_blacklist", Arrays.toString(reservoir.getDimensionBlacklist())));

            return list;
        }

        if (mouseX > 61 && mouseX < 73 && mouseY > 44 && mouseY < 57) {
            list.add(Translator.translateToLocalFormatted("jei.pumpjack.reservoir.power_tier", reservoir.getPowerTier()));
            list.add(Translator.translateToLocalFormatted("jei.pumpjack.reservoir.power_capacity", numberFormat.format(rftTier.get(reservoir.getPowerTier()).capacity)));
            list.add(Translator.translateToLocalFormatted("jei.pumpjack.reservoir.power_usage", numberFormat.format(rftTier.get(reservoir.getPowerTier()).rft)));

            return list;
        }

        return null;
    }

}
