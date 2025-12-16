package ckathode.weaponmod.render;

import ckathode.weaponmod.WeaponModResources;
import ckathode.weaponmod.entity.projectile.EntityFlail;
import ckathode.weaponmod.item.ItemFlail;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class RenderFlail extends WMRenderer<EntityFlail, RenderFlail.FlailRenderState> {

    public RenderFlail(Context context) {
        super(context);
    }

    @Override
    public void submit(FlailRenderState entityRenderState, PoseStack poseStack,
                       SubmitNodeCollector submitNodeCollector, CameraRenderState cameraRenderState) {
        int lm = entityRenderState.lightCoords;
        Entity shooterEntity = entityRenderState.owner;
        if (shooterEntity instanceof LivingEntity shooter) {
            poseStack.pushPose();
            poseStack.pushPose();
            poseStack.mulPose(Axis.YP.rotationDegrees(entityRenderState.yRot - 90.0f));
            poseStack.mulPose(Axis.ZP.rotationDegrees(entityRenderState.xRot));
            float[] color = entityRenderState.materialColor;
            float f11 = -entityRenderState.partialTicks;
            if (f11 > 0.0f) {
                float f12 = -Mth.sin(f11 * 3.0f) * f11;
                poseStack.mulPose(Axis.ZP.rotationDegrees(f12));
            }
            poseStack.mulPose(Axis.XP.rotationDegrees(45.0f));
            poseStack.scale(0.15f, 0.15f, 0.15f);
            poseStack.translate(-4.0f, 0.0f, 0.0f);
            submitNodeCollector.submitCustomGeometry(poseStack,
                    RenderTypes.entityCutout(WeaponModResources.Entity.FLAIL),
                    (pose, consumer) -> {
                        drawVertex(pose, consumer, 1.5f, -2.0f, -2.0f, color[0], color[1], color[2], 1, 0.0f,
                                0.15625f, 0.15f, 0.0f, 0.0f, lm);
                        drawVertex(pose, consumer, 1.5f, -2.0f, 2.0f, color[0], color[1], color[2], 1, 0.15625f,
                                0.15625f, 0.15f, 0.0f, 0.0f, lm);
                        drawVertex(pose, consumer, 1.5f, 2.0f, 2.0f, color[0], color[1], color[2], 1, 0.15625f,
                                0.3125f, 0.15f, 0.0f, 0.0f, lm);
                        drawVertex(pose, consumer, 1.5f, 2.0f, -2.0f, color[0], color[1], color[2], 1, 0.0f, 0.3125f,
                                0.15f, 0.0f, 0.0f, lm);
                        drawVertex(pose, consumer, 1.5f, 2.0f, -2.0f, color[0], color[1], color[2], 1, 0.0f, 0.15625f,
                                -0.15f, 0.0f, 0.0f, lm);
                        drawVertex(pose, consumer, 1.5f, 2.0f, 2.0f, color[0], color[1], color[2], 1, 0.15625f,
                                0.15625f, -0.15f, 0.0f, 0.0f, lm);
                        drawVertex(pose, consumer, 1.5f, -2.0f, 2.0f, color[0], color[1], color[2], 1, 0.15625f,
                                0.3125f, -0.15f, 0.0f, 0.0f, lm);
                        drawVertex(pose, consumer, 1.5f, -2.0f, -2.0f, color[0], color[1], color[2], 1, 0.0f, 0.3125f,
                                -0.15f, 0.0f, 0.0f, lm);
                    });
            for (int j = 0; j < 4; ++j) {
                poseStack.mulPose(Axis.XP.rotationDegrees(90.0f));
                submitNodeCollector.submitCustomGeometry(poseStack,
                        RenderTypes.entityCutout(WeaponModResources.Entity.FLAIL),
                        (pose, consumer) -> {
                            drawVertex(pose, consumer, -8.0f, -2.0f, 0.0f, color[0], color[1], color[2], 1, 0.0f,
                                    0.0f, 0.0f, 0.0f, 0.15f, lm);
                            drawVertex(pose, consumer, 8.0f, -2.0f, 0.0f, color[0], color[1], color[2], 1, 0.5f, 0.0f,
                                    0.0f, 0.0f, 0.15f, lm);
                            drawVertex(pose, consumer, 8.0f, 2.0f, 0.0f, color[0], color[1], color[2], 1, 0.5f,
                                    0.15625f, 0.0f, 0.0f, 0.15f, lm);
                            drawVertex(pose, consumer, -8.0f, 2.0f, 0.0f, color[0], color[1], color[2], 1, 0.0f,
                                    0.15625f, 0.0f, 0.0f, 0.15f, lm);
                        });
            }
            poseStack.popPose();

            float f = (float) entityRenderState.lineOriginOffset.x;
            float g = (float) entityRenderState.lineOriginOffset.y;
            float h = (float) entityRenderState.lineOriginOffset.z;
            float i = Minecraft.getInstance().getWindow().getAppropriateLineWidth();
            submitNodeCollector.submitCustomGeometry(poseStack, RenderTypes.lines(), (pose, consumer) -> {
                int v = 16;
                for (int k = 0; k < v; ++k) {
                    float l = fraction(k, v);
                    float m = fraction(k + 1, v);
                    stringVertex(f, g, h, consumer, pose, l, m, i);
                    stringVertex(f, g, h, consumer, pose, m, l, i);
                }
            });

            poseStack.popPose();
            super.submit(entityRenderState, poseStack, submitNodeCollector, cameraRenderState);
        }
    }

    public static HumanoidArm getHoldingArm(LivingEntity le) {
        return le.getMainHandItem().getItem() instanceof ItemFlail
                ? le.getMainArm()
                : le.getMainArm().getOpposite();
    }

    private Vec3 getHandPos(LivingEntity le, float f, float g) {
        int i = getHoldingArm(le) == HumanoidArm.RIGHT ? 1 : -1;
        if (this.entityRenderDispatcher.options.getCameraType().isFirstPerson() && le == Minecraft.getInstance().player) {
            double n = 960.0 / this.entityRenderDispatcher.options.fov().get();
            Vec3 vec3 = this.entityRenderDispatcher.camera.getNearPlane()
                    .getPointOnPlane(i * 0.525F, -0.1F).scale(n).yRot(f * 0.5F).xRot(-f * 0.7F);
            return le.getEyePosition(g).add(vec3);
        } else {
            float h = Mth.lerp(g, le.yBodyRotO, le.yBodyRot) * (float) (Math.PI / 180.0);
            double d = Mth.sin(h);
            double e = Mth.cos(h);
            float j = le.getScale();
            double k = i * 0.35 * j;
            double l = 0.8 * j;
            float m = le.isCrouching() ? -0.1875F : 0.0F;
            return le.getEyePosition(g).add(-e * k - d * l, m - 0.45 * j, -d * k + e * l);
        }
    }

    private static float fraction(int part, int total) {
        return (float) part / total;
    }

    private static void stringVertex(float f, float g, float h, VertexConsumer vertexConsumer, PoseStack.Pose pose,
                                     float i, float j, float k) {
        float l = f * i;
        float m = g * (i * i + i) * 0.5F + 0.25F;
        float n = h * i;
        float o = f * j - l;
        float p = g * (j * j + j) * 0.5F + 0.25F - m;
        float q = h * j - n;
        float r = Mth.sqrt(o * o + p * p + q * q);
        o /= r;
        p /= r;
        q /= r;
        vertexConsumer.addVertex(pose, l, m, n).setColor(0, 0, 0, 255)
                .setNormal(pose, o, p, q).setLineWidth(k);
    }

    @NotNull
    @Override
    public FlailRenderState createRenderState() {
        return new FlailRenderState();
    }

    @Override
    public void extractRenderState(EntityFlail entity, FlailRenderState entityRenderState, float f) {
        super.extractRenderState(entity, entityRenderState, f);
        entityRenderState.materialColor = entity.getMaterialColor();
        entityRenderState.owner = entity.getOwner();

        if (entityRenderState.owner instanceof LivingEntity le) {
            float g = le.getAttackAnim(f);
            float h = Mth.sin(Mth.sqrt(g) * (float) Math.PI);
            Vec3 vec3 = this.getHandPos(le, h, f);
            Vec3 vec32 = entity.getPosition(f).add(0.0, 0.25, 0.0);
            entityRenderState.lineOriginOffset = vec3.subtract(vec32);
        } else {
            entityRenderState.lineOriginOffset = Vec3.ZERO;
        }
    }

    public static class FlailRenderState extends WMRendererState {
        public float[] materialColor;
        public Entity owner;
        public Vec3 lineOriginOffset;
    }

}
