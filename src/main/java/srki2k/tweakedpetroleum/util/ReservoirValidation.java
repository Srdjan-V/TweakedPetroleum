package srki2k.tweakedpetroleum.util;

import crafttweaker.CraftTweakerAPI;
import srki2k.tweakedpetroleum.util.errorloggingutil.startup.StartupCTLogger;

import java.util.List;

public class ReservoirValidation {

    public static void validateReservoir(String name, int minSize, int maxSize, int replenishRate, int pumpSpeed, int weight, int powerTier, float drainChance,
                                         String[] biomeBlacklist, String[] biomeWhitelist,
                                         List<String> biomeBlacklistList, List<String> biomeWhitelistList) {

        if (drainChance < 0 || 101 < drainChance) {
            CraftTweakerAPI.logError("Reservoir drainChance must be between 0 and 100!", new StartupCTLogger.TPRntimeExeption());
        }

        validateReservoir(name, minSize, maxSize, replenishRate, pumpSpeed, weight, powerTier,
                biomeBlacklist, biomeWhitelist,
                biomeBlacklistList, biomeWhitelistList);

    }

    public static void validateReservoir(String name, int minSize, int maxSize, int replenishRate, int pumpSpeed, int weight, int powerTier,
                                         String[] biomeBlacklist, String[] biomeWhitelist,
                                         List<String> biomeBlacklistList, List<String> biomeWhitelistList) {

        if (name.isEmpty()) {
            CraftTweakerAPI.logError("Reservoir name can not be a empty string!", new StartupCTLogger.TPRntimeExeption());
        }
        if (minSize <= 0) {
            CraftTweakerAPI.logError("Reservoir(" + name + ") minSize has to be at least 1mb!", new StartupCTLogger.TPRntimeExeption());
        }
        if (maxSize < minSize) {
            CraftTweakerAPI.logError("Reservoir(" + name + ":) maxSize can not be smaller than minSize!", new StartupCTLogger.TPRntimeExeption());
        }
        if (weight < 1) {
            CraftTweakerAPI.logError("Reservoir(" + name + ") weight has to be greater than or equal to 1!", new StartupCTLogger.TPRntimeExeption());
        }
        if (pumpSpeed <= 0) {
            CraftTweakerAPI.logError("Reservoir(" + name + ") Pump Speed has to be at least 1mb/t", new StartupCTLogger.TPRntimeExeption());
        }
        if (pumpSpeed < replenishRate) {
            CraftTweakerAPI.logError("Reservoir(" + name + ") Pump Speed can not be smaller than Replenish Rate!", new StartupCTLogger.TPRntimeExeption());

        }
        if (powerTier < 0) {
            CraftTweakerAPI.logError("Reservoir(" + name + ") powerTier can not be smaller than 0!", new StartupCTLogger.TPRntimeExeption());
        }

        for (String string : biomeBlacklist) {
            if (string == null || string.isEmpty()) {
                CraftTweakerAPI.logError("Reservoir(" + name + ") String '" + string + "' in biomeBlacklist is either Empty or Null", new StartupCTLogger.TPRntimeExeption());
                continue;
            }

            biomeBlacklistList.add(string);
        }

        for (String string : biomeWhitelist) {
            if (string == null || string.isEmpty()) {
                CraftTweakerAPI.logError("Reservoir(" + name + ") String '" + string + "' in biomeBlacklist is either Empty or Null", new StartupCTLogger.TPRntimeExeption());
                continue;
            }

            biomeWhitelistList.add(string);
        }


    }

}

