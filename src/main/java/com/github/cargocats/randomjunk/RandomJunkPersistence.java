package com.github.cargocats.randomjunk;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.datafixer.DataFixTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.PersistentState;
import net.minecraft.world.PersistentStateType;

import java.util.HashMap;
import java.util.UUID;

public class RandomJunkPersistence extends PersistentState {
    public HashMap<UUID, PlayerData> players = new HashMap<>();
    public int lidocaineConsumed = 0;

    public RandomJunkPersistence() {}

    public static final Codec<UUID> UUID_CODEC = Codec.STRING.xmap(UUID::fromString, UUID::toString);

    public static final Codec<RandomJunkPersistence> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.INT.fieldOf("lidocaineConsumed").forGetter(s -> s.lidocaineConsumed),
                    Codec.unboundedMap(UUID_CODEC, PlayerData.PLAYER_DATA_CODEC)
                            .fieldOf("players")
                            .forGetter(s -> s.players)
            ).apply(instance, (lidocaineConsumed, players) -> {
                RandomJunkPersistence state = new RandomJunkPersistence();

                state.lidocaineConsumed = lidocaineConsumed;
                state.players = new HashMap<>(players);

                return state;
            })
    );

    public static final PersistentStateType<RandomJunkPersistence> STATE_SAVER_AND_LOADER = new PersistentStateType<>(
            RandomJunk.MOD_ID,
            ctx -> new RandomJunkPersistence(),
            ctx -> RandomJunkPersistence.CODEC,
            DataFixTypes.LEVEL
    );

    public static RandomJunkPersistence getState(ServerWorld world) {
        return world.getPersistentStateManager().getOrCreate(STATE_SAVER_AND_LOADER);
    }

}
