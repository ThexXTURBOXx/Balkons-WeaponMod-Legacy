package ckathode.weaponmod.render;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;

public class EntityItemFake extends EntityItem {

    public static final EntityItemFake INSTANCE = new EntityItemFake(null);

    private ItemStack stack;

    public EntityItemFake(ItemStack stack) {
        super(null);
        this.stack = stack;
    }

    @Override
    public ItemStack getEntityItem() {
        return stack;
    }

    public EntityItemFake setItem(ItemStack stack) {
        this.stack = stack;
        return this;
    }

    @Override
    public void onUpdate() {
        // Do not age etc.
    }

    @Override
    public void onEntityUpdate() {
        // Do not age etc.
    }
}
