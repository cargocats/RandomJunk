package com.github.cargocats.randomjunk.client.entity.render;

import com.github.cargocats.randomjunk.RandomJunk;
import com.github.cargocats.randomjunk.client.entity.model.NerdEntityModel;
import com.github.cargocats.randomjunk.client.entity.render.state.NerdEntityRenderState;
import com.github.cargocats.randomjunk.client.registry.RJEntityModelLayers;
import com.github.cargocats.randomjunk.entity.NerdEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

public class NerdEntityRenderer extends MobEntityRenderer<NerdEntity, NerdEntityRenderState, NerdEntityModel> {
    private static final Identifier TEXTURE = Identifier.of(RandomJunk.MOD_ID, "textures/entity/nerd/texture.png");

    public NerdEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new NerdEntityModel(context.getPart(RJEntityModelLayers.NERD)), 0.5f);
    }

    @Override
    public NerdEntityRenderState createRenderState() {
        return new NerdEntityRenderState();
    }

    @Override
    public Identifier getTexture(NerdEntityRenderState state) {
        return TEXTURE;
    }

    @Override
    public void updateRenderState(NerdEntity nerdEntity, NerdEntityRenderState nerdEntityRenderState, float f) {
        super.updateRenderState(nerdEntity, nerdEntityRenderState, f);

        nerdEntityRenderState.idlingAnimationState.copyFrom(nerdEntity.idlingAnimationState);
        nerdEntityRenderState.yesAnimationState.copyFrom(nerdEntity.yesAnimationState);
    }
}