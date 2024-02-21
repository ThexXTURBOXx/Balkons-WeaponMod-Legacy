package ckathode.weaponmod.render;

import ckathode.weaponmod.entity.projectile.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.entity.*;
import net.minecraft.util.math.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraft.client.*;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.client.renderer.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import ckathode.weaponmod.*;

public class RenderKnife extends Render<EntityKnife>
{
    public RenderKnife(final RenderManager renderManager) {
        super(renderManager);
    }
    
    public void doRender(final EntityKnife entityknife, final double d, final double d1, final double d2, final float f, final float f1) {
        if (!BalkonsWeaponMod.instance.modConfig.itemModelForEntity) {
            this.bindEntityTexture(entityknife);
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            GlStateManager.pushMatrix();
            GlStateManager.disableLighting();
            GlStateManager.translate((float)d, (float)d1, (float)d2);
            GlStateManager.rotate(entityknife.prevRotationYaw + (entityknife.rotationYaw - entityknife.prevRotationYaw) * f1 - 90.0f, 0.0f, 1.0f, 0.0f);
            GlStateManager.rotate(entityknife.prevRotationPitch + (entityknife.rotationPitch - entityknife.prevRotationPitch) * f1, 0.0f, 0.0f, 1.0f);
            final Tessellator tessellator = Tessellator.getInstance();
            final BufferBuilder vertexbuffer = tessellator.getBuffer();
            final float[] color = entityknife.getMaterialColor();
            final int i = 0;
            final float f2 = 0.0f;
            final float f3 = 0.5f;
            final float f4 = 0.0f;
            final float f5 = 0.15625f;
            final float f6 = 0.0f;
            final float f7 = 0.15625f;
            final float f8 = 0.15625f;
            final float f9 = 0.3125f;
            final float f10 = 0.3125f;
            final float f11 = 0.46875f;
            final float f12 = 0.05625f;
            GlStateManager.enableRescaleNormal();
            final float f13 = entityknife.arrowShake - f1;
            if (f13 > 0.0f) {
                final float f14 = -MathHelper.sin(f13 * 3.0f) * f13;
                GlStateManager.rotate(f14, 0.0f, 0.0f, 1.0f);
            }
            GlStateManager.rotate(45.0f, 1.0f, 0.0f, 0.0f);
            GlStateManager.scale(0.05625f, 0.05625f, 0.05625f);
            GlStateManager.translate(-4.0f, 0.0f, 0.0f);
            if (this.renderOutlines) {
                GlStateManager.enableColorMaterial();
                GlStateManager.enableOutlineMode(this.getTeamColor(entityknife));
            }
            GlStateManager.glNormal3f(0.05625f, 0.0f, 0.0f);
            vertexbuffer.begin(7, DefaultVertexFormats.POSITION_TEX);
            vertexbuffer.pos(-7.0, -2.0, -2.0).tex(0.0, 0.15625).endVertex();
            vertexbuffer.pos(-7.0, -2.0, 2.0).tex(0.15625, 0.15625).endVertex();
            vertexbuffer.pos(-7.0, 2.0, 2.0).tex(0.15625, 0.3125).endVertex();
            vertexbuffer.pos(-7.0, 2.0, -2.0).tex(0.0, 0.3125).endVertex();
            tessellator.draw();
            GlStateManager.glNormal3f(-0.05625f, 0.0f, 0.0f);
            vertexbuffer.begin(7, DefaultVertexFormats.POSITION_TEX);
            vertexbuffer.pos(-7.0, 2.0, -2.0).tex(0.0, 0.15625).endVertex();
            vertexbuffer.pos(-7.0, 2.0, 2.0).tex(0.15625, 0.15625).endVertex();
            vertexbuffer.pos(-7.0, -2.0, 2.0).tex(0.15625, 0.3125).endVertex();
            vertexbuffer.pos(-7.0, -2.0, -2.0).tex(0.0, 0.3125).endVertex();
            tessellator.draw();
            for (int j = 0; j < 4; ++j) {
                GlStateManager.rotate(90.0f, 1.0f, 0.0f, 0.0f);
                GlStateManager.glNormal3f(0.0f, 0.0f, 0.05625f);
                vertexbuffer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
                vertexbuffer.pos(-8.0, -2.0, 0.0).tex(0.0, 0.0).color(1.0f, 1.0f, 1.0f, 1.0f).endVertex();
                vertexbuffer.pos(8.0, -2.0, 0.0).tex(0.5, 0.0).color(1.0f, 1.0f, 1.0f, 1.0f).endVertex();
                vertexbuffer.pos(8.0, 2.0, 0.0).tex(0.5, 0.15625).color(1.0f, 1.0f, 1.0f, 1.0f).endVertex();
                vertexbuffer.pos(-8.0, 2.0, 0.0).tex(0.0, 0.15625).color(1.0f, 1.0f, 1.0f, 1.0f).endVertex();
                vertexbuffer.pos(-8.0, -2.0, 0.0).tex(0.0, 0.3125).color(color[0], color[1], color[2], 1.0f).endVertex();
                vertexbuffer.pos(8.0, -2.0, 0.0).tex(0.5, 0.3125).color(color[0], color[1], color[2], 1.0f).endVertex();
                vertexbuffer.pos(8.0, 2.0, 0.0).tex(0.5, 0.46875).color(color[0], color[1], color[2], 1.0f).endVertex();
                vertexbuffer.pos(-8.0, 2.0, 0.0).tex(0.0, 0.46875).color(color[0], color[1], color[2], 1.0f).endVertex();
                tessellator.draw();
            }
            if (this.renderOutlines) {
                GlStateManager.enableColorMaterial();
                GlStateManager.enableOutlineMode(this.getTeamColor(entityknife));
            }
            GlStateManager.disableRescaleNormal();
            GlStateManager.enableLighting();
            GlStateManager.popMatrix();
        }
        else {
            final RenderItem itemRender = Minecraft.getMinecraft().getRenderItem();
            GlStateManager.pushMatrix();
            this.bindEntityTexture(entityknife);
            GlStateManager.translate((float)d, (float)d1, (float)d2);
            GlStateManager.enableRescaleNormal();
            GlStateManager.scale(0.85f, 0.85f, 0.85f);
            GlStateManager.rotate(entityknife.prevRotationYaw + (entityknife.rotationYaw - entityknife.prevRotationYaw) * f1 - 90.0f, 0.0f, 1.0f, 0.0f);
            GlStateManager.rotate(entityknife.prevRotationPitch + (entityknife.rotationPitch - entityknife.prevRotationPitch) * f1 - 45.0f, 0.0f, 0.0f, 1.0f);
            final float f15 = entityknife.arrowShake - f1;
            if (f15 > 0.0f) {
                final float f16 = -MathHelper.sin(f15 * 3.0f) * f15;
                GlStateManager.rotate(f16, 0.0f, 0.0f, 1.0f);
            }
            GlStateManager.translate(-0.15f, -0.15f, 0.0f);
            if (this.renderOutlines) {
                GlStateManager.enableColorMaterial();
                GlStateManager.enableOutlineMode(this.getTeamColor(entityknife));
            }
            itemRender.renderItem(this.getStackToRender(entityknife), ItemCameraTransforms.TransformType.NONE);
            if (this.renderOutlines) {
                GlStateManager.disableOutlineMode();
                GlStateManager.disableColorMaterial();
            }
            GlStateManager.disableRescaleNormal();
            GlStateManager.popMatrix();
        }
        super.doRender(entityknife, d, d1, d2, f, f1);
    }
    
    public ItemStack getStackToRender(final EntityKnife entity) {
        return entity.getWeapon();
    }
    
    protected ResourceLocation getEntityTexture(final EntityKnife entity) {
        return WeaponModResources.Textures.KNIFE;
    }
}
