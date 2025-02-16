package ckathode.weaponmod.render;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class RenderLongItem extends RenderWeaponItem {

    public static final RenderLongItem INSTANCE = new RenderLongItem();

    @Override
    public void renderItem(ItemRenderType type, ItemStack stack, Object... data) {
        Tessellator tess = Tessellator.instance;

        IIcon icon = null;
        if (data.length < 2 || data[1] == null) {
            Item item = stack.getItem();
            if (item != null) {
                icon = item.getIcon(stack, 0);
            }
        } else {
            EntityLivingBase entityLiving = (EntityLivingBase) data[1];
            icon = entityLiving.getItemIcon(stack, 0);
        }

        GL11.glTranslatef(-0.5F, -0.5F, 0F);
        GL11.glScalef(2F, 2F, 1.4F);

        float t = 0.0625F;
        if (icon != null) {
            ItemRenderer.renderItemIn2D(tess, icon.getMaxU(), icon.getMinV(), icon.getMinU(), icon.getMaxV(),
                    icon.getIconWidth() * 16, icon.getIconHeight() * 16, t);
            renderEnchantEffect(tess, stack, 256, 256, t);
        }
    }

}
