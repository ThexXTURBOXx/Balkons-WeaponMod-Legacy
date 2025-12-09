package ckathode.weaponmod.item;

import ckathode.weaponmod.BalkonsWeaponMod;
import ckathode.weaponmod.PhysHelper;
import ckathode.weaponmod.PlayerWeaponData;
import ckathode.weaponmod.WMItemBuilder;
import ckathode.weaponmod.WMUtil;
import ckathode.weaponmod.WarhammerExplosion;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUseAnimation;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.item.component.Tool;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class MeleeCompWarhammer extends MeleeComponent {

    public static final int CHARGE_DELAY = 400;

    public static final String WOOD_ID = "warhammer.wood";
    public static final ItemMelee WOOD_ITEM =
            WMItemBuilder.createStandardWarhammer(ToolMaterial.WOOD, BalkonsWeaponMod.id(WOOD_ID));

    public static final String STONE_ID = "warhammer.stone";
    public static final ItemMelee STONE_ITEM =
            WMItemBuilder.createStandardWarhammer(ToolMaterial.STONE, BalkonsWeaponMod.id(STONE_ID));

    public static final String IRON_ID = "warhammer.iron";
    public static final ItemMelee IRON_ITEM =
            WMItemBuilder.createStandardWarhammer(ToolMaterial.IRON, BalkonsWeaponMod.id(IRON_ID));

    public static final String GOLD_ID = "warhammer.gold";
    public static final ItemMelee GOLD_ITEM =
            WMItemBuilder.createStandardWarhammer(ToolMaterial.GOLD, BalkonsWeaponMod.id(GOLD_ID));

    public static final String DIAMOND_ID = "warhammer.diamond";
    public static final ItemMelee DIAMOND_ITEM =
            WMItemBuilder.createStandardWarhammer(ToolMaterial.DIAMOND, BalkonsWeaponMod.id(DIAMOND_ID));

    public static final String NETHERITE_ID = "warhammer.netherite";
    public static final ItemMelee NETHERITE_ITEM =
            WMItemBuilder.createStandardWarhammer(ToolMaterial.NETHERITE, BalkonsWeaponMod.id(NETHERITE_ID));

    public MeleeCompWarhammer(ToolMaterial itemTier) {
        super(MeleeSpecs.WARHAMMER, itemTier);
    }

    @Override
    public @NotNull Tool getToolComponent() {
        Tool orig = super.getToolComponent();
        return new Tool(orig.rules(), orig.defaultMiningSpeed() * (weaponMaterial.attackDamageBonus() + 2.0f), 1);
    }

    @Override
    public boolean releaseUsing(ItemStack itemstack, Level world, LivingEntity entityliving, int i) {
        int j = getUseDuration(itemstack) - i;
        float f = j / 20.0f;
        f = (f * f + f * 2.0f) / 4.0f;
        if (f > 1.0f) {
            superSmash(itemstack, world, entityliving);
        }
        return true;
    }

    protected void superSmash(ItemStack itemstack, Level world, LivingEntity entityLiving) {
        entityLiving.swing(InteractionHand.MAIN_HAND);
        float f = getEntityDamage() / 2.0f;
        if (world instanceof ServerLevel serverLevel) {
            WarhammerExplosion expl = new WarhammerExplosion(serverLevel, entityLiving, entityLiving.position(), f,
                    false, Explosion.BlockInteraction.DESTROY);
            DamageSource source = entityLiving instanceof Player player
                    ? world.damageSources().playerAttack(player)
                    : world.damageSources().mobAttack(entityLiving);
            expl.doEntityExplosion(source);
            expl.doParticleExplosion(true, false);
            PhysHelper.sendExplosion(world, expl, true, false);
        }
        itemstack.hurtAndBreak(16, entityLiving, EquipmentSlot.MAINHAND);
        if (entityLiving instanceof Player player) {
            player.causeFoodExhaustion(6.0f);
            setSmashed(player);
        }
    }

    public void setSmashed(Player entityplayer) {
        PlayerWeaponData.setLastWarhammerSmashTicks(entityplayer, entityplayer.tickCount);
    }

    public boolean isCharged(Player player) {
        return getCooldown(player) <= 0;
    }

    public float getScaledCooldown(Player player) {
        return (float) getCooldown(player) / CHARGE_DELAY;
    }

    public int getCooldown(Player player) {
        return PlayerWeaponData.getLastWarhammerSmashTicks(player) + CHARGE_DELAY - player.tickCount;
    }

    @Override
    public @NotNull ItemUseAnimation getUseAnimation(ItemStack itemstack) {
        return ItemUseAnimation.BOW;
    }

    @Override
    public int getUseDuration(ItemStack itemstack) {
        return 72000;
    }

    @Override
    public @NotNull InteractionResult use(ItemStack itemstack, Level world,
                                          Player entityplayer, InteractionHand hand) {
        if (itemstack.isEmpty()) {
            return InteractionResult.FAIL;
        }
        if (isCharged(entityplayer)) {
            entityplayer.startUsingItem(hand);
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.FAIL;
    }

    @Override
    public boolean shouldRenderCooldown() {
        Player p = WMUtil.Client.getLocalPlayer();
        return p != null && !isCharged(p);
    }

    @Override
    public float getCooldown() {
        Player p = WMUtil.Client.getLocalPlayer();
        return p == null ? 0 : getScaledCooldown(p);
    }

}
