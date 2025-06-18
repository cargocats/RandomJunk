package com.github.cargocats.randomjunk.client;

import com.github.cargocats.randomjunk.client.datagen.RJBlockLootTables;
import com.github.cargocats.randomjunk.client.datagen.RJDamageTagGenerator;
import com.github.cargocats.randomjunk.client.datagen.RJDynamicRegGenerator;
import com.github.cargocats.randomjunk.client.datagen.RJModelGenerator;
import com.github.cargocats.randomjunk.client.datagen.RJRecipeGenerator;
import com.github.cargocats.randomjunk.client.datagen.RJSoundGenerator;
import com.github.cargocats.randomjunk.client.datagen.RJTagGenerator;
import com.github.cargocats.randomjunk.client.datagen.RJTranslationGenerator;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.registry.RegistryBuilder;
import net.minecraft.registry.RegistryKeys;

public class RandomJunkDataGenerator implements DataGeneratorEntrypoint {

    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

        pack.addProvider(RJModelGenerator::new);
        pack.addProvider(RJBlockLootTables::new);
        pack.addProvider(RJRecipeGenerator::new);
        pack.addProvider(RJTranslationGenerator::new);
        pack.addProvider(RJSoundGenerator::new);
        pack.addProvider(RJDynamicRegGenerator::new);
        pack.addProvider(RJDamageTagGenerator::new);
        pack.addProvider(RJTagGenerator::new);

    }

    @Override
    public void buildRegistry(RegistryBuilder registryBuilder) {
        registryBuilder.addRegistry(RegistryKeys.DAMAGE_TYPE, RJDynamicRegGenerator::bootstrapDamageTypes);
    }
}
