package ckathode.weaponmod.render;

import ckathode.weaponmod.BalkonsWeaponMod;
import ckathode.weaponmod.WeaponModResources;
import ckathode.weaponmod.entity.projectile.EntitySpear;
import com.mojang.blaze3d.platform.GlStateManager;
import javax.annotation.Nonnull;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

public class RenderSpear extends EntityRenderer<EntitySpear> {
    public RenderSpear(EntityRendererManager renderManager) {
        super(renderManager);
    }

    @Override
    public void doRender(@Nonnull EntitySpear entityspear, double d, double d1, double d2,
                         float f, float f1) {
        if (!BalkonsWeaponMod.instance.modConfig.itemModelForEntity.get()) {
            bindEntityTexture(entityspear);
            GlStateManager.color4f(1.0f, 1.0f, 1.0f, 1.0f);
            GlStateManager.pushMatrix();
            GlStateManager.disableLighting();
            GlStateManager.translated(d, d1, d2);
            GlStateManager.rotatef(entityspear.prevRotationYaw + (entityspear.rotationYaw - entityspear.prevRotationYaw) * f1 - 90.0f, 0.0f, 1.0f, 0.0f);
            GlStateManager.rotatef(entityspear.prevRotationPitch + (entityspear.rotationPitch - entityspear.prevRotationPitch) * f1, 0.0f, 0.0f, 1.0f);
            Tessellator tessellator = Tessellator.getInstance();
            BufferBuilder vertexbuffer = tessellator.getBuffer();
            float[] color = entityspear.getMaterialColor();
            double length = 20.0;
            GlStateManager.enableRescaleNormal();
            float f13 = entityspear.arrowShake - f1;
            if (f13 > 0.0f) {
                float f14 = -MathHelper.sin(f13 * 3.0f) * f13;
                GlStateManager.rotatef(f14, 0.0f, 0.0f, 1.0f);
            }
            GlStateManager.rotatef(45.0f, 1.0f, 0.0f, 0.0f);
            GlStateManager.scalef(0.05625f, 0.05625f, 0.05625f);
            GlStateManager.translatef(-4.0f, 0.0f, 0.0f);
            if (renderOutlines) {
                GlStateManager.enableColorMaterial();
                GlStateManager.setupSolidRenderingTextureCombine(getTeamColor(entityspear));
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
            if (renderOutlines) {
                GlStateManager.tearDownSolidRenderingTextureCombine();
                GlStateManager.disableColorMaterial();
            }
            GlStateManager.disableRescaleNormal();
            GlStateManager.enableLighting();
            GlStateManager.popMatrix();
        } else {
            ItemRenderer itemRender = Minecraft.getInstance().getItemRenderer();
            GlStateManager.pushMatrix();
            bindEntityTexture(entityspear);
            GlStateManager.translated(d, d1, d2);
            GlStateManager.enableRescaleNormal();
            GlStateManager.scalef(1.7f, 1.7f, 1.7f);
            GlStateManager.rotatef(entityspear.prevRotationYaw + (entityspear.rotationYaw - entityspear.prevRotationYaw) * f1 - 90.0f, 0.0f, 1.0f, 0.0f);
            GlStateManager.rotatef(entityspear.prevRotationPitch + (entityspear.rotationPitch - entityspear.prevRotationPitch) * f1 - 45.0f, 0.0f, 0.0f, 1.0f);
            float f15 = entityspear.arrowShake - f1;
            if (f15 > 0.0f) {
                float f16 = -MathHelper.sin(f15 * 3.0f) * f15;
                GlStateManager.rotatef(f16, 0.0f, 0.0f, 1.0f);
            }
            GlStateManager.translatef(-0.35f, -0.35f, 0.0f);
            if (renderOutlines) {
                GlStateManager.enableColorMaterial();
                GlStateManager.setupSolidRenderingTextureCombine(getTeamColor(entityspear));
            }
            itemRender.renderItem(getStackToRender(entityspear), TransformType.NONE);
            if (renderOutlines) {
                GlStateManager.tearDownSolidRenderingTextureCombine();
                GlStateManager.disableColorMaterial();
            }
            GlStateManager.disableRescaleNormal();
            GlStateManager.popMatrix();
        }
        super.doRender(entityspear, d, d1, d2, f, f1);
    }

    public ItemStack getStackToRender(EntitySpear entity) {
        return entity.getWeapon();
    }

    @Override
    protected ResourceLocation getEntityTexture(@Nonnull EntitySpear entity) {
        return WeaponModResources.Entity.SPEAR;
    }
}
