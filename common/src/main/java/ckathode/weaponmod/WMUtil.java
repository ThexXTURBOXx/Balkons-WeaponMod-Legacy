package ckathode.weaponmod;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.util.RandomSource;

public class WMUtil {

    public static final RandomSource RANDOM = RandomSource.createNewThreadLocalInstance();

    public enum EffectiveSide {
        CLIENT, SERVER;

        @ExpectPlatform
        public static EffectiveSide get() {
            throw new AssertionError();
        }
    }

}
