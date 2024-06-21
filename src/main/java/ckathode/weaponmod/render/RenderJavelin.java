package ckathode.weaponmod.render;

import ckathode.weaponmod.BalkonsWeaponMod;
import ckathode.weaponmod.WeaponModResources;
import ckathode.weaponmod.entity.projectile.EntityJavelin;
import javax.annotation.Nonnull;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class RenderJavelin extends Render<EntityJavelin> {
    public RenderJavelin(RenderManager renderManager) {
        super(renderManager);
    }

    @Override
    public void doRender(@Nonnull EntityJavelin entityjavelin, double d, double d1, double d2, float f, float f1) {
        if (!BalkonsWeaponMod.instance.modConfig.itemModelForEntity) {
            bindEntityTexture(entityjavelin);
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            GlStateManager.pushMatrix();
            GlStateManager.disableLighting();
            GlStateManager.translate(d, d1, d2);
            GlStateManager.rotate(entityjavelin.prevRotationYaw + (entityjavelin.rotationYaw - entityjavelin.prevRotationYaw) * f1 - 90.0f, 0.0f, 1.0f, 0.0f);
            GlStateManager.rotate(entityjavelin.prevRotationPitch + (entityjavelin.rotationPitch - entityjavelin.prevRotationPitch) * f1, 0.0f, 0.0f, 1.0f);
            Tessellator tessellator = Tessellator.getInstance();
            WorldRenderer vertexbuffer = tessellator.getWorldRenderer();
            double length = 20.0;
            GlStateManager.enableRescaleNormal();
            float f11 = entityjavelin.arrowShake - f1;
            if (f11 > 0.0f) {
                float f12 = -MathHelper.sin(f11 * 3.0f) * f11;
                GlStateManager.rotate(f12, 0.0f, 0.0f, 1.0f);
            }
            GlStateManager.rotate(45.0f, 1.0f, 0.0f, 0.0f);
            GlStateManager.scale(0.05625f, 0.05625f, 0.05625f);
            GlStateManager.translate(-4.0f, 0.0f, 0.0f);
            GL11.glNormal3f(0.05625f, 0.0f, 0.0f);
            vertexbuffer.begin(7, DefaultVertexFormats.POSITION_TEX);
            vertexbuffer.pos(-length, -2.0, -2.0).tex(0.0, 0.15625).endVertex();
            vertexbuffer.pos(-length, -2.0, 2.0).tex(0.15625, 0.15625).endVertex();
            vertexbuffer.pos(-length, 2.0, 2.0).tex(0.15625, 0.3125).endVertex();
            vertexbuffer.pos(-length, 2.0, -2.0).tex(0.0, 0.3125).endVertex();
            tessellator.draw();
            GL11.glNormal3f(-0.05625f, 0.0f, 0.0f);
            vertexbuffer.begin(7, DefaultVertexFormats.POSITION_TEX);
            vertexbuffer.pos(-length, 2.0, -2.0).tex(0.0, 0.15625).endVertex();
            vertexbuffer.pos(-length, 2.0, 2.0).tex(0.15625, 0.15625).endVertex();
            vertexbuffer.pos(-length, -2.0, 2.0).tex(0.15625, 0.3125).endVertex();
            vertexbuffer.pos(-length, -2.0, -2.0).tex(0.0, 0.3125).endVertex();
            tessellator.draw();
            for (int j = 0; j < 4; ++j) {
                GlStateManager.rotate(90.0f, 1.0f, 0.0f, 0.0f);
                GL11.glNormal3f(0.0f, 0.0f, 0.05625f);
                vertexbuffer.begin(7, DefaultVertexFormats.POSITION_TEX);
                vertexbuffer.pos(-length, -2.0, 0.0).tex(0.0, 0.0).endVertex();
                vertexbuffer.pos(length, -2.0, 0.0).tex(1.0, 0.0).endVertex();
                vertexbuffer.pos(length, 2.0, 0.0).tex(1.0, 0.15625).endVertex();
                vertexbuffer.pos(-length, 2.0, 0.0).tex(0.0, 0.15625).endVertex();
                tessellator.draw();
            }
            GlStateManager.disableRescaleNormal();
            GlStateManager.enableLighting();
            GlStateManager.popMatrix();
        } else {
            RenderItem itemRender = Minecraft.getMinecraft().getRenderItem();
            GlStateManager.pushMatrix();
            bindEntityTexture(entityjavelin);
            GlStateManager.translate(d, d1, d2);
            GlStateManager.enableRescaleNormal();
            GlStateManager.scale(1.7f, 1.7f, 1.7f);
            GlStateManager.rotate(entityjavelin.prevRotationYaw + (entityjavelin.rotationYaw - entityjavelin.prevRotationYaw) * f1 - 90.0f, 0.0f, 1.0f, 0.0f);
            GlStateManager.rotate(entityjavelin.prevRotationPitch + (entityjavelin.rotationPitch - entityjavelin.prevRotationPitch) * f1 - 45.0f, 0.0f, 0.0f, 1.0f);
            float f13 = entityjavelin.arrowShake - f1;
            if (f13 > 0.0f) {
                float f14 = -MathHelper.sin(f13 * 3.0f) * f13;
                GlStateManager.rotate(f14, 0.0f, 0.0f, 1.0f);
            }
            GlStateManager.translate(-0.25f, -0.25f, 0.0f);
            GlStateManager.rotate(180.0f, 0.0f, 1.0f, 0.0f);
            itemRender.renderItem(getStackToRender(entityjavelin), TransformType.NONE);
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
        return WeaponModResources.Entity.JAVELIN;
    }
}
