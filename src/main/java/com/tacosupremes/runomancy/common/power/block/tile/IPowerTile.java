package com.tacosupremes.runomancy.common.power.block.tile;

public interface IPowerTile extends IPowerNode {
	
	public int getPower();

	public int addPower(int i);
	
	public int removePower(int i);
	
	public int getMaxPower();
	

	

}
