package ckathode.weaponmod.render;

import ckathode.weaponmod.WeaponModResources;
import ckathode.weaponmod.entity.EntityDummy;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class RenderDummy extends Render {
    private final ModelDummy modelDummy;

    public RenderDummy() {
        modelDummy = new ModelDummy();
        shadowSize = 1.0f;
    }

    @Override
    public void doRender(Entity entity, double d, double d1, double d2, float f, float f1) {
        renderDummy((EntityDummy) entity, d, d1, d2, f, f1);
    }

    public void renderDummy(EntityDummy entitydummy, double d, double d1, double d2, float f, float f1) {
        GL11.glPushMatrix();
        GL11.glTranslated(d, d1 - 0.025f, d2);
        GL11.glRotatef(180.0f - f, 0.0f, 1.0f, 0.0f);
        float f2 = entitydummy.getTimeSinceHit() - f1;
        float f3 = entitydummy.getCurrentDamage() - f1;
        if (f3 < 0.0f) {
            f3 = 0.0f;
        }
        if (f2 > 0.0f) {
            GL11.glRotatef(MathHelper.sin(f2) * f2 * f3 / 10.0f * entitydummy.getRockDirection() / 5.0f, 0.0f,
                    0.0f, 1.0f);
        }
        bindEntityTexture(entitydummy);
        GL11.glScalef(-1.0f, -1.0f, 1.0f);
        GL11.glRotatef(180.0f, 1.0f, 0.0f, 0.0f);
        modelDummy.render(entitydummy, f1, 0.0f, -0.1f, 0.0f, 0.0f, 0.0625f);
        GL11.glPopMatrix();
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity entity) {
        return WeaponModResources.Entity.DUMMY;
    }
}
