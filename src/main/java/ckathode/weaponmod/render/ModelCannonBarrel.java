package ckathode.weaponmod.render;

import ckathode.weaponmod.entity.EntityCannon;
import net.minecraft.client.renderer.model.ModelRenderer;

public class ModelCannonBarrel extends WMModel<EntityCannon> {
    public final ModelRenderer swivelFront;
    public final ModelRenderer swivelBack;
    public final ModelRenderer swivelMain;
    public final ModelRenderer axis;
    public final ModelRenderer seal;
    public final ModelRenderer handCrap;
    public final ModelRenderer frontTip;
    public final ModelRenderer backTip;

    public ModelCannonBarrel() {
        textureWidth = 32;
        textureHeight = 32;
        swivelFront = new ModelRenderer(this, 12, 12);
        swivelFront.addBox(-2.0f, -2.0f, -11.0f, 4, 4, 2);
        swivelFront.setRotationPoint(0.0f, 16.0f, 0.0f);
        swivelFront.setTextureSize(32, 32);
        swivelFront.mirror = true;
        setRotation(swivelFront, 0.0f, 0.0f, 0.0f);
        swivelBack = new ModelRenderer(this, 12, 0);
        swivelBack.addBox(-2.0f, -0.501f, -2.0f, 4, 8, 4);
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
    }

}
