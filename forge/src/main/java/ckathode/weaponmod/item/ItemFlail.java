package ckathode.weaponmod.item;

import ckathode.weaponmod.PlayerWeaponData;
import ckathode.weaponmod.entity.projectile.EntityFlail;
import javax.annotation.Nonnull;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

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
    public void inventoryTick(@Nonnull ItemStack itemstack, @Nonnull Level world,
                              @Nonnull Entity entity, int i, boolean flag) {
        if (!(entity instanceof Player)) {
            return;
        }
        Player player = (Player) entity;
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
    public InteractionResultHolder<ItemStack> use(@Nonnull Level world,
                                                  @Nonnull Player entityplayer,
                                                  @Nonnull InteractionHand hand) {
        ItemStack itemstack = entityplayer.getItemInHand(hand);
        if (hand != InteractionHand.MAIN_HAND) {
            return new InteractionResultHolder<>(InteractionResult.FAIL, itemstack);
        }
        removePreviousFlail(world, entityplayer);
        if (!itemstack.isEmpty()) {
            entityplayer.swing(hand);
            itemstack.hurtAndBreak(1, entityplayer, s -> s.broadcastBreakEvent(InteractionHand.MAIN_HAND));
            throwFlail(itemstack, world, entityplayer);
        }
        return new InteractionResultHolder<>(InteractionResult.SUCCESS, itemstack);
    }

    @Override
    public boolean hurtEnemy(@Nonnull ItemStack itemstack, @Nonnull LivingEntity entityliving,
                             @Nonnull LivingEntity attacker) {
        use(attacker.level, (Player) attacker, InteractionHand.MAIN_HAND);
        return true;
    }

    public void throwFlail(ItemStack itemstack, Level world, Player entityplayer) {
        world.playSound(null, entityplayer.getX(), entityplayer.getY(), entityplayer.getZ(), SoundEvents.ARROW_SHOOT,
                SoundSource.PLAYERS, 0.5f, 0.4f / (ItemFlail.random.nextFloat() * 0.4f + 0.8f));
        if (!world.isClientSide) {
            EntityFlail entityflail = new EntityFlail(world, entityplayer, itemstack);
            entityflail.shootFromRotation(entityplayer, entityplayer.xRot, entityplayer.yRot, 0.0f, 0.75f, 3.0f);
            PlayerWeaponData.setFlailEntityId(entityplayer, entityflail.getId());
            world.addFreshEntity(entityflail);
        }
        setThrown(entityplayer, true);
    }

    public void setThrown(Player entityplayer, boolean flag) {
        PlayerWeaponData.setFlailThrown(entityplayer, flag);
    }

    public boolean isThrown(Player entityplayer) {
        return PlayerWeaponData.isFlailThrown(entityplayer);
    }

    private void removePreviousFlail(Level world, Player entityplayer) {
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
