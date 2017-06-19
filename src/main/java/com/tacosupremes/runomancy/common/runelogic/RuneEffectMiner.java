package com.tacosupremes.runomancy.common.runelogic;

import com.tacosupremes.runomancy.common.block.ModBlocks;
import com.tacosupremes.runomancy.common.block.rune.IRune;
import com.tacosupremes.runomancy.common.block.rune.tile.TileEndRune;
import com.tacosupremes.runomancy.common.lib.LibMisc;
import com.tacosupremes.runomancy.common.power.block.tile.IPowerNode;
import com.tacosupremes.runomancy.common.utils.InventoryHelper;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.BlockStaticLiquid;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.IFluidBlock;

public class RuneEffectMiner implements IFunctionalRuneEffect {

	@Override
	public void doEffect(World w, BlockPos pos, TileEndRune te, NBTTagCompound nbt) {
		
		if(!nbt.hasKey("DONE"))
			nbt.setBoolean("DONE", false);
		
		if(nbt.getBoolean("DONE"))
			return;
		
		if(te.power < getCost())
			return;
		
		if(te.ticks %10 != 0)
			return;
	//	w.spawnParticle(particleType, xCoord, yCoord, zCoord, xOffset, yOffset, zOffset, p_175688_14_);
		int r = 6;
		outerloop:
		for(int yD = -1;yD>=-(te.getPos().getY()-1);yD--){
			for(int xD = -r;xD<=r;xD++){
				for(int zD = -r;zD<=r;zD++){
									
					
				if(yD == -1 &&(Math.abs(xD) < 3 && Math.abs(zD) < 3))
						continue;
					
				BlockPos temp = te.getPos().add(xD, yD, zD);
				
				if(w.getBlockState(temp.up()).getBlock() instanceof IRune || w.getTileEntity(temp.up()) instanceof IPowerNode)
					continue;
				
				if(w.getBlockState(temp).getBlock() instanceof IRune || w.getTileEntity(temp) instanceof IPowerNode)
					continue;
				
				if(yD == -(te.getPos().getY()-1) && xD == r && zD == r)
					nbt.setBoolean("DONE", true);
					
				
				
				if(!isGoodBlock(w,temp))
					continue;
				
				
				//ItemStack is = null;// = new ItemStack(w.getBlockState(temp).getBlock(),1, w.getBlockState(temp).getBlock().getMetaFromState(w.getBlockState(temp)));
				for(ItemStack is : w.getBlockState(temp).getBlock().getDrops(w, temp, w.getBlockState(temp), 0)){
					
				
				
					if(is == null)
						continue;
				
	
				
			
					
					if(getInventory(w, pos, is) != null)
						InventoryHelper.insertItem(is, getInventory(w, pos, is), true);
					else
					{
					
				
				
						EntityItem ent = new EntityItem(w);
						ent.setPosition(te.getPos().getX()+0.5D, te.getPos().getY()+0.2D, te.getPos().getZ()+0.5D);
						ent.setEntityItemStack(is);
						ent.motionX = 0;
						ent.motionY = 0.4D;
						ent.motionZ = 0;
				
						if(!w.isRemote)
							w.spawnEntity(ent);
					}		
				
					}
				
				
				if(!w.isRemote)
				w.setBlockToAir(temp);
				
				w.spawnParticle(EnumParticleTypes.EXPLOSION_LARGE, temp.getX()+0.5D, temp.getY()+0.75D, temp.getZ()+0.5D, 0, 0, 0, 0);
				
				te.power -= getCost();
				
						break outerloop;
						
				}
					
				}
			
				
			}
	}

	@Override
	public Block[] getNeededBlocks() {
		
		Block o = ModBlocks.obsidianRune;
		Block e = ModBlocks.earthRune;
		Block n = null;
		Block d = ModBlocks.endRune;
		
		return new Block[]{o,o,o,o,o,
						   o,e,n,e,o,
						   o,n,d,n,o,
						   o,e,n,e,o,
						   o,o,o,o,o};
	}

	@Override
	public boolean isGenerating() {
	
		return false;
	}

	@Override
	public int[] getFinalBlockStates() {
		
		return new int[]{1,5,5,5,2,
					     8,5,0,5,6,
					     8,0,6,0,6,
					     8,5,0,5,6,
					     3,7,7,7,4};
	}

	@Override
	public int getPowerCapacity() {
		
		return 2000;
	}

	@Override
	public int getTransferRate() {
		
		return 150;
	}

	@Override
	public int getCost() {
		
		return 600;
	}


	
	public boolean isGoodBlock(World w, BlockPos pos){
		
		Block b = w.getBlockState(pos).getBlock();
		
		if(w.getTileEntity(pos) != null)
			return false;
		
		if(b.getBlockHardness(w.getBlockState(pos), w, pos) < 0)
			return false;
		
		if(w.getBlockState(pos).getBlock().isAir(w.getBlockState(pos), w, pos) || FluidRegistry.lookupFluidForBlock(w.getBlockState(pos).getBlock()) != null || w.getBlockState(pos).getBlock() instanceof BlockStaticLiquid || w.getBlockState(pos).getBlock() instanceof BlockLiquid || w.getBlockState(pos).getBlock() instanceof IFluidBlock)
			return false;
		
		
		
		return true;
	}
	
	public IInventory getInventory(World w, BlockPos bp, ItemStack is){
		
		for(EnumFacing e : EnumFacing.HORIZONTALS){
			
			if(InventoryHelper.getInventory(w, bp.add(e.getDirectionVec())) != null && InventoryHelper.insertItem(is, InventoryHelper.getInventory(w, bp.add(e.getDirectionVec())), false) == null)
				return InventoryHelper.getInventory(w, bp.add(e.getDirectionVec()));
		
		}
		return null;
		
	}
	
	@Override
	public String getName() {
		
		return LibMisc.MODID + ".miner.effect";
	}

}
