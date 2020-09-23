package com.tacosupremes.runomancy.common.utils;

import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class BlockUtils {
	/*
	public static BlockPos[] createBlockRange(BlockPos center, int range){
		
		BlockPos[] b = new BlockPos[(int) (Math.pow(2*range+1, 3))];
		
		int i =0;
		
		for(int xD = -range;xD<=range;xD++){
			for(int yD = -range;yD<=range;yD++){
				for(int zD = -range;zD<=range;zD++){
					
					b[i] = center.add(xD, yD, zD);
					i++;
					
				}
			}
			
		}
		
		
		
		
		
		return b;
	}
	
public static BlockPos[] createBlockRange(BlockPos center, int xR, int yR, int zR){
		
		BlockPos[] b = new BlockPos[(2*xR+1)*(2*yR+1)*(2*zR+1)];
		
		int i =0;
		
		for(int xD = -xR;xD<=xR;xD++){
			for(int yD = -yR;yD<=yR;yD++){
				for(int zD = -zR;zD<=zR;zD++){
					
					b[i] = center.add(xD, yD, zD);
					i++;
					
				}
			}
			
		}
		
		
		
		
		
		return b;
	}

*/




	public static double gcd(double a, double b)
	{
		double max = Math.max(a, b);

		double min = Math.min(a, b);

		double temp = max % min;

		return gcdActual(min, temp);
	}


	private static double gcdActual(double a, double b)
	{
		double temp = a % b;

		if(temp == 0)
			return b;

		return gcdActual(b, temp);
	}

  public static void drawLine(World w, Vector3 start, Vector3 end, IParticleData type)
  {
	  
  	double i = 0;




  	double vectorLength = Math.sqrt(Math.pow((start.getX()-end.getX()),2)+Math.pow((start.getY()-end.getY()),2)+Math.pow((start.getZ()-end.getZ()),2));

  	while (i <= vectorLength)
  	{
		double xD = end.getX() - start.getX();
		double yD = end.getY() - start.getY();
		double zD = end.getZ() - start.getZ();

		double dL = i / vectorLength;
		 
  		xD *= dL;
  		yD *= dL;
  		zD *= dL;

  		w.addParticle(type, start.getX() + xD,start.getY() + yD,start.getZ() + zD, 0, 0, 0);

  		i += 1;
  	}

	  w.addParticle(type, end.getX(), end.getY(), end.getZ(), 0, 0, 0);
  }

	public static void drawLineNew(World w, Vector3 start, Vector3 end, IParticleData type)
	{

		double i = 0;

		double vectorLength = Math.sqrt(Math.pow((start.getX()-end.getX()),2)+Math.pow((start.getY()-end.getY()),2)+Math.pow((start.getZ()-end.getZ()),2));


			double xD = end.getX() - start.getX();
			double yD = end.getY() - start.getY();
			double zD = end.getZ() - start.getZ();

			double dL = 0.5;

			xD *= dL;
			yD *= dL;
			zD *= dL;

			w.addParticle(type, start.getX() + xD,start.getY() + yD,start.getZ() + zD, 0, 0, 0);

			i += 1;


		w.addParticle(type, end.getX(), end.getY(), end.getZ(), 0, 0, 0);
	}
  
  public static boolean isBlockPowered(World w, BlockPos pos)
  {
  		return w.isBlockPowered(pos);
  }

}
