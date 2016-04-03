package com.tacosupremes.runomancy.common.item.tool;

import java.util.List;

import com.mojang.realmsclient.gui.ChatFormatting;
import com.tacosupremes.runomancy.common.Runomancy;
import com.tacosupremes.runomancy.common.item.ModItems;
import com.tacosupremes.runomancy.common.utils.BlockUtils;
import com.tacosupremes.runomancy.gui.Categories;
import com.tacosupremes.runomancy.gui.IPageGiver;
import com.tacosupremes.runomancy.gui.ItemPage;
import com.tacosupremes.runomancy.gui.Page;

import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

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
	public float getStrVsBlock(ItemStack stack, IBlockState block) {
	
		return stack.getItemDamage() == stack.getMaxDamage()-1 ? super.getStrVsBlock(stack, block) / 12F : super.getStrVsBlock(stack, block);
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
	public boolean onBlockDestroyed(ItemStack stack, World worldIn, IBlockState blockIn, BlockPos pos,
			EntityLivingBase playerIn) {
		
			if(stack.getItemDamage() == stack.getMaxDamage()-1)
				return true;
			
		
			
		
		
		return super.onBlockDestroyed(stack, worldIn, blockIn, pos, playerIn);
	}
	
	
	



	@Override
	public boolean onBlockStartBreak(ItemStack is, BlockPos pos, EntityPlayer player) {
		
		World w = player.getEntityWorld();
		
		if(!w.getBlockState(pos).getBlock().isWood(w, pos))
			return false;
		
		if(is.hasTagCompound()){
			
			if(is.getTagCompound().getBoolean("ACTIVE")){
				
				List<BlockPos> bl = BlockUtils.getConnectedLogs(w, pos, 1);
				
				if(bl == null || bl.isEmpty())
					return false;
				
				for(BlockPos bp : bl){
					
					if(is.getItemDamage() != is.getMaxDamage()-3)
						is.setItemDamage(is.getItemDamage()+3);
					else
						break;
					
					w.getBlockState(bp).getBlock().dropBlockAsItem(w, bp, w.getBlockState(bp), 0);
					
					w.setBlockToAir(bp);
					
				}
				
			}
		}
		
		return super.onBlockStartBreak(is, pos, player);
		
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
	public ActionResult<ItemStack> onItemRightClick(ItemStack is, World worldIn, EntityPlayer player,
			EnumHand hand) {
		
		 
		 
		 
		 if(!is.hasTagCompound()){
			 is.setTagCompound(new NBTTagCompound());
			 is.getTagCompound().setBoolean("ACTIVE", false);
		 }
		 
		 
		 if(player.isSneaking()){
			 
			 if(is.getTagCompound().getBoolean("ACTIVE"))
				 is.getTagCompound().setBoolean("ACTIVE", false);
			 else
				 is.getTagCompound().setBoolean("ACTIVE", true);
			 
			 player.swingArm(EnumHand.MAIN_HAND);;
			 
		 }
			 
		return super.onItemRightClick(is, worldIn, player, hand);
		
	}



	@Override
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
		
		 if(!stack.hasTagCompound())
				return;
		 
		
			if(stack.getTagCompound().getBoolean("ACTIVE"))
				tooltip.add(ChatFormatting.GREEN + I18n.translateToLocal("runomancy.runic") + " " + I18n.translateToLocal("runomancy.power") + " " + I18n.translateToLocal("runomancy.active"));
			else
				tooltip.add(ChatFormatting.RED + I18n.translateToLocal("runomancy.runic") + " " + I18n.translateToLocal("runomancy.power") + " " + I18n.translateToLocal("runomancy.inactive"));
			
	 }	



	@Override
		public boolean hasEffect(ItemStack stack) {
	
		 return stack.hasTagCompound() ? stack.isItemEnchanted() ? true : stack.getItemDamage() == stack.getMaxDamage()-1 ? false : stack.getTagCompound().getBoolean("ACTIVE") : false;
		}

	
	@Override
	 public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker)
	    {
		
			if(stack.getItemDamage() <= stack.getMaxDamage() - 3)
	        stack.damageItem(2, attacker);
			
	        return true;
	    }
	
	@Override
	public void onUpdate(ItemStack is, World w, Entity entity, int itemSlot, boolean isSelected) {
		
		 if(!is.hasTagCompound())
				return;
		 
		
			if(is.getTagCompound().getBoolean("ACTIVE") && is.getItemDamage() == is.getMaxDamage() - 1){
				
				is.getTagCompound().setBoolean("ACTIVE", false); 
				
			}
	}
	

}
