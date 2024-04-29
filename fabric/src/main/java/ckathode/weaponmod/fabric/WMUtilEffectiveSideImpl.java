package ckathode.weaponmod.fabric;

import ckathode.weaponmod.WMUtil;
import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.Minecraft;

public class WMUtilEffectiveSideImpl {

    public static WMUtil.EffectiveSide get() {
        if (FabricLoader.getInstance().getEnvironmentType() == EnvType.SERVER)
            return WMUtil.EffectiveSide.SERVER;
        return Minecraft.getInstance().isSameThread()
                ? WMUtil.EffectiveSide.CLIENT
                : WMUtil.EffectiveSide.SERVER;
    }

}
