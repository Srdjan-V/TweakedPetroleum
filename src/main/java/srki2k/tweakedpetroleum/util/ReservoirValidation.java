package srki2k.tweakedpetroleum.util;

import srki2k.tweakedpetroleum.util.errorloggingutil.ErrorLoggingUtil;

import java.util.List;

public class ReservoirValidation {


    public static void validateReservoir(String name, int minSize, int maxSize, int replenishRate, int pumpSpeed, int weight, int powerTier,
                                                 int[] dimBlacklist, int[] dimWhitelist, String[] biomeBlacklist, String[] biomeWhitelist,
                                                 List<String> biomeBlacklistList, List<String> biomeWhitelistList, List<String> errors) {

        if (name.isEmpty()) {
            errors.add("Reservoir name can not be a empty string!");
        }
        if (minSize <= 0) {
            errors.add("Reservoir(" + name + ") minSize has to be at least 1mb!");
        }
        if (maxSize < minSize) {
            errors.add("Reservoir(" + name + ") maxSize can not be smaller than minSize!");
        }
        if (weight < 1) {
            errors.add("Reservoir(" + name + ") weight has to be greater than or equal to 1!");
        }
        if (pumpSpeed <= 0) {
            errors.add("Reservoir(" + name + ") Pump Speed has to be at least 1mb/t");
        }
        if (pumpSpeed < replenishRate) {
            errors.add("Reservoir(" + name + ") Pump Speed can not be smaller than Replenish Rate!");
        }
        if (powerTier < 0) {
            errors.add("Reservoir(" + name + ") powerTier can not be smaller than 0!");
        }

        for (String string : biomeBlacklist) {
            if (string == null || string.isEmpty()) {
                errors.add("Reservoir(" + name + ") String '" + string + "' in biomeBlacklist is either Empty or Null");
                continue;
            }

            biomeBlacklistList.add(string);
        }

        for (String string : biomeWhitelist) {
            if (string == null || string.isEmpty()) {
                errors.add("Reservoir(" + name + ") String '" + string + "' in biomeBlacklist is either Empty or Null");
                continue;
            }

            biomeWhitelistList.add(string);
        }

        if (!errors.isEmpty()) {
            ErrorLoggingUtil.getStartupInstance().addErrorToList(errors);
        }

    }

}

