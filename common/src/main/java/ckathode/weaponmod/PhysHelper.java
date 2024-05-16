package ckathode.weaponmod;

import ckathode.weaponmod.network.MsgExplosion;
import ckathode.weaponmod.network.WMMessagePipeline;
import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public final class PhysHelper {
    private static Vec3 kbMotion = Vec3.ZERO;
    private static int knockBackModifier = 0;

    public static AdvancedExplosion createStandardExplosion(Level world, Entity entity, double d,
                                                            double d1, double d2, float size,
                                                            boolean flame,
                                                            Explosion.BlockInteraction mode) {
        AdvancedExplosion explosion = new AdvancedExplosion(world, entity,
                d, d1, d2, size, flame, mode);
        explosion.doEntityExplosion();
        explosion.doBlockExplosion();
        explosion.doParticleExplosion(true, true);
        sendExplosion(world, explosion, true, true);
        return explosion;
    }

    public static AdvancedExplosion createAdvancedExplosion(Level world, Entity entity, double d,
                                                            double d1, double d2, float size,
                                                            boolean destroyBlocks,
                                                            boolean spawnSmallParticles,
                                                            boolean spawnBigParticles,
                                                            boolean flame,
                                                            Explosion.BlockInteraction mode) {
        AdvancedExplosion explosion = new AdvancedExplosion(world, entity,
                d, d1, d2, size, flame, mode);
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

    public static AdvancedExplosion createAdvancedExplosion(Level world, Entity entity,
                                                            DamageSource damagesource, double d,
                                                            double d1, double d2, float size,
                                                            boolean destroyBlocks,
                                                            boolean spawnSmallParticles,
                                                            boolean spawnBigParticles,
                                                            boolean flame,
                                                            Explosion.BlockInteraction mode) {
        AdvancedExplosion explosion = new AdvancedExplosion(world, entity,
                d, d1, d2, size, flame, mode);
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

    public static AdvancedExplosion createAdvancedExplosion(Level world, Entity entity, double d,
                                                            double d1, double d2, float size,
                                                            boolean destroyBlocks,
                                                            boolean spawnParticles,
                                                            boolean flame,
                                                            Explosion.BlockInteraction mode) {
        AdvancedExplosion explosion = new AdvancedExplosion(world, entity,
                d, d1, d2, size, flame, mode);
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

    public static void sendExplosion(Level world, AdvancedExplosion explosion,
                                     boolean smallparts, boolean bigparts) {
        if (world instanceof ServerLevel && !world.isClientSide) {
            MsgExplosion msg = new MsgExplosion(explosion, smallparts, bigparts);
            WMMessagePipeline.sendToAround(msg, (ServerLevel) world, explosion.explosionX, explosion.explosionY,
                    explosion.explosionZ, 64.0, world.dimension());
        }
    }

    public static void knockBack(LivingEntity entityliving, LivingEntity attacker, float knockback) {
        entityliving.setDeltaMovement(kbMotion);
        double dx = attacker.getX() - entityliving.getX();
        double dz;
        for (dz = attacker.getZ() - entityliving.getZ(); dx * dx + dz * dz < 1E-4D;
             dz = (Math.random() - Math.random()) * 0.01D) {
            dx = (Math.random() - Math.random()) * 0.01D;
        }
        double f = Math.sqrt(dx * dx + dz * dz);
        Vec3 motion = entityliving.getDeltaMovement().add(new Vec3(-dx / f * knockback, knockback,
                -dz / f * knockback));
        if (motion.y > 0.4) {
            motion = new Vec3(motion.x, 0.4, motion.z);
        }
        entityliving.setDeltaMovement(motion);
        if (knockBackModifier > 0) {
            dx = -Math.sin(Math.toRadians(attacker.getYRot())) * knockBackModifier * 0.5;
            dz = Math.cos(Math.toRadians(attacker.getYRot())) * knockBackModifier * 0.5;
            entityliving.push(dx, 0.1, dz);
        }
        if (entityliving instanceof ServerPlayer) {
            ((ServerPlayer) entityliving).connection.send(new ClientboundSetEntityMotionPacket(entityliving));
        }
        knockBackModifier = 0;
        kbMotion = Vec3.ZERO;
    }

    public static void prepareKnockbackOnEntity(LivingEntity attacker, LivingEntity entity) {
        knockBackModifier = EnchantmentHelper.getKnockbackBonus(attacker);
        if (attacker.isSprinting()) {
            ++knockBackModifier;
        }
        kbMotion = entity.getDeltaMovement();
    }

}
