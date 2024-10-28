package ckathode.weaponmod.render;

import ckathode.weaponmod.WeaponModResources;
import ckathode.weaponmod.entity.projectile.EntityBlunderShot;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import org.jetbrains.annotations.NotNull;

public class RenderBlunderShot extends WMRenderer<EntityBlunderShot, RenderBlunderShot.BlunderShotRenderState> {

    public RenderBlunderShot(Context context) {
        super(context);
    }

    @Override
    public void render(BlunderShotRenderState entityRenderState, PoseStack ms, MultiBufferSource bufs, int lm) {
        ms.pushPose();
        VertexConsumer builder = bufs.getBuffer(RenderType.entityCutout(WeaponModResources.Entity.BULLET));
        ms.scale(0.04f, 0.04f, 0.04f);
        PoseStack.Pose last = ms.last();
        drawVertex(last, builder, 0.0f, -1.0f, -1.0f, 0.0f, 0.0f, 0.05625f, 0.0f, 0.0f, lm);
        drawVertex(last, builder, 0.0f, -1.0f, 1.0f, 0.3125f, 0.0f, 0.05625f, 0.0f, 0.0f, lm);
        drawVertex(last, builder, 0.0f, 1.0f, 1.0f, 0.3125f, 0.3125f, 0.05625f, 0.0f, 0.0f, lm);
        drawVertex(last, builder, 0.0f, 1.0f, -1.0f, 0.0f, 0.3125f, 0.05625f, 0.0f, 0.0f, lm);
        drawVertex(last, builder, 0.0f, 1.0f, -1.0f, 0.0f, 0.0f, -0.05625f, 0.0f, 0.0f, lm);
        drawVertex(last, builder, 0.0f, 1.0f, 1.0f, 0.3125f, 0.0f, -0.05625f, 0.0f, 0.0f, lm);
        drawVertex(last, builder, 0.0f, -1.0f, 1.0f, 0.3125f, 0.3125f, -0.05625f, 0.0f, 0.0f, lm);
        drawVertex(last, builder, 0.0f, -1.0f, -1.0f, 0.0f, 0.3125f, -0.05625f, 0.0f, 0.0f, lm);
        for (int j = 0; j < 4; ++j) {
            ms.mulPose(Axis.XP.rotationDegrees(90.0f));
            last = ms.last();
            drawVertex(last, builder, -1.0f, -1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.05625f, lm);
            drawVertex(last, builder, 1.0f, -1.0f, 0.0f, 0.3125f, 0.0f, 0.0f, 0.0f, 0.05625f, lm);
            drawVertex(last, builder, 1.0f, 1.0f, 0.0f, 0.3125f, 0.3125f, 0.0f, 0.0f, 0.05625f, lm);
            drawVertex(last, builder, -1.0f, 1.0f, 0.0f, 0.0f, 0.3125f, 0.0f, 0.0f, 0.05625f, lm);
        }
        ms.popPose();
        /*ms.pushPose();
        GlStateManager._disableCull();
        GL11.glLineWidth(1.0f);
        Matrix4f mat = ms.last().pose();
        builder = bufs.getBuffer(RenderType.lines());
        builder.addVertex(mat, (float) entityblundershot.getX(), (float) entityblundershot.getY(),
                        (float) entityblundershot.getZ())
                .setColor(1.0f, 1.0f, 0.8f, 1.0f)
                .setNormal(0.0f, 0.0f, 0.05625f);
        builder.addVertex(mat, (float) entityblundershot.xo, (float) entityblundershot.yo,
                        (float) entityblundershot.zo)
                .setColor(1.0f, 1.0f, 0.8f, 1.0f)
                .setNormal(0.0f, 0.0f, 0.05625f);
        GlStateManager._enableCull();
        ms.popPose();*/
        super.render(entityRenderState, ms, bufs, lm);
    }

    @NotNull
    @Override
    public BlunderShotRenderState createRenderState() {
        return new BlunderShotRenderState();
    }

    public static class BlunderShotRenderState extends WMRendererState {
    }

}
