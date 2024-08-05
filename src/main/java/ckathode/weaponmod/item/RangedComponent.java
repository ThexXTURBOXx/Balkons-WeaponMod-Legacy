package ckathode.weaponmod.item;

import ckathode.weaponmod.BalkonsWeaponMod;
import ckathode.weaponmod.ReloadHelper;
import ckathode.weaponmod.ReloadHelper.ReloadState;
import ckathode.weaponmod.WeaponModAttributes;
import ckathode.weaponmod.entity.projectile.EntityProjectile;
import com.google.common.collect.Multimap;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Item.Properties;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistries;

import static ckathode.weaponmod.BalkonsWeaponMod.MOD_ID;

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
    public float getBlockDamage(ItemStack itemstack, BlockState block) {
        return 0.0f;
    }

    @Override
    public boolean canHarvestBlock(BlockState block) {
        return false;
    }

    @Override
    public boolean onBlockDestroyed(ItemStack itemstack, World world, BlockState block,
                                    BlockPos pos, LivingEntity entityliving) {
        return false;
    }

    @Override
    public boolean hitEntity(ItemStack itemstack, LivingEntity entityliving,
                             LivingEntity attacker) {
        return false;
    }

    @Override
    public float getAttackDelay(ItemStack itemstack, LivingEntity entityliving,
                                LivingEntity attacker) {
        return 0.0f;
    }

    @Override
    public float getKnockBack(ItemStack itemstack, LivingEntity entityliving,
                              LivingEntity attacker) {
        return 0.0f;
    }

    @Override
    public int getItemEnchantability() {
        return 1;
    }

    @Override
    public void addItemAttributeModifiers(Multimap<String, AttributeModifier> multimap) {
        multimap.put(WeaponModAttributes.RELOAD_TIME.getName(), new AttributeModifier(IItemWeapon.RELOAD_TIME_MODIFIER,
                "Weapon reloadtime modifier", rangedSpecs.getReloadTime(), AttributeModifier.Operation.ADDITION));
    }

    @Override
    public boolean onLeftClickEntity(ItemStack itemstack, PlayerEntity player, Entity entity) {
        return false;
    }

    @Override
    public UseAction getUseAction(ItemStack itemstack) {
        ReloadState state = ReloadHelper.getReloadState(itemstack);
        if (state == ReloadState.STATE_NONE) {
            return UseAction.BLOCK;
        }
        if (state == ReloadState.STATE_READY) {
            return UseAction.BOW;
        }
        return UseAction.NONE;
    }

    @Override
    public int getUseDuration(ItemStack itemstack) {
        return MAX_DELAY;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity entityplayer,
                                                    Hand hand) {
        ItemStack itemstack = entityplayer.getHeldItem(hand);
        if (itemstack.isEmpty() || entityplayer.isHandActive()) {
            return new ActionResult<>(ActionResultType.FAIL, itemstack);
        }
        if (!hasAmmo(itemstack, world, entityplayer)) {
            soundEmpty(itemstack, world, entityplayer);
            setReloadState(itemstack, ReloadState.STATE_NONE);
            return new ActionResult<>(ActionResultType.FAIL, itemstack);
        }
        if (isReadyToFire(itemstack)) {
            soundCharge(itemstack, world, entityplayer);
            entityplayer.setActiveHand(hand);
            return new ActionResult<>(ActionResultType.SUCCESS, itemstack);
        }
        entityplayer.setActiveHand(hand);
        return new ActionResult<>(ActionResultType.SUCCESS, itemstack);
    }

    @Override
    public void onUsingTick(ItemStack itemstack, LivingEntity entityliving, int count) {
        PlayerEntity entityplayer = (PlayerEntity) entityliving;
        if (ReloadHelper.getReloadState(itemstack) == ReloadState.STATE_NONE && getUseDuration(itemstack) - count >= getReloadDuration(itemstack)) {
            effectReloadDone(itemstack, entityplayer.world, entityplayer);
            setReloadState(itemstack, ReloadState.STATE_RELOADED);
        }
    }

    @Override
    public void onPlayerStoppedUsing(ItemStack itemstack, World world,
                                     LivingEntity entityliving, int i) {
        PlayerEntity entityplayer = (PlayerEntity) entityliving;
        if (!isReloaded(itemstack)) {
            return;
        }
        if (isReadyToFire(itemstack)) {
            if (hasAmmoAndConsume(itemstack, world, entityplayer)) {
                fire(itemstack, world, entityplayer, i);
            }
            setReloadState(itemstack, ReloadState.STATE_NONE);
        } else {
            setReloadState(itemstack, ReloadState.STATE_READY);
        }
    }

    @Override
    public void inventoryTick(ItemStack itemstack, World world, Entity entity, int i,
                              boolean flag) {
    }

    public void soundEmpty(ItemStack itemstack, World world, PlayerEntity entityplayer) {
        world.playSound(null, entityplayer.posX, entityplayer.posY, entityplayer.posZ,
                SoundEvents.BLOCK_COMPARATOR_CLICK, SoundCategory.PLAYERS, 1.0f, 1.25f);
    }

    public void soundCharge(ItemStack itemstack, World world, PlayerEntity entityplayer) {
    }

    public void postShootingEffects(ItemStack itemstack, PlayerEntity entityplayer,
                                    World world) {
        effectPlayer(itemstack, entityplayer, world);
        effectShoot(world, entityplayer.posX, entityplayer.posY, entityplayer.posZ, entityplayer.rotationYaw,
                entityplayer.rotationPitch);
    }

    public abstract void effectReloadDone(ItemStack stack, World world, PlayerEntity player);

    public abstract void fire(ItemStack stack, World world, LivingEntity entity, int i);

    public abstract void effectPlayer(ItemStack stack, PlayerEntity player, World world);

    public abstract void effectShoot(World world, double x, double y, double z, float yaw, float pitch);

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

    public List<Item> getAmmoItems() {
        return rangedSpecs.getAmmoItems();
    }

    protected ItemStack findAmmo(PlayerEntity entityplayer) {
        if (isAmmo(entityplayer.getHeldItem(Hand.OFF_HAND))) {
            return entityplayer.getHeldItem(Hand.OFF_HAND);
        }
        if (isAmmo(entityplayer.getHeldItem(Hand.MAIN_HAND))) {
            return entityplayer.getHeldItem(Hand.MAIN_HAND);
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
        return getAmmoItems().contains(stack.getItem());
    }

    protected boolean consumeAmmo(PlayerEntity entityplayer) {
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

    public boolean hasAmmoAndConsume(ItemStack itemstack, World world, PlayerEntity entityplayer) {
        return entityplayer.isCreative() || EnchantmentHelper.getEnchantmentLevel(Enchantments.INFINITY,
                itemstack) > 0 || consumeAmmo(entityplayer);
    }

    public boolean hasAmmo(ItemStack itemstack, World world, PlayerEntity entityplayer) {
        boolean flag = !findAmmo(entityplayer).isEmpty();
        return entityplayer.isCreative() || EnchantmentHelper.getEnchantmentLevel(Enchantments.INFINITY,
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
        BLOWGUN("blowgun", 250, Arrays.stream(DartType.dartTypes)
                .filter(Objects::nonNull)
                .map(t -> new ResourceLocation(MOD_ID, t.typeName).toString())
                .toArray(String[]::new)),
        CROSSBOW("crossbow", 250, new ResourceLocation(MOD_ID, "bolt").toString()),
        MUSKET("musket", 80, new ResourceLocation(MOD_ID, "bullet").toString()),
        BLUNDERBUSS("blunderbuss", 80, new ResourceLocation(MOD_ID, "shot").toString()),
        FLINTLOCK("flintlock", 8, new ResourceLocation(MOD_ID, "bullet").toString()),
        MORTAR("mortar", 40, new ResourceLocation(MOD_ID, "shell").toString());

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
                        .map(t -> ForgeRegistries.ITEMS.getValue(new ResourceLocation(t)))
                        .collect(Collectors.toList());
                BalkonsWeaponMod.modLog.debug("Found items {} for {} @{}",
                        ammoItems, Arrays.toString(ammoItemTags), this);
            }
            return ammoItems;
        }
    }
}
