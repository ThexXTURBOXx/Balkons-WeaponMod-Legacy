package ckathode.weaponmod.item;

import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceFluidMode;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public final class ExtendedReachHelper {
    private static final Minecraft mc = Minecraft.getInstance();

    public static RayTraceResult getMouseOver(float frame, float dist) {
        RayTraceResult raytraceResult = null;
        if (mc.getRenderViewEntity() != null && mc.world != null) {
            double var2 = dist;
            raytraceResult = mc.getRenderViewEntity().rayTrace(var2, frame,
                    RayTraceFluidMode.NEVER);
            double calcdist = var2;
            Vec3d pos = mc.getRenderViewEntity().getEyePosition(frame);
            var2 = calcdist;
            if (raytraceResult != null) {
                calcdist = raytraceResult.hitVec.distanceTo(pos);
            }
            Vec3d lookvec = mc.getRenderViewEntity().getLook(frame);
            Vec3d var3 = pos.add(lookvec.x * var2, lookvec.y * var2, lookvec.z * var2);
            Entity pointedEntity = null;
            float var4 = 1.0f;
            List<Entity> list =
                    mc.world.getEntitiesInAABBexcluding(mc.getRenderViewEntity(),
                            mc.getRenderViewEntity().getBoundingBox()
                                    .expand(lookvec.x * var2, lookvec.y * var2, lookvec.z * var2)
                                    .grow(var4, var4, var4),
                            EntitySelectors.NOT_SPECTATING.and(entity -> entity != null && entity.canBeCollidedWith()));
            double d = calcdist;
            for (Entity entity : list) {
                AxisAlignedBB aabb = entity.getBoundingBox().grow(entity.getCollisionBorderSize());
                RayTraceResult raytraceResult2 = aabb.calculateIntercept(pos, var3);
                if (aabb.contains(pos)) {
                    if (d < 0.0) {
                        continue;
                    }
                    pointedEntity = entity;
                    d = 0.0;
                } else {
                    if (raytraceResult2 == null) {
                        continue;
                    }
                    double d2 = pos.distanceTo(raytraceResult2.hitVec);
                    if (d2 >= d && d != 0.0) {
                        continue;
                    }
                    pointedEntity = entity;
                    d = d2;
                }
            }
            if (pointedEntity != null && (d < calcdist || raytraceResult == null)) {
                raytraceResult = new RayTraceResult(pointedEntity);
            }
        }
        return raytraceResult;
    }

}
