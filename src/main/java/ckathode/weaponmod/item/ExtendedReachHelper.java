package ckathode.weaponmod.item;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public final class ExtendedReachHelper {
    private static final Minecraft mc = Minecraft.getInstance();

    public static RayTraceResult getMouseOver(float frame, float dist) {
        RayTraceResult result = null;
        Entity entity = mc.getCameraEntity();
        if (entity != null && mc.level != null) {
            double distNew = dist;
            result = entity.pick(distNew, frame, false);
            double calcDist = distNew;
            Vector3d pos = entity.getEyePosition(frame);
            distNew = calcDist;

            calcDist *= calcDist;
            if (result != null) {
                calcDist = result.getLocation().distanceToSqr(pos);
            }

            Vector3d lookVec = entity.getViewVector(1.0F);
            Vector3d traced = pos.add(lookVec.x * distNew, lookVec.y * distNew, lookVec.z * distNew);
            Entity pointedEntity = null;
            float f = 1.0F;
            final AxisAlignedBB axisalignedbb = entity.getBoundingBox().expandTowards(lookVec.scale(distNew))
                    .inflate(1.0, 1.0, 1.0);
            final EntityRayTraceResult entityraytraceresult = ProjectileHelper.getEntityHitResult(entity, pos, traced,
                    axisalignedbb, e -> !e.isSpectator() && e.isPickable(), calcDist);
            if (entityraytraceresult != null) {
                final Entity entity2 = entityraytraceresult.getEntity();
                final Vector3d hitVec = entityraytraceresult.getLocation();
                final double d1 = pos.distanceToSqr(hitVec);
                if (d1 < calcDist || result == null) {
                    result = entityraytraceresult;
                }
            }
        }
        return result;
    }

}
