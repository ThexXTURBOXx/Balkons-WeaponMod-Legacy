package ckathode.weaponmod.render;

import ckathode.weaponmod.entity.EntityCannon;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.resources.ResourceLocation;

import static ckathode.weaponmod.BalkonsWeaponMod.MOD_ID;

public class ModelCannonLegacy extends WMModel<EntityCannon> {

    public static final ModelLayerLocation CANNON_LEGACY_LAYER =
            new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(MOD_ID, EntityCannon.ID), "cannon_legacy");

    private static final String BARREL_ID = "barrel";
    private static final String BOTTOM_ID = "bottom";
    private static final String FRAME_ID = "frame";
    private static final String SEAT_BOTTOM_ID = "seatBottom";
    private static final String SEAT_FRAME_ID = "seatFrame";

    public final ModelPart barrel;
    public final ModelPart bottom;
    public final ModelPart frame;
    public final ModelPart seatBottom;
    public final ModelPart seatFrame;

    public ModelCannonLegacy(ModelPart root) {
        super(root);
        barrel = root.getChild(BARREL_ID);
        bottom = root.getChild(BOTTOM_ID);
        frame = root.getChild(FRAME_ID);
        seatBottom = root.getChild(SEAT_BOTTOM_ID);
        seatFrame = root.getChild(SEAT_FRAME_ID);
    }

    public static LayerDefinition createLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition partDefinition = meshDefinition.getRoot();
        partDefinition.addOrReplaceChild(BARREL_ID,
                CubeListBuilder.create()
                        .texOffs(0, 14)
                        .addBox(-4.0f, 0.0f, -16.0f, 6, 6, 12, new CubeDeformation(4.0f)),
                PartPose.offsetAndRotation(0.0f, 9.0f, 1.0f, -6.195919f, 0, 0));
        partDefinition.addOrReplaceChild(BOTTOM_ID,
                CubeListBuilder.create()
                        .texOffs(0, 1)
                        .addBox(0.0f, 0.0f, 0.0f, 12, 1, 12, new CubeDeformation(1.0f)),
                PartPose.offset(-7.0f, -1.0f, -5.0f));
        partDefinition.addOrReplaceChild(FRAME_ID,
                CubeListBuilder.create()
                        .texOffs(24, 19)
                        .addBox(0.0f, 0.0f, 0.0f, 2, 2, 2, new CubeDeformation(1.0f)),
                PartPose.offset(-2.0f, 2.0f, 0.0f));
        partDefinition.addOrReplaceChild(SEAT_BOTTOM_ID,
                CubeListBuilder.create()
                        .texOffs(6, 5)
                        .addBox(0.0f, 0.0f, 0.0f, 10, 1, 8, new CubeDeformation(1.0f)),
                PartPose.offset(-6.0f, 8.0f, 8.0f));
        partDefinition.addOrReplaceChild(SEAT_FRAME_ID,
                CubeListBuilder.create()
                        .texOffs(36, 19)
                        .addBox(0.0f, 0.0f, 0.0f, 2, 1, 12, new CubeDeformation(1.0f)),
                PartPose.offset(-2.0f, 6.0f, 0.0f));
        return LayerDefinition.create(meshDefinition, 64, 32);
    }

}
