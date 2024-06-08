package ckathode.weaponmod.entity.projectile;

import ckathode.weaponmod.PlayerWeaponData;
import ckathode.weaponmod.WMDamageSources;
import ckathode.weaponmod.WMRegistries;
import ckathode.weaponmod.item.ItemFlail;
import dev.architectury.networking.NetworkManager;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class EntityFlail extends EntityMaterialProjectile<EntityFlail> {

    public static final String ID = "flail";
    public static final EntityType<EntityFlail> TYPE = WMRegistries.createEntityType(
            ID, EntityDimensions.fixed(0.5f, 0.5f).withEyeHeight(0.0f), EntityFlail::new);

    public boolean isSwinging;
    private float flailDamage;
    private Vec3 distance;

    public EntityFlail(EntityType<EntityFlail> entityType, Level world) {
        super(entityType, world);
        noCulling = true;
        flailDamage = 1.0f;
        distance = Vec3.ZERO;
    }

    public EntityFlail(Level world, double d, double d1, double d2) {
        this(TYPE, world);
        setPos(d, d1, d2);
    }

    public EntityFlail(Level worldIn, LivingEntity shooter, ItemStack itemstack) {
        this(worldIn, shooter.getX(), shooter.getEyeY() - 0.3, shooter.getZ());
        setOwner(shooter);
        setPickupStatusFromEntity(shooter);
        setThrownItemStack(itemstack);
    }

    @NotNull
    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkManager.createAddEntityPacket(this);
    }

    @Override
    public void shootFromRotation(Entity entity, float f, float f1, float f2, float f3, float f4) {
        Vec3 entityMotion = entity.getDeltaMovement();
        setDeltaMovement(getDeltaMovement().add(entityMotion.x, entity.onGround() ? 0 : entityMotion.y,
                entityMotion.z));
        swing(f, f1, f3, f4);
    }

    @Override
    public void tick() {
        super.tick();
        Entity shooter = getOwner();
        if (shooter != null) {
            distance = shooter.position().subtract(position());
            if (distance.lengthSqr() > 9.0) {
                returnToOwner(true);
            }
            if (shooter instanceof Player) {
                ItemStack itemstack = ((Player) shooter).getMainHandItem();
                ItemStack thrownItem = getWeapon();
                if (itemstack.isEmpty() || (thrownItem != null && itemstack.getItem() != thrownItem.getItem()) || !shooter.isAlive()) {
                    pickUpByOwner();
                }
            }
        } else if (!level().isClientSide) {
            remove(RemovalReason.DISCARDED);
        }
        if (inGround) {
            inGround = false;
            return;
        }
        returnToOwner(false);
    }

    public void returnToOwner(boolean looseFromGround) {
        if (looseFromGround) {
            inGround = false;
        }
        Entity shooter = getOwner();
        if (shooter == null) {
            return;
        }
        double targetPosX = shooter.getX();
        double targetPosY = shooter.getBoundingBox().minY + 0.4000000059604645;
        double targetPosZ = shooter.getZ();
        float f = 27.0f;
        float f2 = 2.0f;
        targetPosX += -Math.sin((shooter.getYRot() + f) * 0.017453292f) * Math.cos(shooter.getXRot() * 0.017453292f) * f2;
        targetPosZ += Math.cos((shooter.getYRot() + f) * 0.017453292f) * Math.cos(shooter.getXRot() * 0.017453292f) * f2;
        distance = new Vec3(targetPosX, targetPosY, targetPosZ).subtract(position());
        double distanceTotalSqr = distance.lengthSqr();
        if (distanceTotalSqr > 9.0) {
            setPos(targetPosX, targetPosY, targetPosZ);
        } else if (distanceTotalSqr > 6.25) {
            isSwinging = false;
            setDeltaMovement(getDeltaMovement().scale(-0.5));
        }
        if (!isSwinging) {
            float f3 = 0.2f;
            setDeltaMovement(distance.scale(f3 * Math.sqrt(distanceTotalSqr)));
        }
    }

    public void pickUpByOwner() {
        remove(RemovalReason.DISCARDED);
        Entity shooter = getOwner();
        if (shooter instanceof Player && getWeapon() != null) {
            PlayerWeaponData.setFlailThrown((Player) shooter, false);
        }
    }

    public void swing(float f, float f1, float f2, float f3) {
        if (isSwinging) {
            return;
        }
        playSound(SoundEvents.ARROW_SHOOT, 0.5f, 0.4f / (random.nextFloat() * 0.4f + 0.8f));
        float x = -Mth.sin(f1 * 0.017453292f) * Mth.cos(f * 0.017453292f);
        float y = -Mth.sin(f * 0.017453292f);
        float z = Mth.cos(f1 * 0.017453292f) * Mth.cos(f * 0.017453292f);
        shoot(x, y, z, f2, f3);
        isSwinging = true;
        inGround = false;
    }

    @Override
    public void onEntityHit(Entity entity) {
        if (entity.equals(getOwner())) {
            return;
        }
        Entity shooter = getDamagingEntity();
        DamageSource damagesource;
        if (shooter instanceof LivingEntity) {
            damagesource = damageSources().mobAttack((LivingEntity) shooter);
        } else {
            damagesource = damageSources().source(WMDamageSources.WEAPON, this, shooter);
        }
        if (entity.hurt(damagesource, flailDamage + extraDamage)) {
            playHitSound();
            returnToOwner(true);
        } else {
            bounceBack();
        }
    }

    @Override
    public void bounceBack() {
        setDeltaMovement(getDeltaMovement().scale(-0.8));
        setYRot(getYRot() + 180.0f);
        yRotO += 180.0f;
        ticksInAir = 0;
    }

    @Override
    public void playHitSound() {
        if (inGround) {
            return;
        }
        playSound(SoundEvents.PLAYER_HURT, 1.0f, random.nextFloat() * 0.4f + 0.8f);
    }

    @Override
    public void setThrownItemStack(@NotNull ItemStack itemstack) {
        if (!(itemstack.getItem() instanceof ItemFlail)) {
            return;
        }
        super.setThrownItemStack(itemstack);
        flailDamage = ((ItemFlail) itemstack.getItem()).getFlailDamage();
    }

    @Override
    public void addAdditionalSaveData(CompoundTag nbttagcompound) {
        super.addAdditionalSaveData(nbttagcompound);
        nbttagcompound.putFloat("fDmg", flailDamage);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag nbttagcompound) {
        super.readAdditionalSaveData(nbttagcompound);
        flailDamage = nbttagcompound.getFloat("fDmg");
    }

    @Override
    public void playerTouch(@NotNull Player entityplayer) {
    }

    @Override
    public int getMaxArrowShake() {
        return 0;
    }

    @NotNull
    @Override
    protected ItemStack getDefaultPickupItem() {
        return new ItemStack(WMRegistries.ITEM_FLAIL_WOOD.get());
    }

}
