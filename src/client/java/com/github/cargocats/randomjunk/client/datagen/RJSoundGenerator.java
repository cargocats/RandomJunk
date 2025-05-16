package com.github.cargocats.randomjunk.client.datagen;

import com.github.cargocats.randomjunk.RandomJunk;
import com.github.cargocats.randomjunk.registry.RJSounds;
import net.fabricmc.fabric.api.client.datagen.v1.builder.SoundTypeBuilder;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricSoundsProvider;
import net.minecraft.data.DataOutput;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.Identifier;

import java.util.concurrent.CompletableFuture;

public class RJSoundGenerator extends FabricSoundsProvider {
    public RJSoundGenerator(DataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup, SoundExporter soundExporter) {
        soundExporter.add(
                RJSounds.APPLY_LIDOCAINE,
                SoundTypeBuilder.of(RJSounds.APPLY_LIDOCAINE).sound(
                        SoundTypeBuilder.EntryBuilder.ofFile(Identifier.of(RandomJunk.MOD_ID, "apply_lidocaine"))
                ).category(SoundCategory.PLAYERS)
        );

        soundExporter.add(
                RJSounds.SPRAY_NARCAN,
                SoundTypeBuilder.of(RJSounds.SPRAY_NARCAN).sound(
                        SoundTypeBuilder.EntryBuilder.ofFile(Identifier.of(RandomJunk.MOD_ID, "spray_narcan"))
                ).category(SoundCategory.PLAYERS)
        );
    }

    @Override
    public String getName() {
        return "Random Junk Sounds Provider";
    }
}
