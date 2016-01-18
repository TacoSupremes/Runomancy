package com.tacosupremes.runomancy.common.power.item;

import net.minecraft.item.ItemStack;

public interface IRunicBattery {
	
	public int getPower(ItemStack is);
	
	public int addPower(ItemStack is, int a, boolean doit);
	
	public int removePower(ItemStack is, int a, boolean doit);
	
	public void setPower(ItemStack is, int a);

	public int getCapacity(ItemStack is);
}
