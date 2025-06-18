package com.github.cargocats.randomjunk.network.packet;

import com.github.cargocats.randomjunk.RandomJunk;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public record SafePasswordPacketS2C(BlockPos blockPos, boolean hasPassword) implements CustomPayload {
    public static final Identifier BlOCK_POS_ID = RandomJunk.id("block_pos");
    public static final CustomPayload.Id<SafePasswordPacketS2C> ID = new CustomPayload.Id<>(BlOCK_POS_ID);
    public static final PacketCodec<RegistryByteBuf, SafePasswordPacketS2C> CODEC =
            PacketCodec.tuple(
                    BlockPos.PACKET_CODEC, SafePasswordPacketS2C::blockPos,
                    PacketCodecs.BOOLEAN, SafePasswordPacketS2C::hasPassword,
                    SafePasswordPacketS2C::new
            );

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
