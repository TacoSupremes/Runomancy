package com.tacosupremes.runomancy.common.block;

import com.tacosupremes.runomancy.common.Runomancy;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraftforge.fml.common.registry.GameRegistry;

public abstract class BlockModContainer extends BlockContainer {

	public BlockModContainer(Material materialIn, String s) {
		super(materialIn);
		this.setUnlocalizedName(s);
		this.setCreativeTab(Runomancy.tab);
		ModBlocks.blocks.add(this);
		GameRegistry.registerBlock(this, s);
		GameRegistry.registerTileEntity(tile(), s);
	}
	
	protected abstract Class<? extends TileEntity> tile();

	

	 public EnumBlockRenderType getRenderType(IBlockState state)
	    {
	        return EnumBlockRenderType.MODEL;
	    }
	
	

}
