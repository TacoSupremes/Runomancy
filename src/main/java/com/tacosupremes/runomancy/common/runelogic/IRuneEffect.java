package com.tacosupremes.runomancy.common.runelogic;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.tacosupremes.runomancy.common.block.rune.tile.TileEndRune;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public interface IRuneEffect {
	
	void doEffect(World w, BlockPos pos, TileEndRune te, CompoundNBT nbt);

	Block[] getNeededBlocks();
	
	boolean isGenerating();

	default boolean isConsuming()
	{
		return !isGenerating();
	}
	
	int[] getFinalBlockStates();

	int getPowerCapacity();
	
	int getTransferRate();
	
	String getName();

	default ActionResultType onRightClick(World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit, CompoundNBT nbt)
	{
        return ActionResultType.PASS;
    };

	default boolean hasSpecialRender(){ return false; }

	@OnlyIn(Dist.CLIENT)
	default void render(TileEndRune tileEntityIn, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn, CompoundNBT nbt){}
}



