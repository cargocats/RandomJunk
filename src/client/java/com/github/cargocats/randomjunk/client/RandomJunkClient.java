package com.github.cargocats.randomjunk.client;

import com.github.cargocats.randomjunk.RandomJunk;
import com.github.cargocats.randomjunk.client.gui.OverdoseLayer;
import com.github.cargocats.randomjunk.client.registry.tints.PipeBombTintSource;
import com.github.cargocats.randomjunk.client.registry.RJEntityModelLayers;
import com.github.cargocats.randomjunk.client.registry.RJEntityRenderers;
import com.github.cargocats.randomjunk.components.CountdownTimestampComponent;
import com.github.cargocats.randomjunk.item.LidocaineItem;
import com.github.cargocats.randomjunk.network.packet.SyncLidocaineUsagesS2C;
import com.github.cargocats.randomjunk.registry.RJComponents;
import com.github.cargocats.randomjunk.registry.RJItems;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.hud.VanillaHudElements;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.item.tint.TintSourceTypes;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

public class RandomJunkClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        TintSourceTypes.ID_MAPPER.put(Identifier.of(RandomJunk.MOD_ID, "pipe_bomb_tint"), PipeBombTintSource.CODEC);

        RJEntityModelLayers.initialize();
        RJEntityRenderers.initialize();
        registerItemTooltips();

        HudElementRegistry.attachElementAfter(VanillaHudElements.STATUS_EFFECTS, Identifier.of(RandomJunk.MOD_ID, "overdose_layer"), new OverdoseLayer());

        ClientPlayNetworking.registerGlobalReceiver(SyncLidocaineUsagesS2C.ID, (payload, context) -> {
            RandomJunk.LOG.info("Client: received payload for sync lidocaine, usages: {}", payload.usages());
            LidocaineItem.clientLidocaineUsages = payload.usages();
        });

        RandomJunk.LOG.info("Initialized client!");
    }

    private static void registerItemTooltips() {
        MinecraftClient client = MinecraftClient.getInstance();

        ItemTooltipCallback.EVENT.register((stack, tooltipContext, tooltipType, textList) -> {
            if (stack.isOf(RJItems.LIDOCAINE)) {
                textList.add(Text.literal("Extinguishes and gives regeneration if on fire.").formatted(Formatting.GRAY));
                textList.add(Text.literal("Use too much and you might overdose!").formatted(Formatting.RED));
                textList.add(Text.literal("Use narcan to stop your overdose.").formatted(Formatting.DARK_GRAY));
                textList.add(Text.literal("Usages before overdose: " + (LidocaineItem.OVERDOSE_THRESHOLD + 1 - LidocaineItem.clientLidocaineUsages)).formatted(Formatting.DARK_GRAY));
            } else if (stack.isOf(RJItems.PIPE_BOMB)) {
                CountdownTimestampComponent timer = stack.get(RJComponents.COUNTDOWN_TIMER);
                if (timer != null) {
                    if (client.world == null) return;
                    textList.add(Text.literal("Explodes in: " + (timer.end() - client.world.getTime()) + " ticks").formatted(Formatting.DARK_RED));
                } else {
                    textList.add(Text.literal("Explodes in: " + CountdownTimestampComponent.DEFAULT_LENGTH + " ticks").formatted(Formatting.DARK_RED));
                }
            }
        });
    }
}
