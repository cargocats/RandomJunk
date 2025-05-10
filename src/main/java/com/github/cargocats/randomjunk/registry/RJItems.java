package com.github.cargocats.randomjunk.registry;

import com.github.cargocats.randomjunk.RandomJunk;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.Item;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

import static com.github.cargocats.randomjunk.registry.RJEntityTypes.JOB_APP;
import static com.github.cargocats.randomjunk.registry.RJEntityTypes.NERD;

public class RJItems {
    public static final Item JOB_APP_SPAWN_EGG = registerSpawnEggForEntity(JOB_APP, "job_app_spawn_egg");
    public static final Item NERD_SPAWN_EGG = registerSpawnEggForEntity(NERD, "nerd_spawn_egg");
    public static final Item CRANBERRY = registerItem("cranberry", new Item.Settings().food(
            new FoodComponent.Builder()
                    .nutrition(3)
                    .saturationModifier(0.6f)
                    .build()
    ));

    public static void initialize() {
        RandomJunk.LOG.info("Initialized Random Junk items.");
    }

    private static Item registerItem(String name, Item.Settings settings) {
        RegistryKey<Item> key = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(RandomJunk.MOD_ID, name));
        return Registry.register(Registries.ITEM, key, new Item(settings.registryKey(key)));
    }

    private static SpawnEggItem registerSpawnEggForEntity(EntityType<? extends MobEntity> entity, String name) {
        RegistryKey<Item> key = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(RandomJunk.MOD_ID, name));

        return Registry.register(Registries.ITEM, key, new SpawnEggItem(
                entity, new Item.Settings().registryKey(key)));
    }
}
