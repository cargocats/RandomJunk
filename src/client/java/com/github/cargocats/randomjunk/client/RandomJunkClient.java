package com.github.cargocats.randomjunk.client;

import com.github.cargocats.randomjunk.RandomJunk;
import com.github.cargocats.randomjunk.client.registry.RJEntityModelLayers;
import com.github.cargocats.randomjunk.client.registry.RJEntityRenderers;
import net.fabricmc.api.ClientModInitializer;

public class RandomJunkClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        RJEntityModelLayers.initialize();
        RJEntityRenderers.initialize();

        RandomJunk.LOG.info("Initialized Random Junk client!");
    }
}
