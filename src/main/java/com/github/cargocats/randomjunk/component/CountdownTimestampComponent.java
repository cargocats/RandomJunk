package com.github.cargocats.randomjunk.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record CountdownTimestampComponent(long start, long end) {
    public static final Codec<CountdownTimestampComponent> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.LONG.fieldOf("start").forGetter(CountdownTimestampComponent::start),
            Codec.LONG.fieldOf("end").forGetter(CountdownTimestampComponent::end)
    ).apply(instance, CountdownTimestampComponent::new));

    public static final int DEFAULT_LENGTH = 20 * 15;
}