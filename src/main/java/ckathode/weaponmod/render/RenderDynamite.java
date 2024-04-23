package ckathode.weaponmod.render;

import ckathode.weaponmod.WeaponModResources;
import ckathode.weaponmod.entity.projectile.EntityDynamite;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

public class RenderDynamite extends WMRenderer<EntityDynamite> {

    public RenderDynamite(EntityRendererManager renderManager) {
        super(renderManager);
    }

    @Override
    @ParametersAreNonnullByDefault
    public void render(EntityDynamite entitydynamite, float f, float f1,
                       MatrixStack ms, IRenderTypeBuffer bufs, int lm) {
        IVertexBuilder builder = bufs.getBuffer(RenderType.getEntityCutout(getEntityTexture(entitydynamite)));
        ms.push();
        ms.rotate(Vector3f.YP.rotationDegrees(entitydynamite.rotationYaw + 90.0f));
        ms.rotate(Vector3f.ZP.rotationDegrees(entitydynamite.prevRotationPitch + (entitydynamite.rotationPitch - entitydynamite.prevRotationPitch) * f1));
        float f11 = -f1;
        if (f11 > 0.0f) {
            float f12 = -MathHelper.sin(f11 * 3.0f) * f11;
            ms.rotate(Vector3f.ZP.rotationDegrees(f12));
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
        }
        ms.pop();
        super.render(entitydynamite, f, f1, ms, bufs, lm);
    }

    @Override
    @Nonnull
    @ParametersAreNonnullByDefault
    public ResourceLocation getEntityTexture(@Nonnull EntityDynamite entity) {
        return WeaponModResources.Entity.DYNAMITE;
    }

}
