package srki2k.tweakedpetroleum.util;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiErrorScreen;
import net.minecraftforge.fml.client.CustomModLoadingErrorDisplayException;
import srki2k.tweakedpetroleum.TweakedPetroleum;
import srki2k.tweakedpetroleum.api.crafting.IReservoirType;

import java.util.ArrayList;
import java.util.List;

import static flaxbeard.immersivepetroleum.api.crafting.PumpjackHandler.reservoirList;
import static srki2k.tweakedpetroleum.api.crafting.TweakedPumpjackHandler.rftTier;
import static srki2k.tweakedpetroleum.common.Configs.TPConfig.StartupScriptChecks.*;
import static srki2k.tweakedpetroleum.common.Configs.TPConfig.ImmersivePetroleumOverwrites.*;

public class ErrorLoggingUtil {

    public static class Runtime {
        public static void missingPowerTiersLog() {
            Common.logSetting();

            reservoirList.keySet().
                    stream().
                    map(reservoirType -> (IReservoirType) reservoirType).
                    forEach(tweakedReservoirType -> {
                        if (rftTier.get(tweakedReservoirType.getPowerTier()) == null) {
                            TweakedPetroleum.LOGGER.fatal("Reservoir with the ID (name)" + tweakedReservoirType.getName() + "has no valid Power tier");
                        }
                    });
            throw new Error("Check the Tweaked Petroleum logs");
        }

    }

    public static class Startup {
        private static final List<String> errors = new ArrayList<>();

        public static void setErrors(List<String> error) {
            errors.addAll(error);
        }

        public static void validateScripts() {
            if (scriptsErrorCheck) {
                scriptsErrorCheck();
            }

            if (missingPowerTierCheck) {
                missingPowerTierCheck();
            }

            if (!errors.isEmpty()) {
                Common.logSetting();
                logContentErrors();
                errorScreen();
            }
        }


        private static void scriptsErrorCheck() {
            if (reservoirList.isEmpty()) {
                String error = "No reservoirs are registered";
                errors.add(error);
            }

            if (rftTier.isEmpty()) {
                String error = "No power tiers are registered";
                errors.add(error);
            }

        }
        private static void logContentErrors(){
            errors.forEach(TweakedPetroleum.LOGGER::error);
        }
        private static void missingPowerTierCheck() {
            reservoirList.keySet().
                    stream().
                    map(reservoirType -> (IReservoirType) reservoirType).
                    forEach(tweakedReservoirType -> {
                        if (rftTier.get(tweakedReservoirType.getPowerTier()) == null) {
                            String error = "Reservoir with the ID (name)" + tweakedReservoirType.getName() + "has no valid Power tier";
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
                        drawCenteredString(fontRenderer, error, errorScreen.width / 2, 40 + i * 12);
                    }
                }
            };
        }
        private static void drawCenteredString(FontRenderer fontRendererIn, String text, int x, int y) {
            fontRendererIn.drawStringWithShadow(text, (float) (x - fontRendererIn.getStringWidth(text) / 2), (float) y, 16777215);
        }

    }

    private static class Common {
        private static void logSetting() {
            TweakedPetroleum.LOGGER.info("Immersive Petroleum Overwrites:");
            TweakedPetroleum.LOGGER.info("Disable IP's Reservoir Loading: " + disableIPReservoirLoading);
            TweakedPetroleum.LOGGER.info("Disable IP's Default Pumpjack Capacity and Consumption: " + disableDefaultRFT);
            TweakedPetroleum.LOGGER.info("Disable IP's Pumpjack Zen script: " + disableDefaultRFTZenScriptLoading);

            TweakedPetroleum.LOGGER.info("Startup Script Checks:");
            TweakedPetroleum.LOGGER.info("Do not load with errors in scripts: " + scriptsErrorCheck);
            TweakedPetroleum.LOGGER.info("Do not load with missing power tiers: " + missingPowerTierCheck);

        }

    }

}
