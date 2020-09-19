package com.tacosupremes.runomancy.common.item;

import com.tacosupremes.runomancy.common.block.tile.INode;
import com.tacosupremes.runomancy.common.utils.BlockUtils;
import com.tacosupremes.runomancy.common.utils.Vector3;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.particles.RedstoneParticleData;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ItemRunicWand extends ItemMod
{
	@Override
	public String getItemRegistryName()
	{
		return "runic_wand";
	}




	@Override
	public ActionResultType onItemUse(ItemUseContext context)
	{
		
		World w = context.getWorld();

		BlockPos pos = context.getPos();


		PlayerEntity player = context.getPlayer();

		Hand hand = context.getHand();

		if(w.getTileEntity(pos) instanceof INode){

			if(!player.getHeldItem(hand).hasTag()){
				player.getHeldItem(hand).setTag(new CompoundNBT());
			}
			
			if(player.getHeldItem(hand).getTag().getBoolean("ACTIVE")){
				
				INode n = (INode)w.getTileEntity(pos);
				
				n.getNodeList().add(BlockPos.fromLong(player.getHeldItem(hand).getTag().getLong("LINK")));
				INode n2 = (INode)w.getTileEntity(BlockPos.fromLong(player.getHeldItem(hand).getTag().getLong("LINK")));
				n2.getNodeList().add(pos);
				
				player.getHeldItem(hand).getTag().remove("LINK");
				player.getHeldItem(hand).getTag().putBoolean("ACTIVE", false);
			}else{
				player.getHeldItem(hand).getTag().putBoolean("ACTIVE", true);
				player.getHeldItem(hand).getTag().putLong("LINK", pos.toLong());
			}
			
		//	PowerHelper.drawTorchLines(w, pos, 5, false);
		//	INode n = (IPowerNode)w.getTileEntity(pos);
			
		//	System.out.println(n.getLinkedBlocks().toString());
			
			
		}
		
		
		
		
		return super.onItemUse(context);
		
	}

	@Override
	public void inventoryTick(ItemStack is, World w, Entity e, int itemSlot, boolean isSelected) {
		
		if(!is.hasTag())
			return;
		
		
		
		if(is.getTag().getBoolean("ACTIVE"))
		{
			
			BlockPos bp = BlockPos.fromLong(is.getTag().getLong("LINK"));
			
			 RayTraceResult rr = rayTrace(w, (PlayerEntity) e, RayTraceContext.FluidMode.NONE);
			    
			if(rr == null)
			{
				System.out.print("WHY");
				return;
			}

			double distance = rr.getHitVec().distanceTo(new Vec3d(bp));
			
			BlockUtils.drawLine(w, Vector3.fromBlockPos(bp).add(0.5D), new Vector3(rr.getHitVec()), distance > 7.65D ? RedstoneParticleData.REDSTONE_DUST : ParticleTypes.HAPPY_VILLAGER);
			
			
		}
		
	}
	

	
}
