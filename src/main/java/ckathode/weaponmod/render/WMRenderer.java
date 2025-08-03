package ckathode.weaponmod.render;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;

public abstract class WMRenderer<T> extends Render {

    @Override
    @SuppressWarnings("unchecked") // if this fails, something seriously went wrong...
    public void doRender(Entity entity, double x, double y, double z, float entityYaw, float partialTicks) {
        renderEntity((T) entity, x, y, z, entityYaw, partialTicks);
    }

    public abstract void renderEntity(T entity, double x, double y, double z, float entityYaw, float partialTicks);

}
