package srki2k.tweakedpetroleum.core;

import zone.rong.mixinbooter.ILateMixinLoader;

import java.util.Collections;
import java.util.List;

public class TweakedPetroleumPlugin implements ILateMixinLoader {

    @Override
    public List<String> getMixinConfigs() {
        return Collections.singletonList("mixins.tweakedpetroleum.json");
    }

}
