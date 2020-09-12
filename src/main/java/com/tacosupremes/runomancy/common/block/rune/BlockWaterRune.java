package com.tacosupremes.runomancy.common.block.rune;


import com.tacosupremes.runomancy.common.block.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;

public class BlockWaterRune extends BlockRune
{

    public static final IntegerProperty mode = IntegerProperty.create("mode",0, 16);


    public BlockWaterRune()
    {
        super();
        this.setDefaultState(this.stateContainer.getBaseState().with(mode, 0));
    }


    public BlockWaterRune(Properties properties)
    {
        super(properties);
    }

    @Override
    int getModeCount()
    {
        return ModBlocks.waterCount;
    }

    @Override
    public String getName()
    {
        return "water_rune";
    }

    @Override
    public BlockState getStateWithMode(BlockState state, int i)
    {
        return this.getDefaultState().with(mode, i);
    }

    @Override
    public int getModeFromState(BlockState state)
    {
        return state.get(mode);
    }


    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder)
    {
        builder.add(mode);
    }

}
