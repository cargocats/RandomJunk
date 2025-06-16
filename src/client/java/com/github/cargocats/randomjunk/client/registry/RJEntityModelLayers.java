package com.github.cargocats.randomjunk.client.registry;

import com.github.cargocats.randomjunk.RandomJunk;
import com.github.cargocats.randomjunk.client.entity.model.JobAppEntityModel;
import com.github.cargocats.randomjunk.client.entity.model.NerdEntityModel;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;

public class RJEntityModelLayers {
    public static final EntityModelLayer JOB_APP = new EntityModelLayer(Identifier.of(RandomJunk.MOD_ID, "job_app_model_layer"), "main");
    public static final EntityModelLayer NERD = new EntityModelLayer(Identifier.of(RandomJunk.MOD_ID, "nerd_model_layer"), "main");

    public static void initialize() {
        EntityModelLayerRegistry.registerModelLayer(JOB_APP, JobAppEntityModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(NERD, NerdEntityModel::getTexturedModelData);

        RandomJunk.LOG.info("Initialized entity model layers");
    }
}
