package ckathode.weaponmod.item;

import ckathode.weaponmod.DamageSourceAxe;
import ckathode.weaponmod.WeaponModAttributes;
import com.google.common.collect.Multimap;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3d;

public class MeleeCompBattleaxe extends MeleeComponent {

    public MeleeCompBattleaxe(IItemTier itemTier) {
        super(MeleeSpecs.BATTLEAXE, itemTier);
    }

    @Override
    public boolean onLeftClickEntity(ItemStack itemstack, PlayerEntity player, Entity entity) {
        if (entity instanceof LivingEntity) {
            LivingEntity living = (LivingEntity) entity;
            Vec3d motion = entity.getMotion();
            int prevhurtres = living.hurtResistantTime;
            int prevhurt = living.hurtTime;
            living.attackEntityFrom(new DamageSourceAxe(), getIgnoreArmorAmount(weaponMaterial));
            entity.setMotion(motion);
            living.hurtResistantTime = prevhurtres;
            living.hurtTime = prevhurt;
        }
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
