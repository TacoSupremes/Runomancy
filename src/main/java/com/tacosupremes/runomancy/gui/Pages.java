package com.tacosupremes.runomancy.gui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tacosupremes.runomancy.common.block.ModBlocks;
import com.tacosupremes.runomancy.common.item.ModItems;
import com.tacosupremes.runomancy.common.lib.LibMisc;
import com.tacosupremes.runomancy.common.runelogic.RuneFormations;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import scala.actors.threadpool.Arrays;

public class Pages {
	

	
	public static Map<String, Page> pages = new HashMap<String, Page>();	
	
	public static String[] p = new String[]{LibMisc.MODID + ".basics"};
	
	public static void postInit(){
		
		addPage("HOME", new ContentsPage());
		addPage("RUNES", new RunicContentsPage());
		addPage("BASICS", new ListPage(Arrays.asList(p)));
		addPage("GENERATING", new ListPage(RuneFormations.generatingEffects));
		addPage("FUNCTIONAL", new ListPage(RuneFormations.functionalEffects));
		
		
		
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
