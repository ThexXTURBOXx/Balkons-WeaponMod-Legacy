package ckathode.weaponmod.item;

import ckathode.weaponmod.BalkonsWeaponMod;
import ckathode.weaponmod.PlayerWeaponData;
import ckathode.weaponmod.entity.projectile.EntityFlail;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ItemFlail extends ItemMelee {
    private final float flailDamage;

    public ItemFlail(final String id, final MeleeComponent meleecomponent) {
        super(id, meleecomponent);
        this.flailDamage = 4.0f + meleecomponent.weaponMaterial.getAttackDamage();
        this.addPropertyOverride(new ResourceLocation(BalkonsWeaponMod.MOD_ID, "thrown"), new IItemPropertyGetter() {
            @Override
            @OnlyIn(Dist.CLIENT)
            public float call(@Nonnull final ItemStack stack, @Nullable final World worldIn,
                              @Nullable final EntityLivingBase entityIn) {
                return (entityIn == null) ? 0.0f :
                        ((entityIn.getHeldItemMainhand() == stack && entityIn instanceof EntityPlayer && PlayerWeaponData.isFlailThrown((EntityPlayer) entityIn)) ? 1.0f : 0.0f);
            }
        });
    }

    @Override
    public int getItemEnchantability() {
        return 0;
    }

    @Override
    public void inventoryTick(@Nonnull final ItemStack itemstack, @Nonnull final World world,
                              @Nonnull final Entity entity, final int i, final boolean flag) {
        if (!(entity instanceof EntityPlayer)) {
            return;
        }
        final EntityPlayer player = (EntityPlayer) entity;
        if (!PlayerWeaponData.isFlailThrown(player)) {
            return;
        }
        final ItemStack itemstack2 = player.getHeldItemMainhand();
        if (itemstack2.isEmpty() || itemstack2.getItem() != this) {
            this.setThrown(player, false);
        } else {
            final int id = PlayerWeaponData.getFlailEntityId(player);
            if (id != 0) {
                final Entity entity2 = world.getEntityByID(id);
                if (entity2 instanceof EntityFlail) {
                    ((EntityFlail) entity2).setShooter(player);
                    ((EntityFlail) entity2).setThrownItemStack(itemstack);
                }
            }
        }
    }

    @Nonnull
    @Override
    public ActionResult<ItemStack> onItemRightClick(@Nonnull final World world,
                                                    @Nonnull final EntityPlayer entityplayer,
                                                    @Nonnull final EnumHand hand) {
        final ItemStack itemstack = entityplayer.getHeldItem(hand);
        if (hand != EnumHand.MAIN_HAND) {
            return new ActionResult<>(EnumActionResult.FAIL, itemstack);
        }
        this.removePreviousFlail(world, entityplayer);
        if (!itemstack.isEmpty()) {
            entityplayer.swingArm(hand);
            itemstack.damageItem(1, entityplayer);
            this.throwFlail(itemstack, world, entityplayer);
        }
        return new ActionResult<>(EnumActionResult.SUCCESS, itemstack);
    }

    @Override
    public boolean hitEntity(@Nonnull final ItemStack itemstack, @Nonnull final EntityLivingBase entityliving,
                             @Nonnull final EntityLivingBase attacker) {
        this.onItemRightClick(attacker.world, (EntityPlayer) attacker, EnumHand.MAIN_HAND);
        return true;
    }

    public void throwFlail(final ItemStack itemstack, final World world, final EntityPlayer entityplayer) {
        world.playSound(null, entityplayer.posX, entityplayer.posY, entityplayer.posZ, SoundEvents.ENTITY_ARROW_SHOOT
                , SoundCategory.PLAYERS, 0.5f, 0.4f / (ItemFlail.random.nextFloat() * 0.4f + 0.8f));
        if (!world.isRemote) {
            final EntityFlail entityflail = new EntityFlail(world, entityplayer, itemstack);
            entityflail.shoot(entityplayer, entityplayer.rotationPitch, entityplayer.rotationYaw, 0.0f, 0.75f, 3.0f);
            PlayerWeaponData.setFlailEntityId(entityplayer, entityflail.getEntityId());
            world.spawnEntity(entityflail);
        }
        this.setThrown(entityplayer, true);
    }

    public void setThrown(final EntityPlayer entityplayer, final boolean flag) {
        PlayerWeaponData.setFlailThrown(entityplayer, flag);
    }

    private void removePreviousFlail(final World world, final EntityPlayer entityplayer) {
        final int id = PlayerWeaponData.getFlailEntityId(entityplayer);
        if (id != 0) {
            final Entity entity = world.getEntityByID(id);
            if (entity instanceof EntityFlail) {
                entity.remove();
            }
        }
    }

    public boolean getThrown(final EntityPlayer entityplayer) {
        return PlayerWeaponData.isFlailThrown(entityplayer);
    }

    public float getFlailDamage() {
        return this.flailDamage;
    }
}
