package srki2k.tweakedpetroleum.common;

import srki2k.tweakedpetroleum.TweakedPetroleum;
import net.minecraftforge.common.config.Config;

public class Configs {

    @Config(modid = TweakedPetroleum.MODID)
    @Config.RequiresMcRestart
    public static class TPConfig {


        @Config.Comment({"I do not recommend changing any of these settings, they can produce unexpected bugs and issues.",
                "I will not try to debug any reports, bug, issues with these setting turned off"})
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


            @Config.Comment({"This will disable the registration of reservoirs through the default IP Zen script method. It is not recommended to disable this, default=true"})
            @Config.Name("Disable IP's Pumpjack Zen script")
            @Config.RequiresMcRestart
            public static boolean disableDefaultRFTZenScriptLoading = true;
        }


        @Config.Comment({"This will perform checks at the end of loading Minecraft and crash and generate a report if it finds obvious errors"})
        @Config.Name("Startup Script Checks")
        public static StartupScriptChecks startupScriptChecks;

        public static class StartupScriptChecks {
            @Config.Comment({"This will check if you for missing scripts on startup, default=true"})
            @Config.Name("Do not load with no scripts")
            @Config.RequiresMcRestart
            public static boolean noScriptsCheck = true;

            @Config.Comment({"This will check for missing power tiers on startup, recommend while developing a pack but not in production, default=false"})
            @Config.Name("Do not load with missing power tiers")
            @Config.RequiresMcRestart
            public static boolean missingPowerTierCheck = false;

        }

    }

}