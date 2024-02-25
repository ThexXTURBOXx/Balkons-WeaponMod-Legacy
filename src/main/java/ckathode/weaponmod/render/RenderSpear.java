package ckathode.weaponmod.render;

import ckathode.weaponmod.BalkonsWeaponMod;
import ckathode.weaponmod.WeaponModResources;
import ckathode.weaponmod.entity.projectile.EntitySpear;
import javax.annotation.Nonnull;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

public class RenderSpear extends Render<EntitySpear> {
    public RenderSpear(final RenderManager renderManager) {
        super(renderManager);
    }

    @Override
    public void doRender(@Nonnull final EntitySpear entityspear, final double d, final double d1, final double d2,
                         final float f, final float f1) {
        if (!BalkonsWeaponMod.instance.modConfig.itemModelForEntity.get()) {
            this.bindEntityTexture(entityspear);
            GlStateManager.color4f(1.0f, 1.0f, 1.0f, 1.0f);
            GlStateManager.pushMatrix();
            GlStateManager.disableLighting();
            GlStateManager.translated(d, d1, d2);
            GlStateManager.rotatef(entityspear.prevRotationYaw + (entityspear.rotationYaw - entityspear.prevRotationYaw) * f1 - 90.0f, 0.0f, 1.0f, 0.0f);
            GlStateManager.rotatef(entityspear.prevRotationPitch + (entityspear.rotationPitch - entityspear.prevRotationPitch) * f1, 0.0f, 0.0f, 1.0f);
            final Tessellator tessellator = Tessellator.getInstance();
            final BufferBuilder vertexbuffer = tessellator.getBuffer();
            final float[] color = entityspear.getMaterialColor();
            final int i = 0;
            final float f2 = 0.0f;
            final float f3 = 1.0f;
            final float f4 = 0.0f;
            final float f5 = 0.15625f;
            final float f6 = 0.0f;
            final float f7 = 0.15625f;
            final float f8 = 0.15625f;
            final float f9 = 0.3125f;
            final float f10 = 0.3125f;
            final float f11 = 0.46875f;
            final float f12 = 0.05625f;
            final double length = 20.0;
            GlStateManager.enableRescaleNormal();
            final float f13 = entityspear.arrowShake - f1;
            if (f13 > 0.0f) {
                final float f14 = -MathHelper.sin(f13 * 3.0f) * f13;
                GlStateManager.rotatef(f14, 0.0f, 0.0f, 1.0f);
            }
            GlStateManager.rotatef(45.0f, 1.0f, 0.0f, 0.0f);
            GlStateManager.scalef(0.05625f, 0.05625f, 0.05625f);
            GlStateManager.translatef(-4.0f, 0.0f, 0.0f);
            if (this.renderOutlines) {
                GlStateManager.enableColorMaterial();
                GlStateManager.enableOutlineMode(this.getTeamColor(entityspear));
            }
            GlStateManager.normal3f(0.05625f, 0.0f, 0.0f);
            vertexbuffer.begin(7, DefaultVertexFormats.POSITION_TEX);
            vertexbuffer.pos(-length, -2.0, -2.0).tex(0.0, 0.15625).endVertex();
            vertexbuffer.pos(-length, -2.0, 2.0).tex(0.15625, 0.15625).endVertex();
            vertexbuffer.pos(-length, 2.0, 2.0).tex(0.15625, 0.3125).endVertex();
            vertexbuffer.pos(-length, 2.0, -2.0).tex(0.0, 0.3125).endVertex();
            tessellator.draw();
            GlStateManager.normal3f(-0.05625f, 0.0f, 0.0f);
            vertexbuffer.begin(7, DefaultVertexFormats.POSITION_TEX);
            vertexbuffer.pos(-length, 2.0, -2.0).tex(0.0, 0.15625).endVertex();
            vertexbuffer.pos(-length, 2.0, 2.0).tex(0.15625, 0.15625).endVertex();
            vertexbuffer.pos(-length, -2.0, 2.0).tex(0.15625, 0.3125).endVertex();
            vertexbuffer.pos(-length, -2.0, -2.0).tex(0.0, 0.3125).endVertex();
            tessellator.draw();
            for (int j = 0; j < 4; ++j) {
                GlStateManager.rotatef(90.0f, 1.0f, 0.0f, 0.0f);
                GlStateManager.normal3f(0.0f, 0.0f, 0.05625f);
                vertexbuffer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
                vertexbuffer.pos(-length, -2.0, 0.0).tex(0.0, 0.0).color(1.0f, 1.0f, 1.0f, 1.0f).endVertex();
                vertexbuffer.pos(length, -2.0, 0.0).tex(1.0, 0.0).color(1.0f, 1.0f, 1.0f, 1.0f).endVertex();
                vertexbuffer.pos(length, 2.0, 0.0).tex(1.0, 0.15625).color(1.0f, 1.0f, 1.0f, 1.0f).endVertex();
                vertexbuffer.pos(-length, 2.0, 0.0).tex(0.0, 0.15625).color(1.0f, 1.0f, 1.0f, 1.0f).endVertex();
                vertexbuffer.pos(-length, -2.0, 0.0).tex(0.0, 0.3125).color(color[0], color[1], color[2], 1.0f).endVertex();
                vertexbuffer.pos(length, -2.0, 0.0).tex(1.0, 0.3125).color(color[0], color[1], color[2], 1.0f).endVertex();
                vertexbuffer.pos(length, 2.0, 0.0).tex(1.0, 0.46875).color(color[0], color[1], color[2], 1.0f).endVertex();
                vertexbuffer.pos(-length, 2.0, 0.0).tex(0.0, 0.46875).color(color[0], color[1], color[2], 1.0f).endVertex();
                tessellator.draw();
            }
            if (this.renderOutlines) {
                GlStateManager.disableOutlineMode();
                GlStateManager.disableColorMaterial();
            }
            GlStateManager.disableRescaleNormal();
            GlStateManager.enableLighting();
            GlStateManager.popMatrix();
        } else {
            final ItemRenderer itemRender = Minecraft.getInstance().getItemRenderer();
            GlStateManager.pushMatrix();
            this.bindEntityTexture(entityspear);
            GlStateManager.translated(d, d1, d2);
            GlStateManager.enableRescaleNormal();
            GlStateManager.scalef(1.7f, 1.7f, 1.7f);
            GlStateManager.rotatef(entityspear.prevRotationYaw + (entityspear.rotationYaw - entityspear.prevRotationYaw) * f1 - 90.0f, 0.0f, 1.0f, 0.0f);
            GlStateManager.rotatef(entityspear.prevRotationPitch + (entityspear.rotationPitch - entityspear.prevRotationPitch) * f1 - 45.0f, 0.0f, 0.0f, 1.0f);
            final float f15 = entityspear.arrowShake - f1;
            if (f15 > 0.0f) {
                final float f16 = -MathHelper.sin(f15 * 3.0f) * f15;
                GlStateManager.rotatef(f16, 0.0f, 0.0f, 1.0f);
            }
            GlStateManager.translatef(-0.35f, -0.35f, 0.0f);
            if (this.renderOutlines) {
                GlStateManager.enableColorMaterial();
                GlStateManager.enableOutlineMode(this.getTeamColor(entityspear));
            }
            itemRender.renderItem(this.getStackToRender(entityspear), TransformType.NONE);
            if (this.renderOutlines) {
                GlStateManager.disableOutlineMode();
                GlStateManager.disableColorMaterial();
            }
            GlStateManager.disableRescaleNormal();
            GlStateManager.popMatrix();
        }
        super.doRender(entityspear, d, d1, d2, f, f1);
    }

    public ItemStack getStackToRender(final EntitySpear entity) {
        return entity.getWeapon();
    }

    @Override
    protected ResourceLocation getEntityTexture(@Nonnull final EntitySpear entity) {
        return WeaponModResources.Textures.SPEAR;
    }
}
