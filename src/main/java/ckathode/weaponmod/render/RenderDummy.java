package ckathode.weaponmod.render;

import ckathode.weaponmod.WeaponModResources;
import ckathode.weaponmod.entity.EntityDummy;
import javax.annotation.Nonnull;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

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
        GlStateManager.translated(d, d1 - 0.025f, d2);
        GlStateManager.rotatef(180.0f - f, 0.0f, 1.0f, 0.0f);
        float f2 = entitydummy.getTimeSinceHit() - f1;
        float f3 = entitydummy.getCurrentDamage() - f1;
        if (f3 < 0.0f) {
            f3 = 0.0f;
        }
        if (f2 > 0.0f) {
            GlStateManager.rotatef(MathHelper.sin(f2) * f2 * f3 / 10.0f * entitydummy.getRockDirection() / 5.0f, 0.0f,
                    0.0f, 1.0f);
        }
        bindEntityTexture(entitydummy);
        GlStateManager.scalef(-1.0f, -1.0f, 1.0f);
        GlStateManager.rotatef(180.0f, 1.0f, 0.0f, 0.0f);
        if (renderOutlines) {
            GlStateManager.enableColorMaterial();
            GlStateManager.enableOutlineMode(getTeamColor(entitydummy));
        }
        modelDummy.render(entitydummy, f1, 0.0f, -0.1f, 0.0f, 0.0f, 0.0625f);
        if (renderOutlines) {
            GlStateManager.disableOutlineMode();
            GlStateManager.disableColorMaterial();
        }
        GlStateManager.popMatrix();
        super.doRender(entitydummy, d, d1, d2, f, f1);
    }

    @Override
    protected ResourceLocation getEntityTexture(@Nonnull EntityDummy entity) {
        return WeaponModResources.Entity.DUMMY;
    }
}
