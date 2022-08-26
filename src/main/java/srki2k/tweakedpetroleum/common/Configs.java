package srki2k.tweakedpetroleum.common;

import srki2k.tweakedpetroleum.TweakedPetroleum;
import net.minecraftforge.common.config.Config;

public class Configs {

    @Config(modid = TweakedPetroleum.MODID)
    public static class TPConfig {

        @Config.Comment({"This will perform checks at the end of loading Minecraft and crash and generate a report if it finds obvious errors"})
        @Config.Name("Startup Script Checks")
        public static StartupScriptChecks startupScriptChecks;

        public static class StartupScriptChecks {
            @Config.Comment({"This will check if you have missing scripts on startup, default=true"})
            @Config.Name("Do not load with no scripts")
            @Config.RequiresMcRestart
            public static boolean scriptsErrorCheck = true;

            @Config.Comment({"This will check for missing power tiers on startup, recommend while developing a pack but not in production, default=false"})
            @Config.Name("Do not load with missing power tiers")
            @Config.RequiresMcRestart
            public static boolean missingPowerTierCheck = false;

        }

    }

}