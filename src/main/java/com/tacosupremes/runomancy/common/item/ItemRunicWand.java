package com.tacosupremes.runomancy.common.item;

import com.tacosupremes.runomancy.common.utils.BlockUtils;
import com.tacosupremes.runomancy.common.utils.Vector3;
import com.tacosupremes.runomancy.gui.Categories;
import com.tacosupremes.runomancy.gui.IPageGiver;
import com.tacosupremes.runomancy.gui.ItemPage;
import com.tacosupremes.runomancy.gui.Page;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class ItemRunicWand extends ItemMod implements IPageGiver {

	public ItemRunicWand() {
		super("runicWand",0);
		this.setMaxDamage(250);
		this.setContainerItem(this);
		
		
	}

	@Override
	public ItemStack getContainerItem(ItemStack is) {
		
		
		
		return is.getItemDamage() == is.getMaxDamage()-1 ? null : new ItemStack(is.getItem(), 1, is.getItemDamage()+1);
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
		
		  RayTraceResult raytraceresult = this.getMovingObjectPositionFromPlayer(w, player, false);
		  
		  if(raytraceresult == null)
			  return super.onItemRightClick(is, w, player, hand);
			  
			BlockPos bp = raytraceresult.getBlockPos();
			
			BlockUtils.drawLine(w, Vector3.fromEntity(player).add(0, player.eyeHeight, 0), Vector3.fromBlockPos(bp));
		  
		
		return super.onItemRightClick(is, w, player, hand);
		
	}

	@Override
	public EnumActionResult onItemUse(ItemStack is, EntityPlayer player, World w, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		
		if(!is.hasTagCompound())
			is.setTagCompound(new NBTTagCompound());
		
		  RayTraceResult raytraceresult = this.getMovingObjectPositionFromPlayer(w, player, false);
		     
		
		if(player.isSneaking()){
			is.getTagCompound().setInteger("X", pos.getX());
			is.getTagCompound().setInteger("Y", pos.getY());
			is.getTagCompound().setInteger("Z", pos.getZ());
		}else{
			if(is.getTagCompound().hasKey("X"))
			BlockUtils.drawLine(w, Vector3.fromBlockPos(pos).add(0.5D), new Vector3(is.getTagCompound().getInteger("X")+0.5D,is.getTagCompound().getInteger("Y")+1.5D,is.getTagCompound().getInteger("Z")+0.5D));
		}
		
		return super.onItemUse(is, player, w, pos, hand, facing, hitX, hitY, hitZ);
		
	}
	
	
	
	
	

}
