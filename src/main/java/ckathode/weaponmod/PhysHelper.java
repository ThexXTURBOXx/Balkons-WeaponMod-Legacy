package ckathode.weaponmod;

import ckathode.weaponmod.network.MsgExplosion;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.play.server.SEntityVelocityPacket;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.PacketDistributor;

public final class PhysHelper {
    private static Vec3d kbMotion = Vec3d.ZERO;
    private static int knockBackModifier = 0;

    public static AdvancedExplosion createStandardExplosion(World world, Entity entity, double d,
                                                            double d1, double d2, float size,
                                                            boolean flame, Explosion.Mode mode) {
        AdvancedExplosion explosion = new AdvancedExplosion(world, entity, d, d1, d2, size, flame, mode);
        explosion.doEntityExplosion();
        explosion.doBlockExplosion();
        explosion.doParticleExplosion(true, true);
        sendExplosion(world, explosion, true, true);
        return explosion;
    }

    public static AdvancedExplosion createAdvancedExplosion(World world, Entity entity, double d,
                                                            double d1, double d2, float size,
                                                            boolean destroyBlocks,
                                                            boolean spawnSmallParticles,
                                                            boolean spawnBigParticles, boolean flame,
                                                            Explosion.Mode mode) {
        AdvancedExplosion explosion = new AdvancedExplosion(world, entity, d, d1, d2, size, flame, mode);
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

    public static AdvancedExplosion createAdvancedExplosion(World world, Entity entity,
                                                            DamageSource damagesource, double d,
                                                            double d1, double d2, float size,
                                                            boolean destroyBlocks,
                                                            boolean spawnSmallParticles,
                                                            boolean spawnBigParticles, boolean flame,
                                                            Explosion.Mode mode) {
        AdvancedExplosion explosion = new AdvancedExplosion(world, entity, d, d1, d2, size, flame, mode);
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

    public static AdvancedExplosion createAdvancedExplosion(World world, Entity entity, double d,
                                                            double d1, double d2, float size,
                                                            boolean destroyBlocks, boolean spawnParticles,
                                                            boolean flame, Explosion.Mode mode) {
        AdvancedExplosion explosion = new AdvancedExplosion(world, entity, d, d1, d2, size, flame, mode);
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

    public static void sendExplosion(World world, AdvancedExplosion explosion, boolean smallparts, boolean bigparts) {
        if (world instanceof ServerWorld && !world.isRemote) {
            MsgExplosion msg = new MsgExplosion(explosion, smallparts, bigparts);
            BalkonsWeaponMod.instance.messagePipeline.sendToAllAround(msg,
                    new PacketDistributor.TargetPoint(explosion.explosionX, explosion.explosionY,
                            explosion.explosionZ, 64.0, world.getDimension().getType()));
        }
    }

    public static void knockBack(LivingEntity entityliving, LivingEntity attacker, float knockback) {
        entityliving.setMotion(kbMotion);
        double dx = attacker.posX - entityliving.posX;
        double dz;
        for (dz = attacker.posZ - entityliving.posZ; dx * dx + dz * dz < 1E-4D;
             dz = (Math.random() - Math.random()) * 0.01D) {
            dx = (Math.random() - Math.random()) * 0.01D;
        }
        entityliving.attackedAtYaw =
                (float) (Math.atan2(dz, dx) * 180.0 / 3.141592653589793) - entityliving.rotationYaw;
        float f = MathHelper.sqrt(dx * dx + dz * dz);
        Vec3d motion = entityliving.getMotion().add(new Vec3d(-dx / f * knockback, knockback, -dz / f * knockback));
        if (motion.y > 0.4) {
            motion = new Vec3d(motion.x, 0.4, motion.z);
        }
        entityliving.setMotion(motion);
        if (knockBackModifier > 0) {
            dx = -Math.sin(Math.toRadians(attacker.rotationYaw)) * knockBackModifier * 0.5;
            dz = Math.cos(Math.toRadians(attacker.rotationYaw)) * knockBackModifier * 0.5;
            entityliving.addVelocity(dx, 0.1, dz);
        }
        if (entityliving instanceof ServerPlayerEntity) {
            ((ServerPlayerEntity) entityliving).connection.sendPacket(new SEntityVelocityPacket(entityliving));
        }
        knockBackModifier = 0;
        kbMotion = Vec3d.ZERO;
    }

    public static void prepareKnockbackOnEntity(LivingEntity attacker, LivingEntity entity) {
        knockBackModifier = EnchantmentHelper.getKnockbackModifier(attacker);
        if (attacker.isSprinting()) {
            ++knockBackModifier;
        }
        kbMotion = entity.getMotion();
    }

}
