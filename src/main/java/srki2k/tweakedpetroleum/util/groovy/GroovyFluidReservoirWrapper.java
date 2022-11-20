package srki2k.tweakedpetroleum.util.groovy;

import flaxbeard.immersivepetroleum.api.crafting.PumpjackHandler;
import net.minecraftforge.fluids.FluidStack;
import srki2k.tweakedpetroleum.api.ihelpers.IReservoirType;
import srki2k.tweakedpetroleum.util.groovy.abstractclass.AbstractGroovyReservoirWrapper;

public class GroovyFluidReservoirWrapper extends AbstractGroovyReservoirWrapper<GroovyFluidReservoirWrapper> {

    public GroovyFluidReservoirWrapper(IReservoirType reservoirType, int weight) {
        super(reservoirType, weight);
    }

    public GroovyFluidReservoirWrapper(PumpjackHandler.ReservoirType reservoirType, int weight) {
        super(reservoirType, weight);
    }

    public GroovyFluidReservoirWrapper setFluid(FluidStack fluidStack) {
        GroovyReservoirValidator.validateFluidStack(groovyLogMsg, innerReservoirWrapper.getReservoirType().name, fluidStack);
        if (groovyLogMsg.hasSubMessages()) {
            return this;
        }

        getMutableObject().getIReservoirType().setFluid(fluidStack.getFluid());
        return this;
    }

}
