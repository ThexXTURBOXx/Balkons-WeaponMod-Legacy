package ckathode.weaponmod.render;

import javax.annotation.Nonnull;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.entity.Entity;

public class WMModel<T extends Entity> extends EntityModel<T> {

    @Override
    public void render(@Nonnull T entity, float f, float f1, float f2, float f3, float f4, float f5) {
        for (RendererModel model : boxList) {
            model.render(f5);
        }
    }

    public void setRotation(RendererModel model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

}
