package ckathode.weaponmod;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageType;

import static ckathode.weaponmod.BalkonsWeaponMod.MOD_ID;

public final class WMDamageSources {

    public static final ResourceKey<DamageType> BATTLEAXE = ResourceKey.create(
            Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath(MOD_ID, "battleaxe"));
    public static final ResourceKey<DamageType> WEAPON = ResourceKey.create(
            Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath(MOD_ID, "weapon"));

}
