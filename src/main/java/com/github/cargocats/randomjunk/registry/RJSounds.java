package com.github.cargocats.randomjunk.registry;

import com.github.cargocats.randomjunk.RandomJunk;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class RJSounds {
    public static final SoundEvent APPLY_LIDOCAINE = registerSound("apply_lidocaine");
    public static final SoundEvent SPRAY_NARCAN = registerSound("spray_narcan");
    public static final SoundEvent PIPE_BOMB_BEEP = registerSound("pipe_bomb_beep");

    public static void initialize() {
        RandomJunk.LOG.info("Initialized sounds");
    }

    private static SoundEvent registerSound(String id) {
        Identifier identifier = RandomJunk.id(id);
        return Registry.register(Registries.SOUND_EVENT, identifier, SoundEvent.of(identifier));
    }
}
