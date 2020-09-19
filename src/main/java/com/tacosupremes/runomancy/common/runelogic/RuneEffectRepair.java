package com.tacosupremes.runomancy.common.runelogic;

import java.util.List;

import com.tacosupremes.runomancy.common.block.ModBlocks;
import com.tacosupremes.runomancy.common.block.rune.tile.TileEndRune;
import com.tacosupremes.runomancy.common.lib.LibMisc;
import com.tacosupremes.runomancy.common.recipes.ModRecipes;
import com.tacosupremes.runomancy.common.recipes.RuneChargerRecipe;

import net.minecraft.block.Block;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class RuneEffectRepair implements IFunctionalRuneEffect {

	@Override
	public void doEffect(World w, BlockPos pos, TileEndRune te, CompoundNBT n) {
		
		int x = pos.getX();
		int y = pos.getY();
		int z = pos.getZ();

		  List<ItemEntity> entities = (List<ItemEntity>) w.getEntitiesWithinAABB(ItemEntity.class, new AxisAlignedBB(x - 1, y - 1, z - 1, x + 2, y + 0.3F, z + 2));
		        for (ItemEntity entity : entities) {
	        	
	        	if(!entity.collided)
	        		continue;
	        	
	        	ItemStack is = entity.getItem().copy();
	        	
	        	if(is.isDamageable() && is.isDamaged()){
	        		
	        		
	        		
	        		if(te.power >= this.getCost()){
	        		
	        			te.power-=this.getCost();
	        			is.damageItem(-1,null,null);
	        			//is.setItemDamage(is.getItemDamage()-1);
	        			this.spawnParticleOnEntity(entity);
	        			entity.setItem(is);
	        			
	        		}
	        		
	        	}
	        	
	        	
	        	
	        	for(RuneChargerRecipe r : ModRecipes.rcr)
	        	{
	        		if(is.isItemEqual(r.getIn()))
	        		{
	        			if(te.power >= r.getCost())
	        			{
	        				te.power-=r.getCost();
	        				if(is.getCount() == 1)
	        					entity.setItem(r.getOut().copy());
	        				else
	        					{
	        					
	        					ItemEntity ent = new ItemEntity(w, entity.getPosX(), entity.getPosY(), entity.getPosZ());
	        					ent.setItem(r.getOut().copy());
	        					RuneEffectFurnace.setVelocity(ent, 0, 0.1D, 0);
	        					
	        					entity.setItem(is.copy().split(is.getCount()-1));
	        					
	        					//ent.setPosition(entity.posX, entity.posY+0.1, entity.posZ);
	        					ent.setMotion(0,0.4D,0);
	        					if(!w.isRemote)
	        						w.addEntity(ent);
	        					
	        					this.spawnParticleOnEntity(entity);
	        					
	        				}
	        				break;
	        			}else
	        				break;
	        		}		
	        		
	        	}
	        }

	}

	public void spawnParticleOnEntity(ItemEntity e){

		e.getEntityWorld().addParticle(ParticleTypes.WITCH, e.getPosX(), e.getPosY(), e.getPosZ(), 0, 0.1, 0);
		
	}
	
	@Override
	public Block[] getNeededBlocks() {
		
		Block o = ModBlocks.OBSIDIAN_RUNE.get();
		Block e = ModBlocks.END_RUNE.get();
		
		return new Block[]{ o,o,o,
							o,e,o,
							o,o,o};
	}

	@Override
	public int[] getFinalBlockStates() {
		
		return new int[]{1,5,2,
						 8,1,6,
						 3,7,4};
	}

	@Override
	public boolean isGenerating() {
		
		return false;
	}

	@Override
	public int getPowerCapacity() {
		
		return 1500;
	}

	@Override
	public int getTransferRate() {
		
		return 125;
	}
	@Override
	public int getCost(){
		
		return 250;
	}
	
	@Override
	public String getName() {
		
		return LibMisc.MODID + ".repair.effect";
	}
	
}
