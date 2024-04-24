package ckathode.weaponmod.item;

import ckathode.weaponmod.PlayerWeaponData;
import ckathode.weaponmod.entity.projectile.EntityFlail;
import javax.annotation.Nonnull;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;

public class ItemFlail extends ItemMelee {
    private final float flailDamage;

    public ItemFlail(String id, MeleeComponent meleecomponent) {
        super(id, meleecomponent);
        flailDamage = 4.0f + meleecomponent.weaponMaterial.getAttackDamageBonus();
    }

    @Override
    public int getEnchantmentValue() {
        return 0;
    }

    @Override
    public void inventoryTick(@Nonnull ItemStack itemstack, @Nonnull World world,
                              @Nonnull Entity entity, int i, boolean flag) {
        if (!(entity instanceof PlayerEntity)) {
            return;
        }
        PlayerEntity player = (PlayerEntity) entity;
        if (!isThrown(player)) {
            return;
        }
        ItemStack itemstack2 = player.getMainHandItem();
        if (itemstack2.isEmpty() || !((itemstack2.getItem()) instanceof ItemFlail)) {
            setThrown(player, false);
        } else if (itemstack2.getItem() == this) {
            int id = PlayerWeaponData.getFlailEntityId(player);
            if (id != 0) {
                Entity entity2 = world.getEntity(id);
                if (entity2 instanceof EntityFlail) {
                    ((EntityFlail) entity2).setOwner(player);
                    ((EntityFlail) entity2).setThrownItemStack(itemstack);
                }
            }
        }
    }

    @Nonnull
    @Override
    public ActionResult<ItemStack> use(@Nonnull World world,
                                       @Nonnull PlayerEntity entityplayer,
                                       @Nonnull Hand hand) {
        ItemStack itemstack = entityplayer.getItemInHand(hand);
        if (hand != Hand.MAIN_HAND) {
            return new ActionResult<>(ActionResultType.FAIL, itemstack);
        }
        removePreviousFlail(world, entityplayer);
        if (!itemstack.isEmpty()) {
            entityplayer.swing(hand);
            itemstack.hurtAndBreak(1, entityplayer, s -> s.broadcastBreakEvent(Hand.MAIN_HAND));
            throwFlail(itemstack, world, entityplayer);
        }
        return new ActionResult<>(ActionResultType.SUCCESS, itemstack);
    }

    @Override
    public boolean hurtEnemy(@Nonnull ItemStack itemstack, @Nonnull LivingEntity entityliving,
                             @Nonnull LivingEntity attacker) {
        use(attacker.level, (PlayerEntity) attacker, Hand.MAIN_HAND);
        return true;
    }

    public void throwFlail(ItemStack itemstack, World world, PlayerEntity entityplayer) {
        world.playSound(null, entityplayer.getX(), entityplayer.getY(), entityplayer.getZ(), SoundEvents.ARROW_SHOOT,
                SoundCategory.PLAYERS, 0.5f, 0.4f / (ItemFlail.random.nextFloat() * 0.4f + 0.8f));
        if (!world.isClientSide) {
            EntityFlail entityflail = new EntityFlail(world, entityplayer, itemstack);
            entityflail.shootFromRotation(entityplayer, entityplayer.xRot, entityplayer.yRot, 0.0f, 0.75f, 3.0f);
            PlayerWeaponData.setFlailEntityId(entityplayer, entityflail.getId());
            world.addFreshEntity(entityflail);
        }
        setThrown(entityplayer, true);
    }

    public void setThrown(PlayerEntity entityplayer, boolean flag) {
        PlayerWeaponData.setFlailThrown(entityplayer, flag);
    }

    public boolean isThrown(PlayerEntity entityplayer) {
        return PlayerWeaponData.isFlailThrown(entityplayer);
    }

    private void removePreviousFlail(World world, PlayerEntity entityplayer) {
        int id = PlayerWeaponData.getFlailEntityId(entityplayer);
        if (id != 0) {
            Entity entity = world.getEntity(id);
            if (entity instanceof EntityFlail) {
                entity.remove();
            }
        }
    }

    public float getFlailDamage() {
        return flailDamage;
    }
}
