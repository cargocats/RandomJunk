package com.github.cargocats.randomjunk.client.entity.render;

import com.github.cargocats.randomjunk.RandomJunk;
import com.github.cargocats.randomjunk.client.entity.model.JobAppEntityModel;
import com.github.cargocats.randomjunk.client.entity.render.state.JobAppEntityRenderState;
import com.github.cargocats.randomjunk.client.init.RJEntityModelLayers;
import com.github.cargocats.randomjunk.entity.JobAppEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

public class JobAppEntityRenderer extends MobEntityRenderer<JobAppEntity, JobAppEntityRenderState, JobAppEntityModel> {
    private static final Identifier TEXTURE = RandomJunk.id("textures/entity/job_app/texture.png");

    public JobAppEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new JobAppEntityModel(context.getPart(RJEntityModelLayers.JOB_APP)), 2.0f);
    }

    @Override
    public JobAppEntityRenderState createRenderState() {
        return new JobAppEntityRenderState();
    }

    @Override
    public Identifier getTexture(JobAppEntityRenderState state) {
        return TEXTURE;
    }
}