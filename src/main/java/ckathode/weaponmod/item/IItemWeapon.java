package ckathode.weaponmod.item;

import java.util.Random;
import java.util.UUID;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public interface IItemWeapon {
    UUID WEAPON_MODIFIER = UUID.fromString("D4DA0FD3-7324-40D2-AAC8-B25FF392E157");

    boolean onLeftClickEntity(ItemStack p0, EntityPlayer p1, Entity p2);

    UUID getUUID();

    UUID getUUIDSpeed();

    UUID getUUIDDamage();

    Random getItemRand();

    MeleeComponent getMeleeComponent();

    RangedComponent getRangedComponent();
}
