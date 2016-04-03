package com.tacosupremes.runomancy.common.runelogic;

import com.tacosupremes.runomancy.common.block.ModBlocks;
import com.tacosupremes.runomancy.common.block.rune.tile.TileEndRune;
import com.tacosupremes.runomancy.common.lib.LibMisc;

import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;

public class RuneEffectSolarGen implements IRuneEffect {

	@Override
	public void doEffect(World w, BlockPos pos, TileEndRune te, NBTTagCompound n) {
	
		
	
		
		
	boolean fsky= w.canBlockSeeSky(pos);
	if(w.provider.getHasNoSky()||!fsky)
		return;
			
			
			
	  int i = w.getLightFor(EnumSkyBlock.SKY, pos) - w.getSkylightSubtracted();
      float f = w.getCelestialAngleRadians(1.0F);
      float f1 = f < (float)Math.PI ? 0.0F : ((float)Math.PI * 2F);
      f = f + (f1 - f) * 0.2F;
      i = Math.round((float)i * MathHelper.cos(f));
      i = MathHelper.clamp_int(i, 0, 5);
      
      te.power += i;
	
	}

	@Override
	public Block[] getNeededBlocks() {
		
		Block o = ModBlocks.obsidianRune;
		Block f = ModBlocks.fireRune;
		Block e = ModBlocks.endRune;
		
		return new Block[]{o,f,o,
						   f,e,f,
						   o,f,o};
	}

	@Override
	public boolean isGenerating() {
	
		return true;
	}

	@Override
	public int[] getFinalBlockStates() {
	
		return new int[]{9,2,10,
						 5,2,3,
						 11,4,12};
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
		
		return LibMisc.MODID + ".solar.effect";
	}

}
