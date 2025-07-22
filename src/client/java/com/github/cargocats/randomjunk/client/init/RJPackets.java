package com.github.cargocats.randomjunk.client.init;

import com.github.cargocats.randomjunk.RandomJunk;
import com.github.cargocats.randomjunk.item.LidocaineItem;
import com.github.cargocats.randomjunk.network.packet.SyncLidocaineUsagesS2C;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

public class RJPackets {
    public static void init() {
        ClientPlayNetworking.registerGlobalReceiver(SyncLidocaineUsagesS2C.ID, (payload, context) -> {
            RandomJunk.LOG.info("Client: received payload for sync lidocaine, usages: {}", payload.usages());
            LidocaineItem.clientLidocaineUsages = payload.usages();
        });

        RandomJunk.LOG.info("Initialized client packets");
    }
}
