package com.github.cargocats.randomjunk.registry;

import com.github.cargocats.randomjunk.RandomJunk;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.*;
import net.minecraft.util.Identifier;

public class RJDamageTypes {
    public static final RegistryKey<DamageType> OVERDOSE = RegistryKey.of(
            RegistryKeys.DAMAGE_TYPE, Identifier.of(RandomJunk.MOD_ID, "overdose"));

    public static void initialize() {
        RandomJunk.LOG.info("Initialized Random Junk damage types");
    }
}
