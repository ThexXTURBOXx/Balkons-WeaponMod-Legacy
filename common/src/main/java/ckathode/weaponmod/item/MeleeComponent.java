package ckathode.weaponmod.item;

import ckathode.weaponmod.PhysHelper;
import ckathode.weaponmod.WMRegistries;
import ckathode.weaponmod.WeaponModAttributes;
import java.util.List;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.component.Tool;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class MeleeComponent extends AbstractWeaponComponent {

    public final MeleeSpecs meleeSpecs;
    public final Tier weaponMaterial;

    public MeleeComponent(MeleeSpecs meleespecs, Tier itemTier) {
        meleeSpecs = meleespecs;
        weaponMaterial = itemTier;
    }

    @NotNull
    public Tier getWeaponMaterial() {
        return weaponMaterial == null ? Tiers.WOOD : weaponMaterial;
    }

    @Override
    protected void onSetItem() {
    }

    @NotNull
    public Tool getToolComponent() {
        return new Tool(List.of(
                Tool.Rule.minesAndDrops(List.of(Blocks.COBWEB), meleeSpecs.blockDamage * 10),
                Tool.Rule.overrideSpeed(BlockTags.SWORD_EFFICIENT, meleeSpecs.blockDamage)),
                1.0F, 2);
    }

    @Override
    public ItemAttributeModifiers.Builder setAttributes(ItemAttributeModifiers.Builder attributeBuilder) {
        float dmg = getEntityDamage();
        if (dmg > 0.0f || meleeSpecs.damageMult > 0.0f) {
            attributeBuilder = attributeBuilder
                    .add(Attributes.ATTACK_DAMAGE, new AttributeModifier(Item.BASE_ATTACK_DAMAGE_ID,
                                    dmg, AttributeModifier.Operation.ADD_VALUE),
                            EquipmentSlotGroup.MAINHAND)
                    .add(Attributes.ATTACK_SPEED, new AttributeModifier(Item.BASE_ATTACK_SPEED_ID,
                                    -meleeSpecs.attackDelay, AttributeModifier.Operation.ADD_VALUE),
                            EquipmentSlotGroup.MAINHAND);
        }
        if (meleeSpecs.getKnockBack(weaponMaterial) != 0.4f) {
            attributeBuilder = attributeBuilder
                    .add(WMRegistries.WEAPON_KNOCKBACK, new AttributeModifier(WeaponModAttributes.WEAPON_KNOCKBACK_ID,
                                    meleeSpecs.getKnockBack(weaponMaterial) - 0.4f,
                                    AttributeModifier.Operation.ADD_VALUE),
                            EquipmentSlotGroup.MAINHAND);
        }
        if (this instanceof IExtendedReachItem) {
            try {
                attributeBuilder = attributeBuilder
                        .add(WMRegistries.WEAPON_REACH, new AttributeModifier(WeaponModAttributes.WEAPON_REACH_ID,
                                        ((IExtendedReachItem) this).getExtendedReach(null, null, null) - 3.0f,
                                        AttributeModifier.Operation.ADD_VALUE),
                                EquipmentSlotGroup.MAINHAND);
            } catch (NullPointerException ignored) {
            }
        }
        return attributeBuilder;
    }

    @Override
    public Properties setProperties(Properties properties) {
        return properties
                .component(DataComponents.TOOL, getToolComponent())
                .durability(weaponMaterial == null
                        ? meleeSpecs.durabilityBase
                        : (int) (meleeSpecs.durabilityBase
                                 + weaponMaterial.getUses() * meleeSpecs.durabilityMult));
    }

    @Override
    public float getDamage() {
        if (weaponMaterial == null) {
            return 0.0f;
        }
        return weaponMaterial.getAttackDamageBonus() * meleeSpecs.damageMult;
    }

    @Override
    public float getEntityDamage() {
        return meleeSpecs.damageBase + getDamage();
    }

    @Override
    public boolean mineBlock(ItemStack itemstack, Level world, BlockState block,
                             BlockPos pos, LivingEntity entityliving) {
        if (block.getDestroySpeed(world, pos) != 0.0f) {
            itemstack.hurtAndBreak(meleeSpecs.dmgFromBlock, entityliving, EquipmentSlot.MAINHAND);
        }
        return true;
    }

    @Override
    public boolean hurtEnemy(ItemStack itemstack, LivingEntity entityliving,
                             LivingEntity attacker) {
        if (entityliving.invulnerableTime == entityliving.invulnerableDuration) {
            float kb = getKnockBack(itemstack, entityliving, attacker);
            PhysHelper.knockBack(entityliving, attacker, kb);
            if (meleeSpecs.attackDelay >= 3.0f) {
                entityliving.invulnerableTime += (int) getAttackDelay(itemstack, entityliving, attacker);
            } else {
                float f = (meleeSpecs.attackDelay < 1.0f) ? 1.2f : 2.0f;
                entityliving.invulnerableTime -= (int) (f / getAttackDelay(itemstack, entityliving, attacker));
            }
        }
        itemstack.hurtAndBreak(meleeSpecs.dmgFromEntity, attacker, EquipmentSlot.MAINHAND);
        return true;
    }

    @Override
    public float getAttackDelay(ItemStack itemstack, LivingEntity entityliving,
                                LivingEntity attacker) {
        return meleeSpecs.attackDelay;
    }

    @Override
    public float getKnockBack(ItemStack itemstack, LivingEntity entityliving,
                              LivingEntity attacker) {
        return meleeSpecs.getKnockBack(weaponMaterial);
    }

    @Override
    public int getEnchantmentValue() {
        return (weaponMaterial == null) ? 1 : weaponMaterial.getEnchantmentValue();
    }

    @Override
    public boolean onLeftClickEntity(ItemStack itemstack, Player player, Entity entity) {
        if (entity instanceof LivingEntity) {
            PhysHelper.prepareKnockbackOnEntity(player, (LivingEntity) entity);
        }
        return false;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack itemstack) {
        return UseAnim.BLOCK;
    }

    @Override
    public int getUseDuration(ItemStack itemstack) {
        return 72000;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player entityplayer,
                                                  InteractionHand hand) {
        ItemStack itemstack = entityplayer.getItemInHand(hand);
        if (getUseAnimation(itemstack) != UseAnim.NONE)
            entityplayer.startUsingItem(hand);
        return new InteractionResultHolder<>(InteractionResult.PASS, itemstack);
    }

    @Override
    public void onUsingTick(Level level, LivingEntity livingEntity, ItemStack stack, int remainingUseDuration) {
    }

    @Override
    public void releaseUsing(ItemStack itemstack, Level world,
                             LivingEntity entityliving, int i) {
    }

    @Override
    public void inventoryTick(ItemStack itemstack, Level world, Entity entity, int i,
                              boolean flag) {
    }

    @Environment(EnvType.CLIENT)
    public boolean shouldRenderCooldown() {
        return false;
    }

    @Environment(EnvType.CLIENT)
    public float getCooldown() {
        return 0;
    }

    public enum MeleeSpecs {
        SPEAR(0, 1.0f, 3.0f, 1.0f, 1.0f, 0.2f, 1, 2, 2.7f),
        HALBERD(0, 1.0f, 4.0f, 1.0f, 1.5f, 0.6f, 1, 2, 3.2f),
        BATTLEAXE(0, 1.0f, 3.0f, 1.0f, 1.5f, 0.5f, 1, 2, 3.0f),
        WARHAMMER(0, 1.0f, 4.0f, 1.0f, 1.0f, 0.7f, 1, 2, 3.0f),
        KNIFE(0, 0.5f, 3.0f, 1.0f, 1.5f, 0.2f, 1, 2, 2.0f),
        KATANA(0, 1.0f, 1.0f, 1.0f, 1.0f, 0.0f, 1, 2, 0.2f),
        FIREROD(1, 0.0f, 1.0f, 0.0f, 1.0f, 0.4f, 2, 0, 0.0f),
        BOOMERANG(0, 0.5f, 2.0f, 1.0f, 1.0f, 0.4f, 1, 1, 2.0f),
        NONE(0, 1.0f, 1.0f, 0.0f, 1.0f, 0.4f, 0, 0, 0.0f);

        public final int durabilityBase;
        public final float durabilityMult;
        public final float damageBase;
        public final float damageMult;
        public final float blockDamage;
        public final float knockback;
        public final float attackDelay;
        public final int dmgFromEntity;
        public final int dmgFromBlock;

        MeleeSpecs(int durbase, float durmult, float dmgbase, float dmgmult,
                   float blockdmg, float knockbck, int dmgfromentity, int dmgfromblock,
                   float attackdelay) {
            durabilityBase = durbase;
            durabilityMult = durmult;
            damageBase = dmgbase;
            damageMult = dmgmult;
            blockDamage = blockdmg;
            knockback = knockbck;
            dmgFromEntity = dmgfromentity;
            dmgFromBlock = dmgfromblock;
            attackDelay = attackdelay;
        }

        public float getKnockBack(Tier material) {
            return (material == Tiers.GOLD) ? (knockback * 1.5f) : knockback;
        }
    }

}
