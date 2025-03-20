package ckathode.weaponmod.render;

import ckathode.weaponmod.entity.EntityCannon;
import net.minecraft.client.renderer.entity.model.RendererModel;

public class ModelCannonLegacy extends WMModel<EntityCannon> {

    public final RendererModel barrel;
    public final RendererModel bottom;
    public final RendererModel frame;
    public final RendererModel seatBottom;
    public final RendererModel seatFrame;

    public ModelCannonLegacy() {
        barrel = new RendererModel(this, 0, 14);
        barrel.addBox(-4.0f, 0.0f, -16.0f, 6, 6, 12, 4.0f);
        barrel.setRotationPoint(0.0f, 9.0f, 1.0f);
        barrel.rotateAngleX = -6.195919f;
        barrel.mirror = true;
        setRotation(barrel, 0.0f, 0.0f, 0.0f);
        bottom = new RendererModel(this, 0, 1);
        bottom.addBox(0.0f, 0.0f, 0.0f, 12, 1, 12, 1.0f);
        bottom.setRotationPoint(-7.0f, -1.0f, -5.0f);
        bottom.mirror = true;
        setRotation(bottom, 0.0f, 0.0f, 0.0f);
        frame = new RendererModel(this, 24, 19);
        frame.addBox(0.0f, 0.0f, 0.0f, 2, 2, 2, 1.0f);
        frame.setRotationPoint(-2.0f, 2.0f, 0.0f);
        frame.mirror = true;
        setRotation(frame, 0.0f, 0.0f, 0.0f);
        seatBottom = new RendererModel(this, 6, 5);
        seatBottom.addBox(0.0f, 0.0f, 0.0f, 10, 1, 8, 1.0f);
        seatBottom.setRotationPoint(-6.0f, 8.0f, 8.0f);
        seatBottom.mirror = true;
        setRotation(seatBottom, 0.0f, 0.0f, 0.0f);
        seatFrame = new RendererModel(this, 36, 19);
        seatFrame.addBox(0.0f, 0.0f, 0.0f, 2, 1, 12, 1.0f);
        seatFrame.setRotationPoint(-2.0f, 6.0f, 0.0f);
        seatFrame.mirror = true;
        setRotation(seatFrame, 0.0f, 0.0f, 0.0f);
    }

}
