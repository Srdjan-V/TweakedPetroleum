package io.github.srdjanv.tweakedpetroleum.common.compat.groovy;

import com.cleanroommc.groovyscript.api.GroovyLog;
import flaxbeard.immersivepetroleum.api.crafting.PumpjackHandler;
import io.github.srdjanv.tweakedpetroleum.api.crafting.TweakedPumpjackHandler;
import io.github.srdjanv.tweakedpetroleum.api.mixins.IReservoirType;
import io.github.srdjanv.tweakedpetroleum.api.mixins.IReservoirTypeGetters;
import io.github.srdjanv.tweakedpetroleum.util.ReservoirValidation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

import java.util.Arrays;
import java.util.function.BiFunction;
import java.util.function.Consumer;

public class FluidReservoir extends VirtualizedReservoirRegistry<
        IReservoirType,
        FluidReservoir.FluidReservoirBuilder,
        FluidReservoir.FluidReservoirWrapper> {

    private final BiFunction<PumpjackHandler.ReservoirType, Integer, FluidReservoirWrapper> reservoirTypeWrapperFunction = FluidReservoirWrapper::new;

    @Override protected BiFunction<PumpjackHandler.ReservoirType, Integer, FluidReservoirWrapper> getReservoirTypeWrapperFunction() {
        return reservoirTypeWrapperFunction;
    }

    public FluidReservoir() {
        super(Arrays.asList("FluidReservoir", "fluidReservoir", "fluidreservoir"));
    }

    @Override public FluidReservoirBuilder recipeBuilder() {
        return new FluidReservoirBuilder();
    }

    public class FluidReservoirBuilder extends ReservoirBuilder<FluidReservoirWrapper> {
        protected FluidStack fluid;

        public FluidReservoirBuilder fluid(FluidStack fluid) {
            this.fluid = fluid;
            return this;
        }

        @Override
        public boolean validate() {
            GroovyLog.Msg msg = GroovyLog.msg("Error adding custom fluid reservoir").error();
            ReservoirValidation.validateReservoir(msg::add, name, fluid, minSize, maxSize, replenishRate, pumpSpeed, weight, powerTier, drainChance,
                    biomeBlacklist.toArray(new String[]{}), biomeWhitelist.toArray(new String[]{}));

            return !msg.postIfNotEmpty();
        }

        @Override
        public FluidReservoirWrapper register() {
            if (validate()) {
                IReservoirType res = (IReservoirType) new PumpjackHandler.ReservoirType(name, fluid.getFluid().getName(), minSize, maxSize, replenishRate);

                res.setPumpSpeed(pumpSpeed);
                res.setPowerTier(powerTier);
                res.setReservoirContent(TweakedPumpjackHandler.ReservoirContent.LIQUID);
                if (dimBlacklist != null) res.setDimensionBlacklist(dimBlacklist.stream().mapToInt(Integer::intValue).toArray());
                if (dimWhitelist != null) res.setDimensionWhitelist(dimWhitelist.stream().mapToInt(Integer::intValue).toArray());
                if (biomeBlacklist != null) res.setBiomeBlacklist(biomeBlacklist.toArray(new String[]{}));
                if (biomeWhitelist != null) res.setBiomeWhitelist(biomeWhitelist.toArray(new String[]{}));
                
                FluidReservoirWrapper wrapper = new FluidReservoirWrapper(res, weight);
                add(wrapper);
                return wrapper;
            }

            return null;
        }

    }

    public class FluidReservoirWrapper extends ReservoirWrapper<IReservoirType, FluidReservoirBuilder> {
        private final IReservoirType reservoirType;

        public FluidReservoirWrapper(IReservoirType reservoirType, int weight) {
            this((PumpjackHandler.ReservoirType) reservoirType, weight);
        }

        public FluidReservoirWrapper(PumpjackHandler.ReservoirType reservoirType, int weight) {
            super(FluidReservoir.this::recipeBuilder, reservoirType, weight);
            this.reservoirType = (IReservoirType) reservoirType;
        }

        @Override
        public IReservoirType getRealReservoirType() {
            return reservoirType;
        }

        @Override public FluidReservoirBuilder toBuilder() {
            remove(this);
            FluidReservoirBuilder builder = super.toBuilder();
            builder.fluid(new FluidStack(getReservoirType().getFluid(), 1000));
            return builder;
        }

    }

}
