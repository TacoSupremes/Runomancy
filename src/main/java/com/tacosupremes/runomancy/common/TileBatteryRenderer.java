package com.tacosupremes.runomancy.common;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.tacosupremes.runomancy.common.block.BlockBatteryPortal;
import com.tacosupremes.runomancy.common.block.ModBlocks;
import com.tacosupremes.runomancy.common.power.block.tile.TilePowerStorage;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;

public class TileBatteryRenderer extends TileEntityRenderer<TilePowerStorage>
{

    public TileBatteryRenderer(TileEntityRendererDispatcher rendererDispatcherIn)
    {
        super(rendererDispatcherIn);
    }

    @Override
    public void render(TilePowerStorage tileEntityIn, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn)
    {
        if(tileEntityIn.power > 0)
        {
            matrixStackIn.push();
            //0.65 is max height
            matrixStackIn.scale(0.6F, (float)tileEntityIn.power / tileEntityIn.getMaxPower() * 0.6F + 0.05F, 0.6F);
            matrixStackIn.translate(0.33, 0.1, 0.33);

            Minecraft.getInstance().getBlockRendererDispatcher().renderBlock(BlockBatteryPortal.PURPLE, matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
            matrixStackIn.pop();
        }
    }
}
