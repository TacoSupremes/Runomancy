package com.tacosupremes.runomancy.common.item;

import com.tacosupremes.runomancy.common.Runomancy;
import com.tacosupremes.runomancy.common.lib.LibMisc;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class ItemModBook extends ItemMod{

	public ItemModBook() {
		super("modBook", 0);
		
	}



	@Override
	public ActionResult<ItemStack> onItemRightClick(World w, EntityPlayer player, EnumHand hand){
		
		if(w.isRemote){
			player.openGui(Runomancy.instance, LibMisc.GuiIDs.MODBOOK, w, (int)player.posX, (int)player.posY, (int)player.posZ);
		}
		
		
		return super.onItemRightClick(w, player, hand);
		
	}
	
	
	
	
	

}
