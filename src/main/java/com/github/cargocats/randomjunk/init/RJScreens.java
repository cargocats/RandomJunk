package com.github.cargocats.randomjunk.init;

import com.github.cargocats.randomjunk.RandomJunk;
import com.github.cargocats.randomjunk.network.packet.SafePasswordPacketS2C;
import com.github.cargocats.randomjunk.screen.PasswordScreenHandler;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;

public class RJScreens {
    public static final ScreenHandlerType<PasswordScreenHandler> PASSWORD_SCREEN = register("password", PasswordScreenHandler::new, SafePasswordPacketS2C.PACKET_CODEC);

    public static <T extends ScreenHandler, D extends CustomPayload> ExtendedScreenHandlerType<T, D> register(String name, ExtendedScreenHandlerType.ExtendedFactory<T, D> factory, PacketCodec<? super RegistryByteBuf, D> codec) {
        return Registry.register(Registries.SCREEN_HANDLER, RandomJunk.id(name), new ExtendedScreenHandlerType<>(factory, codec));
    }

    public static void init() {
        RandomJunk.LOG.info("Initialized screen handlers");
    }

}
