package com.github.cargocats.randomjunk.network.packet;

import com.github.cargocats.randomjunk.RandomJunk;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record SyncLidocaineUsagesS2C(int usages) implements CustomPayload {
    public static final Identifier SYNC_LIDOCAINE_PAYLOAD_ID = Identifier.of(RandomJunk.MOD_ID, "sync_lidocaine_usages");
    public static final CustomPayload.Id<SyncLidocaineUsagesS2C> ID = new CustomPayload.Id<>(SYNC_LIDOCAINE_PAYLOAD_ID);
    public static final PacketCodec<RegistryByteBuf, SyncLidocaineUsagesS2C> CODEC = PacketCodec.tuple(PacketCodecs.INTEGER, SyncLidocaineUsagesS2C::usages, SyncLidocaineUsagesS2C::new);

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
