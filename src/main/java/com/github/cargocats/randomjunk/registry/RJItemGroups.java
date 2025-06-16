package com.github.cargocats.randomjunk.registry;

import com.github.cargocats.randomjunk.RandomJunk;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class RJItemGroups {
    public static final RegistryKey<ItemGroup> RANDOM_JUNK_ITEM_GROUP_KEY = RegistryKey.of(Registries.ITEM_GROUP.getKey(), Identifier.of(RandomJunk.MOD_ID, "rj_item_group"));
    public static final ItemGroup RANDOM_JUNK_ITEM_GROUP = FabricItemGroup.builder()
            .icon(() -> new ItemStack(RJItems.CRANBERRY))
            .displayName(Text.translatable("itemGroup.randomjunk"))
            .build();

    public static void initialize() {
        Registry.register(Registries.ITEM_GROUP, RANDOM_JUNK_ITEM_GROUP_KEY, RANDOM_JUNK_ITEM_GROUP);
        ItemGroupEvents.modifyEntriesEvent(RANDOM_JUNK_ITEM_GROUP_KEY).register(itemGroup -> {
            itemGroup.add(RJItems.CRANBERRY);
            itemGroup.add(RJItems.CREATINE);
            itemGroup.add(RJItems.LIDOCAINE);
            itemGroup.add(RJItems.JOB_APP_SPAWN_EGG);
            itemGroup.add(RJItems.NERD_SPAWN_EGG);
            itemGroup.add(RJItems.PROTEIN_DRINK);
            itemGroup.add(RJItems.NARCAN);
            itemGroup.add(RJItems.PIPE_BOMB);
        });

        RandomJunk.LOG.info("Initialized item groups");
    }
}
