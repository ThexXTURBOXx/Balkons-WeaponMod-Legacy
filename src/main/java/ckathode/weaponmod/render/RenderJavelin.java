package ckathode.weaponmod.render;

import ckathode.weaponmod.BalkonsWeaponMod;
import ckathode.weaponmod.WeaponModResources;
import ckathode.weaponmod.entity.projectile.EntityJavelin;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3f;

public class RenderJavelin extends WMRenderer<EntityJavelin> {

    public RenderJavelin(EntityRendererManager renderManager) {
        super(renderManager);
    }

    @Override
    @ParametersAreNonnullByDefault
    public void render(EntityJavelin entityjavelin, float f, float f1,
                       MatrixStack ms, IRenderTypeBuffer bufs, int lm) {
        if (!BalkonsWeaponMod.instance.modConfig.itemModelForEntity.get()) {
            IVertexBuilder builder = bufs.getBuffer(RenderType.entityCutout(getTextureLocation(entityjavelin)));
            ms.pushPose();
            ms.mulPose(Vector3f.YP.rotationDegrees(entityjavelin.yRotO + (entityjavelin.yRot - entityjavelin.yRotO) * f1 - 90.0f));
            ms.mulPose(Vector3f.ZP.rotationDegrees(entityjavelin.xRotO + (entityjavelin.xRot - entityjavelin.xRotO) * f1));
            float length = 20.0f;
            float f11 = entityjavelin.shakeTime - f1;
            if (f11 > 0.0f) {
                float f12 = -MathHelper.sin(f11 * 3.0f) * f11;
                ms.mulPose(Vector3f.ZP.rotationDegrees(f12));
            }
            ms.mulPose(Vector3f.XP.rotationDegrees(45.0f));
            ms.scale(0.05625f, 0.05625f, 0.05625f);
            ms.translate(-4.0f, 0.0f, 0.0f);
            MatrixStack.Entry last = ms.last();
            drawVertex(last, builder, -length, -2.0f, -2.0f, 0.0f, 0.15625f, 0.05625f, 0.0f, 0.0f, lm);
            drawVertex(last, builder, -length, -2.0f, 2.0f, 0.15625f, 0.15625f, 0.05625f, 0.0f, 0.0f, lm);
            drawVertex(last, builder, -length, 2.0f, 2.0f, 0.15625f, 0.3125f, 0.05625f, 0.0f, 0.0f, lm);
            drawVertex(last, builder, -length, 2.0f, -2.0f, 0.0f, 0.3125f, 0.05625f, 0.0f, 0.0f, lm);
            drawVertex(last, builder, -length, 2.0f, -2.0f, 0.0f, 0.15625f, -0.05625f, 0.0f, 0.0f, lm);
            drawVertex(last, builder, -length, 2.0f, 2.0f, 0.15625f, 0.15625f, -0.05625f, 0.0f, 0.0f, lm);
            drawVertex(last, builder, -length, -2.0f, 2.0f, 0.15625f, 0.3125f, -0.05625f, 0.0f, 0.0f, lm);
            drawVertex(last, builder, -length, -2.0f, -2.0f, 0.0f, 0.3125f, -0.05625f, 0.0f, 0.0f, lm);
            for (int j = 0; j < 4; ++j) {
                ms.mulPose(Vector3f.XP.rotationDegrees(90.0f));
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
            ms.mulPose(Vector3f.YP.rotationDegrees(entityjavelin.yRotO + (entityjavelin.yRot - entityjavelin.yRotO) * f1 - 90.0f));
            ms.mulPose(Vector3f.ZP.rotationDegrees(entityjavelin.xRotO + (entityjavelin.xRot - entityjavelin.xRotO) * f1 - 45.0f));
            float f13 = entityjavelin.shakeTime - f1;
            if (f13 > 0.0f) {
                float f14 = -MathHelper.sin(f13 * 3.0f) * f13;
                ms.mulPose(Vector3f.ZP.rotationDegrees(f14));
            }
            ms.translate(-0.25f, -0.25f, 0.0f);
            ms.mulPose(Vector3f.YP.rotationDegrees(180.0f));
            itemRender.renderStatic(getStackToRender(entityjavelin), TransformType.NONE, lm, OverlayTexture.NO_OVERLAY,
                    ms, bufs);
            ms.popPose();
        }
        super.render(entityjavelin, f, f1, ms, bufs, lm);
    }

    public ItemStack getStackToRender(EntityJavelin entity) {
        return new ItemStack(BalkonsWeaponMod.javelin);
    }

    @Override
    @Nonnull
    @ParametersAreNonnullByDefault
    public ResourceLocation getTextureLocation(@Nonnull EntityJavelin entity) {
        return WeaponModResources.Entity.JAVELIN;
    }

}
