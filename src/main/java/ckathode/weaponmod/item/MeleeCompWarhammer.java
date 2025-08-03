package ckathode.weaponmod.item;

import ckathode.weaponmod.PhysHelper;
import ckathode.weaponmod.PlayerWeaponData;
import ckathode.weaponmod.WarhammerExplosion;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class MeleeCompWarhammer extends MeleeComponent {
    public static final int CHARGE_DELAY = 400;

    public MeleeCompWarhammer(Item.ToolMaterial toolmaterial) {
        super(MeleeSpecs.WARHAMMER, toolmaterial);
    }

    @Override
    public float getBlockDamage(ItemStack itemstack, IBlockState block) {
        float f = super.getBlockDamage(itemstack, block);
        float f2 = weaponMaterial.getAttackDamage() + 2.0f;
        return f * f2;
    }

    @Override
    public void onPlayerStoppedUsing(ItemStack itemstack, World world,
                                     EntityLivingBase entityliving, int i) {
        int j = getMaxItemUseDuration(itemstack) - i;
        float f = j / 20.0f;
        f = (f * f + f * 2.0f) / 4.0f;
        if (f > 1.0f) {
            superSmash(itemstack, world, entityliving);
        }
    }

    protected void superSmash(ItemStack itemstack, World world, EntityLivingBase entityLiving) {
        entityLiving.swingArm(EnumHand.MAIN_HAND);
        float f = getEntityDamage() / 2.0f;
        WarhammerExplosion expl = new WarhammerExplosion(world, entityLiving, entityLiving.posX,
                entityLiving.posY, entityLiving.posZ, f, false, true);
        DamageSource source = entityLiving instanceof EntityPlayer
                ? DamageSource.causePlayerDamage((EntityPlayer) entityLiving)
                : DamageSource.causeMobDamage(entityLiving);
        expl.doEntityExplosion(source);
        expl.doParticleExplosion(true, false);
        PhysHelper.sendExplosion(world, expl, true, false);
        itemstack.damageItem(16, entityLiving);
        if (entityLiving instanceof EntityPlayer) {
            ((EntityPlayer) entityLiving).addExhaustion(6.0f);
            setSmashed((EntityPlayer) entityLiving);
        }
    }

    public void setSmashed(EntityPlayer entityplayer) {
        PlayerWeaponData.setLastWarhammerSmashTicks(entityplayer, entityplayer.ticksExisted);
    }

    public boolean isCharged(EntityPlayer entityplayer) {
        return getCooldown(entityplayer) <= 0;
    }

    public float getScaledCooldown(EntityPlayer entityplayer) {
        return (float) getCooldown(entityplayer) / CHARGE_DELAY;
    }

    public int getCooldown(EntityPlayer entityplayer) {
        return PlayerWeaponData.getLastWarhammerSmashTicks(entityplayer) + CHARGE_DELAY - entityplayer.ticksExisted;
    }

    @Override
    public EnumAction getItemUseAction(ItemStack itemstack) {
        return EnumAction.BOW;
    }

    @Override
    public int getMaxItemUseDuration(ItemStack itemstack) {
        return 72000;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack itemstack, World world,
                                                    EntityPlayer entityplayer, EnumHand hand) {
        if (itemstack.isEmpty()) {
            return new ActionResult<>(EnumActionResult.FAIL, itemstack);
        }
        if (isCharged(entityplayer)) {
            entityplayer.setActiveHand(hand);
            return new ActionResult<>(EnumActionResult.SUCCESS, itemstack);
        }
        return new ActionResult<>(EnumActionResult.FAIL, itemstack);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean shouldRenderCooldown() {
        return !isCharged(Minecraft.getMinecraft().player);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public float getCooldown() {
        return getScaledCooldown(Minecraft.getMinecraft().player);
    }
}
