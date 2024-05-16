package ckathode.weaponmod.render;

import ckathode.weaponmod.WeaponModResources;
import ckathode.weaponmod.entity.projectile.EntityMusketBullet;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class RenderMusketBullet extends WMRenderer<EntityMusketBullet> {

    public RenderMusketBullet(Context context) {
        super(context);
    }

    @Override
    public void render(@NotNull EntityMusketBullet entitymusketbullet, float f, float f1,
                       @NotNull PoseStack ms, @NotNull MultiBufferSource bufs, int lm) {
        VertexConsumer builder = bufs.getBuffer(RenderType.entityCutout(getTextureLocation(entitymusketbullet)));
        ms.pushPose();
        ms.scale(0.07f, 0.07f, 0.07f);
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
            ms.mulPose(Axis.XP.rotationDegrees(90.0f));
            last = ms.last();
            drawVertex(last, builder, -1.0f, -1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.05625f, lm);
            drawVertex(last, builder, 1.0f, -1.0f, 0.0f, 0.3125f, 0.0f, 0.0f, 0.0f, 0.05625f, lm);
            drawVertex(last, builder, 1.0f, 1.0f, 0.0f, 0.3125f, 0.3125f, 0.0f, 0.0f, 0.05625f, lm);
            drawVertex(last, builder, -1.0f, 1.0f, 0.0f, 0.0f, 0.3125f, 0.0f, 0.0f, 0.05625f, lm);
        }
        ms.popPose();
        super.render(entitymusketbullet, f, f1, ms, bufs, lm);
    }

    @Override
    @NotNull
    public ResourceLocation getTextureLocation(@NotNull EntityMusketBullet entity) {
        return WeaponModResources.Entity.BULLET;
    }

}
