package ckathode.weaponmod.render;

import ckathode.weaponmod.entity.EntityCannon;
import net.minecraft.client.renderer.model.ModelRenderer;

public class ModelCannonStandard extends WMModel<EntityCannon> {
    public final ModelRenderer consoleMain;
    public final ModelRenderer consoleSideL1;
    public final ModelRenderer consoleSideR1;
    public final ModelRenderer base1;
    public final ModelRenderer base2;
    public final ModelRenderer baseStand;
    public final ModelRenderer axis1;

    public ModelCannonStandard() {
        textureWidth = 32;
        textureHeight = 32;
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

}
