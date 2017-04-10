package com.tacosupremes.runomancy.common.item.tool;

import java.util.List;

import com.mojang.realmsclient.gui.ChatFormatting;
import com.tacosupremes.runomancy.common.Runomancy;
import com.tacosupremes.runomancy.common.item.ModItems;
import com.tacosupremes.runomancy.gui.Categories;
import com.tacosupremes.runomancy.gui.IPageGiver;
import com.tacosupremes.runomancy.gui.ItemPage;
import com.tacosupremes.runomancy.gui.Page;

import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ItemRunicShovel extends ItemSpade implements IPageGiver {

	public ItemRunicShovel() {
		super(ModItems.runic);
		this.setUnlocalizedName("runicShovel");
		this.setRegistryName("runicShovel");
		this.setCreativeTab(Runomancy.tab);
		this.setHasSubtypes(true);
		GameRegistry.register(this);
		ModItems.nitems.add(this);
		this.setNoRepair();
	}

	

	@Override
	public float getStrVsBlock(ItemStack stack, IBlockState block) {
	
		return stack.getItemDamage() == stack.getMaxDamage()-1 ? super.getStrVsBlock(stack, block) / 12F : super.getStrVsBlock(stack, block);
	}
	

	@Override
	public void getSubItems(Item item, CreativeTabs tab, NonNullList<ItemStack> l) {
		
		
		l.add(new ItemStack(item,1,0));
		l.add(new ItemStack(item,1,item.getMaxDamage()-1));
		
		
	}


	@Override
	public boolean onBlockDestroyed(ItemStack stack, World w, IBlockState block, BlockPos pos, EntityLivingBase player) {
		
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
	public ActionResult<ItemStack> onItemRightClick(World w, EntityPlayer player,
			EnumHand hand) {
		 
		 ItemStack is = player.getActiveItemStack();
		 
		 if(!is.hasTagCompound()){
			 is.setTagCompound(new NBTTagCompound());
			 is.getTagCompound().setBoolean("ACTIVE", false);
		 }
		 
		 
		 if(player.isSneaking()){
			 
			 if(!is.getTagCompound().getBoolean("ACTIVE")){
				 is.getTagCompound().setBoolean("ACTIVE", true);
				 
				 if(is.isItemEnchanted()){
					 
					 if(EnchantmentHelper.getEnchantmentLevel(Enchantments.SILK_TOUCH, is) > 0){
					 is.getTagCompound().setBoolean("SILK", true);
					 
					 NBTTagList nl = is.getEnchantmentTagList();
					 
					 for (int i = 0; i < nl.tagCount(); ++i)
			            {
			                int j = nl.getCompoundTagAt(i).getShort("id");
			                
			                if(j == Enchantment.getEnchantmentID(Enchantments.SILK_TOUCH)){
			                	nl.removeTag(i);
			                	break;
			                }
			                
			              
			            }
					 
					 is.setTagInfo("ench", nl);
					 
					 }
					 
					 if(EnchantmentHelper.getEnchantmentLevel(Enchantments.FORTUNE,is) > 0){
						 
						 is.getTagCompound().setBoolean("FORTUNE", true);
						 is.getTagCompound().setInteger("FORTUNELVL", EnchantmentHelper.getEnchantmentLevel(Enchantments.FORTUNE, is));
							
						 NBTTagList nl = is.getEnchantmentTagList();
							
							NBTTagCompound nbt = new NBTTagCompound();
							
							nbt.setShort("id", (short)Enchantment.getEnchantmentID(Enchantments.SILK_TOUCH));
							nbt.setShort("lvl", (short)1);
							
							for (int i = 0; i < nl.tagCount(); ++i)
				            {
				                int j = nl.getCompoundTagAt(i).getShort("id");
				                
				                if(j == Enchantment.getEnchantmentID(Enchantments.FORTUNE)){
				                	nl.removeTag(i);
				                	break;
				                }
				                
				              
				            }
							nl.appendTag(nbt);
							is.setTagInfo("ench", nl);
						 
						 
					 }else{
						 
						NBTTagList nl = is.getEnchantmentTagList();
						
						NBTTagCompound nbt = new NBTTagCompound();
						
						nbt.setShort("id", (short)Enchantment.getEnchantmentID(Enchantments.FORTUNE));
						nbt.setShort("lvl", (short)3);
						nl.appendTag(nbt);
						is.setTagInfo("ench", nl);
						 
					 }
					 
				 }else{
					 
					 NBTTagList nl = new NBTTagList();
						
						NBTTagCompound nbt = new NBTTagCompound();
						
						nbt.setShort("id", (short)Enchantment.getEnchantmentID(Enchantments.SILK_TOUCH));
						nbt.setShort("lvl", (short)1);
						nl.appendTag(nbt);
						is.setTagInfo("ench", nl);
				 }
			 }else{
				 
				 is.getTagCompound().setBoolean("ACTIVE", false);
				 
				 if(EnchantmentHelper.getEnchantmentLevel(Enchantments.FORTUNE, player.getHeldItem(hand)) == 3){
					 
					 NBTTagList nl = is.getEnchantmentTagList();
					 
					 for (int i = 0; i < nl.tagCount(); ++i)
			            {
			                int j = nl.getCompoundTagAt(i).getShort("id");
			                
			                if(j == Enchantment.getEnchantmentID(Enchantments.FORTUNE)){
			                	nl.getCompoundTagAt(i).setShort("id", (short)Enchantment.getEnchantmentID(Enchantments.SILK_TOUCH));
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
			                
			                if(j == Enchantment.getEnchantmentID(Enchantments.SILK_TOUCH)){
			                	nl.removeTag(i);
			                	break;
			                }
			                
			              
			            }
					 
					 is.setTagInfo("ench", nl);
					 
					 
				 }
				 
				 if(is.getTagCompound().getBoolean("FORTUNE")){
					 
					 NBTTagList nl = is.getEnchantmentTagList();
						
						NBTTagCompound nbt = new NBTTagCompound();
						
						nbt.setShort("id", (short)Enchantment.getEnchantmentID(Enchantments.FORTUNE));
						nbt.setShort("lvl", (short)is.getTagCompound().getInteger("FORTUNELVL"));
						nl.appendTag(nbt);
						is.setTagInfo("ench", nl);
						is.getTagCompound().setBoolean("FORTUNE", false);
					 
				 }
				 
				 if(is.getEnchantmentTagList().hasNoTags())
					 is.getTagCompound().removeTag("ench");
				 
			 }
			 
			
			 player.swingArm(hand);
			 
		 }
			 
		
		return super.onItemRightClick(w, player, hand);
		
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
					 
					 if(EnchantmentHelper.getEnchantmentLevel(Enchantments.FORTUNE, is) == 3){
						 
						 NBTTagList nl = is.getEnchantmentTagList();
						 
						 for (int i = 0; i < nl.tagCount(); ++i)
				            {
				                int j = nl.getCompoundTagAt(i).getShort("id");
				                
				                if(j == Enchantment.getEnchantmentID(Enchantments.FORTUNE)){
				                	nl.getCompoundTagAt(i).setShort("id", (short)Enchantment.getEnchantmentID(Enchantments.FORTUNE));
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
				                
				                if(j == Enchantment.getEnchantmentID(Enchantments.SILK_TOUCH)){
				                	nl.removeTag(i);
				                	break;
				                }
				                
				              
				            }
						 
						 is.setTagInfo("ench", nl);
						 
						 
					 }
					 
					 if(is.getTagCompound().getBoolean("FORTUNE")){
						 
						 NBTTagList nl = is.getEnchantmentTagList();
							
							NBTTagCompound nbt = new NBTTagCompound();
							
							nbt.setShort("id", (short)Enchantment.getEnchantmentID(Enchantments.FORTUNE));
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
