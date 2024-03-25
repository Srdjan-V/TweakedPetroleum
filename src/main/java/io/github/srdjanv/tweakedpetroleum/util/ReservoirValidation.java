package io.github.srdjanv.tweakedpetroleum.util;

import crafttweaker.CraftTweakerAPI;
import io.github.srdjanv.tweakedpetroleum.api.crafting.TweakedPumpjackHandler;

import java.util.function.Consumer;

public class ReservoirValidation {

    public static boolean validateReservoir(Consumer<String> errorMassageBuilder, String name, Object content, int minSize, int maxSize, int replenishRate, int pumpSpeed, int weight, int powerTier, float drainChance,
                                            String[] biomeBlacklist, String[] biomeWhitelist) {

        MessageBuilder messageBuilder = new MessageBuilder(errorMassageBuilder);

        messageBuilder.runCheck(drainChance < 0 || 1 < drainChance, "Reservoir drainChance must be between 0 and 1!");
        validateReservoir(messageBuilder, name, content, minSize, maxSize, replenishRate, pumpSpeed, weight, powerTier,
                biomeBlacklist, biomeWhitelist);

        return messageBuilder.isValid();
    }

    public static boolean validateReservoir(Consumer<String> errorMassageBuilder, String name,  Object content, int minSize, int maxSize, int replenishRate, int pumpSpeed, int weight, int powerTier,
                                            String[] biomeBlacklist, String[] biomeWhitelist) {
        MessageBuilder messageBuilder = new MessageBuilder(errorMassageBuilder);
        validateReservoir(messageBuilder, name, content, minSize, maxSize, replenishRate, pumpSpeed, weight, powerTier,
                biomeBlacklist, biomeWhitelist);
        return messageBuilder.isValid();
    }

    public static void validateReservoir(MessageBuilder messageBuilder, String name, Object content, int minSize, int maxSize, int replenishRate, int pumpSpeed, int weight, int powerTier,
                                          String[] biomeBlacklist, String[] biomeWhitelist) {

        messageBuilder.runCheck(name.isEmpty(), "Reservoir name can not be a empty string!");
        messageBuilder.runCheck(content == null, String.format("Trying to add empty reservoir, name: '%s'", name));
        messageBuilder.runCheck(minSize <= 0, String.format("Reservoir(%s) minSize has to be at least 1mb!", name));
        messageBuilder.runCheck(maxSize < minSize, String.format("Reservoir(%s) maxSize can not be smaller than minSize!", name));
        messageBuilder.runCheck(weight < 1, String.format("Reservoir(%s) weight has to be greater than or equal to 1!", name));
        messageBuilder.runCheck(pumpSpeed <= 0, String.format("Reservoir(%s) Pump Speed has to be at least 1mb/t", name));
        messageBuilder.runCheck(pumpSpeed < replenishRate, String.format("Reservoir(%s) Pump Speed can not be smaller than Replenish Rate!", name));
        messageBuilder.runCheck(powerTier < 0, String.format("Reservoir(%s) powerTier can not be smaller than 0!", name));

        if (biomeBlacklist != null)
            for (String string : biomeBlacklist)
                messageBuilder.runCheck(string == null || string.isEmpty(),
                        String.format("Reservoir(%s) String '%s' in biomeBlacklist is either Empty or Null", name, string));

        if (biomeWhitelist != null)
            for (String string : biomeWhitelist)
                messageBuilder.runCheck(string == null || string.isEmpty(),
                        String.format("Reservoir(%s) String '%s' in biomeWhitelist is either Empty or Null", name, string));
    }

    public static class MessageBuilder {
        private final Consumer<String> errorMassageBuilder;
        private boolean valid = true;

        public MessageBuilder(Consumer<String> errorMassageBuilder) {
            this.errorMassageBuilder = errorMassageBuilder;
        }

        public void runCheck(boolean expression, String errorMessage) {
            if (expression) {
                errorMassageBuilder.accept(errorMessage);
                valid = false;
            }
        }

        public boolean isValid() {
            return valid;
        }
    }
}

