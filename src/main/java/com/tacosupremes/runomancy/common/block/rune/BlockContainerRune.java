package com.tacosupremes.runomancy.common.block.rune;

import com.tacosupremes.runomancy.common.block.BlockModContainer;
import com.tacosupremes.runomancy.common.block.ModBlocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class BlockContainerRune extends BlockModContainer implements IRune{

	public BlockContainerRune(Material materialIn, String s) {
		super(materialIn, s);
	//	this.setBlockBounds(0.1F, 0.0F, 0.1F, 0.9F, 0.1F, 0.9F);
		
		ModBlocks.runes.add(this);
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
	
	 public boolean canPlaceBlockAt(World worldIn, BlockPos pos)
	    {
	        return worldIn.getBlockState(pos.down()).getBlock().isSideSolid(worldIn.getBlockState(pos.down()), worldIn, pos.down(), EnumFacing.UP);
	    }

	    /**
	     * is the block grass, dirt or farmland
	     */
	    

	    /**
	     * Called when a neighboring block changes.
	     */
	 

	   

	    @Override
		public void onNeighborChange(IBlockAccess world, BlockPos pos, BlockPos neighbor) {
			
			super.onNeighborChange(world, pos, neighbor);
			 this.checkAndDropBlock((World) world, pos, world.getBlockState(pos));
		}

		protected void checkAndDropBlock(World worldIn, BlockPos pos, IBlockState state)
	    {
	        if (!this.canBlockStay(worldIn, pos, state))
	        {
	            this.dropBlockAsItem(worldIn, pos, state, 0);
	            worldIn.setBlockState(pos, Blocks.AIR.getDefaultState(), 3);
	        }
	    }

	    public boolean canBlockStay(World worldIn, BlockPos pos, IBlockState state)
	    {
	        BlockPos down = pos.down();
	        Block soil = worldIn.getBlockState(down).getBlock();
	        
	        return soil.isSideSolid(worldIn.getBlockState(down), worldIn, down, EnumFacing.UP);
	    }

		@Override
		public TileEntity createNewTileEntity(World worldIn, int meta) {
			
			return null;
			
		}

		@Override
		public IBlockState getStateWithMode(IBlockState state, int i) {
			
			return null;
			
		}

		@Override
		protected Class<? extends TileEntity> tile() {
			
			return null;
			
		}

		@Override
		public boolean isVisuallyOpaque() {
			
			return false;
			
		}
	    
		
		 public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
		    {
		      return new AxisAlignedBB(0.1D, 0.0D, 0.1D, 0.9D, 0.1D, 0.9D);
		    }

		
}
