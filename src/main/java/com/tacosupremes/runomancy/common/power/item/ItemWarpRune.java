package com.tacosupremes.runomancy.common.power.item;

import java.util.List;

import com.tacosupremes.runomancy.common.item.ItemMod;
import com.tacosupremes.runomancy.common.lib.LibMisc;
import com.tacosupremes.runomancy.common.power.PowerHelper;
import com.tacosupremes.runomancy.gui.Categories;
import com.tacosupremes.runomancy.gui.IPageGiver;
import com.tacosupremes.runomancy.gui.ItemPage;
import com.tacosupremes.runomancy.gui.Page;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class ItemWarpRune extends ItemMod implements IPageGiver{

	public ItemWarpRune() {
		super("warpRune");
		
	}

	@Override
	public boolean onItemUse(ItemStack is, EntityPlayer player, World w, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ) {
		
		
		if(!player.isSneaking())
			return false;
		
		
		if(!is.hasTagCompound())
			is.setTagCompound(new NBTTagCompound());
		
		is.getTagCompound().setInteger("X",pos.getX());
		is.getTagCompound().setInteger("Y",pos.getY());
		is.getTagCompound().setInteger("Z",pos.getZ());
		
		
		
		
		
		return true;
		
	}

	@Override
	public ItemStack onItemRightClick(ItemStack is, World w, EntityPlayer player) {
		
		if(!is.hasTagCompound()){

			is.getTagCompound().setInteger("X",0);
			is.getTagCompound().setInteger("Y",0);
			is.getTagCompound().setInteger("Z",0);
			
		}else{
			if(PowerHelper.getBattery(player.inventory, false) == null)
				return is;
			
			ItemStack bat = PowerHelper.getBattery(player.inventory, false);
			
			if(((IRunicBattery)bat.getItem()).getPower(bat) > (int)(player.getDistance(is.getTagCompound().getInteger("X"), is.getTagCompound().getInteger("Y"), is.getTagCompound().getInteger("Z"))*20)){
			player.setPositionAndUpdate(is.getTagCompound().getInteger("X")+0.5D, is.getTagCompound().getInteger("Y")+0.1D, is.getTagCompound().getInteger("Z")+0.5D);
			((IRunicBattery)bat.getItem()).removePower(bat, (int)(player.getDistance(is.getTagCompound().getInteger("X"), is.getTagCompound().getInteger("Y"), is.getTagCompound().getInteger("Z"))*10), true);
			}
			
		}
		
		return is;
		
	}
	
	
	

	@Override
	public void addInformation(ItemStack is, EntityPlayer player, List<String> l, boolean advanced) {
		
		if(!is.hasTagCompound())
			return;
		
		
		int x = is.getTagCompound().getInteger("X");
		int y = is.getTagCompound().getInteger("Y");
		int z = is.getTagCompound().getInteger("Z");
		
		l.add(StatCollector.translateToLocal(LibMisc.MODID+"."+"bound"+":")+x+","+y+","+z);
		
	}

	@Override
	public EnumAction getItemUseAction(ItemStack stack) {
		
		return EnumAction.BLOCK;
		
	}

	@Override
	public Page getPage() {
		
		return new ItemPage(new ItemStack(this));
		
	}

	@Override
	public Categories getCategories() {
		
		return Categories.RunicItems;
		
	}

	@Override
	public boolean hasNormalRecipe() {
		
		return true;
		
	}

	@Override
	public Page getSubPages() {
		
		return null;
		
	}
	
	
	
	

}
