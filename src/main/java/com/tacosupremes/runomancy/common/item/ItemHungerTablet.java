package com.tacosupremes.runomancy.common.item;

import java.util.List;

import com.mojang.realmsclient.gui.ChatFormatting;
import com.tacosupremes.runomancy.gui.Categories;
import com.tacosupremes.runomancy.gui.IPageGiver;
import com.tacosupremes.runomancy.gui.ItemPage;
import com.tacosupremes.runomancy.gui.Page;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;

public class ItemHungerTablet extends ItemMod implements IPageGiver{

	public ItemHungerTablet() {
		super("hungerRune", 1);
		this.setMaxDamage(0);
		this.setMaxStackSize(1);
		
		
	}

	
	 @Override
	  public int getMetadata(int damage) {
	    return damage;
	  }
	 
	 
	 
	




	
	


	@Override
	public ActionResult<ItemStack> onItemRightClick(ItemStack is, World worldIn, EntityPlayer playerIn,
			EnumHand hand) {
		
		
		if(!playerIn.isSneaking())
			return super.onItemRightClick(is, worldIn, playerIn, hand);
		
		if(is.getItemDamage() < 1)
			is.setItemDamage(1);
		else
			is.setItemDamage(0);
		
		
		return super.onItemRightClick(is, worldIn, playerIn, hand);
		
	}


	@Override
	public void onUpdate(ItemStack is, World w, Entity e, int itemSlot, boolean isSelected) {
		
		if(e.ticksExisted %40 != 0)
			return;
		
		if(!is.hasTagCompound()){
			is.setTagCompound(new NBTTagCompound());
			is.getTagCompound().setFloat("SAT",0);
			is.getTagCompound().setInteger("H",0);
			
		}
		EntityPlayer player = (EntityPlayer)e;
		
		
		if(is.getTagCompound().getInteger("H") < 100 && is.getItemDamage() == 1){
		for(int i = 0; i< player.inventory.getSizeInventory(); i++){
			
			ItemStack c = player.inventory.getStackInSlot(i);
			
			if(c == null)
				continue;
			
			if(c.getItem() instanceof ItemFood){
				
				
				ItemFood f = (ItemFood)c.getItem();
				float sat = f.getSaturationModifier(c);
				int food  = f.getHealAmount(c);
				
				is.getTagCompound().setFloat("SAT",Math.min(100, sat+is.getTagCompound().getFloat("SAT")));
				is.getTagCompound().setInteger("H",Math.min(100, food+is.getTagCompound().getInteger("H")));
				
				player.inventory.getStackInSlot(i).stackSize --;
				break;
				
			}
			
		}
		
		}
		
		if(player.canEat(false)){
			
			int foodNeeded = 20 - player.getFoodStats().getFoodLevel();
			
			
			if(foodNeeded <= is.getTagCompound().getInteger("H")){
				
				player.getFoodStats().setFoodLevel(foodNeeded+player.getFoodStats().getFoodLevel());
				is.getTagCompound().setInteger("H", is.getTagCompound().getInteger("H")-foodNeeded);
			}else{
				
				player.getFoodStats().setFoodLevel(player.getFoodStats().getFoodLevel()+is.getTagCompound().getInteger("H"));
				is.getTagCompound().setInteger("H", 0);
			}
			
			int satNeeded =  Math.min(player.getFoodStats().getFoodLevel(), 20);
			
			if(satNeeded <= is.getTagCompound().getFloat("SAT")){
				
				player.getFoodStats().setFoodSaturationLevel(player.getFoodStats().getSaturationLevel() + satNeeded);
				
				is.getTagCompound().setFloat("SAT",is.getTagCompound().getFloat("SAT")-satNeeded);
				
			}else{
				
				player.getFoodStats().setFoodSaturationLevel(is.getTagCompound().getFloat("SAT"));
				
				is.getTagCompound().setFloat("SAT",0);
				
				
				
			}
			
			
			
			
		}
		
		
		
		
	}

	@Override
	public void addInformation(ItemStack is, EntityPlayer playerIn, List<String> l, boolean advanced) {
		
		if(!is.hasTagCompound())
			return;
		
		
			l.add(I18n.translateToLocal("runomancy.contains") + ": "+is.getTagCompound().getInteger("H"));
			
			
			if(is.getItemDamage() == 1)
				l.add("Food Absorption: " + ChatFormatting.GREEN + "Active");
			else
				l.add("Food Absorption: " + ChatFormatting.RED + "Inactive");
		
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
