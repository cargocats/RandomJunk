package com.github.cargocats.randomjunk.init;

import com.github.cargocats.randomjunk.RandomJunk;
import com.github.cargocats.randomjunk.item.LidocaineItem;
import com.github.cargocats.randomjunk.item.NarcanItem;
import com.github.cargocats.randomjunk.item.PipebombItem;
import net.minecraft.block.Block;
import net.minecraft.component.type.ConsumableComponents;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.item.consume.ApplyEffectsConsumeEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;

import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.UnaryOperator;

public class RJItems {
    public static final Item JOB_APP_SPAWN_EGG = register("job_app_spawn_egg", settings -> new SpawnEggItem(RJEntityTypes.JOB_APP, settings));
    public static final Item NERD_SPAWN_EGG = register("nerd_spawn_egg", settings -> new SpawnEggItem(RJEntityTypes.NERD, settings));

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
    public static final Item PIPE_BOMB = register("pipe_bomb", PipebombItem::new, new Item.Settings());

    // Register block items

    public static final Item TOILET_PAPER_BLOCK = register(RJBlocks.TOILET_PAPER);
    public static final Item SAFE_BLOCK = register(RJBlocks.SAFE_BLOCK);

    public static void init() {
        RandomJunk.LOG.info("Initialized items");
    }

    private static Function<Item.Settings, Item> createBlockItemWithUniqueName(Block block) {
        return settings -> new BlockItem(block, settings.useItemPrefixedTranslationKey());
    }

    private static RegistryKey<Item> keyOf(String id) {
        return RegistryKey.of(RegistryKeys.ITEM, RandomJunk.id(id));
    }

    private static RegistryKey<Item> keyOf(RegistryKey<Block> blockKey) {
        return RegistryKey.of(RegistryKeys.ITEM, blockKey.getValue());
    }

    public static Item register(Block block) {
        return register(block, BlockItem::new);
    }

    public static Item register(Block block, Item.Settings settings) {
        return register(block, BlockItem::new, settings);
    }

    public static Item register(Block block, UnaryOperator<Item.Settings> settingsOperator) {
        return register(block, (blockx, settings) -> new BlockItem(blockx, settingsOperator.apply(settings)));
    }

    public static Item register(Block block, Block... blocks) {
        Item item = register(block);

        for (Block block2 : blocks) {
            Item.BLOCK_ITEMS.put(block2, item);
        }

        return item;
    }

    public static Item register(Block block, BiFunction<Block, Item.Settings, Item> factory) {
        return register(block, factory, new Item.Settings());
    }

    public static Item register(Block block, BiFunction<Block, Item.Settings, Item> factory, Item.Settings settings) {
        Optional<RegistryKey<Block>> registryKey = Registries.BLOCK.getKey(block);

        if (registryKey.isEmpty()) {
            throw new IllegalStateException("No registry key for block " + block);
        }

        return register(
                keyOf(registryKey.get()), itemSettings -> factory.apply(block, itemSettings), settings.useBlockPrefixedTranslationKey()
        );
    }

    public static Item register(String id, Function<Item.Settings, Item> factory) {
        return register(keyOf(id), factory, new Item.Settings());
    }

    public static Item register(String id, Function<Item.Settings, Item> factory, Item.Settings settings) {
        return register(keyOf(id), factory, settings);
    }

    public static Item register(String id, Item.Settings settings) {
        return register(keyOf(id), Item::new, settings);
    }

    public static Item register(String id) {
        return register(keyOf(id), Item::new, new Item.Settings());
    }

    public static Item register(RegistryKey<Item> key, Function<Item.Settings, Item> factory) {
        return register(key, factory, new Item.Settings());
    }

    public static Item register(RegistryKey<Item> key, Function<Item.Settings, Item> factory, Item.Settings settings) {
        Item item = factory.apply(settings.registryKey(key));
        if (item instanceof BlockItem blockItem) {
            blockItem.appendBlocks(Item.BLOCK_ITEMS, item);
        }

        RJItemGroups.itemsToGroup.add(new ItemStack(item));

        return Registry.register(Registries.ITEM, key, item);
    }
}
