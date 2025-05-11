package com.github.cargocats.randomjunk.item;

import com.github.cargocats.randomjunk.PlayerData;
import com.github.cargocats.randomjunk.RandomJunk;
import com.github.cargocats.randomjunk.StateSaverAndLoader;
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

        if (!world.isClient) {
            StateSaverAndLoader state = StateSaverAndLoader.getState((ServerWorld) world);

            if (user.isOnFire()) {
                UUID uuid = user.getUuid();
                RandomJunk.LOG.info("Players: {}, class: {}", state.players, state.players.getClass().getName());
                PlayerData playerData = state.players.computeIfAbsent(uuid, id -> new PlayerData());

                state.lidocaineConsumed++;
                RandomJunk.LOG.info("Global Lidocaine total consumed: {}", state.lidocaineConsumed);

                long timeNow = world.getTime();

                playerData.overdoseList.add(timeNow);
                playerData.overdoseList.removeIf(t -> t < (timeNow - TIME_WINDOW_TICKS));

                state.markDirty();

                RandomJunk.LOG.info("Player overdose: {}", playerData.overdoseList);

                if (playerData.overdoseList.size() > OVERDOSE_THRESHOLD) {
                    user.sendMessage(Text.literal("You are overdosing. Use narcan now.").formatted(Formatting.RED), true);
                } else {
                    user.sendMessage(Text.literal("You feel the burns ease and heal..."), true);
                }

                user.extinguishWithSound();
                user.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 5 * 20, 1));
            } else {
                user.sendMessage(Text.literal("You feel no effect."), true);
            }

            if (!user.getAbilities().creativeMode) {
                item.decrement(1);
            }

            user.getItemCooldownManager().set(item, 20);
            return ActionResult.PASS;
        }

        return ActionResult.FAIL;
    }
}
