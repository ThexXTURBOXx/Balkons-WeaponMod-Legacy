package ckathode.weaponmod.render;

import ckathode.weaponmod.BalkonsWeaponMod;
import ckathode.weaponmod.WeaponModResources;
import ckathode.weaponmod.entity.projectile.EntityKnife;
import com.mojang.blaze3d.matrix.MatrixStack;
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
import net.minecraft.util.math.MathHelper;

public class RenderKnife extends WMRenderer<EntityKnife> {

    public RenderKnife(EntityRendererManager renderManager) {
        super(renderManager);
    }

    @Override
    @ParametersAreNonnullByDefault
    public void render(EntityKnife entityknife, float f, float f1,
                       MatrixStack ms, IRenderTypeBuffer bufs, int lm) {
        if (!BalkonsWeaponMod.instance.modConfig.itemModelForEntity.get()) {
            IVertexBuilder builder = bufs.getBuffer(RenderType.getEntityCutout(getEntityTexture(entityknife)));
            ms.push();
            ms.rotate(Vector3f.YP.rotationDegrees(entityknife.prevRotationYaw + (entityknife.rotationYaw - entityknife.prevRotationYaw) * f1 - 90.0f));
            ms.rotate(Vector3f.ZP.rotationDegrees(entityknife.prevRotationPitch + (entityknife.rotationPitch - entityknife.prevRotationPitch) * f1));
            float[] color = entityknife.getMaterialColor();
            float f13 = entityknife.arrowShake - f1;
            if (f13 > 0.0f) {
                float f14 = -MathHelper.sin(f13 * 3.0f) * f13;
                ms.rotate(Vector3f.ZP.rotationDegrees(f14));
            }
            ms.rotate(Vector3f.XP.rotationDegrees(45.0f));
            ms.scale(0.05625f, 0.05625f, 0.05625f);
            ms.translate(-4.0f, 0.0f, 0.0f);
            MatrixStack.Entry last = ms.getLast();
            drawVertex(last, builder, -7.0f, -2.0f, -2.0f, 0.0f, 0.15625f, 0.05625f, 0.0f, 0.0f, lm);
            drawVertex(last, builder, -7.0f, -2.0f, 2.0f, 0.15625f, 0.15625f, 0.05625f, 0.0f, 0.0f, lm);
            drawVertex(last, builder, -7.0f, 2.0f, 2.0f, 0.15625f, 0.3125f, 0.05625f, 0.0f, 0.0f, lm);
            drawVertex(last, builder, -7.0f, 2.0f, -2.0f, 0.0f, 0.3125f, 0.05625f, 0.0f, 0.0f, lm);
            drawVertex(last, builder, -7.0f, 2.0f, -2.0f, 0.0f, 0.15625f, -0.05625f, 0.0f, 0.0f, lm);
            drawVertex(last, builder, -7.0f, 2.0f, 2.0f, 0.15625f, 0.15625f, -0.05625f, 0.0f, 0.0f, lm);
            drawVertex(last, builder, -7.0f, -2.0f, 2.0f, 0.15625f, 0.3125f, -0.05625f, 0.0f, 0.0f, lm);
            drawVertex(last, builder, -7.0f, -2.0f, -2.0f, 0.0f, 0.3125f, -0.05625f, 0.0f, 0.0f, lm);
            for (int j = 0; j < 4; ++j) {
                ms.rotate(Vector3f.XP.rotationDegrees(90.0f));
                last = ms.getLast();
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
            ms.pop();
        } else {
            ItemRenderer itemRender = Minecraft.getInstance().getItemRenderer();
            ms.push();
            ms.scale(0.85f, 0.85f, 0.85f);
            ms.rotate(Vector3f.YP.rotationDegrees(entityknife.prevRotationYaw + (entityknife.rotationYaw - entityknife.prevRotationYaw) * f1 - 90.0f));
            ms.rotate(Vector3f.ZP.rotationDegrees(entityknife.prevRotationPitch + (entityknife.rotationPitch - entityknife.prevRotationPitch) * f1 - 45.0f));
            float f15 = entityknife.arrowShake - f1;
            if (f15 > 0.0f) {
                float f16 = -MathHelper.sin(f15 * 3.0f) * f15;
                ms.rotate(Vector3f.ZP.rotationDegrees(f16));
            }
            ms.translate(-0.15f, -0.15f, 0.0f);
            itemRender.renderItem(getStackToRender(entityknife), TransformType.NONE, lm, OverlayTexture.NO_OVERLAY,
                    ms, bufs);
            ms.pop();
        }
        super.render(entityknife, f, f1, ms, bufs, lm);
    }

    public ItemStack getStackToRender(EntityKnife entity) {
        return entity.getWeapon();
    }

    @Override
    @Nonnull
    @ParametersAreNonnullByDefault
    public ResourceLocation getEntityTexture(@Nonnull EntityKnife entity) {
        return WeaponModResources.Entity.KNIFE;
    }

}
