package ckathode.weaponmod.render;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelDummy extends ModelBase {
    public final ModelRenderer armLeft;
    public final ModelRenderer armRight;
    public final ModelRenderer body;
    public final ModelRenderer head;
    public final ModelRenderer stick;
    public final ModelRenderer inside;

    public ModelDummy() {
        armLeft = new ModelRenderer(this, 0, 24);
        armLeft.addBox(0.0f, 0.0f, 0.0f, 10, 4, 4);
        armLeft.setRotationPoint(6.0f, 18.0f, -2.0f);
        armRight = new ModelRenderer(this, 0, 24);
        armRight.addBox(-10.0f, 0.0f, 0.0f, 10, 4, 4);
        armRight.setRotationPoint(-6.0f, 18.0f, -2.0f);
        body = new ModelRenderer(this, 40, 0);
        body.addBox(0.0f, 0.0f, 0.0f, 6, 8, 6, 3.0f);
        body.setRotationPoint(-3.0f, 11.0f, -3.0f);
        inside = new ModelRenderer(this, 40, 14);
        inside.addBox(0.0f, 0.0f, 0.0f, 6, 8, 6, 2.0f);
        inside.setRotationPoint(-3.0f, 11.0f, -3.0f);
        head = new ModelRenderer(this, 0, 0);
        head.addBox(-5.0f, 0.0f, -5.0f, 6, 6, 6, 2.0f);
        head.setRotationPoint(1.5f, 25.0f, 1.5f);
        stick = new ModelRenderer(this, 24, 0);
        stick.addBox(0.0f, 0.0f, 0.0f, 4, 10, 4);
        stick.setRotationPoint(-2.0f, 0.0f, -2.0f);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float scale) {
        armLeft.render(scale);
        armRight.render(scale);
        body.render(scale);
        inside.render(scale);
        head.render(scale);
        stick.render(scale);
    }
}
