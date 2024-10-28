package ckathode.weaponmod;

import java.util.List;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class WarhammerExplosion extends AdvancedExplosion {

    public WarhammerExplosion(ServerLevel level, Entity entity, Vec3 center, float f, boolean flame,
                              BlockInteraction mode) {
        super(level, entity, center, f, flame, mode);
    }

    @Override
    public void doEntityExplosion(DamageSource damagesource) {
        float size = explosionSize * 2.0f;
        int k1 = Mth.floor(center.x - size - 1.0);
        int l1 = Mth.floor(center.x + size + 1.0);
        int i2 = Mth.floor(center.y - size - 1.0);
        int i3 = Mth.floor(center.y + size + 1.0);
        int j2 = Mth.floor(center.z - size - 1.0);
        int j3 = Mth.floor(center.z + size + 1.0);
        List<Entity> list = serverLevel.getEntities(exploder, new AABB(k1, i2, j2, l1, i3, j3));
        for (Entity entity : list) {
            double dr = Math.sqrt(entity.distanceToSqr(center)) / size;
            if (dr <= 1.0) {
                double dx = entity.getX() - center.x;
                double dy = entity.getEyeY() - center.y;
                double dz = entity.getZ() - center.z;
                double d = Math.sqrt(dx * dx + dy * dy + dz * dz);
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
