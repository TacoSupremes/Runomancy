package com.tacosupremes.runomancy.common.power;

import java.util.ArrayList;
import java.util.List;

import com.tacosupremes.runomancy.common.block.rune.tile.TileEndRune;
import com.tacosupremes.runomancy.common.power.block.tile.IPowerNode;
import com.tacosupremes.runomancy.common.power.block.tile.IPowerTile;
import com.tacosupremes.runomancy.common.utils.BlockUtils;
import com.tacosupremes.runomancy.common.utils.Vector3;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class PowerHelper {
	
	
	public static List<IPowerTile> getPowerSources(World w, BlockPos pos, int r){
		
		
		List<IPowerTile> l = getBlocksInRange(w, pos, null, null, null, true, r);
		
		
		
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
		
		BlockPos bp = getBattery(w, pos, true);
		
		if(bp == null)
			return -1;
		
		IPowerTile l = bp == null ? null : (IPowerTile)w.getTileEntity(bp);
		
		
		
		
		
		
		if(l == null)
			return -1;
		
	
		
		int total = l.getPower();
		int amountDrained = 0;
		
		
			if(amountDrain > total){
				
				if(doit)
					l.removePower(total);
				amountDrained += total;
				
				
			}else{
				
				if(doit)
					l.removePower(amountDrain);
				amountDrained += amountDrain;
				
			}
		
		//	if(amountDrained > 0 && doit)
		//		drawTorchLines(w, bl, pos, true);
		
		return amountDrained;
	}
	
public static int addPower(World w, BlockPos pos, int amountFill, int r, boolean doit){
		
	//List<BlockPos> bl = getTorches(w, pos, r, null, null);
	
	BlockPos bp = getBattery(w, pos, false);
	
	if(bp == null)
		return -1;
	
	IPowerTile l = bp == null ? null : (IPowerTile)w.getTileEntity(bp);
	
	if(l == null)
		return -1;
	
	
		
	
	
			
			
			int amountFilled= 0;
			
			
			
			
				int available = l.getMaxPower()-l.getPower();
				
				
				if(available + amountFilled <=amountFill){
					if(doit)
					l.addPower(available);
					amountFilled+=available;
					
				}else{
					if(doit)
					l.addPower(amountFill-amountFilled);
					amountFilled = amountFill;
					
					
				}
				
		
			
			
			
		
		
		
		
		
		//	if(amountFilled > 0 && doit)
		//		drawTorchLines(w, bl, pos, false);
		
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
	
	
	
	

	public static List<BlockPos> getTorches(World w, BlockPos pos, int r2, List<BlockPos> b, List<String> sb) {
		
		
		if(b == null){
			b = new ArrayList<BlockPos>();
			sb = new ArrayList<String>();
		}
		
		int r = 1;
		while(r < r2){
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
				
						
						if(PowerHelper.isBlockPowered(w, bp))
							continue;
						
						b.add(bp);
						sb.add(bp.toString());
						return getTorches(w,pos,((IPowerNode)tile).getRange(),b,sb);
						
					}
						
										
					if(tile instanceof IPowerNode){
						
						if(PowerHelper.isBlockPowered(w, bp))
							continue;
						b.add(bp);
						sb.add(bp.toString());
						
						return getTorches(w,pos,((IPowerNode)tile).getRange(),b,sb);
					
					}
					
				}
				
			}
					
		}
		
		r++;
		
	}
		
		
		return b;
	
	
	}
	

	
	public static IPowerTile getBattery(World w, List<BlockPos> bp, boolean drain){
		
	IPowerTile ret = null;
		
		if(bp == null || bp.isEmpty())
			return null;
		
		List<Integer> in = new ArrayList<Integer>();
		
		
	for(int i = 0; i< bp.size(); i++){
			
			TileEntity te = w.getTileEntity(bp.get(i));
			
		if(te ==  null)
			continue;
		
		if(te instanceof IPowerTile)
		in.add(i);
	}

	for(Integer i : in){
		
				
			IPowerTile ip =  (IPowerTile) w.getTileEntity(bp.get(i));
			
			if(drain){
				
				if(ip.getPower() <=0)
					continue;
				if(ret == null)
				ret = ip;
				else{
				if(ip.getPower() > ret.getPower())
					ret = ip;
				}
			}else{
				
				if(ip.getMaxPower() == ip.getPower())
					continue;
				if(ret == null)
				ret = ip;
				else{
					if(ip.getPower() < ret.getPower())
						ret = ip;
				}
			}
			
			
		
		
		}
		
		return ret;
		
	}
	
	public static void drawTorchLines(World w, BlockPos pos, int r, boolean drain){
		
		List<BlockPos> bp2 = PowerHelper.getTorches(w, pos, r, null, null);
		
		List<BlockPos> bp = new ArrayList<BlockPos>();
		
		bp.add(pos);
		bp.addAll(bp2);
		
		if(bp.size() == 1)
			return;
		
		
		
		for(int i = 0; i< bp.size()-1; i++){
			
			
			BlockUtils.drawLine(w, Vector3.fromBlockPos(bp.get(i)).add(0.5D), Vector3.fromBlockPos(bp.get(i+1)).add(0.5D), drain ? EnumParticleTypes.REDSTONE : EnumParticleTypes.SPELL_WITCH);
			
			
		}
	
	}
	
public static void drawTorchLines(World w, List<BlockPos> bp2, BlockPos pos, boolean drain){
		
			
		List<BlockPos> bp = new ArrayList<BlockPos>();
		
		bp.add(pos);
		bp.addAll(bp2);
		
		if(bp.size() == 1)
			return;
		
		
		
		for(int i = 0; i< bp.size()-1; i++){
			
			
			BlockUtils.drawLine(w, Vector3.fromBlockPos(bp.get(i)).add(0.5D), Vector3.fromBlockPos(bp.get(i+1)).add(0.5D), drain ? EnumParticleTypes.REDSTONE : EnumParticleTypes.SPELL_WITCH);
			
			
		}
	
	}
	

public static boolean isBlockPowered(World w, BlockPos pos){
	
		  if(w.isBlockPowered(pos))
			  return true;
		  
		  if(w.getTileEntity(pos) instanceof TileEndRune){
			  
			  TileEndRune te = (TileEndRune)w.getTileEntity(pos);
			  
			  int r = te.getRange()-3;
			  
			  for(int xD = -r;xD<=r;xD++){
					for(int yD = -r;yD<=r;yD++){
						for(int zD = -r;zD<=r;zD++){
							
							BlockPos bp = pos.add(xD, yD, zD);
							
							if(BlockUtils.isBlockPowered(w, bp))
								return true;
							
						}
					}
				}
			  
		  }
		  
		
		  
		  
		  return false;
	  }



	public static BlockPos getBattery(World w, BlockPos posS, boolean drain){
		
		List<String> bs = new ArrayList<String>();
		
		List<BlockPos> bl = new ArrayList<BlockPos>();
		
		bl.add(posS);
		
		BlockPos last = null;
		
		for(int i = 0; i< bl.size(); i++){
			
			BlockPos pos = bl.get(i);
			
			if(!bs.isEmpty() && bs.contains(pos.toLong()))
				continue;
			
			IPowerNode ip = (IPowerNode)w.getTileEntity(pos);
			
			if(PowerHelper.isBlockPowered(w, pos))
				continue;
			
			
			if(last != null)
				BlockUtils.drawLine(w, Vector3.fromBlockPos(last).add(0.5D), Vector3.fromBlockPos(pos).add(0.5D), drain ? EnumParticleTypes.REDSTONE : EnumParticleTypes.SPELL_WITCH);
			
			if(w.getTileEntity(pos) instanceof IPowerTile){
				
				IPowerTile pt  = (IPowerTile)ip;
				
				if(drain){
					
					if(pt.getPower() > 0)
						return pos;
					
				}else{
					
					if(pt.getPower() != pt.getMaxPower())
						return pos;
					
				}
					
				
				
			}
			
			
			
			
			
			
			bs.add(pos.toString());
			
			if(ip != null && ip.getLinkedBlocks() != null && !ip.getLinkedBlocks().isEmpty()){
			for(BlockPos bp : ip.getLinkedBlocks()){
				
				if(bp.toString() == pos.toString() || bs.contains(bp.toString()) || w.getTileEntity(bp)  == null || !(w.getTileEntity(bp) instanceof IPowerNode))
					continue;
				
				bl.add(bp);
				
			}
			}
			
			last = pos;
			
			
		}
		
		
		return null;
	}
	

}
