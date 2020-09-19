package com.tacosupremes.runomancy.common.block.rune.tile;

import java.util.ArrayList;
import java.util.List;

import com.tacosupremes.runomancy.common.block.ModBlocks;
import com.tacosupremes.runomancy.common.block.rune.IRune;
import com.tacosupremes.runomancy.common.block.tile.TileMod;
import com.tacosupremes.runomancy.common.power.PowerHelper;
import com.tacosupremes.runomancy.common.power.block.tile.IPowerNode;
import com.tacosupremes.runomancy.common.runelogic.IRuneEffect;
import com.tacosupremes.runomancy.common.runelogic.RuneEffectRepair;
import com.tacosupremes.runomancy.common.runelogic.RuneFormations;

import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.BlockPos;

public class TileEndRune extends TileMod implements IPowerNode, ITickableTileEntity
{
	int currentEffect = -1;
	public int power = 0;
	
	public CompoundNBT rEffect = null;
	
	public List<BlockPos> children = new ArrayList<BlockPos>();
	
	public int ticks = 0;
	
	private List<BlockPos> l = new ArrayList<BlockPos>();

    public TileEndRune()
    {
        super(ModBlocks.TILE_END_RUNE.get());
    }

    public void readCustomNBT(CompoundNBT nbt)
    {
		for(int i =0; i<nbt.getInt("LENGTH");i++){
		children.add(BlockPos.fromLong(nbt.getCompound("children").getLong("L"+i)));
			
		}



		nbt.remove("children");
		this.currentEffect = nbt.getInt("effect");
		this.power = nbt.getInt("power");
		
		this.rEffect = nbt.getCompound("NBTE");
		
		for(int i =0; i<nbt.getCompound("CC").size(); i++)
			l.add(BlockPos.fromLong(nbt.getCompound("CC").getLong("L"+i)));

		nbt.remove("CC");
		
    }

	
	
	public void writeCustomNBT(CompoundNBT nbt)
    {
		CompoundNBT n = new CompoundNBT();
		nbt.putInt("LENGTH", children.size());
		for(int i =0; i<children.size();i++){
			n.putLong("L"+i, children.get(i).toLong());
		}
		nbt.put("children", n);
		nbt.putInt("effect", currentEffect);
		nbt.putInt("power", power);
		if(rEffect != null)
			nbt.put("NBTE", this.rEffect);
		CompoundNBT nn = new CompoundNBT();
		for(int i = 0; i< l.size(); i++){
			nn.putLong("L" + i, l.get(i).toLong());
			}
		
		nbt.put("CC", nn);
		
    }
	
	
	@Override
	public void tick()
	{
		ticks++;

		if(rEffect == null)
			rEffect = new CompoundNBT();
		

		if(!isFormed()){
			effect:
			for(IRuneEffect re : RuneFormations.effects)
			{

				//System.out.println(re.getName());
				
				int sr = Math.round((float)((float)Math.sqrt(re.getNeededBlocks().length)/2F))-1;
				
				int index = 0;
				
			
					for(int zD = -sr;zD<=sr; zD++){
						for(int xD = -sr;xD<=sr; xD++){
						
						BlockPos bp = new BlockPos(this.getPos().add(new BlockPos(xD, 0, zD)));

						BlockState state2 = this.getWorld().getBlockState(bp);

					//	System.out.println(this.getWorld().getBlockState(bp).getBlock().getRegistryName().toString());

						if(this.getWorld().getBlockState(bp).getBlock() == re.getNeededBlocks()[index] && ((IRune)state2.getBlock()).getModeFromState(state2) == 0){
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
									BlockState state = ((IRune)this.getWorld().getBlockState(bp2).getBlock()).getStateWithMode(this.getWorld().getBlockState(bp2), re.getFinalBlockStates()[i2]);

									if(!getWorld().isRemote)
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

			System.out.println("FORMED: " + this.getEffect().getName());

			
			if(shouldDestroy()){
				System.out.println("BIGF");
				destroy();
				this.currentEffect = -1;
				return;
			}
			
			if(PowerHelper.isBlockPowered(this.getWorld(), getPos()))
				return;
			
			if(!this.getEffect().isGenerating()){
				
				if(this.power < this.getEffect().getPowerCapacity()){
				
					
						if(((double)(this.power)/((double)(this.getEffect().getPowerCapacity())) < 0.25D) && PowerHelper.drainPower(this.getWorld(), pos, this.getEffect().getTransferRate(), l, false) > 0){
							
							
							this.power += PowerHelper.drainPower(this.getWorld(), pos, this.getEffect().getTransferRate(), l, true);
		//					BlockUtils.drawLine(getWorld(), Vector3.fromBlockPos(PowerHelper.getTorch(getWorld(), this.getPos(), getRange())).add(0.5D, 1.1D, 0.5D), Vector3.fromBlockPos(pos).add(0.5D));
		//					PowerHelper.drawTorchLines(getWorld(), getPos(), getRange(), true);
						}else{
						
							this.power += PowerHelper.drainPower(this.getWorld(), pos, this.getEffect().getTransferRate()/2, l, true);
		//					BlockUtils.drawLine(getWorld(), Vector3.fromBlockPos(PowerHelper.getTorch(getWorld(), this.getPos(), getRange())).add(0.5D, 1.1D, 0.5D), Vector3.fromBlockPos(pos).add(0.5D));
		//					PowerHelper.drawTorchLines(getWorld(), getPos(), getRange(), true);
							
						}
						
						
					
					
						
					}
					
				}else{
					
					if(this.power > 0 && PowerHelper.addPower(this.getWorld(), pos, this.getEffect().getTransferRate(), l, false) > 0){
						
						this.power-=PowerHelper.addPower(this.getWorld(), pos, this.getEffect().getTransferRate(), l, true);
				//		BlockUtils.drawLine(getWorld(), Vector3.fromBlockPos(pos).add(0.5D), Vector3.fromBlockPos(PowerHelper.getTorch(getWorld(), this.getPos(), getRange())).add(0.5D, 0.6D, 0.5D));
				//		PowerHelper.drawTorchLines(getWorld(), getPos(), getRange(), false);
						
					}
					
					
				}
			
			
			
		RuneFormations.effects.get(currentEffect).doEffect(this.getWorld(), this.getPos(), this, this.rEffect);
		
		
		}
		
		for(int i =0; i< l.size(); i++){
			
			if(this.getWorld().getTileEntity(l.get(i)) == null || this.getWorld().getTileEntity(l.get(i)) == this)
				l.remove(i);
			
		}	
		
		List<String> s = new ArrayList<String>();
		
		for(int i =0; i< l.size(); i++){
			
			if(s.contains(l.get(i).toString())){
				l.remove(i);
				continue;
			}
			
			s.add(l.get(i).toString());
			
			
			
		}
		
	}
	
	
	public boolean shouldDestroy(){
		
		
			IRuneEffect re = RuneFormations.effects.get(currentEffect);
			
			int sr = Math.round((float)((float)Math.sqrt(re.getNeededBlocks().length)/2F))-1;
			
			int index = 0;
			
		
				for(int zD = -sr;zD<=sr; zD++){
					for(int xD = -sr;xD<=sr; xD++){
					
					BlockPos bp = new BlockPos(this.getPos().add(new BlockPos(xD, 0, zD)));
				
					
					if(this.getWorld().getBlockState(bp).getBlock() == re.getNeededBlocks()[index] &&((IRune)this.getWorld().getBlockState(bp).getBlock()).getModeFromState(this.getWorld().getBlockState(bp)) == re.getFinalBlockStates()[index]){
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
	
	public void destroy()
	{

		for(BlockPos b : children)
		{
			this.getWorld().setBlockState(b, this.getWorld().getBlockState(b).getBlock().getDefaultState());
		}
		
		children.removeAll(children);
		
	}
	
	public boolean isFormed(){
	
		return currentEffect != -1;
	}
	
	




	
	public IRuneEffect getEffect(){
		
		if(currentEffect < 0)
			return null;
		
		return RuneFormations.effects.get(currentEffect);
	}



	@Override
	public int getRange() {
		
		return RuneFormations.getRange(getEffect())+3;
		
	}



	@Override
	public List<BlockPos> getLinkedBlocks() {
		

		
		return l ;
		
	}



	@Override
	public void updateLinkedBlocks(BlockPos bp) {
		
		for(int i =0; i< l.size(); i++){
			
			if(l.get(i).compareTo(bp) == 0)
				this.l.remove(i);
				
			
		}
		
		
	}



	


	


}
