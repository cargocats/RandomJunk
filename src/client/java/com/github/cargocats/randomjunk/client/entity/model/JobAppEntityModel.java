package com.github.cargocats.randomjunk.client.entity.model;

import com.github.cargocats.randomjunk.client.entity.render.state.JobAppEntityRenderState;
import net.minecraft.client.model.Dilation;
import net.minecraft.client.model.ModelData;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.model.ModelPartBuilder;
import net.minecraft.client.model.ModelPartData;
import net.minecraft.client.model.ModelTransform;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.entity.model.EntityModel;

public class JobAppEntityModel extends EntityModel<JobAppEntityRenderState> {
    private final ModelPart root;
    public JobAppEntityModel(ModelPart root) {
        super(root);
        this.root = root.getChild("root");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData root = modelPartData.addChild("root", ModelPartBuilder.create().uv(0, 0).cuboid(-32.0F, -128.0F, 0.0F, 64.0F, 128.0F, 1.0F, new Dilation(0.0F)), ModelTransform.origin(0.0F, 24.0F, 0.0F));
        return TexturedModelData.of(modelData, 256, 256);
    }
}
