package com.tacosupremes.runomancy.common.runelogic;

import com.tacosupremes.runomancy.common.block.ModBlocks;
import com.tacosupremes.runomancy.common.block.rune.tile.TileEndRune;
import com.tacosupremes.runomancy.common.lib.LibMisc;
import net.minecraft.block.Block;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;

import java.util.List;

public class RuneEffectFurnaceGen implements IRuneEffect {

	
	
	@Override
	public void doEffect(World w, BlockPos pos, TileEndRune te, CompoundNBT nbt) {
		
		
		
		if(!nbt.contains("FGTL"))
			nbt.putInt("FGTL", 0);
		
		int timeleft = nbt.getInt("FGTL");
		
		int x = pos.getX();
		int y = pos.getY();
		int z = pos.getZ();
		
		if(timeleft > 0)
		{
			te.addPower(10);
		
			nbt.putInt("FGTL", timeleft-2);
			w.addParticle(ParticleTypes.SMOKE, x+0.5D, y+0.2D, z+0.5D, 0, 0, 0);
			
			return;
		}
		
		  List<ItemEntity> entities = w.getEntitiesWithinAABB(ItemEntity.class, new AxisAlignedBB(x - 1, y - 1, z - 1, x + 2, y + 0.3F, z + 2));
		       for (ItemEntity entity : entities)
		       {


				   ItemStack is = entity.getItem().copy();



	        		int f = (int)((double)  ForgeHooks.getBurnTime(entity.getItem()) / 8D);
	        		
	        		if(f > 0)
	        		{
	        			nbt.putInt("FGTL", f);

	        			if(entity.getItem().getCount() == 1)
	        				entity.onKillCommand();
	        			else
	        			{

							entity.setItem(is.copy().split(is.getCount()-1));

	        		//		ItemEntity e = new ItemEntity(w,entity.getPosX(),entity.getPosY() + 0.1D, entity.getPosZ(), new ItemStack(entity.getItem().getItem(), entity.getItem().getCount() - 1));
						//	e.setMotion(entity.getMotion());
	        			//	entity.remove();
	        			//	e.setNoDespawn();
						//	e.setDefaultPickupDelay();


						//	w.addEntity(e);
							//	entity.setItem();
								//	entity.setItem(entity.getItem());


						}
	        				break;
	        		}
	        		else
	        			continue;
	        	
	        
	        }

		
	}

	@Override
	public Block[] getNeededBlocks() {
		
		Block e = ModBlocks.EARTH_RUNE.get();
		Block f = ModBlocks.FIRE_RUNE.get();
		Block n = ModBlocks.END_RUNE.get();


		
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
	
	@Override
	public String getName() {
		
		return LibMisc.MODID + ".furnacegen.effect";
	}

}
