package ckathode.weaponmod.render;

import ckathode.weaponmod.WeaponModResources;
import ckathode.weaponmod.entity.EntityDummy;
import javax.annotation.Nonnull;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

public class RenderDummy extends Render<EntityDummy> {
    private final ModelDummy modelDummy;

    public RenderDummy(RenderManager renderManager) {
        super(renderManager);
        modelDummy = new ModelDummy();
        shadowSize = 1.0f;
    }

    @Override
    public void doRender(EntityDummy entitydummy, double d, double d1, double d2,
                         float f, float f1) {
        GlStateManager.pushMatrix();
        GlStateManager.translate(d, d1 - 0.025f, d2);
        GlStateManager.rotate(180.0f - f, 0.0f, 1.0f, 0.0f);
        float f2 = entitydummy.getTimeSinceHit() - f1;
        float f3 = entitydummy.getCurrentDamage() - f1;
        if (f3 < 0.0f) {
            f3 = 0.0f;
        }
        if (f2 > 0.0f) {
            GlStateManager.rotate(MathHelper.sin(f2) * f2 * f3 / 10.0f * entitydummy.getRockDirection() / 5.0f, 0.0f,
                    0.0f, 1.0f);
        }
        bindEntityTexture(entitydummy);
        GlStateManager.scale(-1.0f, -1.0f, 1.0f);
        GlStateManager.rotate(180.0f, 1.0f, 0.0f, 0.0f);
        modelDummy.render(entitydummy, f1, 0.0f, -0.1f, 0.0f, 0.0f, 0.0625f);
        GlStateManager.popMatrix();
        super.doRender(entitydummy, d, d1, d2, f, f1);
    }

    @Override
    protected ResourceLocation getEntityTexture(@Nonnull EntityDummy entity) {
        return WeaponModResources.Entity.DUMMY;
    }
}
