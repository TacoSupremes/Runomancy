package com.tacosupremes.runomancy.common.runelogic;

import com.tacosupremes.runomancy.common.Runomancy;
import com.tacosupremes.runomancy.common.block.ModBlocks;
import com.tacosupremes.runomancy.common.block.rune.tile.TileEndRune;
import com.tacosupremes.runomancy.common.lib.LibMisc;

import net.minecraft.block.*;

import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.IPlantable;

public class RuneEffectPlantGrower implements IFunctionalRuneEffect {

	@Override
	public void doEffect(World w, BlockPos pos, TileEndRune te, CompoundNBT n) {
		
		int r = RuneFormations.getRange(this)+4;
		
		
		if(te.power < this.getCost())
			return;
		
			for(int xD = -r;xD<=r;xD++){
				for(int yD = -r;yD<=r;yD++){
					for(int zD = -r;zD<=r;zD++){
						
						
						BlockPos bp = pos.add(xD,yD,zD);
						
			
			
			Block b = w.getBlockState(bp).getBlock();
			
			
			
			if(b.isAir(w.getBlockState(bp), w, bp) || b == null || b == Blocks.GRASS || b == Blocks.TALL_GRASS || b == Blocks.POPPY || b == Blocks.DANDELION || b instanceof DoublePlantBlock || b instanceof FlowerBlock)
				continue;
			
			if(b instanceof IGrowable){
				
				if(te.power >= this.getCost()){
					
				
				IGrowable grow = (IGrowable)b;
				
				if(grow.canGrow(w, bp, w.getBlockState(bp), w.isRemote)){
					te.power -= this.getCost();
					for(int i=0;i<=3;i++){
						if(!w.isRemote) {
							//	b.randomTick(w.getBlockState(bp), (ServerWorld) w, bp, Runomancy.rand);

							((IGrowable) b).grow((ServerWorld) w, Runomancy.rand, bp, w.getBlockState(bp));

							w.addParticle(ParticleTypes.HAPPY_VILLAGER, bp.getX() + 0.5D + w.rand.nextGaussian() / 5 - w.rand.nextGaussian() / 5, bp.getY() + 0.3D, bp.getZ() + 0.5D + w.rand.nextGaussian() / 5 - w.rand.nextGaussian() / 5, 0, 0, 0);
						}
						}
					}
				
				}
				
				continue;
			}
			
			
			if(b instanceof StemBlock){
				
				Block crop = getCropFromSeed(((StemBlock)b).getItem(w, bp, w.getBlockState(bp)));
				
				if(crop != null){
					
					if(!blockNear(w, bp, crop)){
						
						te.power -= this.getCost();
						for(int i=0;i<=3;i++){
							if(!w.isRemote) {
								((IGrowable)b).grow((ServerWorld) w, Runomancy.rand,bp, w.getBlockState(bp));

								w.addParticle(ParticleTypes.HAPPY_VILLAGER, bp.getX()+0.5D+w.rand.nextGaussian()/5-w.rand.nextGaussian()/5, bp.getY()+0.3D, bp.getZ()+0.5D+w.rand.nextGaussian()/5-w.rand.nextGaussian()/5, 0,  0, 0);

							}
						//b.updateTick(w, bp, w.getBlockState(bp), w.rand);
						
						//w.spawnParticle(EnumParticleTypes.VILLAGER_HAPPY, bp.getX()+0.5D+w.rand.nextGaussian()/5-w.rand.nextGaussian()/5, bp.getY()+0.3D, bp.getZ()+0.5D+w.rand.nextGaussian()/5-w.rand.nextGaussian()/5, 0, 0, 0, 0);
							
							}
						continue;
					}
					
				}
				
			}
			
			if(b instanceof CactusBlock || b instanceof SugarCaneBlock){
				
				boolean canGrow  = w.getBlockState(bp.up()).getBlock().isAir(w.getBlockState(bp.up()), w, bp.up()); 
				
				if(canGrow)
					canGrow = w.getBlockState(bp.down(2)).getBlock() != b;
				
				
				
				if(!canGrow)
					continue;
			}
			
			if(b instanceof IPlantable){
				
				if(b == Blocks.NETHER_WART && w.getBlockState(bp).get(NetherWartBlock.AGE) == 3)
					continue;
				
				if(te.power >= this.getCost()*2){
					te.power -= this.getCost()*2;

					if(!w.isRemote) {
						((IGrowable)b).grow((ServerWorld) w, Runomancy.rand,bp, w.getBlockState(bp));

						w.addParticle(ParticleTypes.HAPPY_VILLAGER, bp.getX()+0.5D+w.rand.nextGaussian()/5-w.rand.nextGaussian()/5, bp.getY()+0.3D, bp.getZ()+0.5D+w.rand.nextGaussian()/5-w.rand.nextGaussian()/5, 0,  0, 0);

					}

				}
				
				continue;
			}
			
		}
					
				}
			}
			
			
		
		
		
	}
	
	public boolean blockNear(World w, BlockPos bp, Block b){
		
		for(Direction e : Direction.values()){

			if(e == Direction.DOWN || e == Direction.UP)
				continue;

			BlockPos bpo = bp.add(e.getDirectionVec());
			
			if(w.getBlockState(bpo).getBlock() == b)
				return true;
		}
		
		
		return false;
	}
	
public Block getCropFromSeed(ItemStack is){
	
	if(is.isEmpty())
		return null;
	
	if(is.getItem() == Items.MELON_SEEDS)
		return Blocks.MELON;
	
	if(is.getItem() == Items.PUMPKIN_SEEDS)
		return Blocks.PUMPKIN;
	
	return null;
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
