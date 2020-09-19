package com.tacosupremes.runomancy.common.block.tile;

import java.util.List;

import com.tacosupremes.runomancy.common.utils.Vector3;
import net.minecraft.util.math.BlockPos;

public interface INode 
{
	
	public boolean isActiveNode();
	
	public List<BlockPos> getNodeList();
	
	public Vector3 getParticleOffset();

}
