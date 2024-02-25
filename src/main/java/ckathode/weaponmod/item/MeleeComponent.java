package ckathode.weaponmod.item;

import ckathode.weaponmod.PhysHelper;
import ckathode.weaponmod.WeaponModAttributes;
import com.google.common.collect.Multimap;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumAction;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item.Properties;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTier;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class MeleeComponent extends AbstractWeaponComponent {
    public final MeleeSpecs meleeSpecs;
    public final IItemTier weaponMaterial;

    public MeleeComponent(final MeleeSpecs meleespecs, final IItemTier itemTier) {
        this.meleeSpecs = meleespecs;
        this.weaponMaterial = itemTier;
    }

    @Override
    protected void onSetItem() {
    }

    @Override
    public Properties setProperties(Properties properties) {
        return properties.defaultMaxDamage(this.weaponMaterial == null
                ? this.meleeSpecs.durabilityBase
                : (int) (this.meleeSpecs.durabilityBase
                         + this.weaponMaterial.getMaxUses() * this.meleeSpecs.durabilityMult));
    }

    @Override
    public float getEntityDamageMaterialPart() {
        if (this.weaponMaterial == null) {
            return 0.0f;
        }
        return this.weaponMaterial.getAttackDamage() * this.meleeSpecs.damageMult;
    }

    @Override
    public float getEntityDamage() {
        return this.meleeSpecs.damageBase + this.getEntityDamageMaterialPart();
    }

    @Override
    public float getBlockDamage(final ItemStack itemstack, final IBlockState block) {
        if (this.canHarvestBlock(block)) {
            return this.meleeSpecs.blockDamage * 10.0f;
        }
        final Material material = block.getMaterial();
        return (material != Material.PLANTS && material != Material.VINE && material != Material.CORAL && material != Material.LEAVES && material != Material.GOURD) ? 1.0f : this.meleeSpecs.blockDamage;
    }

    @Override
    public boolean canHarvestBlock(final IBlockState state) {
        final Block block = state.getBlock();
        return block == Blocks.COBWEB;
    }

    @Override
    public boolean onBlockDestroyed(final ItemStack itemstack, final World world, final IBlockState block,
                                    final BlockPos pos, final EntityLivingBase entityliving) {
        if (block.getBlockHardness(world, pos) != 0.0f) {
            itemstack.damageItem(this.meleeSpecs.dmgFromBlock, entityliving);
        }
        return true;
    }

    @Override
    public boolean hitEntity(final ItemStack itemstack, final EntityLivingBase entityliving,
                             final EntityLivingBase attacker) {
        if (entityliving.hurtResistantTime == entityliving.maxHurtResistantTime) {
            final float kb = this.getKnockBack(itemstack, entityliving, attacker);
            PhysHelper.knockBack(entityliving, attacker, kb);
            if (this.meleeSpecs.attackDelay >= 3.0f) {
                entityliving.hurtResistantTime += (int) this.getAttackDelay(itemstack, entityliving, attacker);
            } else {
                final float f = (this.meleeSpecs.attackDelay < 1.0f) ? 1.2f : 2.0f;
                entityliving.hurtResistantTime -= (int) (f / this.getAttackDelay(itemstack, entityliving, attacker));
            }
        }
        itemstack.damageItem(this.meleeSpecs.dmgFromEntity, attacker);
        return true;
    }

    @Override
    public float getAttackDelay(final ItemStack itemstack, final EntityLivingBase entityliving,
                                final EntityLivingBase attacker) {
        return this.meleeSpecs.attackDelay;
    }

    @Override
    public float getKnockBack(final ItemStack itemstack, final EntityLivingBase entityliving,
                              final EntityLivingBase attacker) {
        return this.meleeSpecs.getKnockBack(this.weaponMaterial);
    }

    @Override
    public int getItemEnchantability() {
        return (this.weaponMaterial == null) ? 1 : this.weaponMaterial.getEnchantability();
    }

    @Override
    public void addItemAttributeModifiers(final Multimap<String, AttributeModifier> multimap) {
        final float dmg = this.getEntityDamage();
        if (dmg > 0.0f || this.meleeSpecs.damageMult > 0.0f) {
            multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(),
                    new AttributeModifier(this.weapon.getUUIDDamage(), "Weapon attack damage modifier", dmg, 0));
            multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(),
                    new AttributeModifier(this.weapon.getUUIDSpeed(), "Weapon attack speed modifier",
                            -this.meleeSpecs.attackDelay, 0));
        }
        if (this.meleeSpecs.getKnockBack(this.weaponMaterial) != 0.4f) {
            multimap.put(WeaponModAttributes.WEAPON_KNOCKBACK.getName(), new AttributeModifier(this.weapon.getUUID(),
                    "Weapon knockback modifier", this.meleeSpecs.getKnockBack(this.weaponMaterial) - 0.4f, 0));
        }
        if (this instanceof IExtendedReachItem) {
            try {
                multimap.put(WeaponModAttributes.WEAPON_REACH.getName(), new AttributeModifier(this.weapon.getUUID(),
                        "Weapon reach modifier",
                        ((IExtendedReachItem) this).getExtendedReach(null, null, null) - 3.0f, 0));
            } catch (final NullPointerException ignored) {
            }
        }
    }

    @Override
    public boolean onLeftClickEntity(final ItemStack itemstack, final EntityPlayer player, final Entity entity) {
        if (entity instanceof EntityLivingBase) {
            PhysHelper.prepareKnockbackOnEntity(player, (EntityLivingBase) entity);
        }
        return false;
    }

    @Override
    public EnumAction getUseAction(final ItemStack itemstack) {
        return EnumAction.NONE;
    }

    @Override
    public int getUseDuration(final ItemStack itemstack) {
        return 0;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(final World world, final EntityPlayer entityplayer,
                                                    final EnumHand hand) {
        final ItemStack itemstack = entityplayer.getHeldItem(hand);
        return new ActionResult<>(EnumActionResult.PASS, itemstack);
    }

    @Override
    public void onUsingTick(final ItemStack itemstack, final EntityLivingBase entityliving, final int count) {
    }

    @Override
    public void onPlayerStoppedUsing(final ItemStack itemstack, final World world,
                                     final EntityLivingBase entityliving, final int i) {
    }

    @Override
    public void inventoryTick(final ItemStack itemstack, final World world, final Entity entity, final int i,
                              final boolean flag) {
    }

    public enum MeleeSpecs {
        SPEAR(0, 1.0f, 2.0f, 1.0f, 1.0f, 0.2f, 1, 2, 2.7f),
        HALBERD(0, 1.0f, 3.0f, 1.0f, 1.5f, 0.6f, 1, 2, 3.2f),
        BATTLEAXE(0, 1.0f, 2.0f, 1.0f, 1.5f, 0.5f, 1, 2, 3.0f),
        WARHAMMER(0, 1.0f, 3.0f, 1.0f, 1.0f, 0.7f, 1, 2, 3.0f),
        KNIFE(0, 0.5f, 2.0f, 1.0f, 1.5f, 0.2f, 1, 2, 2.0f),
        KATANA(0, 1.0f, 0.0f, 1.0f, 1.0f, 0.0f, 1, 2, 0.2f),
        FIREROD(1, 0.0f, 0.0f, 0.0f, 1.0f, 0.4f, 2, 0, 0.0f),
        BOOMERANG(0, 0.5f, 1.0f, 1.0f, 1.0f, 0.4f, 1, 1, 2.0f),
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

        MeleeSpecs(final int durbase, final float durmult, final float dmgbase, final float dmgmult,
                   final float blockdmg, final float knockback, final int dmgfromentity, final int dmgfromblock,
                   final float attackdelay) {
            this.durabilityBase = durbase;
            this.durabilityMult = durmult;
            this.damageBase = dmgbase;
            this.damageMult = dmgmult;
            this.blockDamage = blockdmg;
            this.knockback = knockback;
            this.dmgFromEntity = dmgfromentity;
            this.dmgFromBlock = dmgfromblock;
            this.attackDelay = attackdelay;
        }

        public float getKnockBack(final IItemTier material) {
            return (material == ItemTier.GOLD) ? (this.knockback * 1.5f) : this.knockback;
        }
    }
}
