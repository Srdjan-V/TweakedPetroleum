package srki2k.tweakedpetroleum.common.compat.groovyscript;

import com.cleanroommc.groovyscript.compat.mods.ModPropertyContainer;
import com.cleanroommc.groovyscript.compat.mods.ModSupport;
import com.cleanroommc.groovyscript.registry.VirtualizedRegistry;
import srki2k.tweakedlib.util.groovyscript.GroovyScriptModSupportContainerWrapper;
import srki2k.tweakedpetroleum.TweakedPetroleum;

public final class GroovyScriptCompat extends ModPropertyContainer {

    public static void registerTweakedPetroleumAddon(VirtualizedRegistry<?> registry) {
        if (modSupportContainer == null) {
            TweakedPetroleum.LOGGER.info("Groovy Script modSupport container is not initialized");
            return;
        }

        TweakedPetroleum.LOGGER.info("Adding Groovy Script sub Commands {}", registry.getAliases());
        modSupportContainer.get().addRegistry(registry);
    }

    private static ModSupport.Container<GroovyScriptCompat> modSupportContainer;

    public static void init() {
        modSupportContainer = GroovyScriptModSupportContainerWrapper.registerGroovyContainer(TweakedPetroleum.MODID, "TweakedPetroleum", GroovyScriptCompat::new);
    }

    private GroovyScriptCompat() {
        addRegistry(TweakedGroovyReservoir.init());
    }
}
