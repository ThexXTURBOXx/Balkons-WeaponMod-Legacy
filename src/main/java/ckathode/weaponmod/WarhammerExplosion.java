package ckathode.weaponmod;

import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class WarhammerExplosion extends AdvancedExplosion {
    public WarhammerExplosion(World world, Entity entity, double d, double d1,
                              double d2, float f, boolean flame, Mode mode) {
        super(world, entity, d, d1, d2, f, flame, mode);
    }

    @Override
    public void doEntityExplosion(DamageSource damagesource) {
        float size = explosionSize * 2.0f;
        int k1 = MathHelper.floor(explosionX - size - 1.0);
        int l1 = MathHelper.floor(explosionX + size + 1.0);
        int i2 = MathHelper.floor(explosionY - size - 1.0);
        int i3 = MathHelper.floor(explosionY + size + 1.0);
        int j2 = MathHelper.floor(explosionZ - size - 1.0);
        int j3 = MathHelper.floor(explosionZ + size + 1.0);
        List<Entity> list = worldObj.getEntitiesWithinAABBExcludingEntity(exploder,
                new AxisAlignedBB(k1, i2, j2, l1, i3, j3));
        for (Entity entity : list) {
            double dr = MathHelper.sqrt(entity.getDistanceSq(explosionX, explosionY, explosionZ)) / size;
            if (dr <= 1.0) {
                double dx = entity.posX - explosionX;
                double dy = entity.posY + entity.getEyeHeight() - explosionY;
                double dz = entity.posZ - explosionZ;
                double d = MathHelper.sqrt(dx * dx + dy * dy + dz * dz);
                dx /= d;
                dy /= d;
                dz /= d;
                double var36 = 1.0 - dr;
                int damage = (int) ((var36 * var36 + var36) / 2.0 * 8.0 * size + 1.0);
                entity.attackEntityFrom(damagesource, (float) damage);
                entity.setMotion(entity.getMotion().add(
                        new Vec3d(dx * var36, dy * var36, dz * var36)));
            }
        }
    }
}
