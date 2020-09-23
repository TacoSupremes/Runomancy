package com.tacosupremes.runomancy.common.block.rune.tile;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Lists;
import com.tacosupremes.runomancy.common.block.ModBlocks;
import com.tacosupremes.runomancy.common.block.rune.IRune;
import com.tacosupremes.runomancy.common.block.tile.INode;
import com.tacosupremes.runomancy.common.block.tile.TileMod;
import com.tacosupremes.runomancy.common.power.PowerHelper;
import com.tacosupremes.runomancy.common.power.block.tile.IPowerNode;
import com.tacosupremes.runomancy.common.power.block.tile.IPowerTile;
import com.tacosupremes.runomancy.common.power.block.tile.TilePowerStorage;
import com.tacosupremes.runomancy.common.runelogic.IRuneEffect;
import com.tacosupremes.runomancy.common.runelogic.RuneEffectRepair;
import com.tacosupremes.runomancy.common.runelogic.RuneFormations;

import com.tacosupremes.runomancy.common.utils.BlockUtils;
import com.tacosupremes.runomancy.common.utils.Vector3;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TileEndRune extends TileMod implements IPowerTile, ITickableTileEntity
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



			
			if(shouldDestroy()){
				System.out.println("BIGF");
				destroy();
				this.currentEffect = -1;
				return;
			}
			
			if(!isActiveNode())
				return;

			System.out.println("FORMED: " + this.getEffect().getName());
			
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
					
				}
				else
				{
					if(this.power > 0)
					{
						List<BlockPos> bp = new ArrayList<>();

						getPathToBattery(getWorld(),getPos(), getNodeList(), bp, new ArrayList<String>(), false);

						if(!bp.isEmpty())
						{


							Vector3 last = getParticleOffset();

							for(int i = 0; i< bp.size(); i++)
							{
								Vector3 current = ((INode) world.getTileEntity(bp.get(i))).getParticleOffset();

								BlockUtils.drawLine(getWorld(), last, current, ParticleTypes.HAPPY_VILLAGER);

								last = current;
							}

							BlockPos batterypos = bp.get(bp.size()-1);

							TilePowerStorage tps = (TilePowerStorage)getWorld().getTileEntity(batterypos);

							if(tps.getPower() < tps.getMaxPower())
							{
								tps.addPower(this.removePower(10));
							}
						}
						else
						{
							System.out.println("TRAGIC");
						}


						//this.power-=PowerHelper.addPower(this.getWorld(), pos, this.getEffect().getTransferRate(), l, true);
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

	/*
	private List<Vector3> getPathToBattery()
	{
		return getPathToBattery(getWorld(), getPos(), this, new ArrayList<Vector3>(),new ArrayList<Long>());
	}
*/
/*
	private List<Vector3> getPathToBattery(World w, BlockPos pos, INode node, List<Vector3> l, List<Long> visited)
	{
		visited.add(pos.toLong());

		if(w.getTileEntity(pos) instanceof TilePowerStorage)
		{
			if(((TilePowerStorage)w.getTileEntity(pos)).getPower() < ((TilePowerStorage)w.getTileEntity(pos)).getPower())
				return l;
		}


		for(BlockPos bp : node.getNodeList())
		{
			if(!visited.contains(bp.toLong()))
			{
				INode in = (INode)w.getTileEntity(bp);

				l.add(in.getParticleOffset());

				return getPathToBattery(w , bp, in, l, visited);
			}

			l.remove(l.size() - 1);

		}

		return new ArrayList<Vector3>();
	}

*/
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
	public int getPower()
	{
		return power;
	}

	@Override
	public int addPower(int i)
	{
		if(power + i > getMaxPower())
		{
			int res = getMaxPower() - power;

			power = getMaxPower();

			return res;
		}
		else
			power += i;
		return i;
	}

	@Override
	public int removePower(int i)
	{
		if(power <= 0 || power < i)
			return 0;

		power -= i;

		return i;
	}

	@Override
	public int getMaxPower()
	{
		return 0;
	}

	@Override
	public boolean canFill()
	{
		return power < getMaxPower();
	}

	@Override
	public boolean isActiveNode()
	{
		return !PowerHelper.isBlockPowered(this.getWorld(), getPos());
	}

	@Override
	public List<BlockPos> getNodeList()
	{
		return l;
	}

	@Override
	public Vector3 getParticleOffset()
	{
		return Vector3.fromBlockPos(getPos()).add(0.5,0.2,0.5);
	}

	public static boolean getPathToBattery(World w, BlockPos posF, List<BlockPos> linked, List<BlockPos> path,
										   List<String> used, boolean includeStart)
	{

		if(includeStart)
		{
			path.add(posF);
		}

		used.add(posF.toString());
		//*G-N - N - N
		// |		 |
		// N -N		 N
		//    |		 |
		//	  N		 C

		for(BlockPos bp : linked)
		{
			if(!used.contains(bp.toString()))
			{
				INode i = (INode)w.getTileEntity(bp);

				used.add(bp.toString());

				if(i == null || !i.isActiveNode())
					continue;

				path.add(bp);

				if(w.getTileEntity(bp) instanceof TilePowerStorage && ((TilePowerStorage)w.getTileEntity(bp)).canFill())
				{
					return true;
				}

				List<BlockPos> l2 = new ArrayList<BlockPos>();

				if(getPathToBattery(w, posF, i.getNodeList(), l2, used, false))
				{
					path.addAll(l2);
					return true;
				}

				if(!path.isEmpty())
					path.remove(path.size() - 1);
			}

		}
		return false;

	}
}
