package ckathode.weaponmod.render;

import ckathode.weaponmod.BalkonsWeaponMod;
import ckathode.weaponmod.WeaponModResources;
import ckathode.weaponmod.entity.EntityCannon;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class RenderCannon extends Render {
    private final ModelCannonBarrel modelBarrel = new ModelCannonBarrel();
    private final ModelCannonStandard modelStandard = new ModelCannonStandard();
    private final ModelCannonLegacy modelLegacy = new ModelCannonLegacy();

    public RenderCannon() {
        shadowSize = 0.7f;
    }

    @Override
    public void doRender(Entity entity, double x, double y, double z, float yaw, float partialTicks) {
        renderCannon((EntityCannon) entity, x, y, z, yaw, partialTicks);
    }

    public void renderCannon(EntityCannon entitycannon, double x, double y, double z, float yaw, float partialTicks) {
        GL11.glPushMatrix();
        if (BalkonsWeaponMod.instance.modConfig.legacyCannonModel) {
            modelLegacy.barrel.rotateAngleX = Math.max(-entitycannon.rotationPitch / 120.0f, -0.25f);
            GL11.glTranslated(x, y - 0.2, z);
            GL11.glRotatef(-yaw, 0.0f, 1.0f, 0.0f);
            final float f3 = entitycannon.getTimeSinceHit() - partialTicks;
            float f4 = entitycannon.getCurrentDamage() - partialTicks;
            if (f4 < 0.0f) {
                f4 = 0.0f;
            }
            if (f3 > 0.0f) {
                GL11.glRotatef(MathHelper.sin(f3) * f3 * f4 / 10.0f * entitycannon.getRockDirection() / 5.0f, 0.0f,
                        0.0f, 1.0f);
            }
            bindEntityTexture(entitycannon);
            GL11.glScalef(-1.0f, -1.0f, 1.0f);
            GL11.glRotatef(180.0f, 1.0f, 0.0f, 0.0f);
            if (entitycannon.isSuperPowered() && entitycannon.ticksExisted % 5 < 2) {
                float f5 = 1.5f;
                GL11.glColor3f(entitycannon.getBrightness(partialTicks) * f5,
                        entitycannon.getBrightness(partialTicks) * f5,
                        entitycannon.getBrightness(partialTicks) * f5);
            }
            modelLegacy.render(entitycannon, 0.0f, 0.0f, -0.1f, 0.0f, 0.0f, 0.0625f);
        } else {
            float rot =
                    entitycannon.prevRotationPitch + (entitycannon.rotationPitch - entitycannon.prevRotationPitch) * partialTicks;
            rot = Math.min(rot, 20.0f);
            yaw = interpolateRotation(entitycannon.prevRotationYaw, entitycannon.rotationYaw, partialTicks);
            GL11.glTranslated(x, y + 2.1, z);
            GL11.glRotatef(180.0f - yaw, 0.0f, 1.0f, 0.0f);
            float f2 = entitycannon.getTimeSinceHit() - partialTicks;
            float f3 = entitycannon.getCurrentDamage() - partialTicks;
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
                GL11.glColor3f(entitycannon.getBrightness(partialTicks) * f4,
                        entitycannon.getBrightness(partialTicks) * f4,
                        entitycannon.getBrightness(partialTicks) * f4);
            }
            GL11.glPushMatrix();
            GL11.glTranslatef(0.0f, 1.0f, 0.0f);
            GL11.glRotatef(rot, 1.0f, 0.0f, 0.0f);
            GL11.glTranslatef(0.0f, -1.0f, 0.0f);
            modelBarrel.render(entitycannon, partialTicks, 0.0f, -0.1f, 0.0f, 0.0f, 0.0625f);
            GL11.glPopMatrix();
            float yawRadians = -(float) Math.toRadians(yaw);
            modelStandard.base1.rotateAngleY = yawRadians;
            modelStandard.base2.rotateAngleY = yawRadians;
            modelStandard.baseStand.rotateAngleY = yawRadians;
            modelStandard.render(entitycannon, partialTicks, 0.0f, -0.1f, 0.0f, 0.0f, 0.0625f);
        }
        GL11.glPopMatrix();
    }

    private float interpolateRotation(float from, float to, float by) {
        return (from + MathHelper.wrapAngleTo180_float(to - from) * by) % 360.0f;
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity entity) {
        return BalkonsWeaponMod.instance.modConfig.legacyCannonModel
                ? WeaponModResources.Entity.CANNON_LEGACY
                : WeaponModResources.Entity.CANNON;
    }
}
