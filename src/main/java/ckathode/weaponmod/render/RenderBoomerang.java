package ckathode.weaponmod.render;

import ckathode.weaponmod.BalkonsWeaponMod;
import ckathode.weaponmod.WeaponModResources;
import ckathode.weaponmod.entity.projectile.EntityBoomerang;
import javax.annotation.Nonnull;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class RenderBoomerang extends Render<EntityBoomerang> {
    public RenderBoomerang(final RenderManager renderManager) {
        super(renderManager);
    }

    @Override
    public void doRender(@Nonnull final EntityBoomerang entityboomerang, final double d, final double d1,
                         final double d2, final float f, final float f1) {
        if (!BalkonsWeaponMod.instance.modConfig.itemModelForEntity) {
            this.bindEntityTexture(entityboomerang);
            GlStateManager.pushMatrix();
            GlStateManager.disableLighting();
            GlStateManager.translate((float) d, (float) d1, (float) d2);
            GlStateManager.rotate(entityboomerang.prevRotationPitch + (entityboomerang.rotationPitch - entityboomerang.prevRotationPitch) * f1, 0.0f, 0.0f, 1.0f);
            GlStateManager.rotate(entityboomerang.prevRotationYaw + (entityboomerang.rotationYaw - entityboomerang.prevRotationYaw) * f1 - 90.0f, 0.0f, 1.0f, 0.0f);
            final Tessellator tessellator = Tessellator.getInstance();
            final BufferBuilder vertexbuffer = tessellator.getBuffer();
            final int mat = entityboomerang.getWeaponMaterialId();
            final float[] color = entityboomerang.getMaterialColor();
            final float ft0 = 0.0f;
            final float ft2 = 0.5f;
            final float ft3 = 1.0f;
            final float fh = 0.08f;
            final float f2 = 0.2f;
            final float f3 = 0.9f;
            final float f4 = 0.8f;
            final float ft4 = 0.5f;
            final float ft5 = 0.65625f;
            GlStateManager.translate(-0.5f, 0.0f, -0.5f);
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            if (this.renderOutlines) {
                GlStateManager.enableColorMaterial();
                GlStateManager.enableOutlineMode(this.getTeamColor(entityboomerang));
            }
            GlStateManager.glNormal3f(0.0f, 1.0f, 0.0f);
            vertexbuffer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
            vertexbuffer.pos(0.0, 0.0, 1.0).tex(0.5, 0.0).color(1.0f, 1.0f, 1.0f, 1.0f).endVertex();
            vertexbuffer.pos(1.0, 0.0, 1.0).tex(0.0, 0.0).color(1.0f, 1.0f, 1.0f, 1.0f).endVertex();
            vertexbuffer.pos(1.0, 0.0, 0.0).tex(0.0, 0.5).color(1.0f, 1.0f, 1.0f, 1.0f).endVertex();
            vertexbuffer.pos(0.0, 0.0, 0.0).tex(0.5, 0.5).color(1.0f, 1.0f, 1.0f, 1.0f).endVertex();
            if (mat != 0) {
                vertexbuffer.pos(0.0, 0.0, 1.0).tex(1.0, 0.0).color(color[0], color[1], color[2], 1.0f).endVertex();
                vertexbuffer.pos(1.0, 0.0, 1.0).tex(0.5, 0.0).color(color[0], color[1], color[2], 1.0f).endVertex();
                vertexbuffer.pos(1.0, 0.0, 0.0).tex(0.5, 0.5).color(color[0], color[1], color[2], 1.0f).endVertex();
                vertexbuffer.pos(0.0, 0.0, 0.0).tex(1.0, 0.5).color(color[0], color[1], color[2], 1.0f).endVertex();
            }
            tessellator.draw();
            GlStateManager.glNormal3f(0.0f, -1.0f, 0.0f);
            vertexbuffer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
            vertexbuffer.pos(1.0, 0.0, 0.0).tex(0.0, 0.5).color(1.0f, 1.0f, 1.0f, 1.0f).endVertex();
            vertexbuffer.pos(1.0, 0.0, 1.0).tex(0.5, 0.5).color(1.0f, 1.0f, 1.0f, 1.0f).endVertex();
            vertexbuffer.pos(0.0, 0.0, 1.0).tex(0.5, 0.0).color(1.0f, 1.0f, 1.0f, 1.0f).endVertex();
            vertexbuffer.pos(0.0, 0.0, 0.0).tex(0.0, 0.0).color(1.0f, 1.0f, 1.0f, 1.0f).endVertex();
            if (mat != 0) {
                vertexbuffer.pos(1.0, 0.0, 0.0).tex(0.5, 0.5).color(color[0], color[1], color[2], 1.0f).endVertex();
                vertexbuffer.pos(1.0, 0.0, 1.0).tex(1.0, 0.5).color(color[0], color[1], color[2], 1.0f).endVertex();
                vertexbuffer.pos(0.0, 0.0, 1.0).tex(1.0, 0.0).color(color[0], color[1], color[2], 1.0f).endVertex();
                vertexbuffer.pos(0.0, 0.0, 0.0).tex(0.5, 0.0).color(color[0], color[1], color[2], 1.0f).endVertex();
            }
            tessellator.draw();
            final float sqrt2 = (float) Math.sqrt(2.0);
            GlStateManager.disableCull();
            GlStateManager.glNormal3f(-sqrt2, 0.0f, sqrt2);
            vertexbuffer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
            vertexbuffer.pos(0.2, -0.08, 0.8).tex(0.5, 0.5).color(1.0f, 1.0f, 1.0f, 1.0f).endVertex();
            vertexbuffer.pos(0.2, 0.08, 0.8).tex(0.5, 0.65625).color(1.0f, 1.0f, 1.0f, 1.0f).endVertex();
            vertexbuffer.pos(0.9, 0.08, 0.8).tex(0.0, 0.65625).color(1.0f, 1.0f, 1.0f, 1.0f).endVertex();
            vertexbuffer.pos(0.9, -0.08, 0.8).tex(0.0, 0.5).color(1.0f, 1.0f, 1.0f, 1.0f).endVertex();
            if (mat != 0) {
                vertexbuffer.pos(0.2, -0.08, 0.8).tex(1.0, 0.5).color(color[0], color[1], color[2], 1.0f).endVertex();
                vertexbuffer.pos(0.2, 0.08, 0.8).tex(1.0, 0.65625).color(color[0], color[1], color[2], 1.0f).endVertex();
                vertexbuffer.pos(0.9, 0.08, 0.8).tex(0.5, 0.65625).color(color[0], color[1], color[2], 1.0f).endVertex();
                vertexbuffer.pos(0.9, -0.08, 0.8).tex(0.5, 0.5).color(color[0], color[1], color[2], 1.0f).endVertex();
            }
            vertexbuffer.pos(0.2, -0.08, 0.8).tex(0.5, 0.5).color(1.0f, 1.0f, 1.0f, 1.0f).endVertex();
            vertexbuffer.pos(0.2, 0.08, 0.8).tex(0.5, 0.65625).color(1.0f, 1.0f, 1.0f, 1.0f).endVertex();
            vertexbuffer.pos(0.2, 0.08, 0.2).tex(0.0, 0.65625).color(1.0f, 1.0f, 1.0f, 1.0f).endVertex();
            vertexbuffer.pos(0.2, -0.08, 0.2).tex(0.0, 0.5).color(1.0f, 1.0f, 1.0f, 1.0f).endVertex();
            if (mat != 0) {
                vertexbuffer.pos(0.2, -0.08, 0.8).tex(1.0, 0.5).color(color[0], color[1], color[2], 1.0f).endVertex();
                vertexbuffer.pos(0.2, 0.08, 0.8).tex(1.0, 0.65625).color(color[0], color[1], color[2], 1.0f).endVertex();
                vertexbuffer.pos(0.2, 0.08, 0.2).tex(0.5, 0.65625).color(color[0], color[1], color[2], 1.0f).endVertex();
                vertexbuffer.pos(0.2, -0.08, 0.2).tex(0.5, 0.5).color(color[0], color[1], color[2], 1.0f).endVertex();
            }
            tessellator.draw();
            if (this.renderOutlines) {
                GlStateManager.disableOutlineMode();
                GlStateManager.disableColorMaterial();
            }
            GlStateManager.enableCull();
            GlStateManager.enableLighting();
            GlStateManager.popMatrix();
        } else {
            final RenderItem itemRender = Minecraft.getMinecraft().getRenderItem();
            GlStateManager.pushMatrix();
            this.bindEntityTexture(entityboomerang);
            GlStateManager.translate((float) d, (float) d1, (float) d2);
            GlStateManager.enableRescaleNormal();
            GlStateManager.scale(0.85f, 0.85f, 0.85f);
            GlStateManager.rotate(entityboomerang.prevRotationPitch + (entityboomerang.rotationPitch - entityboomerang.prevRotationPitch) * f1, 0.0f, 0.0f, 1.0f);
            GlStateManager.rotate(entityboomerang.prevRotationYaw + (entityboomerang.rotationYaw - entityboomerang.prevRotationYaw) * f1 - 90.0f, 0.0f, 1.0f, 0.0f);
            GlStateManager.rotate(90.0f, 1.0f, 0.0f, 0.0f);
            if (this.renderOutlines) {
                GlStateManager.enableColorMaterial();
                GlStateManager.enableOutlineMode(this.getTeamColor(entityboomerang));
            }
            itemRender.renderItem(this.getStackToRender(entityboomerang), ItemCameraTransforms.TransformType.NONE);
            if (this.renderOutlines) {
                GlStateManager.disableOutlineMode();
                GlStateManager.disableColorMaterial();
            }
            GlStateManager.disableRescaleNormal();
            GlStateManager.popMatrix();
        }
        super.doRender(entityboomerang, d, d1, d2, f, f1);
    }

    public ItemStack getStackToRender(final EntityBoomerang entity) {
        return entity.getWeapon();
    }

    @Override
    protected ResourceLocation getEntityTexture(@Nonnull final EntityBoomerang entity) {
        return WeaponModResources.Textures.BOOMERANG;
    }
}
