package com.github.cargocats.randomjunk.client.datagen.provider;

import com.github.cargocats.randomjunk.client.init.tints.PipeBombTintSource;
import com.github.cargocats.randomjunk.init.RJBlocks;
import com.github.cargocats.randomjunk.init.RJItems;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.client.data.BlockStateModelGenerator;
import net.minecraft.client.data.BlockStateVariantMap;
import net.minecraft.client.data.ItemModelGenerator;
import net.minecraft.client.data.Models;
import net.minecraft.client.data.TextureKey;
import net.minecraft.client.data.TextureMap;
import net.minecraft.client.data.TexturedModel;
import net.minecraft.client.data.VariantsBlockModelDefinitionCreator;
import net.minecraft.client.render.model.json.ModelVariantOperator;
import net.minecraft.client.render.model.json.WeightedVariant;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.Direction;

import static net.minecraft.client.data.BlockStateModelGenerator.createWeightedVariant;

public class RJModelProvider extends FabricModelProvider {
    public RJModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        blockStateModelGenerator.registerAxisRotated(RJBlocks.TOILET_PAPER, TexturedModel.CUBE_COLUMN, TexturedModel.CUBE_COLUMN_HORIZONTAL);

        // Safe block
        WeightedVariant weightedVariant = createWeightedVariant(TexturedModel.CUBE_TOP.upload(RJBlocks.SAFE_BLOCK, blockStateModelGenerator.modelCollector));
        WeightedVariant weightedVariant2 = createWeightedVariant(
                TexturedModel.CUBE_TOP
                        .get(RJBlocks.SAFE_BLOCK)
                        .textures(textureMap -> textureMap.put(TextureKey.TOP, TextureMap.getSubId(RJBlocks.SAFE_BLOCK, "_front_open")))
                        .upload(RJBlocks.SAFE_BLOCK, "_open", blockStateModelGenerator.modelCollector)
        );

        BlockStateVariantMap<ModelVariantOperator> UP_DEFAULT_ROTATION_OPERATIONS = BlockStateVariantMap.operations(Properties.FACING)
                .register(Direction.DOWN, BlockStateModelGenerator.ROTATE_X_180)
                .register(Direction.UP, BlockStateModelGenerator.NO_OP)
                .register(Direction.NORTH, BlockStateModelGenerator.ROTATE_X_90)
                .register(Direction.SOUTH, BlockStateModelGenerator.ROTATE_X_90.then(BlockStateModelGenerator.ROTATE_Y_180))
                .register(Direction.WEST, BlockStateModelGenerator.ROTATE_X_90.then(BlockStateModelGenerator.ROTATE_Y_270))
                .register(Direction.EAST, BlockStateModelGenerator.ROTATE_X_90.then(BlockStateModelGenerator.ROTATE_Y_90));

        blockStateModelGenerator.blockStateCollector.accept(
                VariantsBlockModelDefinitionCreator.of(RJBlocks.SAFE_BLOCK)
                        .with(BlockStateVariantMap.models(Properties.OPEN).register(false, weightedVariant).register(true, weightedVariant2))
                        .coordinate(UP_DEFAULT_ROTATION_OPERATIONS)
        );
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        itemModelGenerator.register(RJItems.NERD_SPAWN_EGG, Models.GENERATED);
        itemModelGenerator.register(RJItems.JOB_APP_SPAWN_EGG, Models.GENERATED);
        itemModelGenerator.register(RJItems.CRANBERRY, Models.GENERATED);
        itemModelGenerator.register(RJItems.CREATINE, Models.GENERATED);
        itemModelGenerator.register(RJItems.PROTEIN_DRINK, Models.GENERATED);
        itemModelGenerator.register(RJItems.LIDOCAINE, Models.GENERATED);
        itemModelGenerator.register(RJItems.NARCAN, Models.GENERATED);

        itemModelGenerator.registerWithTintedOverlay(RJItems.PIPE_BOMB, new PipeBombTintSource());
    }
}
