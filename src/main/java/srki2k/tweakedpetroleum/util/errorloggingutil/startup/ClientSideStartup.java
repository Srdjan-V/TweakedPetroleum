package srki2k.tweakedpetroleum.util.errorloggingutil.startup;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiErrorScreen;
import net.minecraftforge.fml.client.CustomModLoadingErrorDisplayException;

public class ClientSideStartup extends StartupErrorLoggingUtil {
    public void validateScripts() {
        super.validateScripts();

        if (!errors.isEmpty()){
            errorScreen();
        }

        markStartupInstanceNull();
    }

    private void errorScreen() {
        throw new CustomModLoadingErrorDisplayException() {
            @Override
            public void initGui(GuiErrorScreen errorScreen, FontRenderer fontRenderer) {
            }

            @Override
            public void drawScreen(GuiErrorScreen errorScreen, FontRenderer fontRenderer, int mouseRelX, int mouseRelY, float tickTime) {
                drawCenteredString(fontRenderer, "The following errors were found in your Tweaked Petroleum configs:", errorScreen.width / 2, 12);
                drawCenteredString(fontRenderer, "You can also visit the logs for a list of errors", errorScreen.width / 2, 24);

                for (int i = 0; i < errors.size(); i++) {
                    String error = errors.get(i);
                    drawCenteredString(fontRenderer, error, errorScreen.width / 2, 40 + i * 12);
                }
            }
        };
    }

    private void drawCenteredString(FontRenderer fontRendererIn, String text, int x, int y) {
        fontRendererIn.drawStringWithShadow(text, (float) (x - fontRendererIn.getStringWidth(text) / 2), (float) y, 16777215);
    }

}
