package com.tacosupremes.runomancy.common.block.rune;

import com.tacosupremes.runomancy.common.block.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;

public class BlockEarthRune extends BlockRune
{

    public BlockEarthRune()
    {
        super();
        this.setDefaultState(this.stateContainer.getBaseState().with(mode, 0));
    }

    public static final IntegerProperty mode = IntegerProperty.create("mode",0, 16);

    @Override
    int getModeCount()
    {
        return ModBlocks.earthCount;
    }



    @Override
    public String getName()
    {
        return "earth_rune";
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
