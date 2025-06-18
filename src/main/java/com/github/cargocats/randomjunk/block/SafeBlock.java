package com.github.cargocats.randomjunk.block;

import com.github.cargocats.randomjunk.block.entity.SafeBlockEntity;
import com.github.cargocats.randomjunk.network.packet.SafePasswordPacket;
import com.github.cargocats.randomjunk.screen.PasswordScreenHandler;
import com.mojang.serialization.MapCodec;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;


public class SafeBlock extends BlockWithEntity {
    public static final MapCodec<SafeBlock> CODEC = createCodec(SafeBlock::new);
    public static final EnumProperty<Direction> FACING = Properties.FACING;
    public static final BooleanProperty OPEN = Properties.OPEN;

    public SafeBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getStateManager().getDefaultState().with(FACING, Direction.NORTH).with(OPEN, false));
    }

    @Override
    public MapCodec<? extends BlockWithEntity> getCodec() { return CODEC; }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if (world instanceof ServerWorld ignored && world.getBlockEntity(pos) instanceof SafeBlockEntity safeBlockEntity) {

            player.openHandledScreen(new ExtendedScreenHandlerFactory<SafePasswordPacket>() {
                @Override
                public @Nullable ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
                    return new PasswordScreenHandler(syncId, playerInventory, safeBlockEntity.getPos(), !safeBlockEntity.getPassword().isEmpty());
                }

                @Override
                public Text getDisplayName() { return Text.literal("Password"); }

                @Override
                public SafePasswordPacket getScreenOpeningData(ServerPlayerEntity player) {
                    return new SafePasswordPacket(safeBlockEntity.getPos(), !safeBlockEntity.getPassword().isEmpty());
                }
            });
        }
        return ActionResult.SUCCESS;
    }

    @Override
    protected @Nullable NamedScreenHandlerFactory createScreenHandlerFactory(BlockState state, World world, BlockPos pos) {
        return super.createScreenHandlerFactory(state, world, pos);
    }

    @Override
    protected void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof SafeBlockEntity safeBlockEntity) {
            safeBlockEntity.tick();
        }
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) { return new SafeBlockEntity(pos, state); }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, OPEN);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(FACING, ctx.getPlayerLookDirection().getOpposite());
    }
}
