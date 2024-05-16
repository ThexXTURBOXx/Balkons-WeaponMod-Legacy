package ckathode.weaponmod.render;

import ckathode.weaponmod.WeaponModResources;
import ckathode.weaponmod.entity.projectile.EntityFlail;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class RenderFlail extends WMRenderer<EntityFlail> {

    public RenderFlail(Context context) {
        super(context);
    }

    @Override
    public void render(@NotNull EntityFlail entityflail, float p, float partialTicks,
                       @NotNull PoseStack ms, @NotNull MultiBufferSource bufs, int lm) {
        Entity shooterEntity = entityflail.getOwner();
        if (shooterEntity instanceof Player shooter) {
            ms.pushPose();
            ms.pushPose();
            ms.mulPose(Axis.YP.rotationDegrees(entityflail.yRotO + (entityflail.getYRot() - entityflail.yRotO) * partialTicks - 90.0f));
            ms.mulPose(Axis.ZP.rotationDegrees(entityflail.xRotO + (entityflail.getXRot() - entityflail.xRotO) * partialTicks));
            float[] color = entityflail.getMaterialColor();
            float f11 = -partialTicks;
            if (f11 > 0.0f) {
                float f12 = -Mth.sin(f11 * 3.0f) * f11;
                ms.mulPose(Axis.ZP.rotationDegrees(f12));
            }
            ms.mulPose(Axis.XP.rotationDegrees(45.0f));
            ms.scale(0.15f, 0.15f, 0.15f);
            ms.translate(-4.0f, 0.0f, 0.0f);
            PoseStack.Pose last = ms.last();
            VertexConsumer builder = bufs.getBuffer(RenderType.entityCutout(getTextureLocation(entityflail)));
            drawVertex(last, builder, 1.5f, -2.0f, -2.0f, color[0], color[1], color[2], 1, 0.0f, 0.15625f, 0.15f, 0.0f,
                    0.0f, lm);
            drawVertex(last, builder, 1.5f, -2.0f, 2.0f, color[0], color[1], color[2], 1, 0.15625f, 0.15625f, 0.15f,
                    0.0f, 0.0f, lm);
            drawVertex(last, builder, 1.5f, 2.0f, 2.0f, color[0], color[1], color[2], 1, 0.15625f, 0.3125f, 0.15f, 0.0f,
                    0.0f, lm);
            drawVertex(last, builder, 1.5f, 2.0f, -2.0f, color[0], color[1], color[2], 1, 0.0f, 0.3125f, 0.15f, 0.0f,
                    0.0f, lm);
            drawVertex(last, builder, 1.5f, 2.0f, -2.0f, color[0], color[1], color[2], 1, 0.0f, 0.15625f, -0.15f, 0.0f,
                    0.0f, lm);
            drawVertex(last, builder, 1.5f, 2.0f, 2.0f, color[0], color[1], color[2], 1, 0.15625f, 0.15625f, -0.15f,
                    0.0f, 0.0f, lm);
            drawVertex(last, builder, 1.5f, -2.0f, 2.0f, color[0], color[1], color[2], 1, 0.15625f, 0.3125f, -0.15f,
                    0.0f, 0.0f, lm);
            drawVertex(last, builder, 1.5f, -2.0f, -2.0f, color[0], color[1], color[2], 1, 0.0f, 0.3125f, -0.15f, 0.0f,
                    0.0f, lm);
            for (int j = 0; j < 4; ++j) {
                ms.mulPose(Axis.XP.rotationDegrees(90.0f));
                last = ms.last();
                drawVertex(last, builder, -8.0f, -2.0f, 0.0f, color[0], color[1], color[2], 1, 0.0f, 0.0f, 0.0f, 0.0f,
                        0.15f, lm);
                drawVertex(last, builder, 8.0f, -2.0f, 0.0f, color[0], color[1], color[2], 1, 0.5f, 0.0f, 0.0f, 0.0f,
                        0.15f, lm);
                drawVertex(last, builder, 8.0f, 2.0f, 0.0f, color[0], color[1], color[2], 1, 0.5f, 0.15625f, 0.0f, 0.0f,
                        0.15f, lm);
                drawVertex(last, builder, -8.0f, 2.0f, 0.0f, color[0], color[1], color[2], 1, 0.0f, 0.15625f, 0.0f,
                        0.0f, 0.15f, lm);
            }
            ms.popPose();
            int i = shooter.getMainArm() == HumanoidArm.RIGHT ? 1 : -1;

            float f = shooter.getAttackAnim(partialTicks);
            float f1 = Mth.sin(Mth.sqrt(f) * 3.1415927F);
            float f2 =
                    Mth.lerp(partialTicks, shooter.yBodyRotO, shooter.yBodyRot) * 0.017453292F;
            double d0 = Mth.sin(f2);
            double d1 = Mth.cos(f2);
            double d2 = (double) i * 0.35;
            double d3 = 0.8;
            double d4;
            double d5;
            double d6;
            float f3;
            double d9;
            if ((entityRenderDispatcher.options == null || entityRenderDispatcher.options.getCameraType().isFirstPerson()) && shooter == Minecraft.getInstance().player) {
                d9 = 960.0 / entityRenderDispatcher.options.fov().get();
                Vec3 vec3d = this.entityRenderDispatcher.camera.getNearPlane()
                        .getPointOnPlane((float) i * 0.525f, -0.1f);
                vec3d = vec3d.scale(d9);
                vec3d = vec3d.yRot(f1 * 0.5F);
                vec3d = vec3d.xRot(-f1 * 0.7F);
                d4 = Mth.lerp(partialTicks, shooter.xo, shooter.getX()) + vec3d.x;
                d5 = Mth.lerp(partialTicks, shooter.yo, shooter.getY()) + vec3d.y;
                d6 = Mth.lerp(partialTicks, shooter.zo, shooter.getZ()) + vec3d.z;
                f3 = shooter.getEyeHeight();
            } else {
                d4 = Mth.lerp(partialTicks, shooter.xo, shooter.getX()) - d1 * d2 - d0 * d3;
                d5 = shooter.yo + (double) shooter.getEyeHeight() + (shooter.getY() - shooter.yo) * (double) partialTicks - 0.45;
                d6 = Mth.lerp(partialTicks, shooter.zo, shooter.getZ()) - d0 * d2 + d1 * d3;
                f3 = shooter.isCrouching() ? -0.1875F : 0.0F;
            }

            d9 = Mth.lerp(partialTicks, entityflail.xo, entityflail.getX());
            double d10 = Mth.lerp(partialTicks, entityflail.yo, entityflail.getY()) + 0.25;
            double d8 = Mth.lerp(partialTicks, entityflail.zo, entityflail.getZ());
            float f4 = (float) (d4 - d9);
            float f5 = (float) (d5 - d10) + f3;
            float f6 = (float) (d6 - d8);
            builder = bufs.getBuffer(RenderType.lineStrip());
            last = ms.last();

            int v = 16;
            for (int k = 0; k <= v; ++k) {
                stringVertex(f4, f5, f6, builder, last, fraction(k, v), fraction(k + 1, v));
            }

            ms.popPose();
            super.render(entityflail, p, partialTicks, ms, bufs, lm);
        }
    }

    private static float fraction(int part, int total) {
        return (float) part / (float) total;
    }

    private static void stringVertex(float f, float g, float h, VertexConsumer builder, PoseStack.Pose pose, float i,
                                     float j) {
        float k = f * i;
        float l = g * (i * i + i) * 0.5f + 0.25f;
        float m = h * i;
        float n = f * j - k;
        float o = g * (j * j + j) * 0.5f + 0.25f - l;
        float p = h * j - m;
        float q = Mth.sqrt(n * n + o * o + p * p);
        builder.vertex(pose.pose(), k, l, m).color(0, 0, 0, 255).normal(pose.normal(), n /= q, o /= q, p /= q).endVertex();
    }

    @Override
    @NotNull
    public ResourceLocation getTextureLocation(@NotNull EntityFlail entity) {
        return WeaponModResources.Entity.FLAIL;
    }

}
