package com.tacosupremes.runomancy.common.block;

import com.tacosupremes.runomancy.common.Runomancy;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BlockMod extends Block {

	public BlockMod(Material materialIn, String s) {
		super(materialIn);
		this.setUnlocalizedName(s);
		this.setCreativeTab(Runomancy.tab);
		this.setRegistryName(s);
		GameRegistry.register(this);
		GameRegistry.register(new ItemBlock(this).setRegistryName(s));
		ModBlocks.blocks.add(this);
	}

	
	
}