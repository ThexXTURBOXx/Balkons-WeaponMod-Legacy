package ckathode.weaponmod.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class WMModel<T extends Entity> extends EntityModel<T> {

    private final List<ModelRenderer> renderers = new ArrayList<>();

    @Override
    @ParametersAreNonnullByDefault
    public void renderToBuffer(MatrixStack ms, IVertexBuilder buf, int lm, int ov, float r, float g, float b, float a) {
        for (ModelRenderer model : renderers) {
            model.render(ms, buf, lm, ov, r, g, b, a);
        }
    }

    @Override
    @ParametersAreNonnullByDefault
    public void accept(ModelRenderer renderer) {
        super.accept(renderer);
        renderers.add(renderer);
    }

    @Override
    @ParametersAreNonnullByDefault
    public void setupAnim(T t, float v, float v1, float v2, float v3, float v4) {
    }

    @ParametersAreNonnullByDefault
    public void setRotation(ModelRenderer model, float x, float y, float z) {
        model.xRot = x;
        model.yRot = y;
        model.zRot = z;
    }

}
