package srki2k.tweakedpetroleum.util.groovy.abstractclass;

import com.cleanroommc.groovyscript.api.GroovyBlacklist;
import com.cleanroommc.groovyscript.api.GroovyLog;
import flaxbeard.immersivepetroleum.api.crafting.PumpjackHandler;
import net.minecraftforge.fluids.Fluid;
import srki2k.tweakedpetroleum.api.crafting.TweakedPumpjackHandler;
import srki2k.tweakedpetroleum.api.ihelpers.IReservoirGetters;
import srki2k.tweakedpetroleum.api.ihelpers.IReservoirType;
import srki2k.tweakedpetroleum.common.compat.groovyscript.TweakedGroovyReservoir;
import srki2k.tweakedpetroleum.util.groovy.GroovyReservoirValidator;

import java.util.List;
import java.util.Objects;

public abstract class AbstractGroovyReservoirWrapper<T extends AbstractGroovyReservoirWrapper<?>> implements IReservoirGetters {


    protected InnerWrapper innerReservoirWrapper;
    protected InnerWrapper mutableInnerReservoirWrapper;
    protected GroovyLog.Msg groovyLogMsg;

    @GroovyBlacklist
    public AbstractGroovyReservoirWrapper(IReservoirType reservoirType, int weight) {
        this.innerReservoirWrapper = new InnerWrapper(reservoirType, weight);
        groovyLogMsg = GroovyLog.msg("Error editing Reservoir").
                add("This Reservoir is not editable!!!").error();
    }

    @GroovyBlacklist
    public AbstractGroovyReservoirWrapper(PumpjackHandler.ReservoirType reservoirType, int weight) {
        this.innerReservoirWrapper = new InnerWrapper(reservoirType, weight);
        groovyLogMsg = GroovyLog.msg("Error editing Reservoir").error();
    }

    @GroovyBlacklist
    public InnerWrapper getInnerReservoirWrapper() {
        return innerReservoirWrapper;
    }

    public int getWeight() {
        return innerReservoirWrapper.weight;
    }

    @Override
    public String getName() {
        return innerReservoirWrapper.reservoirType.getName();
    }

    @Override
    public String getStringFluid() {
        return innerReservoirWrapper.reservoirType.getStringFluid();
    }

    @Override
    public Fluid getFluid() {
        return innerReservoirWrapper.reservoirType.getFluid();
    }

    @Override
    public int getMinSize() {
        return innerReservoirWrapper.reservoirType.getMinSize();
    }

    @Override
    public int getMaxSize() {
        return innerReservoirWrapper.reservoirType.getMaxSize();
    }

    @Override
    public int getReplenishRate() {
        return innerReservoirWrapper.reservoirType.getReplenishRate();
    }

    @Override
    public int getPowerTier() {
        return innerReservoirWrapper.reservoirType.getPowerTier();
    }

    @Override
    public int getPumpSpeed() {
        return innerReservoirWrapper.reservoirType.getPumpSpeed();
    }

    @Override
    public float getDrainChance() {
        return innerReservoirWrapper.reservoirType.getDrainChance();
    }

    @Override
    public int[] getDimensionWhitelist() {
        return innerReservoirWrapper.reservoirType.getDimensionWhitelist().clone();
    }

    @Override
    public int[] getDimensionBlacklist() {
        return innerReservoirWrapper.reservoirType.getDimensionBlacklist().clone();
    }

    @Override
    public String[] getBiomeWhitelist() {
        return innerReservoirWrapper.reservoirType.getBiomeWhitelist().clone();
    }

    @Override
    public String[] getBiomeBlacklist() {
        return innerReservoirWrapper.reservoirType.getBiomeBlacklist().clone();
    }

    @Override
    public TweakedPumpjackHandler.ReservoirContent getReservoirContent() {
        return innerReservoirWrapper.reservoirType.getReservoirContent();
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void cleanInstance() {
        innerReservoirWrapper = null;
        mutableInnerReservoirWrapper = null;
        groovyLogMsg = null;
    }

    protected InnerWrapper getMutableObject() {
        if (mutableInnerReservoirWrapper == null) {
            mutableInnerReservoirWrapper = innerReservoirWrapper.cloneWrapper();
        }
        return mutableInnerReservoirWrapper;
    }

    public T setName(String name) {
        GroovyReservoirValidator.validateName(groovyLogMsg, name);
        if (groovyLogMsg.hasSubMessages()) {
            return (T) this;
        }

        getMutableObject().reservoirType.setName(name);
        return (T) this;
    }

    public T setMinSize(int minSize) {
        GroovyReservoirValidator.validateMinSize(groovyLogMsg, innerReservoirWrapper.reservoirType.getName(), minSize);
        if (groovyLogMsg.hasSubMessages()) {
            return (T) this;
        }

        getMutableObject().reservoirType.setMinSize(minSize);
        return (T) this;
    }

    public T setMaxSize(int maxSize) {
        //validate at apply
        getMutableObject().reservoirType.setMaxSize(maxSize);
        return (T) this;
    }

    public T setReplenishRate(int replenishRate) {
        //validate at apply
        getMutableObject().reservoirType.setReplenishRate(replenishRate);
        return (T) this;
    }

    public T setPumpSpeed(int pumpSpeed) {
        GroovyReservoirValidator.validatePumpSpeed(groovyLogMsg, innerReservoirWrapper.reservoirType.getName(), pumpSpeed);
        if (groovyLogMsg.hasSubMessages()) {
            return (T) this;
        }

        getMutableObject().reservoirType.setPumpSpeed(pumpSpeed);
        return (T) this;
    }

    public T setDrainChance(float drainChance) {
        GroovyReservoirValidator.validateDrainChance(groovyLogMsg, innerReservoirWrapper.reservoirType.getName(), drainChance);
        if (groovyLogMsg.hasSubMessages()) {
            return (T) this;
        }

        getMutableObject().reservoirType.setDrainChance(drainChance);
        return (T) this;
    }

    public T setWeight(int weight) {
        GroovyReservoirValidator.validateWeight(groovyLogMsg, innerReservoirWrapper.reservoirType.getName(), weight);
        if (groovyLogMsg.hasSubMessages()) {
            return (T) this;
        }

        getMutableObject().weight = weight;
        return (T) this;
    }

    public T setPowerTier(int powerTier) {
        GroovyReservoirValidator.validatePowerTier(groovyLogMsg, innerReservoirWrapper.reservoirType.getName(), powerTier);
        if (groovyLogMsg.hasSubMessages()) {
            return (T) this;
        }

        getMutableObject().reservoirType.setPowerTier(powerTier);
        return (T) this;
    }

    public T setDimensionBlacklist(List<Integer> dimensionBlacklist) {
        GroovyReservoirValidator.validateDimBlacklist(groovyLogMsg, innerReservoirWrapper.reservoirType.getName(), dimensionBlacklist);
        if (groovyLogMsg.hasSubMessages()) {
            return (T) this;
        }

        getMutableObject().reservoirType.setDimensionBlacklist(dimensionBlacklist.stream().mapToInt(i->i).toArray());
        return (T) this;
    }

    public T setDimensionWhitelist(List<Integer> dimensionWhitelist) {
        GroovyReservoirValidator.validateDimWhitelist(groovyLogMsg, innerReservoirWrapper.reservoirType.getName(), dimensionWhitelist);
        if (groovyLogMsg.hasSubMessages()) {
            return (T) this;
        }

        getMutableObject().reservoirType.setDimensionWhitelist(dimensionWhitelist.stream().mapToInt(i->i).toArray());
        return (T) this;
    }

    public T setBiomeBlacklist(List<String> biomeBlacklist) {
        GroovyReservoirValidator.validateBiomeBlocklist(groovyLogMsg, innerReservoirWrapper.reservoirType.getName(), biomeBlacklist);
        if (groovyLogMsg.hasSubMessages()) {
            return (T) this;
        }

        getMutableObject().reservoirType.setBiomeBlacklist(biomeBlacklist.toArray(new String[0]));
        return (T) this;
    }

    public T setBiomeWhitelist(List<String> biomeWhitelist) {
        GroovyReservoirValidator.validateBiomeWhitelist(groovyLogMsg, innerReservoirWrapper.reservoirType.getName(), biomeWhitelist);
        if (groovyLogMsg.hasSubMessages()) {
            return (T) this;
        }

        getMutableObject().reservoirType.setBiomeWhitelist(biomeWhitelist.toArray(new String[0]));
        return (T) this;
    }

    public void apply() {
        if (mutableInnerReservoirWrapper == null) {
            groovyLogMsg.add("No values have need changed before calling apply()").post();
        } else {
            GroovyReservoirValidator.validateMaxSize(groovyLogMsg, mutableInnerReservoirWrapper.reservoirType.getName(),
                    mutableInnerReservoirWrapper.reservoirType.getMinSize(),
                    mutableInnerReservoirWrapper.reservoirType.getMaxSize());

            GroovyReservoirValidator.validateReplenishRate(groovyLogMsg, mutableInnerReservoirWrapper.reservoirType.getName(),
                    mutableInnerReservoirWrapper.reservoirType.getReplenishRate(),
                    mutableInnerReservoirWrapper.reservoirType.getPumpSpeed());
        }

        if (groovyLogMsg.hasSubMessages()) {
            groovyLogMsg.post();
            cleanInstance();
            return;
        }

        TweakedGroovyReservoir.getInstance().remove(innerReservoirWrapper);
        TweakedGroovyReservoir.getInstance().add(mutableInnerReservoirWrapper);
        cleanInstance();
    }


    public static class InnerWrapper {
        private final IReservoirType reservoirType;

        private int weight;

        public InnerWrapper(IReservoirType reservoirType, int weight) {
            this.reservoirType = reservoirType;
            this.weight = weight;
        }

        public InnerWrapper(PumpjackHandler.ReservoirType reservoirType, int weight) {
            this.reservoirType = (IReservoirType) reservoirType;
            this.weight = weight;
        }

        public int getWeight() {
            return weight;
        }

        public PumpjackHandler.ReservoirType getReservoirType() {
            return (PumpjackHandler.ReservoirType) reservoirType;
        }

        public IReservoirType getIReservoirType() {
            return reservoirType;
        }

        private InnerWrapper cloneWrapper() {
            IReservoirType cloneReservoirType = (IReservoirType) new PumpjackHandler.ReservoirType(
                    reservoirType.getName(), reservoirType.getName(),
                    reservoirType.getMinSize(), reservoirType.getMaxSize(),
                    reservoirType.getReplenishRate());

            cloneReservoirType.setReservoirContent(reservoirType.getReservoirContent());
            cloneReservoirType.setDrainChance(reservoirType.getDrainChance());
            cloneReservoirType.setPumpSpeed(reservoirType.getPumpSpeed());
            cloneReservoirType.setPowerTier(reservoirType.getPowerTier());


            cloneReservoirType.setDimensionBlacklist(reservoirType.getDimensionBlacklist());
            cloneReservoirType.setDimensionWhitelist(reservoirType.getDimensionWhitelist());

            cloneReservoirType.setBiomeBlacklist(reservoirType.getBiomeBlacklist());
            cloneReservoirType.setBiomeWhitelist(reservoirType.getBiomeWhitelist());

            return new InnerWrapper(cloneReservoirType, weight);
        }


        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            InnerWrapper that = (InnerWrapper) o;
            return reservoirType.getName().equals(that.reservoirType.getName());
        }

        @Override
        public int hashCode() {
            return Objects.hash(reservoirType.getName());
        }
    }

}
