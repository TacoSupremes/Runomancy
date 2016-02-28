package com.tacosupremes.runomancy.common.utils;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
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
public static MovingObjectPosition raytraceEntityPlayerLook(EntityPlayer player, float range) {
    Vec3 eye = new Vec3(player.posX, player.posY + (double)player.getEyeHeight(), player.posZ); // Entity.getPositionEyes
    Vec3 look = player.getLook(1.0f);

    return raytraceEntity(player, eye, look, range, true);
  }

  // based on EntityRenderer.getMouseOver
  public static MovingObjectPosition raytraceEntity(Entity entity, Vec3 start, Vec3 look, double range, boolean ignoreCanBeCollidedWith) {
    //Vec3 look = entity.getLook(partialTicks);
    Vec3 direction = start.addVector(look.xCoord * range, look.yCoord * range, look.zCoord * range);

    //Vec3 direction = vec3.addVector(vec31.xCoord * d0, vec31.yCoord * d0, vec31.zCoord * d0);
    Entity pointedEntity = null;
    Vec3 hit = null;
    AxisAlignedBB bb = entity.getEntityBoundingBox().addCoord(look.xCoord * range, look.yCoord * range, look.zCoord * range).expand(1,1,1);
    @SuppressWarnings("unchecked")
    List<Entity> entitiesInArea = entity.worldObj.getEntitiesWithinAABBExcludingEntity(entity, bb);
    double range2 = range; // range to the current candidate. Used to find the closest entity.

    for(Entity candidate : entitiesInArea)
    {
      if (ignoreCanBeCollidedWith || candidate.canBeCollidedWith())
      {
        // does our vector go through the entity?
        double colBorder = candidate.getCollisionBorderSize();
        AxisAlignedBB entityBB = candidate.getEntityBoundingBox().expand(colBorder, colBorder, colBorder);
        MovingObjectPosition movingobjectposition = entityBB.calculateIntercept(start, direction);

        // needs special casing: vector starts inside the entity
        if (entityBB.isVecInside(start))
        {
          if (0.0D < range2 || range2 == 0.0D)
          {
            pointedEntity = candidate;
            hit = movingobjectposition == null ? start : movingobjectposition.hitVec;
            range2 = 0.0D;
          }
        }
        else if (movingobjectposition != null)
        {
          double dist = start.distanceTo(movingobjectposition.hitVec);

          if (dist < range2 || range2 == 0.0D)
          {
            if (candidate == entity.ridingEntity && !entity.canRiderInteract())
            {
              if (range2 == 0.0D)
              {
                pointedEntity = candidate;
                hit = movingobjectposition.hitVec;
              }
            }
            else
            {
              pointedEntity = candidate;
              hit = movingobjectposition.hitVec;
              range2 = dist;
            }
          }
        }
      }
    }

    if (pointedEntity != null && range2 < range)
    {
      return new MovingObjectPosition(pointedEntity, hit);
    }
    return null;
  }




}
