package srki2k.tweakedpetroleum.common.compat.groovyscript;

import com.cleanroommc.groovyscript.api.GroovyBlacklist;
import com.cleanroommc.groovyscript.api.GroovyLog;
import com.cleanroommc.groovyscript.api.IIngredient;
import com.cleanroommc.groovyscript.helper.ingredient.IngredientHelper;
import com.cleanroommc.groovyscript.registry.VirtualizedRegistry;
import flaxbeard.immersivepetroleum.api.crafting.PumpjackHandler;
import groovyjarjarantlr4.v4.runtime.misc.Nullable;
import net.minecraftforge.fluids.FluidStack;
import srki2k.tweakedpetroleum.api.crafting.TweakedPumpjackHandler;
import srki2k.tweakedpetroleum.api.ihelpers.IReservoirType;
import srki2k.tweakedpetroleum.util.groovy.AbstractReservoirBuilder;
import srki2k.tweakedpetroleum.util.groovy.GroovyReservoirValidator;
import srki2k.tweakedpetroleum.util.groovy.GroovyReservoirWrapper;

import java.util.Map;

public class TweakedGroovyReservoir extends VirtualizedRegistry<GroovyReservoirWrapper> {

    private static TweakedGroovyReservoir instance;


    @GroovyBlacklist
    static TweakedGroovyReservoir init() {
        return instance = new TweakedGroovyReservoir();
    }

    public TweakedGroovyReservoir() {
        super("FluidReservoir", "fluidReservoir");
    }

    @Override
    @GroovyBlacklist
    public void onReload() {
        removeScripted().forEach((recipe) -> PumpjackHandler.reservoirList.remove(recipe.getReservoirType()));
        restoreFromBackup().forEach((recipe) -> PumpjackHandler.reservoirList.put(recipe.getReservoirType(), recipe.getWeight()));
    }

    public void removeAll() {
        PumpjackHandler.reservoirList.forEach((reservoirType, integer) -> addBackup(new GroovyReservoirWrapper(reservoirType, integer)));
        PumpjackHandler.reservoirList.clear();
    }

    public boolean remove(String name) {
        PumpjackHandler.ReservoirType removedReservoirType = null;
        for (Map.Entry<PumpjackHandler.ReservoirType, Integer> reservoir : PumpjackHandler.reservoirList.entrySet()) {
            if (reservoir.getKey().name.equalsIgnoreCase(name)) {
                addBackup(new GroovyReservoirWrapper(reservoir.getKey(), reservoir.getValue()));
                removedReservoirType = reservoir.getKey();
                break;
            }
        }

        if (removedReservoirType == null) {
            return false;
        }

        PumpjackHandler.reservoirList.remove(removedReservoirType);
        return true;
    }

    public boolean remove(GroovyReservoirWrapper reservoir) {
        if (PumpjackHandler.reservoirList.containsKey(reservoir.getReservoirType())) {
            PumpjackHandler.reservoirList.remove(reservoir.getReservoirType());
            addBackup(reservoir);
            return true;
        }
        return false;
    }

    public void add(GroovyReservoirWrapper reservoir) {
        if (reservoir != null) {
            addScripted(reservoir);
            PumpjackHandler.reservoirList.put(reservoir.getReservoirType(), reservoir.getWeight());
        }
    }

    public FluidReservoirBuilder recipeBuilder() {
        return new FluidReservoirBuilder();
    }

    public static class FluidReservoirBuilder extends AbstractReservoirBuilder<FluidReservoirBuilder> {

        public FluidReservoirBuilder fluid(FluidStack fluid) {
            ingredient = (IIngredient) fluid;
            return this;
        }

        @Override
        public boolean validate() {
            GroovyLog.Msg msg = GroovyLog.msg("Error adding custom fluid reservoir").error();
            GroovyReservoirValidator.validateFluidGroovyReservoir(msg, name, ingredient, minSize, maxSize, replenishRate, pumpSpeed, weight, powerTier, drainChance,
                    dimBlacklist, dimWhitelist, biomeBlacklist, biomeWhitelist);

            return !msg.postIfNotEmpty();
        }

        @Override
        public @Nullable GroovyReservoirWrapper register() {
            if (validate()) {
                IReservoirType res = (IReservoirType) new PumpjackHandler.ReservoirType(name, IngredientHelper.toFluidStack(ingredient).getFluid().getName(), minSize, maxSize, replenishRate);
                res.setReservoirContent(TweakedPumpjackHandler.ReservoirContent.LIQUID);
                res.setPumpSpeed(pumpSpeed);
                res.setPowerTier(powerTier);

                if (dimBlacklist != null) {
                    res.setDimensionBlacklist(dimBlacklist.stream().mapToInt(i -> i).toArray());
                } else {
                    res.setDimensionBlacklist(new int[0]);
                }

                if (dimWhitelist != null) {
                    res.setDimensionWhitelist(dimWhitelist.stream().mapToInt(i -> i).toArray());
                } else {
                    res.setDimensionWhitelist(new int[0]);
                }

                if (biomeBlacklist != null) {
                    res.setBiomeBlacklist(biomeBlacklist.toArray(new String[0]));
                } else {
                    res.setBiomeBlacklist(new String[0]);
                }

                if (biomeWhitelist != null) {
                    res.setBiomeWhitelist(biomeWhitelist.toArray(new String[0]));
                } else {
                    res.setBiomeWhitelist(new String[0]);
                }

                GroovyReservoirWrapper groovyReservoirWrapper = new GroovyReservoirWrapper(res, weight);
                instance.add(groovyReservoirWrapper);
                return groovyReservoirWrapper;
            }

            return null;
        }

    }
}
