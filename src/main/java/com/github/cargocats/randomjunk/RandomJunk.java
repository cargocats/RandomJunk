package com.github.cargocats.randomjunk;

import com.github.cargocats.randomjunk.registry.RJEntityTypes;
import com.github.cargocats.randomjunk.registry.RJItemGroups;
import com.github.cargocats.randomjunk.registry.RJItems;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RandomJunk implements ModInitializer {
    public static final String MOD_ID = "randomjunk";
    public static final Logger LOG = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        RJEntityTypes.initialize();
        RJItems.initialize();
        RJItemGroups.initialize();

        LOG.info("Initialized Random Junk!");
    }
}
