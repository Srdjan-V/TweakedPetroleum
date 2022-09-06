package srki2k.tweakedpetroleum.util.errorloggingutil.startup;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiErrorScreen;
import net.minecraftforge.fml.client.CustomModLoadingErrorDisplayException;

import static srki2k.tweakedpetroleum.common.Configs.TPConfig.StartupScriptChecks.zenScriptErrorSyntaxCheck;

public final class ClientSideStartup extends StartupErrorLoggingUtil {

    @Override
    protected void customErrorImplementation() {
        throw new CustomModLoadingErrorDisplayException() {
            @Override
            public void initGui(GuiErrorScreen errorScreen, FontRenderer fontRenderer) {
            }

            @Override
            public void drawScreen(GuiErrorScreen errorScreen, FontRenderer fontRenderer, int mouseRelX, int mouseRelY, float tickTime) {
                int j = 12;
                errorScreen.drawCenteredString(fontRenderer, "The following errors were found in your Tweaked Petroleum configs", errorScreen.width / 2, j, 16777215);
                errorScreen.drawCenteredString(fontRenderer, "You can also visit the logs for a list of errors", errorScreen.width / 2, j += 12, 16777215);

                if (zenScriptErrorSyntaxCheck) {
                    errorScreen.drawCenteredString(fontRenderer, "These errors may contain Zen script errors from other mods", errorScreen.width / 2, j += 12, 16777215);
                }
                j += 16;

                for (int i = 0; i < errors.size(); i++) {
                    errorScreen.drawCenteredString(fontRenderer, errors.get(i), errorScreen.width / 2, j + i * 12, 16777215);
                }
            }
        };
    }

}
