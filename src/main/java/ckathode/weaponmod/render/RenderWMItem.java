package ckathode.weaponmod.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;

public class RenderWMItem {

    public static final RenderWMItem INSTANCE = new RenderWMItem();

    private final Minecraft mc = Minecraft.getMinecraft();
    private final RenderBlocks renderBlocks = new RenderBlocks();

    public void render(Entity entity, double x, double y, double z, ItemStack stack) {
        Item item = stack.getItem();
        if (item == null) return;

        if (ForgeHooksClient.renderEntityItem(new EntityItem(entity.worldObj, x, y, z, stack), stack, 0F, 0F,
                entity.worldObj.rand, mc.renderEngine, renderBlocks, 1)) return;

        if (item.shouldRotateAroundWhenRendering()) GL11.glRotatef(180.0f, 0.0f, 1.0f, 0.0f);
        GL11.glTranslatef(-0.5F, -0.5F, 0.0f);
        RenderWeaponItem.INSTANCE.renderItem(IItemRenderer.ItemRenderType.ENTITY, stack);
    }

}
