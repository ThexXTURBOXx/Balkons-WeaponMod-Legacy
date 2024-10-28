package ckathode.weaponmod.render;

import ckathode.weaponmod.WMRegistries;
import ckathode.weaponmod.WeaponModConfig;
import ckathode.weaponmod.WeaponModResources;
import ckathode.weaponmod.entity.projectile.EntityJavelin;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class RenderJavelin extends WMRenderer<EntityJavelin, RenderJavelin.JavelinRenderState> {

    public RenderJavelin(Context context) {
        super(context);
    }

    @Override
    public void render(JavelinRenderState entityRenderState, PoseStack ms, MultiBufferSource bufs, int lm) {
        if (!WeaponModConfig.get().itemModelForEntity) {
            VertexConsumer builder = bufs.getBuffer(RenderType.entityCutout(WeaponModResources.Entity.JAVELIN));
            ms.pushPose();
            ms.mulPose(Axis.YP.rotationDegrees(entityRenderState.yRot - 90.0f));
            ms.mulPose(Axis.ZP.rotationDegrees(entityRenderState.xRot));
            float length = 20.0f;
            float f11 = entityRenderState.shakeTime;
            if (f11 > 0.0f) {
                float f12 = -Mth.sin(f11 * 3.0f) * f11;
                ms.mulPose(Axis.ZP.rotationDegrees(f12));
            }
            ms.mulPose(Axis.XP.rotationDegrees(45.0f));
            ms.scale(0.05625f, 0.05625f, 0.05625f);
            ms.translate(-4.0f, 0.0f, 0.0f);
            PoseStack.Pose last = ms.last();
            drawVertex(last, builder, -length, -2.0f, -2.0f, 0.0f, 0.15625f, 0.05625f, 0.0f, 0.0f, lm);
            drawVertex(last, builder, -length, -2.0f, 2.0f, 0.15625f, 0.15625f, 0.05625f, 0.0f, 0.0f, lm);
            drawVertex(last, builder, -length, 2.0f, 2.0f, 0.15625f, 0.3125f, 0.05625f, 0.0f, 0.0f, lm);
            drawVertex(last, builder, -length, 2.0f, -2.0f, 0.0f, 0.3125f, 0.05625f, 0.0f, 0.0f, lm);
            drawVertex(last, builder, -length, 2.0f, -2.0f, 0.0f, 0.15625f, -0.05625f, 0.0f, 0.0f, lm);
            drawVertex(last, builder, -length, 2.0f, 2.0f, 0.15625f, 0.15625f, -0.05625f, 0.0f, 0.0f, lm);
            drawVertex(last, builder, -length, -2.0f, 2.0f, 0.15625f, 0.3125f, -0.05625f, 0.0f, 0.0f, lm);
            drawVertex(last, builder, -length, -2.0f, -2.0f, 0.0f, 0.3125f, -0.05625f, 0.0f, 0.0f, lm);
            for (int j = 0; j < 4; ++j) {
                ms.mulPose(Axis.XP.rotationDegrees(90.0f));
                last = ms.last();
                drawVertex(last, builder, -length, -2.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.05625f, lm);
                drawVertex(last, builder, length, -2.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.05625f, lm);
                drawVertex(last, builder, length, 2.0f, 0.0f, 1.0f, 0.15625f, 0.0f, 0.0f, 0.05625f, lm);
                drawVertex(last, builder, -length, 2.0f, 0.0f, 0.0f, 0.15625f, 0.0f, 0.0f, 0.05625f, lm);
            }
            ms.popPose();
        } else {
            ItemRenderer itemRender = Minecraft.getInstance().getItemRenderer();
            ms.pushPose();
            ms.scale(1.7f, 1.7f, 1.7f);
            ms.mulPose(Axis.YP.rotationDegrees(entityRenderState.yRot - 90.0f));
            ms.mulPose(Axis.ZP.rotationDegrees(entityRenderState.xRot - 45.0f));
            float f13 = entityRenderState.shakeTime;
            if (f13 > 0.0f) {
                float f14 = -Mth.sin(f13 * 3.0f) * f13;
                ms.mulPose(Axis.ZP.rotationDegrees(f14));
            }
            ms.translate(-0.25f, -0.25f, 0.0f);
            ms.mulPose(Axis.YP.rotationDegrees(180.0f));
            itemRender.renderStatic(new ItemStack(WMRegistries.ITEM_JAVELIN.get()), ItemDisplayContext.NONE, lm,
                    OverlayTexture.NO_OVERLAY, ms, bufs, Minecraft.getInstance().level, 0);
            ms.popPose();
        }
        super.render(entityRenderState, ms, bufs, lm);
    }

    @NotNull
    @Override
    public JavelinRenderState createRenderState() {
        return new JavelinRenderState();
    }

    @Override
    public void extractRenderState(EntityJavelin entity, JavelinRenderState entityRenderState, float f) {
        super.extractRenderState(entity, entityRenderState, f);
        entityRenderState.shakeTime = entity.shakeTime - f;
    }

    public static class JavelinRenderState extends WMRendererState {
        public float shakeTime;
    }

}
