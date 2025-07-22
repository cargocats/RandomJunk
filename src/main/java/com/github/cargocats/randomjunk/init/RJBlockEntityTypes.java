package com.github.cargocats.randomjunk.init;

import com.github.cargocats.randomjunk.RandomJunk;
import com.github.cargocats.randomjunk.block.entity.SafeBlockEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class RJBlockEntityTypes {
    public static final BlockEntityType<SafeBlockEntity> SAFE_BLOCK = register("safe_block", SafeBlockEntity::new, RJBlocks.SAFE_BLOCK);

    public static void init() {
        RandomJunk.LOG.info("Initialized block entity types");
    }

    private static <T extends BlockEntity> BlockEntityType<T> register(
            String name,
            FabricBlockEntityTypeBuilder.Factory<? extends T> entityFactory,
            Block... blocks
    ) {
        Identifier id = RandomJunk.id(name);
        return Registry.register(Registries.BLOCK_ENTITY_TYPE, id, FabricBlockEntityTypeBuilder.<T>create(entityFactory, blocks).build());
    }
}
