package com.github.cargocats.randomjunk.init;

import com.github.cargocats.randomjunk.RandomJunk;
import com.github.cargocats.randomjunk.entity.JobAppEntity;
import com.github.cargocats.randomjunk.entity.NerdEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;

public class RJEntityTypes {
    public static final EntityType<JobAppEntity> JOB_APP = registerEntity(
            "job_app",
            EntityType.Builder.create(JobAppEntity::new, SpawnGroup.MONSTER)
                    .dimensions(4.0f, 8.0f)
                    .maxTrackingRange(128)
    );

    public static final EntityType<NerdEntity> NERD = registerEntity(
            "nerd",
            EntityType.Builder.create(NerdEntity::new, SpawnGroup.CREATURE)
                    .dimensions(0.6f, 1.8f).maxTrackingRange(32)
    );

    public static void init() {
        FabricDefaultAttributeRegistry.register(JOB_APP, JobAppEntity.createJobAppAttributes());
        FabricDefaultAttributeRegistry.register(NERD, NerdEntity.createNerdAttributes());

        RandomJunk.LOG.info("Initialized entity types");
    }

    private static <T extends Entity> EntityType<T> registerEntity(String id,
                                                             EntityType.Builder<T> builder) {
        RegistryKey<EntityType<?>> key = RegistryKey.of(RegistryKeys.ENTITY_TYPE, RandomJunk.id(id));

        return Registry.register(Registries.ENTITY_TYPE, key, builder.build(key));
    }
}
