package ckathode.weaponmod.item;

import com.google.common.base.Predicates;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public final class ExtendedReachHelper {
    private static final Minecraft mc = Minecraft.getMinecraft();

    public static RayTraceResult getMouseOver(float frame, float dist) {
        RayTraceResult result = null;
        Entity entity = mc.getRenderViewEntity();
        if (entity != null && mc.theWorld != null) {
            double distNew = dist;
            result = entity.rayTrace(distNew, frame);
            double calcDist = distNew;
            Vec3d pos = entity.getPositionEyes(frame);
            distNew = calcDist;

            if (result != null) {
                calcDist = result.hitVec.distanceTo(pos);
            }

            Vec3d lookVec = entity.getLook(1.0F);
            Vec3d traced = pos.addVector(lookVec.xCoord * distNew, lookVec.yCoord * distNew, lookVec.zCoord * distNew);
            Entity pointedEntity = null;
            float f = 1.0F;
            List<Entity> list = mc.theWorld.getEntitiesInAABBexcluding(entity,
                    entity.getEntityBoundingBox()
                            .expand(lookVec.xCoord * distNew, lookVec.yCoord * distNew, lookVec.zCoord * distNew)
                            .expand(f, f, f),
                    Predicates.and(EntitySelectors.NOT_SPECTATING, e -> e != null && e.canBeCollidedWith()));
            double d = calcDist;

            Vec3d hitVec = null;
            for (Entity entity1 : list) {
                AxisAlignedBB aabb = entity1.getEntityBoundingBox().expandXyz(entity1.getCollisionBorderSize());
                RayTraceResult intercept = aabb.calculateIntercept(pos, traced);
                if (aabb.isVecInside(pos)) {
                    if (d >= 0.0D) {
                        pointedEntity = entity1;
                        hitVec = intercept == null ? pos : intercept.hitVec;
                        d = 0.0D;
                    }
                } else if (intercept != null) {
                    double d1 = pos.distanceTo(intercept.hitVec);
                    if (d1 < d || d == 0.0D) {
                        if (entity1.getLowestRidingEntity() == entity.getLowestRidingEntity() && !entity1.canRiderInteract()) {
                            if (d == 0.0D) {
                                pointedEntity = entity1;
                                hitVec = intercept.hitVec;
                            }
                        } else {
                            pointedEntity = entity1;
                            hitVec = intercept.hitVec;
                            d = d1;
                        }
                    }
                }
            }

            if (pointedEntity != null && (d < calcDist || result == null)) {
                result = new RayTraceResult(pointedEntity, hitVec);
            }
        }
        return result;
    }

}
