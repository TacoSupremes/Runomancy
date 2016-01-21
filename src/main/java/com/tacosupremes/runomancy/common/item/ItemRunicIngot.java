package com.tacosupremes.runomancy.common.item;

import com.tacosupremes.runomancy.gui.Categories;
import com.tacosupremes.runomancy.gui.IPageGiver;
import com.tacosupremes.runomancy.gui.ItemPage;
import com.tacosupremes.runomancy.gui.Page;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemRunicIngot extends ItemMod implements IPageGiver{

	public ItemRunicIngot() {
		super("runicIngot", 0);
		
	}

	
	@Override
	public Page getPage() {
		
		return new ItemPage(new ItemStack(this));
		
	}


	@Override
	public Categories getCategories() {
		
		return Categories.RunicItems;
		
	}
	
	@Override
	public boolean hasNormalRecipe() {
		
		return false;
		
	}


	@Override
	public int getSubPages() {
		
		return 0;
		
	}
	
}
