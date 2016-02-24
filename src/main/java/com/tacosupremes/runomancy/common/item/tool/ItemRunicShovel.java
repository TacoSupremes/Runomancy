package com.tacosupremes.runomancy.common.item.tool;

import java.util.List;

import com.tacosupremes.runomancy.common.Runomancy;
import com.tacosupremes.runomancy.common.item.ModItems;
import com.tacosupremes.runomancy.common.power.PowerHelper;
import com.tacosupremes.runomancy.gui.Categories;
import com.tacosupremes.runomancy.gui.IPageGiver;
import com.tacosupremes.runomancy.gui.ItemPage;
import com.tacosupremes.runomancy.gui.Page;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemRunicShovel extends ItemSpade implements IPageGiver {

	public ItemRunicShovel() {
		super(ModItems.runic);
		this.setUnlocalizedName("runicShovel");
		this.setCreativeTab(Runomancy.tab);
		this.setHasSubtypes(true);
		GameRegistry.registerItem(this, this.getUnlocalizedName().substring(5));
		ModItems.nitems.add(this);
		this.setNoRepair();
	}

	

	@Override
	public float getStrVsBlock(ItemStack stack, Block block) {
	
		return stack.getItemDamage() == stack.getMaxDamage()-1 ? super.getStrVsBlock(stack, block) / 12F :super.getStrVsBlock(stack, block);
	}

	

	@Override
	public boolean isItemTool(ItemStack stack) {
		
		return true;
	}


	@Override
	public float getDigSpeed(ItemStack stack, IBlockState state) {
		
		return this.getStrVsBlock(stack, state.getBlock());
	}



	@Override
	public ItemStack onItemRightClick(ItemStack is, World w, EntityPlayer player) {
	
		if(!is.hasTagCompound())
			is.setTagCompound(new NBTTagCompound());
		
		return super.onItemRightClick(is, w, player);
	}



	



	@Override
	public void getSubItems(Item item, CreativeTabs tab, List<ItemStack> l) {
		
		
		l.add(new ItemStack(item,1,1));
		l.add(new ItemStack(item,1,item.getMaxDamage()-1));
		
		
	}



	@Override
	public boolean onBlockDestroyed(ItemStack stack, World worldIn, Block blockIn, BlockPos pos,
			EntityLivingBase playerIn) {
		
			if(stack.getItemDamage() == stack.getMaxDamage()-1)
				return true;
		
		
		return super.onBlockDestroyed(stack, worldIn, blockIn, pos, playerIn);
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


	
	 public boolean getIsRepairable(ItemStack toRepair, ItemStack repair)
	    {
		 return repair.getItem() == ModItems.runicIngot;
	    }
	
	

}