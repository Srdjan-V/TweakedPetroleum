package io.github.srdjanv.tweakedpetroleum.common.compat.groovy;

import io.github.srdjanv.tweakedlib.common.Constants;
import io.github.srdjanv.tweakedlib.common.compat.groovyscript.GroovyScriptRegistry;
import io.github.srdjanv.tweakedpetroleum.util.TweakedPetroleumInitializer;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class GroovyCompat implements TweakedPetroleumInitializer {

    @Override public boolean shouldRun() {
        return Constants.isGroovyScriptLoaded();
    }

    @Override public void preInit(FMLPreInitializationEvent event) {
        GroovyScriptRegistry.getRegistry().addRegistry(new FluidReservoir());
    }

}
