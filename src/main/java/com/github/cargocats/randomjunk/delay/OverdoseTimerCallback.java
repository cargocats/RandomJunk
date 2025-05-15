package com.github.cargocats.randomjunk.delay;

import com.github.cargocats.randomjunk.RandomJunk;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Identifier;
import net.minecraft.util.Uuids;
import net.minecraft.world.timer.Timer;
import net.minecraft.world.timer.TimerCallback;

import java.util.UUID;


public class OverdoseTimerCallback implements TimerCallback<MinecraftServer> {
    private final UUID uuid;

    public static final Identifier ID = Identifier.of(RandomJunk.MOD_ID, "overdose_timer");

    public static final MapCodec<OverdoseTimerCallback> MAP_CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Uuids.CODEC.fieldOf("uuid").forGetter(o -> o.uuid)
    ).apply(instance, OverdoseTimerCallback::new));

    public OverdoseTimerCallback(UUID uuid) {
        this.uuid = uuid;
    }

    @Override
    public void call(MinecraftServer server, Timer<MinecraftServer> events, long time) {
        RandomJunk.LOG.info("Got uuid: {} player: {}", this.uuid, server.getPlayerManager().getPlayer(this.uuid));
    }

    @Override
    public MapCodec<? extends TimerCallback<MinecraftServer>> getCodec() {
        return MAP_CODEC;
    }
}
