package ckathode.weaponmod;

import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class WarhammerExplosion extends AdvancedExplosion {
    public WarhammerExplosion(final World world, final Entity entity, final double d, final double d1,
                              final double d2, final float f, final boolean flame, final boolean smoke) {
        super(world, entity, d, d1, d2, f, flame, smoke);
    }

    @Override
    public void doEntityExplosion(final DamageSource damagesource) {
        final float size = this.explosionSize * 2.0f;
        final int k1 = MathHelper.floor(this.explosionX - size - 1.0);
        final int l1 = MathHelper.floor(this.explosionX + size + 1.0);
        final int i2 = MathHelper.floor(this.explosionY - size - 1.0);
        final int i3 = MathHelper.floor(this.explosionY + size + 1.0);
        final int j2 = MathHelper.floor(this.explosionZ - size - 1.0);
        final int j3 = MathHelper.floor(this.explosionZ + size + 1.0);
        final List<Entity> list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this.exploder,
                new AxisAlignedBB(k1, i2, j2, l1, i3, j3));
        for (final Entity entity : list) {
            final double dr = entity.getDistance(this.explosionX, this.explosionY, this.explosionZ) / size;
            if (dr <= 1.0) {
                double dx = entity.posX - this.explosionX;
                double dy = entity.posY + entity.getEyeHeight() - this.explosionY;
                double dz = entity.posZ - this.explosionZ;
                final double d = MathHelper.sqrt(dx * dx + dy * dy + dz * dz);
                dx /= d;
                dy /= d;
                dz /= d;
                final double var36 = 1.0 - dr;
                final int damage = (int) ((var36 * var36 + var36) / 2.0 * 8.0 * size + 1.0);
                entity.attackEntityFrom(damagesource, (float) damage);
                entity.motionX += dx * var36;
                entity.motionY += dy * var36;
                entity.motionZ += dz * var36;
            }
        }
    }
}
