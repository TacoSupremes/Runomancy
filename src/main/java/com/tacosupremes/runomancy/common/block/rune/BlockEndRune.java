package com.tacosupremes.runomancy.common.block.rune;

import java.util.List;

import com.tacosupremes.runomancy.common.block.ModBlocks;
import com.tacosupremes.runomancy.common.block.rune.tile.TileEndRune;
import com.tacosupremes.runomancy.common.block.tile.INode;

import com.tacosupremes.runomancy.common.power.block.tile.IPowerTile;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class BlockEndRune extends BlockContainerRune
{
	public static final IntegerProperty mode = IntegerProperty.create("mode",0, 16);

	public BlockEndRune()
	{
		super(Properties.create(Material.WOOD).notSolid().hardnessAndResistance(1.0F, 1.5F).sound(SoundType.WOOD));
		this.setDefaultState(this.stateContainer.getBaseState().with(mode, 0));
	}

	@Override
	public void onReplaced(BlockState state, World w, BlockPos pos, BlockState newState, boolean isMoving)
	{
		if (state.getBlock() != newState.getBlock())
		{
			TileEndRune te = (TileEndRune)w.getTileEntity(pos);
			te.destroy();

			List<BlockPos> bpl = ((IPowerTile)w.getTileEntity(pos)).getNodeList();

			super.onReplaced(state, w, pos, newState, isMoving);

			for(BlockPos bp : bpl)
			{
				INode k = (INode)w.getTileEntity(bp);

				k.getNodeList().remove(pos);
			}
		}
	}

	@Override
	int getModeCount()
	{
		return ModBlocks.endRuneCount;
	}

	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world)
	{
		return ModBlocks.TILE_END_RUNE.get().create();
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
	public String getName()
	{
		return "end_rune";
	}

	@Override
	public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit)
	{
		if(((TileEndRune)worldIn.getTileEntity(pos)).getEffect() != null)
			return ((TileEndRune)worldIn.getTileEntity(pos)).getEffect().onRightClick(worldIn, pos, player, handIn, hit,((TileEndRune)worldIn.getTileEntity(pos)).rEffect);

		return super.onBlockActivated(state, worldIn, pos, player, handIn, hit);
	}
}
