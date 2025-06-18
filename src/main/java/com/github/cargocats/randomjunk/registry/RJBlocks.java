package com.github.cargocats.randomjunk.registry;

import com.github.cargocats.randomjunk.RandomJunk;
import com.github.cargocats.randomjunk.block.SafeBlock;
import com.github.cargocats.randomjunk.block.ToiletPaperBlock;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.MapColor;
import net.minecraft.block.enums.NoteBlockInstrument;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.BlockSoundGroup;
import java.util.function.Function;

public class RJBlocks {
    public static final Block TOILET_PAPER = register(
            "toilet_paper",
            ToiletPaperBlock::new,
            AbstractBlock.Settings.create()
                    .burnable()
                    .solid()
                    .nonOpaque()
                    .sounds(BlockSoundGroup.WOOL)
                    .strength(0.8f)

    );

    public static final Block SAFE_BLOCK = register(
            "safe_block",
            SafeBlock::new,
            AbstractBlock.Settings.create()
                    .mapColor(MapColor.DEEPSLATE_GRAY)
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .sounds(BlockSoundGroup.ANVIL)
                    .strength(64.0f, 2048.0f)
    );

    public static void initialize() {
        RandomJunk.LOG.info("Initialized blocks");
    }

    public static Block register(RegistryKey<Block> key, Function<AbstractBlock.Settings, Block> factory, AbstractBlock.Settings settings) {
        Block block = factory.apply(settings.registryKey(key));
        return Registry.register(Registries.BLOCK, key, block);
    }

    public static Block register(RegistryKey<Block> key, AbstractBlock.Settings settings) {
        return register(key, Block::new, settings);
    }

    private static RegistryKey<Block> keyOf(String id) {
        return RegistryKey.of(RegistryKeys.BLOCK, RandomJunk.id(id));
    }

    private static Block register(String id, Function<AbstractBlock.Settings, Block> factory, AbstractBlock.Settings settings) {
        return register(keyOf(id), factory, settings);
    }

    private static Block register(String id, AbstractBlock.Settings settings) {
        return register(id, Block::new, settings);
    }
}
