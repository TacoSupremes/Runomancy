package com.tacosupremes.runomancy.common.runelogic;

import com.tacosupremes.runomancy.common.block.rune.tile.TileEndRune;

import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public interface IRuneEffect {
	
	public void doEffect(World w, BlockPos pos, TileEndRune te, NBTTagCompound nbt);

	public Block[] getNeededBlocks();
	
	public boolean isGenerating();
	
	public int[] getFinalBlockStates();
	
	public int getPowerCapacity();
	
	public int getTransferRate();
	
	public String getName();

	
	
}



