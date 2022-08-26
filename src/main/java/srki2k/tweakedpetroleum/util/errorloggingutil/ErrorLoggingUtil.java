package srki2k.tweakedpetroleum.util.errorloggingutil;

import srki2k.tweakedpetroleum.TweakedPetroleum;
import srki2k.tweakedpetroleum.util.errorloggingutil.runtime.RuntimeErrorLoggingUtil;
import srki2k.tweakedpetroleum.util.errorloggingutil.startup.ClientSideStartup;
import srki2k.tweakedpetroleum.util.errorloggingutil.startup.ServerSideStartup;
import srki2k.tweakedpetroleum.util.errorloggingutil.startup.StartupErrorLoggingUtil;
import java.util.List;
import static srki2k.tweakedpetroleum.common.Configs.TPConfig.StartupScriptChecks.missingPowerTierCheck;
import static srki2k.tweakedpetroleum.common.Configs.TPConfig.StartupScriptChecks.scriptsErrorCheck;

public abstract class ErrorLoggingUtil {
    private static StartupErrorLoggingUtil startupErrorLoggingUtil;

    public static StartupErrorLoggingUtil getStartupInstance() {
        return startupErrorLoggingUtil;
    }

    public static void makeClientSideStartupInstance() {
        startupErrorLoggingUtil = new ClientSideStartup();
    }

    public static void makeServerSideStartupInstance() {
        startupErrorLoggingUtil = new ServerSideStartup();
    }

    protected static void markStartupInstanceNull() {
        startupErrorLoggingUtil = null;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////

    private static RuntimeErrorLoggingUtil runtimeErrorLoggingUtil;

    public static RuntimeErrorLoggingUtil getRuntimeInstance() {
        if (runtimeErrorLoggingUtil == null){
            runtimeErrorLoggingUtil = new RuntimeErrorLoggingUtil();
        }
        return runtimeErrorLoggingUtil;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////

    protected void logSetting() {
        TweakedPetroleum.LOGGER.info("Startup Script Checks:");
        TweakedPetroleum.LOGGER.info("Do not load with errors in scripts: " + scriptsErrorCheck);
        TweakedPetroleum.LOGGER.info("Do not load with missing power tiers: " + missingPowerTierCheck);

    }

    protected void logContentErrors(List<String> errors) {
        errors.forEach(TweakedPetroleum.LOGGER::error);
    }

}
