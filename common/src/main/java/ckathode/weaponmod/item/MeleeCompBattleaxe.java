package ckathode.weaponmod.item;

import ckathode.weaponmod.WMDamageSources;
import ckathode.weaponmod.WMItemBuilder;
import ckathode.weaponmod.WMRegistries;
import ckathode.weaponmod.WeaponModAttributes;
import java.util.List;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.component.Tool;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

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

    @NotNull
    @Override
    public Tool getToolComponent() {
        return new Tool(List.of(
                Tool.Rule.deniesDrops(weaponMaterial.getIncorrectBlocksForDrops()),
                Tool.Rule.minesAndDrops(BlockTags.MINEABLE_WITH_AXE, weaponMaterial.getSpeed())),
                0.75f, 1);
    }

    @Override
    public ItemAttributeModifiers.Builder setAttributes(ItemAttributeModifiers.Builder attributeBuilder) {
        attributeBuilder = super.setAttributes(attributeBuilder);
        if (getIgnoreArmorAmount(weaponMaterial) != 0.0f) {
            attributeBuilder = attributeBuilder
                    .add(WMRegistries.IGNORE_ARMOUR_DAMAGE, new AttributeModifier(
                                    WeaponModAttributes.IGNORE_ARMOUR_DAMAGE_ID,
                                    getIgnoreArmorAmount(weaponMaterial), AttributeModifier.Operation.ADD_VALUE),
                            EquipmentSlotGroup.MAINHAND);
        }
        return attributeBuilder;
    }

    public float getIgnoreArmorAmount(Tier tier) {
        return 1.0f;
    }

}
