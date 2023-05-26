package io.github.srdjanv.tweakedpetroleum.util;

import crafttweaker.CraftTweakerAPI;
import io.github.srdjanv.tweakedpetroleum.api.crafting.TweakedPumpjackHandler;

public class ReservoirValidation {

    public static boolean validateReservoir(String name, TweakedPumpjackHandler.ReservoirContent contentType, Object content, int minSize, int maxSize, int replenishRate, int pumpSpeed, int weight, int powerTier, float drainChance,
                                            String[] biomeBlacklist, String[] biomeWhitelist) {

        boolean valid = true;
        if (drainChance < 0 || 1 < drainChance) {
            CraftTweakerAPI.logError("Reservoir drainChance must be between 0 and 1!");
            valid = false;
        }

        return valid && validateReservoir(name, contentType, content, minSize, maxSize, replenishRate, pumpSpeed, weight, powerTier,
                biomeBlacklist, biomeWhitelist);
    }

    public static boolean validateReservoir(String name, TweakedPumpjackHandler.ReservoirContent contentType, Object content, int minSize, int maxSize, int replenishRate, int pumpSpeed, int weight, int powerTier,
                                            String[] biomeBlacklist, String[] biomeWhitelist) {

        boolean valid = true;
        if (content == null) {
            if (TweakedPumpjackHandler.ReservoirContent.LIQUID == contentType) {
                CraftTweakerAPI.logError(String.format("Trying to add reservoir: %s with non existent fluid", name));
            } else if (TweakedPumpjackHandler.ReservoirContent.GAS == contentType){
                CraftTweakerAPI.logError(String.format("Trying to add reservoir: %s with non existent gas", name));
            } else {
                CraftTweakerAPI.logError(String.format("Trying to add reservoir: %s with non existent contents", name));
            }
            valid = false;
        }
        if (name.isEmpty()) {
            CraftTweakerAPI.logError("Reservoir name can not be a empty string!");
            valid = false;
        }
        if (minSize <= 0) {
            CraftTweakerAPI.logError(String.format("Reservoir(%s) minSize has to be at least 1mb!", name));
            valid = false;
        }
        if (maxSize < minSize) {
            CraftTweakerAPI.logError(String.format("Reservoir(%s) maxSize can not be smaller than minSize!", name));
            valid = false;
        }
        if (weight < 1) {
            CraftTweakerAPI.logError(String.format("Reservoir(%s) weight has to be greater than or equal to 1!", name));
            valid = false;
        }
        if (pumpSpeed <= 0) {
            CraftTweakerAPI.logError(String.format("Reservoir(%s) Pump Speed has to be at least 1mb/t", name));
            valid = false;
        }
        if (pumpSpeed < replenishRate) {
            CraftTweakerAPI.logError(String.format("Reservoir(%s) Pump Speed can not be smaller than Replenish Rate!", name));
            valid = false;
        }
        if (powerTier < 0) {
            CraftTweakerAPI.logError(String.format("Reservoir(%s) powerTier can not be smaller than 0!", name));
            valid = false;
        }

        if (biomeBlacklist != null) {
            for (String string : biomeBlacklist) {
                if (string == null || string.isEmpty()) {
                    CraftTweakerAPI.logError(String.format("Reservoir(%s) String '%s' in biomeBlacklist is either Empty or Null", name, string));
                    valid = false;
                }
            }
        }

        if (biomeWhitelist != null) {
            for (String string : biomeWhitelist) {
                if (string == null || string.isEmpty()) {
                    CraftTweakerAPI.logError(String.format("Reservoir(%s) String '%s' in biomeWhitelist is either Empty or Null", name, string));
                    valid = false;
                }
            }
        }
        return valid;
    }

}

