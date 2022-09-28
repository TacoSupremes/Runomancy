package com.tacosupremes.runomancy.common.item;

import com.tacosupremes.runomancy.common.block.rune.IRune;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potions;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public class ItemDecayHeal extends ItemToggleMod
{

    @Override
    public String getItemRegistryName()
    {
        return "decay_heal";
    }

    @Override
    public void activeTick(ItemStack is, World w, Entity e2, int itemSlot, boolean isSelected)
    {
        LivingEntity e = (LivingEntity) e2;

        if(e.getHealth() < e.getMaxHealth())
        {
            e.setHealth(e.getHealth() + 0.5F);
            corruptWorld(w, e2.getPosition());
            e.addPotionEffect(Potions.LONG_SLOWNESS.getEffects().get(0));
        }
    }

    public static void corruptWorld(World w, BlockPos pos)
    {
        int r = 5;

        for(int x = -r; x <=r; x++)
            for(int y = -r; y <=r; y++)
                for(int z = -r; z <=r; z++)
                {
                    BlockPos pos_ = pos.add(x,y,z);
                    BlockState state_ = w.getBlockState(pos_);

                    if(w.rand.nextInt(100) >= 70)
                        continue;


                    if(!state_.isAir(w, pos_) && w.getTileEntity(pos_) == null && !(state_.getBlock() instanceof IRune))
                    {
                        w.setBlockState(pos_, getRandomCorruptionState(w.rand));
                    }

                }
    }

    public static BlockState getRandomCorruptionState(Random random)
    {
        BlockState[] bs = new BlockState[]{Blocks.NETHERRACK.getDefaultState(), Blocks.END_STONE.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), Blocks.OBSIDIAN.getDefaultState(), Blocks.ANDESITE.getDefaultState()};

        return bs[random.nextInt(bs.length)];
    }
}
