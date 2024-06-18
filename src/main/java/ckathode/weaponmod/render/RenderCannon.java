package ckathode.weaponmod.render;

import ckathode.weaponmod.WeaponModResources;
import ckathode.weaponmod.entity.EntityCannon;
import javax.annotation.Nonnull;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

public class RenderCannon extends Render<EntityCannon> {
    private final ModelCannonBarrel modelBarrel;
    private final ModelCannonStandard modelStandard;

    public RenderCannon(RenderManager renderManager) {
        super(renderManager);
        modelBarrel = new ModelCannonBarrel();
        modelStandard = new ModelCannonStandard();
        shadowSize = 1.0f;
    }

    @Override
    public void doRender(EntityCannon entitycannon, double d, double d1, double d2,
                         float f, float f1) {
        GlStateManager.pushMatrix();
        float rot = entitycannon.prevRotationPitch + (entitycannon.rotationPitch - entitycannon.prevRotationPitch) * f1;
        rot = Math.min(rot, 20.0f);
        GlStateManager.translate(d, d1 + 2.375f, d2);
        GlStateManager.rotate(180.0f - f, 0.0f, 1.0f, 0.0f);
        float f2 = entitycannon.getTimeSinceHit() - f1;
        float f3 = entitycannon.getCurrentDamage() - f1;
        if (f3 < 0.0f) {
            f3 = 0.0f;
        }
        if (f2 > 0.0f) {
            GlStateManager.rotate(MathHelper.sin(f2) * f2 * f3 / 10.0f * entitycannon.getRockDirection() / 5.0f,
                    0.0f, 0.0f, 1.0f);
        }
        bindEntityTexture(entitycannon);
        GlStateManager.scale(-1.6f, -1.6f, 1.6f);
        if (entitycannon.isSuperPowered() && entitycannon.ticksExisted % 5 < 2) {
            float f4 = 1.5f;
            GlStateManager.color(entitycannon.getBrightness(f1) * f4, entitycannon.getBrightness(f1) * f4,
                    entitycannon.getBrightness(f1) * f4);
        }
        if (renderOutlines) {
            GlStateManager.disableOutlineMode();
            GlStateManager.disableColorMaterial();
        }
        GlStateManager.pushMatrix();
        GlStateManager.translate(0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(rot, 1.0f, 0.0f, 0.0f);
        GlStateManager.translate(0.0f, -1.0f, 0.0f);
        modelBarrel.render(entitycannon, f1, 0.0f, -0.1f, 0.0f, 0.0f, 0.0625f);
        GlStateManager.popMatrix();
        float yaw = -(float) Math.toRadians(f);
        modelStandard.base1.rotateAngleY = yaw;
        modelStandard.base2.rotateAngleY = yaw;
        modelStandard.baseStand.rotateAngleY = yaw;
        modelStandard.render(entitycannon, f1, 0.0f, -0.1f, 0.0f, 0.0f, 0.0625f);
        if (renderOutlines) {
            GlStateManager.enableColorMaterial();
            GlStateManager.enableOutlineMode(getTeamColor(entitycannon));
        }
        GlStateManager.popMatrix();
        super.doRender(entitycannon, d, d1, d2, f, f1);
    }

    @Override
    protected ResourceLocation getEntityTexture(@Nonnull EntityCannon entity) {
        return WeaponModResources.Entity.CANNON;
    }
}
