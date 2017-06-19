package com.tacosupremes.runomancy.common.power.block;

import java.util.List;

import com.tacosupremes.runomancy.common.block.BlockModContainer;
import com.tacosupremes.runomancy.common.block.rune.tile.TileEndRune;
import com.tacosupremes.runomancy.common.power.block.tile.IPowerNode;
import com.tacosupremes.runomancy.common.power.block.tile.TilePowerStorage;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockPowerStorage extends BlockModContainer{

	public BlockPowerStorage() {
		super(Material.ROCK, "battery");
	
		
	}
	

	

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		
		return new TilePowerStorage();
	}

	@Override
	protected Class<? extends TileEntity> tile() {

		return TilePowerStorage.class;
	}


	
	@SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.CUTOUT_MIPPED;
    }

	@Override
	public boolean isNormalCube(IBlockState state) {
		
		return false;
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		
		return false;
	}

	@Override
	public boolean isFullBlock(IBlockState state) {
		
		return false;
	}

	@Override
	public boolean isFullCube(IBlockState state) {
		
		return false;
	}

  
    

	@Override
	public boolean hasComparatorInputOverride(IBlockState state) {
		
		return true;
		
	}

	@Override
	public int getComparatorInputOverride(IBlockState blockState, World worldIn, BlockPos pos) {
		//TODO
		return 0;
		
	}

	@Override
	public void breakBlock(World w, BlockPos pos, IBlockState state) {
		
		
		
		
		
List<BlockPos> bpl = ((IPowerNode)w.getTileEntity(pos)).getLinkedBlocks();
		
		super.breakBlock(w, pos, state);
		
		for(BlockPos bp : bpl){
			
			IPowerNode k = (IPowerNode)w.getTileEntity(bp);
			
			k.updateLinkedBlocks(pos);
			k.getLinkedBlocks().remove(pos);
		}
	}
	
    @Override
	 public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
	    {
	      return new AxisAlignedBB(0D, 0D, 0D, 1D, 0.3D, 1D);
	    }
	
}
