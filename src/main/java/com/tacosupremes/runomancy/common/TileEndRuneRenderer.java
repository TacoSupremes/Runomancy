package com.tacosupremes.runomancy.common;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.tacosupremes.runomancy.common.block.BlockBatteryPortal;
import com.tacosupremes.runomancy.common.block.rune.tile.TileEndRune;
import com.tacosupremes.runomancy.common.power.block.tile.TilePowerStorage;
import com.tacosupremes.runomancy.common.runelogic.IRuneEffect;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.nbt.CompoundNBT;

public class TileEndRuneRenderer extends TileEntityRenderer<TileEndRune>
{
    public TileEndRuneRenderer(TileEntityRendererDispatcher rendererDispatcherIn)
    {
        super(rendererDispatcherIn);
    }

    @Override
    public void render(TileEndRune tileEntityIn, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn)
    {
        IRuneEffect effect = tileEntityIn.getEffect();

        if(effect == null)
            return;

        CompoundNBT nbt = tileEntityIn.rEffect;

        if(effect.hasSpecialRender())
        {
            matrixStackIn.push();
            matrixStackIn.translate(0.5D,0,0.5D);
            effect.render(tileEntityIn, partialTicks, matrixStackIn, bufferIn, combinedLightIn, combinedLightIn, nbt);
            matrixStackIn.pop();
        }
    }
}
