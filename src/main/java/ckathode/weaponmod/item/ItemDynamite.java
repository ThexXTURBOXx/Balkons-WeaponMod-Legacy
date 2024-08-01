package ckathode.weaponmod.item;

import ckathode.weaponmod.BalkonsWeaponMod;
import ckathode.weaponmod.entity.projectile.EntityDynamite;
import javax.annotation.Nonnull;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

public class ItemDynamite extends WMItem {
    public ItemDynamite(String id) {
        this(BalkonsWeaponMod.MOD_ID, id);
    }

    public ItemDynamite(String modId, String id) {
        super(modId, id, new Properties().maxStackSize(64));
    }

    @Override
    public int getItemEnchantability() {
        return 0;
    }

    @Nonnull
    @Override
    public ActionResult<ItemStack> onItemRightClick(@Nonnull World world, PlayerEntity entityplayer,
                                                    @Nonnull Hand hand) {
        ItemStack itemstack = entityplayer.getHeldItem(hand);
        if (!entityplayer.isCreative()) {
            itemstack.shrink(1);
        }
        world.playSound(null, entityplayer.posX, entityplayer.posY, entityplayer.posZ, SoundEvents.ENTITY_TNT_PRIMED,
                SoundCategory.PLAYERS, 1.0f, 1.0f / (random.nextFloat() * 0.4f + 0.8f));
        if (!world.isRemote) {
            EntityDynamite entitydynamite = new EntityDynamite(world, entityplayer,
                    40 + random.nextInt(10));
            entitydynamite.shoot(entityplayer, entityplayer.rotationPitch, entityplayer.rotationYaw, 0.0f, 0.7f, 4.0f);
            world.addEntity(entitydynamite);
        }
        return new ActionResult<>(ActionResultType.SUCCESS, itemstack);
    }

}
