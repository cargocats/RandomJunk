package com.github.cargocats.randomjunk.registry;

import com.github.cargocats.randomjunk.RandomJunk;
import com.github.cargocats.randomjunk.network.packet.SafePasswordPacket;
import com.github.cargocats.randomjunk.screen.PasswordScreenHandler;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.util.math.BlockPos;

public class RJScreens {
    public static final ExtendedScreenHandlerType<PasswordScreenHandler, SafePasswordPacket> PASSWORD_SCREEN = register("password_screen", new ExtendedScreenHandlerType<>((syncId, playerInventory, safePasswordPacket) -> {
        BlockPos blockPos = safePasswordPacket.blockPos();
        boolean hasPassword = safePasswordPacket.hasPassword();

        return new PasswordScreenHandler(syncId, playerInventory, blockPos, hasPassword);
    }, SafePasswordPacket.CODEC));

    public static <T extends ScreenHandler, D> ExtendedScreenHandlerType<T, D> register(String id, ExtendedScreenHandlerType<T, D> screenHandlerType) {
        return Registry.register(Registries.SCREEN_HANDLER, RandomJunk.id(id), screenHandlerType);
    }

    public static void initialize() {
        RandomJunk.LOG.info("Initialized screen handlers");
    }

}
