package com.tacosupremes.runomancy.common.block.rune;


import com.tacosupremes.runomancy.common.block.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.BucketItem;
import net.minecraft.item.ItemStack;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;

import javax.annotation.Nullable;

public class BlockWaterRune extends BlockRune
{
    public static final IntegerProperty mode = IntegerProperty.create("mode",0, ModBlocks.waterCount);

    public BlockWaterRune()
    {
        super();
        this.setDefaultState(this.stateContainer.getBaseState().with(mode, 0));
    }

    public BlockWaterRune(Properties properties)
    {
        super(properties);
    }

    @Override
    int getModeCount()
    {
        return ModBlocks.waterCount;
    }

    @Override
    public String getName()
    {
        return "water_rune";
    }

    @Override
    public BlockState getStateWithMode(BlockState state, int i)
    {
        return this.getDefaultState().with(mode, i);
    }

    @Override
    public int getModeFromState(BlockState state)
    {
        return state.get(mode);
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder)
    {
        builder.add(mode);
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack)
    {
        if(worldIn.getDimension().getType() == DimensionType.THE_NETHER)
        {
            if(!worldIn.isRemote)
                worldIn.destroyBlock(pos,false);
        }
    }

}
