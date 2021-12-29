package com.tacosupremes.runomancy.proxy;

import com.tacosupremes.runomancy.common.item.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.thread.SidedThreadGroups;

public class CommonProxy
{
    public static boolean isPlayerHoldingItemStack()
    {
        if(Thread.currentThread().getThreadGroup() != SidedThreadGroups.SERVER)
            return Minecraft.getInstance().player.getHeldItemMainhand().getItem() == ModItems.RUNIC_WAND.get();

        return false;
    }
}
