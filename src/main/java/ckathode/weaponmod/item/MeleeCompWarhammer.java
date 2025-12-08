package ckathode.weaponmod.item;

import ckathode.weaponmod.PhysHelper;
import ckathode.weaponmod.PlayerWeaponData;
import ckathode.weaponmod.WMUtil;
import ckathode.weaponmod.WarhammerExplosion;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

public class MeleeCompWarhammer extends MeleeComponent {
    public static final int CHARGE_DELAY = 400;

    public MeleeCompWarhammer(IItemTier itemTier) {
        super(MeleeSpecs.WARHAMMER, itemTier);
    }

    @Override
    public float getBlockDamage(ItemStack itemstack, BlockState block) {
        float f = super.getBlockDamage(itemstack, block);
        float f2 = weaponMaterial.getAttackDamage() + 2.0f;
        return f * f2;
    }

    @Override
    public void onPlayerStoppedUsing(ItemStack itemstack, World world,
                                     LivingEntity entityliving, int i) {
        int j = getUseDuration(itemstack) - i;
        float f = j / 20.0f;
        f = (f * f + f * 2.0f) / 4.0f;
        if (f > 1.0f) {
            superSmash(itemstack, world, entityliving);
        }
    }

    protected void superSmash(ItemStack itemstack, World world, LivingEntity entityLiving) {
        entityLiving.swingArm(Hand.MAIN_HAND);
        float f = getEntityDamage() / 2.0f;
        WarhammerExplosion expl = new WarhammerExplosion(world, entityLiving, entityLiving.posX,
                entityLiving.posY, entityLiving.posZ, f, false, Explosion.Mode.DESTROY);
        DamageSource source = entityLiving instanceof PlayerEntity
                ? DamageSource.causePlayerDamage((PlayerEntity) entityLiving)
                : DamageSource.causeMobDamage(entityLiving);
        expl.doEntityExplosion(source);
        expl.doParticleExplosion(true, false);
        PhysHelper.sendExplosion(world, expl, true, false);
        itemstack.damageItem(16, entityLiving, s -> s.sendBreakAnimation(Hand.MAIN_HAND));
        if (entityLiving instanceof PlayerEntity) {
            ((PlayerEntity) entityLiving).addExhaustion(6.0f);
            setSmashed((PlayerEntity) entityLiving);
        }
    }

    public void setSmashed(PlayerEntity entityplayer) {
        PlayerWeaponData.setLastWarhammerSmashTicks(entityplayer, entityplayer.ticksExisted);
    }

    public boolean isCharged(PlayerEntity entityplayer) {
        return getCooldown(entityplayer) <= 0;
    }

    public float getScaledCooldown(PlayerEntity entityplayer) {
        return (float) getCooldown(entityplayer) / CHARGE_DELAY;
    }

    public int getCooldown(PlayerEntity entityplayer) {
        return PlayerWeaponData.getLastWarhammerSmashTicks(entityplayer) + CHARGE_DELAY - entityplayer.ticksExisted;
    }

    @Override
    public UseAction getUseAction(ItemStack itemstack) {
        return UseAction.BOW;
    }

    @Override
    public int getUseDuration(ItemStack itemstack) {
        return 72000;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack itemstack, World world,
                                                    PlayerEntity entityplayer, Hand hand) {
        if (itemstack.isEmpty()) {
            return new ActionResult<>(ActionResultType.FAIL, itemstack);
        }
        if (isCharged(entityplayer)) {
            entityplayer.setActiveHand(hand);
            return new ActionResult<>(ActionResultType.SUCCESS, itemstack);
        }
        return new ActionResult<>(ActionResultType.FAIL, itemstack);
    }

    @Override
    public boolean shouldRenderCooldown() {
        return !isCharged(WMUtil.Client.getLocalPlayer());
    }

    @Override
    public float getCooldown() {
        return getScaledCooldown(WMUtil.Client.getLocalPlayer());
    }
}
