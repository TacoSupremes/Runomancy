package com.tacosupremes.runomancy.common.recipes;

import com.tacosupremes.runomancy.common.item.ModItems;

import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

public class PotionAmuletRecipe implements IRecipe {

	@Override
	public boolean matches(InventoryCrafting ii, World w) {
		
		ItemStack[] test = new ItemStack[]{new ItemStack(Items.apple), new ItemStack(Items.potionitem),new ItemStack(Items.apple),
										   new ItemStack(Items.gold_ingot), new ItemStack(Items.diamond), new ItemStack(Items.gold_ingot),
										   new ItemStack(Items.apple), new ItemStack(Items.gold_ingot), new ItemStack(Items.apple)};
		
		for(int i=0;i<ii.getSizeInventory();i++){
			
				if(ii.getStackInSlot(i) == test[i])
					continue;	
				else if(ii.getStackInSlot(i) != null && test[i].getItem() == ii.getStackInSlot(i).getItem() && test[i].getItem() == Items.potionitem)
						continue;
						
				System.out.print("false");
					return false;
			
		}
			
		return true;
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting ii) {
		
		ItemStack is = new ItemStack(ModItems.potionAmulet,1,0);
		
		
		
		for(int i=0;i<ii.getSizeInventory();i++){
			
			
			if(ii.getStackInSlot(i).getItem() == Items.potionitem){
				is.setTagCompound(ii.getStackInSlot(i).getTagCompound());
				break;
			}
			
			
		}
		
		
		return is;
	}

	@Override
	public int getRecipeSize() {

		return 10;
	}

	@Override
	public ItemStack getRecipeOutput() {
	
		return null;
	}

	@Override
	public ItemStack[] getRemainingItems(InventoryCrafting inv) {
		
		return new ItemStack[]{null,null,null,
							   null,null,null,
							   null,null,null};
	}

}
