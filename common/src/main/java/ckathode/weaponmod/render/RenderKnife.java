package ckathode.weaponmod.render;

import ckathode.weaponmod.WeaponModConfig;
import ckathode.weaponmod.WeaponModResources;
import ckathode.weaponmod.entity.projectile.EntityKnife;
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

public class RenderKnife extends WMRenderer<EntityKnife, RenderKnife.KnifeRenderState> {

    public RenderKnife(Context context) {
        super(context);
    }

    @Override
    public void render(KnifeRenderState entityRenderState, PoseStack ms, MultiBufferSource bufs, int lm) {
        if (!WeaponModConfig.get().itemModelForEntity) {
            VertexConsumer builder = bufs.getBuffer(RenderType.entityCutout(WeaponModResources.Entity.KNIFE));
            ms.pushPose();
            ms.mulPose(Axis.YP.rotationDegrees(entityRenderState.yRot - 90.0f));
            ms.mulPose(Axis.ZP.rotationDegrees(entityRenderState.xRot));
            float[] color = entityRenderState.materialColor;
            float f13 = entityRenderState.shakeTime;
            if (f13 > 0.0f) {
                float f14 = -Mth.sin(f13 * 3.0f) * f13;
                ms.mulPose(Axis.ZP.rotationDegrees(f14));
            }
            ms.mulPose(Axis.XP.rotationDegrees(45.0f));
            ms.scale(0.05625f, 0.05625f, 0.05625f);
            ms.translate(-4.0f, 0.0f, 0.0f);
            PoseStack.Pose last = ms.last();
            drawVertex(last, builder, -7.0f, -2.0f, -2.0f, 0.0f, 0.15625f, 0.05625f, 0.0f, 0.0f, lm);
            drawVertex(last, builder, -7.0f, -2.0f, 2.0f, 0.15625f, 0.15625f, 0.05625f, 0.0f, 0.0f, lm);
            drawVertex(last, builder, -7.0f, 2.0f, 2.0f, 0.15625f, 0.3125f, 0.05625f, 0.0f, 0.0f, lm);
            drawVertex(last, builder, -7.0f, 2.0f, -2.0f, 0.0f, 0.3125f, 0.05625f, 0.0f, 0.0f, lm);
            drawVertex(last, builder, -7.0f, 2.0f, -2.0f, 0.0f, 0.15625f, -0.05625f, 0.0f, 0.0f, lm);
            drawVertex(last, builder, -7.0f, 2.0f, 2.0f, 0.15625f, 0.15625f, -0.05625f, 0.0f, 0.0f, lm);
            drawVertex(last, builder, -7.0f, -2.0f, 2.0f, 0.15625f, 0.3125f, -0.05625f, 0.0f, 0.0f, lm);
            drawVertex(last, builder, -7.0f, -2.0f, -2.0f, 0.0f, 0.3125f, -0.05625f, 0.0f, 0.0f, lm);
            for (int j = 0; j < 4; ++j) {
                ms.mulPose(Axis.XP.rotationDegrees(90.0f));
                last = ms.last();
                drawVertex(last, builder, -8.0f, -2.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.05625f, lm);
                drawVertex(last, builder, 8.0f, -2.0f, 0.0f, 0.5f, 0.0f, 0.0f, 0.0f, 0.05625f, lm);
                drawVertex(last, builder, 8.0f, 2.0f, 0.0f, 0.5f, 0.15625f, 0.0f, 0.0f, 0.05625f, lm);
                drawVertex(last, builder, -8.0f, 2.0f, 0.0f, 0.0f, 0.15625f, 0.0f, 0.0f, 0.05625f, lm);
                drawVertex(last, builder, -8.0f, -2.0f, 0.0f, color[0], color[1], color[2], 1.0f, 0.0f, 0.3125f, 0.0f
                        , 0.0f, 0.05625f, lm);
                drawVertex(last, builder, 8.0f, -2.0f, 0.0f, color[0], color[1], color[2], 1.0f, 0.5f, 0.3125f, 0.0f,
                        0.0f, 0.05625f, lm);
                drawVertex(last, builder, 8.0f, 2.0f, 0.0f, color[0], color[1], color[2], 1.0f, 0.5f, 0.46875f, 0.0f,
                        0.0f, 0.05625f, lm);
                drawVertex(last, builder, -8.0f, 2.0f, 0.0f, color[0], color[1], color[2], 1.0f, 0.0f, 0.46875f, 0.0f
                        , 0.0f, 0.05625f, lm);
            }
            ms.popPose();
        } else {
            ItemRenderer itemRender = Minecraft.getInstance().getItemRenderer();
            ms.pushPose();
            ms.scale(0.85f, 0.85f, 0.85f);
            ms.mulPose(Axis.YP.rotationDegrees(entityRenderState.yRot - 90.0f));
            ms.mulPose(Axis.ZP.rotationDegrees(entityRenderState.xRot - 45.0f));
            float f15 = entityRenderState.shakeTime;
            if (f15 > 0.0f) {
                float f16 = -Mth.sin(f15 * 3.0f) * f15;
                ms.mulPose(Axis.ZP.rotationDegrees(f16));
            }
            ms.translate(-0.15f, -0.15f, 0.0f);
            itemRender.renderStatic(entityRenderState.weapon, ItemDisplayContext.NONE, lm,
                    OverlayTexture.NO_OVERLAY, ms, bufs, Minecraft.getInstance().level, 0);
            ms.popPose();
        }
        super.render(entityRenderState, ms, bufs, lm);
    }

    @NotNull
    @Override
    public KnifeRenderState createRenderState() {
        return new KnifeRenderState();
    }

    @Override
    public void extractRenderState(EntityKnife entity, KnifeRenderState entityRenderState, float f) {
        super.extractRenderState(entity, entityRenderState, f);
        entityRenderState.shakeTime = entity.shakeTime - f;
        entityRenderState.weaponMaterialId = entity.getWeaponMaterialId();
        entityRenderState.materialColor = entity.getMaterialColor();
        entityRenderState.weapon = entity.getWeapon();
    }

    public static class KnifeRenderState extends WMRendererState {
        public float shakeTime;
        public int weaponMaterialId;
        public float[] materialColor;
        public ItemStack weapon;
    }

}
