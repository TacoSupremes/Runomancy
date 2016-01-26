package com.tacosupremes.runomancy.common.block;

import java.util.ArrayList;
import java.util.List;

import com.tacosupremes.runomancy.client.render.RenderPowerStorage;
import com.tacosupremes.runomancy.common.block.rune.BlockEarthRune;
import com.tacosupremes.runomancy.common.block.rune.BlockEndRune;
import com.tacosupremes.runomancy.common.block.rune.BlockFireRune;
import com.tacosupremes.runomancy.common.block.rune.BlockObsidianRune;
import com.tacosupremes.runomancy.common.item.ItemRunicWand;
import com.tacosupremes.runomancy.common.item.ModItems;
import com.tacosupremes.runomancy.common.power.block.BlockPowerStorage;
import com.tacosupremes.runomancy.common.power.block.BlockPowerTorch;
import com.tacosupremes.runomancy.common.power.block.tile.TilePowerStorage;
import com.tacosupremes.runomancy.common.runelogic.RuneFormations;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class ModBlocks {
	
	public static List<Block> blocks = new ArrayList<Block>();
	
	public static List<Block> runes = new ArrayList<Block>();
	
	public static Block endRune;
	
	public static Block obsidianRune;
	
	public static Block powerTorch;
	
	public static Block powerStorage;

	public static Block fireRune;
	
	public static Block earthRune;
	
	public static Block waterRune;
	
	public static Block airRune;

	
	
	public static final int obsidianCount = 12;
	public static final int endRuneCount = RuneFormations.effects.size();
	public static final int fireCount = 6;
	public static final int earthCount = 7;
	
	public static void preInit(){
	
		endRune = new BlockEndRune();
		
		obsidianRune = new BlockObsidianRune();
		
		fireRune = new BlockFireRune();
		
		earthRune = new BlockEarthRune();
		
		powerTorch = new BlockPowerTorch();
		
		powerStorage = new BlockPowerStorage();
	}
	
	public static void registerRenders(){
		
		for(Block i : blocks){
			ModItems.registerItemRender(Item.getItemFromBlock(i), 0);
		}
		
		ClientRegistry.bindTileEntitySpecialRenderer(TilePowerStorage.class, new RenderPowerStorage());
		
	}
	
	

}
