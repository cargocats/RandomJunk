package com.github.cargocats.randomjunk.registry;

import com.github.cargocats.randomjunk.RandomJunk;
import com.github.cargocats.randomjunk.entity.effect.OverdoseEffect;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.ColorHelper;

public class RJStatusEffects {
    public static final RegistryEntry<StatusEffect> OVERDOSE = Registry.registerReference(
            Registries.STATUS_EFFECT, Identifier.of(RandomJunk.MOD_ID, "overdose"),
            new OverdoseEffect(StatusEffectCategory.NEUTRAL, ColorHelper.getArgb(255, 0, 0)));

    public static void initialize() {
        RandomJunk.LOG.info("Initialized status effects");
    }
}
