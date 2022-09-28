package com.tacosupremes.runomancy.common.item;

import net.minecraft.block.Blocks;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class ItemLavaGem extends ItemMod
{
    public static final String LAVAEAT = "LAVAEAT";

    @Override
    public String getItemRegistryName()
    {
        return "lava_gem";
    }

    //public


    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn)
    {
        if(stack.hasTag())
            tooltip.add(new StringTextComponent(""+stack.getTag().getInt("LAVA")));
    }

    @Override
    public boolean onEntityItemUpdate(ItemStack stack, ItemEntity entity)
    {
        if(entity.getPersistentData().getBoolean(LAVAEAT))
        {
            int n = 7;
            CompoundNBT nbt = stack.hasTag() ? stack.getTag() : new CompoundNBT();

            if(nbt.getInt("LAVA") < 2500)
            {
                outer:
                for (int x = -n; x <= n; x++)
                {
                    for (int y = -n; y <= 0; y++)
                    {
                        for (int z = -n; z <= n; z++)
                        {
                            BlockPos p = entity.getPosition().down(2).add(x, y, z);
                            if (entity.getEntityWorld().getBlockState(p).getBlock() == Blocks.LAVA)
                            {
                                entity.getEntityWorld().setBlockState(p, Blocks.OBSIDIAN.getDefaultState());
                                nbt.putInt("LAVA", nbt.getInt("LAVA") + 1);
                            }

                            if(nbt.getInt("LAVA") == 2500)
                            {
                                break outer;
                            }
                        }
                    }
                }
                entity.getItem().setTag(nbt);
            }
            entity.getPersistentData().putBoolean(LAVAEAT, false);
            entity.setPickupDelay(0);
        }
        else
        {
            if(entity.isInLava())
            {
                entity.setInvulnerable(true);
                entity.extinguish();
                entity.setPositionAndUpdate(entity.prevPosX, entity.prevPosY+2.75D, entity.prevPosZ);
                entity.getPersistentData().putBoolean(LAVAEAT, true);
                entity.setPickupDelay(100000);
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean hasEffect(ItemStack stack)
    {
        return stack.hasTag() && stack.getTag().getInt("LAVA") == 2500;
    }

    @Override
    public Rarity getRarity(ItemStack stack)
    {
        int i = stack.hasTag() ? stack.getTag().getInt("LAVA") : 0;

        return Rarity.values()[Math.max(0,((int)(4D*(i/2500D)))-1)];
    }
}
