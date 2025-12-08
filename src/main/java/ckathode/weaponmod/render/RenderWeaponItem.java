package ckathode.weaponmod.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;

public class RenderWeaponItem implements IItemRenderer {

    public static final RenderWeaponItem INSTANCE = new RenderWeaponItem();

    public static final ResourceLocation ENCHANTMENT_GLINT = new ResourceLocation(
            "textures/misc/enchanted_item_glint.png");

    private static final Minecraft MC = Minecraft.getMinecraft();

    @Override
    public boolean handleRenderType(ItemStack stack, ItemRenderType type) {
        return type == ItemRenderType.EQUIPPED || type == ItemRenderType.EQUIPPED_FIRST_PERSON;
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack stack, ItemRendererHelper helper) {
        return false;
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack stack, Object... data) {
        Tessellator tess = Tessellator.instance;

        IIcon icon = null;
        if (data.length < 2 || data[1] == null) {
            Item item = stack.getItem();
            if (item != null) {
                icon = item.getIcon(stack, 0);
            }
        } else if (data[1] instanceof EntityLivingBase) {
            EntityLivingBase entityLiving = (EntityLivingBase) data[1];
            icon = entityLiving.getItemIcon(stack, 0);
        }

        float t = 0.0625F;
        if (icon != null) {
            ItemRenderer.renderItemIn2D(tess, icon.getMaxU(), icon.getMinV(), icon.getMinU(), icon.getMaxV(),
                    icon.getIconWidth(), icon.getIconHeight(), t);
            renderEnchantEffect(tess, stack, 256, 256, t);
        }
    }

    public static void renderEnchantEffect(Tessellator tess, ItemStack stack, int iconwidth, int iconheight,
                                           float thickness) {
        if (stack != null && stack.hasEffect(0)) {
            GL11.glDepthFunc(GL11.GL_EQUAL);
            GL11.glDisable(GL11.GL_LIGHTING);
            MC.renderEngine.bindTexture(ENCHANTMENT_GLINT);
            GL11.glEnable(GL11.GL_BLEND);
            OpenGlHelper.glBlendFunc(GL11.GL_SRC_COLOR, GL11.GL_ONE, GL11.GL_ONE, GL11.GL_ZERO);
            float var14 = 0.76F;
            GL11.glColor4f(0.5F * var14, 0.25F * var14, 0.8F * var14, 1.0F);
            GL11.glMatrixMode(GL11.GL_TEXTURE);
            GL11.glPushMatrix();
            float var15 = 0.125F;
            GL11.glScalef(var15, var15, var15);
            float var16 = (Minecraft.getSystemTime() % 3000L) / 3000.0F * 8.0F;
            GL11.glTranslatef(var16, 0.0F, 0.0F);
            GL11.glRotatef(-50.0F, 0.0F, 0.0F, 1.0F);
            ItemRenderer.renderItemIn2D(tess, 0.0F, 0.0F, 1.0F, 1.0F, iconwidth, iconheight, thickness);
            GL11.glPopMatrix();
            GL11.glPushMatrix();
            GL11.glScalef(var15, var15, var15);
            var16 = (Minecraft.getSystemTime() % 4873L) / 4873.0F * 8.0F;
            GL11.glTranslatef(-var16, 0.0F, 0.0F);
            GL11.glRotatef(10.0F, 0.0F, 0.0F, 1.0F);
            ItemRenderer.renderItemIn2D(tess, 0.0F, 0.0F, 1.0F, 1.0F, iconwidth, iconheight, thickness);
            GL11.glPopMatrix();
            GL11.glMatrixMode(GL11.GL_MODELVIEW);
            GL11.glDisable(GL11.GL_BLEND);
            GL11.glEnable(GL11.GL_LIGHTING);
            GL11.glDepthFunc(GL11.GL_LEQUAL);
        }
    }
}
