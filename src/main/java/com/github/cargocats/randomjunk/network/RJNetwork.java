package com.github.cargocats.randomjunk.network;

import com.github.cargocats.randomjunk.block.entity.SafeBlockEntity;
import com.github.cargocats.randomjunk.network.packet.AttemptOpenSafeC2S;
import com.github.cargocats.randomjunk.network.packet.SyncLidocaineUsagesS2C;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.text.Text;

public class RJNetwork {
    public static void initialize() {
        PayloadTypeRegistry.playS2C().register(SyncLidocaineUsagesS2C.ID, SyncLidocaineUsagesS2C.CODEC);
        PayloadTypeRegistry.playC2S().register(AttemptOpenSafeC2S.ID, AttemptOpenSafeC2S.CODEC);

        registerHandlers();
    }

    public static void registerHandlers() {
        ServerPlayNetworking.registerGlobalReceiver(AttemptOpenSafeC2S.ID, (payload, context) -> {
            var player = context.player();
            var world = player.getWorld();
            var pos = payload.blockPos();
            var password = payload.password();

            context.server().execute(() -> {
                var blockEntity = world.getBlockEntity(pos);
                if (blockEntity instanceof SafeBlockEntity safeBlockEntity) {
                    var blockEntityPassword = safeBlockEntity.getPassword();
                    if (blockEntityPassword.isEmpty()) {
                        safeBlockEntity.setPassword(password);
                        player.openHandledScreen(safeBlockEntity.createContainerFactory());
                    } else {
                        if (blockEntityPassword.equals(password)) {
                            player.openHandledScreen(safeBlockEntity.createContainerFactory());
                        } else {
                            player.sendMessage(Text.literal("Wrong password."));
                            player.closeHandledScreen();
                        }
                    }
                }
            });
        });
    }
}
