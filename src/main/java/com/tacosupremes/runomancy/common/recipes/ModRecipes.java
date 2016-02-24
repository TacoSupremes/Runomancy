package com.tacosupremes.runomancy.common.recipes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;
import com.tacosupremes.runomancy.common.block.ModBlocks;
import com.tacosupremes.runomancy.common.item.ModItems;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;

public class ModRecipes {
	
	
	public static List<RuneChargerRecipe> rcr = new ArrayList<RuneChargerRecipe>();
	public static Map<String, ItemStack[]> shapeless = new HashMap<String, ItemStack[]>();
	public static Map<String, ItemStack[]> shaped = new HashMap<String, ItemStack[]>();

	//TODO IMPLEMENT OREDICTIONARY
	
	
	public static void postInit(){
		
		ItemStack w = new ItemStack(ModItems.runicWand,1, OreDictionary.WILDCARD_VALUE);
			
		rcr.add(new RuneChargerRecipe(new ItemStack(ModItems.runicIngot), new ItemStack(Items.iron_ingot, 1), 300));			
		rcr.add(new RuneChargerRecipe(new ItemStack(ModBlocks.powerTorch), new ItemStack(Blocks.torch, 1), 0));
		
		addShapelessRecipe(new ItemStack(ModBlocks.endRune,2), w, new ItemStack(Items.ender_pearl));
		addShapelessRecipe(new ItemStack(ModBlocks.obsidianRune,2), w, new ItemStack(Blocks.obsidian));
		addShapelessRecipe(new ItemStack(ModBlocks.fireRune,2), w, new ItemStack(Items.lava_bucket));
		addShapelessRecipe(new ItemStack(ModBlocks.waterRune,2), w, new ItemStack(Items.water_bucket));
		addShapelessRecipe(new ItemStack(ModBlocks.earthRune,2), w, new ItemStack(Blocks.sapling));
		
		addShapedRecipe(new ItemStack(ModItems.runicPickaxe,1,ModItems.runicPickaxe.getMaxDamage()-1), "RRR"," S "," S ", 'R', new ItemStack(ModItems.runicIngot), 'S', new ItemStack(Items.stick));
		addShapedRecipe(new ItemStack(ModItems.runicAxe,1,ModItems.runicPickaxe.getMaxDamage()-1), " RR"," SR"," S ", 'R', new ItemStack(ModItems.runicIngot), 'S', new ItemStack(Items.stick));
		addShapedRecipe(new ItemStack(ModItems.runicShovel,1,ModItems.runicPickaxe.getMaxDamage()-1), " R "," S "," S ", 'R', new ItemStack(ModItems.runicIngot), 'S', new ItemStack(Items.stick));
		addShapedRecipe(new ItemStack(ModItems.runicHoe,1,ModItems.runicPickaxe.getMaxDamage()-1), " RR"," S "," S ", 'R', new ItemStack(ModItems.runicIngot), 'S', new ItemStack(Items.stick));
		
	}

	private static void addShapelessRecipe(ItemStack itemStack, ItemStack... w) {
		GameRegistry.addShapelessRecipe(itemStack, (Object[])w);
		ItemStack is2 = new ItemStack(itemStack.getItem(),1,itemStack.getItemDamage());
		
		shapeless.put(is2.getUnlocalizedName(), w);
		
	}
	
	
	private static void addShapedRecipe(ItemStack stack, Object... recipeComponents) {
		GameRegistry.addShapedRecipe(stack, recipeComponents);
		
		//Borrowed From CraftingManager
		 String s = "";
	        int i = 0;
	        int j = 0;
	        int k = 0;

	        if (recipeComponents[i] instanceof String[])
	        {
	            String[] astring = (String[])((String[])recipeComponents[i++]);

	            for (int l = 0; l < astring.length; ++l)
	            {
	                String s2 = astring[l];
	                ++k;
	                j = s2.length();
	                s = s + s2;
	            }
	        }
	        else
	        {
	            while (recipeComponents[i] instanceof String)
	            {
	                String s1 = (String)recipeComponents[i++];
	                ++k;
	                j = s1.length();
	                s = s + s1;
	            }
	        }

	        Map<Character, ItemStack> map;

	        for (map = Maps.<Character, ItemStack>newHashMap(); i < recipeComponents.length; i += 2)
	        {
	            Character character = (Character)recipeComponents[i];
	            ItemStack itemstack = null;

	            if (recipeComponents[i + 1] instanceof Item)
	            {
	                itemstack = new ItemStack((Item)recipeComponents[i + 1]);
	            }
	            else if (recipeComponents[i + 1] instanceof Block)
	            {
	                itemstack = new ItemStack((Block)recipeComponents[i + 1], 1, 32767);
	            }
	            else if (recipeComponents[i + 1] instanceof ItemStack)
	            {
	                itemstack = (ItemStack)recipeComponents[i + 1];
	            }

	            map.put(character, itemstack);
	        }

	        ItemStack[] aitemstack = new ItemStack[j * k];

	        for (int i1 = 0; i1 < j * k; ++i1)
	        {
	            char c0 = s.charAt(i1);

	            if (map.containsKey(Character.valueOf(c0)))
	            {
	                aitemstack[i1] = ((ItemStack)map.get(Character.valueOf(c0))).copy();
	            }
	            else
	            {
	                aitemstack[i1] = null;
	            }
	        }

	        ItemStack is2 = new ItemStack(stack.getItem(),1,stack.getItemDamage());
			
	        
	        shaped.put(is2.getUnlocalizedName(), aitemstack);
	      
	    }

	public static ItemStack[] getRecipe(ItemStack item) {
		
	ItemStack is2 = new ItemStack(item.getItem(), 1, item.getItemDamage());

		
		return shapeless.get(is2.getUnlocalizedName()) != null ? shapeless.get(is2.getUnlocalizedName()) : shaped.get(is2.getUnlocalizedName());
		
		
		
	
	}
		
	
	
	
	
	

}
