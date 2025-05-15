package com.github.cargocats.randomjunk;

import com.github.cargocats.randomjunk.delay.OverdoseTimerCallback;
import com.github.cargocats.randomjunk.registry.*;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.server.network.ServerPlayerEntity;
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

        LOG.info("Initialized Random Junk!");

        TimerCallbackSerializer.INSTANCE.registerSerializer(OverdoseTimerCallback.ID, OverdoseTimerCallback.MAP_CODEC);

        ServerPlayConnectionEvents.JOIN.register((playNetworkHandler, packetSender, minecraftServer) -> {
            ServerPlayerEntity player = playNetworkHandler.getPlayer();

            if (OverdoseTimerCallback.UUIDS.contains(player.getUuid())) {
                OverdoseTimerCallback.giveEffect(player);
                OverdoseTimerCallback.UUIDS.remove(player.getUuid());
            }
        });
    }
}
