package com.tacosupremes.runomancy.common.power.block.tile;

import com.tacosupremes.runomancy.common.block.tile.TileMod;
import com.tacosupremes.runomancy.common.utils.BlockUtils;
import com.tacosupremes.runomancy.common.utils.Vector3;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.List;

public class TilePowerTorch extends TileMod implements IPowerNode, ITickableTileEntity
{

	public TilePowerTorch(TileEntityType<?> tileEntityTypeIn)
	{
		super(tileEntityTypeIn);
	}

	@Override
	public int getRange() {
		
		return 7;
	}

	
	
	
	private List<BlockPos> linkedTo = new ArrayList<BlockPos>();
	
	public void readCustomNBT(CompoundNBT nbt) {
		
		
		
		if(nbt.contains("BPS")){
			
			for(int i = 0; i< nbt.getCompound("BPS").size(); i++){
				
				
				linkedTo.add(BlockPos.fromLong(nbt.getCompound("BPS").getLong("B"+i)));
			}
			
			nbt.remove("BPS");
		}
		
	}
	
	public void writeCustomNBT(CompoundNBT nbt) {
		
		
		
		if(!linkedTo.isEmpty()){
			
		CompoundNBT bt = new CompoundNBT();
		
		for(int i = 0; i<linkedTo.size(); i++){
			
			bt.putLong("B"+i, linkedTo.get(i).toLong());
			
		}
			
		nbt.put("BPS", bt);
			
		}
		
	}
	


	@Override
	public List<BlockPos> getLinkedBlocks() {
	
		
		return linkedTo;
		
	}

	@Override
	public void updateLinkedBlocks(BlockPos bp) {
		
		for(int i =0; i< linkedTo.size(); i++){
			
			if(linkedTo.get(i).toString().equals(bp.toString()))
				this.linkedTo.remove(i);
			
		}
		
		
	}

	@Override
	public void tick() {
		
		for(BlockPos bp : linkedTo){			
			
			BlockUtils.drawLine(getWorld(), Vector3.fromBlockPos(getPos()).add(0.5), Vector3.fromBlockPos(bp).add(0.5), ParticleTypes.DRAGON_BREATH);
		}
		
		for(int i =0; i< linkedTo.size(); i++){
			
			if(this.getWorld().getTileEntity(linkedTo.get(i)) == null || this.getWorld().getTileEntity(linkedTo.get(i)) == this)
				linkedTo.remove(i);
			
		}
		
		List<String> s = new ArrayList<String>();
		
		for(int i =0; i< linkedTo.size(); i++){
			
			if(s.contains(linkedTo.get(i).toString())){
				linkedTo.remove(i);
				continue;
			}
			
			s.add(linkedTo.get(i).toString());
			
			
			
		}
		
		
	}
	
}
