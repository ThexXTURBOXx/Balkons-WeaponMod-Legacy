package ckathode.weaponmod.item;

import ckathode.weaponmod.DamageSourceAxe;
import ckathode.weaponmod.WeaponModAttributes;
import ckathode.weaponmod.entity.projectile.MaterialRegistry;
import com.google.common.collect.Multimap;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.vector.Vector3d;

public class MeleeCompBattleaxe extends MeleeComponent {
    public static final float[] DEFAULT_IGNORES = new float[]{1, 1, 1, 1, 1};
    public final float ignoreArmourAmount;

    public MeleeCompBattleaxe(IItemTier itemTier) {
        super(MeleeSpecs.BATTLEAXE, itemTier);
        int ordinal = MaterialRegistry.getOrdinal(itemTier);
        ignoreArmourAmount = ordinal >= 0 && ordinal < DEFAULT_IGNORES.length
                ? DEFAULT_IGNORES[ordinal] : 0;
    }

    @Override
    public boolean onLeftClickEntity(ItemStack itemstack, PlayerEntity player, Entity entity) {
        if (entity instanceof LivingEntity) {
            LivingEntity living = (LivingEntity) entity;
            Vector3d motion = entity.getDeltaMovement();
            int prevhurtres = living.invulnerableTime;
            int prevhurt = living.hurtTime;
            living.hurt(new DamageSourceAxe(), ignoreArmourAmount);
            entity.setDeltaMovement(motion);
            living.invulnerableTime = prevhurtres;
            living.hurtTime = prevhurt;
        }
        return super.onLeftClickEntity(itemstack, player, entity);
    }

    @Override
    public float getDestroySpeed(ItemStack itemstack, BlockState block) {
        return (block.getMaterial() == Material.WOOD) ? (weaponMaterial.getSpeed() * 0.75f) :
                super.getDestroySpeed(itemstack, block);
    }

    @Override
    public boolean canHarvestBlock(ItemStack stack, BlockState block) {
        return block.getMaterial() == Material.WOOD;
    }

    @Override
    public void addItemAttributeModifiers(Multimap<Attribute, AttributeModifier> multimap) {
        super.addItemAttributeModifiers(multimap);
        multimap.put(WeaponModAttributes.IGNORE_ARMOUR_DAMAGE, new AttributeModifier(weapon.getUUID(),
                "Weapon ignore armour modifier", ignoreArmourAmount, AttributeModifier.Operation.ADDITION));
    }

}
