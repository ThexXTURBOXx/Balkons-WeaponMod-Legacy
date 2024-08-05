package ckathode.weaponmod.item;

import ckathode.weaponmod.WMDamageSources;
import ckathode.weaponmod.WMItemBuilder;
import ckathode.weaponmod.WeaponModAttributes;
import com.google.common.collect.Multimap;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.Vec3;

public class MeleeCompBattleaxe extends MeleeComponent {

    public static final String WOOD_ID = "battleaxe.wood";
    public static final ItemMelee WOOD_ITEM = WMItemBuilder.createStandardBattleaxe(Tiers.WOOD);

    public static final String STONE_ID = "battleaxe.stone";
    public static final ItemMelee STONE_ITEM = WMItemBuilder.createStandardBattleaxe(Tiers.STONE);

    public static final String IRON_ID = "battleaxe.iron";
    public static final ItemMelee IRON_ITEM = WMItemBuilder.createStandardBattleaxe(Tiers.IRON);

    public static final String GOLD_ID = "battleaxe.gold";
    public static final ItemMelee GOLD_ITEM = WMItemBuilder.createStandardBattleaxe(Tiers.GOLD);

    public static final String DIAMOND_ID = "battleaxe.diamond";
    public static final ItemMelee DIAMOND_ITEM = WMItemBuilder.createStandardBattleaxe(Tiers.DIAMOND);

    public static final String NETHERITE_ID = "battleaxe.netherite";
    public static final ItemMelee NETHERITE_ITEM = WMItemBuilder.createStandardBattleaxe(Tiers.NETHERITE);

    public MeleeCompBattleaxe(Tier itemTier) {
        super(MeleeSpecs.BATTLEAXE, itemTier);
    }

    @Override
    public boolean onLeftClickEntity(ItemStack itemstack, Player player, Entity entity) {
        if (entity instanceof LivingEntity living) {
            Vec3 motion = entity.getDeltaMovement();
            int prevhurtres = living.invulnerableTime;
            int prevhurt = living.hurtTime;
            living.hurt(player.damageSources().source(WMDamageSources.BATTLEAXE), getIgnoreArmorAmount(weaponMaterial));
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
    public boolean canHarvestBlock(BlockState block) {
        return block.getMaterial() == Material.WOOD;
    }

    @Override
    public void addItemAttributeModifiers(Multimap<Attribute, AttributeModifier> multimap) {
        super.addItemAttributeModifiers(multimap);
        if (getIgnoreArmorAmount(weaponMaterial) != 0.0f) {
            multimap.put(WeaponModAttributes.IGNORE_ARMOUR_DAMAGE,
                    new AttributeModifier(IItemWeapon.IGNORE_ARMOUR_MODIFIER,
                            "Weapon ignore armour modifier", getIgnoreArmorAmount(weaponMaterial),
                            AttributeModifier.Operation.ADDITION));
        }
    }

    public float getIgnoreArmorAmount(Tier tier) {
        return 1.0f;
    }

}
