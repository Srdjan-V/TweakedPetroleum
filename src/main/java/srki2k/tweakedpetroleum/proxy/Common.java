package srki2k.tweakedpetroleum.proxy;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLLoadCompleteEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import srki2k.tweakedpetroleum.util.errorloggingutil.ErrorLoggingUtil;

public class Common {

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        ErrorLoggingUtil.makeServerSideStartupInstance();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
    }

    @Mod.EventHandler
    public void loadComplete(FMLLoadCompleteEvent event) {
        ErrorLoggingUtil.getStartupInstance().validateScripts();
    }

}
