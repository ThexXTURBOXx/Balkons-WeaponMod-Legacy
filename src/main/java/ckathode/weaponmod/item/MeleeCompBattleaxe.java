package ckathode.weaponmod.item;

import ckathode.weaponmod.DamageSourceAxe;
import ckathode.weaponmod.WeaponModAttributes;
import ckathode.weaponmod.entity.projectile.MaterialRegistry;
import com.google.common.collect.Multimap;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;

public class MeleeCompBattleaxe extends MeleeComponent {
    public static final int[] DEFAULT_IGNORES = new int[]{1, 1, 1, 1, 1};
    public int ignoreArmourAmount;

    public MeleeCompBattleaxe(IItemTier itemTier) {
        super(MeleeSpecs.BATTLEAXE, itemTier);
        int ordinal = MaterialRegistry.getOrdinal(itemTier);
        if (ordinal >= 0 && ordinal < MeleeCompBattleaxe.DEFAULT_IGNORES.length) {
            ignoreArmourAmount = MeleeCompBattleaxe.DEFAULT_IGNORES[ordinal];
        }
    }

    @Override
    public boolean onLeftClickEntity(ItemStack itemstack, EntityPlayer player, Entity entity) {
        if (entity instanceof EntityLivingBase) {
            EntityLivingBase living = (EntityLivingBase) entity;
            double mx = entity.motionX;
            double my = entity.motionY;
            double mz = entity.motionZ;
            int prevhurtres = living.hurtResistantTime;
            int prevhurt = living.hurtTime;
            living.attackEntityFrom(new DamageSourceAxe(), (float) ignoreArmourAmount);
            entity.motionX = mx;
            entity.motionY = my;
            entity.motionZ = mz;
            living.hurtResistantTime = prevhurtres;
            living.hurtTime = prevhurt;
        }
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
    public void addItemAttributeModifiers(Multimap<String, AttributeModifier> multimap) {
        super.addItemAttributeModifiers(multimap);
        multimap.put(WeaponModAttributes.IGNORE_ARMOUR_DAMAGE.getName(), new AttributeModifier(weapon.getUUID(),
                "Weapon ignore armour modifier", ignoreArmourAmount, 0));
    }

}
