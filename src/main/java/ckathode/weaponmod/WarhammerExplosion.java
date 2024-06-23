package ckathode.weaponmod;

import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class WarhammerExplosion extends AdvancedExplosion {
    public WarhammerExplosion(World world, Entity entity, double d, double d1,
                              double d2, float f, boolean flame, boolean smoke) {
        super(world, entity, d, d1, d2, f, flame, smoke);
    }

    @Override
    public void doEntityExplosion(DamageSource damagesource) {
        float size = explosionSize * 2.0f;
        int k1 = MathHelper.floor_double(explosionX - size - 1.0);
        int l1 = MathHelper.floor_double(explosionX + size + 1.0);
        int i2 = MathHelper.floor_double(explosionY - size - 1.0);
        int i3 = MathHelper.floor_double(explosionY + size + 1.0);
        int j2 = MathHelper.floor_double(explosionZ - size - 1.0);
        int j3 = MathHelper.floor_double(explosionZ + size + 1.0);
        List<Entity> list = worldObj.getEntitiesWithinAABBExcludingEntity(exploder,
                AxisAlignedBB.getBoundingBox(k1, i2, j2, l1, i3, j3));
        for (Entity entity : list) {
            double dr = entity.getDistance(explosionX, explosionY, explosionZ) / size;
            if (dr <= 1.0) {
                double dx = entity.posX - explosionX;
                double dy = entity.posY + entity.getEyeHeight() - explosionY;
                double dz = entity.posZ - explosionZ;
                double d = MathHelper.floor_double(dx * dx + dy * dy + dz * dz);
                dx /= d;
                dy /= d;
                dz /= d;
                double var36 = 1.0 - dr;
                int damage = (int) ((var36 * var36 + var36) / 2.0 * 8.0 * size + 1.0);
                entity.attackEntityFrom(damagesource, (float) damage);
                entity.motionX += dx * var36;
                entity.motionY += dy * var36;
                entity.motionZ += dz * var36;
            }
        }
    }
}
