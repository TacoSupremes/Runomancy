package com.tacosupremes.runomancy.common.runelogic;

import com.tacosupremes.runomancy.common.block.rune.tile.TileEndRune;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IRuneEffect {
	
	void doEffect(World w, BlockPos pos, TileEndRune te, CompoundNBT nbt);

	Block[] getNeededBlocks();
	
	boolean isGenerating();

	default boolean isConsuming()
	{
		return !isGenerating();
	}
	
	int[] getFinalBlockStates();

	int getPowerCapacity();
	
	int getTransferRate();
	
	String getName();

	
	
}



