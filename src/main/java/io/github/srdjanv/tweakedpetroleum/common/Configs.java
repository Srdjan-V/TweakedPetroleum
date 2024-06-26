package io.github.srdjanv.tweakedpetroleum.common;

import io.github.srdjanv.tweakedpetroleum.TweakedPetroleum;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.Comment;
import net.minecraftforge.common.config.Config.Name;
import net.minecraftforge.common.config.Config.RangeInt;
import net.minecraftforge.common.config.Config.RequiresMcRestart;

public class Configs {

    @Config(modid = TweakedPetroleum.MODID)
    public static class TPConfig {

        @Name("Default Reservoirs")
        public static DefaultReservoirs defaultReservoirs;

        public static class DefaultReservoirs {
            @Comment({"This will enable reservoir registration from IP's configs, default=true"})
            @Name("Register Reservoirs From Config")
            @RequiresMcRestart
            public static boolean enableIPConfigReservoirRegistration = true;

            @Name("Default Pumpjack Power Tiers")
            public static DefaultPumpjackPowerTiers defaultPumpjackPowerTiers;

            public static class DefaultPumpjackPowerTiers {

                @Comment({"This will set the capacity of the pumpjack, default=16000"})
                @Name("Default capacity")
                @RangeInt(min = 1)
                @RequiresMcRestart
                public static int capacity = 16000;

                @Comment({"This will set the power consumption of the pumpjack, default=1024"})
                @Name("Default consumption")
                @RangeInt(min = 1)
                @RequiresMcRestart
                public static int rft = 1024;

                @Comment({"This will set the pump speed of the pumpjack, default=25"})
                @Name("Default pump speed")
                @RangeInt(min = 1)
                @RequiresMcRestart
                public static int pump_speed = 25;
            }
        }

        @Name("JEI Config")
        public static HEIConfig heiConfig;

        public static class HEIConfig {

            @Name("Draw PowerTier")
            public static boolean drawPowerTier = true;

            @Name("Draw SpawnWeight")
            public static boolean drawSpawnWeight = true;

        }
    }

}