package srki2k.tweakedpetroleum.common.compat.hei;

import com.google.common.collect.Lists;
import flaxbeard.immersivepetroleum.api.crafting.PumpjackHandler;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import mezz.jei.util.Translator;
import net.minecraft.world.DimensionType;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import org.lwjgl.input.Keyboard;
import srki2k.tweakedpetroleum.api.crafting.IReservoirType;
import srki2k.tweakedpetroleum.util.Constants;
import zmaster587.advancedRocketry.dimension.DimensionManager;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static flaxbeard.immersivepetroleum.api.crafting.PumpjackHandler.reservoirList;
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

    public FluidStack getReplenishRateFluid() {
        return new FluidStack(reservoirFluid, reservoir.getReplenishRate());
    }

    public int getPumpSpeed() {
        return reservoir.getPumpSpeed();
    }

    public int getMinFluid() {
        return reservoir.getMinSize();
    }

    public int getMaxFluid() {
        return reservoir.getMaxSize();
    }

    public FluidStack getAverageFluid() {
        return new FluidStack(reservoirFluid, (int) (((long) reservoir.getMaxSize() + (long) reservoir.getMinSize()) / 2));
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        ingredients.setOutputs(VanillaTypes.FLUID, Lists.newArrayList(getAverageFluid()));
    }

    private String formatString(String s) {
        return (Character.toUpperCase(s.charAt(0)) + s.substring(1)).replace("_", " ");
    }

    private String detailedDimension(int[] dim) {
        StringBuilder stringBuilder = new StringBuilder();

        int dimLength = dim.length;
        if (dimLength == 0) {
            return stringBuilder.append("[]").toString();
        }

        int elements = 1;
        foundDim:
        for (int id : dim) {
            //Vanilla dims and dims registered with DimensionType.register()
            for (DimensionType dimensionType : DimensionType.values()) {
                if (dimensionType.getId() == id) {
                    stringBuilder.append(formatString(dimensionType.getName())).
                            append(" [").
                            append(id).
                            append("]");

                    if (elements < dimLength) {
                        stringBuilder.append(", ");
                        elements++;
                        continue foundDim;
                    }
                    return stringBuilder.toString();
                }
            }

            //Advanced Rocketry planets
            if (Constants.getAdvancedRocketryLoaded()) {
                DimensionManager dimensionManager = DimensionManager.getInstance();
                Integer[] dims = dimensionManager.getRegisteredDimensions();

                for (Integer integer : dims) {
                    if (integer == id) {
                        stringBuilder.append(formatString(dimensionManager.getDimensionProperties(id).getName())).
                                append(" [").
                                append(id).
                                append("]");

                        if (elements < dimLength) {
                            stringBuilder.append(", ");
                            elements++;
                            continue foundDim;
                        }
                        return stringBuilder.toString();
                    }
                }
            }
        }

        return stringBuilder.append("Wrong dimension id or Missing integration").toString();
    }

    @Override
    public List<String> getTooltipStrings(int mouseX, int mouseY) {

        if (mouseX > 14 && mouseX < 25 && mouseY > 60 && mouseY < 74) {
            List<String> list = new ArrayList<>();

            list.add(formatString(reservoir.getName()));
            list.add(Translator.translateToLocalFormatted("jei.pumpjack.reservoir.max_size", numberFormat.format(reservoir.getMaxSize())));
            list.add(Translator.translateToLocalFormatted("jei.pumpjack.reservoir.min_size", numberFormat.format(reservoir.getMinSize())));

            return list;
        }

        if (mouseX > 37 && mouseX < 50 && mouseY > 60 && mouseY < 74) {
            List<String> list = new ArrayList<>();

            list.add(Translator.translateToLocalFormatted("jei.pumpjack.reservoir.replenishRate", numberFormat.format(reservoir.getReplenishRate())));
            list.add(Translator.translateToLocalFormatted("jei.pumpjack.reservoir.speed", numberFormat.format(reservoir.getPumpSpeed())));

            return list;
        }

        if (mouseX > 61 && mouseX < 74 && mouseY > 60 && mouseY < 74) {
            List<String> list = new ArrayList<>();

            list.add(Translator.translateToLocalFormatted("jei.pumpjack.reservoir.weight", reservoirList.get((PumpjackHandler.ReservoirType) reservoir)));
            list.add("");

            list.add(Translator.translateToLocalFormatted("jei.pumpjack.reservoir.biomes"));
            list.add(Translator.translateToLocalFormatted("jei.pumpjack.reservoir.biome_whitelist", Arrays.toString(reservoir.getBiomeWhitelist())));
            list.add(Translator.translateToLocalFormatted("jei.pumpjack.reservoir.biome_blacklist", Arrays.toString(reservoir.getBiomeBlacklist())));

            list.add(Translator.translateToLocalFormatted("jei.pumpjack.reservoir.dimensions"));
            if (Keyboard.isKeyDown(0x2A)) {
                list.add(Translator.translateToLocalFormatted("jei.pumpjack.reservoir.dimension_whitelist", detailedDimension(reservoir.getDimensionWhitelist())));
                list.add(Translator.translateToLocalFormatted("jei.pumpjack.reservoir.dimension_blacklist", detailedDimension(reservoir.getDimensionBlacklist())));

                return list;
            }

            list.add(Translator.translateToLocalFormatted("jei.pumpjack.reservoir.dimension_whitelist", Arrays.toString(reservoir.getDimensionWhitelist())));
            list.add(Translator.translateToLocalFormatted("jei.pumpjack.reservoir.dimension_blacklist", Arrays.toString(reservoir.getDimensionBlacklist())));


            list.add("");
            list.add(Translator.translateToLocalFormatted("jei.pumpjack.reservoir.lshift"));
            return list;
        }

        if (mouseX > 61 && mouseX < 73 && mouseY > 44 && mouseY < 57) {
            List<String> list = new ArrayList<>();

            list.add(Translator.translateToLocalFormatted("jei.pumpjack.reservoir.power_tier", reservoir.getPowerTier()));
            list.add(Translator.translateToLocalFormatted("jei.pumpjack.reservoir.power_capacity", numberFormat.format(rftTier.get(reservoir.getPowerTier()).capacity)));
            list.add(Translator.translateToLocalFormatted("jei.pumpjack.reservoir.power_usage", numberFormat.format(rftTier.get(reservoir.getPowerTier()).rft)));

            return list;
        }

        return null;
    }

}
