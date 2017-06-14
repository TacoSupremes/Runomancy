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
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockPowerStorage extends BlockModContainer{

	public BlockPowerStorage() {
		super(Material.GLASS, "battery");
		this.setDefaultState(this.getDefaultState().withProperty(mode, 0));
		
	}
	
	public static final PropertyInteger mode = PropertyInteger.create("mode",0, 7);
	

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		
		return new TilePowerStorage();
	}

	@Override
	protected Class<? extends TileEntity> tile() {

		return TilePowerStorage.class;
	}

	@Override
	public BlockRenderLayer getBlockLayer() {
		
		return BlockRenderLayer.CUTOUT;
		
	}

	@SideOnly(Side.CLIENT)
    public IBlockState getStateForEntityRender(IBlockState state)
    {
        return this.getDefaultState().withProperty(mode, 6);
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
	public boolean hasComparatorInputOverride(IBlockState state) {
		
		return true;
		
	}

	@Override
	public int getComparatorInputOverride(IBlockState blockState, World worldIn, BlockPos pos) {
		
		return this.getMetaFromState(blockState);
		
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
    
    
	
}
