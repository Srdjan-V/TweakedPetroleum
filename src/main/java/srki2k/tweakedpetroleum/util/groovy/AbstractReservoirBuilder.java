package srki2k.tweakedpetroleum.util.groovy;

import com.cleanroommc.groovyscript.api.IIngredient;
import com.cleanroommc.groovyscript.helper.recipe.IRecipeBuilder;
import srki2k.tweakedlib.api.powertier.PowerTier;

import java.util.List;

@SuppressWarnings("all")
public abstract class AbstractReservoirBuilder<T extends AbstractReservoirBuilder<?>> implements IRecipeBuilder<GroovyReservoirWrapper> {

    protected String name;
    protected IIngredient ingredient;
    protected int minSize;
    protected int maxSize;
    protected int replenishRate;
    protected int pumpSpeed;
    protected int weight;
    protected Float drainChance = Float.NaN;
    protected int powerTier;
    protected List<Integer> dimBlacklist;
    protected List<Integer> dimWhitelist;
    protected List<String> biomeBlacklist;
    protected List<String> biomeWhitelist;


    public T name(String name) {
        this.name = name;
        return (T) this;
    }

    public T minSize(int minSize) {
        this.minSize = minSize;
        return (T) this;
    }

    public T maxSize(int maxSize) {
        this.maxSize = maxSize;
        return (T) this;
    }

    public T replenishRate(int replenishRate) {
        this.replenishRate = replenishRate;
        return (T) this;
    }

    public T pumpSpeed(int pumpSpeed) {
        this.pumpSpeed = pumpSpeed;
        return (T) this;
    }

    public T weight(int weight) {
        this.weight = weight;
        return (T) this;
    }

    public T powerTier(int powerTier) {
        this.powerTier = powerTier;
        return (T) this;
    }

    public T powerTier(PowerTier powerTier) {
        this.powerTier = powerTier.hashCode();
        return (T) this;
    }

    public T drainChance(float drainChance) {
        this.drainChance = drainChance;
        return (T) this;
    }

    public T dimBlacklist(List<Integer> dimBlacklist) {
        this.dimBlacklist = dimBlacklist;
        return (T) this;
    }

    public T dimWhitelist(List<Integer> dimWhitelist) {
        this.dimWhitelist = dimWhitelist;
        return (T) this;
    }

    public T biomeBlacklist(List<String> biomeBlacklist) {
        this.biomeBlacklist = biomeBlacklist;
        return (T) this;
    }

    public T biomeWhitelist(List<String> biomeWhitelist) {
        this.biomeWhitelist = biomeWhitelist;
        return (T) this;
    }

}

