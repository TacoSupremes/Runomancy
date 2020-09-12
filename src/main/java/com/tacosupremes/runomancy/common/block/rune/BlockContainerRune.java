package com.tacosupremes.runomancy.common.block.rune;


import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;

public abstract class BlockContainerRune extends BlockRune
{

	public BlockContainerRune(Properties properties)
	{
		super(properties);
	}

	public BlockContainerRune()
	{
		this(Properties.create(Material.ROCK).notSolid().hardnessAndResistance(1.0F, 1.5F).sound(SoundType.STONE));
	}

	@Override
	public abstract TileEntity createTileEntity(BlockState state, IBlockReader world);

	@Override
	public boolean hasTileEntity(BlockState state)
	{
		return true;
	}
}