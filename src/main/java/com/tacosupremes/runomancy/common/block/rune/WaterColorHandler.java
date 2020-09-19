package com.tacosupremes.runomancy.common.block.rune;

import com.tacosupremes.runomancy.common.block.ModBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ILightReader;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.Nullable;
import java.awt.*;

public class WaterColorHandler implements IBlockColor
{
    public static final IBlockColor INSTANCE = new WaterColorHandler();

    @Override
    @OnlyIn(Dist.CLIENT)
    public int getColor(BlockState state, @Nullable ILightReader iLightReader, @Nullable BlockPos pos, int p_getColor_4_)
    {
        Color c = new Color(Minecraft.getInstance().world.getBiome(pos).getWaterColor());

        Color c2 = new Color((int)Math.max(0.85D * c.getRed(), 0), (int)Math.max(0, c.getGreen() * 0.85D), (int)Math.min(1.15D * c.getBlue(), 255));

        if(state.getBlock() == ModBlocks.END_RUNE.get() && state != ModBlocks.END_RUNE.get().getDefaultState().with(BlockEndRune.mode, 7))
            return Color.WHITE.getRGB();

        return c2.getRGB();
    }

    public static void registerBlockColors()
    {
        Minecraft.getInstance().getBlockColors().register(INSTANCE, ModBlocks.WATER_RUNE.get(), ModBlocks.END_RUNE.get());
    }
}
