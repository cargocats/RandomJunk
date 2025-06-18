package com.github.cargocats.randomjunk.network;

import com.github.cargocats.randomjunk.RandomJunk;
import com.github.cargocats.randomjunk.block.entity.SafeBlockEntity;
import com.github.cargocats.randomjunk.network.packet.AttemptOpenSafeC2S;
import com.github.cargocats.randomjunk.network.packet.SyncLidocaineUsagesS2C;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
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
                        RandomJunk.LOG.info("Block entity password is empty");
                        safeBlockEntity.setPassword(password);

                        player.openHandledScreen(new NamedScreenHandlerFactory() {
                            @Override
                            public Text getDisplayName() {
                                return Text.literal("Safe");
                            }

                            @Override
                            public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
                                return GenericContainerScreenHandler.createGeneric9x3(syncId, playerInventory, safeBlockEntity);
                            }
                        });
                        // Handle setting password here
                    } else {
                        if (blockEntityPassword.equals(password)) {
                            RandomJunk.LOG.info("Password equals the block entity password, opening the safe block.");

                            player.openHandledScreen(new NamedScreenHandlerFactory() {
                                @Override
                                public Text getDisplayName() {
                                    return Text.literal("Safe");
                                }

                                @Override
                                public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
                                    return GenericContainerScreenHandler.createGeneric9x3(syncId, playerInventory, safeBlockEntity);
                                }
                            });
                        } else {
                            player.sendMessage(Text.literal("Wrong password."));
                            player.closeHandledScreen();
                        }
                        RandomJunk.LOG.info("Okay, I read the password its {}", blockEntityPassword);
                    }

                    RandomJunk.LOG.info("Received password {} from player {}", password, player.getName());
                }
            });
        });
    }
}
