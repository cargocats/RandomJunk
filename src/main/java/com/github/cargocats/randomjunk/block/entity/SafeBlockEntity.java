package com.github.cargocats.randomjunk.block.entity;

import com.github.cargocats.randomjunk.block.SafeBlock;
import com.github.cargocats.randomjunk.network.packet.SafePasswordPacketS2C;
import com.github.cargocats.randomjunk.init.RJBlockEntityTypes;
import com.github.cargocats.randomjunk.screen.PasswordScreenHandler;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.LootableContainerBlockEntity;
import net.minecraft.block.entity.ViewerCountManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class SafeBlockEntity extends LootableContainerBlockEntity implements ExtendedScreenHandlerFactory<SafePasswordPacketS2C> {
    private DefaultedList<ItemStack> inventory = DefaultedList.ofSize(27, ItemStack.EMPTY);
    private String password = "";

    private final ViewerCountManager stateManager = new ViewerCountManager() {
        @Override
        protected void onContainerOpen(World world, BlockPos pos, BlockState state) {
            SafeBlockEntity.this.playSound(state, SoundEvents.BLOCK_BARREL_OPEN);
            SafeBlockEntity.this.setOpen(state, true);
        }

        @Override
        protected void onContainerClose(World world, BlockPos pos, BlockState state) {
            SafeBlockEntity.this.playSound(state, SoundEvents.BLOCK_BARREL_CLOSE);
            SafeBlockEntity.this.setOpen(state, false);
        }

        @Override
        protected void onViewerCountUpdate(World world, BlockPos pos, BlockState state, int oldViewerCount, int newViewerCount) {
        }

        @Override
        protected boolean isPlayerViewing(PlayerEntity player) {
            if (player.currentScreenHandler instanceof GenericContainerScreenHandler) {
                Inventory inventory = ((GenericContainerScreenHandler)player.currentScreenHandler).getInventory();
                return inventory == SafeBlockEntity.this;
            } else {
                return false;
            }
        }
    };

    public void setPassword(String password) {
        this.password = password;
        markDirty();
    }

    public String getPassword() {
        return this.password;
    }

    protected SafeBlockEntity(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState) { super(blockEntityType, blockPos, blockState); }

    public SafeBlockEntity(BlockPos pos, BlockState state) {
        this(RJBlockEntityTypes.SAFE_BLOCK, pos, state);
    }

    @Override
    protected void readData(ReadView view) {
        super.readData(view);
        this.password = view.getString("Password", "");

        if (!this.readLootTable(view)) {
            Inventories.readData(view, this.inventory);
        }
    }

    protected void writeData(WriteView view) {
        super.writeData(view);
        view.putString("Password", this.password);

        if (!this.writeLootTable(view)) {
            Inventories.writeData(view, this.inventory);
        }
    }

    @Override
    public int size() { return 27; }

    @Override
    protected Text getContainerName() { return Text.literal("Safe"); }

    @Override
    protected DefaultedList<ItemStack> getHeldStacks() { return this.inventory; }

    @Override
    protected void setHeldStacks(DefaultedList<ItemStack> inventory) { this.inventory = inventory; }

    @Override
    protected ScreenHandler createScreenHandler(int syncId, PlayerInventory playerInventory) {
        return GenericContainerScreenHandler.createGeneric9x3(syncId, playerInventory, this);
    }

    @Override
    public SafePasswordPacketS2C getScreenOpeningData(ServerPlayerEntity player) {
        return new SafePasswordPacketS2C(this.getPos(), !this.getPassword().isEmpty());
    }

    public ExtendedScreenHandlerFactory<SafePasswordPacketS2C> createPasswordScreenFactory() {
        return new ExtendedScreenHandlerFactory<>() {
            @Override
            public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
                return new PasswordScreenHandler(syncId, playerInventory, new SafePasswordPacketS2C(getPos(), !getPassword().isEmpty()));
            }

            @Override
            public Text getDisplayName() {
                return getContainerName();
            }

            @Override
            public SafePasswordPacketS2C getScreenOpeningData(ServerPlayerEntity player) {
                return SafeBlockEntity.this.getScreenOpeningData(player);
            }
        };
    }

    public NamedScreenHandlerFactory createContainerFactory() {
        return new NamedScreenHandlerFactory() {
            @Override
            public Text getDisplayName() {
                return getContainerName();
            }

            @Override
            public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
                return createScreenHandler(syncId, playerInventory);
            }
        };
    }

    @Override
    public void onOpen(PlayerEntity player) {
        if (!this.removed && !player.isSpectator()) {
            this.stateManager.openContainer(player, this.getWorld(), this.getPos(), this.getCachedState());
        }
    }

    @Override
    public void onClose(PlayerEntity player) {
        if (!this.removed && !player.isSpectator()) {
            this.stateManager.closeContainer(player, this.getWorld(), this.getPos(), this.getCachedState());
        }
    }

    public void tick() {
        if (!this.removed) {
            this.stateManager.updateViewerCount(this.getWorld(), this.getPos(), this.getCachedState());
        }
    }

    void setOpen(BlockState state, boolean open) {
        if (this.world != null) {
            this.world.setBlockState(this.getPos(), state.with(SafeBlock.OPEN, open), Block.NOTIFY_ALL);
        }
    }

    void playSound(BlockState state, SoundEvent soundEvent) {
        double d = this.pos.getX() + 0.5;
        double e = this.pos.getY() + 0.5;
        double f = this.pos.getZ() + 0.5;

        if (this.world != null) {
            this.world.playSound(null, d, e, f, soundEvent, SoundCategory.BLOCKS, 0.5F, this.world.random.nextFloat() * 0.1F + 0.9F);
        }
    }
}
