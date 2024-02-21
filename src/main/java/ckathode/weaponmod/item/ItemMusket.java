package ckathode.weaponmod.item;

import net.minecraft.item.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import net.minecraft.block.state.*;
import net.minecraft.util.math.*;
import net.minecraft.block.material.*;
import net.minecraft.nbt.*;
import net.minecraft.stats.*;
import net.minecraft.inventory.*;
import ckathode.weaponmod.*;

public class ItemMusket extends ItemShooter
{
    protected Item bayonetItem;
    private int bayonetDurability;
    
    public ItemMusket(final String id, final MeleeComponent meleecomponent, final Item bayonetitem) {
        super(id, new RangedCompMusket(), meleecomponent);
        this.bayonetItem = bayonetitem;
        if (meleecomponent.meleeSpecs != MeleeComponent.MeleeSpecs.NONE && meleecomponent.weaponMaterial != null) {
            this.bayonetDurability = meleecomponent.meleeSpecs.durabilityBase + (int)(meleecomponent.weaponMaterial.getMaxUses() * meleecomponent.meleeSpecs.durabilityMult);
        }
    }
    
    public boolean hasBayonet() {
        return this.bayonetItem != null;
    }
    
    @Override
    public boolean hitEntity(final ItemStack itemstack, final EntityLivingBase entityliving, final EntityLivingBase attacker) {
        if (this.hasBayonet()) {
            if (entityliving.hurtResistantTime == entityliving.maxHurtResistantTime) {
                final float kb = this.meleeComponent.getKnockBack(itemstack, entityliving, attacker);
                PhysHelper.knockBack(entityliving, attacker, kb);
                entityliving.hurtResistantTime -= (int)(2.0f / this.meleeComponent.meleeSpecs.attackDelay);
            }
            if (attacker instanceof EntityPlayer && !((EntityPlayer)attacker).capabilities.isCreativeMode) {
                this.bayonetDamage(itemstack, attacker, 1);
            }
        }
        return true;
    }
    
    @Override
    public boolean onBlockDestroyed(final ItemStack itemstack, final World world, final IBlockState block, final BlockPos pos, final EntityLivingBase entityliving) {
        if (this.hasBayonet()) {
            final Material material = block.getMaterial();
            final boolean flag = material != Material.PLANTS && material != Material.VINE && material != Material.CORAL && material != Material.LEAVES && material != Material.GOURD;
            if (entityliving instanceof EntityPlayer && !((EntityPlayer)entityliving).capabilities.isCreativeMode && flag) {
                this.bayonetDamage(itemstack, entityliving, 2);
            }
        }
        return true;
    }
    
    public void bayonetDamage(final ItemStack itemstack, final EntityLivingBase entityliving, final int damage) {
        final EntityPlayer entityplayer = (EntityPlayer)entityliving;
        if (itemstack.getTagCompound() == null) {
            itemstack.setTagCompound(new NBTTagCompound());
        }
        int bayonetdamage = itemstack.getTagCompound().getShort("bayonetDamage") + damage;
        if (bayonetdamage > this.bayonetDurability) {
            entityplayer.renderBrokenItemStack(itemstack);
            final int id = Item.getIdFromItem(this);
            if (id != 0) {
                BalkonsWeaponMod.modLog.debug("Musket Item (" + this.toString() + ") ID = " + id);
                entityplayer.addStat(StatList.getObjectBreakStats(this));
            }
            bayonetdamage = 0;
            final ItemStack itemstack2 = new ItemStack(BalkonsWeaponMod.musket, 1, itemstack.getItemDamage());
            entityplayer.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, itemstack2);
            if (itemstack.getTagCompound().hasKey("rld")) {
                ReloadHelper.setReloadState(itemstack2, ReloadHelper.getReloadState(itemstack));
            }
        }
        itemstack.getTagCompound().setShort("bayonetDamage", (short)bayonetdamage);
    }
}
