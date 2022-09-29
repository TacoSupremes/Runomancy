package com.tacosupremes.runomancy.common.item;

import com.tacosupremes.runomancy.common.utils.BlockUtils;
import com.tacosupremes.runomancy.common.utils.Vector3;
import net.minecraft.block.Blocks;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class ItemLavaGem extends ItemMod
{
    public static final String LAVAEAT = "LAVAEAT";
    public static final String LAVA = "LAVA";
    public static final int MAX_LAVA = 543;

    @Override
    public String getItemRegistryName()
    {
        return "lava_gem";
    }

    public ItemLavaGem()
    {
        super(getDefaultProps());
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn)
    {
        if(stack.hasTag())
            tooltip.add(new StringTextComponent(""+stack.getTag().getInt(LAVA)));
        else
            tooltip.add(new TranslationTextComponent("runomancy.empty"));
    }

    public List<BlockPos> SList(BlockPos start, int r)
    {
        List<BlockPos> l = new ArrayList<BlockPos>();

        return l;
    }

    @Override
    public boolean onEntityItemUpdate(ItemStack stack, ItemEntity entity)
    {
        if(entity.getPersistentData().getBoolean(LAVAEAT))
        {
            int n = 9;
            CompoundNBT nbt = stack.hasTag() ? stack.getTag() : new CompoundNBT();

            if(nbt.getInt(LAVA) < MAX_LAVA)
            {
                for (int y = 0; y >= -3; y--)
                {
                    for (int x = -n; x <= n; x++)
                    {
                        for (int z = -n; z <= n; z++)
                        {
                            if(Math.abs(x) + Math.abs(z) <= n)
                            {
                                BlockPos p = entity.getPosition().down(2).add(x, y, z);
                                if (entity.getEntityWorld().getBlockState(p).getBlock() == Blocks.LAVA)
                                {
                                    entity.getEntityWorld().setBlockState(p, Blocks.OBSIDIAN.getDefaultState());
                                    nbt.putInt(LAVA, nbt.getInt(LAVA) + 1);
                                    BlockUtils.drawLine(entity.getEntityWorld(), Vector3.fromEntity(entity), Vector3.fromBlockPos(p).add(0.5), ParticleTypes.LAVA);
                                    entity.getEntityWorld().addParticle(ParticleTypes.SMOKE, entity.prevPosX, entity.prevPosY, entity.prevPosZ, 0, 0.5, 0);
                                    entity.setPositionAndRotation(entity.prevPosX, entity.prevPosY, entity.prevPosZ, entity.prevRotationYaw + 90, entity.prevRotationPitch + 1F);
                                    entity.getItem().setTag(nbt);
                                    return true;
                                }
                            }
                        }
                    }
                }
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
                entity.setVelocity(0,0.5,0);
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
        return stack.hasTag() && stack.getTag().getInt(LAVA) == MAX_LAVA;
    }

    @Override
    public Rarity getRarity(ItemStack stack)
    {
        int i = stack.hasTag() ? stack.getTag().getInt(LAVA) : 0;

        return Rarity.values()[Math.min(3,Math.max(0, ((int)(4D * (i / (double)MAX_LAVA))) - 1))];
    }
}
