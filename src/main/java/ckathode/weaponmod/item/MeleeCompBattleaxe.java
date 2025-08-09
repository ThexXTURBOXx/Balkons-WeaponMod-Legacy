package ckathode.weaponmod.item;

import ckathode.weaponmod.DamageSourceAxe;
import ckathode.weaponmod.WeaponModAttributes;
import com.google.common.collect.Multimap;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;

public class MeleeCompBattleaxe extends MeleeComponent {

    public MeleeCompBattleaxe(IItemTier itemTier) {
        super(MeleeSpecs.BATTLEAXE, itemTier);
    }

    @Override
    public boolean onLeftClickEntity(ItemStack itemstack, EntityPlayer player, Entity entity) {
        double mx = entity.motionX;
        double my = entity.motionY;
        double mz = entity.motionZ;
        int prevhurtres = entity.hurtResistantTime;
        int prevhurt = entity instanceof EntityLivingBase ? ((EntityLivingBase) entity).hurtTime : 0;
        entity.attackEntityFrom(new DamageSourceAxe(), getIgnoreArmorAmount(weaponMaterial));
        entity.motionX = mx;
        entity.motionY = my;
        entity.motionZ = mz;
        entity.hurtResistantTime = prevhurtres;
        if (entity instanceof EntityLivingBase)
            ((EntityLivingBase) entity).hurtTime = prevhurt;
        return super.onLeftClickEntity(itemstack, player, entity);
    }

    @Override
    public float getBlockDamage(ItemStack itemstack, IBlockState block) {
        return (block.getMaterial() == Material.WOOD) ? (weaponMaterial.getEfficiency() * 0.75f) :
                super.getBlockDamage(itemstack, block);
    }

    @Override
    public boolean canHarvestBlock(IBlockState block) {
        return block.getMaterial() == Material.WOOD;
    }

    @Override
    public boolean canApplyEnchantment(ItemStack stack, Enchantment enchantment) {
        return super.canApplyEnchantment(stack, enchantment) ||
               super.canApplyEnchantment(new ItemStack(Items.DIAMOND_AXE), enchantment);
    }

    @Override
    public void addItemAttributeModifiers(Multimap<String, AttributeModifier> multimap) {
        super.addItemAttributeModifiers(multimap);
        if (getIgnoreArmorAmount(weaponMaterial) != 0.0f) {
            multimap.put(WeaponModAttributes.IGNORE_ARMOUR_DAMAGE.getName(),
                    new AttributeModifier(IItemWeapon.IGNORE_ARMOUR_MODIFIER,
                            "Weapon ignore armour modifier", getIgnoreArmorAmount(weaponMaterial), 0));
        }
    }

    public float getIgnoreArmorAmount(IItemTier itemTier) {
        return 1.0f;
    }

}
