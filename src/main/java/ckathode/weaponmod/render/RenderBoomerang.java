package ckathode.weaponmod.render;

import ckathode.weaponmod.BalkonsWeaponMod;
import ckathode.weaponmod.WeaponModResources;
import ckathode.weaponmod.entity.projectile.EntityBoomerang;
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

public class RenderBoomerang extends Render<EntityBoomerang> {
    public RenderBoomerang(RenderManager renderManager) {
        super(renderManager);
    }

    @Override
    public void doRender(@Nonnull EntityBoomerang entityboomerang, double d, double d1,
                         double d2, float f, float f1) {
        if (!BalkonsWeaponMod.instance.modConfig.itemModelForEntity.get()) {
            bindEntityTexture(entityboomerang);
            GlStateManager.pushMatrix();
            GlStateManager.disableLighting();
            GlStateManager.translated(d, d1, d2);
            GlStateManager.rotatef(entityboomerang.prevRotationPitch + (entityboomerang.rotationPitch - entityboomerang.prevRotationPitch) * f1, 0.0f, 0.0f, 1.0f);
            GlStateManager.rotatef(entityboomerang.prevRotationYaw + (entityboomerang.rotationYaw - entityboomerang.prevRotationYaw) * f1 - 90.0f, 0.0f, 1.0f, 0.0f);
            Tessellator tessellator = Tessellator.getInstance();
            BufferBuilder vertexbuffer = tessellator.getBuffer();
            int mat = entityboomerang.getWeaponMaterialId();
            float[] color = entityboomerang.getMaterialColor();
            float ft0 = 0.0f;
            float ft2 = 0.5f;
            float ft3 = 1.0f;
            float fh = 0.08f;
            float f2 = 0.2f;
            float f3 = 0.9f;
            float f4 = 0.8f;
            float ft4 = 0.5f;
            float ft5 = 0.65625f;
            GlStateManager.translatef(-0.5f, 0.0f, -0.5f);
            GlStateManager.color4f(1.0f, 1.0f, 1.0f, 1.0f);
            if (renderOutlines) {
                GlStateManager.enableColorMaterial();
                GlStateManager.enableOutlineMode(getTeamColor(entityboomerang));
            }
            GlStateManager.normal3f(0.0f, 1.0f, 0.0f);
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
            GlStateManager.normal3f(0.0f, -1.0f, 0.0f);
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
            float sqrt2 = (float) Math.sqrt(2.0);
            GlStateManager.disableCull();
            GlStateManager.normal3f(-sqrt2, 0.0f, sqrt2);
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
            if (renderOutlines) {
                GlStateManager.disableOutlineMode();
                GlStateManager.disableColorMaterial();
            }
            GlStateManager.enableCull();
            GlStateManager.enableLighting();
            GlStateManager.popMatrix();
        } else {
            ItemRenderer itemRender = Minecraft.getInstance().getItemRenderer();
            GlStateManager.pushMatrix();
            bindEntityTexture(entityboomerang);
            GlStateManager.translated(d, d1, d2);
            GlStateManager.enableRescaleNormal();
            GlStateManager.scalef(0.85f, 0.85f, 0.85f);
            GlStateManager.rotatef(entityboomerang.prevRotationPitch + (entityboomerang.rotationPitch - entityboomerang.prevRotationPitch) * f1, 0.0f, 0.0f, 1.0f);
            GlStateManager.rotatef(entityboomerang.prevRotationYaw + (entityboomerang.rotationYaw - entityboomerang.prevRotationYaw) * f1 - 90.0f, 0.0f, 1.0f, 0.0f);
            GlStateManager.rotatef(90.0f, 1.0f, 0.0f, 0.0f);
            if (renderOutlines) {
                GlStateManager.enableColorMaterial();
                GlStateManager.enableOutlineMode(getTeamColor(entityboomerang));
            }
            itemRender.renderItem(getStackToRender(entityboomerang), TransformType.NONE);
            if (renderOutlines) {
                GlStateManager.disableOutlineMode();
                GlStateManager.disableColorMaterial();
            }
            GlStateManager.disableRescaleNormal();
            GlStateManager.popMatrix();
        }
        super.doRender(entityboomerang, d, d1, d2, f, f1);
    }

    public ItemStack getStackToRender(EntityBoomerang entity) {
        return entity.getWeapon();
    }

    @Override
    protected ResourceLocation getEntityTexture(@Nonnull EntityBoomerang entity) {
        return WeaponModResources.Textures.BOOMERANG;
    }
}
