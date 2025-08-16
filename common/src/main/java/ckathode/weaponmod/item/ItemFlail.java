package ckathode.weaponmod.item;

import ckathode.weaponmod.PlayerWeaponData;
import ckathode.weaponmod.WMItemBuilder;
import ckathode.weaponmod.entity.projectile.EntityFlail;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class ItemFlail extends ItemMelee {

    public static final String WOOD_ID = "flail.wood";
    public static final ItemFlail WOOD_ITEM = WMItemBuilder.createStandardFlail(Tiers.WOOD);

    public static final String STONE_ID = "flail.stone";
    public static final ItemFlail STONE_ITEM = WMItemBuilder.createStandardFlail(Tiers.STONE);

    public static final String IRON_ID = "flail.iron";
    public static final ItemFlail IRON_ITEM = WMItemBuilder.createStandardFlail(Tiers.IRON);

    public static final String GOLD_ID = "flail.gold";
    public static final ItemFlail GOLD_ITEM = WMItemBuilder.createStandardFlail(Tiers.GOLD);

    public static final String DIAMOND_ID = "flail.diamond";
    public static final ItemFlail DIAMOND_ITEM = WMItemBuilder.createStandardFlail(Tiers.DIAMOND);

    public static final String NETHERITE_ID = "flail.netherite";
    public static final ItemFlail NETHERITE_ITEM = WMItemBuilder.createStandardFlail(Tiers.NETHERITE);

    private final float flailDamage;

    public ItemFlail(MeleeComponent meleecomponent) {
        super(meleecomponent);
        flailDamage = 4.0f + meleecomponent.weaponMaterial.getAttackDamageBonus();
    }

    @Override
    public void inventoryTick(@NotNull ItemStack itemstack, @NotNull Level world,
                              @NotNull Entity entity, int i, boolean isSelected) {
        if (!(entity instanceof Player player) || !isSelected) {
            return;
        }
        if (!isThrown(player)) {
            return;
        }
        if (!ItemStack.matches(player.getMainHandItem(), itemstack)) {
            setThrown(player, false);
        } else {
            int id = PlayerWeaponData.getFlailEntityId(player);
            if (id != 0) {
                Entity entity2 = world.getEntity(id);
                if (entity2 instanceof EntityFlail flail) {
                    if (EnchantmentHelper.getItemEnchantmentLevel(Enchantments.FIRE_ASPECT, itemstack) > 0) {
                        flail.setSecondsOnFire(2);
                    }
                }
            }
        }
    }

    @NotNull
    @Override
    public InteractionResultHolder<ItemStack> use(@NotNull Level world,
                                                  @NotNull Player entityplayer,
                                                  @NotNull InteractionHand hand) {
        ItemStack itemstack = entityplayer.getItemInHand(hand);
        if (hand != InteractionHand.MAIN_HAND) {
            return new InteractionResultHolder<>(InteractionResult.FAIL, itemstack);
        }
        removePreviousFlail(world, entityplayer);
        entityplayer.swing(hand);
        if (!entityplayer.isCreative()) {
            itemstack.hurtAndBreak(1, entityplayer, s -> {
                s.broadcastBreakEvent(InteractionHand.MAIN_HAND);
                setThrown(entityplayer, false);
            });
        }
        if (!itemstack.isEmpty()) {
            throwFlail(itemstack, world, entityplayer);
        }
        return new InteractionResultHolder<>(InteractionResult.SUCCESS, itemstack);
    }

    @Override
    public boolean hurtEnemy(@NotNull ItemStack itemstack, @NotNull LivingEntity entityliving,
                             @NotNull LivingEntity attacker) {
        if (attacker instanceof Player player)
            use(attacker.level(), player, InteractionHand.MAIN_HAND);
        return true;
    }

    public void throwFlail(ItemStack itemstack, Level world, Player entityplayer) {
        world.playSound(null, entityplayer.getX(), entityplayer.getY(), entityplayer.getZ(), SoundEvents.ARROW_SHOOT,
                SoundSource.PLAYERS, 0.5f, 0.4f / (entityplayer.getRandom().nextFloat() * 0.4f + 0.8f));
        if (!world.isClientSide) {
            EntityFlail entityflail = new EntityFlail(world, entityplayer, itemstack);
            entityflail.shootFromRotation(entityplayer, entityplayer.getXRot(), entityplayer.getYRot(),
                    0.0f, 0.75f, 3.0f);
            PlayerWeaponData.setFlailEntityId(entityplayer, entityflail.getId());
            world.addFreshEntity(entityflail);
            setThrown(entityplayer, true);
        }
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
                entity.remove(Entity.RemovalReason.DISCARDED);
            }
        }
    }

    public float getFlailDamage() {
        return flailDamage;
    }

}
