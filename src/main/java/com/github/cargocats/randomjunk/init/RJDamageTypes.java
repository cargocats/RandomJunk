package com.github.cargocats.randomjunk.init;

import com.github.cargocats.randomjunk.RandomJunk;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;

public class RJDamageTypes {
    public static final RegistryKey<DamageType> OVERDOSE = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, RandomJunk.id("overdose"));

    public static void init() {
        RandomJunk.LOG.info("Initialized damage types");
    }
}
