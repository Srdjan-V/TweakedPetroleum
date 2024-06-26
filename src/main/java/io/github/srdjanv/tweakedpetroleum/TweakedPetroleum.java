package io.github.srdjanv.tweakedpetroleum;

import io.github.srdjanv.tweakedlib.api.integration.DiscoveryHandler;
import io.github.srdjanv.tweakedpetroleum.util.TweakedPetroleumErrorLogging;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLConstructionEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


@Mod(modid = TweakedPetroleum.MODID,
        version = TweakedPetroleum.VERSION,
        name = "Tweaked Petroleum",
        dependencies = "required-after:immersivepetroleum;" +
                       "after:crafttweaker;" +
                       "after:groovyscript;" +
                       "required-after:tweakedlib@[" + Tags.TWEAKED_LIB_VERSION + ",)")
public class TweakedPetroleum {

    public static final String MODID = "tweakedpetroleum";
    public static final String VERSION = Tags.VERSION;

    public static final Logger LOGGER = LogManager.getLogger(MODID);

    @SubscribeEvent
    public void onConfigChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event) {
        if (event.getModID().equals(MODID)) {
            ConfigManager.sync(MODID, net.minecraftforge.common.config.Config.Type.INSTANCE);
        }
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(this);
        DiscoveryHandler.getInstance().preInit(event);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        DiscoveryHandler.getInstance().init(event);
    }

    @Mod.EventHandler
    public void postInit(final FMLPostInitializationEvent event) {
        DiscoveryHandler.getInstance().postInit(event);
    }

}