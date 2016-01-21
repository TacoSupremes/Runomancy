package com.tacosupremes.runomancy.gui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tacosupremes.runomancy.common.block.ModBlocks;
import com.tacosupremes.runomancy.common.item.ModItems;
import com.tacosupremes.runomancy.common.lib.LibMisc;
import com.tacosupremes.runomancy.common.recipes.ModRecipes;
import com.tacosupremes.runomancy.common.recipes.RuneChargerRecipe;
import com.tacosupremes.runomancy.common.runelogic.RuneFormations;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import scala.actors.threadpool.Arrays;

public class Pages {
	

	
	public static Map<String, Page> pages = new HashMap<String, Page>();	
	
	public static String[] p = new String[]{"runomancy.begin"};
	
	
	public static List<Item> runicItems = new ArrayList<Item>();

	public static List<Block> runicBlocks = new ArrayList<Block>();
	
	public static List<String> taken = new ArrayList<String>();
	
	public static void postInit(){
		
		
		
		
		addPage("HOME", new ContentsPage("HOME"));
		addPage("RUNES", new ItemListPage(ModBlocks.runes, "RUNES",0));
		addPage("BASICS", new ListPage(Arrays.asList(p),"BASICS"));
		addPage("GENERATING", new ListPage(RuneFormations.generatingEffects, "GENERATING"));
		addPage("FUNCTIONAL", new ListPage(RuneFormations.functionalEffects, "FUNCTIONAL"));
		
		
		
		
		for(Item i : ModItems.items){
			
			if(i instanceof IPageGiver){
				
				
				
				addPage(i.getUnlocalizedName(),((IPageGiver)i).getPage());
				
				for(int j = 0; j<((IPageGiver)i).getSubPages(); j++){
					
					addPage(i.getUnlocalizedName()+(j+1),((IPageGiver)i).getPage());

				}
				if(((IPageGiver)i).hasNormalRecipe())
				addPage(i.getUnlocalizedName().substring(5)+"REC", new RecipePage(new ItemStack(i)));
				if(((IPageGiver)i).getCategories() == Categories.RunicItems)
					runicItems.add(i);
			}
			
			
		}
		
	for(Item i : ModItems.nitems){
			
			if(i instanceof IPageGiver){
				
				
				
				addPage(i.getUnlocalizedName(),((IPageGiver)i).getPage());
				
				for(int j = 0; j<((IPageGiver)i).getSubPages(); j++){
					
					addPage(i.getUnlocalizedName()+(j+1),((IPageGiver)i).getPage());

				}
				if(((IPageGiver)i).hasNormalRecipe())
				addPage(i.getUnlocalizedName().substring(5)+"REC", new RecipePage(new ItemStack(i)));
				if(((IPageGiver)i).getCategories() == Categories.RunicItems)
					runicItems.add(i);
			}
			
			
		}
		
		
		for(Block i : ModBlocks.blocks){
			
			if(i instanceof IPageGiver){
				
				addPage(i.getUnlocalizedName(),((IPageGiver)i).getPage());
				
				for(int j = 0; j<((IPageGiver)i).getSubPages(); j++){
					
					addPage(i.getUnlocalizedName()+(j+1),((IPageGiver)i).getPage());

				}
				if(((IPageGiver)i).hasNormalRecipe())
				addPage(i.getUnlocalizedName().substring(5)+"REC", new RecipePage(new ItemStack(i)));
				if(((IPageGiver)i).getCategories() == Categories.RunicBlocks)
					runicBlocks.add(i);
			}
			
			
		}
		
		addPage("RUNICITEMS", new ItemListPage(Pages.runicItems, "RUNICITEMS"));
		
		addPage("RUNICBLOCKS", new ItemListPage(Pages.runicBlocks, "RUNICBLOCKS", 0));
		
		for(RuneChargerRecipe r : ModRecipes.rcr){
		addPage(r.getOut().getUnlocalizedName().substring(5)+"REC", new RuneChargerPage(r));
			
			
		}
		
		for(String s : p){
			
			addPage(s, new TextPage(s, "BASICS", 2));
		}
		
	}
	
	
	
	
	public static void addPage(String name, Page p){
		
		pages.put(name, p);
	}
	

}
