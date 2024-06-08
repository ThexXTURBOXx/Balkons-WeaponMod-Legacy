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
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemFlail extends ItemMelee {
    private final float flailDamage;

    public ItemFlail(String id, MeleeComponent meleecomponent) {
        super(id, meleecomponent);
        flailDamage = 4.0f + meleecomponent.weaponMaterial.getAttackDamage();
        addPropertyOverride(new ResourceLocation(BalkonsWeaponMod.MOD_ID, "thrown"), new IItemPropertyGetter() {
            @Override
            @SideOnly(Side.CLIENT)
            public float apply(@Nonnull ItemStack stack, @Nullable World worldIn,
                               @Nullable EntityLivingBase entityIn) {
                return entityIn instanceof EntityPlayer && entityIn.getHeldItemMainhand() == stack
                       && isThrown((EntityPlayer) entityIn) ? 1.0f : 0.0f;
            }
        });
    }

    @Override
    public int getItemEnchantability() {
        return 0;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean isFull3D() {
        return true;
    }

    @Override
    public void onUpdate(@Nonnull ItemStack itemstack, @Nonnull World world,
                         @Nonnull Entity entity, int i, boolean flag) {
        if (!(entity instanceof EntityPlayer)) {
            return;
        }
        EntityPlayer player = (EntityPlayer) entity;
        if (!isThrown(player)) {
            return;
        }
        ItemStack itemstack2 = player.getHeldItemMainhand();
        if (itemstack2.isEmpty() || !((itemstack2.getItem()) instanceof ItemFlail)) {
            setThrown(player, false);
        } else if (itemstack2.getItem() == this) {
            int id = PlayerWeaponData.getFlailEntityId(player);
            if (id != 0) {
                Entity entity2 = world.getEntityByID(id);
                if (entity2 instanceof EntityFlail) {
                    ((EntityFlail) entity2).setThrower(player);
                    ((EntityFlail) entity2).setThrownItemStack(itemstack);
                }
            }
        }
    }

    @Nonnull
    @Override
    public ActionResult<ItemStack> onItemRightClick(@Nonnull World world,
                                                    @Nonnull EntityPlayer entityplayer,
                                                    @Nonnull EnumHand hand) {
        ItemStack itemstack = entityplayer.getHeldItem(hand);
        if (hand != EnumHand.MAIN_HAND) {
            return new ActionResult<>(EnumActionResult.FAIL, itemstack);
        }
        removePreviousFlail(world, entityplayer);
        if (!itemstack.isEmpty()) {
            entityplayer.swingArm(hand);
            if (!entityplayer.isCreative()) {
                itemstack.damageItem(1, entityplayer);
            }
            throwFlail(itemstack, world, entityplayer);
        }
        return new ActionResult<>(EnumActionResult.SUCCESS, itemstack);
    }

    @Override
    public boolean hitEntity(@Nonnull ItemStack itemstack, @Nonnull EntityLivingBase entityliving,
                             @Nonnull EntityLivingBase attacker) {
        onItemRightClick(attacker.world, (EntityPlayer) attacker, EnumHand.MAIN_HAND);
        return true;
    }

    public void throwFlail(ItemStack itemstack, World world, EntityPlayer entityplayer) {
        world.playSound(null, entityplayer.posX, entityplayer.posY, entityplayer.posZ, SoundEvents.ENTITY_ARROW_SHOOT,
                SoundCategory.PLAYERS, 0.5f, 0.4f / (ItemFlail.itemRand.nextFloat() * 0.4f + 0.8f));
        if (!world.isRemote) {
            EntityFlail entityflail = new EntityFlail(world, entityplayer, itemstack);
            entityflail.shoot(entityplayer, entityplayer.rotationPitch, entityplayer.rotationYaw, 0.0f, 0.75f, 3.0f);
            PlayerWeaponData.setFlailEntityId(entityplayer, entityflail.getEntityId());
            world.spawnEntity(entityflail);
        }
        setThrown(entityplayer, true);
    }

    public void setThrown(EntityPlayer entityplayer, boolean flag) {
        PlayerWeaponData.setFlailThrown(entityplayer, flag);
    }

    public boolean isThrown(EntityPlayer entityplayer) {
        return PlayerWeaponData.isFlailThrown(entityplayer);
    }

    private void removePreviousFlail(World world, EntityPlayer entityplayer) {
        int id = PlayerWeaponData.getFlailEntityId(entityplayer);
        if (id != 0) {
            Entity entity = world.getEntityByID(id);
            if (entity instanceof EntityFlail) {
                entity.setDead();
            }
        }
    }

    public float getFlailDamage() {
        return flailDamage;
    }
}
