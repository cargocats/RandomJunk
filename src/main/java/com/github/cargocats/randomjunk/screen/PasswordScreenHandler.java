package com.github.cargocats.randomjunk.screen;

import com.github.cargocats.randomjunk.block.entity.SafeBlockEntity;
import com.github.cargocats.randomjunk.network.packet.SafePasswordPacketS2C;
import com.github.cargocats.randomjunk.registry.RJBlocks;
import com.github.cargocats.randomjunk.registry.RJScreens;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.util.math.BlockPos;

public class PasswordScreenHandler extends ScreenHandler {
    private final SafeBlockEntity safeBlockEntity;
    private final ScreenHandlerContext context;
    private final boolean hasPassword;

    public PasswordScreenHandler(int syncId, PlayerInventory playerInventory, SafePasswordPacketS2C packet) {
        this(syncId, playerInventory, (SafeBlockEntity) playerInventory.player.getWorld().getBlockEntity(packet.blockPos()), packet.hasPassword());
    }

    public PasswordScreenHandler(int syncId, PlayerInventory playerInventory, SafeBlockEntity safeBlockEntity, boolean hasPassword) {
        super(RJScreens.PASSWORD_SCREEN, syncId);

        this.safeBlockEntity = safeBlockEntity;
        this.context = ScreenHandlerContext.create(this.safeBlockEntity.getWorld(), this.safeBlockEntity.getPos());
        this.hasPassword = hasPassword;

        this.addPlayerSlots(playerInventory, 8, 98);
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int slot) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return canUse(this.context, player, RJBlocks.SAFE_BLOCK);
    }

    public BlockPos getBlockPos() {
        return this.safeBlockEntity.getPos();
    }

    public boolean hasPassword() {
        return this.hasPassword;
    }
}