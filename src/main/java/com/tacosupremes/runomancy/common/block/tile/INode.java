package com.tacosupremes.runomancy.common.block.tile;

import java.util.List;

import com.tacosupremes.runomancy.common.utils.Vector3;
import net.minecraft.util.math.BlockPos;

public interface INode 
{
	boolean isActiveNode();
	
	List<BlockPos> getNodeList();

	List<Boolean> getNodeDrawList();
	
	Vector3 getParticleOffset();
}
