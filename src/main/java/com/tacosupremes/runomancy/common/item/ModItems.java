package com.tacosupremes.runomancy.common.item;

import com.tacosupremes.runomancy.common.Runomancy;
import com.tacosupremes.runomancy.common.block.BlockMod;
import com.tacosupremes.runomancy.common.block.ModBlocks;
import com.tacosupremes.runomancy.common.lib.LibMisc;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.WallOrFloorItem;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class ModItems {

	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, LibMisc.MODID);
	
	public static List<ItemMod> items = new ArrayList<ItemMod>();
	
	public static List<Item> nitems = new ArrayList<Item>();

	public static final RegistryObject<Item> ENDER_SHARD = ITEMS.register("ender_shard", () -> new Item(new Item.Properties().group(Runomancy.TAB)));

	public static final RegistryObject<Item> RUNIC_INGOT = ITEMS.register("runic_ingot", () -> new Item(new Item.Properties().group(Runomancy.TAB)));

	public static final RegistryObject<Item> RUNIC_WAND = regItem(ItemRunicWand::new);


	public static final RegistryObject<Item> END_RUNE_ITEM = makeBlockItem(ModBlocks.END_RUNE);

	public static final RegistryObject<Item> OBSIDIAN_RUNE_ITEM = makeBlockItem(ModBlocks.OBSIDIAN_RUNE);

	public static final RegistryObject<Item> EARTH_RUNE_ITEM = makeBlockItem(ModBlocks.EARTH_RUNE);

	public static final RegistryObject<Item> FIRE_RUNE_ITEM = makeBlockItem(ModBlocks.FIRE_RUNE);

	public static final RegistryObject<Item> WATTER_RUNE_ITEM = makeBlockItem(ModBlocks.WATER_RUNE);

	public static final RegistryObject<Item> SOUL_RUNE_ITEM = makeBlockItem(ModBlocks.SOUL_RUNE);

	public static final RegistryObject<Item> NODE_ITEM = makeBlockItem(ModBlocks.NODE);

	public static final RegistryObject<Item> BATTERY_ITEM = makeBlockItem(ModBlocks.BATTERY);

	public static Item runicWand;

	public static Item runicPickaxe;
	
	//public static ToolMaterial runic = ToolMaterial.DIAMOND;/*TODO: FIGURE OUT HOW TO FIX Material*/ //EnumHelper.addToolMaterial("runic", 3, 750, 9.1F, 2.5F, 32);

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


	/*
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

	*/

	public static RegistryObject<Item> regItem(final Supplier<? extends Item> sup)
	{
		return ITEMS.register(((IModItem)sup.get()).getItemRegistryName(), sup);
	}

	public static RegistryObject<Item> makeBlockItem(RegistryObject<BlockMod> b)
	{
		return ITEMS.register(b.getId().getPath(), () -> new BlockItem(b.get(), new Item.Properties().group(Runomancy.TAB)));
	}

}
