package com.tacosupremes.runomancy.common.runelogic;

import java.util.List;

import com.tacosupremes.runomancy.common.block.ModBlocks;
import com.tacosupremes.runomancy.common.block.rune.tile.TileEndRune;

import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class RuneEffectFurnaceGen implements IRuneEffect {

	
	
	@Override
	public void doEffect(World w, BlockPos pos, TileEndRune te, NBTTagCompound nbt) {
		
		
		
		if(!nbt.hasKey("FGTL"))
			nbt.setInteger("FGTL", 0);
		
		int timeleft = nbt.getInteger("FGTL");
		
		int x = pos.getX();
		int y = pos.getY();
		int z = pos.getZ();
		
		if(timeleft > 0){
			te.power += 10;
		
			nbt.setInteger("FGTL", timeleft-2);
			w.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, x+0.5D, y+0.2D, z+0.5D, 0, 0, 0, 0);
			
			return;
		}
		
		  List<EntityItem> entities = (List<EntityItem>) w.getEntitiesWithinAABB(EntityItem.class, AxisAlignedBB.fromBounds(x, y, z, x+1, y + 0.3F, z+1));
	        for (EntityItem entity : entities) {
	        	
	        	ItemStack is = entity.getEntityItem().copy();
	        	
	        	
	        
	        		int f = (int)((double)TileEntityFurnace.getItemBurnTime(is) / 8D);
	        		
	        		if(f > 0){   			
	        			nbt.setInteger("FGTL", f);
	        			entity.setDead();
	        			break;	
	        		}else
	        			continue;
	        	
	        
	        }

		
	}

	@Override
	public Block[] getNeededBlocks() {
		
		Block e = ModBlocks.earthRune;
		Block f = ModBlocks.fireRune;
		Block n = ModBlocks.endRune;
		
		return new Block[]{e,f,e,
						   f,n,f,
						   e,f,e};
	}

	@Override
	public boolean isGenerating() {
		
		return true;
	}

	@Override
	public int[] getFinalBlockStates() {
		
		return new int[]{5,6,5,
						 6,5,6,
						 5,6,5};
	}

	@Override
	public int getPowerCapacity() {
		
		return 500;
	}

	@Override
	public int getTransferRate() {
		
		return 50;
	}

}
