package com.tacosupremes.runomancy.common.power;

import com.tacosupremes.runomancy.common.block.rune.tile.TileEndRune;
import com.tacosupremes.runomancy.common.power.block.tile.IPowerTile;
import com.tacosupremes.runomancy.common.runelogic.RuneFormations;
import com.tacosupremes.runomancy.common.utils.BlockUtils;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class PowerHelper
{

	/*
	public static List<IPowerTile> sortBasedOnPower(List<IPowerTile> p, boolean high){
		
	List<IPowerTile> l = new ArrayList<IPowerTile>();
		
		
	List<Float> in = new ArrayList<Float>();
	
	if(p == null)
	return null;
	for(IPowerTile pt: p){
		in.add((float)pt.getPower());
	}
		
		
		
		
		while(!in.isEmpty()){
			
			int h = high ? in.indexOf(getHigh(in)) : in.indexOf(getLow(in));
			
			l.add(p.get(h));
			in.remove(h);
			
		}
		
				
		return l;
		
	}
	*/
	public static float getHigh(List<Float> in)
	{
		float high = 0;
		for (float h : in) {

			if (h > high)
				high = h;

		}

		return high;

	}

	public static float getLow(List<Float> in)
	{
		float high = Integer.MAX_VALUE;
		for (float h : in) {

			if (h < high)
				high = h;

		}

		return high;

	}

	private static int getTotalAvailablePower(List<IPowerTile> ip)
	{

		int i = 0;

		if (ip == null)
			return 0;

		for (IPowerTile it : ip) {
			i += it.getPower();
		}
		return i;
	}




	public static IPowerTile getBattery(World w, List<BlockPos> bp, boolean drain)
	{

		IPowerTile ret = null;

		if (bp == null || bp.isEmpty())
			return null;

		List<Integer> in = new ArrayList<Integer>();


		for (int i = 0; i < bp.size(); i++) {

			TileEntity te = w.getTileEntity(bp.get(i));

			if (te == null)
				continue;

			if (te instanceof IPowerTile)
				in.add(i);
		}

		for (Integer i : in) {


			IPowerTile ip = (IPowerTile) w.getTileEntity(bp.get(i));

			if (drain) {

				if (ip.getPower() <= 0)
					continue;
				if (ret == null)
					ret = ip;
				else {
					if (ip.getPower() > ret.getPower())
						ret = ip;
				}
			} else {

				if (ip.getMaxPower() == ip.getPower())
					continue;
				if (ret == null)
					ret = ip;
				else {
					if (ip.getPower() < ret.getPower())
						ret = ip;
				}
			}


		}

		return ret;

	}




	public static boolean isBlockPowered(World w, BlockPos pos)
	{

		if (w.isBlockPowered(pos))
			return true;

		if (w.getTileEntity(pos) instanceof TileEndRune) {
			TileEndRune te = (TileEndRune) w.getTileEntity(pos);

			int r = RuneFormations.getRange(te.getEffect());

			for (int xD = -r; xD <= r; xD++) {
				for (int yD = -r; yD <= r; yD++) {
					for (int zD = -r; zD <= r; zD++) {

						BlockPos bp = pos.add(xD, yD, zD);

						if (BlockUtils.isBlockPowered(w, bp))
							return true;

					}
				}
			}

		}


		return false;
	}

}
