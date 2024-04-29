package ckathode.weaponmod.item;

import java.util.Random;
import java.util.UUID;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public interface IItemWeapon {
    UUID WEAPON_MODIFIER = UUID.fromString("D4DA0FD3-7324-40D2-AAC8-B25FF392E157");

    boolean onLeftClickEntity(ItemStack p0, Player p1, Entity p2);

    UUID getUUID();

    UUID getUUIDSpeed();

    UUID getUUIDDamage();

    Random getItemRand();

    MeleeComponent getMeleeComponent();

    RangedComponent getRangedComponent();
}
