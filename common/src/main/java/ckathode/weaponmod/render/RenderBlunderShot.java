package ckathode.weaponmod.render;

import ckathode.weaponmod.WeaponModResources;
import ckathode.weaponmod.entity.projectile.EntityBlunderShot;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class RenderBlunderShot extends WMRenderer<EntityBlunderShot> {

    public RenderBlunderShot(Context context) {
        super(context);
    }

    @Override
    public void render(@NotNull EntityBlunderShot entityblundershot, float f, float f1,
                       @NotNull PoseStack ms, @NotNull MultiBufferSource bufs, int lm) {
        ms.pushPose();
        VertexConsumer builder = bufs.getBuffer(RenderType.entityCutout(getTextureLocation(entityblundershot)));
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
            ms.mulPose(Vector3f.XP.rotationDegrees(90.0f));
            last = ms.last();
            drawVertex(last, builder, -1.0f, -1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.05625f, lm);
            drawVertex(last, builder, 1.0f, -1.0f, 0.0f, 0.3125f, 0.0f, 0.0f, 0.0f, 0.05625f, lm);
            drawVertex(last, builder, 1.0f, 1.0f, 0.0f, 0.3125f, 0.3125f, 0.0f, 0.0f, 0.05625f, lm);
            drawVertex(last, builder, -1.0f, 1.0f, 0.0f, 0.0f, 0.3125f, 0.0f, 0.0f, 0.05625f, lm);
        }
        ms.popPose();
        /*ms.pushPose();
        GlStateManager._disableTexture();
        GlStateManager._disableCull();
        GL11.glLineWidth(1.0f);
        Matrix4f mat = ms.last().pose();
        builder = bufs.getBuffer(RenderType.lines());
        builder.vertex(mat, (float) entityblundershot.getX(), (float) entityblundershot.getY(),
                        (float) entityblundershot.getZ())
                .color(1.0f, 1.0f, 0.8f, 1.0f)
                .normal(0.0f, 0.0f, 0.05625f)
                .endVertex();
        builder.vertex(mat, (float) entityblundershot.xo, (float) entityblundershot.yo,
                        (float) entityblundershot.zo)
                .color(1.0f, 1.0f, 0.8f, 1.0f)
                .normal(0.0f, 0.0f, 0.05625f)
                .endVertex();
        GlStateManager._enableTexture();
        GlStateManager._enableCull();
        ms.popPose();*/
        super.render(entityblundershot, f, f1, ms, bufs, lm);
    }

    @NotNull
    @Override
    public ResourceLocation getTextureLocation(@NotNull EntityBlunderShot entity) {
        return WeaponModResources.Entity.BULLET;
    }

}
