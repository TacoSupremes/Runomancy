package com.tacosupremes.runomancy.common;


import java.util.Random;

import com.tacosupremes.runomancy.common.block.ModBlocks;
import com.tacosupremes.runomancy.common.item.ModItems;
import com.tacosupremes.runomancy.common.lib.LibMisc;
import com.tacosupremes.runomancy.common.recipes.ModRecipes;
import com.tacosupremes.runomancy.common.runelogic.RuneEffectFurnace;
import com.tacosupremes.runomancy.common.runelogic.RuneEffectFurnaceGen;
import com.tacosupremes.runomancy.common.runelogic.RuneEffectMiner;
import com.tacosupremes.runomancy.common.runelogic.RuneEffectPlantGrower;
import com.tacosupremes.runomancy.common.runelogic.RuneEffectRepair;
import com.tacosupremes.runomancy.common.runelogic.RuneEffectSolarGen;
import com.tacosupremes.runomancy.common.runelogic.RuneEffectWell;
import com.tacosupremes.runomancy.common.runelogic.RuneFormations;
import com.tacosupremes.runomancy.gui.GuiHandler;
import com.tacosupremes.runomancy.gui.Pages;
import com.tacosupremes.runomancy.proxy.CommonProxy;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

@Mod(modid = LibMisc.MODID, version = LibMisc.VERSION)
public class Runomancy {

   
    
    @SidedProxy(clientSide = LibMisc.CLIENTPROXY, serverSide = LibMisc.COMMONPROXY)
    public static CommonProxy proxy;
    
    public static CreativeTabs tab;
    
    @Instance(LibMisc.MODID)
    public static Runomancy instance;
    
    
    public static Random rand;
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
    	tab = new RTab();	
  
    	rand = new Random();
    	
    	ModItems.preInit();
    	RuneFormations.addEffect(new RuneEffectRepair());
    	RuneFormations.addEffect(new RuneEffectSolarGen());
    	RuneFormations.addEffect(new RuneEffectFurnace());
    	RuneFormations.addEffect(new RuneEffectPlantGrower());
    	RuneFormations.addEffect(new RuneEffectFurnaceGen());
    	RuneFormations.addEffect(new RuneEffectMiner());
    	RuneFormations.addEffect(new RuneEffectWell());
    	ModBlocks.preInit();
		
    }
    
    
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
    	
    	NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GuiHandler());
		proxy.registerRenders();
    }
    
    @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
    	ModRecipes.postInit();
    	LibMisc.Ores.postInit();
    	Pages.postInit();
    	RuneFormations.makePages();
    	
   
		
    }
    
    public static int randInt(int max, int exclude){
    	
    	int i = rand.nextInt(max);
    	
    	while(i == exclude)
    		i = rand.nextInt(max);
    	
    		
    	
    	return i;
    }
    
    public class RTab extends CreativeTabs {

		public RTab() {
			super(CreativeTabs.getNextID(), LibMisc.MODID);
			
		}

		@Override
		public Item getTabIconItem() {
		
			return Item.getItemFromBlock(ModBlocks.endRune);
		}
    	
    }
    
}
