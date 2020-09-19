package com.tacosupremes.runomancy.common.block.rune;

import com.tacosupremes.runomancy.common.block.ModBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ILightReader;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.awt.*;
import java.awt.color.ColorSpace;

public class GrassColorHandler implements IBlockColor
{
    public static final IBlockColor INSTANCE = new GrassColorHandler();

    @Override
    @OnlyIn(Dist.CLIENT)
    public int getColor(BlockState state, @Nullable ILightReader iLightReader, @Nullable BlockPos pos, int p_getColor_4_)
    {
        Color c = new Color(Minecraft.getInstance().world.getBiome(pos).getGrassColor(pos.getX(), pos.getZ()));

        Color c2 = new Color((int)Math.max(0.85D * c.getRed(), 0), (int)Math.min(255, c.getGreen() * 1.15D), (int)Math.max(0.85D * c.getBlue(), 0));

        return c2.getRGB();
    }

    public static void registerBlockColors()
    {
        Minecraft.getInstance().getBlockColors().register(INSTANCE, ModBlocks.EARTH_RUNE.get());
    }
}
