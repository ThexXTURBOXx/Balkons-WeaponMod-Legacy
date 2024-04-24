package ckathode.weaponmod.render;

import ckathode.weaponmod.WeaponModResources;
import ckathode.weaponmod.entity.EntityDummy;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3f;

public class RenderDummy extends WMRenderer<EntityDummy> {

    private final ModelDummy modelDummy;

    public RenderDummy(EntityRendererManager renderManager) {
        super(renderManager);
        modelDummy = new ModelDummy();
        shadowRadius = 1.0f;
    }

    @Override
    @ParametersAreNonnullByDefault
    public void render(EntityDummy entitydummy, float f, float f1,
                       MatrixStack ms, IRenderTypeBuffer bufs, int lm) {
        ms.pushPose();
        ms.translate(0, -0.025f, 0);
        ms.mulPose(Vector3f.YP.rotationDegrees(180.0f - f));
        float f2 = entitydummy.getTimeSinceHit() - f1;
        float f3 = entitydummy.getCurrentDamage() - f1;
        if (f3 < 0.0f) {
            f3 = 0.0f;
        }
        if (f2 > 0.0f) {
            ms.mulPose(Vector3f.ZP.rotationDegrees(MathHelper.sin(f2) * f2 * f3 / 10.0f * entitydummy.getRockDirection() / 5.0f));
        }
        IVertexBuilder builder = bufs.getBuffer(RenderType.entityCutout(getTextureLocation(entitydummy)));
        ms.scale(-1.0f, -1.0f, 1.0f);
        ms.mulPose(Vector3f.XP.rotationDegrees(180.0f));
        modelDummy.renderToBuffer(ms, builder, lm, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        ms.popPose();
        super.render(entitydummy, f, f1, ms, bufs, lm);
    }

    @Override
    @Nonnull
    @ParametersAreNonnullByDefault
    public ResourceLocation getTextureLocation(@Nonnull EntityDummy entity) {
        return WeaponModResources.Entity.DUMMY;
    }

}
