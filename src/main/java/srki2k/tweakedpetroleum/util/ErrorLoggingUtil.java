package srki2k.tweakedpetroleum.util;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiErrorScreen;
import net.minecraftforge.fml.client.CustomModLoadingErrorDisplayException;
import org.apache.logging.log4j.Level;
import srki2k.tweakedpetroleum.TweakedPetroleum;
import srki2k.tweakedpetroleum.api.crafting.TweakedPumpjackHandler;
import srki2k.tweakedpetroleum.common.Configs;

import java.util.ArrayList;
import java.util.List;

import static flaxbeard.immersivepetroleum.api.crafting.PumpjackHandler.reservoirList;
import static srki2k.tweakedpetroleum.api.crafting.TweakedPumpjackHandler.rftTier;

public class ErrorLoggingUtil {

    public static class Runtime {
        public static void missingRuntimePowerTiersLog() {
            reservoirList.keySet().
                    stream().
                    map(reservoirType -> (TweakedPumpjackHandler.TweakedReservoirType) reservoirType).
                    forEach(tweakedReservoirType -> {
                        if (rftTier.get(tweakedReservoirType.powerTier) == null) {
                            TweakedPetroleum.LOGGER.fatal("Reservoir with the ID (name)" + tweakedReservoirType.name + "has no valid Power tier");
                        }
                    });
            throw new Error("Check the Tweaked Petroleum logs");
        }

    }

    public static class Startup {
        private static final List<String> errors = new ArrayList<>();

        public static void validateScripts() {
            if (Configs.TPConfig.StartupScriptChecks.noScriptsCheck) {
                noScriptsCheck();
            }

            if (Configs.TPConfig.StartupScriptChecks.missingPowerTierCheck) {
                missingPowerTierCheck();
            }

            if (!errors.isEmpty()) {
                logSetting();
                errorScreen();
            }
        }

        private static void logSetting() {
            TweakedPetroleum.LOGGER.info("Disable IP's Reservoir Loading:" + Configs.TPConfig.ImmersivePetroleumOverwrites.disableIPReservoirLoading);
            TweakedPetroleum.LOGGER.info("Disable IP's Default Pumpjack Capacity and Consumption:" + Configs.TPConfig.ImmersivePetroleumOverwrites.disableDefaultRFT);
            TweakedPetroleum.LOGGER.info("Disable IP's Pumpjack Zen script: " + Configs.TPConfig.ImmersivePetroleumOverwrites.disableDefaultRFTZenScriptLoading);
        }

        private static void noScriptsCheck() {
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

        }

        public static void missingPowerTierCheck() {
            reservoirList.keySet().
                    stream().
                    map(reservoirType -> (TweakedPumpjackHandler.TweakedReservoirType) reservoirType).
                    forEach(tweakedReservoirType -> {
                        if (rftTier.get(tweakedReservoirType.powerTier) == null) {
                            String error = "Reservoir with the ID (name)" + tweakedReservoirType.name + "has no valid Power tier";
                            errors.add(error);
                            TweakedPetroleum.LOGGER.fatal(error);
                        }
                    });
        }

        private static void errorScreen() {
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
                        drawCenteredString(fontRenderer, error, errorScreen.width / 2, 24 + i * 12);
                    }
                }
            };
        }

        private static void drawCenteredString(FontRenderer fontRendererIn, String text, int x, int y) {
            fontRendererIn.drawStringWithShadow(text, (float) (x - fontRendererIn.getStringWidth(text) / 2), (float) y, 16777215);
        }

    }
}
