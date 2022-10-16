package srki2k.tweakedpetroleum.util;

import crafttweaker.CraftTweakerAPI;

import java.util.List;

public class ReservoirValidation {

    public static void validateReservoir(String name, int minSize, int maxSize, int replenishRate, int pumpSpeed, int weight, int powerTier, float drainChance,
                                         String[] biomeBlacklist, String[] biomeWhitelist,
                                         List<String> biomeBlacklistList, List<String> biomeWhitelistList) {

        if (drainChance < 0 || 1 < drainChance) {
            CraftTweakerAPI.logError("Reservoir drainChance must be between 0 and 1!");
        }

        validateReservoir(name, minSize, maxSize, replenishRate, pumpSpeed, weight, powerTier,
                biomeBlacklist, biomeWhitelist,
                biomeBlacklistList, biomeWhitelistList);

    }

    public static void validateReservoir(String name, int minSize, int maxSize, int replenishRate, int pumpSpeed, int weight, int powerTier,
                                         String[] biomeBlacklist, String[] biomeWhitelist,
                                         List<String> biomeBlacklistList, List<String> biomeWhitelistList) {

        if (name.isEmpty()) {
            CraftTweakerAPI.logError("Reservoir name can not be a empty string!");
        }
        if (minSize <= 0) {
            CraftTweakerAPI.logError("Reservoir(" + name + ") minSize has to be at least 1mb!");
        }
        if (maxSize < minSize) {
            CraftTweakerAPI.logError("Reservoir(" + name + ":) maxSize can not be smaller than minSize!");
        }
        if (weight < 1) {
            CraftTweakerAPI.logError("Reservoir(" + name + ") weight has to be greater than or equal to 1!");
        }
        if (pumpSpeed <= 0) {
            CraftTweakerAPI.logError("Reservoir(" + name + ") Pump Speed has to be at least 1mb/t");
        }
        if (pumpSpeed < replenishRate) {
            CraftTweakerAPI.logError("Reservoir(" + name + ") Pump Speed can not be smaller than Replenish Rate!");
        }
        if (powerTier < 0) {
            CraftTweakerAPI.logError("Reservoir(" + name + ") powerTier can not be smaller than 0!");
        }

        if (biomeBlacklist != null) {
            for (String string : biomeBlacklist) {
                if (string == null || string.isEmpty()) {
                    CraftTweakerAPI.logError("Reservoir(" + name + ") String '" + string + "' in biomeBlacklist is either Empty or Null");
                    continue;
                }

                biomeBlacklistList.add(string);
            }
        }

        if (biomeWhitelist != null) {
            for (String string : biomeWhitelist) {
                if (string == null || string.isEmpty()) {
                    CraftTweakerAPI.logError("Reservoir(" + name + ") String '" + string + "' in biomeWhitelist is either Empty or Null");
                    continue;
                }

                biomeWhitelistList.add(string);
            }
        }

    }

}

