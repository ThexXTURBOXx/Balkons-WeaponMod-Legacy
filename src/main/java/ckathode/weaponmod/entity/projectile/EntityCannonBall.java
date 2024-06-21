package ckathode.weaponmod.entity.projectile;

import ckathode.weaponmod.BalkonsWeaponMod;
import ckathode.weaponmod.PhysHelper;
import ckathode.weaponmod.WeaponDamageSource;
import ckathode.weaponmod.entity.EntityCannon;
import javax.annotation.Nonnull;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityCannonBall extends EntityProjectile {
    public static final String NAME = "cannonball";

    public EntityCannonBall(World world) {
        super(world);
    }

    public EntityCannonBall(World world, double d, double d1, double d2) {
        this(world);
        setPosition(d, d1, d2);
    }

    public EntityCannonBall(World world, EntityCannon entitycannon, float f, float f1,
                            boolean superPowered) {
        this(world, entitycannon.posX, entitycannon.posY + 1.0, entitycannon.posZ);
        Entity entityPassenger = entitycannon.riddenByEntity;
        setThrower(entitycannon);
        if (entityPassenger instanceof EntityLivingBase) {
            setPickupStatusFromEntity((EntityLivingBase) entityPassenger);
        } else {
            setPickupStatus(PickupStatus.ALLOWED);
        }
        setSize(0.5f, 0.5f);
        float x = -MathHelper.sin(f1 * 0.017453292f) * MathHelper.cos(f * 0.017453292f);
        float y = -MathHelper.sin(f * 0.017453292f);
        float z = MathHelper.cos(f1 * 0.017453292f) * MathHelper.cos(f * 0.017453292f);
        setThrowableHeading(x, y, z, superPowered ? 4.0f : 2.0f, superPowered ? 0.1f : 2.0f);
        motionX += entitycannon.motionX;
        motionZ += entitycannon.motionZ;
        setIsCritical(superPowered);
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        double speed = MathHelper.sqrt_double(motionX * motionX + motionY * motionY + motionZ * motionZ);
        double amount = 8.0;
        if (speed > 1.0) {
            for (int i1 = 1; i1 < amount; ++i1) {
                worldObj.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, posX + motionX * i1 / amount,
                        posY + motionY * i1 / amount, posZ + motionZ * i1 / amount, 0.0, 0.0, 0.0);
            }
        }
    }

    public void createCrater() {
        if (worldObj.isRemote || !inGround || isInWater()) {
            return;
        }
        setDead();
        float f = getIsCritical() ? 5.0f : 2.5f;
        PhysHelper.createAdvancedExplosion(worldObj, this, posX, posY, posZ, f,
                BalkonsWeaponMod.instance.modConfig.cannonDoesBlockDamage, true, false, false);
    }

    @Override
    public void onEntityHit(Entity entity) {
        DamageSource damagesource = WeaponDamageSource.causeProjectileWeaponDamage(this, getDamagingEntity());
        if (entity.attackEntityFrom(damagesource, 30.0f)) {
            worldObj.playSoundAtEntity(this, "game.player.hurt", 1.0F, 1.2F / (rand.nextFloat() * 0.4F + 0.7F));
        }
    }

    @Override
    public void onGroundHit(MovingObjectPosition raytraceResult) {
        BlockPos blockpos = raytraceResult.getBlockPos();
        xTile = blockpos.getX();
        yTile = blockpos.getY();
        zTile = blockpos.getZ();
        IBlockState iBlockState = worldObj.getBlockState(blockpos);
        inTile = iBlockState.getBlock();
        inData = inTile.getMetaFromState(iBlockState);
        motionX = raytraceResult.hitVec.xCoord - posX;
        motionY = raytraceResult.hitVec.yCoord - posY;
        motionZ = raytraceResult.hitVec.zCoord - posZ;
        float f1 = MathHelper.sqrt_double(motionX * motionX + motionY * motionY + motionZ * motionZ);
        posX -= motionX / f1 * 0.05;
        posY -= motionY / f1 * 0.05;
        posZ -= motionZ / f1 * 0.05;
        inGround = true;
        if (inTile.getMaterial() != Material.air) {
            inTile.onEntityCollidedWithBlock(worldObj, blockpos, iBlockState, this);
        }
        createCrater();
    }

    @Override
    public boolean canBeCritical() {
        return true;
    }

    @Override
    public float getAirResistance() {
        return 0.98f;
    }

    @Override
    public float getGravity() {
        return 0.04f;
    }

    @Nonnull
    @Override
    public ItemStack getPickupItem() {
        return new ItemStack(BalkonsWeaponMod.cannonBall, 1);
    }
}
