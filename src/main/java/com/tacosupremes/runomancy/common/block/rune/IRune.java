package com.tacosupremes.runomancy.common.block.rune;


import net.minecraft.block.BlockState;

public interface IRune
{
	public BlockState getStateWithMode(BlockState state, int i);

	public int getModeFromState(BlockState state);
}
	    
