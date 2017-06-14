package com.tacosupremes.runomancy.common.utils;

import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryLargeChest;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ILockableContainer;
import net.minecraft.world.World;

public class InventoryHelper {


public static IInventory getInventory(World w, BlockPos bp){
	
	if(w.getTileEntity(bp) == null)
		return null;
	
	if(!(w.getTileEntity(bp) instanceof IInventory))
		return null;
	
	if(w.getTileEntity(bp) instanceof TileEntityChest){
		Block chestBlock = w.getBlockState(bp).getBlock();
		ILockableContainer tc = ((BlockChest)chestBlock).getLockableContainer(w, bp);
		
	
		if(w.getBlockState(bp.add(-1, 0, 0)).getBlock() == chestBlock)
			return new InventoryLargeChest("Large chest", ((BlockChest)w.getBlockState(bp.add(-1, 0, 0)).getBlock()).getLockableContainer(w, bp.add(-1, 0, 0)),tc);
		if(w.getBlockState(bp.add(-1, 0, 0)).getBlock() == chestBlock)
			return new InventoryLargeChest("Large chest", ((BlockChest)w.getBlockState(bp.add(1, 0, 0)).getBlock()).getLockableContainer(w, bp.add(1, 0, 0)),tc);
		if(w.getBlockState(bp.add(0, 0, -1)).getBlock() == chestBlock)
			return new InventoryLargeChest("Large chest", ((BlockChest)w.getBlockState(bp.add(0, 0, -1)).getBlock()).getLockableContainer(w, bp.add(0, 0, -1)),tc);
		if(w.getBlockState(bp.add(0, 0, 1)).getBlock() == chestBlock)
			return new InventoryLargeChest("Large chest", ((BlockChest)w.getBlockState(bp.add(0, 0, 1)).getBlock()).getLockableContainer(w, bp.add(0, 0, 1)),tc);
		}

	
	return (IInventory)w.getTileEntity(bp);
}

public static int countofItemStack(IInventory ii, ItemStack is){
	
	int amount = 0;
	
	for(int i =0; i< ii.getSizeInventory(); i++){
		
		if(ItemStack.areItemsEqual(is, ii.getStackInSlot(i)))		
			amount += ii.getStackInSlot(i).getCount();
			
		
	}
	
	return amount;
}

public static ItemStack insertItem(ItemStack is3, IInventory ii, boolean doit){
	
	ItemStack is = is3.copy();
	
	
	
	for(int i = 0; i<ii.getSizeInventory(); i++){
		
		if(ii.getStackInSlot(i).isEmpty() || ii.getStackInSlot(i) == null){
			if(doit){
			ii.setInventorySlotContents(i, is);
			ii.markDirty();
			}
			return null;
		}
		
		if(ItemStack.areItemsEqual(is, ii.getStackInSlot(i))){
			
			if(ii.getStackInSlot(i).getCount() + is.getCount() <= is.getMaxStackSize()){
				
				ItemStack is2 = is.copy();
				is2.setCount(is.getCount() + ii.getStackInSlot(i).getCount());
				if(doit){
				ii.setInventorySlotContents(i, is2);
				ii.markDirty();
				}
				return null;
			}else{
				ItemStack is2 = is.copy();
				is.setCount((ii.getStackInSlot(i).getCount() + is.getCount() - is.getMaxStackSize()));
				is2.setCount(is.getMaxStackSize());
				if(doit){
				ii.setInventorySlotContents(i, is2);
				ii.markDirty();
				}
				continue;
				
			}
			
		}
		
		
	}
	
	
	return is;
}

}

	