package srki2k.tweakedpetroleum.util.errorloggingutil;

import net.minecraftforge.fml.relauncher.FMLLaunchHandler;
import srki2k.tweakedpetroleum.TweakedPetroleum;
import srki2k.tweakedpetroleum.util.errorloggingutil.runtime.RuntimeErrorLoggingUtil;
import srki2k.tweakedpetroleum.util.errorloggingutil.startup.ClientSideStartup;
import srki2k.tweakedpetroleum.util.errorloggingutil.startup.ServerSideStartup;
import srki2k.tweakedpetroleum.util.errorloggingutil.startup.StartupErrorLoggingUtil;

import java.util.List;

import static srki2k.tweakedpetroleum.common.Configs.TPConfig.StartupScriptChecks.*;

public abstract class ErrorLoggingUtil {
    private static StartupErrorLoggingUtil startupErrorLoggingUtil;

    public static StartupErrorLoggingUtil getStartupInstance() {
        if (startupErrorLoggingUtil == null) {
            if (FMLLaunchHandler.side().isClient()) {
                startupErrorLoggingUtil = new ClientSideStartup();
                return startupErrorLoggingUtil;
            }
            startupErrorLoggingUtil = new ServerSideStartup();
        }
        return startupErrorLoggingUtil;
    }

    public static void markStartupInstanceNull() {
        startupErrorLoggingUtil = null;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////

    private static RuntimeErrorLoggingUtil runtimeErrorLoggingUtil;

    public static RuntimeErrorLoggingUtil getRuntimeInstance() {
        if (runtimeErrorLoggingUtil == null) {
            runtimeErrorLoggingUtil = new RuntimeErrorLoggingUtil();
        }
        return runtimeErrorLoggingUtil;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////

    protected ErrorLoggingUtil() {
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////

    protected void logSetting() {
        TweakedPetroleum.LOGGER.info("Startup Script Checks:");
        TweakedPetroleum.LOGGER.info("Disable all checks: " +  disableAllChecks);
        TweakedPetroleum.LOGGER.info("Don't load with ZenScript Syntax errors: " +  zenScriptErrorSyntaxCheck);
        TweakedPetroleum.LOGGER.info("Don't load with ZenScript errors: " +  zenScriptErrorsCheck);
        TweakedPetroleum.LOGGER.info("Don't load with no reservoirs/power tiers: " + missingContentCheck);
        TweakedPetroleum.LOGGER.info("Do not load with missing power tiers: " + missingPowerTierCheck);

    }

    protected void logContentErrors(List<String> errors) {
        logSetting();
        errors.forEach(TweakedPetroleum.LOGGER::fatal);
    }

}
