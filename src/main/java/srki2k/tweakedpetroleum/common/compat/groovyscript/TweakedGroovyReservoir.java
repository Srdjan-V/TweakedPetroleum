package srki2k.tweakedpetroleum.common.compat.groovyscript;

import com.cleanroommc.groovyscript.api.GroovyBlacklist;
import com.cleanroommc.groovyscript.api.GroovyLog;
import com.cleanroommc.groovyscript.api.IIngredient;
import com.cleanroommc.groovyscript.helper.ingredient.IngredientHelper;
import flaxbeard.immersivepetroleum.api.crafting.PumpjackHandler;
import net.minecraftforge.fluids.FluidStack;
import srki2k.tweakedpetroleum.api.crafting.TweakedPumpjackHandler;
import srki2k.tweakedpetroleum.api.ihelpers.IReservoirType;
import srki2k.tweakedpetroleum.util.groovy.AbstractReservoirBuilder;
import srki2k.tweakedpetroleum.util.groovy.AbstractVirtualizedReservoirRegistry;
import srki2k.tweakedpetroleum.util.groovy.GroovyReservoirValidator;
import srki2k.tweakedpetroleum.util.groovy.GroovyReservoirWrapper;

@SuppressWarnings("unused")
public class TweakedGroovyReservoir extends AbstractVirtualizedReservoirRegistry<TweakedGroovyReservoir, TweakedGroovyReservoir.FluidReservoirBuilder> {

    public TweakedGroovyReservoir() {
        super("FluidReservoir", "fluidReservoir");
    }


    protected static TweakedGroovyReservoir instance;

    @GroovyBlacklist
    static TweakedGroovyReservoir init() {
        return instance = new TweakedGroovyReservoir();
    }

    @Override
    public TweakedGroovyReservoir getInstance() {
        return instance;
    }

    @Override
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
        public GroovyReservoirWrapper register() {
            if (validate()) {
                IReservoirType res = (IReservoirType) new PumpjackHandler.ReservoirType(name, IngredientHelper.toFluidStack(ingredient).getFluid().getName(), minSize, maxSize, replenishRate);
                res.setReservoirContent(TweakedPumpjackHandler.ReservoirContent.LIQUID);

                commonRegister(res);

                GroovyReservoirWrapper groovyReservoirWrapper = new GroovyReservoirWrapper(res, weight);
                instance.add(groovyReservoirWrapper);
                return groovyReservoirWrapper;
            }

            return null;
        }

    }
}
