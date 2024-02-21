package ckathode.weaponmod.render;

import ckathode.weaponmod.entity.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.renderer.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import ckathode.weaponmod.*;

public class RenderCannon extends Render<EntityCannon>
{
    protected ModelCannon modelCannon;
    protected ModelCannonBarrel modelBarrel;
    protected ModelCannonStandard modelStandard;
    
    public RenderCannon(final RenderManager renderManager) {
        super(renderManager);
        this.modelCannon = new ModelCannon();
        this.modelBarrel = new ModelCannonBarrel();
        this.modelStandard = new ModelCannonStandard();
        this.shadowSize = 1.0f;
    }
    
    public void doRender(final EntityCannon entitycannon, final double d, final double d1, final double d2, final float f, final float f1) {
        GlStateManager.pushMatrix();
        float rot = entitycannon.prevRotationPitch + (entitycannon.rotationPitch - entitycannon.prevRotationPitch) * f1;
        rot = Math.min(rot, 20.0f);
        GlStateManager.translate((float)d, (float)d1 + 2.375f, (float)d2);
        GlStateManager.rotate(180.0f - f, 0.0f, 1.0f, 0.0f);
        final float f2 = entitycannon.getTimeSinceHit() - f1;
        float f3 = entitycannon.getCurrentDamage() - f1;
        if (f3 < 0.0f) {
            f3 = 0.0f;
        }
        if (f2 > 0.0f) {
            GlStateManager.rotate(MathHelper.sin(f2) * f2 * f3 / 10.0f * entitycannon.getRockDirection() / 5.0f, 0.0f, 0.0f, 1.0f);
        }
        this.bindEntityTexture(entitycannon);
        GlStateManager.scale(-1.6f, -1.6f, 1.6f);
        if (entitycannon.isSuperPowered() && entitycannon.ticksExisted % 5 < 2) {
            final float f4 = 1.5f;
            GlStateManager.color(entitycannon.getBrightness() * f4, entitycannon.getBrightness() * f4, entitycannon.getBrightness() * f4);
        }
        if (this.renderOutlines) {
            GlStateManager.disableOutlineMode();
            GlStateManager.disableColorMaterial();
        }
        GlStateManager.pushMatrix();
        GlStateManager.translate(0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(rot, 1.0f, 0.0f, 0.0f);
        GlStateManager.translate(0.0f, -1.0f, 0.0f);
        this.modelBarrel.render(entitycannon, f1, 0.0f, -0.1f, 0.0f, 0.0f, 0.0625f);
        GlStateManager.popMatrix();
        final float yaw = -(float)Math.toRadians(f);
        this.modelStandard.base_1.rotateAngleY = yaw;
        this.modelStandard.base_2.rotateAngleY = yaw;
        this.modelStandard.base_stand.rotateAngleY = yaw;
        this.modelStandard.render(entitycannon, f1, 0.0f, -0.1f, 0.0f, 0.0f, 0.0625f);
        if (this.renderOutlines) {
            GlStateManager.enableColorMaterial();
            GlStateManager.enableOutlineMode(this.getTeamColor(entitycannon));
        }
        GlStateManager.popMatrix();
        super.doRender(entitycannon, d, d1, d2, f, f1);
    }
    
    protected ResourceLocation getEntityTexture(final EntityCannon entity) {
        return WeaponModResources.Textures.CANNON;
    }
}
