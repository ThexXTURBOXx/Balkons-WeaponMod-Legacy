package ckathode.weaponmod.item;

import ckathode.weaponmod.PhysHelper;
import ckathode.weaponmod.PlayerWeaponData;
import ckathode.weaponmod.WarhammerExplosion;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class MeleeCompWarhammer extends MeleeComponent {
    public static final int CHARGE_DELAY = 400;

    public MeleeCompWarhammer(final IItemTier itemTier) {
        super(MeleeSpecs.WARHAMMER, itemTier);
    }

    @Override
    public float getBlockDamage(final ItemStack itemstack, final IBlockState block) {
        final float f = super.getBlockDamage(itemstack, block);
        final float f2 = this.weaponMaterial.getAttackDamage() + 2.0f;
        return f * f2;
    }

    @Override
    public void onPlayerStoppedUsing(final ItemStack itemstack, final World world,
                                     final EntityLivingBase entityliving, final int i) {
        final EntityPlayer entityplayer = (EntityPlayer) entityliving;
        final int j = this.getUseDuration(itemstack) - i;
        float f = j / 20.0f;
        f = (f * f + f * 2.0f) / 4.0f;
        if (f > 1.0f) {
            this.superSmash(itemstack, world, entityplayer);
        }
    }

    protected void superSmash(final ItemStack itemstack, final World world, final EntityPlayer entityplayer) {
        entityplayer.swingArm(EnumHand.MAIN_HAND);
        final float f = this.getEntityDamage() / 2.0f;
        final WarhammerExplosion expl = new WarhammerExplosion(world, entityplayer, entityplayer.posX,
                entityplayer.posY, entityplayer.posZ, f, false, true);
        expl.doEntityExplosion(DamageSource.causePlayerDamage(entityplayer));
        expl.doParticleExplosion(true, false);
        PhysHelper.sendExplosion(world, expl, true, false);
        itemstack.damageItem(16, entityplayer);
        entityplayer.addExhaustion(6.0f);
        this.setSmashed(entityplayer);
    }

    public void setSmashed(final EntityPlayer entityplayer) {
        PlayerWeaponData.setLastWarhammerSmashTicks(entityplayer, entityplayer.ticksExisted);
    }

    public boolean isCharged(final EntityPlayer entityplayer) {
        return entityplayer.ticksExisted > PlayerWeaponData.getLastWarhammerSmashTicks(entityplayer) + 400;
    }

    @Override
    public EnumAction getUseAction(final ItemStack itemstack) {
        return EnumAction.BOW;
    }

    @Override
    public int getUseDuration(final ItemStack itemstack) {
        return 72000;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(final World world, final EntityPlayer entityplayer,
                                                    final EnumHand hand) {
        final ItemStack itemstack = entityplayer.getHeldItem(hand);
        if (itemstack.isEmpty()) {
            return new ActionResult<>(EnumActionResult.FAIL, itemstack);
        }
        if (this.isCharged(entityplayer)) {
            entityplayer.setActiveHand(hand);
            return new ActionResult<>(EnumActionResult.SUCCESS, itemstack);
        }
        return new ActionResult<>(EnumActionResult.FAIL, itemstack);
    }
}
