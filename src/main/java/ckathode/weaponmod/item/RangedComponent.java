package ckathode.weaponmod.item;

import ckathode.weaponmod.BalkonsWeaponMod;
import ckathode.weaponmod.ReloadHelper;
import ckathode.weaponmod.ReloadHelper.ReloadState;
import ckathode.weaponmod.WeaponModAttributes;
import ckathode.weaponmod.entity.projectile.EntityProjectile;
import com.google.common.collect.Multimap;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public abstract class RangedComponent extends AbstractWeaponComponent {
    protected static final int MAX_DELAY = 72000;
    public final RangedSpecs rangedSpecs;

    public static boolean isReloaded(ItemStack itemstack) {
        return ReloadHelper.getReloadState(itemstack).isReloaded();
    }

    public static boolean isReadyToFire(ItemStack itemstack) {
        return ReloadHelper.getReloadState(itemstack) == ReloadState.STATE_READY;
    }

    public static void setReloadState(ItemStack itemstack, ReloadState state) {
        ReloadHelper.setReloadState(itemstack, state);
    }

    public RangedComponent(RangedSpecs rangedspecs) {
        rangedSpecs = rangedspecs;
    }

    @Override
    protected void onSetItem() {
    }

    @Override
    public void setThisItemProperties() {
        item.setMaxDamage(rangedSpecs.durability);
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
    public float getBlockDamage(ItemStack itemstack, Block block) {
        return 0.0f;
    }

    @Override
    public boolean canHarvestBlock(Block block) {
        return false;
    }

    @Override
    public boolean onBlockDestroyed(ItemStack itemstack, World world, Block block,
                                    int x, int y, int z, EntityLivingBase entityliving) {
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
        multimap.put(WeaponModAttributes.RELOAD_TIME.getAttributeUnlocalizedName(),
                new AttributeModifier(IItemWeapon.RELOAD_TIME_MODIFIER, "Weapon reloadtime modifier",
                        rangedSpecs.getReloadTime(), 0));
    }

    @Override
    public boolean onLeftClickEntity(ItemStack itemstack, EntityPlayer player, Entity entity) {
        return false;
    }

    @Override
    public EnumAction getItemUseAction(ItemStack itemstack) {
        ReloadState state = ReloadHelper.getReloadState(itemstack);
        if (state == ReloadState.STATE_NONE) {
            return EnumAction.block;
        }
        if (state == ReloadState.STATE_READY) {
            return EnumAction.bow;
        }
        return EnumAction.none;
    }

    @Override
    public int getMaxItemUseDuration(ItemStack itemstack) {
        return MAX_DELAY;
    }

    @Override
    public ItemStack onItemRightClick(World world, EntityPlayer entityplayer, ItemStack itemstack) {
        if (itemstack == null || itemstack.stackSize <= 0 || entityplayer.isUsingItem()) {
            return itemstack;
        }
        if (!hasAmmo(itemstack, world, entityplayer)) {
            soundEmpty(itemstack, world, entityplayer);
            setReloadState(itemstack, ReloadState.STATE_NONE);
            return itemstack;
        }
        if (isReadyToFire(itemstack)) {
            soundCharge(itemstack, world, entityplayer);
            entityplayer.setItemInUse(itemstack, getMaxItemUseDuration(itemstack));
            return itemstack;
        }
        entityplayer.setItemInUse(itemstack, getMaxItemUseDuration(itemstack));
        return itemstack;
    }

    @Override
    public void onUsingTick(ItemStack itemstack, EntityLivingBase entityliving, int count) {
        if (ReloadHelper.getReloadState(itemstack) == ReloadState.STATE_NONE && getMaxItemUseDuration(itemstack) - count >= getReloadDuration(itemstack)) {
            effectReloadDone(itemstack, entityliving.worldObj, entityliving);
            setReloadState(itemstack, ReloadState.STATE_RELOADED);
        }
    }

    @Override
    public void onPlayerStoppedUsing(ItemStack itemstack, World world,
                                     EntityLivingBase entityliving, int i) {
        if (!isReloaded(itemstack)) {
            return;
        }
        if (isReadyToFire(itemstack)) {
            if (hasAmmoAndConsume(itemstack, world, entityliving)) {
                fire(itemstack, world, entityliving, i);
            }
            setReloadState(itemstack, ReloadState.STATE_NONE);
        } else {
            setReloadState(itemstack, ReloadState.STATE_READY);
        }
    }

    @Override
    public void onUpdate(ItemStack itemstack, World world, Entity entity, int i,
                         boolean flag) {
    }

    public void soundEmpty(ItemStack itemstack, World world, EntityPlayer entityplayer) {
        world.playSoundAtEntity(entityplayer, "random.click", 1.0f, 1.25f);
    }

    public void soundCharge(ItemStack itemstack, World world, EntityPlayer entityplayer) {
    }

    public void postShootingEffects(ItemStack itemstack, EntityLivingBase entityLiving,
                                    World world) {
        if (entityLiving instanceof EntityPlayer)
            effectPlayer(itemstack, (EntityPlayer) entityLiving, world);
        effectShoot(world, entityLiving.posX, entityLiving.posY, entityLiving.posZ, entityLiving.rotationYaw,
                entityLiving.rotationPitch);
    }

    public abstract void effectReloadDone(ItemStack stack, World world, EntityLivingBase player);

    public abstract void fire(ItemStack stack, World world, EntityLivingBase entity, int i);

    public abstract void effectPlayer(ItemStack stack, EntityPlayer player, World world);

    public abstract void effectShoot(World world, double x, double y, double z, float yaw, float pitch);

    public static void applyProjectileEnchantments(EntityProjectile entity, ItemStack itemstack) {
        if (EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, itemstack) > 0) {
            entity.setPickupStatus(EntityProjectile.PickupStatus.DISALLOWED);
        }
        int damage = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, itemstack);
        if (damage > 0) {
            entity.setExtraDamage(damage);
        }
        int knockback = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, itemstack);
        if (knockback > 0) {
            entity.setKnockbackStrength(knockback);
        }
        if (EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, itemstack) > 0) {
            entity.setFire(100);
        }
    }

    public int getReloadDuration(ItemStack itemstack) {
        return rangedSpecs.getReloadTime();
    }

    public List<Item> getAmmoItems() {
        return rangedSpecs.getAmmoItems();
    }

    protected ItemStack findAmmo(EntityPlayer entityplayer) {
        int slot = WMItem.findAnyItemSlot(entityplayer, getAmmoItems());
        if (slot < 0) return null;
        return entityplayer.inventory.mainInventory[slot];
    }

    protected boolean consumeAmmo(EntityPlayer entityplayer) {
        return WMItem.consumeAnyInventoryItem(entityplayer, getAmmoItems());
    }

    public boolean hasAmmoAndConsume(ItemStack itemstack, World world, EntityLivingBase entityliving) {
        if (!(entityliving instanceof EntityPlayer)) return true;
        EntityPlayer entityplayer = (EntityPlayer) entityliving;
        return entityplayer.capabilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId,
                itemstack) > 0 || consumeAmmo(entityplayer);
    }

    public boolean hasAmmo(ItemStack itemstack, World world, EntityPlayer entityplayer) {
        boolean flag = findAmmo(entityplayer) != null;
        return entityplayer.capabilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId,
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

    @Override
    public boolean shouldRotateAroundWhenRendering() {
        return false;
    }

    public enum RangedSpecs {
        BLOWGUN("blowgun", 250, BalkonsWeaponMod.id("dart").toString()),
        CROSSBOW("crossbow", 250, BalkonsWeaponMod.id("bolt").toString()),
        MUSKET("musket", 80, BalkonsWeaponMod.id("bullet").toString()),
        BLUNDERBUSS("blunderbuss", 80, BalkonsWeaponMod.id("shot").toString()),
        FLINTLOCK("flintlock", 8, BalkonsWeaponMod.id("bullet").toString()),
        MORTAR("mortar", 40, BalkonsWeaponMod.id("shell").toString());

        private int reloadTime;
        private List<Item> ammoItems;
        private final String[] ammoItemTags;
        public final String reloadTimeTag;
        public final int durability;

        RangedSpecs(String reloadtimetag, int durability, String... ammoitemtags) {
            ammoItemTags = ammoitemtags;
            reloadTimeTag = reloadtimetag;
            this.durability = durability;
            ammoItems = null;
            reloadTime = -1;
        }

        public int getReloadTime() {
            if (reloadTime < 0 && BalkonsWeaponMod.instance != null) {
                reloadTime = BalkonsWeaponMod.instance.modConfig.getReloadTime(reloadTimeTag);
                BalkonsWeaponMod.modLog.debug("Found reload time {} for {} @{}",
                        reloadTime, reloadTimeTag, this);
            }
            return reloadTime;
        }

        public List<Item> getAmmoItems() {
            if (ammoItems == null) {
                ammoItems = Arrays.stream(ammoItemTags)
                        .map(Item.itemRegistry::getObject)
                        .filter(o -> o instanceof Item)
                        .map(item -> (Item) item)
                        .collect(Collectors.toList());
                BalkonsWeaponMod.modLog.debug("Found items {} for {} @{}",
                        ammoItems, Arrays.toString(ammoItemTags), this);
            }
            return ammoItems;
        }
    }
}
