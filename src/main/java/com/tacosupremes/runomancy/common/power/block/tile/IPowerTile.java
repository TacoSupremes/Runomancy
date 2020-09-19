package com.tacosupremes.runomancy.common.power.block.tile;

import com.tacosupremes.runomancy.common.block.tile.INode;

public interface IPowerTile extends INode
{
	
	public int getPower();

	public int addPower(int i);
	
	public int removePower(int i);
	
	public int getMaxPower();
	

	

}
