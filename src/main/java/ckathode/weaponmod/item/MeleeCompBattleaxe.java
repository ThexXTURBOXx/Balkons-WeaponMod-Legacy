package ckathode.weaponmod.item;

import ckathode.weaponmod.DamageSourceAxe;
import ckathode.weaponmod.WeaponModAttributes;
import com.google.common.collect.Multimap;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.Vec3d;

public class MeleeCompBattleaxe extends MeleeComponent {

    public MeleeCompBattleaxe(IItemTier itemTier) {
        super(MeleeSpecs.BATTLEAXE, itemTier);
    }

    @Override
    public boolean onLeftClickEntity(ItemStack itemstack, PlayerEntity player, Entity entity) {
        Vec3d motion = entity.getMotion();
        int prevhurtres = entity.hurtResistantTime;
        int prevhurt = entity instanceof LivingEntity ? ((LivingEntity) entity).hurtTime : 0;
        entity.attackEntityFrom(new DamageSourceAxe(), getIgnoreArmorAmount(weaponMaterial));
        entity.setMotion(motion);
        entity.hurtResistantTime = prevhurtres;
        if (entity instanceof LivingEntity)
            ((LivingEntity) entity).hurtTime = prevhurt;
        return super.onLeftClickEntity(itemstack, player, entity);
    }

    @Override
    public float getBlockDamage(ItemStack itemstack, BlockState block) {
        return (block.getMaterial() == Material.WOOD) ? (weaponMaterial.getEfficiency() * 0.75f) :
                super.getBlockDamage(itemstack, block);
    }

    @Override
    public boolean canHarvestBlock(BlockState block) {
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
                            "Weapon ignore armour modifier", getIgnoreArmorAmount(weaponMaterial),
                            AttributeModifier.Operation.ADDITION));
        }
    }

    public float getIgnoreArmorAmount(IItemTier itemTier) {
        return 1.0f;
    }

}
