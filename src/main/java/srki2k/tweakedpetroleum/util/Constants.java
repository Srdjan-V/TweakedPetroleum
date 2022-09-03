package srki2k.tweakedpetroleum.util;

import net.minecraftforge.fml.common.Loader;

public class Constants {

    private static boolean isAdvancedRocketryLoaded;
    private static boolean isTweakedPetroleumGasLoaded;

    public static boolean isAdvancedRocketryLoaded(){
        return isAdvancedRocketryLoaded;
    }

    public static boolean isTweakedPetroleumGasLoaded(){
        return isTweakedPetroleumGasLoaded;
    }

    public static void init(){
        isAdvancedRocketryLoaded = Loader.isModLoaded("advancedrocketry");
        isTweakedPetroleumGasLoaded = Loader.isModLoaded("tweakedpetroleumgas");
    }
}
