package srki2k.tweakedpetroleum.common;

import srki2k.tweakedpetroleum.TweakedPetroleum;
import net.minecraftforge.common.config.Config;

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

            @Config.Comment({"This will set the power tier of the default IP Reservoirs, default=0"})
            @Config.Name("Default Reservoirs PowerTier")
            @Config.RangeInt(min = 0)
            @Config.RequiresMcRestart
            public static int defaultPowerTier = 0;

        }

    }

}