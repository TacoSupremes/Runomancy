package com.tacosupremes.runomancy.common.power.block.tile;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

public class TilePowerTorch extends TileEntity implements IPowerNode{

	@Override
	public int getRange() {
		
		return 7;
	}

	
	private List<BlockPos> linkedTo = new ArrayList<BlockPos>();
	
	private void readCustomNBT(NBTTagCompound nbt) {
		
		
		
		if(nbt.hasKey("BPS")){
			
			for(int i = 0; i< nbt.getCompoundTag("BPS").getSize(); i++){
				
				
				linkedTo.add(BlockPos.fromLong(nbt.getCompoundTag("BPS").getLong("B"+i)));
			}
			
			nbt.removeTag("BPS");
		}
		
	}
	
	private void writeCustomNBT(NBTTagCompound nbt) {
		
		
		
		if(!linkedTo.isEmpty()){
			
		NBTTagCompound bt = new NBTTagCompound();
		
		for(int i = 0; i<linkedTo.size(); i++){
			
			bt.setLong("B"+i, linkedTo.get(i).toLong());
			
		}
			
		nbt.setTag("BPS", bt);
			
		}
		
	}
	
	@Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt)
    {
       
        writeCustomNBT(nbt);
        return  super.writeToNBT(nbt);
    }
	
	@Override
	public void readFromNBT(NBTTagCompound nbt)
    {
        super.readFromNBT(nbt);
        readCustomNBT(nbt);
    }
	
	@Override
	public SPacketUpdateTileEntity getUpdatePacket() {
		
		NBTTagCompound nbt = new NBTTagCompound();
        this.writeCustomNBT(nbt);
        return new SPacketUpdateTileEntity(this.getPos(), -999, nbt);
		
	}

	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
		super.onDataPacket(net, pkt);		
		this.readCustomNBT(pkt.getNbtCompound());
	}

	@Override
	public List<BlockPos> getLinkedBlocks() {
	
		
		return linkedTo;
		
	}

	@Override
	public void updateLinkedBlocks() {
		
		for(int i =0; i< linkedTo.size(); i++){
			
			if(this.getWorld().getTileEntity(linkedTo.get(i)) == null)
				this.linkedTo.remove(i);
			
		}
		
		
	}
	
}
