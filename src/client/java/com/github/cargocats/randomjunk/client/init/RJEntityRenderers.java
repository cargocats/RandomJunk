package com.github.cargocats.randomjunk.client.init;

import com.github.cargocats.randomjunk.RandomJunk;
import com.github.cargocats.randomjunk.client.entity.render.JobAppEntityRenderer;
import com.github.cargocats.randomjunk.client.entity.render.NerdEntityRenderer;
import com.github.cargocats.randomjunk.init.RJEntityTypes;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

public class RJEntityRenderers {
    public static void init() {
        EntityRendererRegistry.register(RJEntityTypes.JOB_APP, JobAppEntityRenderer::new);
        EntityRendererRegistry.register(RJEntityTypes.NERD, NerdEntityRenderer::new);

        RandomJunk.LOG.info("Initialized entity renderers");
    }
}
