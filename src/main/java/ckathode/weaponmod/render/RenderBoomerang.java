package ckathode.weaponmod.render;

import ckathode.weaponmod.BalkonsWeaponMod;
import ckathode.weaponmod.WeaponModResources;
import ckathode.weaponmod.entity.projectile.EntityBoomerang;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class RenderBoomerang extends WMRenderer<EntityBoomerang> {

    public RenderBoomerang(EntityRendererManager renderManager) {
        super(renderManager);
    }

    @Override
    @ParametersAreNonnullByDefault
    public void render(EntityBoomerang entityboomerang, float f, float f1,
                       MatrixStack ms, IRenderTypeBuffer bufs, int lm) {
        if (!BalkonsWeaponMod.instance.modConfig.itemModelForEntity.get()) {
            IVertexBuilder builder = bufs.getBuffer(RenderType.getEntityCutout(getEntityTexture(entityboomerang)));
            ms.push();
            ms.rotate(Vector3f.ZP.rotationDegrees(entityboomerang.prevRotationPitch + (entityboomerang.rotationPitch - entityboomerang.prevRotationPitch) * f1));
            ms.rotate(Vector3f.YP.rotationDegrees(entityboomerang.prevRotationYaw + (entityboomerang.rotationYaw - entityboomerang.prevRotationYaw) * f1 - 90.0f));
            int material = entityboomerang.getWeaponMaterialId();
            float[] color = entityboomerang.getMaterialColor();
            ms.translate(-0.5f, 0.0f, -0.5f);
            MatrixStack.Entry last = ms.getLast();
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
            GlStateManager.disableCull();
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
            GlStateManager.enableCull();
            ms.pop();
        } else {
            ItemRenderer itemRender = Minecraft.getInstance().getItemRenderer();
            ms.push();
            ms.scale(0.85f, 0.85f, 0.85f);
            ms.rotate(Vector3f.ZP.rotationDegrees(entityboomerang.prevRotationPitch + (entityboomerang.rotationPitch - entityboomerang.prevRotationPitch) * f1));
            ms.rotate(Vector3f.YP.rotationDegrees(entityboomerang.prevRotationYaw + (entityboomerang.rotationYaw - entityboomerang.prevRotationYaw) * f1 - 90.0f));
            ms.rotate(Vector3f.XP.rotationDegrees(90.0f));
            itemRender.renderItem(getStackToRender(entityboomerang), TransformType.NONE, lm, OverlayTexture.NO_OVERLAY,
                    ms, bufs);
            ms.pop();
        }
        super.render(entityboomerang, f, f1, ms, bufs, lm);
    }

    public ItemStack getStackToRender(EntityBoomerang entity) {
        return entity.getWeapon();
    }

    @Override
    @Nonnull
    @ParametersAreNonnullByDefault
    public ResourceLocation getEntityTexture(@Nonnull EntityBoomerang entity) {
        return WeaponModResources.Entity.BOOMERANG;
    }

}
