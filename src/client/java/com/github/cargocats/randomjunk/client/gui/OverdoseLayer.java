package com.github.cargocats.randomjunk.client.gui;

import com.github.cargocats.randomjunk.registry.RJStatusEffects;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElement;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.util.Util;
import net.minecraft.util.math.ColorHelper;

public class OverdoseLayer implements HudElement {
    private static final MinecraftClient client = MinecraftClient.getInstance();

    public void render(DrawContext context, RenderTickCounter tickCounter) {
        if (client.player == null) return;

        ClientPlayerEntity player = client.player;

        long now = Util.getMeasuringTimeMs();
        StatusEffectInstance effect = player.getStatusEffect(RJStatusEffects.OVERDOSE);

        if (effect != null && !player.isDead()) {
            int alpha = (int) (80 - 30 * (1 + Math.cos((now / 75.0)) * tickCounter.getTickProgress(false)));

            context.fill(0, 0, context.getScaledWindowWidth(), context.getScaledWindowHeight(), ColorHelper.getArgb(alpha, 255, 0, 0));
        }
    }
}
