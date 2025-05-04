package com.github.cargocats.randomjunk.registry;

import com.github.cargocats.randomjunk.RandomJunk;
import com.github.cargocats.randomjunk.entity.JobAppEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public class REntityTypes {
    public static final EntityType<JobAppEntity> JOB_APP = register(
            "job_app",
            EntityType.Builder.create(
                    JobAppEntity::new,
                    SpawnGroup.MONSTER
            )
                    .dimensions(4.0F, 8.0F)
                    .maxTrackingRange(128)
    );

    public static void initialize() {
        FabricDefaultAttributeRegistry.register(JOB_APP, HostileEntity.createHostileAttributes()
                .add(EntityAttributes.MOVEMENT_SPEED, 0.5f)
                .add(EntityAttributes.ATTACK_DAMAGE, 20.0f)
                .add(EntityAttributes.STEP_HEIGHT, 16.0f)
                .add(EntityAttributes.MAX_HEALTH, 100.0f)
                .add(EntityAttributes.FOLLOW_RANGE, 128.0f)
                .build()
        );

        RandomJunk.LOG.info("Initialized Random Junk entity types.");
    }

    private static <T extends Entity> EntityType<T> register(String id,
                                                             EntityType.Builder<T> builder) {
        RegistryKey<EntityType<?>> key = RegistryKey.of(RegistryKeys.ENTITY_TYPE, Identifier.of(RandomJunk.MOD_ID, id));

        return Registry.register(Registries.ENTITY_TYPE, key, builder.build(key));
    }
}
