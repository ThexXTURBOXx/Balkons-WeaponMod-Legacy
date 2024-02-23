package ckathode.weaponmod.render;

import javax.annotation.Nonnull;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
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
        this.textureWidth = 32;
        this.textureHeight = 32;
        (this.console_main_ = new ModelRenderer(this, 12, 20)).addBox(-2.5f, -1.0f, -1.0f, 5, 1, 2);
        this.console_main_.setRotationPoint(0.0f, 20.0f, 0.0f);
        this.console_main_.setTextureSize(32, 32);
        this.console_main_.mirror = true;
        this.setRotation(this.console_main_, 0.0f, 0.0f, 0.0f);
        (this.console_side_l1 = new ModelRenderer(this, 26, 20)).addBox(2.5f, -4.0f, -1.0f, 1, 5, 2);
        this.console_side_l1.setRotationPoint(0.0f, 19.0f, 0.0f);
        this.console_side_l1.setTextureSize(32, 32);
        this.console_side_l1.mirror = true;
        this.setRotation(this.console_side_l1, 0.0f, 0.0f, 0.0f);
        (this.console_side_r1 = new ModelRenderer(this, 26, 20)).addBox(-3.5f, -4.0f, -1.0f, 1, 5, 2);
        this.console_side_r1.setRotationPoint(0.0f, 19.0f, 0.0f);
        this.console_side_r1.setTextureSize(32, 32);
        this.console_side_r1.mirror = true;
        this.setRotation(this.console_side_r1, 0.0f, 0.0f, 0.0f);
        (this.base_1 = new ModelRenderer(this, 0, 26)).addBox(-2.0f, -2.0f, -2.0f, 4, 2, 4);
        this.base_1.setRotationPoint(0.0f, 24.0f, 0.0f);
        this.base_1.setTextureSize(32, 32);
        this.base_1.mirror = true;
        this.setRotation(this.base_1, 0.0f, 0.0f, 0.0f);
        (this.base_2 = new ModelRenderer(this, 16, 28)).addBox(-1.5f, -3.0f, -1.5f, 3, 1, 3);
        this.base_2.setRotationPoint(0.0f, 24.0f, 0.0f);
        this.base_2.setTextureSize(32, 32);
        this.base_2.mirror = true;
        this.setRotation(this.base_2, 0.0f, 0.0f, 0.0f);
        (this.base_stand = new ModelRenderer(this, 0, 23)).addBox(-1.0f, -4.0f, -1.0f, 2, 1, 2);
        this.base_stand.setRotationPoint(0.0f, 24.0f, 0.0f);
        this.base_stand.setTextureSize(32, 32);
        this.base_stand.mirror = true;
        this.setRotation(this.base_stand, 0.0f, 0.0f, 0.0f);
        (this.axis1 = new ModelRenderer(this, 22, 23)).addBox(-0.5f, -5.5f, -0.5f, 1, 3, 1);
        this.axis1.setRotationPoint(0.0f, 24.0f, 0.0f);
        this.axis1.setTextureSize(32, 32);
        this.axis1.mirror = true;
        this.setRotation(this.axis1, 0.0f, 0.0f, 0.0f);
    }

    @Override
    public void render(@Nonnull final Entity entity, final float f, final float f1, final float f2, final float f3,
                       final float f4, final float f5) {
        super.render(entity, f, f1, f2, f3, f4, f5);
        this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        this.console_main_.render(f5);
        this.console_side_l1.render(f5);
        this.console_side_r1.render(f5);
        this.base_1.render(f5);
        this.base_2.render(f5);
        this.base_stand.render(f5);
        this.axis1.render(f5);
    }

    public void setRotation(final ModelRenderer model, final float x, final float y, final float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

    @Override
    public void setRotationAngles(final float f, final float f1, final float f2, final float f3, final float f4,
                                  final float f5, @Nonnull final Entity entity) {
        super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    }
}
