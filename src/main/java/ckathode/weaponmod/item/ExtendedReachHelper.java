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
        RayTraceResult result = null;
        Entity entity = mc.getRenderViewEntity();
        if (entity != null && mc.world != null) {
            double distNew = dist;
            result = entity.rayTrace(distNew, frame, RayTraceFluidMode.NEVER);
            double calcDist = distNew;
            Vec3d pos = entity.getEyePosition(frame);
            distNew = calcDist;

            if (result != null) {
                calcDist = result.hitVec.distanceTo(pos);
            }

            Vec3d lookVec = entity.getLook(1.0F);
            Vec3d traced = pos.add(lookVec.x * distNew, lookVec.y * distNew, lookVec.z * distNew);
            Entity pointedEntity = null;
            float f = 1.0F;
            List<Entity> list = mc.world.getEntitiesInAABBexcluding(entity,
                    entity.getBoundingBox()
                            .expand(lookVec.x * distNew, lookVec.y * distNew, lookVec.z * distNew)
                            .grow(f, f, f),
                    EntitySelectors.NOT_SPECTATING.and(Entity::canBeCollidedWith));
            double d = calcDist;

            Vec3d hitVec = null;
            for (Entity entity1 : list) {
                AxisAlignedBB aabb = entity1.getBoundingBox().grow(entity1.getCollisionBorderSize());
                RayTraceResult intercept = aabb.calculateIntercept(pos, traced);
                if (aabb.contains(pos)) {
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
