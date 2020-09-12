package com.tacosupremes.runomancy.common.runelogic;

import com.tacosupremes.runomancy.common.block.ModBlocks;
import com.tacosupremes.runomancy.common.block.rune.IRune;
import com.tacosupremes.runomancy.common.block.rune.tile.TileEndRune;
import com.tacosupremes.runomancy.common.lib.LibMisc;
import com.tacosupremes.runomancy.common.power.block.tile.IPowerNode;
import com.tacosupremes.runomancy.common.utils.InventoryUtils;
import net.minecraft.block.Block;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tileentity.HopperTileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fluids.IFluidBlock;

public class RuneEffectMiner implements IFunctionalRuneEffect {

	@Override
	public void doEffect(World w, BlockPos pos, TileEndRune te, CompoundNBT nbt) {
		
		if(!nbt.contains("DONE"))
			nbt.putBoolean("DONE", false);
		
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
					nbt.putBoolean("DONE", true);
					
				
				
				if(!isGoodBlock(w,temp))
					continue;
				
				
				//ItemStack is = null;// = new ItemStack(w.getBlockState(temp).getBlock(),1, w.getBlockState(temp).getBlock().getMetaFromState(w.getBlockState(temp)));
				for(ItemStack is : Block.getDrops(w.getBlockState(temp), (ServerWorld) w, temp,  null)){
					
				
				
					if(is == null)
						continue;
				
	
				
			
					
					if(getInventory(w, pos, is) != null)
						HopperTileEntity.putStackInInventoryAllSlots(null,getInventory(w, pos, is),is,null);
					else
					{
						ItemEntity ent = new ItemEntity(w, te.getPos().getX()+0.5D, te.getPos().getY()+0.2D, te.getPos().getZ()+0.5D);
						ent.setItem(is);

						ent.setMotion(0,0.4D,0);
				
						if(!w.isRemote)
							w.addEntity(ent);
					}		
				
					}
				
				
				if(!w.isRemote)
					w.destroyBlock(temp,false);
				
				w.addParticle(ParticleTypes.EXPLOSION, temp.getX()+0.5D, temp.getY()+0.75D, temp.getZ()+0.5D, 0, 0, 0);
				
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



		if(w.getBlockState(pos).getBlock().isAir(w.getBlockState(pos), w, pos) || w.getBlockState(pos).getBlock() instanceof FlowingFluidBlock || w.getBlockState(pos).getBlock() instanceof IFluidBlock)
			return false;
		
		
		
		return true;
	}
	
	public IInventory getInventory(World w, BlockPos bp, ItemStack is){
		
		for(Direction e : Direction.values()){
			if(e == Direction.DOWN || e == Direction.UP)
			if(InventoryUtils.getInventory(w, bp.add(e.getDirectionVec())) != null && InventoryUtils.itemFits(is, InventoryUtils.getInventory(w, bp.add(e.getDirectionVec()))))
				return InventoryUtils.getInventory(w, bp.add(e.getDirectionVec()));
		
		}
		return null;
		
	}
	
	@Override
	public String getName() {
		
		return LibMisc.MODID + ".miner.effect";
	}

}
