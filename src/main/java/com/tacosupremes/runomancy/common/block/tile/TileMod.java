package com.tacosupremes.runomancy.common.block.tile;

import javax.annotation.Nonnull;

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TileMod extends TileEntity implements ITickable{
	
	@Override
	public boolean shouldRefresh(World world, BlockPos pos, @Nonnull IBlockState oldState, @Nonnull IBlockState newState) {
		return oldState.getBlock() != newState.getBlock();
	}

	@Nonnull
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound par1nbtTagCompound) {
		NBTTagCompound ret = super.writeToNBT(par1nbtTagCompound);
		writeCustomNBT(ret);
		return ret;
	}

	@Nonnull
	@Override
	public final NBTTagCompound getUpdateTag() {
		return writeToNBT(new NBTTagCompound());
	}

	@Override
	public void readFromNBT(NBTTagCompound par1nbtTagCompound) {
		super.readFromNBT(par1nbtTagCompound);
		readCustomNBT(par1nbtTagCompound);
	}

	public void writeCustomNBT(NBTTagCompound cmp) {}

	public void readCustomNBT(NBTTagCompound cmp) {}

	@Override
	public final SPacketUpdateTileEntity getUpdatePacket() {
		NBTTagCompound tag = new NBTTagCompound();
		writeCustomNBT(tag);
		return new SPacketUpdateTileEntity(pos, -999, tag);
	}

	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity packet) {
		super.onDataPacket(net, packet);
		readCustomNBT(packet.getNbtCompound());
	}

	@Override
	public void update() {}


}
