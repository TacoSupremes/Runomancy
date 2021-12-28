package com.tacosupremes.runomancy.common.runelogic;

import java.util.List;

import com.tacosupremes.runomancy.common.block.ModBlocks;
import com.tacosupremes.runomancy.common.block.rune.tile.TileEndRune;
import com.tacosupremes.runomancy.common.lib.LibMisc;

import com.tacosupremes.runomancy.common.recipes.ModRecipes;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class RuneEffectFurnace implements IFunctionalRuneEffect {

	@Override
	public void doEffect(World w, BlockPos pos, TileEndRune te, CompoundNBT n) {
		
		int x = pos.getX();
		int y = pos.getY();
		int z = pos.getZ();
		
		if(te.getPower() < this.getCost())
			return;
		
		  List<ItemEntity> entities = w.getEntitiesWithinAABB(ItemEntity.class, new AxisAlignedBB(x - 1, y - 1, z - 1, x + 2, y + 0.3F, z + 2));
	        for (ItemEntity entity : entities) {
	        	
	        	ItemStack is = entity.getItem();
	        	
	        	
	        	
	        	ItemStack result = ModRecipes.getSmeltingResult(w, is);
	        	
	        	if(!result.isEmpty())
	        	{
	        		if(te.getPower() >= getCost()){
	        		
	        			te.removePower(getCost());
	        			
	        			spawnParticleOnEntity(ParticleTypes.SMOKE, entity);
	        			spawnParticleOnEntity(ParticleTypes.FLAME, entity);
	        			
	        			if(is.getCount() == 1){
	        				entity.setItem(result.copy());
	        				entity.setPositionAndUpdate(entity.getPosX(), entity.getPosY()+0.1D, entity.getPosZ());
	        			}
	        			else
						{
							ItemEntity ent = new ItemEntity(w, entity.getPosX(), entity.getPosY(), entity.getPosZ());
							ent.setItem(result.copy());
							RuneEffectFurnace.setVelocity(ent, 0, 0.1D, 0);

							entity.setItem(is.copy().split(is.getCount()-1));

							//ent.setPosition(entity.posX, entity.posY+0.1, entity.posZ);
							ent.setMotion(0,0.4D,0);
							if(!w.isRemote)
								w.addEntity(ent);
	        			}
	        		}
	        		
	        	}
	        
	        }
	}
	        public static void spawnParticleOnEntity(BasicParticleType en, ItemEntity e)
			{
	    		e.getEntityWorld().addParticle(en, e.getPosX(), e.getPosY(), e.getPosZ(), 0, 0, 0);
	    	}
		
	        
	        public static void setVelocity(Entity e, double x, double y, double z)
			{
	        	e.setMotion(x,y,z);
	        }

	@Override
	public Block[] getNeededBlocks() {
		
		Block o = ModBlocks.OBSIDIAN_RUNE.get();
		Block f = ModBlocks.FIRE_RUNE.get();
		Block e = ModBlocks.END_RUNE.get();
		
		
		return new Block[]{o,o,o,o,o,
						   o,f,f,f,o,
						   o,f,e,f,o,
						   o,f,f,f,o,
						   o,o,o,o,o};
	}

	@Override
	public boolean isGenerating() {
	
		return false;
	}

	@Override
	public int[] getFinalBlockStates() {
		
		return new int[]{1,5,5,5,2,
						 8,1,1,1,6,
						 8,1,3,1,6,
						 8,1,1,1,6,
						 3,7,7,7,4};
	}

	@Override
	public int getPowerCapacity() {
	
		return 500;
	}

	@Override
	public int getTransferRate() {
	
		return 20;
	}

	@Override
	public int getCost() {
			
		return 5;
	}
	@Override
	public String getName() {
		
		return LibMisc.MODID + ".furnace.effect";
	}

}
