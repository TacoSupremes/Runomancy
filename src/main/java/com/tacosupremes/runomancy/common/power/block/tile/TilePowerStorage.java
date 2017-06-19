package com.tacosupremes.runomancy.common.power.block.tile;

import java.util.ArrayList;
import java.util.List;

import com.tacosupremes.runomancy.common.block.tile.TileMod;
import com.tacosupremes.runomancy.common.power.block.BlockPowerStorage;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;

public class TilePowerStorage extends TileMod implements IPowerTile, ITickable{
	
	
	public int power = 0;
	
	
	
	private List<BlockPos> linkedTo = new ArrayList<BlockPos>();
	
	public void readCustomNBT(NBTTagCompound nbt) {
		
		this.power = nbt.getInteger("power");
		
		if(nbt.hasKey("BPS")){
			
			for(int i = 0; i< nbt.getCompoundTag("BPS").getSize(); i++){
				
				
				linkedTo.add(BlockPos.fromLong(nbt.getCompoundTag("BPS").getLong("B"+i)));
			}
			
			nbt.removeTag("BPS");
		}
		
	}
	
	public void writeCustomNBT(NBTTagCompound nbt) {
		
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
		
	
		
		if(this.getPower() > this.getMaxPower())
			this.power = this.getMaxPower();
		
		
		//System.out.println(this.getPower());
		
		
			
		
		if(this.power > 0 && this.power <= 6000){
			
			this.getWorld().spawnParticle(EnumParticleTypes.REDSTONE, this.getPos().getX()+0.5D, this.getPos().getY()+0.2D, this.getPos().getZ()+0.5D, 0, 0, 0);
			
		
		}
		
		
		if(this.power > 6000 && this.power < 12000){
			
			this.getWorld().spawnParticle(EnumParticleTypes.SPELL_WITCH, this.getPos().getX()+0.5D, this.getPos().getY()+0.2D, this.getPos().getZ()+0.5D, 0, 0, 0);
			
		}
		
		
		if(this.power >= 12000){
			
			this.getWorld().spawnParticle(EnumParticleTypes.VILLAGER_HAPPY, this.getPos().getX()+0.5D, this.getPos().getY()+0.2D, this.getPos().getZ()+0.5D, 0, 0, 0);
			
			
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
	
	
	

}
