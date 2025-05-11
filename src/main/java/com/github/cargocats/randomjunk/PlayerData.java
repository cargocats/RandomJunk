package com.github.cargocats.randomjunk;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import java.util.ArrayList;
import java.util.List;

public class PlayerData {
    public List<Long> overdoseList;

    public PlayerData(List<Long> overdoseList) {
        this.overdoseList = overdoseList;
    }

    public PlayerData() {
        this.overdoseList = new ArrayList<>();
    }

    public static final Codec<PlayerData> PLAYER_DATA_CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.LONG.listOf().fieldOf("overdoseList").forGetter(data -> data.overdoseList)
    ).apply(instance, (overdoseList) -> {
        PlayerData data = new PlayerData();
        data.overdoseList = new ArrayList<>(overdoseList);

        return data;
    }));
}
