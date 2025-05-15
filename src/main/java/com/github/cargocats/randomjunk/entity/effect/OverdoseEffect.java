package com.github.cargocats.randomjunk.entity.effect;

import com.github.cargocats.randomjunk.registry.RJDamageTypes;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.world.ServerWorld;

public class OverdoseEffect extends StatusEffect {
    public OverdoseEffect(StatusEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public boolean applyUpdateEffect(ServerWorld world, LivingEntity entity, int amplifier) {
        DamageSource damageSource = new DamageSource(
                world.getRegistryManager()
                        .getOrThrow(RegistryKeys.DAMAGE_TYPE)
                        .getEntry(RJDamageTypes.OVERDOSE.getValue()).get()
        );
        entity.damage(world, damageSource, 2.0f * Math.max(1, amplifier));
        return true;
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        int i = 10 >> amplifier;
        return i == 0 || duration % i == 0;
    }
}
