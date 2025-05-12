package com.github.cargocats.randomjunk.client.datagen;

import com.github.cargocats.randomjunk.registry.RJDamageTypes;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

import java.util.concurrent.CompletableFuture;

public class RJDamageTagGenerator extends FabricTagProvider<DamageType> {
    public RJDamageTagGenerator(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, RegistryKeys.DAMAGE_TYPE, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        getOrCreateTagBuilder(TagKey.of(RegistryKeys.DAMAGE_TYPE, Identifier.ofVanilla("bypasses_armor")))
                .add(RJDamageTypes.OVERDOSE);

        getOrCreateTagBuilder(TagKey.of(RegistryKeys.DAMAGE_TYPE, Identifier.ofVanilla("no_impact")))
                .add(RJDamageTypes.OVERDOSE);
    }
}
