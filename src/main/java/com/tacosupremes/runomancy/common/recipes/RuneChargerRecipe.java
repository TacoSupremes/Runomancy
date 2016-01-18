package com.tacosupremes.runomancy.common.recipes;

import net.minecraft.item.ItemStack;

public class RuneChargerRecipe {
	
	private ItemStack in;
	private ItemStack out;
	private int cost;
	public RuneChargerRecipe(ItemStack in, ItemStack out, int cost){
		this.in = in;
		this.out = out;
		this.cost = cost;
		
		
	}
	public ItemStack getIn() {
		return in;
	}
	public ItemStack getOut() {
		return out;
	}
	public int getCost() {
		return cost;
	}
	
	
	
}
