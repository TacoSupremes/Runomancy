package com.tacosupremes.runomancy.gui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tacosupremes.runomancy.common.block.ModBlocks;
import com.tacosupremes.runomancy.common.item.ModItems;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class Pages {
	

	
	public static Map<String, Page> pages = new HashMap<String, Page>();	
	
	public static void init(){
		
		addPage("HOME", new ContentsPage());
		addPage("RUNES", new RunicContentsPage());
		
		
		for(Item i : ModItems.items){
			
			if(i instanceof IPageGiver){
				
				addPage(i.getUnlocalizedName(),((IPageGiver)i).getPage());
				addPage(i.getUnlocalizedName().substring(5)+"REC", new RecipePage(new ItemStack(i)));
			}
			
			
		}
		
		for(Block i : ModBlocks.blocks){
			
			if(i instanceof IPageGiver){
				
				addPage(i.getUnlocalizedName(),((IPageGiver)i).getPage());
				addPage(i.getUnlocalizedName().substring(5)+"REC", new RecipePage(new ItemStack(i)));
				
			}
			
			
		}
		
		
	}
	
	
	
	
	public static void addPage(String name, Page p){
		
		pages.put(name, p);
	}
	

}
