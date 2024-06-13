package ckathode.weaponmod.render;

import ckathode.weaponmod.entity.EntityCannon;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.resources.ResourceLocation;

import static ckathode.weaponmod.BalkonsWeaponMod.MOD_ID;

public class ModelCannonStandard extends WMModel<EntityCannon> {

    public static final ModelLayerLocation CANNON_STANDARD_LAYER =
            new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(MOD_ID, EntityCannon.ID), "standard");

    private static final String CONSOLE_MAIN_ID = "consoleMain";
    private static final String CONSOLE_SIDE_L1_ID = "consoleSideL1";
    private static final String CONSOLE_SIDE_R1_ID = "consoleSideR1";
    private static final String BASE_1_ID = "base1";
    private static final String BASE_2_ID = "base2";
    private static final String BASE_STAND_ID = "baseStand";
    private static final String AXIS_1_ID = "axis1";

    public final ModelPart consoleMain;
    public final ModelPart consoleSideL1;
    public final ModelPart consoleSideR1;
    public final ModelPart base1;
    public final ModelPart base2;
    public final ModelPart baseStand;
    public final ModelPart axis1;

    public ModelCannonStandard(ModelPart root) {
        super(root);
        consoleMain = root.getChild(CONSOLE_MAIN_ID);
        consoleSideL1 = root.getChild(CONSOLE_SIDE_L1_ID);
        consoleSideR1 = root.getChild(CONSOLE_SIDE_R1_ID);
        base1 = root.getChild(BASE_1_ID);
        base2 = root.getChild(BASE_2_ID);
        baseStand = root.getChild(BASE_STAND_ID);
        axis1 = root.getChild(AXIS_1_ID);
    }

    public static LayerDefinition createLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition partDefinition = meshDefinition.getRoot();
        partDefinition.addOrReplaceChild(CONSOLE_MAIN_ID,
                CubeListBuilder.create()
                        .texOffs(12, 20)
                        .addBox(-2.5f, -1.0f, -1.0f, 5, 1, 2),
                PartPose.offset(0.0f, 20.0f, 0.0f));
        partDefinition.addOrReplaceChild(CONSOLE_SIDE_L1_ID,
                CubeListBuilder.create()
                        .texOffs(26, 20)
                        .addBox(2.5f, -4.0f, -1.0f, 1, 5, 2),
                PartPose.offset(0.0f, 19.0f, 0.0f));
        partDefinition.addOrReplaceChild(CONSOLE_SIDE_R1_ID,
                CubeListBuilder.create()
                        .texOffs(26, 20)
                        .addBox(-3.5f, -4.0f, -1.0f, 1, 5, 2),
                PartPose.offset(0.0f, 19.0f, 0.0f));
        partDefinition.addOrReplaceChild(BASE_1_ID,
                CubeListBuilder.create()
                        .texOffs(0, 26)
                        .addBox(-2.0f, -2.0f, -2.0f, 4, 2, 4),
                PartPose.offset(0.0f, 24.0f, 0.0f));
        partDefinition.addOrReplaceChild(BASE_2_ID,
                CubeListBuilder.create()
                        .texOffs(16, 28)
                        .addBox(-1.5f, -3.0f, -1.5f, 3, 1, 3),
                PartPose.offset(0.0f, 24.0f, 0.0f));
        partDefinition.addOrReplaceChild(BASE_STAND_ID,
                CubeListBuilder.create()
                        .texOffs(0, 23)
                        .addBox(-1.0f, -4.0f, -1.0f, 2, 1, 2),
                PartPose.offset(0.0f, 24.0f, 0.0f));
        partDefinition.addOrReplaceChild(AXIS_1_ID,
                CubeListBuilder.create()
                        .texOffs(22, 23)
                        .addBox(-0.5f, -5.5f, -0.5f, 1, 3, 1),
                PartPose.offset(0.0f, 24.0f, 0.0f));
        return LayerDefinition.create(meshDefinition, 32, 32);
    }

}
