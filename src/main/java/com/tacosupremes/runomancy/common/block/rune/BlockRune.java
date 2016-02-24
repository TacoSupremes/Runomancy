package com.tacosupremes.runomancy.common.block.rune;

import java.util.Random;

import com.tacosupremes.runomancy.common.block.BlockMod;
import com.tacosupremes.runomancy.common.block.ModBlocks;
import com.tacosupremes.runomancy.common.item.ModItems;
import com.tacosupremes.runomancy.gui.Categories;
import com.tacosupremes.runomancy.gui.IPageGiver;
import com.tacosupremes.runomancy.gui.Page;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class BlockRune extends BlockMod implements IRune, IPageGiver {

	
	
	
	
	 
	public BlockRune(Material materialIn, String s) {
		super(materialIn, s);
		this.setBlockBounds(0.1F, 0.0F, 0.1F, 0.9F, 0.1F, 0.9F);
		ModBlocks.runes.add(this);
	
	}
	
	public BlockRune(String s) {
		this(Material.circuits, s);
		
	
	}
	
	@SideOnly(Side.CLIENT)
    public EnumWorldBlockLayer getBlockLayer()
    {
        return EnumWorldBlockLayer.CUTOUT_MIPPED;
    }

	@Override
	public boolean isNormalCube() {
		
		return false;
	}

	@Override
	public boolean isOpaqueCube() {
		
		return false;
	}

	@Override
	public boolean isFullBlock() {
		
		return false;
	}

	@Override
	public boolean isFullCube() {
		
		return false;
	}
	
	
	 public boolean canPlaceBlockAt(World worldIn, BlockPos pos)
	    {
	        return worldIn.getBlockState(pos.down()).getBlock().isSideSolid(worldIn, pos.down(), EnumFacing.UP);
	    }

	    /**
	     * is the block grass, dirt or farmland
	     */
	    

	    /**
	     * Called when a neighboring block changes.
	     */
	    public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock)
	    {
	        super.onNeighborBlockChange(worldIn, pos, state, neighborBlock);
	        this.checkAndDropBlock(worldIn, pos, state);
	    }

	   

	    protected void checkAndDropBlock(World worldIn, BlockPos pos, IBlockState state)
	    {
	        if (!this.canBlockStay(worldIn, pos, state))
	        {
	            this.dropBlockAsItem(worldIn, pos, state, 0);
	            worldIn.setBlockState(pos, Blocks.air.getDefaultState(), 3);
	        }
	    }

	    public boolean canBlockStay(World worldIn, BlockPos pos, IBlockState state)
	    {
	        BlockPos down = pos.down();
	        Block soil = worldIn.getBlockState(down).getBlock();
	        
	        return soil.isSideSolid(worldIn, down, EnumFacing.UP);
	    }

		@Override
		public boolean hasNormalRecipe() {
			
			return true;
			
		}

		

		@Override
		public Page getSubPages() {
			
			return null;
			
		}
		

	

}
