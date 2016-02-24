package com.tacosupremes.runomancy.common.runelogic;

import com.tacosupremes.runomancy.common.block.ModBlocks;
import com.tacosupremes.runomancy.common.block.rune.tile.TileEndRune;
import com.tacosupremes.runomancy.common.lib.LibMisc;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSnow;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;

public class RuneEffectWell implements IFunctionalRuneEffect {

	@Override
	public void doEffect(World w, BlockPos pos, TileEndRune te, NBTTagCompound nbt) {
		
		
		if(snowMode(w,pos)){
			
			for(int x = -1; x<= 1; x++){
				for(int z = -1; z<= 1; z++){
					
					if(x == 0 || z == 0)
						continue;
					
					if(te.ticks %40 != 0)
						continue;
				
					if(te.power < this.getCost())
						return;
					
		if(w.getBlockState(pos.add(x, 0, z)).getBlock().isAir(w, pos.add(x, 0, z)) && w.getBlockState(pos.add(x, 0, z)).getBlock() != Blocks.snow_layer){
			w.spawnParticle(EnumParticleTypes.SNOW_SHOVEL, pos.getX() + x +0.5D, pos.getY() + 0.1, pos.getZ() + z + 0.5D, 0, 0, 0, 0);
			if(!w.isRemote)
			w.setBlockState(pos.add(x,0,z), Blocks.snow_layer.getDefaultState());
			
			te.power -= this.getCost();
			
			return;
		}
				
				}		
				
			}
			
			for(int x = -1; x<= 1; x++){
				for(int z = -1; z<= 1; z++){
					
					if(x == 0 || z == 0)
						continue;
					
					if(te.ticks %40 != 0)
						continue;
					
					if(te.power < this.getCost())
						return;
					
					if(w.getBlockState(pos.add(x,0,z)).getBlock() != Blocks.snow_layer)
						continue;
				
			
		if(w.getBlockState(pos.add(x, 0, z)).getValue(BlockSnow.LAYERS) != 8){
			w.spawnParticle(EnumParticleTypes.SNOW_SHOVEL, pos.getX() + x +0.5D, pos.getY() + 0.1 + ((double)w.getBlockState(pos.add(x, 0, z)).getValue(BlockSnow.LAYERS)/8D), pos.getZ() + z + 0.5D, 0, 0, 0, 0);
					
			if(!w.isRemote)
			w.setBlockState(pos.add(x, 0, z), w.getBlockState(pos.add(x, 0, z)).withProperty(BlockSnow.LAYERS,w.getBlockState(pos.add(x, 0, z)).getValue(BlockSnow.LAYERS)+1));
			te.power -= this.getCost();
			return;
		}
				
				}		
				
			}
			
			return;
		}
		
		
		
		for(int x = -1; x<= 1; x++){
			for(int z = -1; z<= 1; z++){
				
				if(x == 0 || z == 0)
					continue;
			
		
	if(w.getBlockState(pos.add(x, -1, z)).getBlock().isAir(w, pos.add(x, -1, z))){
		if(!w.isRemote)
		w.setBlockState(pos.add(x,-1,z), Blocks.water.getDefaultState());
	}
			
			}		
			
		}
		
	}
	
	
	
	public boolean snowMode(World w, BlockPos pos){
		
		for(int x = -1; x<= 1; x++){
			
			for(int z = -1; z<= 1; z++){
				
				if(x == 0 || z == 0)
					continue;
		
				if(w.getBlockState(pos.add(x, -1, z)).getBlock() != Blocks.snow)
					return false;
		
			}
		}
		
		return true;
	}

	@Override
	public Block[] getNeededBlocks() {

		Block w = ModBlocks.waterRune;
		Block e = ModBlocks.endRune;
		Block n = null;
		return new Block[]{n,w,n,
						   w,e,w,
						   n,w,n};

	}

	@Override
	public boolean isGenerating() {

		return false;

	}

	@Override
	public int[] getFinalBlockStates() {

		return new int[]{0,1,0,
		               	 4,7,2,
		                 0,3,0};

	}

	@Override
	public int getPowerCapacity() {

		return 100;

	}

	@Override
	public int getTransferRate() {

		return 20;

	}

	@Override
	public String getName() {

		return LibMisc.MODID + ".well.effect";

	}

	@Override
	public int getCost() {

		return 10;

	}

}
