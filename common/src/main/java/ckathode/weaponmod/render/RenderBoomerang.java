package ckathode.weaponmod.render;

import ckathode.weaponmod.WeaponModConfig;
import ckathode.weaponmod.WeaponModResources;
import ckathode.weaponmod.entity.projectile.EntityBoomerang;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class RenderBoomerang extends WMRenderer<EntityBoomerang, RenderBoomerang.BoomerangRenderState> {

    public RenderBoomerang(Context context) {
        super(context);
    }

    @Override
    public void render(BoomerangRenderState entityRenderState, PoseStack ms, MultiBufferSource bufs, int lm) {
        if (!WeaponModConfig.get().itemModelForEntity) {
            VertexConsumer builder = bufs.getBuffer(RenderType.entityCutout(WeaponModResources.Entity.BOOMERANG));
            ms.pushPose();
            ms.mulPose(Axis.ZP.rotationDegrees(entityRenderState.xRot));
            ms.mulPose(Axis.YP.rotationDegrees(entityRenderState.yRot - 90.0f));
            int material = entityRenderState.weaponMaterialId;
            float[] color = entityRenderState.materialColor;
            ms.translate(-0.5f, 0.0f, -0.5f);
            PoseStack.Pose last = ms.last();
            drawVertex(last, builder, 0.0f, 0.0f, 1.0f, 0.5f, 0.0f, 0.0f, 1.0f, 0.0f, lm);
            drawVertex(last, builder, 1.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, lm);
            drawVertex(last, builder, 1.0f, 0.0f, 0.0f, 0.0f, 0.5f, 0.0f, 1.0f, 0.0f, lm);
            drawVertex(last, builder, 0.0f, 0.0f, 0.0f, 0.5f, 0.5f, 0.0f, 1.0f, 0.0f, lm);
            if (material != 0) {
                drawVertex(last, builder, 0.0f, 0.0f, 1.0f, color[0], color[1], color[2], 1.0f, 1.0f, 0.0f, 0.0f,
                        0.0f, 0.05625f, lm);
                drawVertex(last, builder, 1.0f, 0.0f, 1.0f, color[0], color[1], color[2], 1.0f, 0.5f, 0.0f, 0.0f,
                        0.0f, 0.05625f, lm);
                drawVertex(last, builder, 1.0f, 0.0f, 0.0f, color[0], color[1], color[2], 1.0f, 0.5f, 0.5f, 0.0f,
                        0.0f, 0.05625f, lm);
                drawVertex(last, builder, 0.0f, 0.0f, 0.0f, color[0], color[1], color[2], 1.0f, 1.0f, 0.5f, 0.0f,
                        0.0f, 0.05625f, lm);
            }
            drawVertex(last, builder, 1.0f, 0.0f, 0.0f, 0.0f, 0.5f, 0.0f, -1.0f, 0.0f, lm);
            drawVertex(last, builder, 1.0f, 0.0f, 1.0f, 0.5f, 0.5f, 0.0f, -1.0f, 0.0f, lm);
            drawVertex(last, builder, 0.0f, 0.0f, 1.0f, 0.5f, 0.0f, 0.0f, -1.0f, 0.0f, lm);
            drawVertex(last, builder, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, -1.0f, 0.0f, lm);
            if (material != 0) {
                drawVertex(last, builder, 1.0f, 0.0f, 0.0f, color[0], color[1], color[2], 1.0f, 0.5f, 0.5f, 0.0f,
                        -1.0f, 0.0f, lm);
                drawVertex(last, builder, 1.0f, 0.0f, 1.0f, color[0], color[1], color[2], 1.0f, 1.0f, 0.5f, 0.0f,
                        -1.0f, 0.0f, lm);
                drawVertex(last, builder, 0.0f, 0.0f, 1.0f, color[0], color[1], color[2], 1.0f, 1.0f, 0.0f, 0.0f,
                        -1.0f, 0.0f, lm);
                drawVertex(last, builder, 0.0f, 0.0f, 0.0f, color[0], color[1], color[2], 1.0f, 0.5f, 0.0f, 0.0f,
                        -1.0f, 0.0f, lm);
            }
            GlStateManager._disableCull();
            drawVertex(last, builder, 0.2f, -0.08f, 0.8f, 0.5f, 0.5f, -SQRT2, 0.0f, SQRT2, lm);
            drawVertex(last, builder, 0.2f, 0.08f, 0.8f, 0.5f, 0.65625f, -SQRT2, 0.0f, SQRT2, lm);
            drawVertex(last, builder, 0.9f, 0.08f, 0.8f, 0.0f, 0.65625f, -SQRT2, 0.0f, SQRT2, lm);
            drawVertex(last, builder, 0.9f, -0.08f, 0.8f, 0.0f, 0.5f, -SQRT2, 0.0f, SQRT2, lm);
            if (material != 0) {
                drawVertex(last, builder, 0.2f, -0.08f, 0.8f, color[0], color[1], color[2], 1.0f, 1.0f, 0.5f, -SQRT2,
                        0.0f, SQRT2, lm);
                drawVertex(last, builder, 0.2f, 0.08f, 0.8f, color[0], color[1], color[2], 1.0f, 1.0f, 0.65625f,
                        -SQRT2, 0.0f, SQRT2, lm);
                drawVertex(last, builder, 0.9f, 0.08f, 0.8f, color[0], color[1], color[2], 1.0f, 0.5f, 0.65625f,
                        -SQRT2, 0.0f, SQRT2, lm);
                drawVertex(last, builder, 0.9f, -0.08f, 0.8f, color[0], color[1], color[2], 1.0f, 0.5f, 0.5f, -SQRT2,
                        0.0f, SQRT2, lm);
            }
            drawVertex(last, builder, 0.2f, -0.08f, 0.8f, 0.5f, 0.5f, -SQRT2, 0.0f, SQRT2, lm);
            drawVertex(last, builder, 0.2f, 0.08f, 0.8f, 0.5f, 0.65625f, -SQRT2, 0.0f, SQRT2, lm);
            drawVertex(last, builder, 0.2f, 0.08f, 0.2f, 0.0f, 0.65625f, -SQRT2, 0.0f, SQRT2, lm);
            drawVertex(last, builder, 0.2f, -0.08f, 0.2f, 0.0f, 0.5f, -SQRT2, 0.0f, SQRT2, lm);
            if (material != 0) {
                drawVertex(last, builder, 0.2f, -0.08f, 0.8f, color[0], color[1], color[2], 1.0f, 1.0f, 0.5f, -SQRT2,
                        0.0f, SQRT2, lm);
                drawVertex(last, builder, 0.2f, 0.08f, 0.8f, color[0], color[1], color[2], 1.0f, 1.0f, 0.65625f,
                        -SQRT2, 0.0f, SQRT2, lm);
                drawVertex(last, builder, 0.2f, 0.08f, 0.2f, color[0], color[1], color[2], 1.0f, 0.5f, 0.65625f,
                        -SQRT2, 0.0f, SQRT2, lm);
                drawVertex(last, builder, 0.2f, -0.08f, 0.2f, color[0], color[1], color[2], 1.0f, 0.5f, 0.5f, -SQRT2,
                        0.0f, SQRT2, lm);
            }
            GlStateManager._enableCull();
            ms.popPose();
        } else {
            ItemRenderer itemRender = Minecraft.getInstance().getItemRenderer();
            ms.pushPose();
            ms.scale(0.85f, 0.85f, 0.85f);
            ms.mulPose(Axis.ZP.rotationDegrees(entityRenderState.xRot));
            ms.mulPose(Axis.YP.rotationDegrees(entityRenderState.yRot - 90.0f));
            ms.mulPose(Axis.XP.rotationDegrees(90.0f));
            itemRender.renderStatic(entityRenderState.weapon, ItemDisplayContext.NONE, lm,
                    OverlayTexture.NO_OVERLAY, ms, bufs, Minecraft.getInstance().level, 0);
            ms.popPose();
        }
        super.render(entityRenderState, ms, bufs, lm);
    }

    @NotNull
    @Override
    public BoomerangRenderState createRenderState() {
        return new BoomerangRenderState();
    }

    @Override
    public void extractRenderState(EntityBoomerang entity, BoomerangRenderState entityRenderState, float f) {
        super.extractRenderState(entity, entityRenderState, f);
        entityRenderState.weaponMaterialId = entity.getWeaponMaterialId();
        entityRenderState.materialColor = entity.getMaterialColor();
        entityRenderState.weapon = entity.getWeapon();
    }

    public static class BoomerangRenderState extends WMRendererState {
        public int weaponMaterialId;
        public float[] materialColor;
        public ItemStack weapon;
    }

}
