package com.tacosupremes.runomancy.common.block;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import com.tacosupremes.runomancy.common.block.rune.BlockEarthRune;
import com.tacosupremes.runomancy.common.block.rune.BlockEndRune;
import com.tacosupremes.runomancy.common.block.rune.BlockFireRune;
import com.tacosupremes.runomancy.common.block.rune.BlockObsidianRune;
import com.tacosupremes.runomancy.common.block.rune.BlockSoulRune;
import com.tacosupremes.runomancy.common.block.rune.BlockWaterRune;
import com.tacosupremes.runomancy.common.block.rune.tile.TileEndRune;
import com.tacosupremes.runomancy.common.block.tile.TileNode;
import com.tacosupremes.runomancy.common.lib.LibMisc;
import com.tacosupremes.runomancy.common.power.block.BlockPowerStorage;
import com.tacosupremes.runomancy.common.power.block.tile.TilePowerStorage;
import com.tacosupremes.runomancy.common.runelogic.RuneFormations;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModBlocks {

	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, LibMisc.MODID);

	public static final DeferredRegister<TileEntityType<?>> TILES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, LibMisc.MODID);

	// blocks

	public static final RegistryObject<BlockMod> END_RUNE = regBlock(BlockEndRune::new);

	public static final RegistryObject<TileEntityType<?>> TILE_END_RUNE = regTile(TileEndRune::new, ModBlocks.END_RUNE);

	public static final RegistryObject<BlockMod> OBSIDIAN_RUNE = regBlock(BlockObsidianRune::new);

	public static final RegistryObject<BlockMod> FIRE_RUNE = regBlock(BlockFireRune::new);

	public static final RegistryObject<BlockMod> EARTH_RUNE = regBlock(BlockEarthRune::new);

	public static final RegistryObject<BlockMod> WATER_RUNE = regBlock(BlockWaterRune::new);

	public static final RegistryObject<BlockMod> SOUL_RUNE = regBlock(BlockSoulRune::new);

	public static final RegistryObject<BlockMod> NODE = regBlock(BlockNode::new);

	public static final RegistryObject<TileEntityType<?>> TILE_NODE = regTile(TileNode::new, ModBlocks.NODE);

	public static final RegistryObject<BlockMod> BATTERY_INSIDE = regBlock(BlockBatteryPortal::new);

	public static final RegistryObject<BlockMod> BATTERY = regBlock(BlockPowerStorage::new);

	public static final RegistryObject<TileEntityType<TilePowerStorage>> TILE_BATTERY = TILES.register(ModBlocks.BATTERY.getId().getPath(), () -> TileEntityType.Builder.create(TilePowerStorage::new, ModBlocks.BATTERY.get()).build(null));

	//public static List<Block> blocks = new ArrayList<Block>();
	
	public static List<Block> runes = new ArrayList<Block>();

	public static Block powerTorch;
	
	public static Block powerStorage;

	public static Block marker;



	public static final int obsidianCount = 12;
	public static final int endRuneCount = RuneFormations.effects.size();
	public static final int fireCount = 6;
	public static final int earthCount = 7;

	public static final int waterCount = 5;

	public static final int soulCount = 4;

	/*
	public static void preInit(){

		endRune = new BlockEndRune();

		obsidianRune = new BlockObsidianRune();

		fireRune = new BlockFireRune();

		earthRune = new BlockEarthRune();

		waterRune = new BlockWaterRune();

		soulRune = new BlockSoulRune();

		powerTorch = new BlockPowerTorch();

		powerStorage = new BlockPowerStorage();

		marker = new BlockMarker();
	}
*/

	public static RegistryObject<TileEntityType<?>> regTile(final Supplier<? extends TileEntity> sup, RegistryObject<BlockMod> bm)
	{
		return TILES.register(bm.getId().getPath(), () -> TileEntityType.Builder.create(sup, bm.get()).build(null));
	}

	public static RegistryObject<BlockMod> regBlock(final Supplier<? extends BlockMod> sup)
	{
		return  BLOCKS.register(sup.get().getName(), sup);
	}
	
	

}
