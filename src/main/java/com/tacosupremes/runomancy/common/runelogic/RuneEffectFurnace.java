package com.tacosupremes.runomancy.common.runelogic;

import java.util.List;

import com.tacosupremes.runomancy.common.block.ModBlocks;
import com.tacosupremes.runomancy.common.block.rune.tile.TileEndRune;
import com.tacosupremes.runomancy.common.lib.LibMisc;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class RuneEffectFurnace implements IFunctionalRuneEffect {

	@Override
	public void doEffect(World w, BlockPos pos, TileEndRune te, NBTTagCompound n) {
		
		int x = pos.getX();
		int y = pos.getY();
		int z = pos.getZ();
		
		if(te.power <this.getCost())
			return;
		
		  List<EntityItem> entities = (List<EntityItem>) w.getEntitiesWithinAABB(EntityItem.class, new AxisAlignedBB(x - 1, y - 1, z - 1, x + 2, y + 0.3F, z + 2));
	        for (EntityItem entity : entities) {
	        	
	        	ItemStack is = entity.getEntityItem();
	        	
	        	
	        	
	        	ItemStack result = FurnaceRecipes.instance().getSmeltingResult(is); 
	        	
	        	if(result != null){
	        		
	        
	        		
	        		if(te.power >= this.getCost()){
	        		
	        			te.power-=this.getCost();
	        			
	        			spawnParticleOnEntity(EnumParticleTypes.SMOKE_NORMAL, entity);
	        			spawnParticleOnEntity(EnumParticleTypes.FLAME, entity);
	        			
	        			if(is.getCount() == 1){
	        				entity.setEntityItemStack(result.copy());
	        				entity.setPositionAndUpdate(entity.posX, entity.posY+0.1D, entity.posZ);
	        			}else{
	        				entity.getEntityItem().shrink(1);
	        			//	entity.setEntityItemStack(is);
	        				EntityItem ent = new EntityItem(w);
							ent.setPosition(entity.posX, entity.posY+0.1D, entity.posZ);
							ent.setEntityItemStack(result);
							ent.motionX = 0;
							ent.motionY = 0.2D;
							ent.motionZ = 0;
							
	        				if(!w.isRemote){
	        					w.spawnEntity(ent);
	        				}
	        			}
	        		}
	        		
	        	}
	        
	        }
	}
	        public static void spawnParticleOnEntity(EnumParticleTypes en,EntityItem e){
	    		
	    		e.getEntityWorld().spawnParticle(en, e.posX, e.posY, e.posZ, 0, 0, 0, 1);
	    		
	    	}
		
	        
	        public static void setVelocity(Entity e, double x, double y, double z){
	        	
	        	e.motionX = x;
	        	e.motionY = y;
	        	e.motionZ = z;
	        	
	        	
	        }

	@Override
	public Block[] getNeededBlocks() {
		
		Block o = ModBlocks.obsidianRune;
		Block f = ModBlocks.fireRune;
		Block e = ModBlocks.endRune;
		
		
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
