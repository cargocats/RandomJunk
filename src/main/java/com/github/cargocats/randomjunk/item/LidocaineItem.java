package com.github.cargocats.randomjunk.item;

import com.github.cargocats.randomjunk.RandomJunkPersistence;
import com.github.cargocats.randomjunk.network.packet.SyncLidocaineUsagesS2C;
import com.github.cargocats.randomjunk.init.RJSounds;
import com.github.cargocats.randomjunk.timer.OverdoseTimerCallback;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

import java.util.UUID;

public class LidocaineItem extends Item {
    public static final long TIME_WINDOW_TICKS = 60 * 20;
    public static final int OVERDOSE_THRESHOLD = 5;
    public static int clientLidocaineUsages = 0;

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
            if (user.isOnFire()) {
                RandomJunkPersistence persistence = RandomJunkPersistence.getState((ServerWorld) world);
                RandomJunkPersistence.PlayerData playerData = persistence.getOrCreatePlayerData(user);

                UUID uuid = user.getUuid();

                persistence.lidocaineConsumed++;
                long timeNow = world.getTime();

                playerData.overdoseList.add(timeNow);
                playerData.overdoseList.removeIf(t -> t < (timeNow - TIME_WINDOW_TICKS));

                persistence.markDirty();

                if (playerData.overdoseList.size() > OVERDOSE_THRESHOLD) {
                    user.sendMessage(Text.literal("You are overdosing. Use narcan now...").formatted(Formatting.RED), true);

                    world.getServer().getSaveProperties().getMainWorldProperties().getScheduledEvents()
                            .setEvent("overdoseTimer" + uuid, world.getTime() + 100, new OverdoseTimerCallback(uuid));
                } else {
                    user.sendMessage(Text.literal("You feel the burns ease and heal... (" + (OVERDOSE_THRESHOLD + 1 - playerData.overdoseList.size()) + " uses before overdose)"), true);
                }

                ServerPlayNetworking.send((ServerPlayerEntity) user, new SyncLidocaineUsagesS2C(playerData.overdoseList.size()));
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
