package ckathode.weaponmod.render;

import ckathode.weaponmod.BalkonsWeaponMod;
import ckathode.weaponmod.WeaponModResources;
import ckathode.weaponmod.entity.projectile.EntityJavelin;
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

public class RenderJavelin extends Render<EntityJavelin> {
    public RenderJavelin(RenderManager renderManager) {
        super(renderManager);
    }

    @Override
    public void doRender(@Nonnull EntityJavelin entityjavelin, double d, double d1, double d2
            , float f, float f1) {
        if (!BalkonsWeaponMod.instance.modConfig.itemModelForEntity.get()) {
            bindEntityTexture(entityjavelin);
            GlStateManager.color4f(1.0f, 1.0f, 1.0f, 1.0f);
            GlStateManager.pushMatrix();
            GlStateManager.disableLighting();
            GlStateManager.translated(d, d1, d2);
            GlStateManager.rotatef(entityjavelin.prevRotationYaw + (entityjavelin.rotationYaw - entityjavelin.prevRotationYaw) * f1 - 90.0f, 0.0f, 1.0f, 0.0f);
            GlStateManager.rotatef(entityjavelin.prevRotationPitch + (entityjavelin.rotationPitch - entityjavelin.prevRotationPitch) * f1, 0.0f, 0.0f, 1.0f);
            Tessellator tessellator = Tessellator.getInstance();
            BufferBuilder vertexbuffer = tessellator.getBuffer();
            int i = 0;
            float f2 = 0.0f;
            float f3 = 1.0f;
            float f4 = 0.0f;
            float f5 = 0.15625f;
            float f6 = 0.0f;
            float f7 = 0.15625f;
            float f8 = 0.15625f;
            float f9 = 0.3125f;
            float f10 = 0.05625f;
            double length = 20.0;
            GlStateManager.enableRescaleNormal();
            float f11 = entityjavelin.arrowShake - f1;
            if (f11 > 0.0f) {
                float f12 = -MathHelper.sin(f11 * 3.0f) * f11;
                GlStateManager.rotatef(f12, 0.0f, 0.0f, 1.0f);
            }
            GlStateManager.rotatef(45.0f, 1.0f, 0.0f, 0.0f);
            GlStateManager.scalef(0.05625f, 0.05625f, 0.05625f);
            GlStateManager.translatef(-4.0f, 0.0f, 0.0f);
            if (renderOutlines) {
                GlStateManager.enableColorMaterial();
                GlStateManager.enableOutlineMode(getTeamColor(entityjavelin));
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
                vertexbuffer.begin(7, DefaultVertexFormats.POSITION_TEX);
                vertexbuffer.pos(-length, -2.0, 0.0).tex(0.0, 0.0).endVertex();
                vertexbuffer.pos(length, -2.0, 0.0).tex(1.0, 0.0).endVertex();
                vertexbuffer.pos(length, 2.0, 0.0).tex(1.0, 0.15625).endVertex();
                vertexbuffer.pos(-length, 2.0, 0.0).tex(0.0, 0.15625).endVertex();
                tessellator.draw();
            }
            if (renderOutlines) {
                GlStateManager.disableOutlineMode();
                GlStateManager.disableColorMaterial();
            }
            GlStateManager.disableRescaleNormal();
            GlStateManager.enableLighting();
            GlStateManager.popMatrix();
        } else {
            ItemRenderer itemRender = Minecraft.getInstance().getItemRenderer();
            GlStateManager.pushMatrix();
            bindEntityTexture(entityjavelin);
            GlStateManager.translated(d, d1, d2);
            GlStateManager.enableRescaleNormal();
            GlStateManager.scalef(1.7f, 1.7f, 1.7f);
            GlStateManager.rotatef(entityjavelin.prevRotationYaw + (entityjavelin.rotationYaw - entityjavelin.prevRotationYaw) * f1 - 90.0f, 0.0f, 1.0f, 0.0f);
            GlStateManager.rotatef(entityjavelin.prevRotationPitch + (entityjavelin.rotationPitch - entityjavelin.prevRotationPitch) * f1 - 45.0f, 0.0f, 0.0f, 1.0f);
            float f13 = entityjavelin.arrowShake - f1;
            if (f13 > 0.0f) {
                float f14 = -MathHelper.sin(f13 * 3.0f) * f13;
                GlStateManager.rotatef(f14, 0.0f, 0.0f, 1.0f);
            }
            GlStateManager.translatef(-0.25f, -0.25f, 0.0f);
            GlStateManager.rotatef(180.0f, 0.0f, 1.0f, 0.0f);
            if (renderOutlines) {
                GlStateManager.enableColorMaterial();
                GlStateManager.enableOutlineMode(getTeamColor(entityjavelin));
            }
            itemRender.renderItem(getStackToRender(entityjavelin), TransformType.NONE);
            if (renderOutlines) {
                GlStateManager.disableOutlineMode();
                GlStateManager.disableColorMaterial();
            }
            GlStateManager.disableRescaleNormal();
            GlStateManager.popMatrix();
        }
        super.doRender(entityjavelin, d, d1, d2, f, f1);
    }

    public ItemStack getStackToRender(EntityJavelin entity) {
        return new ItemStack(BalkonsWeaponMod.javelin);
    }

    @Override
    protected ResourceLocation getEntityTexture(@Nonnull EntityJavelin entity) {
        return WeaponModResources.Textures.JAVELIN;
    }
}
