package ckathode.weaponmod.render;

import ckathode.weaponmod.WeaponModResources;
import ckathode.weaponmod.entity.EntityDummy;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;

public class RenderDummy extends WMRenderer<EntityDummy, RenderDummy.DummyRenderState> {

    private final ModelDummy modelDummy;

    public RenderDummy(Context context) {
        super(context);
        modelDummy = new ModelDummy(context.bakeLayer(ModelDummy.MAIN_LAYER));
        shadowRadius = 0.7f;
    }

    @Override
    public void render(DummyRenderState entityRenderState, PoseStack ms, MultiBufferSource bufs, int lm) {
        ms.pushPose();
        ms.translate(0, -0.025f, 0);
        ms.mulPose(Axis.YP.rotationDegrees(180.0f - entityRenderState.yRot));
        float f2 = entityRenderState.timeSinceHit;
        float f3 = entityRenderState.currentDamage;
        if (f3 < 0.0f) {
            f3 = 0.0f;
        }
        if (f2 > 0.0f) {
            ms.mulPose(Axis.ZP.rotationDegrees(Mth.sin(f2) * f2 * f3 / 10.0f * entityRenderState.rockDirection / 5.0f));
        }
        VertexConsumer builder = bufs.getBuffer(RenderType.entityCutout(WeaponModResources.Entity.DUMMY));
        ms.scale(-1.0f, -1.0f, 1.0f);
        ms.mulPose(Axis.XP.rotationDegrees(180.0f));
        modelDummy.renderToBuffer(ms, builder, lm, OverlayTexture.NO_OVERLAY);
        ms.popPose();
        super.render(entityRenderState, ms, bufs, lm);
    }

    @NotNull
    @Override
    public DummyRenderState createRenderState() {
        return new DummyRenderState();
    }

    @Override
    public void extractRenderState(EntityDummy entity, DummyRenderState entityRenderState, float f) {
        super.extractRenderState(entity, entityRenderState, f);
        entityRenderState.timeSinceHit = entity.getTimeSinceHit() - f;
        entityRenderState.currentDamage = entity.getCurrentDamage() - f;
        entityRenderState.rockDirection = entity.getRockDirection();
    }

    public static class DummyRenderState extends WMRendererState {
        public float timeSinceHit;
        public float currentDamage;
        public int rockDirection;
    }

}
