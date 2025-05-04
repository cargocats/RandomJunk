package com.github.cargocats.randomjunk.client.registry;

import com.github.cargocats.randomjunk.RandomJunk;
import com.github.cargocats.randomjunk.client.entity.model.JobAppEntityModel;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;

public class REntityModelLayers {
    public static final EntityModelLayer JOB_APP = new EntityModelLayer(Identifier.of(RandomJunk.MOD_ID, "job_app_mlayer"), "main");

    public static void initialize() {
        RandomJunk.LOG.info("Initialized RandomJunk entity model layers");
        EntityModelLayerRegistry.registerModelLayer(JOB_APP, JobAppEntityModel::getTexturedModelData);
    }
}
