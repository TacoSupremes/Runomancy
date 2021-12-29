package com.tacosupremes.runomancy.common.power.block;

import com.tacosupremes.runomancy.common.block.BlockModContainer;
import com.tacosupremes.runomancy.common.block.ModBlocks;
import com.tacosupremes.runomancy.common.power.block.tile.TilePowerStorage;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import java.util.stream.Stream;

public class BlockPowerStorage extends BlockModContainer
{

    public BlockPowerStorage()
    {
        super(Properties.create(Material.ROCK));
    }

    private static VoxelShape shape = Stream.of(
            Block.makeCuboidShape(7, 15, 7, 9, 16, 9),
            Block.makeCuboidShape(0, 0, 0, 16, 2, 16),
            Block.makeCuboidShape(3, 2, 3, 13, 12, 13),
            Block.makeCuboidShape(2, 0, 2, 3, 12, 3),
            Block.makeCuboidShape(13, 0, 2, 14, 12, 3),
            Block.makeCuboidShape(13, 0, 13, 14, 12, 14),
            Block.makeCuboidShape(2, 0, 13, 3, 12, 14),
            Block.makeCuboidShape(2, 12, 2, 14, 13, 14),
            Block.makeCuboidShape(6, 13, 6, 10, 15, 10)
    ).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR)).get();

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context)
    {
        return shape;
    }

    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world)
    {
        return ModBlocks.TILE_BATTERY.get().create();
    }

    @Override
    public String getName()
    {
        return "battery";
    }

    public boolean eventReceived(BlockState state, World w, BlockPos pos, int id, int param)
    {
        if(!w.isRemote)
            return true;

        w.getTileEntity(pos).receiveClientEvent(id, param);

        return false;
    }
}
