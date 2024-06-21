package ckathode.weaponmod.render;

import ckathode.weaponmod.BalkonsWeaponMod;
import ckathode.weaponmod.WeaponModResources;
import ckathode.weaponmod.entity.projectile.EntitySpear;
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

public class RenderSpear extends Render<EntitySpear> {
    public RenderSpear(RenderManager renderManager) {
        super(renderManager);
    }

    @Override
    public void doRender(@Nonnull EntitySpear entityspear, double d, double d1, double d2,
                         float f, float f1) {
        if (!BalkonsWeaponMod.instance.modConfig.itemModelForEntity) {
            bindEntityTexture(entityspear);
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            GlStateManager.pushMatrix();
            GlStateManager.disableLighting();
            GlStateManager.translate(d, d1, d2);
            GlStateManager.rotate(entityspear.prevRotationYaw + (entityspear.rotationYaw - entityspear.prevRotationYaw) * f1 - 90.0f, 0.0f, 1.0f, 0.0f);
            GlStateManager.rotate(entityspear.prevRotationPitch + (entityspear.rotationPitch - entityspear.prevRotationPitch) * f1, 0.0f, 0.0f, 1.0f);
            Tessellator tessellator = Tessellator.getInstance();
            WorldRenderer vertexbuffer = tessellator.getWorldRenderer();
            float[] color = entityspear.getMaterialColor();
            double length = 20.0;
            GlStateManager.enableRescaleNormal();
            float f13 = entityspear.arrowShake - f1;
            if (f13 > 0.0f) {
                float f14 = -MathHelper.sin(f13 * 3.0f) * f13;
                GlStateManager.rotate(f14, 0.0f, 0.0f, 1.0f);
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
            GlStateManager.disableRescaleNormal();
            GlStateManager.enableLighting();
            GlStateManager.popMatrix();
        } else {
            RenderItem itemRender = Minecraft.getMinecraft().getRenderItem();
            GlStateManager.pushMatrix();
            bindEntityTexture(entityspear);
            GlStateManager.translate(d, d1, d2);
            GlStateManager.enableRescaleNormal();
            GlStateManager.scale(1.7f, 1.7f, 1.7f);
            GlStateManager.rotate(entityspear.prevRotationYaw + (entityspear.rotationYaw - entityspear.prevRotationYaw) * f1 - 90.0f, 0.0f, 1.0f, 0.0f);
            GlStateManager.rotate(entityspear.prevRotationPitch + (entityspear.rotationPitch - entityspear.prevRotationPitch) * f1 - 45.0f, 0.0f, 0.0f, 1.0f);
            float f15 = entityspear.arrowShake - f1;
            if (f15 > 0.0f) {
                float f16 = -MathHelper.sin(f15 * 3.0f) * f15;
                GlStateManager.rotate(f16, 0.0f, 0.0f, 1.0f);
            }
            GlStateManager.translate(-0.35f, -0.35f, 0.0f);
            itemRender.renderItem(getStackToRender(entityspear), TransformType.NONE);
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
