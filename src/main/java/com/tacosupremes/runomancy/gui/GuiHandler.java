package com.tacosupremes.runomancy.gui;

import com.tacosupremes.runomancy.common.lib.LibMisc;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {

	@Override
	public Object getServerGuiElement(int id, EntityPlayer player, World w, int x, int y, int z) {
		
		return null;
	}

	@Override
	public Object getClientGuiElement(int id, EntityPlayer player, World w, int x, int y, int z) {
		
		
		if(id == LibMisc.GuiIDs.MODBOOK)
			return new GuiModBook();
		
		
		return null;
	}

}
