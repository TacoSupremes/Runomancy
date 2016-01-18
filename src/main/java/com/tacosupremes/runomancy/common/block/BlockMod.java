package com.tacosupremes.runomancy.common.block;

import com.tacosupremes.runomancy.common.Runomancy;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BlockMod extends Block {

	public BlockMod(Material materialIn, String s) {
		super(materialIn);
		this.setUnlocalizedName(s);
		this.setCreativeTab(Runomancy.tab);
		GameRegistry.registerBlock(this, s);
		ModBlocks.blocks.add(this);
	}

	@Override
	public int getRenderType() {
	
		return 3;
	}
	
	
	
}