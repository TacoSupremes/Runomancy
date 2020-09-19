package com.tacosupremes.runomancy.common.block;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Random;

public class BlockNode extends BlockModContainer
{
	public static final DirectionProperty FACING = DirectionalBlock.FACING;

	private static final VoxelShape up = VoxelShapes.combineAndSimplify(Block.makeCuboidShape(6, 0, 6, 10, 5, 10), Block.makeCuboidShape(7, 5, 7, 9, 6, 9), IBooleanFunction.OR);

	private static final VoxelShape down = VoxelShapes.combineAndSimplify(Block.makeCuboidShape(6, 11, 6, 10, 16, 10), Block.makeCuboidShape(7, 10, 7, 9, 11, 9), IBooleanFunction.OR);

	private static final VoxelShape north = VoxelShapes.combineAndSimplify(Block.makeCuboidShape(6, 6, 11, 10, 10, 16), Block.makeCuboidShape(7, 7, 10, 9, 9, 11), IBooleanFunction.OR);

	private static final VoxelShape east = VoxelShapes.combineAndSimplify(Block.makeCuboidShape(0, 6, 6, 5, 10, 10), Block.makeCuboidShape(5, 7, 7, 6, 9, 9), IBooleanFunction.OR);

	private static final VoxelShape south = VoxelShapes.combineAndSimplify(Block.makeCuboidShape(6, 6, 0, 10, 10, 5), Block.makeCuboidShape(7, 7, 5, 9, 9, 6), IBooleanFunction.OR);

	private static final VoxelShape west = VoxelShapes.combineAndSimplify(Block.makeCuboidShape(11, 6, 6, 16, 10, 10), Block.makeCuboidShape(10, 7, 7, 11, 9, 9), IBooleanFunction.OR);

	public BlockNode()
	{
		super(Properties.create(Material.ROCK).notSolid().hardnessAndResistance(0).sound(SoundType.STONE));
		this.setDefaultState(this.stateContainer.getBaseState().with(FACING, Direction.UP));
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context)
	{
		switch (state.get(FACING))
		{
			case UP:
				return up;
			case DOWN:
				return down;
			case EAST:
				return east;
			case WEST:
				return west;
			case NORTH:
				return north;
			case SOUTH:
				return south;
		}

		return null;
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context)
	{
		BlockPos blockpos = context.getPos();

		for(Direction d : context.getNearestLookingDirections())
		{
			BlockState t = this.getDefaultState().with(FACING, d.getOpposite());

			if(isValidPosition(t,context.getWorld(), blockpos))
				return t;
		}

		return this.getDefaultState();
	}

	@Override
	public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos)
	{
		return !isValidPosition(stateIn, worldIn, currentPos) ? Blocks.AIR.getDefaultState() : stateIn;
	}

	@Override
	public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos)
	{
		Direction d = state.get(FACING).getOpposite();

		return hasEnoughSolidSide(worldIn, pos.add(d.getDirectionVec()), d.getOpposite());
	}

	@OnlyIn(Dist.CLIENT)
	public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand)
	{
		Direction enumfacing = stateIn.get(FACING);
		double d0 = (double)pos.getX() + 0.5D;
		double d1 = (double)pos.getY() + 0.7D;
		double d2 = (double)pos.getZ() + 0.5D;
		double d3 = 0.22D;
		double d4 = 0.27D;

	        if (enumfacing.getAxis().isHorizontal())
	        {
	            Direction enumfacing1 = enumfacing.getOpposite();

	            worldIn.addParticle(ParticleTypes.SMOKE, d0 + 0.27D * (double)enumfacing1.getXOffset(), d1 + 0.22D, d2 + 0.27D * (double)enumfacing1.getZOffset(), 0.0D, 0.0D, 0.0D);
	            worldIn.addParticle(ParticleTypes.FLAME, d0 + 0.27D * (double)enumfacing1.getXOffset(), d1 + 0.22D, d2 + 0.27D * (double)enumfacing1.getZOffset(), 0.0D, 0.0D, 0.0D);
	        }
	        else
	        {
	            worldIn.addParticle(ParticleTypes.SMOKE, d0, d1, d2, 0.0D, 0.0D, 0.0D);
	            worldIn.addParticle(ParticleTypes.FLAME, d0, d1, d2, 0.0D, 0.0D, 0.0D);
	        }
	}

	@Override
	public BlockState rotate(BlockState state, Rotation rot)
	{
		return state.with(FACING, rot.rotate(state.get(FACING)));
	}

	@Override
	public BlockState mirror(BlockState state, Mirror mirrorIn)
	{
		return state.rotate(mirrorIn.toRotation(state.get(FACING)));
	}

	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder)
	{
		builder.add(FACING);
	}

	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world)
	{
		return ModBlocks.TILE_NODE.get().create();
	}

	@Override
	public String getName()
	{
		return "node";
	}
}