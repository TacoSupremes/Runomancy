package com.tacosupremes.runomancy.common.runelogic;

import java.util.List;

import com.tacosupremes.runomancy.common.block.ModBlocks;
import com.tacosupremes.runomancy.common.block.rune.tile.TileEndRune;
import com.tacosupremes.runomancy.common.lib.LibMisc;
import com.tacosupremes.runomancy.common.recipes.ModRecipes;
import com.tacosupremes.runomancy.common.recipes.RuneChargerRecipe;

import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class RuneEffectRepair implements IFunctionalRuneEffect {

	@Override
	public void doEffect(World w, BlockPos pos, TileEndRune te, NBTTagCompound n) {
		
		int x = pos.getX();
		int y = pos.getY();
		int z = pos.getZ();
		
		
		
		
		
		
		
		  List<EntityItem> entities = (List<EntityItem>) w.getEntitiesWithinAABB(EntityItem.class, new AxisAlignedBB(x - 1, y - 1, z - 1, x + 2, y + 0.3F, z + 2));
		        for (EntityItem entity : entities) {
	        	
	        	if(!entity.isCollided)
	        		continue;
	        	
	        	ItemStack is = entity.getEntityItem().copy();
	        	
	        	if(is.isItemStackDamageable() && is.isItemDamaged()){
	        		
	        		
	        		
	        		if(te.power >= this.getCost()){
	        		
	        			te.power-=this.getCost();
	        			is.setItemDamage(is.getItemDamage()-1);
	        			this.spawnParticleOnEntity(entity);
	        			entity.setEntityItemStack(is);
	        			
	        		}
	        		
	        	}
	        	
	        	
	        	
	        	for(RuneChargerRecipe r : ModRecipes.rcr){
	        		
	        		
	        		
	        		if(is.getItem()== r.getIn().getItem() && is.getItemDamage() == r.getIn().getItemDamage()){
	        			if(te.power >= r.getCost()){
	        				te.power-=r.getCost();
	        				if(is.getCount() == 1)
	        				entity.setEntityItemStack(r.getOut().copy());
	        				else{
	        					
	        					EntityItem ent = new EntityItem(w);
	        					ent.setEntityItemStack(r.getOut().copy());
	        					RuneEffectFurnace.setVelocity(ent, 0, 0.1D, 0);
	        					
	        					entity.setEntityItemStack(is.copy().splitStack(is.getCount()-1));
	        					
	        					ent.setPosition(entity.posX, entity.posY+0.1, entity.posZ);
	        					ent.motionY = 0.4D;
	        					if(!w.isRemote)
	        						w.spawnEntity(ent);
	        					
	        					this.spawnParticleOnEntity(entity);
	        					
	        				}
	        				break;
	        			}else
	        				break;
	        		}		
	        		
	        	}
	        }

	}

	public void spawnParticleOnEntity(EntityItem e){
		
		e.getEntityWorld().spawnParticle(EnumParticleTypes.SPELL_WITCH, e.posX, e.posY, e.posZ, 0, 0, 0, 1);
		
	}
	
	@Override
	public Block[] getNeededBlocks() {
		
		Block o = ModBlocks.obsidianRune;
		Block e = ModBlocks.endRune;
		
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
