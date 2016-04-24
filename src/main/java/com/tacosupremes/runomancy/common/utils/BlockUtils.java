package com.tacosupremes.runomancy.common.utils;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
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
public static IBlockState fromItemStack(ItemStack is){
	
	Block b = Block.getBlockFromItem(is.getItem());
	if(b == null)
		return null;
	
	
	return b.getStateFromMeta(is.getItemDamage());
	
}

public static ItemStack toItemStack(IBlockState s){
	

	return new ItemStack(s.getBlock(),1,s.getBlock().getMetaFromState(s));
}

public static int getMeta(World w, BlockPos pos){
	
	return w.getBlockState(pos).getBlock().getMetaFromState(w.getBlockState(pos));
	
}
  
  public static List<BlockPos> getConnectedBlocks(World w, BlockPos pos, int r, IBlockState ib){
		
	  return getConnectedBlocks(w, pos, null, null, true, r, ib);
  }
  
  private static List<BlockPos> getConnectedBlocks(World w, BlockPos pos, List<BlockPos> b, List<String> s, boolean firstTime, int r, IBlockState ib){
		
		
		if(firstTime){
			
			List<BlockPos> b2 = new ArrayList<BlockPos>();
			List<String> s2 = new ArrayList<String>();
			
			for(int xD = -r;xD<=r;xD++){
				for(int yD = -r;yD<=r;yD++){
					for(int zD = -r;zD<=r;zD++){
						
						if(xD==0 &&yD==0&&zD==0)
							continue;
						
						BlockPos bp = pos.add(xD, yD, zD);
					
						
						if(w.getBlockState(bp).getBlock() == ib.getBlock() && ib.getBlock().getMetaFromState(ib) == w.getBlockState(bp).getBlock().getMetaFromState(w.getBlockState(bp))){
							
							
							b2.add(bp);
							s2.add(bp.toString());
							continue;
						}
						
						
						
						
						
						
						
				}
				
				}
			}
				
			return getConnectedBlocks(w, pos, b2, s2, false, r, ib);
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
						
							
							if(s.contains(bp.toString()))
								continue;
							
					
							if(w.getBlockState(bp).getBlock() == ib.getBlock() && ib.getBlock().getMetaFromState(ib) == w.getBlockState(bp).getBlock().getMetaFromState(w.getBlockState(bp))){
								
								
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
					return getConnectedBlocks(w, pos, b, s, false, r, ib);
				
			}
		
		return b;
		
	}

  public static List<BlockPos> getConnectedLogs(World w, BlockPos pos, int r){
		
	  return getConnectedLogs(w, pos, null, null, true, r);
  }
  
  private static List<BlockPos> getConnectedLogs(World w, BlockPos pos, List<BlockPos> b, List<String> s, boolean firstTime, int r){
		
		
		if(firstTime){
			
			List<BlockPos> b2 = new ArrayList<BlockPos>();
			List<String> s2 = new ArrayList<String>();
			
			for(int xD = -r;xD<=r;xD++){
				for(int yD = -r;yD<=r;yD++){
					for(int zD = -r;zD<=r;zD++){
						
						if(xD==0 &&yD==0&&zD==0)
							continue;
						
						BlockPos bp = pos.add(xD, yD, zD);
					
						
						if(w.getBlockState(bp).getBlock().isWood(w,bp)){
							
							
							b2.add(bp);
							s2.add(bp.toString());
							continue;
						}
						
						
						
						
						
						
						
				}
				
				}
			}
				
			return getConnectedLogs(w, pos, b2, s2, false, r);
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
						
							
							if(s.contains(bp.toString()))
								continue;
							
					
							if(w.getBlockState(bp).getBlock().isWood(w,bp)){
										
								
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
					return getConnectedLogs(w, pos, b, s, false, r);
				
				
				
			}
			
			
		
		
		
		return b;
		
	}
  
  
 
  
  public static void drawLine(World w, Vector3 start, Vector3 end, EnumParticleTypes type){
	  
	  double i = 0;
	  
	  
	 double vectorLength = Math.sqrt(Math.pow((start.getX()-end.getX()),2)+Math.pow((start.getY()-end.getY()),2)+Math.pow((start.getZ()-end.getZ()),2));
	 
	 while (i <= vectorLength){
		 
		 double xD = end.getX() - start.getX();
		 double yD = end.getY() - start.getY();
		 double zD = end.getZ() - start.getZ();
		 
		 double dL = i / vectorLength;
		 
		 xD *= dL;
		 yD *= dL;
		 zD *= dL;
		
		 w.spawnParticle(type, start.getX()+xD,start.getY()+yD,start.getZ()+zD, 0, 0, 0, 0);
			 
		 
		 i++;
	 }
	  
	  
	  
  }
  
  public static boolean isBlockPowered(World w, BlockPos pos){
	  
	
		  return w.isBlockPowered(pos);
	  

  }
  
  public static List<BlockPos> getBlocksInRadius(BlockPos bp, int r, boolean cube){
	  
	  List<BlockPos> l = new ArrayList<BlockPos>();
	  
	  for(int i = 1; i<= r; i++){
		  l.add(bp.add(r, cube ? r : 0, r));
		  l.add(bp.add(-r, cube ? -r : 0, -r));
		  l.add(bp.add(r, cube ? r : 0, r));
		  l.add(bp.add(r, cube ? r : 0, r));
	  }
	  
	  return l;
  }


}
