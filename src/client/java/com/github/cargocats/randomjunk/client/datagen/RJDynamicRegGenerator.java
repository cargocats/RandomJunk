package com.github.cargocats.randomjunk.client.datagen;

import com.github.cargocats.randomjunk.registry.RJDamageTypes;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.entity.damage.DamageScaling;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class RJDynamicRegGenerator extends FabricDynamicRegistryProvider {
    public RJDynamicRegGenerator(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup, Entries entries) {
        entries.addAll(wrapperLookup.getOrThrow(RegistryKeys.DAMAGE_TYPE));
    }

    public static void bootstrapDamageTypes(Registerable<DamageType> damageTypeRegisterable) {
        damageTypeRegisterable.register(RJDamageTypes.OVERDOSE, new DamageType(
                "overdose",
                DamageScaling.WHEN_CAUSED_BY_LIVING_NON_PLAYER,
                0.5f
        ));
    }

    @Override
    public String getName() {
        return "Random Junk Dynamic Registry Provider";
    }
}
