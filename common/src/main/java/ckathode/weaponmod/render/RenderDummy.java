package ckathode.weaponmod.render;

import ckathode.weaponmod.WeaponModResources;
import ckathode.weaponmod.entity.EntityDummy;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;

public class RenderDummy extends WMRenderer<EntityDummy> {

    private final ModelDummy modelDummy;

    public RenderDummy(Context context) {
        super(context);
        modelDummy = new ModelDummy(context.bakeLayer(ModelDummy.MAIN_LAYER));
        shadowRadius = 1.0f;
    }

    @Override
    public void render(@NotNull EntityDummy entitydummy, float f, float f1,
                       @NotNull PoseStack ms, @NotNull MultiBufferSource bufs, int lm) {
        ms.pushPose();
        ms.translate(0, -0.025f, 0);
        ms.mulPose(Vector3f.YP.rotationDegrees(180.0f - f));
        float f2 = entitydummy.getTimeSinceHit() - f1;
        float f3 = entitydummy.getCurrentDamage() - f1;
        if (f3 < 0.0f) {
            f3 = 0.0f;
        }
        if (f2 > 0.0f) {
            ms.mulPose(Vector3f.ZP.rotationDegrees(Mth.sin(f2) * f2 * f3 / 10.0f * entitydummy.getRockDirection() / 5.0f));
        }
        VertexConsumer builder = bufs.getBuffer(RenderType.entityCutout(getTextureLocation(entitydummy)));
        ms.scale(-1.0f, -1.0f, 1.0f);
        ms.mulPose(Vector3f.XP.rotationDegrees(180.0f));
        modelDummy.renderToBuffer(ms, builder, lm, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        ms.popPose();
        super.render(entitydummy, f, f1, ms, bufs, lm);
    }

    @Override
    @NotNull
    public ResourceLocation getTextureLocation(@NotNull EntityDummy entity) {
        return WeaponModResources.Entity.DUMMY;
    }

}
