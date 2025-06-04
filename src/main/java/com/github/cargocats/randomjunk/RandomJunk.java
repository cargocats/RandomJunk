package com.github.cargocats.randomjunk;

import com.github.cargocats.randomjunk.delay.OverdoseTimerCallback;
import com.github.cargocats.randomjunk.network.packet.SyncLidocaineUsagesS2C;
import com.github.cargocats.randomjunk.registry.*;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.world.timer.Timer;
import net.minecraft.world.timer.TimerCallbackSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

public class RandomJunk implements ModInitializer {
    public static final String MOD_ID = "randomjunk";
    public static final Logger LOG = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        RJEntityTypes.initialize();
        RJItems.initialize();
        RJItemGroups.initialize();
        RJStatusEffects.initialize();
        RJSounds.initialize();

        LOG.info("Initialized Random Junk");

        PayloadTypeRegistry.playS2C().register(SyncLidocaineUsagesS2C.ID, SyncLidocaineUsagesS2C.CODEC);

        TimerCallbackSerializer.INSTANCE.registerSerializer(OverdoseTimerCallback.ID, OverdoseTimerCallback.MAP_CODEC);

        ServerPlayConnectionEvents.JOIN.register((playNetworkHandler, packetSender, minecraftServer) -> {
            ServerPlayerEntity player = playNetworkHandler.getPlayer();

            if (OverdoseTimerCallback.UUIDS.contains(player.getUuid())) {
                OverdoseTimerCallback.giveEffect(player);
                OverdoseTimerCallback.UUIDS.remove(player.getUuid());
            }

            StateSaverAndLoader state = StateSaverAndLoader.getState((ServerWorld) player.getWorld());
            UUID uuid = player.getUuid();
            PlayerData playerData = state.players.computeIfAbsent(uuid, id -> new PlayerData());
            ServerPlayNetworking.send(player, new SyncLidocaineUsagesS2C(playerData.overdoseList.size()));
        });

        ServerLivingEntityEvents.AFTER_DEATH.register(Identifier.of(RandomJunk.MOD_ID, "overdose_after_death"), (livingEntity, damageSource) -> {
            if (livingEntity instanceof PlayerEntity playerEntity) {
                RandomJunk.LOG.info("{} has died", playerEntity);

                MinecraftServer server = playerEntity.getServer();
                if (server != null) {
                    Timer<MinecraftServer> timer = server.getSaveProperties().getMainWorldProperties().getScheduledEvents();
                    String overdoseIdentifier = "overdoseTimer" + playerEntity.getUuid();

                    if (timer.getEventNames().contains(overdoseIdentifier)) {
                        timer.remove(overdoseIdentifier);
                    }

                    StateSaverAndLoader state = StateSaverAndLoader.getState((ServerWorld) livingEntity.getWorld());
                    UUID uuid = livingEntity.getUuid();
                    PlayerData playerData = state.players.computeIfAbsent(uuid, id -> new PlayerData());

                    if (!playerData.overdoseList.isEmpty()) {
                        ServerPlayNetworking.send((ServerPlayerEntity) livingEntity, new SyncLidocaineUsagesS2C(0));
                        playerData.overdoseList.clear();
                        state.markDirty();
                    }
                }
            }
        });
    }
}
