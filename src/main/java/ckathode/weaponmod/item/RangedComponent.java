package ckathode.weaponmod.item;

import ckathode.weaponmod.BalkonsWeaponMod;
import ckathode.weaponmod.ReloadHelper;
import ckathode.weaponmod.WeaponModAttributes;
import ckathode.weaponmod.entity.projectile.EntityProjectile;
import com.google.common.collect.Multimap;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.Item.Properties;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistries;

public abstract class RangedComponent extends AbstractWeaponComponent {
    protected static final int MAX_DELAY = 72000;
    public final RangedSpecs rangedSpecs;

    public static boolean isReloaded(ItemStack itemstack) {
        return ReloadHelper.getReloadState(itemstack) >= ReloadHelper.STATE_RELOADED;
    }

    public static boolean isReadyToFire(ItemStack itemstack) {
        return ReloadHelper.getReloadState(itemstack) == ReloadHelper.STATE_READY;
    }

    public static void setReloadState(ItemStack itemstack, int state) {
        ReloadHelper.setReloadState(itemstack, state);
    }

    public RangedComponent(RangedSpecs rangedspecs) {
        rangedSpecs = rangedspecs;
    }

    @Override
    protected void onSetItem() {
    }

    @Override
    public Properties setProperties(Properties properties) {
        return properties.defaultMaxDamage(rangedSpecs.durability);
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
    public float getBlockDamage(ItemStack itemstack, IBlockState block) {
        return 0.0f;
    }

    @Override
    public boolean canHarvestBlock(IBlockState block) {
        return false;
    }

    @Override
    public boolean onBlockDestroyed(ItemStack itemstack, World world, IBlockState block,
                                    BlockPos pos, EntityLivingBase entityliving) {
        return false;
    }

    @Override
    public boolean hitEntity(ItemStack itemstack, EntityLivingBase entityliving,
                             EntityLivingBase attacker) {
        return false;
    }

    @Override
    public float getAttackDelay(ItemStack itemstack, EntityLivingBase entityliving,
                                EntityLivingBase attacker) {
        return 0.0f;
    }

    @Override
    public float getKnockBack(ItemStack itemstack, EntityLivingBase entityliving,
                              EntityLivingBase attacker) {
        return 0.0f;
    }

    @Override
    public int getItemEnchantability() {
        return 1;
    }

    @Override
    public void addItemAttributeModifiers(Multimap<String, AttributeModifier> multimap) {
        multimap.put(WeaponModAttributes.RELOAD_TIME.getName(), new AttributeModifier(weapon.getUUID(), "Weapon "
                                                                                                        +
                                                                                                        "reloadtime modifier", rangedSpecs.getReloadTime(), 0));
    }

    @Override
    public boolean onLeftClickEntity(ItemStack itemstack, EntityPlayer player, Entity entity) {
        return false;
    }

    @Override
    public EnumAction getUseAction(ItemStack itemstack) {
        int state = ReloadHelper.getReloadState(itemstack);
        if (state == ReloadHelper.STATE_READY) {
            return EnumAction.BOW;
        }
        return EnumAction.NONE;
    }

    @Override
    public int getUseDuration(ItemStack itemstack) {
        return MAX_DELAY;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer entityplayer,
                                                    EnumHand hand) {
        ItemStack itemstack = entityplayer.getHeldItem(hand);
        if (itemstack.isEmpty() || entityplayer.isHandActive()) {
            return new ActionResult<>(EnumActionResult.FAIL, itemstack);
        }
        if (!hasAmmo(itemstack, world, entityplayer)) {
            soundEmpty(itemstack, world, entityplayer);
            setReloadState(itemstack, ReloadHelper.STATE_NONE);
            return new ActionResult<>(EnumActionResult.FAIL, itemstack);
        }
        if (isReadyToFire(itemstack)) {
            soundCharge(itemstack, world, entityplayer);
            entityplayer.setActiveHand(hand);
            return new ActionResult<>(EnumActionResult.SUCCESS, itemstack);
        }
        entityplayer.setActiveHand(hand);
        return new ActionResult<>(EnumActionResult.SUCCESS, itemstack);
    }

    @Override
    public void onUsingTick(ItemStack itemstack, EntityLivingBase entityliving, int count) {
        EntityPlayer entityplayer = (EntityPlayer) entityliving;
        if (ReloadHelper.getReloadState(itemstack) == ReloadHelper.STATE_NONE && getUseDuration(itemstack) - count >= getReloadDuration(itemstack)) {
            effectReloadDone(itemstack, entityplayer.world, entityplayer);
            setReloadState(itemstack, ReloadHelper.STATE_RELOADED);
        }
    }

    @Override
    public void onPlayerStoppedUsing(ItemStack itemstack, World world,
                                     EntityLivingBase entityliving, int i) {
        EntityPlayer entityplayer = (EntityPlayer) entityliving;
        if (!isReloaded(itemstack)) {
            return;
        }
        if (isReadyToFire(itemstack)) {
            if (hasAmmoAndConsume(itemstack, world, entityplayer)) {
                fire(itemstack, world, entityplayer, i);
            }
            setReloadState(itemstack, ReloadHelper.STATE_NONE);
        } else {
            setReloadState(itemstack, ReloadHelper.STATE_READY);
        }
    }

    @Override
    public void inventoryTick(ItemStack itemstack, World world, Entity entity, int i,
                              boolean flag) {
    }

    public void soundEmpty(ItemStack itemstack, World world, EntityPlayer entityplayer) {
        world.playSound(null, entityplayer.posX, entityplayer.posY, entityplayer.posZ,
                SoundEvents.BLOCK_COMPARATOR_CLICK, SoundCategory.PLAYERS, 1.0f, 1.25f);
    }

    public void soundCharge(ItemStack itemstack, World world, EntityPlayer entityplayer) {
    }

    public void postShootingEffects(ItemStack itemstack, EntityPlayer entityplayer,
                                    World world) {
        effectPlayer(itemstack, entityplayer, world);
        effectShoot(world, entityplayer.posX, entityplayer.posY, entityplayer.posZ, entityplayer.rotationYaw,
                entityplayer.rotationPitch);
    }

    public abstract void effectReloadDone(ItemStack p0, World p1, EntityPlayer p2);

    public abstract void fire(ItemStack p0, World p1, EntityLivingBase p2, int p3);

    public abstract void effectPlayer(ItemStack p0, EntityPlayer p1, World p2);

    public abstract void effectShoot(World p0, double p1, double p2, double p3,
                                     float p4, float p5);

    public void applyProjectileEnchantments(EntityProjectile<?> entity, ItemStack itemstack) {
        if (EnchantmentHelper.getEnchantmentLevel(Enchantments.INFINITY, itemstack) > 0) {
            entity.setPickupStatus(EntityProjectile.PickupStatus.DISALLOWED);
        }
        int damage = EnchantmentHelper.getEnchantmentLevel(Enchantments.POWER, itemstack);
        if (damage > 0) {
            entity.setExtraDamage((float) damage);
        }
        int knockback = EnchantmentHelper.getEnchantmentLevel(Enchantments.PUNCH, itemstack);
        if (knockback > 0) {
            entity.setKnockbackStrength(knockback);
        }
        if (EnchantmentHelper.getEnchantmentLevel(Enchantments.FLAME, itemstack) > 0) {
            entity.setFire(100);
        }
    }

    public int getReloadDuration(ItemStack itemstack) {
        return rangedSpecs.getReloadTime();
    }

    public Item getAmmoItem() {
        return rangedSpecs.getAmmoItem();
    }

    protected ItemStack findAmmo(EntityPlayer entityplayer) {
        if (isAmmo(entityplayer.getHeldItem(EnumHand.OFF_HAND))) {
            return entityplayer.getHeldItem(EnumHand.OFF_HAND);
        }
        if (isAmmo(entityplayer.getHeldItem(EnumHand.MAIN_HAND))) {
            return entityplayer.getHeldItem(EnumHand.MAIN_HAND);
        }
        for (int i = 0; i < entityplayer.inventory.getSizeInventory(); ++i) {
            ItemStack itemstack = entityplayer.inventory.getStackInSlot(i);
            if (isAmmo(itemstack)) {
                return itemstack;
            }
        }
        return ItemStack.EMPTY;
    }

    protected boolean isAmmo(ItemStack stack) {
        return stack.getItem() == getAmmoItem();
    }

    protected boolean consumeAmmo(EntityPlayer entityplayer) {
        ItemStack itemAmmo = findAmmo(entityplayer);
        if (itemAmmo.isEmpty()) {
            return false;
        }
        itemAmmo.shrink(1);
        if (itemAmmo.isEmpty()) {
            entityplayer.inventory.deleteStack(itemAmmo);
        }
        return true;
    }

    public boolean hasAmmoAndConsume(ItemStack itemstack, World world, EntityPlayer entityplayer) {
        return entityplayer.abilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantments.INFINITY,
                itemstack) > 0 || consumeAmmo(entityplayer);
    }

    public boolean hasAmmo(ItemStack itemstack, World world, EntityPlayer entityplayer) {
        boolean flag = !findAmmo(entityplayer).isEmpty();
        return entityplayer.abilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantments.INFINITY,
                itemstack) > 0 || flag;
    }

    public float getFOVMultiplier(int ticksinuse) {
        float f1 = ticksinuse / getMaxAimTimeTicks();
        if (f1 > 1.0f) {
            f1 = 1.0f;
        } else {
            f1 *= f1;
        }
        return 1.0f - f1 * getMaxZoom();
    }

    protected float getMaxAimTimeTicks() {
        return 20.0f;
    }

    protected float getMaxZoom() {
        return 0.15f;
    }

    public enum RangedSpecs {
        BLOWGUN("dart", "blowgun", 250),
        CROSSBOW("bolt", "crossbow", 250),
        MUSKET("bullet", "musket", 80),
        BLUNDERBUSS("shot", "blunderbuss", 80),
        FLINTLOCK("bullet", "flintlock", 8),
        MORTAR("shell", "mortar", 40);

        private int reloadTime;
        private Item ammoItem;
        private String ammoItemTag;
        public final String reloadTimeTag;
        public final int durability;

        RangedSpecs(String ammoitemtag, String reloadtimetag, int durability) {
            ammoItemTag = ammoitemtag;
            reloadTimeTag = reloadtimetag;
            this.durability = durability;
            ammoItem = null;
            reloadTime = -1;
        }

        public int getReloadTime() {
            if (reloadTime < 0 && BalkonsWeaponMod.instance != null) {
                reloadTime = BalkonsWeaponMod.instance.modConfig.getReloadTime(reloadTimeTag);
                BalkonsWeaponMod.modLog.debug("Found reload time " + reloadTime + " for " + reloadTimeTag + " @" + this);
            }
            return reloadTime;
        }

        public Item getAmmoItem() {
            if (ammoItem == null && ammoItemTag != null) {
                ammoItem = ForgeRegistries.ITEMS.getValue(new ResourceLocation(BalkonsWeaponMod.MOD_ID,
                        ammoItemTag));
                BalkonsWeaponMod.modLog.debug("Found item " + ammoItem + " for " + ammoItemTag + " @" + this);
                ammoItemTag = null;
            }
            return ammoItem;
        }
    }
}
