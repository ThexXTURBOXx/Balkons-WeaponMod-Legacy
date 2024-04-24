package ckathode.weaponmod.item;

import ckathode.weaponmod.PhysHelper;
import ckathode.weaponmod.PlayerWeaponData;
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
    public float getDestroySpeed(ItemStack itemstack, BlockState block) {
        float f = super.getDestroySpeed(itemstack, block);
        float f2 = weaponMaterial.getAttackDamageBonus() + 2.0f;
        return f * f2;
    }

    @Override
    public void releaseUsing(ItemStack itemstack, World world,
                             LivingEntity entityliving, int i) {
        PlayerEntity entityplayer = (PlayerEntity) entityliving;
        int j = getUseDuration(itemstack) - i;
        float f = j / 20.0f;
        f = (f * f + f * 2.0f) / 4.0f;
        if (f > 1.0f) {
            superSmash(itemstack, world, entityplayer);
        }
    }

    protected void superSmash(ItemStack itemstack, World world, PlayerEntity entityplayer) {
        entityplayer.swing(Hand.MAIN_HAND);
        float f = getEntityDamage() / 2.0f;
        WarhammerExplosion expl = new WarhammerExplosion(world, entityplayer, entityplayer.getX(),
                entityplayer.getY(), entityplayer.getZ(), f, false, Explosion.Mode.DESTROY);
        expl.doEntityExplosion(DamageSource.playerAttack(entityplayer));
        expl.doParticleExplosion(true, false);
        PhysHelper.sendExplosion(world, expl, true, false);
        itemstack.hurtAndBreak(16, entityplayer, s -> s.broadcastBreakEvent(Hand.MAIN_HAND));
        entityplayer.causeFoodExhaustion(6.0f);
        setSmashed(entityplayer);
    }

    public void setSmashed(PlayerEntity entityplayer) {
        PlayerWeaponData.setLastWarhammerSmashTicks(entityplayer, entityplayer.tickCount);
    }

    public boolean isCharged(PlayerEntity entityplayer) {
        return entityplayer.tickCount > PlayerWeaponData.getLastWarhammerSmashTicks(entityplayer) + CHARGE_DELAY;
    }

    @Override
    public UseAction getUseAnimation(ItemStack itemstack) {
        return UseAction.BOW;
    }

    @Override
    public int getUseDuration(ItemStack itemstack) {
        return 72000;
    }

    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity entityplayer,
                                       Hand hand) {
        ItemStack itemstack = entityplayer.getItemInHand(hand);
        if (itemstack.isEmpty()) {
            return new ActionResult<>(ActionResultType.FAIL, itemstack);
        }
        if (isCharged(entityplayer)) {
            entityplayer.startUsingItem(hand);
            return new ActionResult<>(ActionResultType.SUCCESS, itemstack);
        }
        return new ActionResult<>(ActionResultType.FAIL, itemstack);
    }
}
