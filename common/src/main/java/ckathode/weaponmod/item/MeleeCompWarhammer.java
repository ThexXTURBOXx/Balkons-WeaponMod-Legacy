package ckathode.weaponmod.item;

import ckathode.weaponmod.PhysHelper;
import ckathode.weaponmod.PlayerWeaponData;
import ckathode.weaponmod.WMItemBuilder;
import ckathode.weaponmod.WarhammerExplosion;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class MeleeCompWarhammer extends MeleeComponent {

    public static final int CHARGE_DELAY = 400;

    public static final String WOOD_ID = "warhammer.wood";
    public static final ItemMelee WOOD_ITEM = WMItemBuilder.createStandardWarhammer(Tiers.WOOD);

    public static final String STONE_ID = "warhammer.stone";
    public static final ItemMelee STONE_ITEM = WMItemBuilder.createStandardWarhammer(Tiers.STONE);

    public static final String IRON_ID = "warhammer.iron";
    public static final ItemMelee IRON_ITEM = WMItemBuilder.createStandardWarhammer(Tiers.IRON);

    public static final String GOLD_ID = "warhammer.gold";
    public static final ItemMelee GOLD_ITEM = WMItemBuilder.createStandardWarhammer(Tiers.GOLD);

    public static final String DIAMOND_ID = "warhammer.diamond";
    public static final ItemMelee DIAMOND_ITEM = WMItemBuilder.createStandardWarhammer(Tiers.DIAMOND);

    public static final String NETHERITE_ID = "warhammer.netherite";
    public static final ItemMelee NETHERITE_ITEM = WMItemBuilder.createStandardWarhammer(Tiers.NETHERITE);

    public MeleeCompWarhammer(Tier itemTier) {
        super(MeleeSpecs.WARHAMMER, itemTier);
    }

    @Override
    public float getDestroySpeed(ItemStack itemstack, BlockState block) {
        float f = super.getDestroySpeed(itemstack, block);
        float f2 = weaponMaterial.getAttackDamageBonus() + 2.0f;
        return f * f2;
    }

    @Override
    public void releaseUsing(ItemStack itemstack, Level world,
                             LivingEntity entityliving, int i) {
        Player entityplayer = (Player) entityliving;
        int j = getUseDuration(itemstack) - i;
        float f = j / 20.0f;
        f = (f * f + f * 2.0f) / 4.0f;
        if (f > 1.0f) {
            superSmash(itemstack, world, entityplayer);
        }
    }

    protected void superSmash(ItemStack itemstack, Level world, Player entityplayer) {
        entityplayer.swing(InteractionHand.MAIN_HAND);
        float f = getEntityDamage() / 2.0f;
        WarhammerExplosion expl = new WarhammerExplosion(world, entityplayer, entityplayer.getX(),
                entityplayer.getY(), entityplayer.getZ(), f, false, Explosion.BlockInteraction.DESTROY);
        expl.doEntityExplosion(world.damageSources().playerAttack(entityplayer));
        expl.doParticleExplosion(true, false);
        PhysHelper.sendExplosion(world, expl, true, false);
        itemstack.hurtAndBreak(16, entityplayer, s -> s.broadcastBreakEvent(InteractionHand.MAIN_HAND));
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
    public UseAnim getUseAnimation(ItemStack itemstack) {
        return UseAnim.BOW;
    }

    @Override
    public int getUseDuration(ItemStack itemstack) {
        return 72000;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player entityplayer,
                                                  InteractionHand hand) {
        ItemStack itemstack = entityplayer.getItemInHand(hand);
        if (itemstack.isEmpty()) {
            return new InteractionResultHolder<>(InteractionResult.FAIL, itemstack);
        }
        if (isCharged(entityplayer)) {
            entityplayer.startUsingItem(hand);
            return new InteractionResultHolder<>(InteractionResult.SUCCESS, itemstack);
        }
        return new InteractionResultHolder<>(InteractionResult.FAIL, itemstack);
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
