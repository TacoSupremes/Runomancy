package com.tacosupremes.runomancy.common.power.block;

import com.tacosupremes.runomancy.common.block.BlockModContainer;
import com.tacosupremes.runomancy.common.power.block.tile.TilePowerStorage;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockPowerStorage extends BlockModContainer{

	public BlockPowerStorage() {
		super(Material.glass, "battery");
		
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		
		return new TilePowerStorage();
	}

	@Override
	protected Class<? extends TileEntity> tile() {

		return TilePowerStorage.class;
	}

}
