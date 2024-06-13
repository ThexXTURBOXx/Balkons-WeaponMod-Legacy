package ckathode.weaponmod.render;

import ckathode.weaponmod.entity.EntityDummy;
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

public class ModelDummy extends WMModel<EntityDummy> {

    public static final ModelLayerLocation MAIN_LAYER =
            new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(MOD_ID, EntityDummy.ID), "main");

    private static final String ARM_LEFT_ID = "armLeft";
    private static final String ARM_RIGHT_ID = "armRight";
    private static final String BODY_ID = "body";
    private static final String HEAD_ID = "head";
    private static final String STICK_ID = "stick";
    private static final String INSIDE_ID = "inside";

    public final ModelPart armLeft;
    public final ModelPart armRight;
    public final ModelPart body;
    public final ModelPart head;
    public final ModelPart stick;
    public final ModelPart inside;

    public ModelDummy(ModelPart root) {
        super(root);
        armLeft = root.getChild(ARM_LEFT_ID);
        armRight = root.getChild(ARM_RIGHT_ID);
        body = root.getChild(BODY_ID);
        head = root.getChild(HEAD_ID);
        stick = root.getChild(STICK_ID);
        inside = root.getChild(INSIDE_ID);
    }

    public static LayerDefinition createLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition partDefinition = meshDefinition.getRoot();
        partDefinition.addOrReplaceChild(ARM_LEFT_ID,
                CubeListBuilder.create()
                        .texOffs(0, 24)
                        .addBox(0.0f, 0.0f, 0.0f, 10, 4, 4),
                PartPose.offset(6.0f, 18.0f, -2.0f));
        partDefinition.addOrReplaceChild(ARM_RIGHT_ID,
                CubeListBuilder.create()
                        .texOffs(0, 24)
                        .addBox(-10.0f, 0.0f, 0.0f, 10, 4, 4),
                PartPose.offset(-6.0f, 18.0f, -2.0f));
        partDefinition.addOrReplaceChild(BODY_ID,
                CubeListBuilder.create()
                        .texOffs(40, 0)
                        .addBox(0.0f, 0.0f, 0.0f, 6, 8, 6, new CubeDeformation(3.0f)),
                PartPose.offset(-3.0f, 11.0f, -3.0f));
        partDefinition.addOrReplaceChild(INSIDE_ID,
                CubeListBuilder.create()
                        .texOffs(40, 14)
                        .addBox(0.0f, 0.0f, 0.0f, 6, 8, 6, new CubeDeformation(2.0f)),
                PartPose.offset(-3.0f, 11.0f, -3.0f));
        partDefinition.addOrReplaceChild(HEAD_ID,
                CubeListBuilder.create()
                        .texOffs(0, 0)
                        .addBox(-5.0f, 0.0f, -5.0f, 6, 6, 6, new CubeDeformation(2.0f)),
                PartPose.offset(1.5f, 25.0f, 1.5f));
        partDefinition.addOrReplaceChild(STICK_ID,
                CubeListBuilder.create()
                        .texOffs(24, 0)
                        .addBox(0.0f, 0.0f, 0.0f, 4, 10, 4),
                PartPose.offset(-2.0f, 0.0f, -2.0f));
        return LayerDefinition.create(meshDefinition, 64, 32);
    }

}
