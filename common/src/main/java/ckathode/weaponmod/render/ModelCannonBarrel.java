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

public class ModelCannonBarrel extends WMModel<EntityCannon> {

    public static final ModelLayerLocation CANNON_BARREL_LAYER =
            new ModelLayerLocation(new ResourceLocation(MOD_ID, EntityCannon.ID), "barrel");

    private static final String SWIVEL_FRONT_ID = "swivelFront";
    private static final String SWIVEL_BACK_ID = "swivelBack";
    private static final String SWIVEL_MAIN_ID = "swivelMain";
    private static final String AXIS_ID = "axis";
    private static final String SEAL_ID = "seal";
    private static final String HAND_CRAP_ID = "handCrap";
    private static final String FRONT_TIP_ID = "frontTip";
    private static final String BACK_TIP_ID = "backTip";

    public final ModelPart swivelFront;
    public final ModelPart swivelBack;
    public final ModelPart swivelMain;
    public final ModelPart axis;
    public final ModelPart seal;
    public final ModelPart handCrap;
    public final ModelPart frontTip;
    public final ModelPart backTip;

    public ModelCannonBarrel(ModelPart root) {
        super(root);
        swivelFront = root.getChild(SWIVEL_FRONT_ID);
        swivelBack = root.getChild(SWIVEL_BACK_ID);
        swivelMain = root.getChild(SWIVEL_MAIN_ID);
        axis = root.getChild(AXIS_ID);
        seal = root.getChild(SEAL_ID);
        handCrap = root.getChild(HAND_CRAP_ID);
        frontTip = root.getChild(FRONT_TIP_ID);
        backTip = root.getChild(BACK_TIP_ID);
    }

    public static LayerDefinition createLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition partDefinition = meshDefinition.getRoot();
        partDefinition.addOrReplaceChild(SWIVEL_FRONT_ID,
                CubeListBuilder.create()
                        .texOffs(12, 12)
                        .addBox(-2.0f, -2.0f, -11.0f, 4, 4, 2),
                PartPose.offset(0.0f, 16.0f, 0.0f));
        partDefinition.addOrReplaceChild(SWIVEL_BACK_ID,
                CubeListBuilder.create()
                        .texOffs(12, 0)
                        .addBox(-2.0f, -0.501f, -2.0f, 4, 8, 4),
                PartPose.offsetAndRotation(0.0f, 16.0f, 0.0f, 1.570796f, 0.0f, 0.0f));
        partDefinition.addOrReplaceChild(SWIVEL_MAIN_ID,
                CubeListBuilder.create()
                        .texOffs(0, 0)
                        .addBox(-1.5f, -11.5f, -1.5f, 3, 20, 3),
                PartPose.offsetAndRotation(0.0f, 16.0f, 0.0f, 1.570796f, 0.0f, 0.0f));
        partDefinition.addOrReplaceChild(AXIS_ID,
                CubeListBuilder.create()
                        .texOffs(12, 18)
                        .addBox(-4.0f, -0.5f, -0.5f, 8, 1, 1),
                PartPose.offsetAndRotation(0.0f, 16.0f, 0.0f, 1.570796f, 0.0f, 0.0f));
        partDefinition.addOrReplaceChild(SEAL_ID,
                CubeListBuilder.create()
                        .texOffs(9, 0)
                        .addBox(-1.0f, -2.5f, 5.5f, 2, 1, 1),
                PartPose.offset(0.0f, 16.0f, 0.0f));
        partDefinition.addOrReplaceChild(HAND_CRAP_ID,
                CubeListBuilder.create()
                        .texOffs(28, 0)
                        .addBox(-0.5f, 8.5f, -0.5f, 1, 7, 1),
                PartPose.offsetAndRotation(0.0f, 16.0f, 0.0f, 1.570796f, 0.0f, 0.0f));
        partDefinition.addOrReplaceChild(FRONT_TIP_ID,
                CubeListBuilder.create()
                        .texOffs(24, 12)
                        .addBox(-1.0f, 9.0f, -1.0f, 2, 1, 2),
                PartPose.offsetAndRotation(0.0f, 16.0f, 0.0f, 1.570796f, 0.0f, 0.0f));
        partDefinition.addOrReplaceChild(BACK_TIP_ID,
                CubeListBuilder.create()
                        .texOffs(24, 12)
                        .addBox(-1.0f, 15.5f, -1.0f, 2, 1, 2),
                PartPose.offsetAndRotation(0.0f, 16.0f, 0.0f, 1.570796f, 0.0f, 0.0f));
        return LayerDefinition.create(meshDefinition, 32, 32);
    }

}
