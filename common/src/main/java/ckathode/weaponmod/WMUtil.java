package ckathode.weaponmod;

import dev.architectury.injectables.annotations.ExpectPlatform;

public class WMUtil {

    public enum EffectiveSide {
        CLIENT, SERVER;

        @ExpectPlatform
        public static EffectiveSide get() {
            throw new AssertionError();
        }
    }

}
