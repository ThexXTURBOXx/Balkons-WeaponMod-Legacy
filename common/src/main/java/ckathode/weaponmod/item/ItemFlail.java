package ckathode.weaponmod.item;

import ckathode.weaponmod.BalkonsWeaponMod;
import ckathode.weaponmod.PlayerWeaponData;
import ckathode.weaponmod.WMItemBuilder;
import ckathode.weaponmod.entity.projectile.EntityFlail;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ItemFlail extends ItemMelee {

    public static final String WOOD_ID = "flail.wood";
    public static final ItemFlail WOOD_ITEM =
            WMItemBuilder.createStandardFlail(ToolMaterial.WOOD, BalkonsWeaponMod.id(WOOD_ID));

    public static final String STONE_ID = "flail.stone";
    public static final ItemFlail STONE_ITEM =
            WMItemBuilder.createStandardFlail(ToolMaterial.STONE, BalkonsWeaponMod.id(STONE_ID));

    public static final String IRON_ID = "flail.iron";
    public static final ItemFlail IRON_ITEM =
            WMItemBuilder.createStandardFlail(ToolMaterial.IRON, BalkonsWeaponMod.id(IRON_ID));

    public static final String GOLD_ID = "flail.gold";
    public static final ItemFlail GOLD_ITEM =
            WMItemBuilder.createStandardFlail(ToolMaterial.GOLD, BalkonsWeaponMod.id(GOLD_ID));

    public static final String DIAMOND_ID = "flail.diamond";
    public static final ItemFlail DIAMOND_ITEM =
            WMItemBuilder.createStandardFlail(ToolMaterial.DIAMOND, BalkonsWeaponMod.id(DIAMOND_ID));

    public static final String NETHERITE_ID = "flail.netherite";
    public static final ItemFlail NETHERITE_ITEM =
            WMItemBuilder.createStandardFlail(ToolMaterial.NETHERITE, BalkonsWeaponMod.id(NETHERITE_ID));

    private final float flailDamage;

    public ItemFlail(MeleeComponent meleecomponent, @NotNull ResourceLocation id) {
        super(meleecomponent, id);
        flailDamage = 4.0f + meleecomponent.weaponMaterial.attackDamageBonus();
    }

    @Override
    public void inventoryTick(ItemStack itemStack, ServerLevel serverLevel, Entity entity,
                              @Nullable EquipmentSlot equipmentSlot) {
        if (!(entity instanceof Player player) || equipmentSlot == null || equipmentSlot.getType() != EquipmentSlot.Type.HAND) {
            return;
        }
        if (!isThrown(player)) {
            return;
        }
        if (!ItemStack.matches(player.getMainHandItem(), itemStack)) {
            setThrown(player, false);
        } else {
            int id = PlayerWeaponData.getFlailEntityId(player);
            if (id != 0) {
                Entity entity2 = serverLevel.getEntity(id);
                if (entity2 instanceof EntityFlail flail) {
                    Holder<Enchantment> fireAspect = player.registryAccess()
                            .lookupOrThrow(Registries.ENCHANTMENT).get(Enchantments.FIRE_ASPECT).orElse(null);
                    if (fireAspect != null && EnchantmentHelper.getItemEnchantmentLevel(fireAspect, itemStack) > 0) {
                        flail.igniteForSeconds(2);
                    }
                }
            }
        }
    }

    @Override
    public @NotNull InteractionResult use(@NotNull Level world,
                                          @NotNull Player entityplayer,
                                          @NotNull InteractionHand hand) {
        ItemStack itemstack = entityplayer.getItemInHand(hand);
        if (hand != InteractionHand.MAIN_HAND) {
            return InteractionResult.FAIL;
        }
        removePreviousFlail(world, entityplayer);
        entityplayer.swing(hand);
        if (!entityplayer.isCreative()) {
            Level level = entityplayer.level();
            if (level instanceof ServerLevel serverLevel) {
                itemstack.hurtAndBreak(1, serverLevel, entityplayer instanceof ServerPlayer player ? player : null,
                        i -> setThrown(entityplayer, false));
            }
        }
        if (!itemstack.isEmpty()) {
            throwFlail(itemstack, world, entityplayer);
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public void hurtEnemy(@NotNull ItemStack itemstack, @NotNull LivingEntity entityliving,
                          @NotNull LivingEntity attacker) {
        if (attacker instanceof Player player)
            use(attacker.level(), player, InteractionHand.MAIN_HAND);
    }

    public void throwFlail(ItemStack itemstack, Level world, Player entityplayer) {
        world.playSound(null, entityplayer.getX(), entityplayer.getY(), entityplayer.getZ(), SoundEvents.ARROW_SHOOT,
                SoundSource.PLAYERS, 0.5f, 0.4f / (entityplayer.getRandom().nextFloat() * 0.4f + 0.8f));
        if (!world.isClientSide()) {
            EntityFlail entityflail = new EntityFlail(world, entityplayer, itemstack);
            entityflail.shootFromRotation(entityplayer, entityplayer.getXRot(), entityplayer.getYRot(),
                    0.0f, 0.75f, 3.0f);
            PlayerWeaponData.setFlailEntityId(entityplayer, entityflail.getId());
            MeleeComponent.applyProjectileEnchantments(entityflail, itemstack);
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
