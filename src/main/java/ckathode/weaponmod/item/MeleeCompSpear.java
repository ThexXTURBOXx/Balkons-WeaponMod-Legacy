package ckathode.weaponmod.item;

import ckathode.weaponmod.BalkonsWeaponMod;
import ckathode.weaponmod.entity.projectile.EntitySpear;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumAction;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

public class MeleeCompSpear extends MeleeComponent implements IExtendedReachItem {
    public MeleeCompSpear(IItemTier itemTier) {
        super(MeleeSpecs.SPEAR, itemTier);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack itemstack, World world,
                                                    EntityPlayer entityplayer, EnumHand hand) {
        if (!BalkonsWeaponMod.instance.modConfig.canThrowSpear.get()) {
            return super.onItemRightClick(itemstack, world, entityplayer, hand);
        }
        if (!world.isRemote) {
            EntitySpear entityspear = new EntitySpear(world, entityplayer, itemstack.copy());
            entityspear.shoot(entityplayer, entityplayer.rotationPitch, entityplayer.rotationYaw, 0.0f, 0.8f, 3.0f);
            applyProjectileEnchantments(entityspear, itemstack);
            world.spawnEntity(entityspear);
        }
        world.playSound(null, entityplayer.posX, entityplayer.posY, entityplayer.posZ, SoundEvents.ENTITY_ARROW_SHOOT
                , SoundCategory.PLAYERS, 1.0f, 1.0f / (weapon.getItemRand().nextFloat() * 0.4f + 0.8f));
        if (!entityplayer.isCreative()) {
            itemstack = itemstack.copy();
            itemstack.shrink(1);
        }
        return new ActionResult<>(EnumActionResult.SUCCESS, itemstack);
    }

    @Override
    public EnumAction getUseAction(ItemStack itemstack) {
        return BalkonsWeaponMod.instance.modConfig.canThrowSpear.get()
                ? EnumAction.NONE : super.getUseAction(itemstack);
    }

    @Override
    public float getExtendedReach(World world, EntityLivingBase living, ItemStack itemstack) {
        return 4.0f;
    }
}
