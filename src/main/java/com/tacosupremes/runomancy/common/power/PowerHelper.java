package com.tacosupremes.runomancy.common.power;

import java.util.ArrayList;
import java.util.List;

import com.tacosupremes.runomancy.common.power.block.tile.IPowerNode;
import com.tacosupremes.runomancy.common.power.block.tile.IPowerTile;
import com.tacosupremes.runomancy.common.utils.BlockUtils;
import com.tacosupremes.runomancy.common.utils.Vector3;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class PowerHelper {
	
	
	public static List<IPowerTile> getPowerSources(World w, BlockPos pos, int r){
		
		
		List<IPowerTile> l =getBlocksInRange(w, pos, null, null, null, true, r);
		
		
		
		return l.isEmpty() ? null : l;
	}
	
	
	
public static List<IPowerTile> getBlocksInRange(World w, BlockPos pos, List<IPowerTile> l, List<BlockPos> b, List<String> s, boolean firstTime, int r){
		
		
		if(firstTime){
			List<IPowerTile> l2 = new ArrayList<IPowerTile>();
			List<BlockPos> b2 = new ArrayList<BlockPos>();
			List<String> s2 = new ArrayList<String>();
			
			for(int xD = -r;xD<=r;xD++){
				for(int yD = -r;yD<=r;yD++){
					for(int zD = -r;zD<=r;zD++){
						
						if(xD==0 &&yD==0&&zD==0)
							continue;
						
						BlockPos bp = pos.add(xD, yD, zD);
						TileEntity tile = w.getTileEntity(bp);
						
						if(tile == null || !(tile instanceof IPowerNode || tile instanceof IPowerTile))
							continue;
						
						if(tile instanceof IPowerTile){
							
							l2.add((IPowerTile)tile);
							b2.add(bp);
							s2.add(bp.toString());
							continue;
						}
						
						if(tile instanceof IPowerNode){
							
							b2.add(bp);
							s2.add(bp.toString());
							continue;
						}
						
						
						
						
						
				}
				
				}
			}
				
			return getBlocksInRange(w, pos, l2, b2, s2, false, r);
			}else{
				
				boolean blocksAdded = false;
				
				
				for(int i=0; i<b.size();i++){
					
					BlockPos pos2 = b.get(i);
				for(int xD = -r;xD<=r;xD++){
					for(int yD = -r;yD<=r;yD++){
						for(int zD = -r;zD<=r;zD++){
							
							if(xD==0 &&yD==0&&zD==0)
								continue;
							
							
							BlockPos bp = pos2.add(xD, yD, zD);
							TileEntity tile = w.getTileEntity(bp);
							
							if(tile == null || s.contains(bp.toString())|| !(tile instanceof IPowerNode || tile instanceof IPowerTile))
								continue;
							
							if(tile instanceof IPowerTile){
								
								l.add((IPowerTile)tile);
								b.add(bp);
								s.add(bp.toString());
								blocksAdded = true;
								continue;
							}
							
							if(tile instanceof IPowerNode){
								
								b.add(bp);
								s.add(bp.toString());
								blocksAdded = true;
								continue;
							}
							
							
							
							
							
					}
					
					}
				}
				
				}
				
				
				
				
				if(blocksAdded)
					return  getBlocksInRange(w, pos, l, b, s, false, r);
				
				
				
			}
			
			
		
		
		
		return l;
		
	}
	

	
	public static int getTotalAvailablePower(World w, BlockPos pos, int r){
		int i = 0;
		
		
		List<IPowerTile> ip = getPowerSources(w, pos, r);
		
		if(ip == null)
			return 0;
		
		for(IPowerTile it :ip){
			i+=it.getPower();
		}
		
		return i;
	}
	
	
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
	
	public static float getHigh(List<Float> in){
		float high = 0;
		for(float h : in){
			
			if(h>high)
				high=h;
			
		}
		
		return high;
		
	}
	
	public static float getLow(List<Float> in){
		float high = Integer.MAX_VALUE;
		for(float h : in){
			
			if(h<high)
				high=h;
			
		}
		
		return high;
		
	}
	
	public static int drainPower(World w, BlockPos pos, int amountDrain, int r, boolean doit){
		
		
		List<IPowerTile> l = sortBasedOnPower(getPowerSources(w, pos, r), true);
		
		
		if(l== null)
			return -1;
		int total = getTotalAvailablePower(l);
		int amountDrained = 0;
		
		if(total <= amountDrain){
			
			for(IPowerTile ipt : l){
				if(doit)
				ipt.removePower(ipt.getPower());
			}
			
			return total;
		}else{
			int index = 0;
			
			while (amountDrained<amountDrain){
			if(amountDrained+ l.get(index).getPower()<=amountDrain){
			amountDrained+= l.get(index).getPower();
			if(doit)
			l.get(index).removePower(l.get(index).getPower());
			}else{
				if(doit)
				l.get(index).removePower(amountDrained + l.get(index).getPower()-amountDrain);
				amountDrained = amountDrain;
				break;
			}
			index++;
			
			if(index >= l.size())
				break;
			
			}
			
			
		}
		
		
		
		
		
		
		return amountDrained;
	}
	
public static int addPower(World w, BlockPos pos, int amountFill, int r, boolean doit){
		
	
		
		List<IPowerTile> l = sortBasedOnPower(getPowerSources(w, pos, r), false);
		
	if(l == null)
		return -1;
	
			int index = 0;
			
			int amountFilled= 0;
			
			
			while (amountFilled<amountFill){
			
				int available = l.get(index).getMaxPower()-l.get(index).getPower();
				
				
				if(available + amountFilled <=amountFill){
					if(doit)
					l.get(index).addPower(available);
					amountFilled+=available;
					
				}else{
					if(doit)
					l.get(index).addPower(amountFill-amountFilled);
					amountFilled = amountFill;
					
					break;
				}
				
			index++;
			
			if(index >= l.size())
				break;
			
		}
		
		
		
		
		
		
		return amountFilled;
	}

	private static int getTotalAvailablePower(List<IPowerTile> ip) {
		
		int i = 0;

		if(ip == null)
			return 0;
		
		for(IPowerTile it :ip){
			i+=it.getPower();
		}
		return i; 
	}
	
	
	
	

	public static List<BlockPos> getTorches(World w, BlockPos pos, int r, List<BlockPos> b, List<String> sb) {
		
		
		if(b == null){
			b = new ArrayList<BlockPos>();
			sb = new ArrayList<String>();
		}
		for(int xD = -r;xD<=r;xD++){
			for(int yD = -r;yD<=r;yD++){
				for(int zD = -r;zD<=r;zD++){
					
					if(xD==0 &&yD==0&&zD==0)
						continue;
					
					BlockPos bp =  b.isEmpty() ? pos.add(xD, yD, zD) : b.get(b.size()-1).add(xD, yD, zD);
					TileEntity tile = w.getTileEntity(bp);
					
					if(tile == null || !(tile instanceof IPowerNode || tile instanceof IPowerTile) || sb.contains(bp.toString()))
						continue;
					
					if(tile instanceof IPowerTile){
						
					if(((IPowerTile)tile).getMaxPower() != ((IPowerTile)tile).getPower()){
						b.add(bp);
						sb.add(bp.toString());
					return b;
					}
						
					}
					
					if(tile instanceof IPowerNode){
						b.add(bp);
						sb.add(bp.toString());
						
						return getTorches(w,pos,r,b,sb);
					
					}
					
				}
				
			}
					
		}
		
		
		return null;
		
	
	
	}
	
	public static void drawTorchLines(World w, BlockPos pos, int r, boolean drain){
		
		List<BlockPos> bp2 = PowerHelper.getTorches(w, pos, r, null, null);
		
		List<BlockPos> bp = new ArrayList<BlockPos>();
		
		bp.add(pos);
		bp.addAll(bp2);
		
		if(bp == null || bp.isEmpty() || bp.size() == 1)
			return;
		
		
		
		for(int i = 0; i< bp.size()-1; i++){
			
			
			BlockUtils.drawLine(w, Vector3.fromBlockPos(bp.get(i)).add(0.5D), Vector3.fromBlockPos(bp.get(i+1)).add(0.5D), drain ? EnumParticleTypes.REDSTONE : EnumParticleTypes.SPELL_WITCH);
			
			
		}
	
	}

}
