package ckathode.weaponmod.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import java.util.function.Function;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;

public class WMModel<T extends Entity> extends EntityModel<T> {

    private final ModelPart root;

    public WMModel(ModelPart root) {
        this(root, RenderType::entityCutoutNoCull);
    }

    public WMModel(ModelPart root, Function<ResourceLocation, RenderType> function) {
        super(function);
        this.root = root;
    }

    @Override
    public void renderToBuffer(@NotNull PoseStack poseStack, @NotNull VertexConsumer vertexConsumer,
                               int lm, int ov, int color) {
        root.getAllParts().forEach(modelPart -> modelPart.render(poseStack, vertexConsumer, lm, ov, color));
    }

    @Override
    public void setupAnim(@NotNull T t, float v, float v1, float v2, float v3, float v4) {
    }

}
