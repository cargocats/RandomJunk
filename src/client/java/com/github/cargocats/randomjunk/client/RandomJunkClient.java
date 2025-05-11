package com.github.cargocats.randomjunk.client;

import com.github.cargocats.randomjunk.RandomJunk;
import com.github.cargocats.randomjunk.client.registry.RJEntityModelLayers;
import com.github.cargocats.randomjunk.client.registry.RJEntityRenderers;
import com.github.cargocats.randomjunk.registry.RJItems;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class RandomJunkClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        RJEntityModelLayers.initialize();
        RJEntityRenderers.initialize();
        registerItemTooltips();

        RandomJunk.LOG.info("Initialized Random Junk client!");
    }

    private static void registerItemTooltips() {
        ItemTooltipCallback.EVENT.register((stack, tooltipContext, tooltipType, textList) -> {
            if (stack.isOf(RJItems.LIDOCAINE)) {
                textList.add(Text.literal("Extinguishes and gives regeneration if on fire.").formatted(Formatting.GRAY));
                textList.add(Text.literal("Use too much and you might overdose!").formatted(Formatting.RED));
                textList.add(Text.literal("Use narcan to stop your overdose.").formatted(Formatting.DARK_GRAY));
            }
        });
    }
}
