package ckathode.weaponmod.render;

import ckathode.weaponmod.BalkonsWeaponMod;
import ckathode.weaponmod.WeaponModResources;
import ckathode.weaponmod.entity.projectile.EntityBoomerang;
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
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class RenderBoomerang extends Render<EntityBoomerang> {
    public RenderBoomerang(RenderManager renderManager) {
        super(renderManager);
    }

    @Override
    public void doRender(@Nonnull EntityBoomerang entityboomerang, double d, double d1,
                         double d2, float f, float f1) {
        if (!BalkonsWeaponMod.instance.modConfig.itemModelForEntity) {
            bindEntityTexture(entityboomerang);
            GlStateManager.pushMatrix();
            GlStateManager.disableLighting();
            GlStateManager.translate(d, d1, d2);
            GlStateManager.rotate(entityboomerang.prevRotationPitch + (entityboomerang.rotationPitch - entityboomerang.prevRotationPitch) * f1, 0.0f, 0.0f, 1.0f);
            GlStateManager.rotate(entityboomerang.prevRotationYaw + (entityboomerang.rotationYaw - entityboomerang.prevRotationYaw) * f1 - 90.0f, 0.0f, 1.0f, 0.0f);
            Tessellator tessellator = Tessellator.getInstance();
            WorldRenderer vertexbuffer = tessellator.getWorldRenderer();
            int mat = entityboomerang.getWeaponMaterialId();
            float[] color = entityboomerang.getMaterialColor();
            GlStateManager.translate(-0.5f, 0.0f, -0.5f);
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            GL11.glNormal3f(0.0f, 1.0f, 0.0f);
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
            GL11.glNormal3f(0.0f, -1.0f, 0.0f);
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
            GL11.glNormal3f(-sqrt2, 0.0f, sqrt2);
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
            GlStateManager.enableCull();
            GlStateManager.enableLighting();
            GlStateManager.popMatrix();
        } else {
            RenderItem itemRender = Minecraft.getMinecraft().getRenderItem();
            GlStateManager.pushMatrix();
            bindEntityTexture(entityboomerang);
            GlStateManager.translate(d, d1, d2);
            GlStateManager.enableRescaleNormal();
            GlStateManager.scale(0.85f, 0.85f, 0.85f);
            GlStateManager.rotate(entityboomerang.prevRotationPitch + (entityboomerang.rotationPitch - entityboomerang.prevRotationPitch) * f1, 0.0f, 0.0f, 1.0f);
            GlStateManager.rotate(entityboomerang.prevRotationYaw + (entityboomerang.rotationYaw - entityboomerang.prevRotationYaw) * f1 - 90.0f, 0.0f, 1.0f, 0.0f);
            GlStateManager.rotate(90.0f, 1.0f, 0.0f, 0.0f);
            itemRender.renderItem(getStackToRender(entityboomerang), TransformType.NONE);
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
        return WeaponModResources.Entity.BOOMERANG;
    }
}
