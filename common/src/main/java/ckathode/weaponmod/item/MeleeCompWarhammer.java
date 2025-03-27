package ckathode.weaponmod.item;

import ckathode.weaponmod.BalkonsWeaponMod;
import ckathode.weaponmod.PhysHelper;
import ckathode.weaponmod.PlayerWeaponData;
import ckathode.weaponmod.WMItemBuilder;
import ckathode.weaponmod.WarhammerExplosion;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
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
        return new Tool(orig.rules(), orig.defaultMiningSpeed() * (weaponMaterial.attackDamageBonus() + 2.0f),
                1, false);
    }

    @Override
    public boolean releaseUsing(ItemStack itemstack, Level world, LivingEntity entityliving, int i) {
        Player entityplayer = (Player) entityliving;
        int j = getUseDuration(itemstack) - i;
        float f = j / 20.0f;
        f = (f * f + f * 2.0f) / 4.0f;
        if (f > 1.0f) {
            superSmash(itemstack, world, entityplayer);
        }
        return true;
    }

    protected void superSmash(ItemStack itemstack, Level world, Player entityplayer) {
        entityplayer.swing(InteractionHand.MAIN_HAND);
        float f = getEntityDamage() / 2.0f;
        if (world instanceof ServerLevel serverLevel) {
            WarhammerExplosion expl = new WarhammerExplosion(serverLevel, entityplayer, entityplayer.position(), f,
                    false, Explosion.BlockInteraction.DESTROY);
            expl.doEntityExplosion(world.damageSources().playerAttack(entityplayer));
            expl.doParticleExplosion(true, false);
            PhysHelper.sendExplosion(world, expl, true, false);
        }
        itemstack.hurtAndBreak(16, entityplayer, EquipmentSlot.MAINHAND);
        entityplayer.causeFoodExhaustion(6.0f);
        setSmashed(entityplayer);
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
    public @NotNull InteractionResult use(Level world, Player entityplayer, InteractionHand hand) {
        ItemStack itemstack = entityplayer.getItemInHand(hand);
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
    @Environment(EnvType.CLIENT)
    public boolean shouldRenderCooldown() {
        return Minecraft.getInstance().player != null && !isCharged(Minecraft.getInstance().player);
    }

    @Override
    @Environment(EnvType.CLIENT)
    public float getCooldown() {
        return Minecraft.getInstance().player == null ? 0 : getScaledCooldown(Minecraft.getInstance().player);
    }

}
