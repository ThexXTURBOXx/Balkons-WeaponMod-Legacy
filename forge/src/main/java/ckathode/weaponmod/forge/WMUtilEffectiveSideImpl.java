package ckathode.weaponmod.forge;

import ckathode.weaponmod.WMUtil;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.util.thread.EffectiveSide;

public class WMUtilEffectiveSideImpl {

    public static WMUtil.EffectiveSide get() {
        if (EffectiveSide.get() == LogicalSide.SERVER)
            return WMUtil.EffectiveSide.SERVER;
        return WMUtil.EffectiveSide.CLIENT;
    }

}
