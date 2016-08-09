package com.tacosupremes.runomancy.common.power.block.tile;

import java.util.ArrayList;
import java.util.List;

import com.tacosupremes.runomancy.common.power.block.BlockPowerStorage;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;

public class TilePowerStorage extends TileEntity implements IPowerTile, ITickable{
	
	
	public int power = 0;
	
	private List<BlockPos> linkedTo = new ArrayList<BlockPos>();
	
	private void readCustomNBT(NBTTagCompound nbt) {
		
		this.power = nbt.getInteger("power");
		
		if(nbt.hasKey("BPS")){
			
			for(int i = 0; i< nbt.getCompoundTag("BPS").getSize(); i++){
				
				
				linkedTo.add(BlockPos.fromLong(nbt.getCompoundTag("BPS").getLong("B"+i)));
			}
			
			nbt.removeTag("BPS");
		}
		
	}
	
	private void writeCustomNBT(NBTTagCompound nbt) {
		
		nbt.setInteger("power", power);
		
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
        
        return super.writeToNBT(nbt);
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
			if(this.getWorld().getBlockState(getPos()).getValue(BlockPowerStorage.mode) != 1){
				this.getWorld().setBlockState(getPos(), this.getWorld().getBlockState(getPos()).withProperty(BlockPowerStorage.mode, 1));
				((TilePowerStorage)this.getWorld().getTileEntity(getPos())).addPower(power);
		}
		}
		
		if(this.power > 3000 && this.power <= 6000){
			if(this.getWorld().getBlockState(getPos()).getValue(BlockPowerStorage.mode) != 2){
				
				
				this.getWorld().setBlockState(getPos(), this.getWorld().getBlockState(getPos()).withProperty(BlockPowerStorage.mode, 2));
				((TilePowerStorage)this.getWorld().getTileEntity(getPos())).addPower(power);
			}
		}
		
		if(this.power > 6000 && this.power <= 9000){
			if(this.getWorld().getBlockState(getPos()).getValue(BlockPowerStorage.mode) != 3){
				this.getWorld().setBlockState(getPos(), this.getWorld().getBlockState(getPos()).withProperty(BlockPowerStorage.mode, 3));
				((TilePowerStorage)this.getWorld().getTileEntity(getPos())).addPower(power);
			}
		}
		
		if(this.power > 9000 && this.power <= 12000){
			if(this.getWorld().getBlockState(getPos()).getValue(BlockPowerStorage.mode) != 4){
				this.getWorld().setBlockState(getPos(), this.getWorld().getBlockState(getPos()).withProperty(BlockPowerStorage.mode, 4));
				((TilePowerStorage)this.getWorld().getTileEntity(getPos())).addPower(power);
				
			}
		}
		
		if(this.power > 12000 && this.power < 18000){
			if(this.getWorld().getBlockState(getPos()).getValue(BlockPowerStorage.mode) != 5){
				this.getWorld().setBlockState(getPos(), this.getWorld().getBlockState(getPos()).withProperty(BlockPowerStorage.mode, 5));
				((TilePowerStorage)this.getWorld().getTileEntity(getPos())).addPower(power);
				
			}
		}
		
		if(this.power >= 18000){
			if(this.getWorld().getBlockState(getPos()).getValue(BlockPowerStorage.mode) != 6){
				this.getWorld().setBlockState(getPos(), this.getWorld().getBlockState(getPos()).withProperty(BlockPowerStorage.mode, 6));
				((TilePowerStorage)this.getWorld().getTileEntity(getPos())).addPower(power);
				
			}
		}
		
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
