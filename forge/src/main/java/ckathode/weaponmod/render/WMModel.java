package ckathode.weaponmod.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.Entity;

public class WMModel<T extends Entity> extends EntityModel<T> {

    private final List<ModelPart> renderers = new ArrayList<>();

    @Override
    @ParametersAreNonnullByDefault
    public void renderToBuffer(PoseStack ms, VertexConsumer buf, int lm, int ov, float r, float g, float b, float a) {
        for (ModelPart model : renderers) {
            model.render(ms, buf, lm, ov, r, g, b, a);
        }
    }

    @Override
    @ParametersAreNonnullByDefault
    public void accept(ModelPart renderer) {
        super.accept(renderer);
        renderers.add(renderer);
    }

    @Override
    @ParametersAreNonnullByDefault
    public void setupAnim(T t, float v, float v1, float v2, float v3, float v4) {
    }

    @ParametersAreNonnullByDefault
    public void setRotation(ModelPart model, float x, float y, float z) {
        model.xRot = x;
        model.yRot = y;
        model.zRot = z;
    }

}
