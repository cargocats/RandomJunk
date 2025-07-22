package com.github.cargocats.randomjunk.client.datagen.provider;

import com.github.cargocats.randomjunk.init.RJBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class RJBlockLootTableProvider extends FabricBlockLootTableProvider {
    public RJBlockLootTableProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generate() {
        addDrop(RJBlocks.SAFE_BLOCK);
        addDrop(RJBlocks.TOILET_PAPER);
    }
}
