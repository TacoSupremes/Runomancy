package com.tacosupremes.runomancy.common.block.rune;

import java.util.List;

import com.tacosupremes.runomancy.common.block.ModBlocks;
import com.tacosupremes.runomancy.common.block.rune.tile.TileEndRune;
import com.tacosupremes.runomancy.common.power.block.tile.IPowerNode;
import com.tacosupremes.runomancy.gui.Categories;
import com.tacosupremes.runomancy.gui.IPageGiver;
import com.tacosupremes.runomancy.gui.ItemPage;
import com.tacosupremes.runomancy.gui.Page;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
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
		
		
		List<BlockPos> bpl = ((IPowerNode)w.getTileEntity(pos)).getLinkedBlocks();
		
		super.breakBlock(w, pos, state);
		
		for(BlockPos bp : bpl){
			
			IPowerNode k = (IPowerNode)w.getTileEntity(bp);
			
			k.updateLinkedBlocks();
			
		}
		
		
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
	
	@Override
	public Page getSubPages() {
		
		return null;
		
	}
	

	
	
}
