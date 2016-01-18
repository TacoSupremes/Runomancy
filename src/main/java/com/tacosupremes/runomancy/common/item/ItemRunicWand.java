package com.tacosupremes.runomancy.common.item;

import net.minecraft.item.ItemStack;

public class ItemRunicWand extends ItemMod {

	public ItemRunicWand() {
		super("runicWand",0);
		this.setMaxDamage(250);
		this.setContainerItem(this);
		
		
	}

	@Override
	public ItemStack getContainerItem(ItemStack is) {
		
		
		
		return is;
	}


	
	
	
	
	
	

}
