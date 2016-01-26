package com.tacosupremes.runomancy.proxy;

import com.tacosupremes.runomancy.common.block.ModBlocks;
import com.tacosupremes.runomancy.common.item.ModItems;

import net.minecraft.client.Minecraft;

public class ClientProxy extends CommonProxy {

	@Override
	public void registerRenders() {
		ModItems.registerRenders();
		ModBlocks.registerRenders();
	}

	@Override
	public boolean isShiftDown() {
		
		return Minecraft.getMinecraft().currentScreen != null ? Minecraft.getMinecraft().currentScreen.isShiftKeyDown() : false;
		
	}

	
	
}
