package com.tacosupremes.runomancy.common.utils;

import net.minecraft.util.EnumFacing;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;

public class FluidUtils {
	
	
	public static int insertFluid(EnumFacing d, Fluid f, IFluidHandler ih){
		
		return ih.fill(new FluidStack(f, f.BUCKET_VOLUME), true);

	}

}
