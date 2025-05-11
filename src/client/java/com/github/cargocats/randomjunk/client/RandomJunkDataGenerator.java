package com.github.cargocats.randomjunk.client;

import com.github.cargocats.randomjunk.client.datagen.RJModelGenerator;
import com.github.cargocats.randomjunk.client.datagen.RJRecipeGenerator;
import com.github.cargocats.randomjunk.client.datagen.RJTranslationGenerator;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class RandomJunkDataGenerator implements DataGeneratorEntrypoint {

    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

        pack.addProvider(RJModelGenerator::new);
        pack.addProvider(RJRecipeGenerator::new);
        pack.addProvider(RJTranslationGenerator::new);
    }
}
