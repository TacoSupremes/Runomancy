package com.tacosupremes.runomancy.common.item;

import com.tacosupremes.runomancy.common.Runomancy;
import net.minecraft.item.Item;

public abstract class ItemMod extends Item implements IModItem
{
    public ItemMod()
    {
        this(ItemMod.getDefaultProps());
    }

    public ItemMod(Properties props)
    {
        super(props);
    }

    public static Properties getDefaultProps()
    {
        return new Item.Properties().group(Runomancy.TAB);
    }

}
