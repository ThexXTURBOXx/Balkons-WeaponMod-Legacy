package ckathode.weaponmod.item;

import ckathode.weaponmod.BalkonsWeaponMod;
import ckathode.weaponmod.PlayerWeaponData;
import ckathode.weaponmod.entity.projectile.EntityFlail;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
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
                              @Nullable LivingEntity entityIn) {
                return entityIn instanceof PlayerEntity && entityIn.getHeldItemMainhand() == stack
                       && isThrown((PlayerEntity) entityIn) ? 1.0f : 0.0f;
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
        if (!(entity instanceof PlayerEntity)) {
            return;
        }
        PlayerEntity player = (PlayerEntity) entity;
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
                    ((EntityFlail) entity2).setShooter(player);
                    ((EntityFlail) entity2).setThrownItemStack(itemstack);
                }
            }
        }
    }

    @Nonnull
    @Override
    public ActionResult<ItemStack> onItemRightClick(@Nonnull World world,
                                                    @Nonnull PlayerEntity entityplayer,
                                                    @Nonnull Hand hand) {
        ItemStack itemstack = entityplayer.getHeldItem(hand);
        if (hand != Hand.MAIN_HAND) {
            return new ActionResult<>(ActionResultType.FAIL, itemstack);
        }
        removePreviousFlail(world, entityplayer);
        if (!itemstack.isEmpty()) {
            entityplayer.swingArm(hand);
            if (!entityplayer.isCreative()) {
                itemstack.damageItem(1, entityplayer, s -> s.sendBreakAnimation(Hand.MAIN_HAND));
            }
            if (!itemstack.isEmpty()) {
                throwFlail(itemstack, world, entityplayer);
            }
        }
        return new ActionResult<>(ActionResultType.SUCCESS, itemstack);
    }

    @Override
    public boolean hitEntity(@Nonnull ItemStack itemstack, @Nonnull LivingEntity entityliving,
                             @Nonnull LivingEntity attacker) {
        onItemRightClick(attacker.world, (PlayerEntity) attacker, Hand.MAIN_HAND);
        return true;
    }

    public void throwFlail(ItemStack itemstack, World world, PlayerEntity entityplayer) {
        world.playSound(null, entityplayer.posX, entityplayer.posY, entityplayer.posZ, SoundEvents.ENTITY_ARROW_SHOOT,
                SoundCategory.PLAYERS, 0.5f, 0.4f / (ItemFlail.random.nextFloat() * 0.4f + 0.8f));
        if (!world.isRemote) {
            EntityFlail entityflail = new EntityFlail(world, entityplayer, itemstack);
            entityflail.shoot(entityplayer, entityplayer.rotationPitch, entityplayer.rotationYaw, 0.0f, 0.75f, 3.0f);
            PlayerWeaponData.setFlailEntityId(entityplayer, entityflail.getEntityId());
            world.addEntity(entityflail);
            setThrown(entityplayer, true);
        }
    }

    public void setThrown(PlayerEntity entityplayer, boolean flag) {
        PlayerWeaponData.setFlailThrown(entityplayer, flag);
    }

    public boolean isThrown(PlayerEntity entityplayer) {
        return PlayerWeaponData.isFlailThrown(entityplayer);
    }

    private void removePreviousFlail(World world, PlayerEntity entityplayer) {
        int id = PlayerWeaponData.getFlailEntityId(entityplayer);
        if (id != 0) {
            Entity entity = world.getEntityByID(id);
            if (entity instanceof EntityFlail) {
                entity.remove();
            }
        }
    }

    public float getFlailDamage() {
        return flailDamage;
    }
}
