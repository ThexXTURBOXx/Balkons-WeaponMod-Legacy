package ckathode.weaponmod.render;

import javax.annotation.Nonnull;
import net.minecraft.client.renderer.entity.model.ModelBase;
import net.minecraft.client.renderer.entity.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelCannon extends ModelBase {
    public final ModelRenderer swiwel_front;
    public final ModelRenderer swivel_back;
    public final ModelRenderer swivel_main;
    public final ModelRenderer axis;
    public final ModelRenderer seal;
    public final ModelRenderer handcrap;
    public final ModelRenderer fronttip;
    public final ModelRenderer backtip;
    public final ModelRenderer console_main_;
    public final ModelRenderer console_side_l1;
    public final ModelRenderer console_side_r1;
    public final ModelRenderer base_1;
    public final ModelRenderer base_2;
    public final ModelRenderer base_stand;
    public final ModelRenderer axis1;

    public ModelCannon() {
        textureWidth = 32;
        textureHeight = 32;
        swiwel_front = new ModelRenderer(this, 12, 12);
        swiwel_front.addBox(-2.0f, -2.0f, -11.0f, 4, 4, 2);
        swiwel_front.setRotationPoint(0.0f, 16.0f, 0.0f);
        swiwel_front.setTextureSize(32, 32);
        swiwel_front.mirror = true;
        setRotation(swiwel_front, 0.0f, 0.0f, 0.0f);
        swivel_back = new ModelRenderer(this, 12, 0);
        swivel_back.addBox(-2.0f, -0.5f, -2.0f, 4, 8, 4);
        swivel_back.setRotationPoint(0.0f, 16.0f, 0.0f);
        swivel_back.setTextureSize(32, 32);
        swivel_back.mirror = true;
        setRotation(swivel_back, 1.570796f, 0.0f, 0.0f);
        swivel_main = new ModelRenderer(this, 0, 0);
        swivel_main.addBox(-1.5f, -11.5f, -1.5f, 3, 20, 3);
        swivel_main.setRotationPoint(0.0f, 16.0f, 0.0f);
        swivel_main.setTextureSize(32, 32);
        swivel_main.mirror = true;
        setRotation(swivel_main, 1.570796f, 0.0f, 0.0f);
        axis = new ModelRenderer(this, 12, 18);
        axis.addBox(-4.0f, -0.5f, -0.5f, 8, 1, 1);
        axis.setRotationPoint(0.0f, 16.0f, 0.0f);
        axis.setTextureSize(32, 32);
        axis.mirror = true;
        setRotation(axis, 1.570796f, 0.0f, 0.0f);
        seal = new ModelRenderer(this, 9, 0);
        seal.addBox(-1.0f, -2.5f, 5.5f, 2, 1, 1);
        seal.setRotationPoint(0.0f, 16.0f, 0.0f);
        seal.setTextureSize(32, 32);
        seal.mirror = true;
        setRotation(seal, 0.0f, 0.0f, 0.0f);
        handcrap = new ModelRenderer(this, 28, 0);
        handcrap.addBox(-0.5f, 8.5f, -0.5f, 1, 7, 1);
        handcrap.setRotationPoint(0.0f, 16.0f, 0.0f);
        handcrap.setTextureSize(32, 32);
        handcrap.mirror = true;
        setRotation(handcrap, 1.570796f, 0.0f, 0.0f);
        fronttip = new ModelRenderer(this, 24, 12);
        fronttip.addBox(-1.0f, 9.0f, -1.0f, 2, 1, 2);
        fronttip.setRotationPoint(0.0f, 16.0f, 0.0f);
        fronttip.setTextureSize(32, 32);
        fronttip.mirror = true;
        setRotation(fronttip, 1.570796f, 0.0f, 0.0f);
        backtip = new ModelRenderer(this, 24, 12);
        backtip.addBox(-1.0f, 15.5f, -1.0f, 2, 1, 2);
        backtip.setRotationPoint(0.0f, 16.0f, 0.0f);
        backtip.setTextureSize(32, 32);
        backtip.mirror = true;
        setRotation(backtip, 1.570796f, 0.0f, 0.0f);
        console_main_ = new ModelRenderer(this, 12, 20);
        console_main_.addBox(-2.5f, -1.0f, -1.0f, 5, 1, 2);
        console_main_.setRotationPoint(0.0f, 20.0f, 0.0f);
        console_main_.setTextureSize(32, 32);
        console_main_.mirror = true;
        setRotation(console_main_, 0.0f, 0.0f, 0.0f);
        console_side_l1 = new ModelRenderer(this, 26, 20);
        console_side_l1.addBox(2.5f, -4.0f, -1.0f, 1, 5, 2);
        console_side_l1.setRotationPoint(0.0f, 19.0f, 0.0f);
        console_side_l1.setTextureSize(32, 32);
        console_side_l1.mirror = true;
        setRotation(console_side_l1, 0.0f, 0.0f, 0.0f);
        console_side_r1 = new ModelRenderer(this, 26, 20);
        console_side_r1.addBox(-3.5f, -4.0f, -1.0f, 1, 5, 2);
        console_side_r1.setRotationPoint(0.0f, 19.0f, 0.0f);
        console_side_r1.setTextureSize(32, 32);
        console_side_r1.mirror = true;
        setRotation(console_side_r1, 0.0f, 0.0f, 0.0f);
        base_1 = new ModelRenderer(this, 0, 26);
        base_1.addBox(-2.0f, -2.0f, -2.0f, 4, 2, 4);
        base_1.setRotationPoint(0.0f, 24.0f, 0.0f);
        base_1.setTextureSize(32, 32);
        base_1.mirror = true;
        setRotation(base_1, 0.0f, 0.0f, 0.0f);
        base_2 = new ModelRenderer(this, 16, 28);
        base_2.addBox(-1.5f, -3.0f, -1.5f, 3, 1, 3);
        base_2.setRotationPoint(0.0f, 24.0f, 0.0f);
        base_2.setTextureSize(32, 32);
        base_2.mirror = true;
        setRotation(base_2, 0.0f, 0.0f, 0.0f);
        base_stand = new ModelRenderer(this, 0, 23);
        base_stand.addBox(-1.0f, -4.0f, -1.0f, 2, 1, 2);
        base_stand.setRotationPoint(0.0f, 24.0f, 0.0f);
        base_stand.setTextureSize(32, 32);
        base_stand.mirror = true;
        setRotation(base_stand, 0.0f, 0.0f, 0.0f);
        axis1 = new ModelRenderer(this, 22, 23);
        axis1.addBox(-0.5f, -5.5f, -0.5f, 1, 3, 1);
        axis1.setRotationPoint(0.0f, 24.0f, 0.0f);
        axis1.setTextureSize(32, 32);
        axis1.mirror = true;
        setRotation(axis1, 0.0f, 0.0f, 0.0f);
    }

    @Override
    public void render(@Nonnull Entity entity, float f, float f1, float f2, float f3, float f4, float scale) {
        super.render(entity, f, f1, f2, f3, f4, scale);
        setRotationAngles(f, f1, f2, f3, f4, scale, entity);
        swiwel_front.render(scale);
        swivel_back.render(scale);
        swivel_main.render(scale);
        axis.render(scale);
        seal.render(scale);
        handcrap.render(scale);
        fronttip.render(scale);
        backtip.render(scale);
        console_main_.render(scale);
        console_side_l1.render(scale);
        console_side_r1.render(scale);
        base_1.render(scale);
        base_2.render(scale);
        base_stand.render(scale);
        axis1.render(scale);
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
