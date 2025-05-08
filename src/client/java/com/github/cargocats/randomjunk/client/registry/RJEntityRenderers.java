package com.github.cargocats.randomjunk.client.registry;

import com.github.cargocats.randomjunk.RandomJunk;
import com.github.cargocats.randomjunk.client.entity.render.JobAppEntityRenderer;
import com.github.cargocats.randomjunk.client.entity.render.NerdEntityRenderer;
import com.github.cargocats.randomjunk.registry.RJEntityTypes;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

public class RJEntityRenderers {
    public static void initialize() {
        EntityRendererRegistry.register(RJEntityTypes.JOB_APP, JobAppEntityRenderer::new);
        EntityRendererRegistry.register(RJEntityTypes.NERD, NerdEntityRenderer::new);
        RandomJunk.LOG.info("Initialized RandomJunk entity renderers");
    }
}
