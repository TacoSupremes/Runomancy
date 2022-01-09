package com.tacosupremes.runomancy.common.runelogic;

import com.tacosupremes.runomancy.common.block.ModBlocks;
import com.tacosupremes.runomancy.common.block.rune.tile.TileEndRune;
import com.tacosupremes.runomancy.common.lib.LibMisc;
import net.minecraft.block.Block;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.LightType;
import net.minecraft.world.World;

public class RuneEffectSolarGen implements IRuneEffect {

	@Override
	public void doEffect(World w, BlockPos pos, TileEndRune te, CompoundNBT n)
	{
		if(!w.canBlockSeeSky(pos))
			return;

		int i = w.getLightFor(LightType.SKY, pos) - w.getSkylightSubtracted();
		float f = w.getCelestialAngleRadians(1.0F);
		float f1 = f < (float)Math.PI ? 0.0F : ((float)Math.PI * 2F);
		f = f + (f1 - f) * 0.2F;
		i = Math.round((float)i * MathHelper.cos(f));
		i = MathHelper.clamp(i, 0, 10);
      
		te.addPower(i);
	}

	@Override
	public Block[] getNeededBlocks() {
		
		Block o = ModBlocks.OBSIDIAN_RUNE.get();
		Block f = ModBlocks.FIRE_RUNE.get();
		Block e = ModBlocks.END_RUNE.get();
		
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
	public String getName()
	{
		return LibMisc.MODID + ".solar.effect";
	}
}
