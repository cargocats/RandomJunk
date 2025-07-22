package com.github.cargocats.randomjunk.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.PillarBlock;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.Direction;

public class ToiletPaperBlock extends PillarBlock {
    public static final IntProperty ROLLS = IntProperty.of("rolls", 0, 2);
    public static final MapCodec<ToiletPaperBlock> CODEC = createCodec(ToiletPaperBlock::new);

    @Override
    public MapCodec<ToiletPaperBlock> getCodec() {
        return CODEC;
    }

    public ToiletPaperBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getStateManager().getDefaultState().with(AXIS, Direction.Axis.Y).with(ROLLS, 2));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(ROLLS);
    }
}
