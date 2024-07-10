package ckathode.weaponmod.render;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class RenderWeaponItem implements IItemRenderer {
    public static final ResourceLocation ENCHANTMENT_GLINT = new ResourceLocation(
            "minecraft", "%blur%/misc/enchanted_item_glint.png");

    protected Minecraft mc;

    public RenderWeaponItem() {
        mc = FMLClientHandler.instance().getClient();
    }

    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type) {
        return type == ItemRenderType.EQUIPPED;
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
        return false;
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
        Tessellator tess = Tessellator.instance;

        EntityLivingBase entityliving = (EntityLivingBase) data[1];
        IIcon icon = entityliving.getItemIcon(item, 0);

        float t = 0.0625F;
        ItemRenderer.renderItemIn2D(tess, icon.getMaxU(), icon.getMinV(), icon.getMinU(), icon.getMaxV(),
                icon.getIconWidth(), icon.getIconHeight(), t);
        renderEnchantEffect(tess, item, 256, 256, t);
    }

    protected void renderEnchantEffect(Tessellator tess, ItemStack item, int iconwidth, int iconheight,
                                       float thickness) {
        if (item != null && item.hasEffect(0)) {
            GL11.glDepthFunc(GL11.GL_EQUAL);
            GL11.glDisable(GL11.GL_LIGHTING);
            mc.renderEngine.bindTexture(ENCHANTMENT_GLINT);
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_SRC_COLOR, GL11.GL_ONE);
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
