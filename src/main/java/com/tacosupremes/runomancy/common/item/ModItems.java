package com.tacosupremes.runomancy.common.item;

import java.util.ArrayList;
import java.util.List;

import com.tacosupremes.runomancy.common.Runomancy;
import com.tacosupremes.runomancy.common.item.tool.ItemRunicAxe;
import com.tacosupremes.runomancy.common.item.tool.ItemRunicHoe;
import com.tacosupremes.runomancy.common.item.tool.ItemRunicPickaxe;
import com.tacosupremes.runomancy.common.item.tool.ItemRunicShovel;
import com.tacosupremes.runomancy.common.lib.LibMisc;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.EnumHelper;

public class ModItems {
	
	public static List<ItemMod> items = new ArrayList<ItemMod>();
	
	public static List<Item> nitems = new ArrayList<Item>();
	
	
	
	public static Item runicWand;

	public static Item runicPickaxe;
	
	public static ToolMaterial runic = EnumHelper.addToolMaterial("runic", 3, 75, 7.1F, 2, 32);

	public static Item levitateRune;
	
	public static Item runicIngot;

	public static Item runicAxe;

	public static Item runicShovel;

	public static Item runicHoe;
	
	public static Item modBook;
	
	public static Item soulGem;
	
	public static Item hungerTablet;
	
	public static Item enderShard;
	
	public static void preInit(){
		
		runicWand = new ItemRunicWand();
		
		runicPickaxe = new ItemRunicPickaxe();
		
		runicAxe = new ItemRunicAxe();
		
		runicShovel = new ItemRunicShovel();
		
		runicHoe = new ItemRunicHoe();
		
		hungerTablet = new ItemHungerTablet();
	
		levitateRune = new ItemLevitateRune();
		
		enderShard = new ItemEnderShard();
		
		soulGem = new ItemSoulGem();
		
		runicIngot = new ItemRunicIngot();
		
		modBook = new ItemModBook();
	}
	
	public static void registerRenders(){
		
		
		for(ItemMod i : items){
			
			if(i.meta !=0){
				
				ResourceLocation[] s = new ResourceLocation[i.meta+1];
				
				for(int i2 = 0; i2<i.meta+1;i2++){
					
					s[i2] = new ResourceLocation("runomancy:" + i.getUnlocalizedName().substring(5) +(i2 == 0 ? "" : i2));
					
				}
				
				
				ModelBakery.registerItemVariants(i, s);
				
				
				for(int i2 = 0; i2<=i.meta;i2++){
					ModItems.registerItemRender(i, i2);
					
				}
				
				
			}
			
			if(i.meta == 0)
				ModItems.registerItemRender(i, 0);
		}
		
		
		for(Item i : nitems){
			registerItemRender(i, 0);
		}
		
		
		
	}
	
	public static void registerItemRender(Item i, int meta){
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(i, meta, new ModelResourceLocation(LibMisc.MODID+":"+i.getUnlocalizedName().substring(5)+ (meta == 0 ? "" : String.valueOf(meta)), "inventory"));
	}
	
	

}
