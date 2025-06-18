package com.github.cargocats.randomjunk.screen;

import com.github.cargocats.randomjunk.registry.RJScreens;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.util.math.BlockPos;

public class PasswordScreenHandler extends ScreenHandler {
    private final BlockPos blockPos;
    private final boolean hasPassword;

    public PasswordScreenHandler(int syncId, PlayerInventory playerInventory, BlockPos blockPos, boolean hasPassword) {
        super(RJScreens.PASSWORD_SCREEN, syncId);
        this.blockPos = blockPos;
        this.hasPassword = hasPassword;
        this.addPlayerSlots(playerInventory, 8, 98);
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int slot) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return true;
    }

    public BlockPos getBlockPos() {
        return blockPos;
    }

    public boolean hasPassword() {
        return this.hasPassword;
    }
}