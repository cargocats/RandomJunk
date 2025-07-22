package com.github.cargocats.randomjunk.network.packet;

import com.github.cargocats.randomjunk.RandomJunk;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public record AttemptOpenSafeC2S(BlockPos blockPos, String password) implements CustomPayload {
    public static final Identifier ATTEMPT_OPEN_SAFE = RandomJunk.id("attempt_open_safe");
    public static final Id<AttemptOpenSafeC2S> ID = new Id<>(ATTEMPT_OPEN_SAFE);

    public static final PacketCodec<RegistryByteBuf, AttemptOpenSafeC2S> PACKET_CODEC = PacketCodec.tuple(
            BlockPos.PACKET_CODEC, AttemptOpenSafeC2S::blockPos,
            PacketCodecs.STRING, AttemptOpenSafeC2S::password,
            AttemptOpenSafeC2S::new
    );

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
