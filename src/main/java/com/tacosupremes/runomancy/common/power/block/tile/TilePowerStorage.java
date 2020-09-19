package com.tacosupremes.runomancy.common.power.block.tile;

import com.tacosupremes.runomancy.common.block.ModBlocks;
import com.tacosupremes.runomancy.common.block.tile.TileMod;
import com.tacosupremes.runomancy.common.utils.Vector3;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.particles.RedstoneParticleData;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.List;

public class TilePowerStorage extends TileMod implements IPowerTile, ITickableTileEntity
{
	
	public int power = 0;

	private List<BlockPos> linkedTo = new ArrayList<BlockPos>();

	public TilePowerStorage()
	{
		super(ModBlocks.TILE_BATTERY.get());
	}

	public TilePowerStorage(TileEntityType<?> tileEntityTypeIn)
	{
		super(tileEntityTypeIn);
	}

	private static Vector3 offset = new Vector3(0.5,0.35D,0.5);

	public void readCustomNBT(CompoundNBT nbt) {
		
		this.power = nbt.getInt("power");
		
		if(nbt.contains("BPS")){
			
			for(int i = 0; i< nbt.getCompound("BPS").size(); i++){
				
				
				linkedTo.add(BlockPos.fromLong(nbt.getCompound("BPS").getLong("B"+i)));
			}
			
			nbt.remove("BPS");
		}
		
	}
	
	public void writeCustomNBT(CompoundNBT nbt) {
		
		nbt.putInt("power", power);
		
		if(!linkedTo.isEmpty()){
			
		CompoundNBT bt = new CompoundNBT();
		
		for(int i = 0; i<linkedTo.size(); i++){
			
			bt.putLong("B"+i, linkedTo.get(i).toLong());
			
		}
			
		nbt.put("BPS", bt);

		}
		
	}
	
	
	@Override
	public int getPower() {
		
		return power;
	}

	@Override
	public int addPower(int i) {
		power += i;
		return power;
	}

	@Override
	public int removePower(int i) {
		
		return addPower(-i);
	}



	@Override
	public int getMaxPower() {
	
		return 18000;
	}

	

	@Override
	public void tick()
	{

		if(this.getPower() > this.getMaxPower())
			this.power = this.getMaxPower();
		

		power++;
		//System.out.println(this.getPower());
		
		
			
		
		if(this.power > 0 && this.power <= 6000){
			
			this.getWorld().addParticle(RedstoneParticleData.REDSTONE_DUST, this.getPos().getX()+0.5D, this.getPos().getY()+0.45D, this.getPos().getZ()+0.5D, 0, 0, 0);
			
		
		}
		
		
		if(this.power > 6000 && this.power < 12000){
			
			this.getWorld().addParticle(ParticleTypes.WITCH, this.getPos().getX()+0.5D, this.getPos().getY()+0.45D, this.getPos().getZ()+0.5D, 0, 0, 0);
			
		}
		
		
		if(this.power >= 12000){
			
			this.getWorld().addParticle(ParticleTypes.HAPPY_VILLAGER, this.getPos().getX()+0.5D, this.getPos().getY()+0.45D, this.getPos().getZ()+0.5D, 0, 0, 0);
			
			
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

	/*
	@Override
	public List<BlockPos> getLinkedBlocks() {
		
		
		return linkedTo;
		
	}

	@Override
	public int getRange() {

		return 5;
	}

	@Override
	public void updateLinkedBlocks(BlockPos bp) {
		
		for(int i =0; i< linkedTo.size(); i++){
			
			if(linkedTo.get(i).toString().equals(bp.toString()))
				this.linkedTo.remove(i);
			
		}
		
		
	}
*/

	@Override
	public boolean isActiveNode()
	{
		return !getWorld().isBlockPowered(getPos());
	}

	@Override
	public List<BlockPos> getNodeList()
	{
		return linkedTo;
	}

	@Override
	public Vector3 getParticleOffset()
	{
		return Vector3.fromBlockPos(getPos()).add(offset);
	}
}
