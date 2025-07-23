package com.github.cargocats.randomjunk.item;

import com.github.cargocats.randomjunk.init.RJDataAttachments;
import com.github.cargocats.randomjunk.init.RJSounds;
import com.github.cargocats.randomjunk.network.packet.SyncLidocaineUsagesS2C;
import com.github.cargocats.randomjunk.timer.OverdoseTimerCallback;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class LidocaineItem extends Item {
    public static final long TIME_WINDOW_TICKS = 60 * 20;
    public static final int OVERDOSE_THRESHOLD = 5;
    public static int clientLidocaineUsages = 0;

    public LidocaineItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult use(World world, PlayerEntity player, Hand hand) {
        ItemStack item = player.getStackInHand(hand);

        if (player.isOnFire()) {
            player.playSound(RJSounds.APPLY_LIDOCAINE, 1.0f, 1.0f + (world.getRandom().nextFloat() - world.getRandom().nextFloat()) * 0.4F);
        }

        if (!world.isClient) {
            if (player.isOnFire()) {
                world.setAttached(RJDataAttachments.LIDOCAINE_CONSUMED, world.getAttachedOrCreate(RJDataAttachments.LIDOCAINE_CONSUMED) + 1);

                List<Long> overdoseList = player.getAttachedOrCreate(RJDataAttachments.OVERDOSE_LIST);
                List<Long> overdoseListCopy = new ArrayList<>(overdoseList);

                long timeNow = world.getTime();
                overdoseListCopy.add(timeNow);
                overdoseListCopy.removeIf(t -> t < (timeNow - TIME_WINDOW_TICKS));
                player.setAttached(RJDataAttachments.OVERDOSE_LIST, overdoseListCopy);

                if (overdoseListCopy.size() > OVERDOSE_THRESHOLD) {
                    player.sendMessage(Text.literal("You are overdosing. Use narcan now...").formatted(Formatting.RED), true);

                    world.getServer().getSaveProperties().getMainWorldProperties().getScheduledEvents()
                            .setEvent("overdoseTimer" + player.getUuid(), world.getTime() + 100, new OverdoseTimerCallback(player.getUuid()));
                } else {
                    player.sendMessage(Text.literal("You feel the burns ease and heal... (" + (OVERDOSE_THRESHOLD + 1 - overdoseListCopy.size()) + " uses before overdose)"), true);
                }

                ServerPlayNetworking.send((ServerPlayerEntity) player, new SyncLidocaineUsagesS2C(overdoseListCopy.size()));
                player.extinguishWithSound();
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 5 * 20, 1));
            } else {
                player.sendMessage(Text.literal("You feel no effect."), true);
            }

            item.decrementUnlessCreative(1, player);
            player.getItemCooldownManager().set(item, 20);
            return ActionResult.PASS;
        }

        return ActionResult.FAIL;
    }
}
