package ckathode.weaponmod.item;

import java.util.UUID;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public interface IItemWeapon {

    UUID KNOCKBACK_MODIFIER = UUID.fromString("D4DA0FD3-7324-40D2-AAC8-B25FF392E157");
    UUID REACH_MODIFIER = UUID.fromString("01AB3341-3BBD-4761-ACEF-7F5FA7A7BF91");
    UUID RELOAD_TIME_MODIFIER = UUID.fromString("E9BA7518-6CA4-493F-BB2C-06A8DD08CB2A");
    UUID IGNORE_ARMOUR_MODIFIER = UUID.fromString("7A5DECCB-DC97-4B80-BA8D-815E8FFE58CE");

    boolean onLeftClickEntity(ItemStack itemstack, Player player, Entity entity);

    MeleeComponent getMeleeComponent();

    RangedComponent getRangedComponent();

}
