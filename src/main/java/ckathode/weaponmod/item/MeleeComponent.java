package ckathode.weaponmod.item;

import net.minecraft.block.state.*;
import net.minecraft.block.material.*;
import net.minecraft.init.*;
import net.minecraft.block.*;
import net.minecraft.world.*;
import net.minecraft.util.math.*;
import com.google.common.collect.*;
import net.minecraft.entity.ai.attributes.*;
import ckathode.weaponmod.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;
import net.minecraft.item.*;
import net.minecraft.util.*;

public class MeleeComponent extends AbstractWeaponComponent
{
    public final MeleeSpecs meleeSpecs;
    public final Item.ToolMaterial weaponMaterial;
    
    public MeleeComponent(final MeleeSpecs meleespecs, final Item.ToolMaterial toolmaterial) {
        this.meleeSpecs = meleespecs;
        this.weaponMaterial = toolmaterial;
    }
    
    @Override
    protected void onSetItem() {
    }
    
    @Override
    public void setThisItemProperties() {
        if (this.weaponMaterial == null) {
            this.item.setMaxDamage(this.meleeSpecs.durabilityBase);
        }
        else {
            this.item.setMaxDamage((int)(this.meleeSpecs.durabilityBase + this.weaponMaterial.getMaxUses() * this.meleeSpecs.durabilityMult));
        }
        this.item.setMaxStackSize(this.meleeSpecs.stackSize);
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
        return block == Blocks.WEB;
    }
    
    @Override
    public boolean onBlockDestroyed(final ItemStack itemstack, final World world, final IBlockState block, final BlockPos pos, final EntityLivingBase entityliving) {
        if (block.getBlockHardness(world, pos) != 0.0f) {
            itemstack.damageItem(this.meleeSpecs.dmgFromBlock, entityliving);
        }
        return true;
    }
    
    @Override
    public boolean hitEntity(final ItemStack itemstack, final EntityLivingBase entityliving, final EntityLivingBase attacker) {
        if (entityliving.hurtResistantTime == entityliving.maxHurtResistantTime) {
            final float kb = this.getKnockBack(itemstack, entityliving, attacker);
            PhysHelper.knockBack(entityliving, attacker, kb);
            if (this.meleeSpecs.attackDelay >= 3.0f) {
                entityliving.hurtResistantTime += (int)this.getAttackDelay(itemstack, entityliving, attacker);
            }
            else {
                final float f = (this.meleeSpecs.attackDelay < 1.0f) ? 1.2f : 2.0f;
                entityliving.hurtResistantTime -= (int)(f / this.getAttackDelay(itemstack, entityliving, attacker));
            }
        }
        itemstack.damageItem(this.meleeSpecs.dmgFromEntity, attacker);
        return true;
    }
    
    @Override
    public float getAttackDelay(final ItemStack itemstack, final EntityLivingBase entityliving, final EntityLivingBase attacker) {
        return this.meleeSpecs.attackDelay;
    }
    
    @Override
    public float getKnockBack(final ItemStack itemstack, final EntityLivingBase entityliving, final EntityLivingBase attacker) {
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
            multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(this.weapon.getUUIDDamage(), "Weapon attack damage modifier", dmg, 0));
            multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(this.weapon.getUUIDSpeed(), "Weapon attack speed modifier", -this.meleeSpecs.attackDelay, 0));
        }
        if (this.meleeSpecs.getKnockBack(this.weaponMaterial) != 0.4f) {
            multimap.put(WeaponModAttributes.WEAPON_KNOCKBACK.getName(), new AttributeModifier(this.weapon.getUUID(), "Weapon knockback modifier", this.meleeSpecs.getKnockBack(this.weaponMaterial) - 0.4f, 0));
        }
        if (this instanceof IExtendedReachItem) {
            try {
                multimap.put(WeaponModAttributes.WEAPON_REACH.getName(), new AttributeModifier(this.weapon.getUUID(), "Weapon reach modifier", ((IExtendedReachItem)this).getExtendedReach(null, null, null) - 3.0f, 0));
            }
            catch (final NullPointerException ex) {}
        }
    }
    
    @Override
    public boolean onLeftClickEntity(final ItemStack itemstack, final EntityPlayer player, final Entity entity) {
        if (entity instanceof EntityLivingBase) {
            PhysHelper.prepareKnockbackOnEntity(player, (EntityLivingBase)entity);
        }
        return false;
    }
    
    @Override
    public EnumAction getItemUseAction(final ItemStack itemstack) {
        return EnumAction.NONE;
    }
    
    @Override
    public int getMaxItemUseDuration(final ItemStack itemstack) {
        return 0;
    }
    
    @Override
    public ActionResult<ItemStack> onItemRightClick(final World world, final EntityPlayer entityplayer, final EnumHand hand) {
        final ItemStack itemstack = entityplayer.getHeldItem(hand);
        return (ActionResult<ItemStack>)new ActionResult(EnumActionResult.PASS, itemstack);
    }
    
    @Override
    public void onUsingTick(final ItemStack itemstack, final EntityLivingBase entityliving, final int count) {
    }
    
    @Override
    public void onPlayerStoppedUsing(final ItemStack itemstack, final World world, final EntityLivingBase entityliving, final int i) {
    }
    
    @Override
    public void onUpdate(final ItemStack itemstack, final World world, final Entity entity, final int i, final boolean flag) {
    }
    
    public enum MeleeSpecs
    {
        SPEAR(0, 1.0f, 2.0f, 1.0f, 1.0f, 0.2f, 1, 2, 1, 2.7f), 
        HALBERD(0, 1.0f, 3.0f, 1.0f, 1.5f, 0.6f, 1, 2, 1, 3.2f), 
        BATTLEAXE(0, 1.0f, 2.0f, 1.0f, 1.5f, 0.5f, 1, 2, 1, 3.0f), 
        WARHAMMER(0, 1.0f, 3.0f, 1.0f, 1.0f, 0.7f, 1, 2, 1, 3.0f), 
        KNIFE(0, 0.5f, 2.0f, 1.0f, 1.5f, 0.2f, 1, 2, 1, 2.0f), 
        KATANA(0, 1.0f, 0.0f, 1.0f, 1.0f, 0.0f, 1, 2, 1, 0.2f), 
        FIREROD(1, 0.0f, 0.0f, 0.0f, 1.0f, 0.4f, 2, 0, 1, 0.0f), 
        BOOMERANG(0, 0.5f, 1.0f, 1.0f, 1.0f, 0.4f, 1, 1, 1, 2.0f), 
        NONE(0, 1.0f, 1.0f, 0.0f, 1.0f, 0.4f, 0, 0, 1, 0.0f);
        
        public final int durabilityBase;
        public final float durabilityMult;
        public final float damageBase;
        public final float damageMult;
        public final float blockDamage;
        public final float knockback;
        public final float attackDelay;
        public final int dmgFromEntity;
        public final int dmgFromBlock;
        public final int stackSize;
        
        private MeleeSpecs(final int durbase, final float durmult, final float dmgbase, final float dmgmult, final float blockdmg, final float knockback, final int dmgfromentity, final int dmgfromblock, final int stacksize, final float attackdelay) {
            this.durabilityBase = durbase;
            this.durabilityMult = durmult;
            this.damageBase = dmgbase;
            this.damageMult = dmgmult;
            this.blockDamage = blockdmg;
            this.knockback = knockback;
            this.dmgFromEntity = dmgfromentity;
            this.dmgFromBlock = dmgfromblock;
            this.stackSize = stacksize;
            this.attackDelay = attackdelay;
        }
        
        public float getKnockBack(final Item.ToolMaterial material) {
            return (material == Item.ToolMaterial.GOLD) ? (this.knockback * 1.5f) : this.knockback;
        }
    }
}
