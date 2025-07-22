package com.github.cargocats.randomjunk.mixin;

import com.github.cargocats.randomjunk.init.RJStatusEffects;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.consume.ClearAllEffectsConsumeEffect;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ClearAllEffectsConsumeEffect.class)
public class ClearAllEffectsConsumeEffectMixin {
    @WrapOperation(method = "onConsume", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/entity/LivingEntity;clearStatusEffects()Z"
    ))

    private boolean preserveOverdoseEffect(LivingEntity user, Operation<Boolean> original, World world, ItemStack stack) {
        StatusEffectInstance effect = user.getStatusEffect(RJStatusEffects.OVERDOSE);
        boolean cleared = original.call(user);

        if (effect != null && !stack.isOf(Items.TOTEM_OF_UNDYING)) {
            user.addStatusEffect(effect);
        }

        return cleared;
    }
}
