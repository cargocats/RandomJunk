package com.github.cargocats.randomjunk;

import com.github.cargocats.randomjunk.init.RJBlockEntityTypes;
import com.github.cargocats.randomjunk.init.RJBlocks;
import com.github.cargocats.randomjunk.init.RJComponents;
import com.github.cargocats.randomjunk.init.RJDamageTypes;
import com.github.cargocats.randomjunk.init.RJDataAttachments;
import com.github.cargocats.randomjunk.init.RJEntityTypes;
import com.github.cargocats.randomjunk.init.RJItemGroups;
import com.github.cargocats.randomjunk.init.RJItems;
import com.github.cargocats.randomjunk.init.RJScreens;
import com.github.cargocats.randomjunk.init.RJSounds;
import com.github.cargocats.randomjunk.init.RJStatusEffects;
import com.github.cargocats.randomjunk.network.RJNetwork;
import com.github.cargocats.randomjunk.network.packet.SyncLidocaineUsagesS2C;
import com.github.cargocats.randomjunk.timer.OverdoseTimerCallback;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.world.timer.Timer;
import net.minecraft.world.timer.TimerCallbackSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

public class RandomJunk implements ModInitializer {
    public static final String MOD_ID = "randomjunk";
    public static final Logger LOG = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        registerRegistries();
        registerEvents();
        registerTimers();

        LOG.info("Initialized Random Junk");
    }

    public void registerRegistries() {
        RJComponents.init();
        RJNetwork.init();
        RJDataAttachments.init();

        RJItems.init();
        RJBlocks.init();
        RJBlockEntityTypes.init();
        RJEntityTypes.init();
        RJScreens.init();
        RJStatusEffects.init();

        RJSounds.init();
        RJItemGroups.init();
        RJDamageTypes.init();
    }

    public void registerEvents() {
        ServerPlayConnectionEvents.JOIN.register((playNetworkHandler, packetSender, minecraftServer) -> {
            ServerPlayerEntity playerEntity = playNetworkHandler.getPlayer();

            if (OverdoseTimerCallback.UUIDS.contains(playerEntity.getUuid())) {
                OverdoseTimerCallback.giveEffect(playerEntity);
                OverdoseTimerCallback.UUIDS.remove(playerEntity.getUuid());
            }

            ServerPlayNetworking.send(playerEntity, new SyncLidocaineUsagesS2C(playerEntity.getAttachedOrCreate(RJDataAttachments.OVERDOSE_LIST).size()));
        });

        ServerLivingEntityEvents.AFTER_DEATH.register(RandomJunk.id("overdose_after_death"), (livingEntity, damageSource) -> {
            if (livingEntity instanceof PlayerEntity playerEntity) {
                MinecraftServer server = playerEntity.getServer();

                if (server != null) {
                    Timer<MinecraftServer> timer = server.getSaveProperties().getMainWorldProperties().getScheduledEvents();
                    String overdoseIdentifier = "overdoseTimer" + playerEntity.getUuid();

                    timer.remove(overdoseIdentifier);

                    playerEntity.setAttached(RJDataAttachments.OVERDOSE_LIST, new ArrayList<>());
                    ServerPlayNetworking.send(((ServerPlayerEntity) playerEntity), new SyncLidocaineUsagesS2C(0));
                }
            }
        });
    }

    public void registerTimers() {
        TimerCallbackSerializer.INSTANCE.registerSerializer(OverdoseTimerCallback.ID, OverdoseTimerCallback.MAP_CODEC);
    }

    public static Identifier id(String path) {
        return Identifier.of(MOD_ID, path);
    }
}
