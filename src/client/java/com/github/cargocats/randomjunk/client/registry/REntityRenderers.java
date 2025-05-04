package com.github.cargocats.randomjunk.client.registry;

import com.github.cargocats.randomjunk.client.entity.render.JobAppEntityRenderer;
import com.github.cargocats.randomjunk.registry.REntityTypes;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

public class REntityRenderers {
    public static void initialize() {
        EntityRendererRegistry.register(REntityTypes.JOB_APP, JobAppEntityRenderer::new);
    }
}
