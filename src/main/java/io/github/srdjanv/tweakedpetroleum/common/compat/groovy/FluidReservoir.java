package io.github.srdjanv.tweakedpetroleum.common.compat.groovy;

import com.cleanroommc.groovyscript.api.GroovyLog;
import flaxbeard.immersivepetroleum.api.crafting.PumpjackHandler;
import io.github.srdjanv.tweakedpetroleum.api.crafting.TweakedPumpjackHandler;
import io.github.srdjanv.tweakedpetroleum.api.mixins.ITweakedPetReservoirType;
import io.github.srdjanv.tweakedpetroleum.util.ReservoirValidation;
import net.minecraftforge.fluids.FluidStack;

import java.util.Arrays;
import java.util.function.BiFunction;

public class FluidReservoir extends VirtualizedReservoirRegistry<
        ITweakedPetReservoirType,
        FluidReservoir.FluidReservoirBuilder,
        FluidReservoir.FluidReservoirWrapper> {

    private final BiFunction<PumpjackHandler.ReservoirType, Integer, FluidReservoirWrapper> reservoirTypeWrapperFunction = FluidReservoirWrapper::new;

    @Override protected BiFunction<PumpjackHandler.ReservoirType, Integer, FluidReservoirWrapper> getReservoirTypeWrapperFunction() {
        return reservoirTypeWrapperFunction;
    }

    public FluidReservoir() {
        super("Fluid");
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
            ReservoirValidation.validateReservoir(msg::add, name, fluid, minSize, maxSize, replenishRate, pumpSpeed, weight, powerTier, drainChance.floatValue(),
                    biomeBlacklist == null ? null : biomeBlacklist.toArray(new String[]{}), biomeWhitelist == null ? null : biomeWhitelist.toArray(new String[]{}));

            return !msg.postIfNotEmpty();
        }

        @SuppressWarnings("UnreachableCode") @Override
        public FluidReservoirWrapper register() {
            if (!validate()) return null;
            ITweakedPetReservoirType res = (ITweakedPetReservoirType) new PumpjackHandler.ReservoirType(name, fluid.getFluid().getName(), minSize, maxSize, replenishRate);

            res.setDrainChance(drainChance.floatValue());
            res.setPumpSpeed(pumpSpeed);
            res.setPowerTier(powerTier);
            res.setReservoirContent(TweakedPumpjackHandler.ReservoirContent.LIQUID);
            if (dimBlacklist != null) res.setDimensionBlacklist(dimBlacklist.toIntArray());
            if (dimWhitelist != null) res.setDimensionWhitelist(dimWhitelist.toIntArray());
            if (biomeBlacklist != null) res.setBiomeBlacklist(biomeBlacklist.toArray(new String[]{}));
            if (biomeWhitelist != null) res.setBiomeWhitelist(biomeWhitelist.toArray(new String[]{}));

            FluidReservoirWrapper wrapper = new FluidReservoirWrapper(res, weight);
            add(wrapper);
            return wrapper;
        }

    }

    public class FluidReservoirWrapper extends ReservoirWrapper<ITweakedPetReservoirType, FluidReservoirBuilder> {
        private final ITweakedPetReservoirType reservoirType;

        public FluidReservoirWrapper(ITweakedPetReservoirType reservoirType, int weight) {
            this((PumpjackHandler.ReservoirType) reservoirType, weight);
        }

        public FluidReservoirWrapper(PumpjackHandler.ReservoirType reservoirType, int weight) {
            super(FluidReservoir.this::recipeBuilder, reservoirType, weight);
            this.reservoirType = (ITweakedPetReservoirType) reservoirType;
        }

        @Override
        public ITweakedPetReservoirType getRealReservoirType() {
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
