package com.github.cargocats.randomjunk.registry;

import com.github.cargocats.randomjunk.RandomJunk;
import com.github.cargocats.randomjunk.item.LidocaineItem;
import com.github.cargocats.randomjunk.item.NarcanItem;
import net.minecraft.component.type.ConsumableComponents;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.item.consume.ApplyEffectsConsumeEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

import java.util.function.Function;

import static com.github.cargocats.randomjunk.registry.RJEntityTypes.JOB_APP;
import static com.github.cargocats.randomjunk.registry.RJEntityTypes.NERD;

public class RJItems {
    public static final Item JOB_APP_SPAWN_EGG = registerSpawnEggForEntity(JOB_APP, "job_app_spawn_egg");
    public static final Item NERD_SPAWN_EGG = registerSpawnEggForEntity(NERD, "nerd_spawn_egg");

    public static final Item CRANBERRY = register("cranberry", settings -> new Item(
            settings.food(new FoodComponent.Builder().nutrition(3).saturationModifier(0.6f).build())
    ));

    public static final Item CREATINE = register("creatine", settings -> new Item(settings.fireproof()));
    public static final Item PROTEIN_DRINK = register("protein_drink", settings -> new Item(
            settings.food(
                    new FoodComponent.Builder().nutrition(3).saturationModifier(1.8f).build(),
                    ConsumableComponents.drink().consumeEffect(new ApplyEffectsConsumeEffect(new StatusEffectInstance(StatusEffects.STRENGTH, 60 * 2 * 20, 0))).build()
            ).useRemainder(Items.BUCKET)
    ));

    public static final Item LIDOCAINE = register("lidocaine", LidocaineItem::new);
    public static final Item NARCAN = register("narcan", NarcanItem::new);

    public static void initialize() {
        RandomJunk.LOG.info("Initialized Random Junk items");
    }

    public static Item register(String id, Function<Item.Settings, Item> factory) {
        return register(keyOf(id), factory, new Item.Settings());
    }

    private static Item register(RegistryKey<Item> key, Function<Item.Settings, Item> factory, Item.Settings settings) {
        Item item = factory.apply(settings.registryKey(key));
        if (item instanceof BlockItem blockItem) {
            blockItem.appendBlocks(Item.BLOCK_ITEMS, item);
        }

        return Registry.register(Registries.ITEM, key, item);
    }

    @SuppressWarnings("unused")
    public static Item register(String id, Item.Settings settings) {
        return register(keyOf(id), Item::new, settings);
    }

    private static RegistryKey<Item> keyOf(String id) {
        return RegistryKey.of(RegistryKeys.ITEM, Identifier.of(RandomJunk.MOD_ID, id));
    }

    private static SpawnEggItem registerSpawnEggForEntity(EntityType<? extends MobEntity> entity, String name) {
        RegistryKey<Item> key = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(RandomJunk.MOD_ID, name));

        return Registry.register(Registries.ITEM, key, new SpawnEggItem(
                entity, new Item.Settings().registryKey(key)));
    }
}
