package com.tacosupremes.runomancy.common.utils;

import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryLargeChest;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.BlockPos;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.ILockableContainer;
import net.minecraft.world.World;

public class InventoryHelper {
	
	public static void insertItem(ItemStack ism, IInventory ii){
		
		
		
		if(itemsLeft(ism, ii) != 0)	
			return;
		
		
		ItemStack is = ism.copy();
		
	//	int mx = ii.getInventoryStackLimit();
		
		for(int i = 0; i< ii.getSizeInventory();i++){
			
			ItemStack slot = ii.getStackInSlot(i);
			
			if(is == null || is.stackSize == 0)
				return;
			
			if(slot == null){
				ii.setInventorySlotContents(i, is);
				
				return;
			}
			
			if(slot.areItemsEqual(is, slot)){
				
				if(slot.stackSize +is.stackSize <= slot.getMaxStackSize()){
					ii.setInventorySlotContents(i, new ItemStack(slot.getItem(),slot.stackSize+is.stackSize,slot.getItemDamage()));
					break;
				}else{
					
					is.splitStack(slot.getMaxStackSize()-slot.stackSize);
					ii.setInventorySlotContents(i, new ItemStack(slot.getItem(),slot.getMaxStackSize(),slot.getItemDamage()));
					
				}
				
				
			}
		}
		
	}
	
public static int itemsLeft(ItemStack ism, IInventory iio){
		
		ItemStack is = ism.copy();
		
		NewInventory ii = new NewInventory(iio);
		
		
		for(int i = 0; i< ii.getSizeInventory();i++){
			
			ItemStack slot = ii.getStackInSlot(i);
			
			if(is == null || is.stackSize == 0)
				return 0;
			
			if(slot == null){
				ii.setInventorySlotContents(i, is);
				return 0;
			}
			
			if(slot.areItemsEqual(is, slot)){
				
				if(slot.stackSize +is.stackSize <= slot.getMaxStackSize()){
					ii.setInventorySlotContents(i, new ItemStack(slot.getItem(),slot.stackSize + is.stackSize,slot.getItemDamage()));
					return 0;
				}else{
					
					is.splitStack(slot.getMaxStackSize()-slot.stackSize);
					ii.setInventorySlotContents(i, new ItemStack(slot.getItem(),slot.getMaxStackSize(),slot.getItemDamage()));
					
				}
				
				
			}
		}
		
		if(is == null || is.stackSize == 0)
			return 0;
		
		return is.stackSize;
	}

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
			amount += ii.getStackInSlot(i).stackSize;
			
		
	}
	
	return amount;
}

}

	