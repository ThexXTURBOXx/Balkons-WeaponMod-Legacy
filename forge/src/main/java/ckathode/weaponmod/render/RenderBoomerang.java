package ckathode.weaponmod.render;

import ckathode.weaponmod.WeaponModResources;
import ckathode.weaponmod.entity.projectile.EntityBoomerang;
import ckathode.weaponmod.forge.BalkonsWeaponModForge;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.ItemTransforms.TransformType;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class RenderBoomerang extends WMRenderer<EntityBoomerang> {

    public RenderBoomerang(EntityRenderDispatcher renderManager) {
        super(renderManager);
    }

    @Override
    @ParametersAreNonnullByDefault
    public void render(EntityBoomerang entityboomerang, float f, float f1,
                       PoseStack ms, MultiBufferSource bufs, int lm) {
        if (!BalkonsWeaponModForge.instance.modConfig.itemModelForEntity.get()) {
            VertexConsumer builder = bufs.getBuffer(RenderType.entityCutout(getTextureLocation(entityboomerang)));
            ms.pushPose();
            ms.mulPose(Vector3f.ZP.rotationDegrees(entityboomerang.xRotO + (entityboomerang.xRot - entityboomerang.xRotO) * f1));
            ms.mulPose(Vector3f.YP.rotationDegrees(entityboomerang.yRotO + (entityboomerang.yRot - entityboomerang.yRotO) * f1 - 90.0f));
            int material = entityboomerang.getWeaponMaterialId();
            float[] color = entityboomerang.getMaterialColor();
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
            ms.mulPose(Vector3f.ZP.rotationDegrees(entityboomerang.xRotO + (entityboomerang.xRot - entityboomerang.xRotO) * f1));
            ms.mulPose(Vector3f.YP.rotationDegrees(entityboomerang.yRotO + (entityboomerang.yRot - entityboomerang.yRotO) * f1 - 90.0f));
            ms.mulPose(Vector3f.XP.rotationDegrees(90.0f));
            itemRender.renderStatic(getStackToRender(entityboomerang), TransformType.NONE, lm,
                    OverlayTexture.NO_OVERLAY,
                    ms, bufs);
            ms.popPose();
        }
        super.render(entityboomerang, f, f1, ms, bufs, lm);
    }

    public ItemStack getStackToRender(EntityBoomerang entity) {
        return entity.getWeapon();
    }

    @Override
    @Nonnull
    @ParametersAreNonnullByDefault
    public ResourceLocation getTextureLocation(@Nonnull EntityBoomerang entity) {
        return WeaponModResources.Entity.BOOMERANG;
    }

}
