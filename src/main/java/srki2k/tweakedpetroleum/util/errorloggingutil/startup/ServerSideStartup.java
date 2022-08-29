package srki2k.tweakedpetroleum.util.errorloggingutil.startup;

public final class ServerSideStartup extends StartupErrorLoggingUtil {

    @Override
    protected void customErrorImplementation() {
        throw new Error("Check the logs for Tweaked Petroleum startup errors");
    }
}
