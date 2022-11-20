package srki2k.tweakedpetroleum.common.compat.groovyscript;

import com.cleanroommc.groovyscript.api.GroovyBlacklist;
import com.cleanroommc.groovyscript.api.GroovyLog;
import flaxbeard.immersivepetroleum.api.crafting.PumpjackHandler;
import net.minecraftforge.fluids.FluidStack;
import srki2k.tweakedpetroleum.api.crafting.TweakedPumpjackHandler;
import srki2k.tweakedpetroleum.api.ihelpers.IReservoirType;
import srki2k.tweakedpetroleum.util.groovy.GroovyFluidReservoirWrapper;
import srki2k.tweakedpetroleum.util.groovy.GroovyReservoirValidator;
import srki2k.tweakedpetroleum.util.groovy.abstractclass.AbstractReservoirBuilder;
import srki2k.tweakedpetroleum.util.groovy.abstractclass.AbstractVirtualizedReservoirRegistry;

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

    @GroovyBlacklist
    public static TweakedGroovyReservoir getInstance() {
        return instance;
    }

    @Override
    public FluidReservoirBuilder recipeBuilder() {
        return new FluidReservoirBuilder();
    }

    public static class FluidReservoirBuilder extends AbstractReservoirBuilder<FluidReservoirBuilder> {
        protected FluidStack fluid;
        public FluidReservoirBuilder fluid(FluidStack fluid) {
            this.fluid = fluid;
            return this;
        }

        @Override
        public boolean validate() {
            GroovyLog.Msg msg = GroovyLog.msg("Error adding custom fluid reservoir").error();
            GroovyReservoirValidator.validateFluidGroovyReservoir(msg, name, fluid, minSize, maxSize, replenishRate, pumpSpeed, weight, powerTier, drainChance,
                    dimBlacklist, dimWhitelist, biomeBlacklist, biomeWhitelist);

            return !msg.postIfNotEmpty();
        }

        @Override
        public GroovyFluidReservoirWrapper register() {
            if (validate()) {
                IReservoirType res = (IReservoirType) new PumpjackHandler.ReservoirType(name, fluid.getFluid().getName(), minSize, maxSize, replenishRate);
                res.setReservoirContent(TweakedPumpjackHandler.ReservoirContent.LIQUID);

                commonRegister(res);

                GroovyFluidReservoirWrapper groovyReservoirWrapper = new GroovyFluidReservoirWrapper(res, weight);
                getInstance().add(groovyReservoirWrapper.getInnerReservoirWrapper());
                return groovyReservoirWrapper;
            }

            return null;
        }

    }
}
