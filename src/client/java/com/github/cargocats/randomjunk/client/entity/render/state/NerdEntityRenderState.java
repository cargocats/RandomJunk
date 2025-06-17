package com.github.cargocats.randomjunk.client.entity.render.state;

import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.entity.AnimationState;

public class NerdEntityRenderState extends LivingEntityRenderState {
    public final AnimationState idlingAnimationState = new AnimationState();
    public final AnimationState yesAnimationState = new AnimationState();
}
