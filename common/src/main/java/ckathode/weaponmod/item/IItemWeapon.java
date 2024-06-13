package ckathode.weaponmod.item;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public interface IItemWeapon {

    boolean onLeftClickEntity(ItemStack p0, Player p1, Entity p2);

    MeleeComponent getMeleeComponent();

    RangedComponent getRangedComponent();

}
