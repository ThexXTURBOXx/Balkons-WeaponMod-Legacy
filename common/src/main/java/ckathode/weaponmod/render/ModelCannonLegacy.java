package ckathode.weaponmod.render;

import ckathode.weaponmod.entity.EntityCannon;
import net.minecraft.client.model.geom.ModelPart;

public class ModelCannonLegacy extends WMModel<EntityCannon> {

    public final ModelPart barrel;
    public final ModelPart bottom;
    public final ModelPart frame;
    public final ModelPart seatBottom;
    public final ModelPart seatFrame;

    public ModelCannonLegacy() {
        barrel = new ModelPart(this, 0, 14);
        barrel.addBox(-4.0f, 0.0f, -16.0f, 6, 6, 12, 4.0f);
        barrel.setPos(0.0f, 9.0f, 1.0f);
        barrel.xRot = -6.195919f;
        barrel.mirror = true;
        setRotation(barrel, 0.0f, 0.0f, 0.0f);
        bottom = new ModelPart(this, 0, 1);
        bottom.addBox(0.0f, 0.0f, 0.0f, 12, 1, 12, 1.0f);
        bottom.setPos(-7.0f, -1.0f, -5.0f);
        bottom.mirror = true;
        setRotation(bottom, 0.0f, 0.0f, 0.0f);
        frame = new ModelPart(this, 24, 19);
        frame.addBox(0.0f, 0.0f, 0.0f, 2, 2, 2, 1.0f);
        frame.setPos(-2.0f, 2.0f, 0.0f);
        frame.mirror = true;
        setRotation(frame, 0.0f, 0.0f, 0.0f);
        seatBottom = new ModelPart(this, 6, 5);
        seatBottom.addBox(0.0f, 0.0f, 0.0f, 10, 1, 8, 1.0f);
        seatBottom.setPos(-6.0f, 8.0f, 8.0f);
        seatBottom.mirror = true;
        setRotation(seatBottom, 0.0f, 0.0f, 0.0f);
        seatFrame = new ModelPart(this, 36, 19);
        seatFrame.addBox(0.0f, 0.0f, 0.0f, 2, 1, 12, 1.0f);
        seatFrame.setPos(-2.0f, 6.0f, 0.0f);
        seatFrame.mirror = true;
        setRotation(seatFrame, 0.0f, 0.0f, 0.0f);
    }

}
