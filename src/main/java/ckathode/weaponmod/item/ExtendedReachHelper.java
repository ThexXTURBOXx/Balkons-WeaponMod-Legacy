package ckathode.weaponmod.item;

import ckathode.weaponmod.entity.projectile.EntityProjectile;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;

@SideOnly(Side.CLIENT)
public final class ExtendedReachHelper {
    private static final Minecraft mc = Minecraft.getMinecraft();

    public static MovingObjectPosition getMouseOver(float frame, float dist) {
        MovingObjectPosition result = null;
        EntityLivingBase entity = mc.renderViewEntity;
        if (entity != null && mc.theWorld != null) {
            double distNew = dist;
            result = entity.rayTrace(distNew, frame);
            double calcDist = distNew;
            Vec3 pos = entity.getPosition(frame);
            distNew = calcDist;

            if (result != null) {
                calcDist = result.hitVec.distanceTo(pos);
            }

            Vec3 lookVec = entity.getLook(1.0F);
            Vec3 traced = pos.addVector(lookVec.xCoord * distNew, lookVec.yCoord * distNew, lookVec.zCoord * distNew);
            Entity pointedEntity = null;
            float f = 1.0F;
            List<Entity> list = mc.theWorld.getEntitiesWithinAABBExcludingEntity(entity,
                    EntityProjectile.getBoundingBox(entity)
                            .addCoord(lookVec.xCoord * distNew, lookVec.yCoord * distNew, lookVec.zCoord * distNew)
                            .expand(f, f, f));
            double d = calcDist;

            Vec3 hitVec = null;
            for (Entity entity1 : list) {
                float bordersize = entity1.getCollisionBorderSize();
                AxisAlignedBB aabb = entity1.getBoundingBox().expand(bordersize, bordersize, bordersize);
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
