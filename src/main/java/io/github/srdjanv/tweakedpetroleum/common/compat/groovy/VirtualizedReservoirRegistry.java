package io.github.srdjanv.tweakedpetroleum.common.compat.groovy;

import com.cleanroommc.groovyscript.api.GroovyBlacklist;
import com.cleanroommc.groovyscript.api.GroovyLog;
import com.cleanroommc.groovyscript.helper.recipe.IRecipeBuilder;
import com.cleanroommc.groovyscript.registry.VirtualizedRegistry;
import flaxbeard.immersivepetroleum.api.crafting.PumpjackHandler;
import io.github.srdjanv.tweakedlib.api.powertier.PowerTier;
import io.github.srdjanv.tweakedpetroleum.api.crafting.TweakedPumpjackHandler;
import io.github.srdjanv.tweakedpetroleum.api.mixins.ITweakedPetReservoirType;
import io.github.srdjanv.tweakedpetroleum.api.mixins.ITweakedPetReservoirTypeGetters;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@SuppressWarnings("rawtypes")
public abstract class VirtualizedReservoirRegistry<
        T extends ITweakedPetReservoirType,
        B extends VirtualizedReservoirRegistry.ReservoirBuilder<W>,
        W extends VirtualizedReservoirRegistry.ReservoirWrapper> extends VirtualizedRegistry<W> {

    public VirtualizedReservoirRegistry(Collection<String> aliases) {
        super(aliases);
    }

    protected abstract BiFunction<PumpjackHandler.ReservoirType, Integer, W> getReservoirTypeWrapperFunction();

    @Override
    @GroovyBlacklist
    public void onReload() {
        removeScripted().forEach((recipe) -> PumpjackHandler.reservoirList.remove(recipe.getReservoirType()));
        restoreFromBackup().forEach((recipe) -> PumpjackHandler.reservoirList.put(recipe.getReservoirType(), recipe.getWeight()));
    }

    public void removeAll() {
        PumpjackHandler.reservoirList.forEach((reservoirType, integer) -> addBackup(getReservoirTypeWrapperFunction().apply(reservoirType, integer)));
        PumpjackHandler.reservoirList.clear();
    }

    public void remove(String name) {
        var res = get(name);
        remove(res);
    }

    public void remove(W reservoir) {
        if (PumpjackHandler.reservoirList.containsKey(reservoir.getReservoirType())) {
            PumpjackHandler.reservoirList.remove(reservoir.getReservoirType());
            addBackup(reservoir);
            GroovyLog.msg("Removed reservoir with name: {}", reservoir.getName()).info().post();
        } else GroovyLog.msg("Error removing custom reservoir with name: {}", reservoir.getName())
                .add("That reservoir does not exist").error().post();
    }

    public List<W> getAll() {
        List<W> reservoirs = PumpjackHandler.reservoirList.entrySet()
                .stream().collect(ArrayList::new,
                        (list, map) -> list.add(getReservoirTypeWrapperFunction().apply(map.getKey(), map.getValue())),
                        ArrayList::addAll);

        if (reservoirs.isEmpty()) {
            GroovyLog.msg("Error getting all custom reservoirs").error()
                    .add("No reservoirs exist").post();
        }

        return reservoirs;
    }

    public W get(String name) {
        for (Map.Entry<PumpjackHandler.ReservoirType, Integer> reservoir : PumpjackHandler.reservoirList.entrySet()) {
            if (reservoir.getKey().name.equalsIgnoreCase(name))
                return getReservoirTypeWrapperFunction().apply(reservoir.getKey(), reservoir.getValue());
        }

        GroovyLog.msg("Error getting custom reservoir with name: {}", name).error().post();
        return null;
    }

    public void add(W wrapper) {
        if (wrapper == null) {
            GroovyLog.msg("Reservoir to add is null").error().post();
            return;
        }

        if (!PumpjackHandler.reservoirList.containsKey(wrapper.getReservoirType())) {
            addScripted(wrapper);
            PumpjackHandler.reservoirList.put(wrapper.getReservoirType(), wrapper.getWeight());
            GroovyLog.msg("Added reservoir {}", wrapper.getName()).info().post();
        } else GroovyLog.msg("Reservoir {}, is already registered remove it first", wrapper.getName()).error().post();
    }

    public abstract B recipeBuilder();

    public abstract static class ReservoirWrapper<T extends ITweakedPetReservoirType, B extends VirtualizedReservoirRegistry.ReservoirBuilder<?>> implements ITweakedPetReservoirTypeGetters {
        private final Supplier<B> builderSupplier;
        private final PumpjackHandler.ReservoirType reservoirType;
        private final int weight;

        public int getWeight() {
            return weight;
        }

        public PumpjackHandler.ReservoirType getReservoirType() {
            return reservoirType;
        }

        public abstract T getRealReservoirType();

        @GroovyBlacklist
        public ReservoirWrapper(Supplier<B> builderSupplier, PumpjackHandler.ReservoirType reservoirType, int weight) {
            this.builderSupplier = builderSupplier;
            this.reservoirType = reservoirType;
            this.weight = weight;
        }

        @Override
        public String getName() {
            return getRealReservoirType().getName();
        }

        @Override public String getStringFluid() {
            return getRealReservoirType().getStringFluid();
        }

        @Override public int getMinSize() {
            return getRealReservoirType().getMinSize();
        }

        @Override public int getMaxSize() {
            return getRealReservoirType().getMaxSize();
        }

        @Override public int getReplenishRate() {
            return getRealReservoirType().getReplenishRate();
        }

        @Override public int getPumpSpeed() {
            return getRealReservoirType().getPumpSpeed();
        }

        @Override public float getDrainChance() {
            return getRealReservoirType().getDrainChance();
        }

        @Override public String[] getBiomeWhitelist() {
            return getRealReservoirType().getBiomeWhitelist().clone();
        }

        @Override public String[] getBiomeBlacklist() {
            return getRealReservoirType().getBiomeBlacklist().clone();
        }

        @Override public TweakedPumpjackHandler.ReservoirContent getReservoirContent() {
            return getRealReservoirType().getReservoirContent();
        }

        @Override
        public int[] getDimensionWhitelist() {
            return getRealReservoirType().getDimensionWhitelist().clone();
        }

        @Override
        public int[] getDimensionBlacklist() {
            return getRealReservoirType().getDimensionBlacklist().clone();
        }

        @Override
        public int getPowerTier() {
            return getRealReservoirType().getPowerTier();
        }

        public B toBuilder() {
            var builder = builderSupplier.get();
            builder.name(getRealReservoirType().getName());
            builder.weight(weight);
            builder.minSize(getMinSize());
            builder.maxSize(getMaxSize());
            builder.replenishRate(getReplenishRate());
            builder.drainChance(getDrainChance());
            builder.pumpSpeed(getPumpSpeed());
            builder.powerTier(getPowerTier());
            builder.dimBlacklist(Arrays.stream(getDimensionBlacklist()).boxed().collect(Collectors.toList()));
            builder.dimWhitelist(Arrays.stream(getDimensionWhitelist()).boxed().collect(Collectors.toList()));
            builder.biomeBlacklist(Arrays.stream(getBiomeBlacklist()).collect(Collectors.toList()));
            builder.biomeWhitelist(Arrays.stream(getBiomeWhitelist()).collect(Collectors.toList()));
            return builder;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ReservoirWrapper that = (ReservoirWrapper) o;
            return Objects.equals(reservoirType, that.getReservoirType());
        }

        @Override
        public int hashCode() {
            return Objects.hash(reservoirType);
        }
    }

    public abstract static class ReservoirBuilder<T> implements IRecipeBuilder<T> {
        protected String name;
        protected int weight;
        protected int minSize;
        protected int maxSize;
        protected int replenishRate;
        protected int pumpSpeed;
        protected float drainChance;
        protected Integer powerTier;
        protected List<Integer> dimBlacklist;
        protected List<Integer> dimWhitelist;
        protected List<String> biomeWhitelist;
        protected List<String> biomeBlacklist;

        public ReservoirBuilder<T> name(String name) {
            this.name = name;
            return this;
        }

        public ReservoirBuilder<T> weight(int weight) {
            this.weight = weight;
            return this;
        }

        public ReservoirBuilder<T> minSize(int size) {
            this.minSize = size;
            return this;
        }

        public ReservoirBuilder<T> maxSize(int size) {
            this.maxSize = size;
            return this;
        }

        public ReservoirBuilder<T> replenishRate(int rate) {
            this.replenishRate = rate;
            return this;
        }

        public ReservoirBuilder<T> pumpSpeed(int speed) {
            this.pumpSpeed = speed;
            return this;
        }

        public ReservoirBuilder<T> powerTier(int powerTier) {
            this.powerTier = powerTier;
            return this;
        }

        public ReservoirBuilder<T> powerTier(PowerTier powerTier) {
            this.powerTier = powerTier.hashCode();
            return this;
        }

        public ReservoirBuilder<T> dimBlacklist(List<Integer> dimBlacklist) {
            this.dimBlacklist = dimBlacklist;
            return this;
        }

        public ReservoirBuilder<T> dimWhitelist(List<Integer> dimWhitelist) {
            this.dimWhitelist = dimWhitelist;
            return this;
        }

        public ReservoirBuilder<T> drainChance(float chance) {
            this.drainChance = chance;
            return this;
        }

        public ReservoirBuilder<T> biomeBlacklist(List<String> blacklist) {
            this.biomeBlacklist = blacklist;
            return this;
        }

        public ReservoirBuilder<T> biomeWhitelist(List<String> whitelist) {
            this.biomeWhitelist = whitelist;
            return this;
        }

    }

}
