package com.tacosupremes.runomancy.common.block.tile;

import com.tacosupremes.runomancy.common.utils.BlockUtils;
import com.tacosupremes.runomancy.common.utils.Vector3;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TileMarker extends TileEntity implements ITickable {

	@Override
	public void update() {
		
		if(this.getBlockMetadata() == 0)
			return;
		
		World w = this.getWorld();
		
		if(xF != null && (w.getTileEntity(xF) == null || !(w.getTileEntity(xF) instanceof TileMarker)))
			xF = null;
		
		if(yF != null && (w.getTileEntity(yF) == null || !(w.getTileEntity(yF) instanceof TileMarker)))
			yF = null;
		
		if(zF != null && (w.getTileEntity(zF) == null || !(w.getTileEntity(zF) instanceof TileMarker)))
			zF = null;
			
			
		if(xF == null && yF == null && zF == null)
			this.getWorld().setBlockState(getPos(), this.getWorld().getBlockState(getPos()).getBlock().getStateFromMeta(0));
		
		
		
		if(xF != null  && yF != null && zF != null){
			
			BlockUtils.drawLine(w, Vector3.fromBlockPos(pos).add(0.5D, 0.5D, 0.5D), Vector3.fromBlockPos(xF).add(0.5D, 0.5D, 0.5D), EnumParticleTypes.REDSTONE);
			BlockUtils.drawLine(w, Vector3.fromBlockPos(pos).add(0.5D, 0.5D, 0.5D), Vector3.fromBlockPos(zF).add(0.5D, 0.5D, 0.5D), EnumParticleTypes.REDSTONE);
			
			BlockPos corner = new BlockPos(xF.getX(), xF.getY(), zF.getZ());
			BlockUtils.drawLine(w, Vector3.fromBlockPos(xF).add(0.5D, 0.5D, 0.5D), Vector3.fromBlockPos(corner).add(0.5D, 0.5D, 0.5D), EnumParticleTypes.REDSTONE);
			BlockUtils.drawLine(w, Vector3.fromBlockPos(zF).add(0.5D, 0.5D, 0.5D), Vector3.fromBlockPos(corner).add(0.5D, 0.5D, 0.5D),  EnumParticleTypes.REDSTONE);
		
			Vector3 yo = new Vector3(0,Math.abs(xF.getY()-yF.getY()), 0);
			
			BlockUtils.drawLine(w, Vector3.fromBlockPos(pos).add(0.5D, 0.5D, 0.5D).add(yo), Vector3.fromBlockPos(xF).add(0.5D, 0.5D, 0.5D).add(yo), EnumParticleTypes.REDSTONE);
			BlockUtils.drawLine(w, Vector3.fromBlockPos(pos).add(0.5D, 0.5D, 0.5D).add(yo), Vector3.fromBlockPos(zF).add(0.5D, 0.5D, 0.5D).add(yo), EnumParticleTypes.REDSTONE);
			
		
			BlockUtils.drawLine(w, Vector3.fromBlockPos(xF).add(0.5D, 0.5D, 0.5D).add(yo), Vector3.fromBlockPos(corner).add(yo).add(0.5D, 0.5D, 0.5D), EnumParticleTypes.REDSTONE);
			BlockUtils.drawLine(w, Vector3.fromBlockPos(zF).add(0.5D, 0.5D, 0.5D).add(yo), Vector3.fromBlockPos(corner).add(yo).add(0.5D, 0.5D, 0.5D),  EnumParticleTypes.REDSTONE);
		
			BlockUtils.drawLine(w, Vector3.fromBlockPos(pos).add(0.5D, 0.5D, 0.5D).add(yo), Vector3.fromBlockPos(pos).add(0.5D, 0.5D, 0.5D), EnumParticleTypes.REDSTONE);
			BlockUtils.drawLine(w, Vector3.fromBlockPos(corner).add(yo).add(0.5D, 0.5D, 0.5D), Vector3.fromBlockPos(corner).add(0.5D, 0.5D, 0.5D), EnumParticleTypes.REDSTONE);
			BlockUtils.drawLine(w, Vector3.fromBlockPos(xF).add(0.5D, 0.5D, 0.5D).add(yo), Vector3.fromBlockPos(xF).add(0.5D, 0.5D, 0.5D), EnumParticleTypes.REDSTONE);
			BlockUtils.drawLine(w, Vector3.fromBlockPos(zF).add(0.5D, 0.5D, 0.5D).add(yo), Vector3.fromBlockPos(zF).add(0.5D, 0.5D, 0.5D), EnumParticleTypes.REDSTONE);
			
			return;
		}
		
if(xF != null && zF != null){
	
	BlockUtils.drawLine(w, Vector3.fromBlockPos(pos).add(0.5D, 0.5D, 0.5D), Vector3.fromBlockPos(xF).add(0.5D, 0.5D, 0.5D), EnumParticleTypes.REDSTONE);
	BlockUtils.drawLine(w, Vector3.fromBlockPos(pos).add(0.5D, 0.5D, 0.5D), Vector3.fromBlockPos(zF).add(0.5D, 0.5D, 0.5D), EnumParticleTypes.REDSTONE);
	
	BlockPos corner = new BlockPos(xF.getX(), xF.getY(), zF.getZ());
	BlockUtils.drawLine(w, Vector3.fromBlockPos(xF).add(0.5D, 0.5D, 0.5D), Vector3.fromBlockPos(corner), EnumParticleTypes.REDSTONE);
	BlockUtils.drawLine(w, Vector3.fromBlockPos(zF).add(0.5D, 0.5D, 0.5D), Vector3.fromBlockPos(corner),  EnumParticleTypes.REDSTONE);

	return;
}

if(xF != null && yF != null){
	
	BlockUtils.drawLine(w, Vector3.fromBlockPos(pos).add(0.5D, 0.5D, 0.5D), Vector3.fromBlockPos(xF).add(0.5D, 0.5D, 0.5D), EnumParticleTypes.REDSTONE);
	BlockUtils.drawLine(w, Vector3.fromBlockPos(pos).add(0.5D, 0.5D, 0.5D), Vector3.fromBlockPos(yF).add(0.5D, 0.5D, 0.5D), EnumParticleTypes.REDSTONE);
	
	BlockPos corner = new BlockPos(xF.getX(), yF.getY(), xF.getZ());
	BlockUtils.drawLine(w, Vector3.fromBlockPos(xF).add(0.5D, 0.5D, 0.5D), Vector3.fromBlockPos(corner), EnumParticleTypes.REDSTONE);
	BlockUtils.drawLine(w, Vector3.fromBlockPos(yF).add(0.5D, 0.5D, 0.5D), Vector3.fromBlockPos(corner),  EnumParticleTypes.REDSTONE);

	return;
}

if(yF != null && zF != null){
	
	BlockUtils.drawLine(w, Vector3.fromBlockPos(pos).add(0.5D, 0.5D, 0.5D), Vector3.fromBlockPos(yF).add(0.5D, 0.5D, 0.5D), EnumParticleTypes.REDSTONE);
	BlockUtils.drawLine(w, Vector3.fromBlockPos(pos).add(0.5D, 0.5D, 0.5D), Vector3.fromBlockPos(zF).add(0.5D, 0.5D, 0.5D), EnumParticleTypes.REDSTONE);
	
	BlockPos corner = new BlockPos(zF.getX(), yF.getY(), zF.getZ());
	BlockUtils.drawLine(w, Vector3.fromBlockPos(yF).add(0.5D, 0.5D, 0.5D), Vector3.fromBlockPos(corner), EnumParticleTypes.REDSTONE);
	BlockUtils.drawLine(w, Vector3.fromBlockPos(zF).add(0.5D, 0.5D, 0.5D), Vector3.fromBlockPos(corner),  EnumParticleTypes.REDSTONE);

	return;
}

	

		
	if(xF != null){
		
		BlockUtils.drawLine(w, Vector3.fromBlockPos(pos).add(0.5D, 0.5D, 0.5D), Vector3.fromBlockPos(xF).add(0.5D, 0.5D, 0.5D), EnumParticleTypes.REDSTONE);
		
	}
	
	if(yF != null){
		
		BlockUtils.drawLine(w, Vector3.fromBlockPos(pos).add(0.5D, 0.5D, 0.5D), Vector3.fromBlockPos(yF).add(0.5D, 0.5D, 0.5D), EnumParticleTypes.REDSTONE);
		
	}
		
	if(zF != null){
		
		BlockUtils.drawLine(w, Vector3.fromBlockPos(pos).add(0.5D, 0.5D, 0.5D), Vector3.fromBlockPos(zF).add(0.5D, 0.5D, 0.5D), EnumParticleTypes.REDSTONE);
		
	}		
		
		
		
		
	}
	
	public BlockPos xF = null;
	public BlockPos yF = null;
	public BlockPos zF = null;
	
	


	public void readCustomNBT(NBTTagCompound nbt)
    {
		
		this.xF = BlockPos.fromLong(nbt.getLong("xD"));
		
		this.yF = BlockPos.fromLong(nbt.getLong("yD"));
		
		this.zF = BlockPos.fromLong(nbt.getLong("zD"));
		
		
    }

	
	
	public void writeCustomNBT(NBTTagCompound nbt)
    {
		
		if(xF != null)
			nbt.setLong("xD", xF.toLong());
		
		if(yF != null)
			nbt.setLong("yD", yF.toLong());
		
		if(zF != null)
			nbt.setLong("zD", zF.toLong());
	
    }
	
	
	@Override
	public void readFromNBT(NBTTagCompound nbt)
    {
        super.readFromNBT(nbt);
        readCustomNBT(nbt);
    }
	@Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt)
    {
        
        writeCustomNBT(nbt);
        
        return super.writeToNBT(nbt);
    }
	
	//Client Packet stuff
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

	public boolean isArea3D(){
		
		return xF != null && yF != null && zF != null;
	}
	
	
	
	public boolean isArea(){
		
		return xF != null && yF != null || xF != null && zF != null || yF != null && zF != null;
	}
	
	
}
