package com.tacosupremes.runomancy.common.utils;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;


	public class NewInventory implements IInventory{

		IInventory o;
		public NewInventory(IInventory i){
			this.o = i;
			this.init();
		}
		
		ItemStack[] stuff; 
		
		
		
		public void init(){
			
			stuff = new ItemStack[o.getSizeInventory()];		
			
			for(int i =0; i< o.getSizeInventory();i++){
				
				stuff[i] = o.getStackInSlot(i);
			}
		}
		

		@Override
		public boolean hasCustomName() {
			
			return false;
		}

	
		@Override
		public int getSizeInventory() {
			
			return o.getSizeInventory();
		}

		@Override
		public ItemStack getStackInSlot(int index) {
			
			return stuff[index];
		}

		@Override
		public ItemStack decrStackSize(int index, int count) {
			
			return stuff[index].splitStack(count);
		}

		
		@Override
		public void setInventorySlotContents(int index, ItemStack stack) {
			
			stuff[index] = stack;
			
		}

		@Override
		public int getInventoryStackLimit() {
			
			return 64;
		}

		@Override
		public void markDirty() {
			
			
		}

	

		@Override
		public void openInventory(EntityPlayer player) {
			
			
		}

		@Override
		public void closeInventory(EntityPlayer player) {
			
			
		}

		@Override
		public boolean isItemValidForSlot(int index, ItemStack stack) {
			
			return true;
		}

		@Override
		public int getField(int id) {
			
			return 0;
		}

		@Override
		public void setField(int id, int value) {
			
			
		}

		@Override
		public int getFieldCount() {
			
			return 0;
		}

		@Override
		public void clear() {
			
			
		}
		@Override
		public String getName() {
		
			return null;
		}
		@Override
		public ItemStack removeStackFromSlot(int index) {
			
			ItemStack is = stuff[index].copy();
			
			stuff[index] = null;
			
			
			return is;
		}


		@Override
		public ITextComponent getDisplayName() {
			
			return null;
			
		}


		@Override
		public boolean isEmpty() {
			
			for(ItemStack is : stuff){
				
				if(is != null)
					return false;
			}
			
			return true;
		}


		@Override
		public boolean isUsableByPlayer(EntityPlayer player) {
			// TODO Auto-generated method stub
			return false;
		}
		
	}




