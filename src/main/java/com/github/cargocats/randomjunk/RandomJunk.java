package com.github.cargocats.randomjunk;

import com.github.cargocats.randomjunk.delay.OverdoseTimerCallback;
import com.github.cargocats.randomjunk.registry.*;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.world.timer.Timer;
import net.minecraft.world.timer.TimerCallbackSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

        LOG.info("Initialized Random Junk!");

        TimerCallbackSerializer.INSTANCE.registerSerializer(OverdoseTimerCallback.ID, OverdoseTimerCallback.MAP_CODEC);

        ServerPlayConnectionEvents.JOIN.register((playNetworkHandler, packetSender, minecraftServer) -> {
            ServerPlayerEntity player = playNetworkHandler.getPlayer();

            if (OverdoseTimerCallback.UUIDS.contains(player.getUuid())) {
                OverdoseTimerCallback.giveEffect(player);
                OverdoseTimerCallback.UUIDS.remove(player.getUuid());
            }
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
                }
            }
        });
    }
}
