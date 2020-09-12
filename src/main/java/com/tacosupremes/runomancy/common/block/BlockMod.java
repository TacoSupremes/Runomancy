package com.tacosupremes.runomancy.common.block;

import net.minecraft.block.Block;

public abstract class BlockMod extends Block
{
    public BlockMod(Properties properties)
    {
        super(properties);
    }

    public abstract String getName();
}
