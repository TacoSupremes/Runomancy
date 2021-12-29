package com.tacosupremes.runomancy.common.block.tile;

import com.tacosupremes.runomancy.common.block.BlockNode;
import com.tacosupremes.runomancy.common.block.ModBlocks;
import com.tacosupremes.runomancy.common.utils.BlockUtils;
import com.tacosupremes.runomancy.common.utils.Vector3;
import net.minecraft.nbt.CompoundNBT;

import net.minecraft.particles.RedstoneParticleData;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;


import java.util.ArrayList;
import java.util.List;

public class TileNode extends TileMod implements INode, ITickableTileEntity
{
	public TileNode()
	{
		super(ModBlocks.TILE_NODE.get());
	}

	@Override
	public boolean isActiveNode() 
	{
		return !this.getWorld().isBlockPowered(this.getPos());
	}
	
	private List<BlockPos> linkedTo = new ArrayList<BlockPos>();
	private List<Boolean> linkedToDraw = new ArrayList<Boolean>();

	@Override
	public void writeCustomNBT(CompoundNBT cmp)
	{
		if(!linkedTo.isEmpty())
		{
			cmp.putInt("SIZE", linkedTo.size());
			
			for(int i = 0; i < linkedTo.size(); i++)
			{
				cmp.putLong("L" + i, linkedTo.get(i).toLong());
				cmp.putBoolean("B" + i, linkedToDraw.get(i));
			}
		}
	}

	@Override
	public void readCustomNBT(CompoundNBT cmp)
	{
		if(cmp.contains("SIZE"))
		{
			for(int i = 0; i < cmp.getInt("SIZE"); i++)
			{
				linkedTo.add(BlockPos.fromLong(cmp.getLong("L" + i)));
				linkedToDraw.add(cmp.getBoolean("B"+i));

				cmp.remove("L" + i);
				cmp.remove("B" + i);
			}
			
			cmp.remove("SIZE");
		}
	}
	
	@Override
	public List<BlockPos> getNodeList()
	{
		return this.linkedTo;
	}

	@Override
	public void tick()
	{
		spawnNodeParticles(getWorld(),this);
	}

	public static void spawnNodeParticles(World w, INode node)
	{
		List<BlockPos> linkedTo = node.getNodeList();
		List<Boolean> linkedToDraw = node.getNodeDrawList();

		for(int i = 0; i< linkedTo.size(); i++)
		{
			if(w.getTileEntity(linkedTo.get(i)) instanceof INode)
			{
				if(node.isActiveNode() && ((INode)w.getTileEntity(linkedTo.get(i))).isActiveNode() && linkedToDraw.get(i))
					BlockUtils.drawLine(w, node.getParticleOffset(), ((INode)w.getTileEntity(linkedTo.get(i))).getParticleOffset(), RedstoneParticleData.REDSTONE_DUST);
			}
			else
			{
				linkedTo.remove(i);
				break;
			}

		}
	}

	@Override
	public Vector3 getParticleOffset()
	{
		Direction enumfacing = this.getWorld().getBlockState(getPos()).get(BlockNode.FACING);
	
		double d0 = (double)pos.getX() + 0.5D;
  
		double d1 = (double)pos.getY() + 0.5D;
	    
		double d2 = (double)pos.getZ() + 0.5D;
	    
		if (enumfacing.getAxis().isHorizontal())
		{
			Direction enumfacing1 = enumfacing.getOpposite();
	        double offset = 0.15D;
	        return new Vector3(d0 + offset * (double)enumfacing1.getXOffset(), d1, d2 + offset * (double)enumfacing1.getZOffset());
		}
		else
		{
			if(enumfacing == Direction.UP)
				return new Vector3(d0, d1-0.1D, d2);
			else
				return new Vector3(d0, pos.getY() + 0.55D, d2);
	    }	
	}

	@Override
	public List<Boolean> getNodeDrawList()
	{
		return linkedToDraw;
	}
}
