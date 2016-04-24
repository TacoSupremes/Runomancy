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
			
			
		int r = 64;
				
			for(int x = -r; x<= r; x++){
				
				if(x == 0)
					continue;
				
				if(xF != null)
					break;
				
				if(w.getBlockState(pos.add(x, 0, 0)).getBlock() == ModBlocks.marker){
					xF = pos.add(x, 0, 0);
					break;
				}
					
			}
			
			for(int y = -r; y<= r; y++){
				
				if(y == 0)
					continue;
				
				if(yF != null)
					break;
				
				
				if(w.getBlockState(pos.add(0, y, 0)).getBlock() == ModBlocks.marker){
					yF = pos.add(0, y, 0);
					break;
				}
					
			}

			for(int z = -r; z<= r; z++){
				
				if(z == 0)
					continue;
				if(zF != null)
					break;
				
				
	if(w.getBlockState(pos.add(0, 0, z)).getBlock() == ModBlocks.marker){
		zF = pos.add(0, 0, z);
		break;
	}
	
			}
			
			
			System.out.println("XF" + xF +":" + "ZF" + ":" + zF);
		
			
			if(xF != null  && yF != null && zF != null){
				
				BlockUtils.drawLine(w, Vector3.fromBlockPos(pos), Vector3.fromBlockPos(xF), EnumParticleTypes.REDSTONE);
				BlockUtils.drawLine(w, Vector3.fromBlockPos(pos), Vector3.fromBlockPos(zF), EnumParticleTypes.REDSTONE);
				
				BlockPos corner = new BlockPos(xF.getX(), xF.getY(), zF.getZ());
				BlockUtils.drawLine(w, Vector3.fromBlockPos(xF), Vector3.fromBlockPos(corner), EnumParticleTypes.REDSTONE);
				BlockUtils.drawLine(w, Vector3.fromBlockPos(zF), Vector3.fromBlockPos(corner),  EnumParticleTypes.REDSTONE);
			
				Vector3 yo = new Vector3(0,Math.abs(xF.getY()-yF.getY()), 0);
				
				BlockUtils.drawLine(w, Vector3.fromBlockPos(pos).add(yo), Vector3.fromBlockPos(xF).add(yo), EnumParticleTypes.REDSTONE);
				BlockUtils.drawLine(w, Vector3.fromBlockPos(pos).add(yo), Vector3.fromBlockPos(zF).add(yo), EnumParticleTypes.REDSTONE);
				
			
				BlockUtils.drawLine(w, Vector3.fromBlockPos(xF).add(yo), Vector3.fromBlockPos(corner).add(yo), EnumParticleTypes.REDSTONE);
				BlockUtils.drawLine(w, Vector3.fromBlockPos(zF).add(yo), Vector3.fromBlockPos(corner).add(yo),  EnumParticleTypes.REDSTONE);
			
				BlockUtils.drawLine(w, Vector3.fromBlockPos(pos).add(yo), Vector3.fromBlockPos(pos), EnumParticleTypes.REDSTONE);
				BlockUtils.drawLine(w, Vector3.fromBlockPos(corner).add(yo), Vector3.fromBlockPos(corner), EnumParticleTypes.REDSTONE);
				BlockUtils.drawLine(w, Vector3.fromBlockPos(xF).add(yo), Vector3.fromBlockPos(xF), EnumParticleTypes.REDSTONE);
				BlockUtils.drawLine(w, Vector3.fromBlockPos(zF).add(yo), Vector3.fromBlockPos(zF), EnumParticleTypes.REDSTONE);
				
				return super.onItemUse(is, player, w, pos, hand, facing, hitX, hitY, hitZ);
			}
			
	if(xF != null && zF != null){
		
		BlockUtils.drawLine(w, Vector3.fromBlockPos(pos), Vector3.fromBlockPos(xF), EnumParticleTypes.REDSTONE);
		BlockUtils.drawLine(w, Vector3.fromBlockPos(pos), Vector3.fromBlockPos(zF), EnumParticleTypes.REDSTONE);
		
		BlockPos corner = new BlockPos(xF.getX(), xF.getY(), zF.getZ());
		BlockUtils.drawLine(w, Vector3.fromBlockPos(xF), Vector3.fromBlockPos(corner), EnumParticleTypes.REDSTONE);
		BlockUtils.drawLine(w, Vector3.fromBlockPos(zF), Vector3.fromBlockPos(corner),  EnumParticleTypes.REDSTONE);
	
		return super.onItemUse(is, player, w, pos, hand, facing, hitX, hitY, hitZ);
	}
		

			
		if(xF != null){
			
			BlockUtils.drawLine(w, Vector3.fromBlockPos(pos), Vector3.fromBlockPos(xF), EnumParticleTypes.REDSTONE);
			
		}
		
		if(yF != null){
			
			BlockUtils.drawLine(w, Vector3.fromBlockPos(pos), Vector3.fromBlockPos(yF), EnumParticleTypes.REDSTONE);
			
		}
			
		if(zF != null){
			
			BlockUtils.drawLine(w, Vector3.fromBlockPos(pos), Vector3.fromBlockPos(zF), EnumParticleTypes.REDSTONE);
			
		}		
			
			
			
			
		}
		
		if(w.getBlockState(pos).getBlock() == ModBlocks.powerTorch){
			
			PowerHelper.drawTorchLines(w, pos, 5, false);
			
		}
		
		return super.onItemUse(is, player, w, pos, hand, facing, hitX, hitY, hitZ);
		
	}
	
}
