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
import java.util.Random;

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

	private static Vector3 offset = new Vector3(0.5,0.95D,0.5);

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
	public int addPower(int i)
	{
		if(!canFill())
			return 0;

		//System.out.print("\nadding power to battery" + getPower() + "--->");

		if(power + i > getMaxPower())
		{
			int res = getMaxPower() - power;

			power = getMaxPower();

		//	System.out.print(power);

			getWorld().addBlockEvent(getPos(), ModBlocks.BATTERY.get(), 0, power);

			return res;
		}
		else
			power += i;

		//System.out.print(power);

		getWorld().addBlockEvent(getPos(), ModBlocks.BATTERY.get(), 0, power);
		return i;
	}



	@Override
	public int removePower(int i)
	{
		if (power <= 0)
			return 0;

		//System.out.print("\nremoving power from bat" + getPower() + "--->");

		if(power < i)
		{
			int t = power;
			power = 0;
		//	System.out.print(0);
			getWorld().addBlockEvent(getPos(), ModBlocks.BATTERY.get(), 0, power);
			return t;
		}

		power -= i;

	//	System.out.print(power);
		getWorld().addBlockEvent(getPos(), ModBlocks.BATTERY.get(), 0, power);

		return i;
	}



	@Override
	public int getMaxPower()
	{
		return 16000;
	}


	public void spawnEnergy()
	{
		Random rand = this.getWorld().getRandom();

		int level = ((int)Math.floor((double)power / getMaxPower())) * 5 + 1;


		for(int j = 0; j < level; j++)
		for(int i = 0; i < 4; i++)
			this.getWorld().addParticle(ParticleTypes.WITCH, this.getPos().getX()+0.5D+ rand.nextGaussian() / 8.5D, this.getPos().getY()+0.15D+j*0.1, this.getPos().getZ()+0.5D+ rand.nextGaussian() / 8.5D, 0, -1, 0);

	}


	@Override
	public void tick()
	{
		if(this.getPower() > this.getMaxPower())
			this.power = this.getMaxPower();

		//power += getMaxPower() / 15 / 20;
		//System.out.println(this.getPower());

		//spawnEnergy();



		for(int i =0; i< linkedTo.size(); i++)
		{
			if(this.getWorld().getTileEntity(linkedTo.get(i)) == null || this.getWorld().getTileEntity(linkedTo.get(i)) == this)
				linkedTo.remove(i);
		}

		/*
		List<String> s = new ArrayList<String>();
		
		for(int i =0; i< linkedTo.size(); i++){
			
			if(s.contains(linkedTo.get(i).toString())){
				linkedTo.remove(i);
				continue;
			}
			
			s.add(linkedTo.get(i).toString());
			
			
			
		}
		*/
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

	@Override
	public boolean canFill()
	{
		return power < getMaxPower();
	}

	@Override
	public boolean receiveClientEvent(int id, int type)
	{
		if(!getWorld().isRemote())
			return true;

		this.power = type;
		return false;
	}
}
