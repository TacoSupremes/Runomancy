package com.tacosupremes.runomancy.common.utils;


import net.minecraft.fluid.Fluid;
import net.minecraft.util.Direction;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;

public class FluidUtils
{

	public static int insertFluid(Direction d, Fluid f, IFluidHandler ih){
		
		return ih.fill(new FluidStack(f, 1000), IFluidHandler.FluidAction.EXECUTE);

	}

}
