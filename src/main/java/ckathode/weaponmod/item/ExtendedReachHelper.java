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
public abstract class ExtendedReachHelper {
    private static Minecraft mc;

    public static RayTraceResult getMouseOver(final float frame, final float dist) {
        RayTraceResult raytraceResult = null;
        if (ExtendedReachHelper.mc.getRenderViewEntity() != null && ExtendedReachHelper.mc.world != null) {
            double var2 = dist;
            raytraceResult = ExtendedReachHelper.mc.getRenderViewEntity().rayTrace(var2, frame,
                    RayTraceFluidMode.NEVER);
            double calcdist = var2;
            final Vec3d pos = ExtendedReachHelper.mc.getRenderViewEntity().getEyePosition(frame);
            var2 = calcdist;
            if (raytraceResult != null) {
                calcdist = raytraceResult.hitVec.distanceTo(pos);
            }
            final Vec3d lookvec = ExtendedReachHelper.mc.getRenderViewEntity().getLook(frame);
            final Vec3d var3 = pos.add(lookvec.x * var2, lookvec.y * var2, lookvec.z * var2);
            Entity pointedEntity = null;
            final float var4 = 1.0f;
            final List<Entity> list =
                    ExtendedReachHelper.mc.world.getEntitiesInAABBexcluding(ExtendedReachHelper.mc.getRenderViewEntity(),
                            ExtendedReachHelper.mc.getRenderViewEntity().getBoundingBox()
                                    .expand(lookvec.x * var2, lookvec.y * var2, lookvec.z * var2)
                                    .grow(var4, var4, var4),
                            EntitySelectors.NOT_SPECTATING.and(entity -> entity != null && entity.canBeCollidedWith()));
            double d = calcdist;
            for (final Entity entity : list) {
                final AxisAlignedBB aabb = entity.getBoundingBox().grow(entity.getCollisionBorderSize());
                final RayTraceResult raytraceResult2 = aabb.calculateIntercept(pos, var3);
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
                    final double d2 = pos.distanceTo(raytraceResult2.hitVec);
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

    static {
        ExtendedReachHelper.mc = Minecraft.getInstance();
    }
}
