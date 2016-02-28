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
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemRunicAxe extends ItemAxe implements IPageGiver{

	public ItemRunicAxe() {
		super(ModItems.runic);
		this.setUnlocalizedName("runicAxe");
		this.setCreativeTab(Runomancy.tab);
		this.setHasSubtypes(true);
		GameRegistry.registerItem(this, this.getUnlocalizedName().substring(5));
		ModItems.nitems.add(this);
		
	
	}

	

	@Override
	public float getStrVsBlock(ItemStack stack, Block block) {
	
		return stack.getItemDamage() == stack.getMaxDamage()-1 ? super.getStrVsBlock(stack, block) / 12F :super.getStrVsBlock(stack, block);
	}

	
	


	@Override
	public float getDigSpeed(ItemStack stack, IBlockState state) {
		
		return this.getStrVsBlock(stack, state.getBlock());
	}


	 public boolean getIsRepairable(ItemStack toRepair, ItemStack repair)
	    {
		 return repair.getItem() == ModItems.runicIngot;
	    }
	



	@Override
	public boolean isItemTool(ItemStack stack) {
		
		return true;
	}



	@Override
	public void getSubItems(Item item, CreativeTabs tab, List<ItemStack> l) {
		
		
		l.add(new ItemStack(item,1,0));
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
		
		return new ItemPage(new ItemStack(this, 1, this.getMaxDamage() - 1 ));
		
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
	
	
	@Override
	public ItemStack onItemRightClick(ItemStack is, World w, EntityPlayer player) {
		 
		 
		 if(!is.hasTagCompound()){
			 is.setTagCompound(new NBTTagCompound());
			 is.getTagCompound().setBoolean("ACTIVE", false);
		 }
		 
		 
		 if(player.isSneaking()){
			 
			 if(is.getTagCompound().getBoolean("ACTIVE"))
				 is.getTagCompound().setBoolean("ACTIVE", false);
			 else
				 is.getTagCompound().setBoolean("ACTIVE", true);
			 
			 player.swingItem();
			 
		 }
			 
			 
		
		return super.onItemRightClick(is, w, player);
		
	}
	 
	 
	 
	 @Override
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
		
		 if(!stack.hasTagCompound())
				return;
		 
		
			if(stack.getTagCompound().getBoolean("ACTIVE"))
				tooltip.add(EnumChatFormatting.GREEN + StatCollector.translateToLocal("runomancy.runic") + " " + StatCollector.translateToLocal("runomancy.power") + " " + StatCollector.translateToLocal("runomancy.active"));
			else
				tooltip.add(EnumChatFormatting.RED + StatCollector.translateToLocal("runomancy.runic") + " " + StatCollector.translateToLocal("runomancy.power") + " " + StatCollector.translateToLocal("runomancy.inactive"));
			
	 }	



	@Override
		public boolean hasEffect(ItemStack stack) {
	
		 return stack.hasTagCompound() ? stack.isItemEnchanted() ? true : stack.getItemDamage() == stack.getMaxDamage()-1 ? false : stack.getTagCompound().getBoolean("ACTIVE") : false;
		}

	
	
	

}
