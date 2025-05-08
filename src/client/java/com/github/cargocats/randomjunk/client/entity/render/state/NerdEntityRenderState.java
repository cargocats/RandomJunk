package com.github.cargocats.randomjunk.client.entity.render.state;

import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.entity.AnimationState;

public class NerdEntityRenderState extends LivingEntityRenderState {
    public final AnimationState idlingAnimationState;
    public final AnimationState yesAnimationState;

    public NerdEntityRenderState() {
        this.idlingAnimationState = new AnimationState();
        this.yesAnimationState = new AnimationState();
    }
}
