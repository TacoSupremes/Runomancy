package com.tacosupremes.runomancy.common.runelogic;

import java.util.List;

import com.tacosupremes.runomancy.common.block.ModBlocks;
import com.tacosupremes.runomancy.common.block.rune.tile.TileEndRune;
import com.tacosupremes.runomancy.common.lib.LibMisc;
import com.tacosupremes.runomancy.common.power.PowerHelper;
import com.tacosupremes.runomancy.common.power.block.tile.IPowerTile;
import com.tacosupremes.runomancy.common.power.item.IRunicBattery;
import com.tacosupremes.runomancy.common.recipes.ModRecipes;
import com.tacosupremes.runomancy.common.recipes.RuneChargerRecipe;

import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;

public class RuneEffectRepair implements IFunctionalRuneEffect {

	@Override
	public void doEffect(World w, BlockPos pos, TileEndRune te, NBTTagCompound n) {
		
		int x = pos.getX();
		int y = pos.getY();
		int z = pos.getZ();
		
		if(te.power <this.getCost())
			return;
		
		  List<EntityItem> entities = (List<EntityItem>) w.getEntitiesWithinAABB(EntityItem.class, AxisAlignedBB.fromBounds(x, y, z, x + 1, y + 0.3F, z + 1));
	        for (EntityItem entity : entities) {
	        	
	        	ItemStack is = entity.getEntityItem().copy();
	        	
	        	if(is.isItemStackDamageable() && is.isItemDamaged()){
	        		
	        		
	        		
	        		if(te.power >= this.getCost()){
	        		
	        			te.power-=this.getCost();
	        			is.setItemDamage(is.getItemDamage()-1);
	        			this.spawnParticleOnEntity(entity);
	        			entity.setEntityItemStack(is);
	        			
	        		}
	        		
	        	}
	        	
	        	if(is.getItem() instanceof IRunicBattery){
	        		
	        	IRunicBattery bat =	(IRunicBattery)is.getItem();
	        	if(te.power > this.getTransferRate() && bat.addPower(is, this.getTransferRate(), false) > 0){
	        	bat.addPower(is, this.getTransferRate(), true);
	        	te.power-= this.getTransferRate();
	        	this.spawnParticleOnEntity(entity);
    			entity.setEntityItemStack(is);
	        	}
	        	}
	        	
	        	for(RuneChargerRecipe r : ModRecipes.rcr){
	        		
	        		
	        		
	        		if(is.getItem()== r.getIn().getItem() && is.getItemDamage() == r.getIn().getItemDamage()){
	        			if(te.power >= r.getCost()){
	        				te.power-=r.getCost();
	        				if(is.stackSize == 1)
	        				entity.setEntityItemStack(r.getOut());
	        				else{
	        					
	        					EntityItem ent = new EntityItem(w);
	        					ent.setEntityItemStack(r.getOut());
	        					RuneEffectFurnace.setVelocity(ent, 0, 0.1D, 0);
	        					is.stackSize--;
	        					entity.setEntityItemStack(is);
	        					ent.setPosition(entity.posX, entity.posY, entity.posZ);
	        					if(!w.isRemote)
	        						w.spawnEntityInWorld(ent);
	        					
	        					this.spawnParticleOnEntity(entity);
	        					
	        				}
	        				break;
	        			}
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
		
		return 750;
	}

	@Override
	public int getTransferRate() {
		
		return 75;
	}
	@Override
	public int getCost(){
		
		return 150;
	}
	
	@Override
	public String getName() {
		
		return LibMisc.MODID + ".repair.effect";
	}
	
}
