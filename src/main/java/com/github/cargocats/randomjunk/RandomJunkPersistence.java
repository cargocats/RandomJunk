package com.github.cargocats.randomjunk;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.datafixer.DataFixTypes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.PersistentState;
import net.minecraft.world.PersistentStateType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class RandomJunkPersistence extends PersistentState {
    public HashMap<UUID, PlayerData> players = new HashMap<>();
    public int lidocaineConsumed = 0;

    private static final Codec<UUID> UUID_CODEC = Codec.STRING.xmap(UUID::fromString, UUID::toString);
    private static final Codec<RandomJunkPersistence> CODEC = RecordCodecBuilder.create(instance ->
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

    private static final PersistentStateType<RandomJunkPersistence> PERSISTENT_TYPE = new PersistentStateType<>(
            RandomJunk.MOD_ID,
            ctx -> new RandomJunkPersistence(),
            ctx -> RandomJunkPersistence.CODEC,
            DataFixTypes.LEVEL
    );

    public static RandomJunkPersistence getState(ServerWorld world) {
        return world.getPersistentStateManager().getOrCreate(PERSISTENT_TYPE);
    }

    public PlayerData getOrCreatePlayerData(PlayerEntity player) {
        return players.computeIfAbsent(player.getUuid(), id -> new PlayerData());
    }

    public static class PlayerData {
        public List<Long> overdoseList;

        public PlayerData() {
            this.overdoseList = new ArrayList<>();
        }

        public static final Codec<PlayerData> PLAYER_DATA_CODEC = RecordCodecBuilder.create(instance ->
                instance.group(
                        Codec.LONG.listOf().fieldOf("overdoseList").forGetter(data -> data.overdoseList)
                ).apply(instance, (overdoseList) -> {
                    PlayerData data = new PlayerData();
                    data.overdoseList = new ArrayList<>(overdoseList);

                    return data;
                }));
    }
}
