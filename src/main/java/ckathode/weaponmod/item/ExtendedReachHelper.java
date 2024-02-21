package ckathode.weaponmod.item;

import net.minecraftforge.fml.relauncher.*;
import net.minecraft.client.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;
import javax.annotation.*;
import com.google.common.base.*;
import java.util.*;
import net.minecraft.util.math.*;
import net.minecraftforge.fml.client.*;

@SideOnly(Side.CLIENT)
public abstract class ExtendedReachHelper
{
    private static Minecraft mc;

    public static RayTraceResult getMouseOver(final float frame, final float dist) {
        RayTraceResult raytraceResult = null;
        if (ExtendedReachHelper.mc.getRenderViewEntity() != null && ExtendedReachHelper.mc.world != null) {
            double var2 = dist;
            raytraceResult = ExtendedReachHelper.mc.getRenderViewEntity().rayTrace(var2, frame);
            double calcdist = var2;
            final Vec3d pos = ExtendedReachHelper.mc.getRenderViewEntity().getPositionEyes(frame);
            var2 = calcdist;
            if (raytraceResult != null) {
                calcdist = raytraceResult.hitVec.distanceTo(pos);
            }
            final Vec3d lookvec = ExtendedReachHelper.mc.getRenderViewEntity().getLook(frame);
            final Vec3d var3 = pos.add(lookvec.x * var2, lookvec.y * var2, lookvec.z * var2);
            Entity pointedEntity = null;
            final float var4 = 1.0f;
            final List<Entity> list = ExtendedReachHelper.mc.world.getEntitiesInAABBexcluding(ExtendedReachHelper.mc.getRenderViewEntity(), ExtendedReachHelper.mc.getRenderViewEntity().getEntityBoundingBox().expand(lookvec.x * var2, lookvec.y * var2, lookvec.z * var2).grow(var4, var4, var4), Predicates.and(EntitySelectors.NOT_SPECTATING, entity -> entity != null && entity.canBeCollidedWith()));
            double d = calcdist;
            for (final Entity entity : list) {
                final AxisAlignedBB aabb = entity.getEntityBoundingBox().grow(entity.getCollisionBorderSize());
                final RayTraceResult raytraceResult2 = aabb.calculateIntercept(pos, var3);
                if (aabb.contains(pos)) {
                    if (d < 0.0) {
                        continue;
                    }
                    pointedEntity = entity;
                    d = 0.0;
                }
                else {
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
        ExtendedReachHelper.mc = FMLClientHandler.instance().getClient();
    }
}
