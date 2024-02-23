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

    public RenderDummy(final RenderManager renderManager) {
        super(renderManager);
        this.modelDummy = new ModelDummy();
        this.shadowSize = 1.0f;
    }

    @Override
    public void doRender(final EntityDummy entitydummy, final double d, final double d1, final double d2,
                         final float f, final float f1) {
        GlStateManager.pushMatrix();
        GlStateManager.translate((float) d, (float) d1 - 0.025f, (float) d2);
        GlStateManager.rotate(180.0f - f, 0.0f, 1.0f, 0.0f);
        final float f2 = entitydummy.getTimeSinceHit() - f1;
        float f3 = entitydummy.getCurrentDamage() - f1;
        if (f3 < 0.0f) {
            f3 = 0.0f;
        }
        if (f2 > 0.0f) {
            GlStateManager.rotate(MathHelper.sin(f2) * f2 * f3 / 10.0f * entitydummy.getRockDirection() / 5.0f, 0.0f,
                    0.0f, 1.0f);
        }
        this.bindEntityTexture(entitydummy);
        GlStateManager.scale(-1.0f, -1.0f, 1.0f);
        GlStateManager.rotate(180.0f, 1.0f, 0.0f, 0.0f);
        if (this.renderOutlines) {
            GlStateManager.enableColorMaterial();
            GlStateManager.enableOutlineMode(this.getTeamColor(entitydummy));
        }
        this.modelDummy.render(entitydummy, f1, 0.0f, -0.1f, 0.0f, 0.0f, 0.0625f);
        if (this.renderOutlines) {
            GlStateManager.disableOutlineMode();
            GlStateManager.disableColorMaterial();
        }
        GlStateManager.popMatrix();
        super.doRender(entitydummy, d, d1, d2, f, f1);
    }

    @Override
    protected ResourceLocation getEntityTexture(@Nonnull final EntityDummy entity) {
        return WeaponModResources.Textures.DUMMY;
    }
}
