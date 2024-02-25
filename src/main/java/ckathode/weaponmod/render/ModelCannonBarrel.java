package ckathode.weaponmod.render;

import javax.annotation.Nonnull;
import net.minecraft.client.renderer.entity.model.ModelBase;
import net.minecraft.client.renderer.entity.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelCannonBarrel extends ModelBase {
    public final ModelRenderer swiwel_front;
    public final ModelRenderer swivel_back;
    public final ModelRenderer swivel_main;
    public final ModelRenderer axis;
    public final ModelRenderer seal;
    public final ModelRenderer handcrap;
    public final ModelRenderer fronttip;
    public final ModelRenderer backtip;

    public ModelCannonBarrel() {
        textureWidth = 32;
        textureHeight = 32;
        (swiwel_front = new ModelRenderer(this, 12, 12)).addBox(-2.0f, -2.0f, -11.0f, 4, 4, 2);
        swiwel_front.setRotationPoint(0.0f, 16.0f, 0.0f);
        swiwel_front.setTextureSize(32, 32);
        swiwel_front.mirror = true;
        setRotation(swiwel_front, 0.0f, 0.0f, 0.0f);
        (swivel_back = new ModelRenderer(this, 12, 0)).addBox(-2.0f, -0.5f, -2.0f, 4, 8, 4);
        swivel_back.setRotationPoint(0.0f, 16.0f, 0.0f);
        swivel_back.setTextureSize(32, 32);
        swivel_back.mirror = true;
        setRotation(swivel_back, 1.570796f, 0.0f, 0.0f);
        (swivel_main = new ModelRenderer(this, 0, 0)).addBox(-1.5f, -11.5f, -1.5f, 3, 20, 3);
        swivel_main.setRotationPoint(0.0f, 16.0f, 0.0f);
        swivel_main.setTextureSize(32, 32);
        swivel_main.mirror = true;
        setRotation(swivel_main, 1.570796f, 0.0f, 0.0f);
        (axis = new ModelRenderer(this, 12, 18)).addBox(-4.0f, -0.5f, -0.5f, 8, 1, 1);
        axis.setRotationPoint(0.0f, 16.0f, 0.0f);
        axis.setTextureSize(32, 32);
        axis.mirror = true;
        setRotation(axis, 1.570796f, 0.0f, 0.0f);
        (seal = new ModelRenderer(this, 9, 0)).addBox(-1.0f, -2.5f, 5.5f, 2, 1, 1);
        seal.setRotationPoint(0.0f, 16.0f, 0.0f);
        seal.setTextureSize(32, 32);
        seal.mirror = true;
        setRotation(seal, 0.0f, 0.0f, 0.0f);
        (handcrap = new ModelRenderer(this, 28, 0)).addBox(-0.5f, 8.5f, -0.5f, 1, 7, 1);
        handcrap.setRotationPoint(0.0f, 16.0f, 0.0f);
        handcrap.setTextureSize(32, 32);
        handcrap.mirror = true;
        setRotation(handcrap, 1.570796f, 0.0f, 0.0f);
        (fronttip = new ModelRenderer(this, 24, 12)).addBox(-1.0f, 9.0f, -1.0f, 2, 1, 2);
        fronttip.setRotationPoint(0.0f, 16.0f, 0.0f);
        fronttip.setTextureSize(32, 32);
        fronttip.mirror = true;
        setRotation(fronttip, 1.570796f, 0.0f, 0.0f);
        (backtip = new ModelRenderer(this, 24, 12)).addBox(-1.0f, 15.5f, -1.0f, 2, 1, 2);
        backtip.setRotationPoint(0.0f, 16.0f, 0.0f);
        backtip.setTextureSize(32, 32);
        backtip.mirror = true;
        setRotation(backtip, 1.570796f, 0.0f, 0.0f);
    }

    @Override
    public void render(@Nonnull Entity entity, float f, float f1, float f2, float f3,
                       float f4, float f5) {
        super.render(entity, f, f1, f2, f3, f4, f5);
        setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        swiwel_front.render(f5);
        swivel_back.render(f5);
        swivel_main.render(f5);
        axis.render(f5);
        seal.render(f5);
        handcrap.render(f5);
        fronttip.render(f5);
        backtip.render(f5);
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
