package ckathode.weaponmod.render;

import ckathode.weaponmod.entity.EntityDummy;
import net.minecraft.client.renderer.entity.model.RendererModel;

public class ModelDummy extends WMModel<EntityDummy> {
    public final RendererModel armLeft;
    public final RendererModel armRight;
    public final RendererModel body;
    public final RendererModel head;
    public final RendererModel stick;
    public final RendererModel inside;

    public ModelDummy() {
        armLeft = new RendererModel(this, 0, 24);
        armLeft.addBox(0.0f, 0.0f, 0.0f, 10, 4, 4);
        armLeft.setRotationPoint(6.0f, 18.0f, -2.0f);
        armRight = new RendererModel(this, 0, 24);
        armRight.addBox(-10.0f, 0.0f, 0.0f, 10, 4, 4);
        armRight.setRotationPoint(-6.0f, 18.0f, -2.0f);
        body = new RendererModel(this, 40, 0);
        body.addBox(0.0f, 0.0f, 0.0f, 6, 8, 6, 3.0f);
        body.setRotationPoint(-3.0f, 11.0f, -3.0f);
        inside = new RendererModel(this, 40, 14);
        inside.addBox(0.0f, 0.0f, 0.0f, 6, 8, 6, 2.0f);
        inside.setRotationPoint(-3.0f, 11.0f, -3.0f);
        head = new RendererModel(this, 0, 0);
        head.addBox(-5.0f, 0.0f, -5.0f, 6, 6, 6, 2.0f);
        head.setRotationPoint(1.5f, 25.0f, 1.5f);
        stick = new RendererModel(this, 24, 0);
        stick.addBox(0.0f, 0.0f, 0.0f, 4, 10, 4);
        stick.setRotationPoint(-2.0f, 0.0f, -2.0f);
    }

}
