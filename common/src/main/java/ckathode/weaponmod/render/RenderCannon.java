package ckathode.weaponmod.render;

import ckathode.weaponmod.WeaponModResources;
import ckathode.weaponmod.entity.EntityCannon;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;

public class RenderCannon extends EntityRenderer<EntityCannon> {

    private final ModelCannonBarrel modelBarrel;
    private final ModelCannonStandard modelStandard;

    public RenderCannon(Context context) {
        super(context);
        modelBarrel = new ModelCannonBarrel(context.bakeLayer(ModelCannonBarrel.CANNON_BARREL_LAYER));
        modelStandard = new ModelCannonStandard(context.bakeLayer(ModelCannonStandard.CANNON_STANDARD_LAYER));
        shadowRadius = 1.0f;
    }

    @Override
    public void render(@NotNull EntityCannon entitycannon, float f, float f1,
                       @NotNull PoseStack ms, @NotNull MultiBufferSource bufs, int lm) {
        ms.pushPose();
        float rot = entitycannon.xRotO + (entitycannon.getXRot() - entitycannon.xRotO) * f1;
        rot = Math.min(rot, 20.0f);
        f = interpolateRotation(entitycannon.yRotO, entitycannon.getYRot(), f1);
        ms.translate(0, 2.375f, 0);
        ms.mulPose(Axis.YP.rotationDegrees(180.0f - f));
        float f2 = entitycannon.getHurtTime() - f1;
        float f3 = entitycannon.getCurrentDamage() - f1;
        if (f3 < 0.0f) {
            f3 = 0.0f;
        }
        if (f2 > 0.0f) {
            ms.mulPose(Axis.ZP.rotationDegrees(Mth.sin(f2) * f2 * f3 / 10.0f * entitycannon.getRockDirection() / 5.0f));
        }
        VertexConsumer builder = bufs.getBuffer(RenderType.entityCutout(getTextureLocation(entitycannon)));
        ms.scale(-1.6f, -1.6f, 1.6f);
        int color = 0xFFCCCCCC;
        if (entitycannon.isSuperPowered() && entitycannon.tickCount % 5 < 2) color = 0xFFFFFFFF;
        ms.pushPose();
        ms.translate(0.0f, 1.0f, 0.0f);
        ms.mulPose(Axis.XP.rotationDegrees(rot));
        ms.translate(0.0f, -1.0f, 0.0f);
        modelBarrel.renderToBuffer(ms, builder, lm, OverlayTexture.NO_OVERLAY, color);
        ms.popPose();
        float yawRadians = -(float) Math.toRadians(f);
        modelStandard.base1.yRot = yawRadians;
        modelStandard.base2.yRot = yawRadians;
        modelStandard.baseStand.yRot = yawRadians;
        modelStandard.renderToBuffer(ms, builder, lm, OverlayTexture.NO_OVERLAY, color);
        ms.popPose();
        super.render(entitycannon, f, f1, ms, bufs, lm);
    }

    private float interpolateRotation(float from, float to, float by) {
        return (from + Mth.wrapDegrees(to - from) * by) % 360.0f;
    }

    @Override
    @NotNull
    public ResourceLocation getTextureLocation(@NotNull EntityCannon entity) {
        return WeaponModResources.Entity.CANNON;
    }

}
