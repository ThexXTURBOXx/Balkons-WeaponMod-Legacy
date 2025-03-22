package ckathode.weaponmod.render;

import ckathode.weaponmod.BalkonsWeaponMod;
import ckathode.weaponmod.WeaponModResources;
import ckathode.weaponmod.entity.EntityCannon;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

public class RenderCannon extends WMRenderer<EntityCannon> {

    private final ModelCannonBarrel modelBarrel = new ModelCannonBarrel();
    private final ModelCannonStandard modelStandard = new ModelCannonStandard();
    private final ModelCannonLegacy modelLegacy = new ModelCannonLegacy();

    public RenderCannon(EntityRendererManager renderManager) {
        super(renderManager);
        shadowSize = 0.7f;
    }

    @Override
    @ParametersAreNonnullByDefault
    public void render(EntityCannon entitycannon, float f, float f1,
                       MatrixStack ms, IRenderTypeBuffer bufs, int lm) {
        ms.push();
        if (BalkonsWeaponMod.instance.modConfig.legacyCannonModel.get()) {
            modelLegacy.barrel.rotateAngleX = Math.max(-entitycannon.rotationPitch / 120.0f, -0.25f);
            ms.translate(0, 0.1, 0);
            ms.rotate(Vector3f.YP.rotationDegrees(-f));
            final float f3 = entitycannon.getTimeSinceHit() - f1;
            float f4 = entitycannon.getCurrentDamage() - f1;
            if (f4 < 0.0f) {
                f4 = 0.0f;
            }
            if (f3 > 0.0f) {
                ms.rotate(Vector3f.ZP.rotationDegrees(MathHelper.sin(f3) * f3 * f4 / 10.0f * entitycannon.getRockDirection() / 5.0f));
            }
            IVertexBuilder builder = bufs.getBuffer(RenderType.getEntityCutout(getEntityTexture(entitycannon)));
            ms.scale(-1.0f, -1.0f, 1.0f);
            ms.rotate(Vector3f.XP.rotationDegrees(180.0f));
            float f5 = 1f;
            if (entitycannon.isSuperPowered() && entitycannon.ticksExisted % 5 < 2) f5 = 1.5f;
            modelLegacy.render(ms, builder, lm, OverlayTexture.NO_OVERLAY,
                    entitycannon.getBrightness() * f5, entitycannon.getBrightness() * f5,
                    entitycannon.getBrightness() * f5, 1);
        } else {
            float rot =
                    entitycannon.prevRotationPitch + (entitycannon.rotationPitch - entitycannon.prevRotationPitch) * f1;
            rot = Math.min(rot, 20.0f);
            f = interpolateRotation(entitycannon.prevRotationYaw, entitycannon.rotationYaw, f1);
            ms.translate(0, 2.375f, 0);
            ms.rotate(Vector3f.YP.rotationDegrees(180.0f - f));
            float f2 = entitycannon.getTimeSinceHit() - f1;
            float f3 = entitycannon.getCurrentDamage() - f1;
            if (f3 < 0.0f) {
                f3 = 0.0f;
            }
            if (f2 > 0.0f) {
                ms.rotate(Vector3f.ZP.rotationDegrees(MathHelper.sin(f2) * f2 * f3 / 10.0f * entitycannon.getRockDirection() / 5.0f));
            }
            IVertexBuilder builder = bufs.getBuffer(RenderType.getEntityCutout(getEntityTexture(entitycannon)));
            ms.scale(-1.6f, -1.6f, 1.6f);
            float f4 = 1f;
            if (entitycannon.isSuperPowered() && entitycannon.ticksExisted % 5 < 2) f4 = 1.5f;
            ms.push();
            ms.translate(0.0f, 1.0f, 0.0f);
            ms.rotate(Vector3f.XP.rotationDegrees(rot));
            ms.translate(0.0f, -1.0f, 0.0f);
            modelBarrel.render(ms, builder, lm, OverlayTexture.NO_OVERLAY,
                    entitycannon.getBrightness() * f4, entitycannon.getBrightness() * f4,
                    entitycannon.getBrightness() * f4, 1);
            ms.pop();
            float yawRadians = -(float) Math.toRadians(f);
            modelStandard.base1.rotateAngleY = yawRadians;
            modelStandard.base2.rotateAngleY = yawRadians;
            modelStandard.baseStand.rotateAngleY = yawRadians;
            modelStandard.render(ms, builder, lm, OverlayTexture.NO_OVERLAY,
                    entitycannon.getBrightness() * f4, entitycannon.getBrightness() * f4,
                    entitycannon.getBrightness() * f4, 1);
        }
        ms.pop();
        super.render(entitycannon, f, f1, ms, bufs, lm);
    }

    private float interpolateRotation(float from, float to, float by) {
        return (from + MathHelper.wrapDegrees(to - from) * by) % 360.0f;
    }

    @Override
    @Nonnull
    @ParametersAreNonnullByDefault
    public ResourceLocation getEntityTexture(@Nonnull EntityCannon entity) {
        return BalkonsWeaponMod.instance.modConfig.legacyCannonModel.get()
                ? WeaponModResources.Entity.CANNON_LEGACY
                : WeaponModResources.Entity.CANNON;
    }

}
