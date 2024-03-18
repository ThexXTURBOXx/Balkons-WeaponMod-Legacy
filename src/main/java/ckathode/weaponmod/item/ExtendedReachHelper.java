package ckathode.weaponmod.item;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public final class ExtendedReachHelper {
    private static final Minecraft mc = Minecraft.getInstance();

    public static RayTraceResult getMouseOver(float frame, float dist) {
        RayTraceResult result = null;
        Entity entity = mc.getRenderViewEntity();
        if (entity != null && mc.world != null) {
            double distNew = dist;
            result = entity.pick(distNew, frame, false);
            double calcDist = distNew;
            Vec3d pos = entity.getEyePosition(frame);
            distNew = calcDist;

            calcDist *= calcDist;
            if (result != null) {
                calcDist = result.getHitVec().squareDistanceTo(pos);
            }

            Vec3d lookVec = entity.getLook(1.0F);
            Vec3d traced = pos.add(lookVec.x * distNew, lookVec.y * distNew, lookVec.z * distNew);
            Entity pointedEntity = null;
            float f = 1.0F;
            final AxisAlignedBB axisalignedbb = entity.getBoundingBox().expand(lookVec.scale(distNew))
                    .grow(1.0, 1.0, 1.0);
            final EntityRayTraceResult entityraytraceresult = ProjectileHelper.rayTraceEntities(entity, pos, traced,
                    axisalignedbb, e -> !e.isSpectator() && e.canBeCollidedWith(), calcDist);
            if (entityraytraceresult != null) {
                final Entity entity2 = entityraytraceresult.getEntity();
                final Vec3d hitVec = entityraytraceresult.getHitVec();
                final double d1 = pos.squareDistanceTo(hitVec);
                if (d1 < calcDist || result == null) {
                    result = entityraytraceresult;
                }
            }
        }
        return result;
    }

}
