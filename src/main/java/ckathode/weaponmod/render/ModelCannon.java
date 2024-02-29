package ckathode.weaponmod.render;

import javax.annotation.Nonnull;
import net.minecraft.client.renderer.entity.model.ModelBase;
import net.minecraft.client.renderer.entity.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelCannon extends ModelBase {
    public final ModelRenderer swivelFront;
    public final ModelRenderer swivelBack;
    public final ModelRenderer swivelMain;
    public final ModelRenderer axis;
    public final ModelRenderer seal;
    public final ModelRenderer handCrap;
    public final ModelRenderer frontTip;
    public final ModelRenderer backTip;
    public final ModelRenderer consoleMain;
    public final ModelRenderer consoleSideL1;
    public final ModelRenderer consoleSideR1;
    public final ModelRenderer base1;
    public final ModelRenderer base2;
    public final ModelRenderer baseStand;
    public final ModelRenderer axis1;

    public ModelCannon() {
        textureWidth = 32;
        textureHeight = 32;
        swivelFront = new ModelRenderer(this, 12, 12);
        swivelFront.addBox(-2.0f, -2.0f, -11.0f, 4, 4, 2);
        swivelFront.setRotationPoint(0.0f, 16.0f, 0.0f);
        swivelFront.setTextureSize(32, 32);
        swivelFront.mirror = true;
        setRotation(swivelFront, 0.0f, 0.0f, 0.0f);
        swivelBack = new ModelRenderer(this, 12, 0);
        swivelBack.addBox(-2.0f, -0.5f, -2.0f, 4, 8, 4);
        swivelBack.setRotationPoint(0.0f, 16.0f, 0.0f);
        swivelBack.setTextureSize(32, 32);
        swivelBack.mirror = true;
        setRotation(swivelBack, 1.570796f, 0.0f, 0.0f);
        swivelMain = new ModelRenderer(this, 0, 0);
        swivelMain.addBox(-1.5f, -11.5f, -1.5f, 3, 20, 3);
        swivelMain.setRotationPoint(0.0f, 16.0f, 0.0f);
        swivelMain.setTextureSize(32, 32);
        swivelMain.mirror = true;
        setRotation(swivelMain, 1.570796f, 0.0f, 0.0f);
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
        handCrap = new ModelRenderer(this, 28, 0);
        handCrap.addBox(-0.5f, 8.5f, -0.5f, 1, 7, 1);
        handCrap.setRotationPoint(0.0f, 16.0f, 0.0f);
        handCrap.setTextureSize(32, 32);
        handCrap.mirror = true;
        setRotation(handCrap, 1.570796f, 0.0f, 0.0f);
        frontTip = new ModelRenderer(this, 24, 12);
        frontTip.addBox(-1.0f, 9.0f, -1.0f, 2, 1, 2);
        frontTip.setRotationPoint(0.0f, 16.0f, 0.0f);
        frontTip.setTextureSize(32, 32);
        frontTip.mirror = true;
        setRotation(frontTip, 1.570796f, 0.0f, 0.0f);
        backTip = new ModelRenderer(this, 24, 12);
        backTip.addBox(-1.0f, 15.5f, -1.0f, 2, 1, 2);
        backTip.setRotationPoint(0.0f, 16.0f, 0.0f);
        backTip.setTextureSize(32, 32);
        backTip.mirror = true;
        setRotation(backTip, 1.570796f, 0.0f, 0.0f);
        consoleMain = new ModelRenderer(this, 12, 20);
        consoleMain.addBox(-2.5f, -1.0f, -1.0f, 5, 1, 2);
        consoleMain.setRotationPoint(0.0f, 20.0f, 0.0f);
        consoleMain.setTextureSize(32, 32);
        consoleMain.mirror = true;
        setRotation(consoleMain, 0.0f, 0.0f, 0.0f);
        consoleSideL1 = new ModelRenderer(this, 26, 20);
        consoleSideL1.addBox(2.5f, -4.0f, -1.0f, 1, 5, 2);
        consoleSideL1.setRotationPoint(0.0f, 19.0f, 0.0f);
        consoleSideL1.setTextureSize(32, 32);
        consoleSideL1.mirror = true;
        setRotation(consoleSideL1, 0.0f, 0.0f, 0.0f);
        consoleSideR1 = new ModelRenderer(this, 26, 20);
        consoleSideR1.addBox(-3.5f, -4.0f, -1.0f, 1, 5, 2);
        consoleSideR1.setRotationPoint(0.0f, 19.0f, 0.0f);
        consoleSideR1.setTextureSize(32, 32);
        consoleSideR1.mirror = true;
        setRotation(consoleSideR1, 0.0f, 0.0f, 0.0f);
        base1 = new ModelRenderer(this, 0, 26);
        base1.addBox(-2.0f, -2.0f, -2.0f, 4, 2, 4);
        base1.setRotationPoint(0.0f, 24.0f, 0.0f);
        base1.setTextureSize(32, 32);
        base1.mirror = true;
        setRotation(base1, 0.0f, 0.0f, 0.0f);
        base2 = new ModelRenderer(this, 16, 28);
        base2.addBox(-1.5f, -3.0f, -1.5f, 3, 1, 3);
        base2.setRotationPoint(0.0f, 24.0f, 0.0f);
        base2.setTextureSize(32, 32);
        base2.mirror = true;
        setRotation(base2, 0.0f, 0.0f, 0.0f);
        baseStand = new ModelRenderer(this, 0, 23);
        baseStand.addBox(-1.0f, -4.0f, -1.0f, 2, 1, 2);
        baseStand.setRotationPoint(0.0f, 24.0f, 0.0f);
        baseStand.setTextureSize(32, 32);
        baseStand.mirror = true;
        setRotation(baseStand, 0.0f, 0.0f, 0.0f);
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
        swivelFront.render(scale);
        swivelBack.render(scale);
        swivelMain.render(scale);
        axis.render(scale);
        seal.render(scale);
        handCrap.render(scale);
        frontTip.render(scale);
        backTip.render(scale);
        consoleMain.render(scale);
        consoleSideL1.render(scale);
        consoleSideR1.render(scale);
        base1.render(scale);
        base2.render(scale);
        baseStand.render(scale);
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
