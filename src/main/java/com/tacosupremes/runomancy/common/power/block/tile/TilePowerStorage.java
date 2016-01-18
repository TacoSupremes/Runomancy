package com.tacosupremes.runomancy.common.power.block.tile;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class TilePowerStorage extends TileEntity implements IPowerTile{
	
	
	int power = 0;
	
	private void readCustomNBT(NBTTagCompound nbt) {
		
		this.power = nbt.getInteger("power");
		
	}
	
	private void writeCustomNBT(NBTTagCompound nbt) {
		
		nbt.setInteger("power", power);
		
	}
	
	@Override
    public void writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);
        writeCustomNBT(nbt);
    }
	
	@Override
	public void readFromNBT(NBTTagCompound nbt)
    {
        super.readFromNBT(nbt);
        readCustomNBT(nbt);
    }
	
	@Override
	public Packet getDescriptionPacket() {
		NBTTagCompound nbt = new NBTTagCompound();
        this.writeCustomNBT(nbt);
        return new S35PacketUpdateTileEntity(this.getPos(), -999, nbt);
	}

	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
		super.onDataPacket(net, pkt);		
		this.readCustomNBT(pkt.getNbtCompound());
	}

	@Override
	public int getPower() {
		
		return power;
	}

	@Override
	public int addPower(int i) {
		power+=i;
		return power;
	}

	@Override
	public int removePower(int i) {
		
		return addPower(-i);
	}

	@Override
	public int getRange() {
		
		return 5;
	}

	@Override
	public int getMaxPower() {
	
		return 20000;
	}
	
	

}
