package com.tacosupremes.runomancy.common.power.block.tile;

import net.minecraft.tileentity.TileEntity;

public class TilePowerTorch extends TileEntity implements IPowerNode{

	@Override
	public int getRange() {
		
		return 5;
	}

}
