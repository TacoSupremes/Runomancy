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
import net.minecraft.init.Blocks;
import net.minecraft.init.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ItemRunicPickaxe extends ItemPickaxe implements IPageGiver {

	public ItemRunicPickaxe() {
		super(ModItems.runic);
		this.setUnlocalizedName("runicPickaxe");
		this.setCreativeTab(Runomancy.tab);
		this.setHasSubtypes(true);
		GameRegistry.registerItem(this, this.getUnlocalizedName().substring(5));
		ModItems.nitems.add(this);
		this.setNoRepair();
	}

	@Override
	public float getStrVsBlock(ItemStack stack, IBlockState block) {
	
		return stack.getItemDamage() == stack.getMaxDamage()-1 ? super.getStrVsBlock(stack, block) / 12F : block == Blocks.obsidian ? 4*super.getStrVsBlock(stack, block) : super.getStrVsBlock(stack, block);
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
	public boolean onBlockDestroyed(ItemStack stack, World w, IBlockState block, BlockPos pos, EntityLivingBase player) {
		
		if(stack.getItemDamage() == stack.getMaxDamage())
			stack.setItemDamage(stack.getMaxDamage()-1);
	 
		
			if(stack.getItemDamage() == stack.getMaxDamage()-1)
				return true;
			
			if(stack.hasTagCompound() && stack.getTagCompound().getBoolean("ACTIVE")){
				if(stack.getItemDamage() >= stack.getMaxDamage() - 5)
					stack.setItemDamage(stack.getMaxDamage()-1);
				else
					stack.setItemDamage(stack.getItemDamage()+5);	
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

	 
	 
	 
	 
	 
	 @Override
	public ActionResult<ItemStack> onItemRightClick(ItemStack is, World w, EntityPlayer player,
			EnumHand hand) {
		
		 
		 if(!is.hasTagCompound()){
			 is.setTagCompound(new NBTTagCompound());
			 is.getTagCompound().setBoolean("ACTIVE", false);
		 }
		 
		 
		 if(player.isSneaking()){
			 
			 if(!is.getTagCompound().getBoolean("ACTIVE")){
				 is.getTagCompound().setBoolean("ACTIVE", true);
				 if(is.isItemEnchanted()){
					 
					 if(EnchantmentHelper.getEnchantmentLevel(Enchantments.silkTouch, player.getHeldItem(EnumHand.MAIN_HAND)) > 0){
					 is.getTagCompound().setBoolean("SILK", true);
					 
					 NBTTagList nl = is.getEnchantmentTagList();
					 
					 for (int i = 0; i < nl.tagCount(); ++i)
			            {
			                int j = nl.getCompoundTagAt(i).getShort("id");
			                
			                if(j == Enchantment.getEnchantmentID(Enchantments.silkTouch)){
			                	nl.removeTag(i);
			                	break;
			                }
			                
			              
			            }
					 
					 is.setTagInfo("ench", nl);
					 
					 }
					 
					 if(EnchantmentHelper.getEnchantmentLevel(Enchantments.fortune, player.getHeldItem(hand)) > 0){
						 
						 NBTTagList nl = is.getEnchantmentTagList();
							
							NBTTagCompound nbt = new NBTTagCompound();
							
							nbt.setShort("id", (short)Enchantment.getEnchantmentID(Enchantments.fortune));
							nbt.setShort("lvl", (short)(3 + EnchantmentHelper.getEnchantmentLevel(Enchantments.fortune, player.getHeldItem(EnumHand.MAIN_HAND))));
							
							for (int i = 0; i < nl.tagCount(); ++i)
				            {
				                int j = nl.getCompoundTagAt(i).getShort("id");
				                
				                if(j == Enchantment.getEnchantmentID(Enchantments.fortune)){
				                	nl.removeTag(i);
				                	break;
				                }
				                
				              
				            }
							nl.appendTag(nbt);
							is.setTagInfo("ench", nl);
						 
						 
					 }else{
						 
						NBTTagList nl = is.getEnchantmentTagList();
						
						NBTTagCompound nbt = new NBTTagCompound();
						
						nbt.setShort("id", (short)Enchantment.getEnchantmentID(Enchantments.fortune));
						nbt.setShort("lvl", (short)3);
						nl.appendTag(nbt);
						is.setTagInfo("ench", nl);
						 
					 }
					 
				 }else{
					 
					 NBTTagList nl = new NBTTagList();
						
						NBTTagCompound nbt = new NBTTagCompound();
						
						nbt.setShort("id", (short)Enchantment.getEnchantmentID(Enchantments.fortune));
						nbt.setShort("lvl", (short)3);
						nl.appendTag(nbt);
						is.setTagInfo("ench", nl);
				 }
			 }else{
				 is.getTagCompound().setBoolean("ACTIVE", false);
				 
				 
				 if(EnchantmentHelper.getEnchantmentLevel(Enchantments.fortune, player.getHeldItem(EnumHand.MAIN_HAND)) > 3){
					 
					 NBTTagList nl = is.getEnchantmentTagList();
					 
					 for (int i = 0; i < nl.tagCount(); ++i)
			            {
			                int j = nl.getCompoundTagAt(i).getShort("id");
			                
			                if(j == Enchantment.getEnchantmentID(Enchantments.fortune)){
			                	nl.getCompoundTagAt(i).setShort("lvl", (short)(EnchantmentHelper.getEnchantmentLevel(Enchantments.fortune, (player).getHeldItem(EnumHand.MAIN_HAND)) - 3));
			                	break;
			                }
			                
			              
			            }
					 
					 is.setTagInfo("ench", nl);
					 
					 
				 }else{
					 
					 NBTTagList nl = is.getEnchantmentTagList();
						
					 
					 for (int i = 0; i < nl.tagCount(); ++i)
			            {
			                int j = nl.getCompoundTagAt(i).getShort("id");
			                
			                if(j == Enchantment.getEnchantmentID(Enchantments.fortune)){
			                	nl.removeTag(i);
			                	break;
			                }
			                
			              
			            }
					 
					 is.setTagInfo("ench", nl);
					 
					 
				 }
				 
				 if(is.getTagCompound().getBoolean("SILK")){
					 
					 NBTTagList nl = is.getEnchantmentTagList();
						
						NBTTagCompound nbt = new NBTTagCompound();
						
						nbt.setShort("id", (short)Enchantment.getEnchantmentID(Enchantments.silkTouch));
						nbt.setShort("lvl", (short)1);
						nl.appendTag(nbt);
						is.setTagInfo("ench", nl);
						is.getTagCompound().setBoolean("SILK", false);
					 
				 }
				 
				 if(is.getEnchantmentTagList().hasNoTags())
					 is.getTagCompound().removeTag("ench");
				 
			 }
			 
			
			 player.swingArm(hand);
			 
		 }
			 
			 
		 
		 
		return super.onItemRightClick(is, w, player, hand);
		
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



	public boolean getIsRepairable(ItemStack toRepair, ItemStack repair){
		
		 return repair.getItem() == ModItems.runicIngot;
	    }
	
	
	@Override
	 public boolean hitEntity(ItemStack is, EntityLivingBase target, EntityLivingBase attacker)
	    {
		
			if(is.getItemDamage() <= is.getMaxDamage() - 3)
	        is.damageItem(2, attacker);
			
	        return true;
	    }

	@Override
	public void onUpdate(ItemStack is, World w, Entity entity, int itemSlot, boolean isSelected) {
		
		 if(!is.hasTagCompound())
				return;
		 
		 if(is.getItemDamage() == is.getMaxDamage())
				is.setItemDamage(is.getMaxDamage()-1);
		 
		 
			if(is.getTagCompound().getBoolean("ACTIVE") && is.getItemDamage() == is.getMaxDamage() - 1){
				
 is.getTagCompound().setBoolean("ACTIVE", false);
				 
				 
				 if(EnchantmentHelper.getEnchantmentLevel(Enchantments.fortune, ((EntityPlayer) entity).getHeldItem(EnumHand.MAIN_HAND)) > 3){
					 
					 NBTTagList nl = is.getEnchantmentTagList();
					 
					 for (int i = 0; i < nl.tagCount(); ++i)
			            {
			                int j = nl.getCompoundTagAt(i).getShort("id");
			                
			                if(j == Enchantment.getEnchantmentID(Enchantments.fortune)){
			                	nl.getCompoundTagAt(i).setShort("lvl", (short)(EnchantmentHelper.getEnchantmentLevel(Enchantments.fortune, ((EntityPlayer) entity).getHeldItem(EnumHand.MAIN_HAND)) - 3));
			                	break;
			                }
			                
			              
			            }
					 
					 is.setTagInfo("ench", nl);
					 
					 
				 }else{
					 
					 NBTTagList nl = is.getEnchantmentTagList();
						
					 
					 for (int i = 0; i < nl.tagCount(); ++i)
			            {
			                int j = nl.getCompoundTagAt(i).getShort("id");
			                
			                if(j == Enchantment.getEnchantmentID(Enchantments.fortune)){
			                	nl.removeTag(i);
			                	break;
			                }
			                
			              
			            }
					 
					 is.setTagInfo("ench", nl);
					 
					 
				 }
				 
				 if(is.getTagCompound().getBoolean("SILK")){
					 
					 NBTTagList nl = is.getEnchantmentTagList();
						
						NBTTagCompound nbt = new NBTTagCompound();
						
						nbt.setShort("id", (short)Enchantment.getEnchantmentID(Enchantments.silkTouch));
						nbt.setShort("lvl", (short)1);
						nl.appendTag(nbt);
						is.setTagInfo("ench", nl);
						is.getTagCompound().setBoolean("SILK", false);
					 
				 }
				 
				 if(is.getEnchantmentTagList().hasNoTags())
					 is.getTagCompound().removeTag("ench");
				 
			 }
				
				
			}
				
		
	}
	
	
	
	


