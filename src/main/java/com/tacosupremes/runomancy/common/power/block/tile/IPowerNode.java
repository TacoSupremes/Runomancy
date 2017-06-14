package com.tacosupremes.runomancy.common.power.block.tile;

import java.util.List;

import net.minecraft.util.math.BlockPos;

public interface IPowerNode {

	public int getRange();
	
	public List<BlockPos> getLinkedBlocks();
	
	public void updateLinkedBlocks(BlockPos pos);
	
	public BlockPos getPos();
	
	
	
	
}
