package ckathode.weaponmod;

import ckathode.weaponmod.network.MsgExplosion;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.play.server.SPacketEntityVelocity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.NetworkRegistry;

public abstract class PhysHelper {
    private static double kbMotionX;
    private static double kbMotionY;
    private static double kbMotionZ;
    private static int knockBackModifier;

    public static AdvancedExplosion createStandardExplosion(final World world, final Entity entity, final double d,
                                                            final double d1, final double d2, final float size,
                                                            final boolean flame, final boolean smoke) {
        final AdvancedExplosion explosion = new AdvancedExplosion(world, entity, d, d1, d2, size, flame, smoke);
        explosion.doEntityExplosion();
        explosion.doBlockExplosion();
        explosion.doParticleExplosion(true, true);
        sendExplosion(world, explosion, true, true);
        return explosion;
    }

    public static AdvancedExplosion createAdvancedExplosion(final World world, final Entity entity, final double d,
                                                            final double d1, final double d2, final float size,
                                                            final boolean destroyBlocks,
                                                            final boolean spawnSmallParticles,
                                                            final boolean spawnBigParticles, final boolean flame,
                                                            final boolean smoke) {
        final AdvancedExplosion explosion = new AdvancedExplosion(world, entity, d, d1, d2, size, flame, smoke);
        explosion.doEntityExplosion();
        if (destroyBlocks) {
            explosion.doBlockExplosion();
        }
        if (flame) {
            explosion.doFlaming();
        }
        explosion.doParticleExplosion(spawnSmallParticles, spawnBigParticles);
        sendExplosion(world, explosion, spawnSmallParticles, spawnBigParticles);
        return explosion;
    }

    public static AdvancedExplosion createAdvancedExplosion(final World world, final Entity entity,
                                                            final DamageSource damagesource, final double d,
                                                            final double d1, final double d2, final float size,
                                                            final boolean destroyBlocks,
                                                            final boolean spawnSmallParticles,
                                                            final boolean spawnBigParticles, final boolean flame,
                                                            final boolean smoke) {
        final AdvancedExplosion explosion = new AdvancedExplosion(world, entity, d, d1, d2, size, flame, smoke);
        explosion.doEntityExplosion(damagesource);
        if (destroyBlocks) {
            explosion.doBlockExplosion();
        }
        if (flame) {
            explosion.doFlaming();
        }
        explosion.doParticleExplosion(spawnSmallParticles, spawnBigParticles);
        sendExplosion(world, explosion, spawnSmallParticles, spawnBigParticles);
        return explosion;
    }

    public static AdvancedExplosion createAdvancedExplosion(final World world, final Entity entity, final double d,
                                                            final double d1, final double d2, final float size,
                                                            final boolean destroyBlocks, final boolean spawnParticles
            , final boolean flame, final boolean smoke) {
        final AdvancedExplosion explosion = new AdvancedExplosion(world, entity, d, d1, d2, size, flame, smoke);
        explosion.doEntityExplosion();
        if (destroyBlocks) {
            explosion.doBlockExplosion();
        }
        if (flame) {
            explosion.doFlaming();
        }
        explosion.doParticleExplosion(spawnParticles, spawnParticles);
        sendExplosion(world, explosion, spawnParticles, spawnParticles);
        return explosion;
    }

    public static void sendExplosion(final World world, final AdvancedExplosion explosion, final boolean smallparts,
                                     final boolean bigparts) {
        if (world instanceof WorldServer && !world.isRemote) {
            final MsgExplosion msg = new MsgExplosion(explosion, smallparts, bigparts);
            BalkonsWeaponMod.instance.messagePipeline.sendToAllAround(msg,
                    new NetworkRegistry.TargetPoint(world.provider.getDimension(), explosion.explosionX,
                            explosion.explosionY, explosion.explosionZ, 64.0));
        }
    }

    public static void knockBack(final EntityLivingBase entityliving, final EntityLivingBase attacker,
                                 final float knockback) {
        entityliving.motionX = PhysHelper.kbMotionX;
        entityliving.motionY = PhysHelper.kbMotionY;
        entityliving.motionZ = PhysHelper.kbMotionZ;
        double dx;
        double dz;
        for (dx = attacker.posX - entityliving.posX, dz = attacker.posZ - entityliving.posZ; dx * dx + dz * dz < 1.0E-4; dx = (Math.random() - Math.random()) * 0.01, dz = (Math.random() - Math.random()) * 0.01) {
        }
        entityliving.attackedAtYaw =
                (float) (Math.atan2(dz, dx) * 180.0 / 3.141592653589793) - entityliving.rotationYaw;
        final float f = MathHelper.sqrt(dx * dx + dz * dz);
        entityliving.motionX -= dx / f * knockback;
        entityliving.motionY += knockback;
        entityliving.motionZ -= dz / f * knockback;
        if (entityliving.motionY > 0.4) {
            entityliving.motionY = 0.4;
        }
        if (PhysHelper.knockBackModifier > 0) {
            dx = -Math.sin(Math.toRadians(attacker.rotationYaw)) * PhysHelper.knockBackModifier * 0.5;
            dz = Math.cos(Math.toRadians(attacker.rotationYaw)) * PhysHelper.knockBackModifier * 0.5;
            entityliving.addVelocity(dx, 0.1, dz);
        }
        if (entityliving instanceof EntityPlayerMP) {
            ((EntityPlayerMP) entityliving).connection.sendPacket(new SPacketEntityVelocity(entityliving));
        }
        PhysHelper.knockBackModifier = 0;
        PhysHelper.kbMotionX = (PhysHelper.kbMotionY = (PhysHelper.kbMotionZ = 0.0));
    }

    public static void prepareKnockbackOnEntity(final EntityLivingBase attacker, final EntityLivingBase entity) {
        PhysHelper.knockBackModifier = EnchantmentHelper.getKnockbackModifier(attacker);
        if (attacker.isSprinting()) {
            ++PhysHelper.knockBackModifier;
        }
        PhysHelper.kbMotionX = entity.motionX;
        PhysHelper.kbMotionY = entity.motionY;
        PhysHelper.kbMotionZ = entity.motionZ;
    }

    static {
        PhysHelper.kbMotionX = 0.0;
        PhysHelper.kbMotionY = 0.0;
        PhysHelper.kbMotionZ = 0.0;
        PhysHelper.knockBackModifier = 0;
    }
}
