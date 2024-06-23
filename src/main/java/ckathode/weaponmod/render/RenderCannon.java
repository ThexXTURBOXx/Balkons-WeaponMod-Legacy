package ckathode.weaponmod.render;

import ckathode.weaponmod.WeaponModResources;
import ckathode.weaponmod.entity.EntityCannon;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class RenderCannon extends Render {
    private final ModelCannonBarrel modelBarrel;
    private final ModelCannonStandard modelStandard;

    public RenderCannon() {
        modelBarrel = new ModelCannonBarrel();
        modelStandard = new ModelCannonStandard();
        shadowSize = 1.0f;
    }

    @Override
    public void doRender(Entity entity, double d, double d1, double d2, float f, float f1) {
        renderCannon((EntityCannon) entity, d, d1, d2, f, f1);
    }

    public void renderCannon(EntityCannon entitycannon, double d, double d1, double d2, float f, float f1) {
        GL11.glPushMatrix();
        float rot = entitycannon.prevRotationPitch + (entitycannon.rotationPitch - entitycannon.prevRotationPitch) * f1;
        rot = Math.min(rot, 20.0f);
        GL11.glTranslated(d, d1 + 2.1, d2);
        GL11.glRotatef(180.0f - f, 0.0f, 1.0f, 0.0f);
        float f2 = entitycannon.getTimeSinceHit() - f1;
        float f3 = entitycannon.getCurrentDamage() - f1;
        if (f3 < 0.0f) {
            f3 = 0.0f;
        }
        if (f2 > 0.0f) {
            GL11.glRotatef(MathHelper.sin(f2) * f2 * f3 / 10.0f * entitycannon.getRockDirection() / 5.0f,
                    0.0f, 0.0f, 1.0f);
        }
        bindEntityTexture(entitycannon);
        GL11.glScalef(-1.6f, -1.6f, 1.6f);
        if (entitycannon.isSuperPowered() && entitycannon.ticksExisted % 5 < 2) {
            float f4 = 1.5f;
            GL11.glColor3f(entitycannon.getBrightness(f1) * f4, entitycannon.getBrightness(f1) * f4,
                    entitycannon.getBrightness(f1) * f4);
        }
        GL11.glPushMatrix();
        GL11.glTranslatef(0.0f, 1.0f, 0.0f);
        GL11.glRotatef(rot, 1.0f, 0.0f, 0.0f);
        GL11.glTranslatef(0.0f, -1.0f, 0.0f);
        modelBarrel.render(entitycannon, f1, 0.0f, -0.1f, 0.0f, 0.0f, 0.0625f);
        GL11.glPopMatrix();
        float yaw = -(float) Math.toRadians(f);
        modelStandard.base1.rotateAngleY = yaw;
        modelStandard.base2.rotateAngleY = yaw;
        modelStandard.baseStand.rotateAngleY = yaw;
        modelStandard.render(entitycannon, f1, 0.0f, -0.1f, 0.0f, 0.0f, 0.0625f);
        GL11.glPopMatrix();
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity entity) {
        return WeaponModResources.Entity.CANNON;
    }
}
