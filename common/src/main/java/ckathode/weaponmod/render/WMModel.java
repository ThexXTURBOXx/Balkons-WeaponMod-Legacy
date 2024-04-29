package ckathode.weaponmod.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;

public class WMModel<T extends Entity> extends EntityModel<T> {

    private final List<ModelPart> renderers = new ArrayList<>();

    @Override
    public void renderToBuffer(@NotNull PoseStack ms, @NotNull VertexConsumer buf, int lm, int ov, float r, float g,
                               float b, float a) {
        for (ModelPart model : renderers) {
            model.render(ms, buf, lm, ov, r, g, b, a);
        }
    }

    @Override
    public void accept(@NotNull ModelPart renderer) {
        super.accept(renderer);
        renderers.add(renderer);
    }

    @Override
    public void setupAnim(@NotNull T t, float v, float v1, float v2, float v3, float v4) {
    }

    public void setRotation(@NotNull ModelPart model, float x, float y, float z) {
        model.xRot = x;
        model.yRot = y;
        model.zRot = z;
    }

}
