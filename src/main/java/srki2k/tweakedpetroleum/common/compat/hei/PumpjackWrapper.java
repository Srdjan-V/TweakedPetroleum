package srki2k.tweakedpetroleum.common.compat.hei;

import com.google.common.collect.Lists;
import flaxbeard.immersivepetroleum.api.crafting.PumpjackHandler;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import mezz.jei.util.Translator;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import srki2k.tweakedpetroleum.api.crafting.TweakedPumpjackHandler;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;

import static flaxbeard.immersivepetroleum.api.crafting.PumpjackHandler.reservoirList;
import static srki2k.tweakedpetroleum.api.crafting.TweakedPumpjackHandler.rftTier;

@SuppressWarnings("NullableProblems")
public class PumpjackWrapper implements IRecipeWrapper {
    public static NumberFormat numberFormat = new DecimalFormat("#,###,###,###.#");
    private final TweakedPumpjackHandler.TweakedReservoirType reservoir;
    private final Fluid reservoirFluid;

    public PumpjackWrapper(TweakedPumpjackHandler.TweakedReservoirType reservoir) {
        this.reservoir = reservoir;
        reservoirFluid = reservoir.getFluid();
    }


    public Fluid getReservoirFluid() {
        return reservoirFluid;
    }

    public FluidStack getAverageFluid() {
        return new FluidStack(reservoirFluid, (reservoir.maxSize + reservoir.minSize) / 2);
    }

    public OptionalInt getOptionalMaxFluidReplenishRate() {
        return reservoirList.keySet().
                stream().
                filter(reservoirType -> reservoirType.getFluid().equals(reservoirFluid)).
                mapToInt(value -> value.replenishRate).
                max();
    }

    public OptionalInt getOptionalMaxAverageFluid() {
        return reservoirList.keySet().
                stream().
                filter(reservoirType -> reservoirType.getFluid().equals(reservoirFluid)).
                mapToInt(value -> (value.maxSize + value.minSize) / 2).
                max();
    }

    public Optional<PumpjackHandler.ReservoirType> getReservoirWeight() {
        return reservoirList.keySet().
                stream().
                filter(reservoirType -> reservoirType.name.equals(reservoir.name)).
                findFirst();
    }

    public TweakedPumpjackHandler.TweakedReservoirType getReservoir() {
        return reservoir;
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        ingredients.setOutputs(VanillaTypes.FLUID, Lists.newArrayList(getAverageFluid()));
    }

    @Override
    public List<String> getTooltipStrings(int mouseX, int mouseY) {
        List<String> list = new ArrayList<>();

        if (mouseX > 14 && mouseX < 25 && mouseY > 60 && mouseY < 74) {

            list.add(Character.toUpperCase(reservoir.name.charAt(0)) + reservoir.name.substring(1));
            list.add(Translator.translateToLocalFormatted("jei.pumpjack.reservoir.max_size", numberFormat.format(reservoir.maxSize)));
            list.add(Translator.translateToLocalFormatted("jei.pumpjack.reservoir.min_size", numberFormat.format(reservoir.minSize)));

            return list;
        }

        if (mouseX > 37 && mouseX < 50 && mouseY > 60 && mouseY < 74) {
            list.add(Translator.translateToLocalFormatted("jei.pumpjack.reservoir.replenishRate", numberFormat.format(reservoir.replenishRate)));
            list.add(Translator.translateToLocalFormatted("jei.pumpjack.reservoir.speed", numberFormat.format(reservoir.pumpSpeed)));

            return list;
        }

        if (mouseX > 61 && mouseX < 74 && mouseY > 60 && mouseY < 74) {

            getReservoirWeight().
                    ifPresent(reservoirType -> {
                        list.add(Translator.translateToLocalFormatted("jei.pumpjack.reservoir.weight", reservoirList.get(reservoirType)));
                        list.add("");
                    });

            list.add(Translator.translateToLocalFormatted("jei.pumpjack.reservoir.biomes"));
            list.add(Translator.translateToLocalFormatted("jei.pumpjack.reservoir.biome_whitelist", Arrays.toString(reservoir.biomeWhitelist)));
            list.add(Translator.translateToLocalFormatted("jei.pumpjack.reservoir.biome_blacklist", Arrays.toString(reservoir.biomeBlacklist)));

            list.add(Translator.translateToLocalFormatted("jei.pumpjack.reservoir.dimensions"));
            list.add(Translator.translateToLocalFormatted("jei.pumpjack.reservoir.dimension_whitelist", Arrays.toString(reservoir.dimensionWhitelist)));
            list.add(Translator.translateToLocalFormatted("jei.pumpjack.reservoir.dimension_blacklist", Arrays.toString(reservoir.dimensionBlacklist)));

            return list;
        }

        if (mouseX > 61 && mouseX < 73 && mouseY > 44 && mouseY < 57) {
            list.add(Translator.translateToLocalFormatted("jei.pumpjack.reservoir.power_tier", reservoir.powerTier));
            list.add(Translator.translateToLocalFormatted("jei.pumpjack.reservoir.power_capacity", numberFormat.format(rftTier.get(reservoir.powerTier).capacity)));
            list.add(Translator.translateToLocalFormatted("jei.pumpjack.reservoir.power_usage", numberFormat.format(rftTier.get(reservoir.powerTier).rft)));

            return list;
        }

        return null;
    }

}
