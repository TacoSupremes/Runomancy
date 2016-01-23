package com.tacosupremes.runomancy.common.block.rune.tile;

import java.util.ArrayList;
import java.util.List;

import com.tacosupremes.runomancy.common.block.rune.IRune;
import com.tacosupremes.runomancy.common.power.PowerHelper;
import com.tacosupremes.runomancy.common.power.block.tile.IPowerTile;
import com.tacosupremes.runomancy.common.runelogic.IFunctionalRuneEffect;
import com.tacosupremes.runomancy.common.runelogic.IRuneEffect;
import com.tacosupremes.runomancy.common.runelogic.RuneFormations;
import com.tacosupremes.runomancy.common.utils.BlockUtils;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ITickable;
import net.minecraft.util.Vec3i;

public class TileEndRune extends TileEntity implements ITickable{

	
	int currentEffect = -1;
	public int power = 0;
	
	public NBTTagCompound rEffect = null;
	
	public List<BlockPos> children = new ArrayList<BlockPos>();
	
	public int ticks = 0;
	
	

	
	public void readCustomNBT(NBTTagCompound nbt)
    {
		for(int i =0; i<nbt.getInteger("LENGTH");i++){
		children.add(BlockPos.fromLong(nbt.getCompoundTag("children").getLong("L"+i)));
			
		}
		
		nbt.removeTag("children");
		this.currentEffect = nbt.getInteger("effect");
		this.power = nbt.getInteger("power");
		
		this.rEffect = nbt.getCompoundTag("NBTE");
		
		
    }

	
	
	public void writeCustomNBT(NBTTagCompound nbt)
    {
		NBTTagCompound n = new NBTTagCompound();
		nbt.setInteger("LENGTH", children.size());
		for(int i =0; i<children.size();i++){
			n.setLong("L"+i, children.get(i).toLong());
		}
		nbt.setTag("children", n);
		nbt.setInteger("effect", currentEffect);
		nbt.setInteger("power", power);
		nbt.setTag("NBTE", this.rEffect);
		
    }
	
	
	@Override
	public void update() {
		
		ticks++;
		
		if(rEffect == null)
			rEffect = new NBTTagCompound();
		
		
		
		if(!isFormed()){
			effect:
			for(IRuneEffect re : RuneFormations.effects){
				
				
				int sr = Math.round((float)((float)Math.sqrt(re.getNeededBlocks().length)/2F))-1;
				
				int index = 0;
				
			
					for(int zD = -sr;zD<=sr; zD++){
						for(int xD = -sr;xD<=sr; xD++){
						
						BlockPos bp = new BlockPos(this.getPos().add(new BlockPos(xD, 0, zD)));
				
						
						if(this.getWorld().getBlockState(bp).getBlock() == re.getNeededBlocks()[index]){
							index++;
						}else if(re.getNeededBlocks()[index] == null)
							index++;
						else{
							index = 0;
							continue effect;
						}
						
						if(index == re.getNeededBlocks().length){
							
							this.currentEffect = RuneFormations.effects.indexOf(re);
							int i2 = 0;
							
								for(int zD2 = -sr;zD2<=sr; zD2++){
									for(int xD2 = -sr;xD2<=sr; xD2++){	
									BlockPos bp2 = new BlockPos(this.getPos().add(new BlockPos(xD2, 0, zD2)));
									if(re.getNeededBlocks()[i2] == null){
										i2++;
										continue;
									}
									IBlockState state = ((IRune)this.getWorld().getBlockState(bp2).getBlock()).getStateWithMode(this.getWorld().getBlockState(bp2), re.getFinalBlockStates()[i2]);
									this.getWorld().setBlockState(bp2, state);
									children.add(bp2);
									i2++;
									
									
								}
								}
							break effect;
						}
						
					}
					}
					
				
				
			}
			
			
			
		}else{
			
			if(shouldDestroy()){
				destroy();
				this.currentEffect = -1;
				return;
			}
			
			if(!this.getEffect().isGenerating()){
				
				if(this.power < this.getEffect().getPowerCapacity()){
				
					if(PowerHelper.getTotalAvailablePower(this.getWorld(), getPos(), RuneFormations.getRange(getEffect())+3) > 0){
					
						if(((double)(this.power)/((double)(this.getEffect().getPowerCapacity())) < 0.25D)){
							
							this.power += PowerHelper.drainPower(this.getWorld(), pos, this.getEffect().getTransferRate(), RuneFormations.getRange(getEffect())+3, true);
					
						}else{
						
							this.power += PowerHelper.drainPower(this.getWorld(), pos, this.getEffect().getTransferRate()/2, RuneFormations.getRange(getEffect())+3, true);
							
						}
						
						}
					
					
						
					}
					
				}else{
					
					if(this.power > 0 && PowerHelper.addPower(this.getWorld(), pos, this.getEffect().getTransferRate(), RuneFormations.getRange(getEffect())+3, false)!=-1){
						this.power-=PowerHelper.addPower(this.getWorld(), pos, this.getEffect().getTransferRate(), RuneFormations.getRange(getEffect())+3, true);
					}
					
					
				}
			
			
			
		RuneFormations.effects.get(currentEffect).doEffect(this.getWorld(), this.getPos(), this, this.rEffect);
		
		
		}
		
	}
	
	
	public boolean shouldDestroy(){
		
		
			IRuneEffect re = RuneFormations.effects.get(currentEffect);
			
			int sr = Math.round((float)((float)Math.sqrt(re.getNeededBlocks().length)/2F))-1;
			
			int index = 0;
			
		
				for(int zD = -sr;zD<=sr; zD++){
					for(int xD = -sr;xD<=sr; xD++){
					
					BlockPos bp = new BlockPos(this.getPos().add(new BlockPos(xD, 0, zD)));
				
					
					if(this.getWorld().getBlockState(bp).getBlock() == re.getNeededBlocks()[index] &&((IRune)this.getWorld().getBlockState(bp).getBlock()).getMetaFromState(this.getWorld().getBlockState(bp)) == re.getFinalBlockStates()[index]){
						index++;
					}else if(re.getNeededBlocks()[index] == null)
					index++;
					else{
						index = 0;
						return true;
					}
					
					if(index == re.getNeededBlocks().length)
						return false;
					
					
				}
				}
				
			
			return true;
		
	}
	
	public void destroy(){
		
		
		
		for(BlockPos b : children){
			
			this.getWorld().setBlockState(b, this.getWorld().getBlockState(b).getBlock().getDefaultState());
			
			
		}
		
		children.removeAll(children);
		
	}
	
	public boolean isFormed(){
	
		return currentEffect != -1;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt)
    {
        super.readFromNBT(nbt);
        readCustomNBT(nbt);
    }
	@Override
    public void writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);
        writeCustomNBT(nbt);
    }
	
	//Client Packet stuff
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

	
	public IRuneEffect getEffect(){
		
		if(currentEffect < 0)
			return null;
		
		return RuneFormations.effects.get(currentEffect);
	}

}
