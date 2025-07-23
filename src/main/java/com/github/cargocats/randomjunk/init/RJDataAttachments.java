package com.github.cargocats.randomjunk.init;

import com.github.cargocats.randomjunk.RandomJunk;
import com.mojang.serialization.Codec;
import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentSyncPredicate;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.minecraft.network.codec.PacketCodecs;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("UnstableApiUsage")
public class RJDataAttachments {
    public static final AttachmentType<Integer> LIDOCAINE_CONSUMED = AttachmentRegistry.create(
            RandomJunk.id("lidocaine_consumed"),
            builder -> builder
                    .initializer(() -> 0)
                    .persistent(Codec.INT)
                    .syncWith(PacketCodecs.INTEGER, AttachmentSyncPredicate.all())
    );
    public static final AttachmentType<List<Long>> OVERDOSE_LIST = AttachmentRegistry.create(
            RandomJunk.id("overdose_list"),
            builder -> builder
                    .initializer(ArrayList::new)
                    .persistent(Codec.list(Codec.LONG))
                    .syncWith(PacketCodecs.collection(ArrayList::new, PacketCodecs.LONG), AttachmentSyncPredicate.all())
    );

    public static void init() {
        RandomJunk.LOG.info("Initialized data attachments");
    }
}
