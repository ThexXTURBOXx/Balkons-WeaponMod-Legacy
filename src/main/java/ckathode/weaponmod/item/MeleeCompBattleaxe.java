package ckathode.weaponmod.item;

import ckathode.weaponmod.DamageSourceAxe;
import ckathode.weaponmod.WeaponModAttributes;
import com.google.common.collect.Multimap;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class MeleeCompBattleaxe extends MeleeComponent {

    public MeleeCompBattleaxe(Item.ToolMaterial toolmaterial) {
        super(MeleeSpecs.BATTLEAXE, toolmaterial);
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
            living.attackEntityFrom(new DamageSourceAxe(), getIgnoreArmorAmount(weaponMaterial));
            entity.motionX = mx;
            entity.motionY = my;
            entity.motionZ = mz;
            living.hurtResistantTime = prevhurtres;
            living.hurtTime = prevhurt;
        }
        return super.onLeftClickEntity(itemstack, player, entity);
    }

    @Override
    public float getBlockDamage(ItemStack itemstack, Block block) {
        return (block.getMaterial() == Material.wood) ? (weaponMaterial.getEfficiencyOnProperMaterial() * 0.75f) :
                super.getBlockDamage(itemstack, block);
    }

    @Override
    public boolean canHarvestBlock(Block block) {
        return block.getMaterial() == Material.wood;
    }

    @Override
    public void addItemAttributeModifiers(Multimap<String, AttributeModifier> multimap) {
        super.addItemAttributeModifiers(multimap);
        if (getIgnoreArmorAmount(weaponMaterial) != 0.0f) {
            multimap.put(WeaponModAttributes.IGNORE_ARMOUR_DAMAGE.getAttributeUnlocalizedName(),
                    new AttributeModifier(IItemWeapon.IGNORE_ARMOUR_MODIFIER,
                            "Weapon ignore armour modifier", getIgnoreArmorAmount(weaponMaterial), 0));
        }
    }

    public float getIgnoreArmorAmount(Item.ToolMaterial material) {
        return 1.0f;
    }

}
