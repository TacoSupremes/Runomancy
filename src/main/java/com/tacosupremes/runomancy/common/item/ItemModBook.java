package com.tacosupremes.runomancy.common.item;

import com.tacosupremes.runomancy.common.Runomancy;
import com.tacosupremes.runomancy.common.lib.LibMisc;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemModBook extends ItemMod{

	public ItemModBook() {
		super("modBook", 0);
		
	}

	@Override
	public ItemStack onItemRightClick(ItemStack is, World w, EntityPlayer player) {
		
		if(w.isRemote){
			player.openGui(Runomancy.instance, LibMisc.GuiIDs.MODBOOK, w, (int)player.posX, (int)player.posY, (int)player.posZ);
		}
		
		return super.onItemRightClick(is, w, player);
	}
	
	
	

}
