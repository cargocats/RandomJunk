package com.github.cargocats.randomjunk.init;

import com.github.cargocats.randomjunk.RandomJunk;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.text.Text;

public class RJItemGroups {
    public static final RegistryKey<ItemGroup> RANDOM_JUNK_ITEM_GROUP_KEY = RegistryKey.of(Registries.ITEM_GROUP.getKey(), RandomJunk.id("rj_item_group"));
    public static final ItemGroup RANDOM_JUNK_ITEM_GROUP = FabricItemGroup.builder()
            .icon(() -> new ItemStack(RJItems.CRANBERRY))
            .displayName(Text.translatable("itemGroup.randomjunk"))
            .build();

    public static void init() {
        Registry.register(Registries.ITEM_GROUP, RANDOM_JUNK_ITEM_GROUP_KEY, RANDOM_JUNK_ITEM_GROUP);

        ItemGroupEvents.modifyEntriesEvent(RANDOM_JUNK_ITEM_GROUP_KEY).register(entries -> {
            for (var item : Registries.ITEM) {
                if (RandomJunk.MOD_ID.equals(Registries.ITEM.getId(item).getNamespace())) {
                    entries.add(item);
                }
            }
        });

        RandomJunk.LOG.info("Initialized item groups");
    }
}
