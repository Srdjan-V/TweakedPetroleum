package srki2k.tweakedpetroleum.common;

import srki2k.tweakedpetroleum.TweakedPetroleum;
import net.minecraftforge.common.config.Config;

public class Configs {

    @Config(modid = TweakedPetroleum.MODID)
    public static class TPConfig {

        @Config.Comment({"This will perform checks at the end of loading Tweaked Petroleum and crash and generate a report",
                "These setting are meant to be used by mod-pack devs, and they should be turned off in production"})
        @Config.Name("Startup Script Checks")
        public static StartupScriptChecks startupScriptChecks;

        public static class StartupScriptChecks {

            @Config.Comment({"This will disable all checks, default=false"})
            @Config.Name("Disable all checks")
            @Config.RequiresMcRestart
            public static boolean disableAllChecks = false;

            @Config.Comment({"This exists to catch syntactical errors with ZenScript (sadly this will catch errors from other mods), default=true"})
            @Config.Name("Don't load with ZenScript Syntax errors")
            @Config.RequiresMcRestart
            public static boolean zenScriptErrorSyntaxCheck = true;

            @Config.Comment({"If you have not made any syntactical error this will log errors made with the passed values, default=true"})
            @Config.Name("Don't load with ZenScript errors")
            @Config.RequiresMcRestart
            public static boolean zenScriptErrorsCheck = true;

            @Config.Comment({"This will check if you have 0 registered reservoirs/power tiers scripts on startup, default=true"})
            @Config.Name("Don't load with no reservoirs/power tiers")
            @Config.RequiresMcRestart
            public static boolean missingContentCheck = true;

            @Config.Comment({"This will check for missing power tiers on startup, recommend while developing a pack but not in production, default=false"})
            @Config.Name("Do not load with missing power tiers")
            @Config.RequiresMcRestart
            public static boolean missingPowerTierCheck = false;

        }

    }

}