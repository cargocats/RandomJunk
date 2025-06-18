package com.github.cargocats.randomjunk.item;

import com.github.cargocats.randomjunk.component.CountdownTimestampComponent;
import com.github.cargocats.randomjunk.registry.RJComponents;
import com.github.cargocats.randomjunk.registry.RJSounds;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import net.minecraft.world.explosion.ExplosionBehavior;
import org.jetbrains.annotations.Nullable;

public class PipebombItem extends Item {
    public PipebombItem(Settings settings) {
        super(settings);
    }

    private float getPitch(int timer, int maxTimer) {
        float t = 1f - ((float) timer / maxTimer);
        float eased = t * t;
        return (float) 0.1 + ((float) 2.0 - (float) 0.1) * eased;
    }

    private boolean shouldBeep(int timer, int maxTimer) {
        int minInterval = 5;
        int maxInterval = 40;

        float progress = 1.0f - (float) timer / maxTimer;
        int interval = (int) (maxInterval - (maxInterval - minInterval) * progress);

        return timer % interval == 0;
    }

    @Override
    public void inventoryTick(ItemStack stack, ServerWorld world, Entity entity, @Nullable EquipmentSlot slot) {
        CountdownTimestampComponent timestampComponent = stack.get(RJComponents.COUNTDOWN_TIMER);

        if (timestampComponent != null) {
            long timeEnd = timestampComponent.end();
            long timeLeft = timeEnd - world.getTime();

            if (timeLeft <= 0) {
                stack.setCount(0);
                world.createExplosion(
                        null, Explosion.createDamageSource(world, entity),
                        new ExplosionBehavior(), entity.getPos(),
                        4.0f, false, World.ExplosionSourceType.TNT
                );
            } else {
                int maxTime = (int) (timeEnd - timestampComponent.start());

                if (shouldBeep((int) timeLeft, maxTime)) {
                    world.playSound(
                            null,
                            entity.getBlockPos(),
                            RJSounds.PIPE_BOMB_BEEP,
                            SoundCategory.PLAYERS,
                            2.0f,
                            getPitch((int) timeLeft, maxTime)
                    );
                }
            }
        } else {
            stack.set(RJComponents.COUNTDOWN_TIMER, new CountdownTimestampComponent(world.getTime(), world.getTime() + CountdownTimestampComponent.DEFAULT_LENGTH));
        }
    }

    @Override
    public boolean allowComponentsUpdateAnimation(PlayerEntity player, Hand hand, ItemStack oldStack, ItemStack newStack) {
        CountdownTimestampComponent timestampComponent = newStack.get(RJComponents.COUNTDOWN_TIMER);
        if (timestampComponent != null) {
            long timeEnd = timestampComponent.end();
            int maxTime = (int) (timeEnd - timestampComponent.start());
            long timeLeft = timeEnd - player.getWorld().getTime();

            return shouldBeep((int) timeLeft, maxTime);
        }
        return false;
    }
}
