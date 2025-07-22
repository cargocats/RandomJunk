package com.github.cargocats.randomjunk.client.datagen;

import com.github.cargocats.randomjunk.client.datagen.provider.RJBlockLootTableProvider;
import com.github.cargocats.randomjunk.client.datagen.provider.RJDamageTagProvider;
import com.github.cargocats.randomjunk.client.datagen.provider.RJDynamicRegProvider;
import com.github.cargocats.randomjunk.client.datagen.provider.RJItemTagProvider;
import com.github.cargocats.randomjunk.client.datagen.provider.RJModelProvider;
import com.github.cargocats.randomjunk.client.datagen.provider.RJRecipeProvider;
import com.github.cargocats.randomjunk.client.datagen.provider.RJSoundProvider;
import com.github.cargocats.randomjunk.client.datagen.provider.RJTranslationProvider;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.registry.RegistryBuilder;
import net.minecraft.registry.RegistryKeys;

public class RandomJunkDataGenerator implements DataGeneratorEntrypoint {

    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

        pack.addProvider(RJModelProvider::new);
        pack.addProvider(RJBlockLootTableProvider::new);
        pack.addProvider(RJRecipeProvider::new);
        pack.addProvider(RJTranslationProvider::new);
        pack.addProvider(RJSoundProvider::new);
        pack.addProvider(RJDynamicRegProvider::new);
        pack.addProvider(RJDamageTagProvider::new);
        pack.addProvider(RJItemTagProvider::new);
    }

    @Override
    public void buildRegistry(RegistryBuilder registryBuilder) {
        registryBuilder.addRegistry(RegistryKeys.DAMAGE_TYPE, RJDynamicRegProvider::bootstrapDamageTypes);
    }
}
