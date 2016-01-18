package com.tacosupremes.runomancy.common.power.item;

import com.tacosupremes.runomancy.common.item.ItemMod;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class ItemVacuumBucket extends ItemMod {

	public ItemVacuumBucket() {
		super("vacuumBucket",0);
		
	}

	@Override
	public ItemStack onItemRightClick(ItemStack is, World w, EntityPlayer player) {
		
		
		 MovingObjectPosition pos = this.getMovingObjectPositionFromPlayer(w, player, false);

		if(pos == null)
			return is;
		
		
		BlockPos bp = pos.getBlockPos();
		if(w.isAirBlock(bp))
			return is;
		
		w.setBlockState(bp, Blocks.cobblestone.getDefaultState());
		
		return super.onItemRightClick(is, w, player);
	}





}
