package ckathode.weaponmod.render;

import ckathode.weaponmod.WeaponModResources;
import ckathode.weaponmod.entity.projectile.EntityMortarShell;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import org.jetbrains.annotations.NotNull;

public class RenderMortarShell extends WMRenderer<EntityMortarShell, RenderMortarShell.MortarShellRenderState> {

    public RenderMortarShell(Context context) {
        super(context);
        shadowRadius = 0.3f;
    }

    @Override
    public void render(MortarShellRenderState entityRenderState, PoseStack ms, MultiBufferSource bufs, int lm) {
        ms.pushPose();
        VertexConsumer builder = bufs.getBuffer(RenderType.entityCutout(WeaponModResources.Entity.CANNONBALL));
        ms.mulPose(Axis.YP.rotationDegrees(180.0f - entityRenderState.yRot));
        ms.scale(-1.0f, -1.0f, 1.0f);
        ms.scale(0.2f, 0.2f, 0.2f);
        ms.mulPose(Axis.XP.rotationDegrees(180.0f));
        PoseStack.Pose last = ms.last();
        drawVertex(last, builder, -0.5f, 0.5f, -0.5f, 0.0f, 1.0f, 0.0f, 0.0f, 0.05625f, lm);
        drawVertex(last, builder, 0.5f, 0.5f, -0.5f, 1.0f, 1.0f, 0.0f, 0.0f, 0.05625f, lm);
        drawVertex(last, builder, 0.5f, -0.5f, -0.5f, 1.0f, 0.0f, 0.0f, 0.0f, 0.05625f, lm);
        drawVertex(last, builder, -0.5f, -0.5f, -0.5f, 0.0f, 0.0f, 0.0f, 0.0f, 0.05625f, lm);
        drawVertex(last, builder, -0.5f, -0.5f, 0.5f, 0.0f, 0.0f, 0.0f, 0.0f, 0.05625f, lm);
        drawVertex(last, builder, 0.5f, -0.5f, 0.5f, 1.0f, 0.0f, 0.0f, 0.0f, 0.05625f, lm);
        drawVertex(last, builder, 0.5f, 0.5f, 0.5f, 1.0f, 1.0f, 0.0f, 0.0f, 0.05625f, lm);
        drawVertex(last, builder, -0.5f, 0.5f, 0.5f, 0.0f, 1.0f, 0.0f, 0.0f, 0.05625f, lm);
        drawVertex(last, builder, -0.5f, -0.5f, -0.5f, 0.0f, 0.0f, 0.0f, 0.0f, 0.05625f, lm);
        drawVertex(last, builder, 0.5f, -0.5f, -0.5f, 1.0f, 0.0f, 0.0f, 0.0f, 0.05625f, lm);
        drawVertex(last, builder, 0.5f, -0.5f, 0.5f, 1.0f, 1.0f, 0.0f, 0.0f, 0.05625f, lm);
        drawVertex(last, builder, -0.5f, -0.5f, 0.5f, 0.0f, 1.0f, 0.0f, 0.0f, 0.05625f, lm);
        drawVertex(last, builder, -0.5f, 0.5f, 0.5f, 0.0f, 1.0f, 0.0f, 0.0f, 0.05625f, lm);
        drawVertex(last, builder, 0.5f, 0.5f, 0.5f, 1.0f, 1.0f, 0.0f, 0.0f, 0.05625f, lm);
        drawVertex(last, builder, 0.5f, 0.5f, -0.5f, 1.0f, 0.0f, 0.0f, 0.0f, 0.05625f, lm);
        drawVertex(last, builder, -0.5f, 0.5f, -0.5f, 0.0f, 0.0f, 0.0f, 0.0f, 0.05625f, lm);
        drawVertex(last, builder, -0.5f, -0.5f, 0.5f, 0.0f, 0.0f, 0.0f, 0.0f, 0.05625f, lm);
        drawVertex(last, builder, -0.5f, 0.5f, 0.5f, 1.0f, 0.0f, 0.0f, 0.0f, 0.05625f, lm);
        drawVertex(last, builder, -0.5f, 0.5f, -0.5f, 1.0f, 1.0f, 0.0f, 0.0f, 0.05625f, lm);
        drawVertex(last, builder, -0.5f, -0.5f, -0.5f, 0.0f, 1.0f, 0.0f, 0.0f, 0.05625f, lm);
        drawVertex(last, builder, 0.5f, -0.5f, -0.5f, 0.0f, 0.0f, 0.0f, 0.0f, 0.05625f, lm);
        drawVertex(last, builder, 0.5f, 0.5f, -0.5f, 1.0f, 0.0f, 0.0f, 0.0f, 0.05625f, lm);
        drawVertex(last, builder, 0.5f, 0.5f, 0.5f, 1.0f, 1.0f, 0.0f, 0.0f, 0.05625f, lm);
        drawVertex(last, builder, 0.5f, -0.5f, 0.5f, 0.0f, 1.0f, 0.0f, 0.0f, 0.05625f, lm);
        ms.popPose();
        super.render(entityRenderState, ms, bufs, lm);
    }

    @NotNull
    @Override
    public MortarShellRenderState createRenderState() {
        return new MortarShellRenderState();
    }

    public static class MortarShellRenderState extends WMRendererState {
    }

}
