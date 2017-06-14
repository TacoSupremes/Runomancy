package com.tacosupremes.runomancy.common.item;

import java.util.List;

import com.tacosupremes.runomancy.common.block.ModBlocks;
import com.tacosupremes.runomancy.common.power.PowerHelper;
import com.tacosupremes.runomancy.common.power.block.tile.IPowerNode;
import com.tacosupremes.runomancy.common.utils.BlockUtils;
import com.tacosupremes.runomancy.common.utils.Vector3;
import com.tacosupremes.runomancy.gui.Categories;
import com.tacosupremes.runomancy.gui.IPageGiver;
import com.tacosupremes.runomancy.gui.ItemPage;
import com.tacosupremes.runomancy.gui.Page;

import net.minecraft.block.Block;
import net.minecraft.command.server.CommandMessage;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;

public class ItemRunicWand extends ItemMod implements IPageGiver {

	public ItemRunicWand() {
		super("runicWand",0);
		this.setMaxDamage(250);
		this.setContainerItem(this);
		
		//TODO : MAKE THIS FUNCTION AS RULER
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
	public EnumActionResult onItemUse(EntityPlayer player, World w, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		
		
	

		
		
		if(w.getTileEntity(pos) instanceof IPowerNode){
			
			
		
		
			
			if(!player.getHeldItem(hand).hasTagCompound()){
				player.getHeldItem(hand).setTagCompound(new NBTTagCompound());
			}
			
			if(player.getHeldItem(hand).getTagCompound().getBoolean("ACTIVE")){
				
				IPowerNode n = (IPowerNode)w.getTileEntity(pos);
				
				n.getLinkedBlocks().add(BlockPos.fromLong(player.getHeldItem(hand).getTagCompound().getLong("LINK")));
				IPowerNode n2 = (IPowerNode)w.getTileEntity(BlockPos.fromLong(player.getHeldItem(hand).getTagCompound().getLong("LINK")));
				n2.getLinkedBlocks().add(pos);
				
				player.getHeldItem(hand).getTagCompound().removeTag("LINK");
				player.getHeldItem(hand).getTagCompound().setBoolean("ACTIVE", false);
			}else{
				player.getHeldItem(hand).getTagCompound().setBoolean("ACTIVE", true);
				player.getHeldItem(hand).getTagCompound().setLong("LINK", pos.toLong());
			}
			
		//	PowerHelper.drawTorchLines(w, pos, 5, false);
			IPowerNode n = (IPowerNode)w.getTileEntity(pos);
			
			System.out.println(n.getLinkedBlocks().toString());
			
			
		}
		
		
		
		
		return super.onItemUse(player, w, pos, hand, facing, hitX, hitY, hitZ);
		
	}

	@Override
	public void onUpdate(ItemStack is, World w, Entity e, int itemSlot, boolean isSelected) {
		
		if(!is.hasTagCompound())
			return;
		
		
		
		if(is.getTagCompound().getBoolean("ACTIVE")){
			
			BlockPos bp = BlockPos.fromLong(is.getTagCompound().getLong("LINK"));
			
			 RayTraceResult rr = this.rayTrace(w, (EntityPlayer) e, true);
			    
			if(rr == null)
			{
				System.out.print("WHY");
				return;
			}
			double distance = Math.sqrt(bp.distanceSq(rr.hitVec.xCoord, rr.hitVec.yCoord, rr.hitVec.zCoord));
			
			BlockUtils.drawLine(w, Vector3.fromBlockPos(bp).add(0.5D), new Vector3(rr.hitVec), distance > 7.65D ? EnumParticleTypes.REDSTONE : EnumParticleTypes.VILLAGER_HAPPY);
			
			
		}
		
	}
	
	
	
}
