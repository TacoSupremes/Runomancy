package com.tacosupremes.runomancy.common.power.block.tile;

import java.util.ArrayList;
import java.util.List;

import com.tacosupremes.runomancy.common.block.tile.TileMod;
import com.tacosupremes.runomancy.common.utils.BlockUtils;
import com.tacosupremes.runomancy.common.utils.Vector3;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;

public class TilePowerTorch extends TileMod implements IPowerNode, ITickable{

	@Override
	public int getRange() {
		
		return 7;
	}

	
	
	
	private List<BlockPos> linkedTo = new ArrayList<BlockPos>();
	
	public void readCustomNBT(NBTTagCompound nbt) {
		
		
		
		if(nbt.hasKey("BPS")){
			
			for(int i = 0; i< nbt.getCompoundTag("BPS").getSize(); i++){
				
				
				linkedTo.add(BlockPos.fromLong(nbt.getCompoundTag("BPS").getLong("B"+i)));
			}
			
			nbt.removeTag("BPS");
		}
		
	}
	
	public void writeCustomNBT(NBTTagCompound nbt) {
		
		
		
		if(!linkedTo.isEmpty()){
			
		NBTTagCompound bt = new NBTTagCompound();
		
		for(int i = 0; i<linkedTo.size(); i++){
			
			bt.setLong("B"+i, linkedTo.get(i).toLong());
			
		}
			
		nbt.setTag("BPS", bt);
			
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
	public void update() {
		
		for(BlockPos bp : linkedTo){			
			
			BlockUtils.drawLine(getWorld(), Vector3.fromBlockPos(getPos()).add(0.5), Vector3.fromBlockPos(bp).add(0.5), EnumParticleTypes.DRAGON_BREATH);
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
