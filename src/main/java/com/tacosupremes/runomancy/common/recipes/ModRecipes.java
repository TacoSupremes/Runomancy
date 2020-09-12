package com.tacosupremes.runomancy.common.recipes;

import com.tacosupremes.runomancy.common.block.ModBlocks;
import com.tacosupremes.runomancy.common.item.ModItems;
import net.minecraft.block.Blocks;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.FurnaceRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModRecipes {
	
	
	public static List<RuneChargerRecipe> rcr = new ArrayList<RuneChargerRecipe>();
	public static Map<String, ItemStack[][]> shapeless = new HashMap<String, ItemStack[][]>();
	public static Map<String, ItemStack[][]> shaped = new HashMap<String, ItemStack[][]>();
	public static Map<String, ItemStack> furnace = new HashMap<String, ItemStack>();


	public static ItemStack getSmeltingResult(World w, ItemStack is)
	{
		Inventory i = new Inventory(3);

		i.setInventorySlotContents(0, is);

		FurnaceRecipe ir = w.getRecipeManager().getRecipe(IRecipeType.SMELTING, i, w).orElse(null);

		if(ir != null)
			return ir.getRecipeOutput();

		return ItemStack.EMPTY;
	}

	public static void postInit()
	{

		//	ItemStack w = new ItemStack(ModItems.runicWand,1, OreDictionary.WILDCARD_VALUE);


		rcr.add(new RuneChargerRecipe(new ItemStack(ModItems.runicIngot), new ItemStack(Items.IRON_INGOT, 1), 300));
		rcr.add(new RuneChargerRecipe(new ItemStack(ModBlocks.powerTorch), new ItemStack(Blocks.TORCH, 1), 0));

		/*
		//addShapelessRecipe(new ItemStack(ModBlocks.endRune), w, new ItemStack(ModItems.enderShard));
		//addShapelessRecipe(new ItemStack(ModBlocks.obsidianRune,2), w, new ItemStack(Blocks.OBSIDIAN));
		addShapelessRecipe(new ItemStack(ModBlocks.fireRune,2), w, new ItemStack(Items.LAVA_BUCKET));
		addShapelessRecipe(new ItemStack(ModBlocks.waterRune,2), w, new ItemStack(Items.WATER_BUCKET));
		addShapelessOreDictRecipe(new ItemStack(ModBlocks.earthRune,2), w, "treeSapling");
		addShapelessRecipe(new ItemStack(ModItems.modBook), new ItemStack(ModItems.enderShard), new ItemStack(Items.BOOK));
		addShapedRecipe(new ItemStack(ModItems.runicWand), "  E"," S ","S  ", 'E', new ItemStack(ModItems.enderShard), 'S', new ItemStack(Items.STICK));
		addShapelessRecipe(new ItemStack(ModItems.enderShard, 4), new ItemStack(Items.ENDER_PEARL));


		//	addShapedRecipe(new ItemStack(ModItems.runicPickaxe,1,ModItems.runicPickaxe.getMaxDamage()-1), "RRR"," S "," S ", 'R', new ItemStack(ModItems.runicIngot), 'S', new ItemStack(Items.STICK));
	//	addShapedRecipe(new ItemStack(ModItems.runicAxe,1,ModItems.runicAxe.getMaxDamage()-1), "RR ","RS "," S ", 'R', new ItemStack(ModItems.runicIngot), 'S', new ItemStack(Items.STICK));
	//	addShapedRecipe(new ItemStack(ModItems.runicShovel,1,ModItems.runicShovel.getMaxDamage()-1), " R "," S "," S ", 'R', new ItemStack(ModItems.runicIngot), 'S', new ItemStack(Items.STICK));
	//	addShapedRecipe(new ItemStack(ModItems.runicHoe,1,ModItems.runicHoe.getMaxDamage()-1), " RR"," S "," S ", 'R', new ItemStack(ModItems.runicIngot), 'S', new ItemStack(Items.STICK));
		
		addFurnaceRecipe(new ItemStack(ModBlocks.marker), new ItemStack(Blocks.TORCH),5);
		
		addFurnaceRecipe(new ItemStack(ModItems.soulGem,1,1), new ItemStack(ModBlocks.soulRune, 2, 0), 0);


	}



	

	
	private static void addOreDictRecipe(ItemStack output, Object... r) {	
		
		CraftingManager.getInstance().getRecipeList().add(new ShapedOreRecipe(output, r));
		
		List<ItemStack[]> l = new ArrayList<ItemStack[]>();
		
		int sCount = 0;
		for(int i = 0; i< r.length; i++){
			
			if(sCount == 3)
				break;
			
			if(r[i] instanceof String){
				sCount++;
				for(char c : ((String)r[i]).toCharArray()){
					
					if(c == ' '){
						l.add(null);
						continue;
					}
					for(int i2 = 0; i2< r.length; i2++){
						
						if(!(r[i2] instanceof Character))
							continue;
						
						if(((Character)r[i2]) == c){
							
							if(r[i2+1] instanceof ItemStack){
								l.add(new ItemStack[]{(ItemStack)r[i2+1]});
							}else{
								l.add(OreDictionary.getOres((String)r[i2+1]).toArray(new ItemStack[OreDictionary.getOres((String)r[i2+1]).size()]));
							}
							
							continue;
							
						}else if(i2 != r.length-1)
							continue;
						else{
							
							System.err.println("RIP RUN");
							
							return;
							
							}
						
					}
					
				}
				
			}
			
			
		}
		
		  ItemStack is2 = new ItemStack(output.getItem(),1,output.getItemDamage());
		
		shaped.put(is2.getUnlocalizedName(), l.toArray(new ItemStack[l.size()][9]));
		
	}

	private static void addShapelessOreDictRecipe(ItemStack output, Object... recipe) {
		
		
		ItemStack[][] ns = new ItemStack[recipe.length][1]; 
		
		int index = 0;
		
		List<String> ls = new ArrayList<String>();
		
		for(Object o : recipe){
			
			if(o instanceof ItemStack){
				
				ns[index][0] = (ItemStack)o;
				index++;
				continue;
			}else if(o instanceof String){
				
				ls.add((String)o);
				continue;
				
				
			}
			
		}
		
		if(ls.isEmpty()){
			
		System.err.println("USE NORMAL SHAPELESSRECIPE");
			return;
		}else{
			
			for(String s : ls){
				
				
				
				ns[index] = OreDictionary.getOres(s).toArray(new ItemStack[OreDictionary.getOres(s).size()]);
				index++;
				
			}
			
			
		}
		  ItemStack is2 = new ItemStack(output.getItem(),1,output.getItemDamage());
		  
		 shapeless.put(is2.getUnlocalizedName(), ns);
		CraftingManager.getInstance().getRecipeList().add(new ShapelessOreRecipe(output, recipe));
	}
	
	*/
	}

}
