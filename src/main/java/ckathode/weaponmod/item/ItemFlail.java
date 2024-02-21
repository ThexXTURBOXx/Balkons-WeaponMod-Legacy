package ckathode.weaponmod.item;

import net.minecraft.item.*;
import net.minecraft.world.*;
import javax.annotation.*;
import net.minecraft.entity.player.*;
import ckathode.weaponmod.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraft.entity.*;
import ckathode.weaponmod.entity.projectile.*;
import net.minecraft.init.*;
import net.minecraft.util.*;

public class ItemFlail extends ItemMelee
{
    private float flailDamage;
    
    public ItemFlail(final String id, final MeleeComponent meleecomponent) {
        super(id, meleecomponent);
        this.flailDamage = 4.0f + meleecomponent.weaponMaterial.getAttackDamage() * 1.0f;
        this.addPropertyOverride(new ResourceLocation("thrown"), new IItemPropertyGetter() {
            @SideOnly(Side.CLIENT)
            public float apply(final ItemStack stack, @Nullable final World worldIn, @Nullable final EntityLivingBase entityIn) {
                return (entityIn == null) ? 0.0f : ((entityIn.getHeldItemMainhand() == stack && entityIn instanceof EntityPlayer && PlayerWeaponData.isFlailThrown((EntityPlayer)entityIn)) ? 1.0f : 0.0f);
            }
        });
    }
    
    @Override
    public int getItemEnchantability() {
        return 0;
    }
    
    @SideOnly(Side.CLIENT)
    public boolean isFull3D() {
        return true;
    }
    
    @Override
    public void onUpdate(final ItemStack itemstack, final World world, final Entity entity, final int i, final boolean flag) {
        if (!(entity instanceof EntityPlayer)) {
            return;
        }
        final EntityPlayer player = (EntityPlayer)entity;
        if (!PlayerWeaponData.isFlailThrown(player)) {
            return;
        }
        final ItemStack itemstack2 = player.getHeldItemMainhand();
        if (itemstack2.isEmpty() || itemstack2.getItem() != this) {
            this.setThrown(player, false);
        }
        else {
            final int id = PlayerWeaponData.getFlailEntityId(player);
            if (id != 0) {
                final Entity entity2 = world.getEntityByID(id);
                if (entity2 instanceof EntityFlail) {
                    ((EntityFlail)entity2).shootingEntity = player;
                    ((EntityFlail)entity2).setThrownItemStack(itemstack);
                }
            }
        }
    }
    
    @Override
    public ActionResult<ItemStack> onItemRightClick(final World world, final EntityPlayer entityplayer, final EnumHand hand) {
        final ItemStack itemstack = entityplayer.getHeldItem(hand);
        if (hand != EnumHand.MAIN_HAND) {
            return (ActionResult<ItemStack>)new ActionResult(EnumActionResult.FAIL, itemstack);
        }
        this.removePreviousFlail(world, entityplayer);
        if (!itemstack.isEmpty()) {
            entityplayer.swingArm(hand);
            itemstack.damageItem(1, entityplayer);
            this.throwFlail(itemstack, world, entityplayer);
        }
        return (ActionResult<ItemStack>)new ActionResult(EnumActionResult.SUCCESS, itemstack);
    }
    
    @Override
    public boolean hitEntity(final ItemStack itemstack, final EntityLivingBase entityliving, final EntityLivingBase attacker) {
        this.onItemRightClick(attacker.world, (EntityPlayer)attacker, EnumHand.MAIN_HAND);
        return true;
    }
    
    public void throwFlail(final ItemStack itemstack, final World world, final EntityPlayer entityplayer) {
        world.playSound(null, entityplayer.posX, entityplayer.posY, entityplayer.posZ, SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.PLAYERS, 0.5f, 0.4f / (ItemFlail.itemRand.nextFloat() * 0.4f + 0.8f));
        if (!world.isRemote) {
            final EntityFlail entityflail = new EntityFlail(world, entityplayer, itemstack);
            entityflail.shoot(entityplayer, entityplayer.rotationPitch, entityplayer.rotationYaw, 0.0f, 0.75f, 3.0f);
            PlayerWeaponData.setFlailEntityId(entityplayer, entityflail.getEntityId());
            world.spawnEntity(entityflail);
        }
        this.setThrown(entityplayer, true);
    }
    
    public void setThrown(final EntityPlayer entityplayer, final boolean flag) {
        PlayerWeaponData.setFlailThrown(entityplayer, flag);
    }
    
    private void removePreviousFlail(final World world, final EntityPlayer entityplayer) {
        final int id = PlayerWeaponData.getFlailEntityId(entityplayer);
        if (id != 0) {
            final Entity entity = world.getEntityByID(id);
            if (entity instanceof EntityFlail) {
                entity.setDead();
            }
        }
    }
    
    public boolean getThrown(final EntityPlayer entityplayer) {
        return PlayerWeaponData.isFlailThrown(entityplayer);
    }
    
    public float getFlailDamage() {
        return this.flailDamage;
    }
}
