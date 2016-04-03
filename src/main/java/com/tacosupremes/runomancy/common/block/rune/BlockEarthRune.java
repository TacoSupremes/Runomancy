package com.tacosupremes.runomancy.common.block.rune;

import com.tacosupremes.runomancy.common.block.ModBlocks;
import com.tacosupremes.runomancy.gui.Categories;
import com.tacosupremes.runomancy.gui.ItemPage;
import com.tacosupremes.runomancy.gui.Page;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ColorizerGrass;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.biome.BiomeColorHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockEarthRune extends BlockRune {

public static final PropertyInteger mode = PropertyInteger.create("mode",0, ModBlocks.earthCount);
	
	
	
	public BlockEarthRune() {
		super(Material.circuits, "earthRune");
		
		this.setDefaultState(this.getDefaultState().withProperty(mode, 0));
		
		
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

    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {mode});
    }

	@Override
	public IBlockState getStateWithMode(IBlockState state, int i) {
		
		return this.getDefaultState().withProperty(mode, i);
	}

	@SideOnly(Side.CLIENT)
    public int getBlockColor()
    {
        return ColorizerGrass.getGrassColor(0.5D, 1.0D);
    }

    @SideOnly(Side.CLIENT)
    public int getRenderColor(IBlockState state)
    {
        return this.getBlockColor();
    }

    @SideOnly(Side.CLIENT)
    public int colorMultiplier(IBlockAccess worldIn, BlockPos pos, int renderPass)
    {
        return BiomeColorHelper.getGrassColorAtPos(worldIn, pos);
    }

   


	@Override
	public Page getPage() {
		
		return new ItemPage(new ItemStack(this));
	}


	@Override
	public Categories getCategories() {
		
		return Categories.Runes;
	}
}
