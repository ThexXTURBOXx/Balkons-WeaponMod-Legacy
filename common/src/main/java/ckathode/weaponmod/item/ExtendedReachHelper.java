package ckathode.weaponmod.item;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

@Environment(EnvType.CLIENT)
public final class ExtendedReachHelper {

    private static final Minecraft mc = Minecraft.getInstance();

    public static HitResult getMouseOver(float frame, float dist) {
        HitResult result = null;
        Entity entity = mc.getCameraEntity();
        if (entity != null && mc.level != null) {
            double distNew = dist;
            result = entity.pick(distNew, frame, false);
            double calcDist = distNew;
            Vec3 pos = entity.getEyePosition(frame);
            distNew = calcDist;

            calcDist *= calcDist;
            if (result != null) {
                calcDist = result.getLocation().distanceToSqr(pos);
            }

            Vec3 lookVec = entity.getViewVector(1.0F);
            Vec3 traced = pos.add(lookVec.x * distNew, lookVec.y * distNew, lookVec.z * distNew);
            Entity pointedEntity = null;
            float f = 1.0F;
            final AABB axisalignedbb = entity.getBoundingBox().expandTowards(lookVec.scale(distNew))
                    .inflate(1.0, 1.0, 1.0);
            final EntityHitResult entityraytraceresult = ProjectileUtil.getEntityHitResult(entity, pos, traced,
                    axisalignedbb, e -> !e.isSpectator() && e.isPickable(), calcDist);
            if (entityraytraceresult != null) {
                final Entity entity2 = entityraytraceresult.getEntity();
                final Vec3 hitVec = entityraytraceresult.getLocation();
                final double d1 = pos.distanceToSqr(hitVec);
                if (d1 < calcDist || result == null) {
                    result = entityraytraceresult;
                }
            }
        }
        return result;
    }

}
