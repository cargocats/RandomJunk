package com.github.cargocats.randomjunk.client;

import com.github.cargocats.randomjunk.RandomJunk;
import com.github.cargocats.randomjunk.client.gui.OverdoseLayer;
import com.github.cargocats.randomjunk.client.init.RJEntityModelLayers;
import com.github.cargocats.randomjunk.client.init.RJEntityRenderers;
import com.github.cargocats.randomjunk.client.init.RJPackets;
import com.github.cargocats.randomjunk.client.init.tints.PipeBombTintSource;
import com.github.cargocats.randomjunk.client.screen.PasswordScreen;
import com.github.cargocats.randomjunk.component.CountdownTimestampComponent;
import com.github.cargocats.randomjunk.init.RJComponents;
import com.github.cargocats.randomjunk.init.RJItems;
import com.github.cargocats.randomjunk.init.RJScreens;
import com.github.cargocats.randomjunk.item.LidocaineItem;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.hud.VanillaHudElements;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.render.item.tint.TintSourceTypes;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class RandomJunkClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        TintSourceTypes.ID_MAPPER.put(RandomJunk.id("pipe_bomb_tint"), PipeBombTintSource.CODEC);

        RJPackets.init();
        RJEntityModelLayers.init();
        RJEntityRenderers.init();
        registerItemTooltips();

        HandledScreens.register(RJScreens.PASSWORD_SCREEN, PasswordScreen::new);
        HudElementRegistry.attachElementAfter(VanillaHudElements.STATUS_EFFECTS, RandomJunk.id("overdose_layer"), new OverdoseLayer());

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
