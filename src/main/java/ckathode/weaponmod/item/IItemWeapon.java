package ckathode.weaponmod.item;

import java.util.Random;
import java.util.UUID;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public interface IItemWeapon {
    UUID WEAPON_MODIFIER = UUID.fromString("D4DA0FD3-7324-40D2-AAC8-B25FF392E157");

    boolean onLeftClickEntity(final ItemStack p0, final EntityPlayer p1, final Entity p2);

    UUID getUUID();

    UUID getUUIDSpeed();

    UUID getUUIDDamage();

    Random getItemRand();

    MeleeComponent getMeleeComponent();

    RangedComponent getRangedComponent();
}
