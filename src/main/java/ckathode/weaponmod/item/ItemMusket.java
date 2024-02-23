package ckathode.weaponmod.item;

import ckathode.weaponmod.BalkonsWeaponMod;
import ckathode.weaponmod.PhysHelper;
import ckathode.weaponmod.ReloadHelper;
import javax.annotation.Nonnull;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.stats.StatList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemMusket extends ItemShooter {
    protected final Item bayonetItem;
    private int bayonetDurability;

    public ItemMusket(final String id, final MeleeComponent meleecomponent, final Item bayonetitem) {
        super(id, new RangedCompMusket(), meleecomponent);
        this.bayonetItem = bayonetitem;
        if (meleecomponent.meleeSpecs != MeleeComponent.MeleeSpecs.NONE && meleecomponent.weaponMaterial != null) {
            this.bayonetDurability =
                    meleecomponent.meleeSpecs.durabilityBase + (int) (meleecomponent.weaponMaterial.getMaxUses() * meleecomponent.meleeSpecs.durabilityMult);
        }
    }

    public boolean hasBayonet() {
        return this.bayonetItem != null;
    }

    @Override
    public boolean hitEntity(@Nonnull final ItemStack itemstack, @Nonnull final EntityLivingBase entityliving,
                             @Nonnull final EntityLivingBase attacker) {
        if (this.hasBayonet()) {
            if (entityliving.hurtResistantTime == entityliving.maxHurtResistantTime) {
                final float kb = this.meleeComponent.getKnockBack(itemstack, entityliving, attacker);
                PhysHelper.knockBack(entityliving, attacker, kb);
                entityliving.hurtResistantTime -= (int) (2.0f / this.meleeComponent.meleeSpecs.attackDelay);
            }
            if (attacker instanceof EntityPlayer && !((EntityPlayer) attacker).capabilities.isCreativeMode) {
                this.bayonetDamage(itemstack, attacker, 1);
            }
        }
        return true;
    }

    @Override
    public boolean onBlockDestroyed(@Nonnull final ItemStack itemstack, @Nonnull final World world,
                                    @Nonnull final IBlockState block, @Nonnull final BlockPos pos,
                                    @Nonnull final EntityLivingBase entityliving) {
        if (this.hasBayonet()) {
            final Material material = block.getMaterial();
            final boolean flag =
                    material != Material.PLANTS && material != Material.VINE && material != Material.CORAL && material != Material.LEAVES && material != Material.GOURD;
            if (entityliving instanceof EntityPlayer && !((EntityPlayer) entityliving).capabilities.isCreativeMode && flag) {
                this.bayonetDamage(itemstack, entityliving, 2);
            }
        }
        return true;
    }

    public void bayonetDamage(final ItemStack itemstack, final EntityLivingBase entityliving, final int damage) {
        final EntityPlayer entityplayer = (EntityPlayer) entityliving;
        if (itemstack.getTagCompound() == null) {
            itemstack.setTagCompound(new NBTTagCompound());
        }
        int bayonetdamage = itemstack.getTagCompound().getShort("bayonetDamage") + damage;
        if (bayonetdamage > this.bayonetDurability) {
            entityplayer.renderBrokenItemStack(itemstack);
            final int id = Item.getIdFromItem(this);
            if (id != 0) {
                BalkonsWeaponMod.modLog.debug("Musket Item (" + this + ") ID = " + id);
                entityplayer.addStat(StatList.getObjectBreakStats(this));
            }
            bayonetdamage = 0;
            final ItemStack itemstack2 = new ItemStack(BalkonsWeaponMod.musket, 1, itemstack.getItemDamage());
            entityplayer.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, itemstack2);
            if (itemstack.getTagCompound().hasKey("rld")) {
                ReloadHelper.setReloadState(itemstack2, ReloadHelper.getReloadState(itemstack));
            }
        }
        itemstack.getTagCompound().setShort("bayonetDamage", (short) bayonetdamage);
    }
}
