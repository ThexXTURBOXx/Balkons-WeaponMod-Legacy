package ckathode.weaponmod.render;

import ckathode.weaponmod.WeaponModResources;
import ckathode.weaponmod.entity.projectile.EntityCannonBall;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;

public class RenderCannonBall extends WMRenderer<EntityCannonBall> {

    public RenderCannonBall(EntityRendererManager renderManager) {
        super(renderManager);
        shadowSize = 0.5f;
    }

    @Override
    @ParametersAreNonnullByDefault
    public void render(EntityCannonBall entitycannonball, float f, float f1,
                       MatrixStack ms, IRenderTypeBuffer bufs, int lm) {
        ms.push();
        IVertexBuilder builder = bufs.getBuffer(RenderType.getEntityCutout(getEntityTexture(entitycannonball)));
        ms.rotate(Vector3f.YP.rotationDegrees(180.0f - f));
        ms.scale(-1.0f, -1.0f, 1.0f);
        ms.scale(0.7f, 0.7f, 0.7f);
        ms.rotate(Vector3f.XP.rotationDegrees(180.0f));
        MatrixStack.Entry last = ms.getLast();
        drawVertex(last, builder, -0.5f, 0.5f, -0.5f, 0.0f, 1.0f, 0.0f, 0.0f, 0.05625f, lm);
        drawVertex(last, builder, 0.5f, 0.5f, -0.5f, 1.0f, 1.0f, 0.0f, 0.0f, 0.05625f, lm);
        drawVertex(last, builder, 0.5f, -0.5f, -0.5f, 1.0f, 0.0f, 0.0f, 0.0f, 0.05625f, lm);
        drawVertex(last, builder, -0.5f, -0.5f, -0.5f, 0.0f, 0.0f, 0.0f, 0.0f, 0.05625f, lm);
        drawVertex(last, builder, -0.5f, -0.5f, 0.5f, 0.0f, 0.0f, 0.0f, 0.0f, 0.05625f, lm);
        drawVertex(last, builder, 0.5f, -0.5f, 0.5f, 1.0f, 0.0f, 0.0f, 0.0f, 0.05625f, lm);
        drawVertex(last, builder, 0.5f, 0.5f, 0.5f, 1.0f, 1.0f, 0.0f, 0.0f, 0.05625f, lm);
        drawVertex(last, builder, -0.5f, 0.5f, 0.5f, 0.0f, 1.0f, 0.0f, 0.0f, 0.05625f, lm);
        drawVertex(last, builder, -0.5f, -0.5f, -0.5f, 0.0f, 0.0f, 0.0f, 0.0f, 0.05625f, lm);
        drawVertex(last, builder, 0.5f, -0.5f, -0.5f, 1.0f, 0.0f, 0.0f, 0.0f, 0.05625f, lm);
        drawVertex(last, builder, 0.5f, -0.5f, 0.5f, 1.0f, 1.0f, 0.0f, 0.0f, 0.05625f, lm);
        drawVertex(last, builder, -0.5f, -0.5f, 0.5f, 0.0f, 1.0f, 0.0f, 0.0f, 0.05625f, lm);
        drawVertex(last, builder, -0.5f, 0.5f, 0.5f, 0.0f, 1.0f, 0.0f, 0.0f, 0.05625f, lm);
        drawVertex(last, builder, 0.5f, 0.5f, 0.5f, 1.0f, 1.0f, 0.0f, 0.0f, 0.05625f, lm);
        drawVertex(last, builder, 0.5f, 0.5f, -0.5f, 1.0f, 0.0f, 0.0f, 0.0f, 0.05625f, lm);
        drawVertex(last, builder, -0.5f, 0.5f, -0.5f, 0.0f, 0.0f, 0.0f, 0.0f, 0.05625f, lm);
        drawVertex(last, builder, -0.5f, -0.5f, 0.5f, 0.0f, 0.0f, 0.0f, 0.0f, 0.05625f, lm);
        drawVertex(last, builder, -0.5f, 0.5f, 0.5f, 1.0f, 0.0f, 0.0f, 0.0f, 0.05625f, lm);
        drawVertex(last, builder, -0.5f, 0.5f, -0.5f, 1.0f, 1.0f, 0.0f, 0.0f, 0.05625f, lm);
        drawVertex(last, builder, -0.5f, -0.5f, -0.5f, 0.0f, 1.0f, 0.0f, 0.0f, 0.05625f, lm);
        drawVertex(last, builder, 0.5f, -0.5f, -0.5f, 0.0f, 0.0f, 0.0f, 0.0f, 0.05625f, lm);
        drawVertex(last, builder, 0.5f, 0.5f, -0.5f, 1.0f, 0.0f, 0.0f, 0.0f, 0.05625f, lm);
        drawVertex(last, builder, 0.5f, 0.5f, 0.5f, 1.0f, 1.0f, 0.0f, 0.0f, 0.05625f, lm);
        drawVertex(last, builder, 0.5f, -0.5f, 0.5f, 0.0f, 1.0f, 0.0f, 0.0f, 0.05625f, lm);
        ms.pop();
        super.render(entitycannonball, f, f1, ms, bufs, lm);
    }

    @Override
    @Nonnull
    @ParametersAreNonnullByDefault
    public ResourceLocation getEntityTexture(@Nonnull EntityCannonBall entity) {
        return WeaponModResources.Entity.CANNONBALL;
    }

}
