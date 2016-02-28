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
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
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
	
		return stack.getItemDamage() == stack.getMaxDamage()-1 ? super.getStrVsBlock(stack, block) / 12F : super.getStrVsBlock(stack, block);
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
	public void getSubItems(Item item, CreativeTabs tab, List<ItemStack> l) {
		
		
		l.add(new ItemStack(item,1,0));
		l.add(new ItemStack(item,1,item.getMaxDamage()-1));
		
		
	}


	@Override
	public boolean onBlockDestroyed(ItemStack stack, World w, Block block, BlockPos pos, EntityLivingBase player) {
		
		if(stack.getItemDamage() == stack.getMaxDamage())
			stack.setItemDamage(stack.getMaxDamage()-1);
		
			if(stack.getItemDamage() == stack.getMaxDamage()-1)
				return true;
			
			if(stack.hasTagCompound() && stack.getTagCompound().getBoolean("ACTIVE")){
				if(stack.getItemDamage() >= stack.getMaxDamage() - 3)
					stack.setItemDamage(stack.getMaxDamage()-2);
				else
					stack.setItemDamage(stack.getItemDamage()+3);
				
				return true;
			}
		return super.onBlockDestroyed(stack, w, block, pos, player);
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
	
	 @Override
	public ItemStack onItemRightClick(ItemStack is, World w, EntityPlayer player) {
		 
		 
		 if(!is.hasTagCompound()){
			 is.setTagCompound(new NBTTagCompound());
			 is.getTagCompound().setBoolean("ACTIVE", false);
		 }
		 
		 
		 if(player.isSneaking()){
			 
			 if(!is.getTagCompound().getBoolean("ACTIVE")){
				 is.getTagCompound().setBoolean("ACTIVE", true);
				 
				 if(is.isItemEnchanted()){
					 
					 if(EnchantmentHelper.getSilkTouchModifier(player)){
					 is.getTagCompound().setBoolean("SILK", true);
					 
					 NBTTagList nl = is.getEnchantmentTagList();
					 
					 for (int i = 0; i < nl.tagCount(); ++i)
			            {
			                int j = nl.getCompoundTagAt(i).getShort("id");
			                
			                if(j == Enchantment.silkTouch.effectId){
			                	nl.removeTag(i);
			                	break;
			                }
			                
			              
			            }
					 
					 is.setTagInfo("ench", nl);
					 
					 }
					 
					 if(EnchantmentHelper.getFortuneModifier(player) > 0){
						 
						 is.getTagCompound().setBoolean("FORTUNE", true);
						 is.getTagCompound().setInteger("FORTUNELVL", EnchantmentHelper.getFortuneModifier(player));
							
						 NBTTagList nl = is.getEnchantmentTagList();
							
							NBTTagCompound nbt = new NBTTagCompound();
							
							nbt.setShort("id", (short)Enchantment.silkTouch.effectId);
							nbt.setShort("lvl", (short)1);
							
							for (int i = 0; i < nl.tagCount(); ++i)
				            {
				                int j = nl.getCompoundTagAt(i).getShort("id");
				                
				                if(j == Enchantment.fortune.effectId){
				                	nl.removeTag(i);
				                	break;
				                }
				                
				              
				            }
							nl.appendTag(nbt);
							is.setTagInfo("ench", nl);
						 
						 
					 }else{
						 
						NBTTagList nl = is.getEnchantmentTagList();
						
						NBTTagCompound nbt = new NBTTagCompound();
						
						nbt.setShort("id", (short)Enchantment.fortune.effectId);
						nbt.setShort("lvl", (short)3);
						nl.appendTag(nbt);
						is.setTagInfo("ench", nl);
						 
					 }
					 
				 }else{
					 
					 NBTTagList nl = new NBTTagList();
						
						NBTTagCompound nbt = new NBTTagCompound();
						
						nbt.setShort("id", (short)Enchantment.silkTouch.effectId);
						nbt.setShort("lvl", (short)1);
						nl.appendTag(nbt);
						is.setTagInfo("ench", nl);
				 }
			 }else{
				 
				 is.getTagCompound().setBoolean("ACTIVE", false);
				 
				 if(EnchantmentHelper.getFortuneModifier(player) == 3){
					 
					 NBTTagList nl = is.getEnchantmentTagList();
					 
					 for (int i = 0; i < nl.tagCount(); ++i)
			            {
			                int j = nl.getCompoundTagAt(i).getShort("id");
			                
			                if(j == Enchantment.fortune.effectId){
			                	nl.getCompoundTagAt(i).setShort("id", (short)Enchantment.silkTouch.effectId);
			                	nl.getCompoundTagAt(i).setShort("lvl", (short)1);
			                	break;
			                }
			                
			              
			            }
					 
					 is.setTagInfo("ench", nl);
					 
					 
				 }else{
					 
					 NBTTagList nl = is.getEnchantmentTagList();
						
					 
					 for (int i = 0; i < nl.tagCount(); ++i)
			            {
			                int j = nl.getCompoundTagAt(i).getShort("id");
			                
			                if(j == Enchantment.silkTouch.effectId){
			                	nl.removeTag(i);
			                	break;
			                }
			                
			              
			            }
					 
					 is.setTagInfo("ench", nl);
					 
					 
				 }
				 
				 if(is.getTagCompound().getBoolean("FORTUNE")){
					 
					 NBTTagList nl = is.getEnchantmentTagList();
						
						NBTTagCompound nbt = new NBTTagCompound();
						
						nbt.setShort("id", (short)Enchantment.fortune.effectId);
						nbt.setShort("lvl", (short)is.getTagCompound().getInteger("FORTUNELVL"));
						nl.appendTag(nbt);
						is.setTagInfo("ench", nl);
						is.getTagCompound().setBoolean("FORTUNE", false);
					 
				 }
				 
				 if(is.getEnchantmentTagList().hasNoTags())
					 is.getTagCompound().removeTag("ench");
				 
			 }
			 
			
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

		@Override
		 public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker)
		    {
			
				if(stack.getItemDamage() < stack.getMaxDamage() - 3)
		        stack.damageItem(2, attacker);
				
		        return true;
		    }
		

		@Override
		public void onUpdate(ItemStack is, World w, Entity entity, int itemSlot, boolean isSelected) {
			
			 if(!is.hasTagCompound())
					return;
			 
			 if(is.getItemDamage() == is.getMaxDamage())
					is.setItemDamage(is.getMaxDamage()-1);
			 
			
				if(is.getTagCompound().getBoolean("ACTIVE") && is.getItemDamage() == is.getMaxDamage() - 1){
					
					 EntityPlayer player = (EntityPlayer)entity;
					 
					 is.getTagCompound().setBoolean("ACTIVE", false);
					 
					 if(EnchantmentHelper.getFortuneModifier(player) == 3){
						 
						 NBTTagList nl = is.getEnchantmentTagList();
						 
						 for (int i = 0; i < nl.tagCount(); ++i)
				            {
				                int j = nl.getCompoundTagAt(i).getShort("id");
				                
				                if(j == Enchantment.fortune.effectId){
				                	nl.getCompoundTagAt(i).setShort("id", (short)Enchantment.silkTouch.effectId);
				                	nl.getCompoundTagAt(i).setShort("lvl", (short)1);
				                	break;
				                }
				                
				              
				            }
						 
						 is.setTagInfo("ench", nl);
						 
						 
					 }else{
						 
						 NBTTagList nl = is.getEnchantmentTagList();
							
						 
						 for (int i = 0; i < nl.tagCount(); ++i)
				            {
				                int j = nl.getCompoundTagAt(i).getShort("id");
				                
				                if(j == Enchantment.silkTouch.effectId){
				                	nl.removeTag(i);
				                	break;
				                }
				                
				              
				            }
						 
						 is.setTagInfo("ench", nl);
						 
						 
					 }
					 
					 if(is.getTagCompound().getBoolean("FORTUNE")){
						 
						 NBTTagList nl = is.getEnchantmentTagList();
							
							NBTTagCompound nbt = new NBTTagCompound();
							
							nbt.setShort("id", (short)Enchantment.fortune.effectId);
							nbt.setShort("lvl", (short)is.getTagCompound().getInteger("FORTUNELVL"));
							nl.appendTag(nbt);
							is.setTagInfo("ench", nl);
							is.getTagCompound().setBoolean("FORTUNE", false);
						 
					 }
					 
					 if(is.getEnchantmentTagList().hasNoTags())
						 is.getTagCompound().removeTag("ench");
					 
				 }
					
				}
				
		

}
