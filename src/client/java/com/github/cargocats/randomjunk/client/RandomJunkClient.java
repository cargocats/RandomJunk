package com.github.cargocats.randomjunk.client;

import com.github.cargocats.randomjunk.RandomJunk;
import com.github.cargocats.randomjunk.client.registry.REntityModelLayers;
import com.github.cargocats.randomjunk.client.registry.REntityRenderers;
import net.fabricmc.api.ClientModInitializer;

public class RandomJunkClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        REntityModelLayers.initialize();
        REntityRenderers.initialize();

        RandomJunk.LOG.info("Initialized Random Junk client!");
    }
}
