package srki2k.tweakedpetroleum.common;

import srki2k.tweakedpetroleum.TweakedPetroleum;
import net.minecraftforge.common.config.Config;

public class Configs {

    @Config(modid = TweakedPetroleum.MODID)
    @Config.RequiresMcRestart
    public static class TPConfig {

        @Config.Name("Immersive Petroleum Overwrites")
        public static ImmersivePetroleumOverwrites immersivePetroleumOverwrites;
        public static class ImmersivePetroleumOverwrites {

            @Config.Comment({"Disable Immersive Petroleum loading reservoirs from IP's config, default=true"})
            @Config.Name("Disable IP's Reservoir Loading")
            @Config.RequiresMcRestart
            public static boolean disableIPReservoirLoading = true;

            @Config.Comment({"Setting this to false will set the default power capacity to 16000 RF and the consumption set in IP's configs (default 1024 RF/t)",
                    "(This will be power tier 0, ti can be overridden with ZenScript 'registerPowerUsage()') default=true"})
            @Config.Name("Disable IP's Default Pumpjack Capacity and Consumption")
            @Config.RequiresMcRestart
            public static boolean disableDefaultRFT = true;
        }


    }

}