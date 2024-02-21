package ckathode.weaponmod.item;

import net.minecraft.block.state.*;
import net.minecraft.world.*;
import net.minecraft.util.math.*;
import com.google.common.collect.*;
import net.minecraft.entity.ai.attributes.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;
import ckathode.weaponmod.entity.projectile.*;
import net.minecraft.init.*;
import net.minecraft.enchantment.*;
import net.minecraft.item.*;
import ckathode.weaponmod.*;
import net.minecraft.util.*;

public abstract class RangedComponent extends AbstractWeaponComponent
{
    protected static final int MAX_DELAY = 72000;
    public final RangedSpecs rangedSpecs;
    
    public static boolean isReloaded(final ItemStack itemstack) {
        return ReloadHelper.getReloadState(itemstack) >= ReloadHelper.STATE_RELOADED;
    }
    
    public static boolean isReadyToFire(final ItemStack itemstack) {
        return ReloadHelper.getReloadState(itemstack) == ReloadHelper.STATE_READY;
    }
    
    public static void setReloadState(final ItemStack itemstack, final int state) {
        ReloadHelper.setReloadState(itemstack, state);
    }
    
    public RangedComponent(final RangedSpecs rangedspecs) {
        this.rangedSpecs = rangedspecs;
    }
    
    @Override
    protected void onSetItem() {
    }
    
    @Override
    public void setThisItemProperties() {
        this.item.setMaxDamage(this.rangedSpecs.durability);
        this.item.setMaxStackSize(this.rangedSpecs.stackSize);
    }
    
    @Override
    public float getEntityDamageMaterialPart() {
        return 0.0f;
    }
    
    @Override
    public float getEntityDamage() {
        return 0.0f;
    }
    
    @Override
    public float getBlockDamage(final ItemStack itemstack, final IBlockState block) {
        return 0.0f;
    }
    
    @Override
    public boolean canHarvestBlock(final IBlockState block) {
        return false;
    }
    
    @Override
    public boolean onBlockDestroyed(final ItemStack itemstack, final World world, final IBlockState block, final BlockPos pos, final EntityLivingBase entityliving) {
        return false;
    }
    
    @Override
    public boolean hitEntity(final ItemStack itemstack, final EntityLivingBase entityliving, final EntityLivingBase attacker) {
        return false;
    }
    
    @Override
    public float getAttackDelay(final ItemStack itemstack, final EntityLivingBase entityliving, final EntityLivingBase attacker) {
        return 0.0f;
    }
    
    @Override
    public float getKnockBack(final ItemStack itemstack, final EntityLivingBase entityliving, final EntityLivingBase attacker) {
        return 0.0f;
    }
    
    @Override
    public int getItemEnchantability() {
        return 1;
    }
    
    @Override
    public void addItemAttributeModifiers(final Multimap<String, AttributeModifier> multimap) {
        multimap.put(WeaponModAttributes.RELOAD_TIME.getName(), new AttributeModifier(this.weapon.getUUID(), "Weapon reloadtime modifier", this.rangedSpecs.getReloadTime(), 0));
    }
    
    @Override
    public boolean onLeftClickEntity(final ItemStack itemstack, final EntityPlayer player, final Entity entity) {
        return false;
    }
    
    @Override
    public EnumAction getItemUseAction(final ItemStack itemstack) {
        final int state = ReloadHelper.getReloadState(itemstack);
        if (state == ReloadHelper.STATE_READY) {
            return EnumAction.BOW;
        }
        return EnumAction.NONE;
    }
    
    @Override
    public int getMaxItemUseDuration(final ItemStack itemstack) {
        return 72000;
    }
    
    @Override
    public ActionResult<ItemStack> onItemRightClick(final World world, final EntityPlayer entityplayer, final EnumHand hand) {
        final ItemStack itemstack = entityplayer.getHeldItem(hand);
        if (itemstack.isEmpty() || entityplayer.isHandActive()) {
            return (ActionResult<ItemStack>)new ActionResult(EnumActionResult.FAIL, itemstack);
        }
        if (!this.hasAmmo(itemstack, world, entityplayer)) {
            this.soundEmpty(itemstack, world, entityplayer);
            setReloadState(itemstack, ReloadHelper.STATE_NONE);
            return (ActionResult<ItemStack>)new ActionResult(EnumActionResult.FAIL, itemstack);
        }
        if (isReadyToFire(itemstack)) {
            this.soundCharge(itemstack, world, entityplayer);
            entityplayer.setActiveHand(hand);
            return (ActionResult<ItemStack>)new ActionResult(EnumActionResult.SUCCESS, itemstack);
        }
        entityplayer.setActiveHand(hand);
        return (ActionResult<ItemStack>)new ActionResult(EnumActionResult.SUCCESS, itemstack);
    }
    
    @Override
    public void onUsingTick(final ItemStack itemstack, final EntityLivingBase entityliving, final int count) {
        final EntityPlayer entityplayer = (EntityPlayer)entityliving;
        if (ReloadHelper.getReloadState(itemstack) == ReloadHelper.STATE_NONE && this.getMaxItemUseDuration(itemstack) - count >= this.getReloadDuration(itemstack)) {
            this.effectReloadDone(itemstack, entityplayer.world, entityplayer);
            setReloadState(itemstack, ReloadHelper.STATE_RELOADED);
        }
    }
    
    @Override
    public void onPlayerStoppedUsing(final ItemStack itemstack, final World world, final EntityLivingBase entityliving, final int i) {
        final EntityPlayer entityplayer = (EntityPlayer)entityliving;
        if (!isReloaded(itemstack)) {
            return;
        }
        if (isReadyToFire(itemstack)) {
            if (this.hasAmmoAndConsume(itemstack, world, entityplayer)) {
                this.fire(itemstack, world, entityplayer, i);
            }
            setReloadState(itemstack, ReloadHelper.STATE_NONE);
        }
        else {
            setReloadState(itemstack, ReloadHelper.STATE_READY);
        }
    }
    
    @Override
    public void onUpdate(final ItemStack itemstack, final World world, final Entity entity, final int i, final boolean flag) {
    }
    
    public void soundEmpty(final ItemStack itemstack, final World world, final EntityPlayer entityplayer) {
        world.playSound(null, entityplayer.posX, entityplayer.posY, entityplayer.posZ, SoundEvents.BLOCK_COMPARATOR_CLICK, SoundCategory.PLAYERS, 1.0f, 1.25f);
    }
    
    public void soundCharge(final ItemStack itemstack, final World world, final EntityPlayer entityplayer) {
    }
    
    public final void postShootingEffects(final ItemStack itemstack, final EntityPlayer entityplayer, final World world) {
        this.effectPlayer(itemstack, entityplayer, world);
        this.effectShoot(world, entityplayer.posX, entityplayer.posY, entityplayer.posZ, entityplayer.rotationYaw, entityplayer.rotationPitch);
    }
    
    public abstract void effectReloadDone(final ItemStack p0, final World p1, final EntityPlayer p2);
    
    public abstract void fire(final ItemStack p0, final World p1, final EntityLivingBase p2, final int p3);
    
    public abstract void effectPlayer(final ItemStack p0, final EntityPlayer p1, final World p2);
    
    public abstract void effectShoot(final World p0, final double p1, final double p2, final double p3, final float p4, final float p5);
    
    public void applyProjectileEnchantments(final EntityProjectile entity, final ItemStack itemstack) {
        if (EnchantmentHelper.getEnchantmentLevel(Enchantments.INFINITY, itemstack) > 0) {
            entity.setPickupMode(0);
        }
        final int damage = EnchantmentHelper.getEnchantmentLevel(Enchantments.POWER, itemstack);
        if (damage > 0) {
            entity.setExtraDamage((float)damage);
        }
        final int knockback = EnchantmentHelper.getEnchantmentLevel(Enchantments.PUNCH, itemstack);
        if (knockback > 0) {
            entity.setKnockbackStrength(knockback);
        }
        if (EnchantmentHelper.getEnchantmentLevel(Enchantments.FLAME, itemstack) > 0) {
            entity.setFire(100);
        }
    }
    
    public int getReloadDuration(final ItemStack itemstack) {
        return this.rangedSpecs.getReloadTime();
    }
    
    public Item getAmmoItem() {
        return this.rangedSpecs.getAmmoItem();
    }
    
    protected ItemStack findAmmo(final EntityPlayer entityplayer) {
        if (this.isAmmo(entityplayer.getHeldItem(EnumHand.OFF_HAND))) {
            return entityplayer.getHeldItem(EnumHand.OFF_HAND);
        }
        if (this.isAmmo(entityplayer.getHeldItem(EnumHand.MAIN_HAND))) {
            return entityplayer.getHeldItem(EnumHand.MAIN_HAND);
        }
        for (int i = 0; i < entityplayer.inventory.getSizeInventory(); ++i) {
            final ItemStack itemstack = entityplayer.inventory.getStackInSlot(i);
            if (this.isAmmo(itemstack)) {
                return itemstack;
            }
        }
        return ItemStack.EMPTY;
    }
    
    protected boolean isAmmo(final ItemStack stack) {
        return stack.getItem() == this.getAmmoItem();
    }
    
    protected boolean consumeAmmo(final EntityPlayer entityplayer) {
        final ItemStack itemAmmo = this.findAmmo(entityplayer);
        if (itemAmmo.isEmpty()) {
            return false;
        }
        itemAmmo.shrink(1);
        if (itemAmmo.isEmpty()) {
            entityplayer.inventory.deleteStack(itemAmmo);
        }
        return true;
    }
    
    public boolean hasAmmoAndConsume(final ItemStack itemstack, final World world, final EntityPlayer entityplayer) {
        return entityplayer.capabilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantments.INFINITY, itemstack) > 0 || this.consumeAmmo(entityplayer);
    }
    
    public boolean hasAmmo(final ItemStack itemstack, final World world, final EntityPlayer entityplayer) {
        final boolean flag = !this.findAmmo(entityplayer).isEmpty();
        return entityplayer.capabilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantments.INFINITY, itemstack) > 0 || flag;
    }
    
    public float getFOVMultiplier(final int ticksinuse) {
        float f1 = ticksinuse / this.getMaxAimTimeTicks();
        if (f1 > 1.0f) {
            f1 = 1.0f;
        }
        else {
            f1 *= f1;
        }
        return 1.0f - f1 * this.getMaxZoom();
    }
    
    protected float getMaxAimTimeTicks() {
        return 20.0f;
    }
    
    protected float getMaxZoom() {
        return 0.15f;
    }
    
    public enum RangedSpecs
    {
        BLOWGUN("weaponmod:dart", "blowgun", 250, 1), 
        CROSSBOW("weaponmod:bolt", "crossbow", 250, 1), 
        MUSKET("weaponmod:bullet", "musket", 80, 1), 
        BLUNDERBUSS("weaponmod:shot", "blunderbuss", 80, 1), 
        FLINTLOCK("weaponmod:bullet", "flintlock", 80, 1), 
        MORTAR("weaponmod:shell", "mortar", 40, 1);
        
        private int reloadTime;
        private Item ammoItem;
        private String ammoItemTag;
        public final String reloadTimeTag;
        public final int durability;
        public final int stackSize;
        
        private RangedSpecs(final String ammoitemtag, final String reloadtimetag, final int durability, final int stacksize) {
            this.ammoItemTag = ammoitemtag;
            this.reloadTimeTag = reloadtimetag;
            this.durability = durability;
            this.stackSize = stacksize;
            this.ammoItem = null;
            this.reloadTime = -1;
        }
        
        public int getReloadTime() {
            if (this.reloadTime < 0 && BalkonsWeaponMod.instance != null) {
                this.reloadTime = BalkonsWeaponMod.instance.modConfig.getReloadTime(this.reloadTimeTag);
                BalkonsWeaponMod.modLog.debug("Found reaload time " + this.reloadTime + " for " + this.reloadTimeTag + " @" + this);
            }
            return this.reloadTime;
        }
        
        public Item getAmmoItem() {
            if (this.ammoItem == null && !this.ammoItemTag.isEmpty()) {
                this.ammoItem = Item.REGISTRY.getObject(new ResourceLocation(this.ammoItemTag));
                BalkonsWeaponMod.modLog.debug("Found item " + this.ammoItem + " for " + this.ammoItemTag + " @" + this);
                this.ammoItemTag.isEmpty();
            }
            return this.ammoItem;
        }
    }
}
