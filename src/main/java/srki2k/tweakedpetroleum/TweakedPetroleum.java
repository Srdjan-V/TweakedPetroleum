package srki2k.tweakedpetroleum;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import srki2k.tweakedpetroleum.api.crafting.TweakedPumpjackHandler;
import srki2k.tweakedpetroleum.common.Configs;


@Mod(modid = TweakedPetroleum.MODID,
        version = TweakedPetroleum.VERSION,
        name = "Tweaked Petroleum",
        dependencies = "required-after:immersivepetroleum;" +
                "required-after:crafttweaker;")
public class TweakedPetroleum {

    public static final String MODID = "tweakedpetroleum";
    public static final String VERSION = "1.0";

    @SubscribeEvent
    public void onConfigChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event) {
        if (event.getModID().equals(MODID)) {
            ConfigManager.sync(MODID, net.minecraftforge.common.config.Config.Type.INSTANCE);
        }
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        if (!Configs.TPConfig.ImmersivePetroleumOverwrites.disableDefaultRFT) {
            TweakedPumpjackHandler.registerPowerUsage(0, 16000, flaxbeard.immersivepetroleum.common.Config.IPConfig.Extraction.pumpjack_consumption);
        }
    }

}