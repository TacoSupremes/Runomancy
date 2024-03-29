package com.tacosupremes.runomancy.common;


import com.tacosupremes.runomancy.common.block.ModBlocks;
import com.tacosupremes.runomancy.common.block.rune.GrassColorHandler;
import com.tacosupremes.runomancy.common.block.rune.WaterColorHandler;
import com.tacosupremes.runomancy.common.item.ModItems;
import com.tacosupremes.runomancy.common.lib.LibMisc;
import com.tacosupremes.runomancy.common.recipes.ModRecipes;
import com.tacosupremes.runomancy.common.runelogic.*;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import java.util.Random;

@Mod(LibMisc.MODID)
public class Runomancy
{
	public Runomancy()
	{
		// Register the setup method for modloading
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);

		ModBlocks.BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());

		ModBlocks.TILES.register(FMLJavaModLoadingContext.get().getModEventBus());

		ModItems.ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());

		//ModEnchantments.ENCHANTS.register(FMLJavaModLoadingContext.get().getModEventBus());

		//ModRecipes.RECIPES.register(FMLJavaModLoadingContext.get().getModEventBus());
		// Register the enqueueIMC method for modloading
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
		// Register the processIMC method for modloading
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);
		// Register the doClientStuff method for modloading
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);

		MinecraftForge.EVENT_BUS.register(this);
		// Register ourselves for server and other game events we are interested in
		//MinecraftForge.EVENT_BUS.register(OfflinePlayerUtils.class);
		//MinecraftForge.EVENT_BUS.register(this);
		//MinecraftForge.EVENT_BUS.register(WS2Events.class);
		//MinecraftForge.EVENT_BUS.register(new LootHandler());
	}


	private void setup(final FMLCommonSetupEvent event)
	{
		// some preinit code
		RuneFormations.addEffects();
		ModRecipes.addChargerRecipes();
		//LOGGER.info("HELLO FROM PREINIT");
		//LOGGER.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());
	}

	private void doClientStuff(final FMLClientSetupEvent event)
	{
		WaterColorHandler.registerBlockColors();
		GrassColorHandler.registerBlockColors();
		RenderTypeLookup.setRenderLayer(ModBlocks.NODE.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.BATTERY.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.BATTERY_INSIDE.get(), RenderType.getCutout());

		ClientRegistry.bindTileEntityRenderer(ModBlocks.TILE_BATTERY.get(), TileBatteryRenderer::new);
		ClientRegistry.bindTileEntityRenderer(ModBlocks.TILE_END_RUNE.get(), TileEndRuneRenderer::new);
	}

	private void enqueueIMC(final InterModEnqueueEvent event)
	{
		// some example code to dispatch IMC to another mod
	}

	private void processIMC(final InterModProcessEvent event)
	{
		// some example code to receive and process InterModComms from other mods
	}
   // @SidedProxy(clientSide = LibMisc.CLIENTPROXY, serverSide = LibMisc.COMMONPROXY)
  //  public static CommonProxy proxy;
    
   // public static CreativeTabs tab;

    public static Random rand = new Random();

    public static int randInt(int max, int exclude)
	{
    	int i = rand.nextInt(max);
    	
    	while(i == exclude)
    		i = rand.nextInt(max);

    	return i;
    }

	@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
	public static class RegistryEvents
	{
		@SubscribeEvent
		public static void onBlocksRegistry(final RegistryEvent.Register<Block> blockRegistryEvent)
		{
			// register a new block here
			// LOGGER.info("HELLO from Register Block");
			// blockRegistryEvent.getRegistry().register(ModBlocks.test);
		}
	}

	public static final ItemGroup TAB = new ItemGroup("runomancy.tab")
	{
		@Override
		public ItemStack createIcon()
		{
			return new ItemStack(ModBlocks.END_RUNE.get());
		}
	};
}
