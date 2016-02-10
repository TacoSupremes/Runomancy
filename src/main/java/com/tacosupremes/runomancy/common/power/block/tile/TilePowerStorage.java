package com.tacosupremes.runomancy.common.power.block.tile;

import com.tacosupremes.runomancy.common.power.block.BlockPowerStorage;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.world.World;

public class TilePowerStorage extends TileEntity implements IPowerTile, ITickable{
	
	
	public int power = 0;
	
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
	
		return 18000;
	}

	

	@Override
	public void update() {
		
		if(this.worldObj.isRemote)
			return;
		
		if(this.getPower() > this.getMaxPower())
			this.power = this.getMaxPower();
		
		
		System.out.println(this.getPower());
		
		if(this.power == 0){
			if(this.getWorld().getBlockState(getPos()).getValue(BlockPowerStorage.mode) != 0)
				this.getWorld().setBlockState(getPos(), this.getWorld().getBlockState(getPos()).withProperty(BlockPowerStorage.mode, 0));
		}
		
		if(this.power > 0 && this.power <= 3000){
			if(this.getWorld().getBlockState(getPos()).getValue(BlockPowerStorage.mode) != 1)
				this.getWorld().setBlockState(getPos(), this.getWorld().getBlockState(getPos()).withProperty(BlockPowerStorage.mode, 1));
		}
		
		if(this.power > 3000 && this.power <= 6000){
			if(this.getWorld().getBlockState(getPos()).getValue(BlockPowerStorage.mode) != 2){
				
				int pc = this.power;
				this.getWorld().setBlockState(getPos(), this.getWorld().getBlockState(getPos()).withProperty(BlockPowerStorage.mode, 2));
				((TilePowerStorage)this.getWorld().getTileEntity(getPos())).power = pc;
			}
		}
		
		if(this.power > 6000 && this.power <= 9000){
			if(this.getWorld().getBlockState(getPos()).getValue(BlockPowerStorage.mode) != 3)
				this.getWorld().setBlockState(getPos(), this.getWorld().getBlockState(getPos()).withProperty(BlockPowerStorage.mode, 3));
		}
		
		if(this.power > 9000 && this.power <= 12000){
			if(this.getWorld().getBlockState(getPos()).getValue(BlockPowerStorage.mode) != 4)
				this.getWorld().setBlockState(getPos(), this.getWorld().getBlockState(getPos()).withProperty(BlockPowerStorage.mode, 4));
		}
		
		if(this.power > 12000 && this.power <= 15000){
			if(this.getWorld().getBlockState(getPos()).getValue(BlockPowerStorage.mode) != 5)
				this.getWorld().setBlockState(getPos(), this.getWorld().getBlockState(getPos()).withProperty(BlockPowerStorage.mode, 5));
		}
		
		if(this.power > 15000 && this.power <= 18000){
			if(this.getWorld().getBlockState(getPos()).getValue(BlockPowerStorage.mode) != 6)
				this.getWorld().setBlockState(getPos(), this.getWorld().getBlockState(getPos()).withProperty(BlockPowerStorage.mode, 6));
		}
		
		
	}
	
	

}
