package com.tacosupremes.runomancy.common.block.rune;

import net.minecraft.block.state.IBlockState;

public interface IRune {
	
	public IBlockState getStateWithMode(IBlockState state, int i);
	 public int getMetaFromState(IBlockState state);
}
	    
