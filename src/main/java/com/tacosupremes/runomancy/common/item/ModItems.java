package com.tacosupremes.runomancy.common.item;

import java.util.ArrayList;
import java.util.List;

import com.tacosupremes.runomancy.common.item.tool.ItemRunicAxe;
import com.tacosupremes.runomancy.common.item.tool.ItemRunicHoe;
import com.tacosupremes.runomancy.common.item.tool.ItemRunicPickaxe;
import com.tacosupremes.runomancy.common.item.tool.ItemRunicShovel;
import com.tacosupremes.runomancy.common.lib.LibMisc;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.EnumHelper;

public class ModItems {
	
	public static List<ItemMod> items = new ArrayList<ItemMod>();
	
	public static List<Item> nitems = new ArrayList<Item>();
	
	public static Item runicWand;

	public static Item runicPickaxe;
	
	public static ToolMaterial runic = ToolMaterial.DIAMOND;/*TODO: FIGURE OUT HOW TO FIX Material*/ //EnumHelper.addToolMaterial("runic", 3, 750, 9.1F, 2.5F, 32);

	public static Item levitateRune;
	
	public static Item runicIngot;

	public static Item runicAxe;

	public static Item runicShovel;

	public static Item runicHoe;
	
	public static Item runicSword;
	
	public static Item modBook;
	
	public static Item soulGem;
	
	public static Item hungerTablet;
	
	public static Item enderShard;
	
	public static Item fluidGem;
	
	public static Item mobRepeller;
	
	public static Item builderScroll;
	
	public static void preInit(){
		
		runicWand = new ItemRunicWand();
		
		runicPickaxe = new ItemRunicPickaxe();
		
		runicAxe = new ItemRunicAxe();
		
		runicShovel = new ItemRunicShovel();
		
		runicHoe = new ItemRunicHoe();
		
		hungerTablet = new ItemHungerTablet();
	
		levitateRune = new ItemLevitateRune();
		
		mobRepeller = new ItemRunicShield();
		
		enderShard = new ItemEnderShard();
		
		soulGem = new ItemSoulGem();
		
		runicIngot = new ItemRunicIngot();
		
		modBook = new ItemModBook();
		
		builderScroll = new ItemBuilderScroll();
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
		
		if(i == null)
			return;
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(i, meta, new ModelResourceLocation(LibMisc.MODID+":"+i.getUnlocalizedName().substring(5)+ (meta == 0 ? "" : String.valueOf(meta)), "inventory"));
	}
	
	

}
