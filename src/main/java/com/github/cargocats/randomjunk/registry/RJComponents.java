package com.github.cargocats.randomjunk.registry;

import com.github.cargocats.randomjunk.RandomJunk;
import com.github.cargocats.randomjunk.component.CountdownTimestampComponent;
import net.minecraft.component.ComponentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.function.UnaryOperator;

public class RJComponents {
    public static final ComponentType<CountdownTimestampComponent> COUNTDOWN_TIMER = register(
            RandomJunk.id("timestamp"),
            builder -> builder.codec(CountdownTimestampComponent.CODEC)
    );

    private static <T> ComponentType<T> register(Identifier id, UnaryOperator<ComponentType.Builder<T>> builderOperator) {
        return Registry.register(Registries.DATA_COMPONENT_TYPE, id, (builderOperator.apply(ComponentType.builder())).build());
    }

    public static void initialize() {
        RandomJunk.LOG.info("Initialized components");
    }
}
