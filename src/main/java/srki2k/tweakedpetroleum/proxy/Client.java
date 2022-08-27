package srki2k.tweakedpetroleum.proxy;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLLoadCompleteEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import srki2k.tweakedpetroleum.util.errorloggingutil.ErrorLoggingUtil;

public class Client extends Common {

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        ErrorLoggingUtil.makeClientSideStartupInstance();
    }

    @Override
    public void init(FMLInitializationEvent event) {
        super.init(event);
    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {
        super.postInit(event);
    }

    @Override
    public void loadComplete(FMLLoadCompleteEvent event) {
        ErrorLoggingUtil.getStartupInstance().validateScripts();
    }

}
