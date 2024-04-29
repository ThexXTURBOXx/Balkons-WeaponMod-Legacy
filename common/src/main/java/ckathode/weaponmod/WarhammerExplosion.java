package ckathode.weaponmod;

import java.util.List;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class WarhammerExplosion extends AdvancedExplosion {

    public WarhammerExplosion(Level world, Entity entity, double d, double d1,
                              double d2, float f, boolean flame, BlockInteraction mode) {
        super(world, entity, d, d1, d2, f, flame, mode);
    }

    @Override
    public void doEntityExplosion(DamageSource damagesource) {
        float size = explosionSize * 2.0f;
        int k1 = Mth.floor(explosionX - size - 1.0);
        int l1 = Mth.floor(explosionX + size + 1.0);
        int i2 = Mth.floor(explosionY - size - 1.0);
        int i3 = Mth.floor(explosionY + size + 1.0);
        int j2 = Mth.floor(explosionZ - size - 1.0);
        int j3 = Mth.floor(explosionZ + size + 1.0);
        List<Entity> list = worldObj.getEntities(exploder, new AABB(k1, i2, j2, l1, i3, j3));
        for (Entity entity : list) {
            double dr = Mth.sqrt(entity.distanceToSqr(explosionX, explosionY, explosionZ)) / size;
            if (dr <= 1.0) {
                double dx = entity.getX() - explosionX;
                double dy = entity.getEyeY() - explosionY;
                double dz = entity.getZ() - explosionZ;
                double d = Mth.sqrt(dx * dx + dy * dy + dz * dz);
                dx /= d;
                dy /= d;
                dz /= d;
                double var36 = 1.0 - dr;
                int damage = (int) ((var36 * var36 + var36) / 2.0 * 8.0 * size + 1.0);
                entity.hurt(damagesource, (float) damage);
                entity.setDeltaMovement(entity.getDeltaMovement().add(
                        new Vec3(dx * var36, dy * var36, dz * var36)));
            }
        }
    }

}
