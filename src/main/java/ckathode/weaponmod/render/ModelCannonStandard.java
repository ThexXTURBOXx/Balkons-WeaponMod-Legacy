package ckathode.weaponmod.render;

import javax.annotation.Nonnull;
import net.minecraft.client.renderer.entity.model.ModelBase;
import net.minecraft.client.renderer.entity.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelCannonStandard extends ModelBase {
    public final ModelRenderer console_main_;
    public final ModelRenderer console_side_l1;
    public final ModelRenderer console_side_r1;
    public final ModelRenderer base_1;
    public final ModelRenderer base_2;
    public final ModelRenderer base_stand;
    public final ModelRenderer axis1;

    public ModelCannonStandard() {
        textureWidth = 32;
        textureHeight = 32;
        (console_main_ = new ModelRenderer(this, 12, 20)).addBox(-2.5f, -1.0f, -1.0f, 5, 1, 2);
        console_main_.setRotationPoint(0.0f, 20.0f, 0.0f);
        console_main_.setTextureSize(32, 32);
        console_main_.mirror = true;
        setRotation(console_main_, 0.0f, 0.0f, 0.0f);
        (console_side_l1 = new ModelRenderer(this, 26, 20)).addBox(2.5f, -4.0f, -1.0f, 1, 5, 2);
        console_side_l1.setRotationPoint(0.0f, 19.0f, 0.0f);
        console_side_l1.setTextureSize(32, 32);
        console_side_l1.mirror = true;
        setRotation(console_side_l1, 0.0f, 0.0f, 0.0f);
        (console_side_r1 = new ModelRenderer(this, 26, 20)).addBox(-3.5f, -4.0f, -1.0f, 1, 5, 2);
        console_side_r1.setRotationPoint(0.0f, 19.0f, 0.0f);
        console_side_r1.setTextureSize(32, 32);
        console_side_r1.mirror = true;
        setRotation(console_side_r1, 0.0f, 0.0f, 0.0f);
        (base_1 = new ModelRenderer(this, 0, 26)).addBox(-2.0f, -2.0f, -2.0f, 4, 2, 4);
        base_1.setRotationPoint(0.0f, 24.0f, 0.0f);
        base_1.setTextureSize(32, 32);
        base_1.mirror = true;
        setRotation(base_1, 0.0f, 0.0f, 0.0f);
        (base_2 = new ModelRenderer(this, 16, 28)).addBox(-1.5f, -3.0f, -1.5f, 3, 1, 3);
        base_2.setRotationPoint(0.0f, 24.0f, 0.0f);
        base_2.setTextureSize(32, 32);
        base_2.mirror = true;
        setRotation(base_2, 0.0f, 0.0f, 0.0f);
        (base_stand = new ModelRenderer(this, 0, 23)).addBox(-1.0f, -4.0f, -1.0f, 2, 1, 2);
        base_stand.setRotationPoint(0.0f, 24.0f, 0.0f);
        base_stand.setTextureSize(32, 32);
        base_stand.mirror = true;
        setRotation(base_stand, 0.0f, 0.0f, 0.0f);
        (axis1 = new ModelRenderer(this, 22, 23)).addBox(-0.5f, -5.5f, -0.5f, 1, 3, 1);
        axis1.setRotationPoint(0.0f, 24.0f, 0.0f);
        axis1.setTextureSize(32, 32);
        axis1.mirror = true;
        setRotation(axis1, 0.0f, 0.0f, 0.0f);
    }

    @Override
    public void render(@Nonnull Entity entity, float f, float f1, float f2, float f3,
                       float f4, float f5) {
        super.render(entity, f, f1, f2, f3, f4, f5);
        setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        console_main_.render(f5);
        console_side_l1.render(f5);
        console_side_r1.render(f5);
        base_1.render(f5);
        base_2.render(f5);
        base_stand.render(f5);
        axis1.render(f5);
    }

    public void setRotation(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

    @Override
    public void setRotationAngles(float f, float f1, float f2, float f3, float f4,
                                  float f5, @Nonnull Entity entity) {
        super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    }
}
