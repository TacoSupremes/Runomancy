package com.tacosupremes.runomancy.common.runelogic;

import com.tacosupremes.runomancy.common.block.ModBlocks;
import com.tacosupremes.runomancy.common.block.rune.tile.TileEndRune;
import com.tacosupremes.runomancy.common.lib.LibMisc;

import net.minecraft.block.Block;
import net.minecraft.block.BlockCactus;
import net.minecraft.block.BlockNetherWart;
import net.minecraft.block.BlockReed;
import net.minecraft.block.BlockStem;
import net.minecraft.block.IGrowable;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;

public class RuneEffectPlantGrower implements IFunctionalRuneEffect {

	@Override
	public void doEffect(World w, BlockPos pos, TileEndRune te, NBTTagCompound n) {
		
		int r = RuneFormations.getRange(this)+4;
		
		
		if(te.power < this.getCost())
			return;
		
			for(int xD = -r;xD<=r;xD++){
				for(int yD = -r;yD<=r;yD++){
					for(int zD = -r;zD<=r;zD++){
						
						
						BlockPos bp = pos.add(xD,yD,zD);
						
			
			
			Block b = w.getBlockState(bp).getBlock();
			
			
			
			if(b.isAir(w.getBlockState(bp), w, bp) || b == null || b == Blocks.grass || b == Blocks.tallgrass || b == Blocks.red_flower || b == Blocks.yellow_flower || b instanceof BlockStem)
				continue;
			
			if(b instanceof IGrowable){
				
				if(te.power >= this.getCost()){
					
				
				IGrowable grow = (IGrowable)b;
				
				if(grow.canGrow(w, bp, w.getBlockState(bp), w.isRemote)){
					te.power -= this.getCost();
					for(int i=0;i<=3;i++){
						if(!w.isRemote)
					b.updateTick(w, bp, w.getBlockState(bp), w.rand);
					
					w.spawnParticle(EnumParticleTypes.VILLAGER_HAPPY, bp.getX()+0.5D+w.rand.nextGaussian()/5-w.rand.nextGaussian()/5, bp.getY()+0.3D, bp.getZ()+0.5D+w.rand.nextGaussian()/5-w.rand.nextGaussian()/5, 0, 0, 0, 0);
						
						}
					}
				
				}
				
				continue;
			}
			
			if(b instanceof BlockCactus || b instanceof BlockReed){
				
				boolean canGrow = false;
				
				canGrow = w.getBlockState(bp.up()).getBlock().isAir(w.getBlockState(bp.up()), w, bp.up()); 
				
				if(canGrow)
					canGrow = w.getBlockState(bp.down(2)).getBlock() != b;
				
				
				
				if(!canGrow)
					continue;
			}
			
			if(b instanceof IPlantable){
				
				if(b == Blocks.nether_wart && ((BlockNetherWart)b).getMetaFromState(w.getBlockState(bp)) == 3)
					continue;
				
				if(te.power >= this.getCost()*2){
					te.power -= this.getCost()*2;
				
				b.updateTick(w, bp, w.getBlockState(bp), w.rand);
				w.spawnParticle(EnumParticleTypes.VILLAGER_HAPPY, bp.getX()+0.5D+w.rand.nextGaussian()/5-w.rand.nextGaussian()/5, bp.getY()+0.3D, bp.getZ()+0.5D+w.rand.nextGaussian()/5-w.rand.nextGaussian()/5, 0, 0, 0, 0);
				
				
				}
				
				continue;
			}
			
		}
					
				}
			}
			
			
		
		
		
	}
	
	public boolean blockNear(World w, BlockPos bp, Block b){
		
		for(EnumFacing e : EnumFacing.HORIZONTALS){
			
			BlockPos bpo = bp.add(e.getDirectionVec());
			
			if(w.getBlockState(bpo).getBlock() == b)
				return true;
		}
		
		
		return false;
	}

	@Override
	public Block[] getNeededBlocks() {
	
		Block e = ModBlocks.earthRune;
		Block d = ModBlocks.endRune;
		Block n = null;
		return new Block[]{n,e,n,
						   e,d,e,
						   n,e,n};
	}

	@Override
	public boolean isGenerating() {
		
		return false;
	}

	@Override
	public int[] getFinalBlockStates() {
		
		return new int[]{0,1,0,
						 4,4,2,
						 0,3,0};
	}

	@Override
	public int getPowerCapacity() {
		
		return 750;
	}

	@Override
	public int getTransferRate() {
		
		return 75;
	}

	@Override
	public int getCost() {
		
		return 100;
	}
	
	@Override
	public String getName() {
		
		return LibMisc.MODID + ".plant.effect";
	}

}
