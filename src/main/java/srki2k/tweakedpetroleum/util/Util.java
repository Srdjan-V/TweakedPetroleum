package srki2k.tweakedpetroleum.util;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiErrorScreen;
import net.minecraftforge.fml.client.CustomModLoadingErrorDisplayException;
import srki2k.tweakedpetroleum.TweakedPetroleum;

import java.util.ArrayList;
import java.util.List;

import static flaxbeard.immersivepetroleum.api.crafting.PumpjackHandler.reservoirList;
import static srki2k.tweakedpetroleum.api.crafting.TweakedPumpjackHandler.rftTier;

public class Util {

    private static final List<String> errors = new ArrayList<>();

    public static void validateState() {
        if (reservoirList.isEmpty()) {
            String error = "No reservoirs are registered";
            TweakedPetroleum.LOGGER.fatal(error);
            errors.add(error);
        }

        if (rftTier.isEmpty()) {
            String error = "No power tiers are registered";
            TweakedPetroleum.LOGGER.fatal(error);
            errors.add(error);
        }

        if (!errors.isEmpty()) {
            errorScreen();
        }

    }

    private static void errorScreen() {
        throw new CustomModLoadingErrorDisplayException() {
            @Override
            public void initGui(GuiErrorScreen errorScreen, FontRenderer fontRenderer) {
            }

            @Override
            public void drawScreen(GuiErrorScreen errorScreen, FontRenderer fontRenderer, int mouseRelX, int mouseRelY, float tickTime) {
                drawCenteredString(fontRenderer, "The following errors were found in your Tweaked Petroleum configs:", errorScreen.width / 2, errorScreen.height / 2 - 12);

                for (int i = 0; i < errors.size(); i++) {
                    String error = errors.get(i);
                    drawCenteredString(fontRenderer, error, errorScreen.width / 2, errorScreen.height / 2 + i * 12);
                }
            }
        };
    }

    private static void drawCenteredString(FontRenderer fontRendererIn, String text, int x, int y) {
        fontRendererIn.drawStringWithShadow(text, (float) (x - fontRendererIn.getStringWidth(text) / 2), (float) y, 16777215);
    }


}
