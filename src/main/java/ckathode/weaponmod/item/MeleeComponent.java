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
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class MeleeComponent extends AbstractWeaponComponent {
    public final MeleeSpecs meleeSpecs;
    public final Item.ToolMaterial weaponMaterial;

    public MeleeComponent(MeleeSpecs meleespecs, Item.ToolMaterial toolmaterial) {
        meleeSpecs = meleespecs;
        weaponMaterial = toolmaterial;
    }

    @Override
    protected void onSetItem() {
    }

    @Override
    public void setThisItemProperties() {
        item.setMaxDamage(weaponMaterial == null
                ? meleeSpecs.durabilityBase
                : (int) (meleeSpecs.durabilityBase
                         + weaponMaterial.getMaxUses() * meleeSpecs.durabilityMult));
    }

    @Override
    public float getEntityDamageMaterialPart() {
        if (weaponMaterial == null) {
            return 0.0f;
        }
        return weaponMaterial.getDamageVsEntity() * meleeSpecs.damageMult;
    }

    @Override
    public float getEntityDamage() {
        return meleeSpecs.damageBase + getEntityDamageMaterialPart();
    }

    @Override
    public float getBlockDamage(ItemStack itemstack, IBlockState block) {
        if (canHarvestBlock(block)) {
            return meleeSpecs.blockDamage * 10.0f;
        }
        Material material = block.getMaterial();
        return (material != Material.PLANTS && material != Material.VINE && material != Material.CORAL && material != Material.LEAVES && material != Material.GOURD) ? 1.0f : meleeSpecs.blockDamage;
    }

    @Override
    public boolean canHarvestBlock(IBlockState state) {
        Block block = state.getBlock();
        return block == Blocks.WEB;
    }

    @Override
    public boolean onBlockDestroyed(ItemStack itemstack, World world, IBlockState block,
                                    BlockPos pos, EntityLivingBase entityliving) {
        if (block.getBlockHardness(world, pos) != 0.0f) {
            itemstack.damageItem(meleeSpecs.dmgFromBlock, entityliving);
            if (itemstack.stackSize <= 0 && entityliving instanceof EntityPlayer) {
                ((EntityPlayer) entityliving).inventory.deleteStack(itemstack);
            }
        }
        return true;
    }

    @Override
    public boolean hitEntity(ItemStack itemstack, EntityLivingBase entityliving,
                             EntityLivingBase attacker) {
        if (entityliving.hurtResistantTime == entityliving.maxHurtResistantTime) {
            float kb = getKnockBack(itemstack, entityliving, attacker);
            PhysHelper.knockBack(entityliving, attacker, kb);
            if (meleeSpecs.attackDelay >= 3.0f) {
                entityliving.hurtResistantTime += (int) getAttackDelay(itemstack, entityliving, attacker);
            } else {
                float f = (meleeSpecs.attackDelay < 1.0f) ? 1.2f : 2.0f;
                entityliving.hurtResistantTime -= (int) (f / getAttackDelay(itemstack, entityliving, attacker));
            }
        }
        itemstack.damageItem(meleeSpecs.dmgFromEntity, attacker);
        if (itemstack.stackSize <= 0 && attacker instanceof EntityPlayer) {
            ((EntityPlayer) attacker).inventory.deleteStack(itemstack);
        }
        return true;
    }

    @Override
    public float getAttackDelay(ItemStack itemstack, EntityLivingBase entityliving,
                                EntityLivingBase attacker) {
        return meleeSpecs.attackDelay;
    }

    @Override
    public float getKnockBack(ItemStack itemstack, EntityLivingBase entityliving,
                              EntityLivingBase attacker) {
        return meleeSpecs.getKnockBack(weaponMaterial);
    }

    @Override
    public int getItemEnchantability() {
        return (weaponMaterial == null) ? 1 : weaponMaterial.getEnchantability();
    }

    @Override
    public void addItemAttributeModifiers(Multimap<String, AttributeModifier> multimap) {
        float dmg = getEntityDamage();
        if (dmg > 0.0f || meleeSpecs.damageMult > 0.0f) {
            multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(),
                    new AttributeModifier(IItemWeapon.ATTACK_DAMAGE_MODIFIER,
                            "Weapon attack damage modifier", dmg, 0));
            multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(),
                    new AttributeModifier(IItemWeapon.ATTACK_SPEED_MODIFIER,
                            "Weapon attack speed modifier", -meleeSpecs.attackDelay, 0));
        }
        if (meleeSpecs.getKnockBack(weaponMaterial) != 0.4f) {
            multimap.put(WeaponModAttributes.WEAPON_KNOCKBACK.getName(),
                    new AttributeModifier(IItemWeapon.KNOCKBACK_MODIFIER,
                            "Weapon knockback modifier", meleeSpecs.getKnockBack(weaponMaterial) - 0.4f, 0));
        }
        if (this instanceof IExtendedReachItem) {
            try {
                multimap.put(WeaponModAttributes.WEAPON_REACH.getName(),
                        new AttributeModifier(IItemWeapon.REACH_MODIFIER,
                                "Weapon reach modifier",
                                ((IExtendedReachItem) this).getExtendedReach(null, null, null) - 3.0f, 0));
            } catch (NullPointerException ignored) {
            }
        }
    }

    @Override
    public boolean onLeftClickEntity(ItemStack itemstack, EntityPlayer player, Entity entity) {
        if (entity instanceof EntityLivingBase) {
            PhysHelper.prepareKnockbackOnEntity(player, (EntityLivingBase) entity);
        }
        return false;
    }

    @Override
    public EnumAction getItemUseAction(ItemStack itemstack) {
        return EnumAction.BLOCK;
    }

    @Override
    public int getMaxItemUseDuration(ItemStack itemstack) {
        return 72000;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer entityplayer,
                                                    EnumHand hand) {
        ItemStack itemstack = entityplayer.getHeldItem(hand);
        if (getItemUseAction(itemstack) != EnumAction.NONE)
            entityplayer.setActiveHand(hand);
        return new ActionResult<>(EnumActionResult.PASS, itemstack);
    }

    @Override
    public void onUsingTick(ItemStack itemstack, EntityLivingBase entityliving, int count) {
    }

    @Override
    public void onPlayerStoppedUsing(ItemStack itemstack, World world,
                                     EntityLivingBase entityliving, int i) {
    }

    @Override
    public void onUpdate(ItemStack itemstack, World world, Entity entity, int i,
                         boolean flag) {
    }

    @SideOnly(Side.CLIENT)
    public boolean shouldRenderCooldown() {
        return false;
    }

    @SideOnly(Side.CLIENT)
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
        NONE(0, 0.0f, 1.0f, 0.0f, 1.0f, 0.4f, 0, 0, 0.0f);

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
                   float blockdmg, float knockback, int dmgfromentity, int dmgfromblock,
                   float attackdelay) {
            durabilityBase = durbase;
            durabilityMult = durmult;
            damageBase = dmgbase;
            damageMult = dmgmult;
            blockDamage = blockdmg;
            this.knockback = knockback;
            dmgFromEntity = dmgfromentity;
            dmgFromBlock = dmgfromblock;
            attackDelay = attackdelay;
        }

        public float getKnockBack(final Item.ToolMaterial material) {
            return (material == Item.ToolMaterial.GOLD) ? (knockback * 1.5f) : knockback;
        }
    }
}
