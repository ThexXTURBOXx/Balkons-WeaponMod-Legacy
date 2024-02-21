package ckathode.weaponmod.item;

import net.minecraft.item.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;
import java.util.*;

public interface IItemWeapon
{
    public static final UUID WEAPON_MODIFIER = UUID.fromString("D4DA0FD3-7324-40D2-AAC8-B25FF392E157");
    
    boolean onLeftClickEntity(final ItemStack p0, final EntityPlayer p1, final Entity p2);
    
    UUID getUUID();
    
    UUID getUUIDSpeed();
    
    UUID getUUIDDamage();
    
    Random getItemRand();
    
    MeleeComponent getMeleeComponent();
    
    RangedComponent getRangedComponent();
}
