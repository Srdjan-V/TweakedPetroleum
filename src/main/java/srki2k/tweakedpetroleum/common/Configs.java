package srki2k.tweakedpetroleum.common;

import net.minecraftforge.common.config.Config;
import srki2k.tweakedpetroleum.TweakedPetroleum;

public class Configs {

    @Config(modid = TweakedPetroleum.MODID)
    public static class TPConfig {

        @Config.Name("Default Reservoirs")
        public static DefaultReservoirs defaultReservoirs;

        public static class DefaultReservoirs {
            @Config.Comment({"This will Register default IP Reservoirs(Oil, Water, Lava), default=true"})
            @Config.Name("Register Reservoirs")
            @Config.RequiresMcRestart
            public static boolean defaultReservoirs = true;

            @Config.Name("Default Pumpjack Power Tiers")
            public static DefaultPumpjackPowerTiers defaultPumpjackPowerTiers;

            public static class DefaultPumpjackPowerTiers {

                @Config.Comment({"This will set the capacity of the pumpjack, default=16000"})
                @Config.Name("Default capacity")
                @Config.RangeInt(min = 1)
                @Config.RequiresMcRestart
                public static int capacity = 16000;

                @Config.Comment({"This will set the power consumption of the pumpjack, default=1024"})
                @Config.Name("Default consumption")
                @Config.RangeInt(min = 1)
                @Config.RequiresMcRestart
                public static int rft = 1024;

            }
        }
    }

}