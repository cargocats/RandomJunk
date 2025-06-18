package com.github.cargocats.randomjunk.network.packet;

import com.github.cargocats.randomjunk.RandomJunk;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public record SafePasswordPacket(BlockPos blockPos, boolean hasPassword) implements CustomPayload {
    public static final Identifier BlOCK_POS_ID = RandomJunk.id("block_pos");
    public static final CustomPayload.Id<SafePasswordPacket> ID = new CustomPayload.Id<>(BlOCK_POS_ID);
    public static final PacketCodec<RegistryByteBuf, SafePasswordPacket> CODEC =
            PacketCodec.tuple(
                    BlockPos.PACKET_CODEC, SafePasswordPacket::blockPos,
                    PacketCodecs.BOOLEAN, SafePasswordPacket::hasPassword,
                    SafePasswordPacket::new
            );

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
