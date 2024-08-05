package ckathode.weaponmod.item;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public interface IItemWeapon {

    boolean onLeftClickEntity(ItemStack itemstack, Player player, Entity entity);

    MeleeComponent getMeleeComponent();

    RangedComponent getRangedComponent();

}
