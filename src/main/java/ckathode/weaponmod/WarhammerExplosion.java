package ckathode.weaponmod;

import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import java.util.*;

public class WarhammerExplosion extends AdvancedExplosion
{
    public WarhammerExplosion(final World world, final Entity entity, final double d, final double d1, final double d2, final float f, final boolean flame, final boolean smoke) {
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
        final List<Entity> list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this.exploder, new AxisAlignedBB(k1, i2, j2, l1, i3, j3));
        for (int m = 0; m < list.size(); ++m) {
            final Entity entity = list.get(m);
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
                final int damage = (int)((var36 * var36 + var36) / 2.0 * 8.0 * size + 1.0);
                entity.attackEntityFrom(damagesource, (float)damage);
                final Entity entity2 = entity;
                entity2.motionX += dx * var36;
                final Entity entity3 = entity;
                entity3.motionY += dy * var36;
                final Entity entity4 = entity;
                entity4.motionZ += dz * var36;
            }
        }
    }
}
