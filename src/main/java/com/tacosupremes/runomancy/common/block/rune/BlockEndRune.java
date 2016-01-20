package com.tacosupremes.runomancy.common.block.rune;

import com.tacosupremes.runomancy.common.block.BlockModContainer;
import com.tacosupremes.runomancy.common.block.ModBlocks;
import com.tacosupremes.runomancy.common.block.rune.tile.TileEndRune;
import com.tacosupremes.runomancy.gui.Categories;
import com.tacosupremes.runomancy.gui.GuiModBook;
import com.tacosupremes.runomancy.gui.IPageGiver;
import com.tacosupremes.runomancy.gui.ItemPage;
import com.tacosupremes.runomancy.gui.Page;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockEndRune extends BlockContainerRune implements IPageGiver{

	public BlockEndRune() {
		super(Material.circuits, "endRune");
		
		
	}

	public static final PropertyInteger mode = PropertyInteger.create("mode",0, ModBlocks.endRuneCount);
	
	
	
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		
		return new TileEndRune();
	}

	@Override
	protected Class<? extends TileEntity> tile() {
		
		return TileEndRune.class;
	}
	
	
	


	@Override
	public void breakBlock(World w, BlockPos pos, IBlockState state) {
		
		
		TileEndRune te = (TileEndRune)w.getTileEntity(pos);
		te.destroy();
		
		super.breakBlock(w, pos, state);
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
	
	@SideOnly(Side.CLIENT)
    public IBlockState getStateForEntityRender(IBlockState state)
    {
        return this.getDefaultState().withProperty(mode, 0);
    }

    /**
     * Convert the given metadata into a BlockState for this Block
     */
    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(mode, meta);
    }

    /**
     * Convert the BlockState into the correct metadata value
     */
    public int getMetaFromState(IBlockState state)
    {
       

        return state.getValue(mode);
    }

    protected BlockState createBlockState()
    {
        return new BlockState(this, new IProperty[] {mode});
    }

	@Override
	public IBlockState getStateWithMode(IBlockState state, int i) {
		
		return this.getDefaultState().withProperty(mode, i);
	}

	@Override
	public Page getPage() {
		
		return new ItemPage(new ItemStack(this));
	}


	@Override
	public Categories getCategories() {
		
		return Categories.Runes;
	}


	@Override
	public boolean hasNormalRecipe() {
		
		return true;
		
	}
	

	
	
}
