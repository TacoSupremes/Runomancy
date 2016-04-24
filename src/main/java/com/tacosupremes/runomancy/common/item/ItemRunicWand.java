package com.tacosupremes.runomancy.common.item;

import java.util.List;

import com.tacosupremes.runomancy.common.block.ModBlocks;
import com.tacosupremes.runomancy.common.power.PowerHelper;
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
import net.minecraft.util.EnumParticleTypes;
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
	public EnumActionResult onItemUse(ItemStack is, EntityPlayer player, World w, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		
		
		
		
		if(w.getBlockState(pos).getBlock() == ModBlocks.marker){
			
			BlockPos xF = null;
			BlockPos yF = null;
			BlockPos zF = null;
			
			
			
			for(int x = 1; x<= 64; x++){
				
				if(w.getBlockState(pos.add(x, 0, 0)).getBlock() == ModBlocks.marker){
					xF = pos.add(x, 0, 0);
					break;
				}
					
			}
			
			for(int y = 1; y<= 64; y++){
				
				if(w.getBlockState(pos.add(0, y, 0)).getBlock() == ModBlocks.marker){
					yF = pos.add(0, y, 0);
					break;
				}
					
			}

			for(int z = 1; z<= 64; z++){
	
	if(w.getBlockState(pos.add(0, 0, z)).getBlock() == ModBlocks.marker){
		zF = pos.add(0, 0, z);
		break;
	}
	
			}
	if(xF != null && zF != null){
		
		BlockUtils.drawLine(w, Vector3.fromBlockPos(pos), Vector3.fromBlockPos(xF), EnumParticleTypes.REDSTONE);
		BlockUtils.drawLine(w, Vector3.fromBlockPos(pos), Vector3.fromBlockPos(zF), EnumParticleTypes.REDSTONE);
		
		BlockPos corner = new BlockPos(xF.getX(), xF.getY(), zF.getZ());
		BlockUtils.drawLine(w, Vector3.fromBlockPos(xF), Vector3.fromBlockPos(corner), EnumParticleTypes.REDSTONE);
		BlockUtils.drawLine(w, Vector3.fromBlockPos(zF), Vector3.fromBlockPos(corner),  EnumParticleTypes.REDSTONE);
		
	}
		

			
			
			
			
			
			
			
			
		}
		
		if(w.getBlockState(pos).getBlock() == ModBlocks.powerTorch){
			
			PowerHelper.drawTorchLines(w, pos, 5, false);
			
		}
		
		return super.onItemUse(is, player, w, pos, hand, facing, hitX, hitY, hitZ);
		
	}
	
}
