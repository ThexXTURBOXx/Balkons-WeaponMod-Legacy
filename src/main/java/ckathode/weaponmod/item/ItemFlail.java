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

    public ItemFlail(String id, MeleeComponent meleecomponent) {
        super(id, meleecomponent);
        flailDamage = 4.0f + meleecomponent.weaponMaterial.getAttackDamage();
        addPropertyOverride(new ResourceLocation(BalkonsWeaponMod.MOD_ID, "thrown"), new IItemPropertyGetter() {
            @Override
            @OnlyIn(Dist.CLIENT)
            public float call(@Nonnull ItemStack stack, @Nullable World worldIn,
                              @Nullable EntityLivingBase entityIn) {
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
    public void inventoryTick(@Nonnull ItemStack itemstack, @Nonnull World world,
                              @Nonnull Entity entity, int i, boolean flag) {
        if (!(entity instanceof EntityPlayer)) {
            return;
        }
        EntityPlayer player = (EntityPlayer) entity;
        if (!PlayerWeaponData.isFlailThrown(player)) {
            return;
        }
        ItemStack itemstack2 = player.getHeldItemMainhand();
        if (itemstack2.isEmpty() || itemstack2.getItem() != this) {
            setThrown(player, false);
        } else {
            int id = PlayerWeaponData.getFlailEntityId(player);
            if (id != 0) {
                Entity entity2 = world.getEntityByID(id);
                if (entity2 instanceof EntityFlail) {
                    ((EntityFlail) entity2).setShooter(player);
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
            itemstack.damageItem(1, entityplayer);
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
        world.playSound(null, entityplayer.posX, entityplayer.posY, entityplayer.posZ, SoundEvents.ENTITY_ARROW_SHOOT
                , SoundCategory.PLAYERS, 0.5f, 0.4f / (ItemFlail.random.nextFloat() * 0.4f + 0.8f));
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

    private void removePreviousFlail(World world, EntityPlayer entityplayer) {
        int id = PlayerWeaponData.getFlailEntityId(entityplayer);
        if (id != 0) {
            Entity entity = world.getEntityByID(id);
            if (entity instanceof EntityFlail) {
                entity.remove();
            }
        }
    }

    public boolean getThrown(EntityPlayer entityplayer) {
        return PlayerWeaponData.isFlailThrown(entityplayer);
    }

    public float getFlailDamage() {
        return flailDamage;
    }
}
