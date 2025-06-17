package com.github.cargocats.randomjunk.client.entity.model;

import com.github.cargocats.randomjunk.client.entity.render.animation.NerdAnimations;
import com.github.cargocats.randomjunk.client.entity.render.state.NerdEntityRenderState;
import net.minecraft.client.model.Dilation;
import net.minecraft.client.model.ModelData;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.model.ModelPartBuilder;
import net.minecraft.client.model.ModelPartData;
import net.minecraft.client.model.ModelTransform;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.entity.animation.Animation;
import net.minecraft.client.render.entity.model.EntityModel;

public class NerdEntityModel extends EntityModel<NerdEntityRenderState> {
    private final Animation walkingAnimation;
	private final Animation idleAnimation;
	private final Animation yesAnimation;

	public NerdEntityModel(ModelPart root) {
		super(root);

		this.walkingAnimation = NerdAnimations.WALK.createAnimation(root);
		this.idleAnimation = NerdAnimations.IDLING.createAnimation(root);
		this.yesAnimation = NerdAnimations.YES.createAnimation(root);
	}

	public static TexturedModelData getTexturedModelData() {
		return TexturedModelData.of(getModelData(), 64, 64);
	}

	@SuppressWarnings("unused")
	protected static ModelData getModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData player = modelPartData.addChild("player", ModelPartBuilder.create(), ModelTransform.origin(0.0F, 24.0F, 0.0F));

		ModelPartData body = player.addChild("body", ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));

		ModelPartData head = body.addChild("head", ModelPartBuilder.create().uv(0, 0).cuboid(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new Dilation(0.0F)), ModelTransform.origin(0.0F, -24.0F, 1.0F));

		ModelPartData torso = body.addChild("torso", ModelPartBuilder.create().uv(0, 16).cuboid(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new Dilation(0.0F)), ModelTransform.origin(0.0F, -24.0F, 1.0F));

		ModelPartData limbs = body.addChild("limbs", ModelPartBuilder.create(), ModelTransform.origin(0.0F, -18.0F, 0.0F));

		ModelPartData legs = limbs.addChild("legs", ModelPartBuilder.create(), ModelTransform.origin(0.0F, 6.0F, 1.0F));

		ModelPartData right_leg = legs.addChild("right_leg", ModelPartBuilder.create().uv(24, 16).cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.0F)), ModelTransform.origin(-2.0F, 0.0F, 0.0F));

		ModelPartData left_leg = legs.addChild("left_leg", ModelPartBuilder.create().uv(16, 32).cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.0F)), ModelTransform.origin(2.0F, 0.0F, 0.0F));

		ModelPartData arms = limbs.addChild("arms", ModelPartBuilder.create(), ModelTransform.origin(0.0F, -6.0F, 0.0F));

		ModelPartData right_arm = arms.addChild("right_arm", ModelPartBuilder.create().uv(32, 0).cuboid(-4.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.0F)), ModelTransform.origin(-4.0F, 0.0F, 1.0F));

		ModelPartData left_arm = arms.addChild("left_arm", ModelPartBuilder.create().uv(0, 32).cuboid(0.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.0F)), ModelTransform.origin(4.0F, 0.0F, 1.0F));

		return modelData;
	}

	@Override
	public void setAngles(NerdEntityRenderState nerdEntityRenderState) {
		super.setAngles(nerdEntityRenderState);

		this.walkingAnimation.applyWalking(nerdEntityRenderState.limbSwingAnimationProgress, nerdEntityRenderState.limbSwingAmplitude, 2.0f, 2.5f);
		this.idleAnimation.apply(nerdEntityRenderState.idlingAnimationState, nerdEntityRenderState.age);
		this.yesAnimation.apply(nerdEntityRenderState.yesAnimationState, nerdEntityRenderState.age);
	}
}