package srki2k.tweakedpetroleum.common;

import srki2k.tweakedpetroleum.TweakedPetroleum;
import net.minecraftforge.common.config.Config;

public class Configs {

    @Config(modid = TweakedPetroleum.MODID)
    public static class TPConfig {

        @Config.Comment({"This will perform checks at the end of loading Tweaked Petroleum and crash and generate a report",
                "These setting are meant to be used by mod-pack devs, and they should be turned off in production"})
        @Config.Name("Startup Script Checks")
        public static ScriptChecks ScriptChecks;

        public static class ScriptChecks {

            @Config.Comment({"This will disable all Logging, default=false"})
            @Config.Name("Disable Logging")
            @Config.RequiresMcRestart
            public static boolean disableLogging = false;

            @Config.Comment({"This will check if you have 0 registered reservoirs on startup, default=true"})
            @Config.Name("Log missing reservoirs")
            @Config.RequiresMcRestart
            public static boolean logMissingContent = true;

            @Config.Comment({"Log missing reservoirs to players in game, default=true"})
            @Config.Name("Log missing reservoirs to players")
            @Config.RequiresMcRestart
            public static boolean logToPlayers = true;

            @Config.Comment({"This will check for missing power tiers on startup, recommend while developing a pack but not in production, default=false"})
            @Config.Name("Log Missing PowerTiers for on startup")
            @Config.RequiresMcRestart
            public static boolean logMissingPowerTier = false;

        }

    }

}