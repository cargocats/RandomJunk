package com.github.cargocats.randomjunk.client.datagen;

import com.github.cargocats.randomjunk.registry.RJEntityTypes;
import com.github.cargocats.randomjunk.registry.RJItemGroups;
import com.github.cargocats.randomjunk.registry.RJItems;
import com.github.cargocats.randomjunk.registry.RJSounds;
import com.github.cargocats.randomjunk.registry.RJStatusEffects;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class RJTranslationGenerator extends FabricLanguageProvider {
    public RJTranslationGenerator(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generateTranslations(RegistryWrapper.WrapperLookup wrapperLookup, TranslationBuilder translationBuilder) {
        translationBuilder.add(RJItems.CRANBERRY, "Cranberry");
        translationBuilder.add(RJItems.CREATINE, "Creatine");
        translationBuilder.add(RJItems.LIDOCAINE, "Lidocaine");
        translationBuilder.add(RJItems.PROTEIN_DRINK, "Protein Drink");
        translationBuilder.add(RJItems.NERD_SPAWN_EGG, "Nerd Spawn Egg");
        translationBuilder.add(RJItems.JOB_APP_SPAWN_EGG, "Job App Spawn Egg");
        translationBuilder.add(RJItems.NARCAN, "Narcan");
        translationBuilder.add(RJItems.PIPE_BOMB, "Pipe Bomb");

        translationBuilder.add(RJEntityTypes.JOB_APP, "Job App");
        translationBuilder.add(RJEntityTypes.NERD, "Nerd");
        translationBuilder.add(RJItemGroups.RANDOM_JUNK_ITEM_GROUP_KEY, "Random Junk");

        translationBuilder.add(RJStatusEffects.OVERDOSE.value(), "Overdose");

        translationBuilder.add(RJSounds.APPLY_LIDOCAINE, "Apply lidocaine");
        translationBuilder.add(RJSounds.SPRAY_NARCAN, "Spray narcan");
        translationBuilder.add(RJSounds.PIPE_BOMB_BEEP, "Pipe bomb beep");

        translationBuilder.add("death.attack.overdose", "%1$s died from an Overdose");
    }
}
