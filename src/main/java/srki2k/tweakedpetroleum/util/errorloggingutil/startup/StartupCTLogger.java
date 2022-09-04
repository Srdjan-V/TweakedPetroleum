package srki2k.tweakedpetroleum.util.errorloggingutil.startup;

import crafttweaker.api.player.IPlayer;
import crafttweaker.runtime.ILogger;
import srki2k.tweakedpetroleum.util.errorloggingutil.ErrorLoggingUtil;

public class StartupCTLogger implements ILogger {

    StartupCTLogger(){
    }

    @Override
    public void logCommand(String message) {

    }

    @Override
    public void logInfo(String message) {

    }

    @Override
    public void logWarning(String message) {

    }

    @Override
    public void logError(String message) {

    }

    @Override
    public void logError(String message, Throwable exception) {
        if (exception == null || exception instanceof TPRntimeExeption) {
            ErrorLoggingUtil.getStartupInstance().errors.add(message);
        }
    }

    @Override
    public void logPlayer(IPlayer player) {

    }

    @Override
    public void logDefault(String message) {

    }

    @Override
    public boolean isLogDisabled() {
        return false;
    }

    @Override
    public void setLogDisabled(boolean logDisabled) {

    }

    public static class TPRntimeExeption extends RuntimeException {

    }
}
