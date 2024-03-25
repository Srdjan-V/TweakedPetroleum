package io.github.srdjanv.tweakedpetroleum.util;

import io.github.srdjanv.tweakedlib.api.integration.IInitializer;
import io.github.srdjanv.tweakedpetroleum.TweakedPetroleum;

public interface TweakedPetroleumInitializer extends IInitializer {
    default String getModID() {
        return TweakedPetroleum.MODID;
    }
}
