package ckathode.weaponmod.render;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelCannonLegacy extends ModelBase {

    public final ModelRenderer barrel;
    public final ModelRenderer bottom;
    public final ModelRenderer frame;
    public final ModelRenderer seatBottom;
    public final ModelRenderer seatFrame;

    public ModelCannonLegacy() {
        barrel = new ModelRenderer(this, 0, 14);
        barrel.addBox(-4.0f, 0.0f, -16.0f, 6, 6, 12, 4.0f);
        barrel.setRotationPoint(0.0f, 9.0f, 1.0f);
        barrel.rotateAngleX = -6.195919f;
        barrel.mirror = true;
        bottom = new ModelRenderer(this, 0, 1);
        bottom.addBox(0.0f, 0.0f, 0.0f, 12, 1, 12, 1.0f);
        bottom.setRotationPoint(-7.0f, -1.0f, -5.0f);
        bottom.mirror = true;
        frame = new ModelRenderer(this, 24, 19);
        frame.addBox(0.0f, 0.0f, 0.0f, 2, 2, 2, 1.0f);
        frame.setRotationPoint(-2.0f, 2.0f, 0.0f);
        frame.mirror = true;
        seatBottom = new ModelRenderer(this, 6, 5);
        seatBottom.addBox(0.0f, 0.0f, 0.0f, 10, 1, 8, 1.0f);
        seatBottom.setRotationPoint(-6.0f, 8.0f, 8.0f);
        seatBottom.mirror = true;
        seatFrame = new ModelRenderer(this, 36, 19);
        seatFrame.addBox(0.0f, 0.0f, 0.0f, 2, 1, 12, 1.0f);
        seatFrame.setRotationPoint(-2.0f, 6.0f, 0.0f);
        seatFrame.mirror = true;
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3,
                       float f4, float f5) {
        super.render(entity, f, f1, f2, f3, f4, f5);
        setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        barrel.render(f5);
        bottom.render(f5);
        frame.render(f5);
        seatBottom.render(f5);
        seatFrame.render(f5);
    }

    public void setRotation(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

    @Override
    public void setRotationAngles(float f, float f1, float f2, float f3, float f4,
                                  float f5, Entity entity) {
        super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    }

}
