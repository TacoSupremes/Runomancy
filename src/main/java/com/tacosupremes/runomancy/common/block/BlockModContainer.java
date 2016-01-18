package com.tacosupremes.runomancy.common.block;

import com.tacosupremes.runomancy.common.Runomancy;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
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

	
	@Override
	public int getRenderType() {
	
		return 3;
	}
	
	
	
	

}
