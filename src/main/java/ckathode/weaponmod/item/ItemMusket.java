package ckathode.weaponmod.item;

import ckathode.weaponmod.BalkonsWeaponMod;
import ckathode.weaponmod.PhysHelper;
import ckathode.weaponmod.ReloadHelper;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
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
    @Nullable
    protected final Item bayonetItem;
    private final int bayonetDurability;

    public ItemMusket(String id, MeleeComponent meleecomponent, @Nullable Item bayonetitem) {
        super(id, new RangedCompMusket(), meleecomponent);
        bayonetItem = bayonetitem;
        bayonetDurability =
                meleecomponent.meleeSpecs != MeleeComponent.MeleeSpecs.NONE && meleecomponent.weaponMaterial != null
                        ? meleecomponent.meleeSpecs.durabilityBase +
                          (int) (meleecomponent.weaponMaterial.getMaxUses() * meleecomponent.meleeSpecs.durabilityMult)
                        : 0;
    }

    public boolean hasBayonet() {
        return bayonetItem != null;
    }

    @Override
    public boolean hitEntity(@Nonnull ItemStack itemstack, @Nonnull EntityLivingBase entityliving,
                             @Nonnull EntityLivingBase attacker) {
        if (hasBayonet()) {
            if (entityliving.hurtResistantTime == entityliving.maxHurtResistantTime) {
                float kb = meleeComponent.getKnockBack(itemstack, entityliving, attacker);
                PhysHelper.knockBack(entityliving, attacker, kb);
                entityliving.hurtResistantTime -= (int) (2.0f / meleeComponent.meleeSpecs.attackDelay);
            }
            if (attacker instanceof EntityPlayer && !((EntityPlayer) attacker).isCreative()) {
                bayonetDamage(itemstack, attacker, 1);
            }
        }
        return true;
    }

    @Override
    public boolean onBlockDestroyed(@Nonnull ItemStack itemstack, @Nonnull World world,
                                    @Nonnull IBlockState block, @Nonnull BlockPos pos,
                                    @Nonnull EntityLivingBase entityliving) {
        if (hasBayonet()) {
            Material material = block.getMaterial();
            boolean flag =
                    material != Material.PLANTS && material != Material.VINE && material != Material.CORAL && material != Material.LEAVES && material != Material.GOURD;
            if (entityliving instanceof EntityPlayer && !((EntityPlayer) entityliving).isCreative() && flag) {
                bayonetDamage(itemstack, entityliving, 2);
            }
        }
        return true;
    }

    public void bayonetDamage(ItemStack itemstack, EntityLivingBase entityliving, int damage) {
        EntityPlayer entityplayer = (EntityPlayer) entityliving;
        if (itemstack.getTagCompound() == null) {
            itemstack.setTagCompound(new NBTTagCompound());
        }
        int bayonetdamage = itemstack.getTagCompound().getShort("bayonetDamage") + damage;
        if (bayonetdamage > bayonetDurability) {
            entityplayer.renderBrokenItemStack(itemstack);
            entityplayer.addStat(StatList.getObjectBreakStats(this));
            bayonetdamage = 0;
            ItemStack itemstack2 = new ItemStack(BalkonsWeaponMod.musket, 1);
            itemstack2.setItemDamage(itemstack.getItemDamage());
            entityplayer.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, itemstack2);
            if (itemstack.getTagCompound().hasKey("rld")) {
                ReloadHelper.setReloadState(itemstack2, ReloadHelper.getReloadState(itemstack));
            }
        }
        itemstack.getTagCompound().setShort("bayonetDamage", (short) bayonetdamage);
    }
}
