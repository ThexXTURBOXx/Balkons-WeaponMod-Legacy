package ckathode.weaponmod.render;

import ckathode.weaponmod.WeaponModConfig;
import ckathode.weaponmod.WeaponModResources;
import ckathode.weaponmod.entity.projectile.EntityKnife;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.ItemTransforms.TransformType;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class RenderKnife extends WMRenderer<EntityKnife> {

    public RenderKnife(Context context) {
        super(context);
    }

    @Override
    public void render(@NotNull EntityKnife entityknife, float f, float f1,
                       @NotNull PoseStack ms, @NotNull MultiBufferSource bufs, int lm) {
        if (!WeaponModConfig.get().itemModelForEntity) {
            VertexConsumer builder = bufs.getBuffer(RenderType.entityCutout(getTextureLocation(entityknife)));
            ms.pushPose();
            ms.mulPose(Vector3f.YP.rotationDegrees(entityknife.yRotO + (entityknife.getYRot() - entityknife.yRotO) * f1 - 90.0f));
            ms.mulPose(Vector3f.ZP.rotationDegrees(entityknife.xRotO + (entityknife.getXRot() - entityknife.xRotO) * f1));
            float[] color = entityknife.getMaterialColor();
            float f13 = entityknife.shakeTime - f1;
            if (f13 > 0.0f) {
                float f14 = -Mth.sin(f13 * 3.0f) * f13;
                ms.mulPose(Vector3f.ZP.rotationDegrees(f14));
            }
            ms.mulPose(Vector3f.XP.rotationDegrees(45.0f));
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
                ms.mulPose(Vector3f.XP.rotationDegrees(90.0f));
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
            ms.mulPose(Vector3f.YP.rotationDegrees(entityknife.yRotO + (entityknife.getYRot() - entityknife.yRotO) * f1 - 90.0f));
            ms.mulPose(Vector3f.ZP.rotationDegrees(entityknife.xRotO + (entityknife.getXRot() - entityknife.xRotO) * f1 - 45.0f));
            float f15 = entityknife.shakeTime - f1;
            if (f15 > 0.0f) {
                float f16 = -Mth.sin(f15 * 3.0f) * f15;
                ms.mulPose(Vector3f.ZP.rotationDegrees(f16));
            }
            ms.translate(-0.15f, -0.15f, 0.0f);
            itemRender.renderStatic(getStackToRender(entityknife), TransformType.NONE, lm,
                    OverlayTexture.NO_OVERLAY, ms, bufs, entityknife.getId());
            ms.popPose();
        }
        super.render(entityknife, f, f1, ms, bufs, lm);
    }

    public ItemStack getStackToRender(EntityKnife entity) {
        return entity.getWeapon();
    }

    @Override
    @NotNull
    public ResourceLocation getTextureLocation(@NotNull EntityKnife entity) {
        return WeaponModResources.Entity.KNIFE;
    }

}
