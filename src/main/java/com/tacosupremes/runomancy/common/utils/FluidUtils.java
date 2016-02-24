package com.tacosupremes.runomancy.common.utils;

import net.minecraft.util.EnumFacing;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidHandler;

public class FluidUtils {
	
	
	public static int insertFluid(EnumFacing d, Fluid f, IFluidHandler ih){
		
		return ih.fill(d, new FluidStack(f, FluidContainerRegistry.BUCKET_VOLUME), true);

	}

}
