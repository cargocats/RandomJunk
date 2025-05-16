package com.github.cargocats.randomjunk.item;

import com.github.cargocats.randomjunk.PlayerData;
import com.github.cargocats.randomjunk.StateSaverAndLoader;
import com.github.cargocats.randomjunk.delay.OverdoseTimerCallback;
import com.github.cargocats.randomjunk.registry.RJSounds;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

import java.util.*;

public class LidocaineItem extends Item {
    private static final long TIME_WINDOW_TICKS = 60 * 20;
    private static final int OVERDOSE_THRESHOLD = 5;

    public LidocaineItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        ItemStack item = user.getStackInHand(hand);

        if (user.isOnFire()) {
            user.playSound(RJSounds.APPLY_LIDOCAINE,1.0f, 1.0f + (world.getRandom().nextFloat() - world.getRandom().nextFloat()) * 0.4F);
        }

        if (!world.isClient) {
            StateSaverAndLoader state = StateSaverAndLoader.getState((ServerWorld) world);

            if (user.isOnFire()) {
                UUID uuid = user.getUuid();
                PlayerData playerData = state.players.computeIfAbsent(uuid, id -> new PlayerData());

                state.lidocaineConsumed++;
                long timeNow = world.getTime();

                playerData.overdoseList.add(timeNow);
                playerData.overdoseList.removeIf(t -> t < (timeNow - TIME_WINDOW_TICKS));

                state.markDirty();

                if (playerData.overdoseList.size() > OVERDOSE_THRESHOLD) {
                    user.sendMessage(Text.literal("You are overdosing. Use narcan now...").formatted(Formatting.RED), true);

                    world.getServer().getSaveProperties().getMainWorldProperties().getScheduledEvents()
                            .setEvent("overdoseTimer" + uuid, world.getTime() + 100, new OverdoseTimerCallback(uuid));
                } else {
                    user.sendMessage(Text.literal("You feel the burns ease and heal... (" + (OVERDOSE_THRESHOLD + 1 - playerData.overdoseList.size()) + " uses before overdose)"), true);
                }

                user.extinguishWithSound();
                user.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 5 * 20, 1));
            } else {
                user.sendMessage(Text.literal("You feel no effect."), true);
            }

            item.decrementUnlessCreative(1, user);
            user.getItemCooldownManager().set(item, 20);
            return ActionResult.PASS;
        }

        return ActionResult.FAIL;
    }
}
