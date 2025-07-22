package com.github.cargocats.randomjunk.client.datagen.provider;

import com.github.cargocats.randomjunk.init.RJDamageTypes;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.DamageTypeTags;

import java.util.concurrent.CompletableFuture;

public class RJDamageTagProvider extends FabricTagProvider<DamageType> {
    public RJDamageTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, RegistryKeys.DAMAGE_TYPE, registriesFuture);
    }


    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        this.builder(DamageTypeTags.BYPASSES_ARMOR).add(RJDamageTypes.OVERDOSE);
        this.builder(DamageTypeTags.NO_IMPACT).add(RJDamageTypes.OVERDOSE);
    }
}
