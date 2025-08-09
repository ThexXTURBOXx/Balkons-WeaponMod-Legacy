package ckathode.weaponmod.item;

import ckathode.weaponmod.BalkonsWeaponMod;
import ckathode.weaponmod.PhysHelper;
import ckathode.weaponmod.ReloadHelper;
import java.util.List;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.stats.StatList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ItemMusket extends ItemShooter {
    @Nullable
    protected final Item bayonetItem;
    private final int bayonetDurability;

    public ItemMusket(String id, MeleeComponent meleecomponent, @Nullable Item bayonetitem) {
        this(BalkonsWeaponMod.MOD_ID, id, meleecomponent, bayonetitem);
    }

    public ItemMusket(String modId, String id, MeleeComponent meleecomponent, @Nullable Item bayonetitem) {
        super(modId, id, new RangedCompMusket(), meleecomponent);
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
    public boolean canApplyAtEnchantingTable(@NotNull ItemStack stack, @NotNull Enchantment enchantment) {
        return rangedComponent.canApplyEnchantment(stack, enchantment); // do not allow melee enchantments
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip,
                               ITooltipFlag flag) {
        super.addInformation(stack, world, tooltip, flag);
        // 2 is ID for short
        if (hasBayonet() && stack.hasTag() && stack.getTag().contains("bayonetDamage", 2)) {
            short dmg = stack.getTag().getShort("bayonetDamage");
            if (dmg != 0) {
                tooltip.add(new TextComponentTranslation("tooltip.bayonetdurability",
                        bayonetDurability - dmg, bayonetDurability));
            }
        }
    }

    @Override
    public boolean hitEntity(@NotNull ItemStack itemstack, @NotNull EntityLivingBase entityliving,
                             @NotNull EntityLivingBase attacker) {
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
    public boolean onBlockDestroyed(@NotNull ItemStack itemstack, @NotNull World world,
                                    @NotNull IBlockState block, @NotNull BlockPos pos,
                                    @NotNull EntityLivingBase entityliving) {
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
        if (itemstack.getTag() == null) {
            itemstack.setTag(new NBTTagCompound());
        }
        int bayonetdamage = itemstack.getTag().getShort("bayonetDamage") + damage;
        if (bayonetdamage > bayonetDurability) {
            entityliving.renderBrokenItemStack(itemstack);
            if (entityliving instanceof EntityPlayer)
                ((EntityPlayer) entityliving).addStat(StatList.ITEM_BROKEN.get(this));
            bayonetdamage = 0;
            ItemStack itemstack2 = new ItemStack(BalkonsWeaponMod.musket, 1);
            itemstack2.setDamage(itemstack.getDamage());
            entityliving.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, itemstack2);
            if (itemstack.getTag().contains("rld")) {
                ReloadHelper.setReloadState(itemstack2, ReloadHelper.getReloadState(itemstack));
            }
        }
        itemstack.getTag().putShort("bayonetDamage", (short) bayonetdamage);
    }
}
