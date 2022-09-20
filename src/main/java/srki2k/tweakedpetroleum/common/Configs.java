package srki2k.tweakedpetroleum.common;

import srki2k.tweakedpetroleum.TweakedPetroleum;
import net.minecraftforge.common.config.Config;

public class Configs {

    @Config(modid = TweakedPetroleum.MODID)
    public static class TPConfig {

        @Config.Name("Logging")
        public static Logging logging;

        public static class Logging {

            @Config.Comment({"This will disable all Logging, default=false"})
            @Config.Name("Disable Logging")
            @Config.RequiresMcRestart
            public static boolean disableLogging = false;

            @Config.Comment({"This will check if you have 0 registered reservoirs, default=true"})
            @Config.Name("Log missing reservoirs")
            @Config.RequiresMcRestart
            public static boolean logMissingContent = true;

            @Config.Comment({"This will log missing power tiers on startup",
                            "it will still crash if you try to use a non existent power tier and generate a report, even if this setting is enabled",
                    "recommend while developing a pack but not in production, default=false"})
            @Config.Name("Log Missing PowerTiers on startup")
            @Config.RequiresMcRestart
            public static boolean logMissingPowerTier = false;

            @Config.Comment({"Log errors to the player once he joins the game, default=true"})
            @Config.Name("Log errors to players")
            @Config.RequiresMcRestart
            public static boolean logToPlayers = true;

        }

    }

}