package com.tacosupremes.runomancy.common.block;


import net.minecraft.block.BlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;

public abstract class BlockModContainer extends BlockMod
{

	public BlockModContainer(Properties properties)
	{
		super(properties);
	}

	@Override
	public abstract TileEntity createTileEntity(BlockState state, IBlockReader world);

	@Override
	public boolean hasTileEntity(BlockState state)
	{
		return true;
	}
}
