package srki2k.tweakedpetroleum.util;

import net.minecraftforge.fml.common.Loader;

public class Constants {

    private static boolean isAdvancedRocketryLoaded;

    public static boolean getAdvancedRocketryLoaded(){
        return isAdvancedRocketryLoaded;
    }

    public static void init(){
        isAdvancedRocketryLoaded = Loader.isModLoaded("advancedrocketry");
    }
}
