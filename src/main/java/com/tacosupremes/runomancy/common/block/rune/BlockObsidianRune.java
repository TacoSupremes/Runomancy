package com.tacosupremes.runomancy.common.block.rune;

import com.tacosupremes.runomancy.common.block.BlockMod;
import com.tacosupremes.runomancy.common.block.ModBlocks;
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
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockObsidianRune extends BlockRune implements IPageGiver{

	public static final PropertyInteger mode = PropertyInteger.create("mode",0, ModBlocks.obsidianCount);
	
	
	
	public BlockObsidianRune() {
		super(Material.circuits, "obsidianRune");
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
}
