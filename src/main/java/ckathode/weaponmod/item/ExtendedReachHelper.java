package ckathode.weaponmod.item;

import com.google.common.base.Predicates;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public final class ExtendedReachHelper {
    private static final Minecraft mc = Minecraft.getMinecraft();

    public static MovingObjectPosition getMouseOver(float frame, float dist) {
        MovingObjectPosition result = null;
        Entity entity = mc.getRenderViewEntity();
        if (entity != null && mc.theWorld != null) {
            double distNew = dist;
            result = entity.rayTrace(distNew, frame);
            double calcDist = distNew;
            Vec3 pos = entity.getPositionEyes(frame);
            distNew = calcDist;

            if (result != null) {
                calcDist = result.hitVec.distanceTo(pos);
            }

            Vec3 lookVec = entity.getLook(1.0F);
            Vec3 traced = pos.addVector(lookVec.xCoord * distNew, lookVec.yCoord * distNew, lookVec.zCoord * distNew);
            Entity pointedEntity = null;
            float f = 1.0F;
            List<Entity> list = mc.theWorld.getEntitiesInAABBexcluding(entity,
                    entity.getEntityBoundingBox()
                            .expand(lookVec.xCoord * distNew, lookVec.yCoord * distNew, lookVec.zCoord * distNew)
                            .expand(f, f, f),
                    Predicates.and(EntitySelectors.NOT_SPECTATING, e -> e != null && e.canBeCollidedWith()));
            double d = calcDist;

            Vec3 hitVec = null;
            for (Entity entity1 : list) {
                float borderSize = entity1.getCollisionBorderSize();
                AxisAlignedBB aabb = entity1.getEntityBoundingBox().expand(borderSize, borderSize, borderSize);
                MovingObjectPosition intercept = aabb.calculateIntercept(pos, traced);
                if (aabb.isVecInside(pos)) {
                    if (d >= 0.0D) {
                        pointedEntity = entity1;
                        hitVec = intercept == null ? pos : intercept.hitVec;
                        d = 0.0D;
                    }
                } else if (intercept != null) {
                    double d1 = pos.distanceTo(intercept.hitVec);
                    if (d1 < d || d == 0.0D) {
                        pointedEntity = entity;
                        hitVec = intercept.hitVec;
                        d = d1;
                    }
                }
            }

            if (pointedEntity != null && (d < calcDist || result == null)) {
                result = new MovingObjectPosition(pointedEntity, hitVec);
            }
        }
        return result;
    }

}
