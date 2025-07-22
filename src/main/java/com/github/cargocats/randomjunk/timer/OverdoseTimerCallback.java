package com.github.cargocats.randomjunk.timer;

import com.github.cargocats.randomjunk.RandomJunk;
import com.github.cargocats.randomjunk.init.RJStatusEffects;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.Uuids;
import net.minecraft.world.timer.Timer;
import net.minecraft.world.timer.TimerCallback;

import java.util.ArrayList;
import java.util.UUID;

public class OverdoseTimerCallback implements TimerCallback<MinecraftServer> {
    private final UUID uuid;
    public static final ArrayList<UUID> UUIDS = new ArrayList<>();
    public static final Identifier ID = RandomJunk.id("overdose_timer");

    public static final MapCodec<OverdoseTimerCallback> MAP_CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Uuids.CODEC.fieldOf("uuid").forGetter(o -> o.uuid)
    ).apply(instance, OverdoseTimerCallback::new));

    public OverdoseTimerCallback(UUID uuid) {
        this.uuid = uuid;
    }

    @Override
    public void call(MinecraftServer server, Timer<MinecraftServer> events, long time) {
        ServerPlayerEntity player = server.getPlayerManager().getPlayer(this.uuid);
        giveEffect(player);

        if (player == null) {
            UUIDS.add(this.uuid);
        }
    }

    public static void giveEffect(ServerPlayerEntity player) {
        if (player != null) {
            player.addStatusEffect(new StatusEffectInstance(RJStatusEffects.OVERDOSE, 5 * 60 * 20, 0));
        }
    }

    @Override
    public MapCodec<? extends TimerCallback<MinecraftServer>> getCodec() {
        return MAP_CODEC;
    }
}
