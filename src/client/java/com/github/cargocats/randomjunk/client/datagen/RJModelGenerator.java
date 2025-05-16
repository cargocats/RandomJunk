package com.github.cargocats.randomjunk.client.datagen;

import com.github.cargocats.randomjunk.registry.RJItems;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.client.data.BlockStateModelGenerator;
import net.minecraft.client.data.ItemModelGenerator;
import net.minecraft.client.data.Models;

public class RJModelGenerator extends FabricModelProvider {
    public RJModelGenerator(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {

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
    }
}
