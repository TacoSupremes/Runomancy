package com.tacosupremes.runomancy.common.runelogic;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.tacosupremes.runomancy.common.block.ModBlocks;
import com.tacosupremes.runomancy.common.block.rune.tile.TileEndRune;
import com.tacosupremes.runomancy.common.item.ItemSoulGem;
import com.tacosupremes.runomancy.common.item.ModItems;
import com.tacosupremes.runomancy.common.lib.LibMisc;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.Quaternion;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.lwjgl.opengl.GL11;

public class RuneEffectSoulGen implements IRuneEffect
{
    public static final String ITEM = "ITEM";
    public static final String CLAIM = "CLAIM";

    @Override
    public void doEffect(World w, BlockPos pos, TileEndRune te, CompoundNBT nbt)
    {
        if(te.getPower() < te.getMaxPower())
        {
            if (nbt.contains(ITEM))
            {
                LivingEntity e = ItemSoulGem.getEntity(nbt.getCompound(ITEM), w);

                if(e.getHealth() >= 0.5F)
                {
                    e.setPosition(pos.getX() + 0.5F, pos.getY() + 0.2F + 1, pos.getZ() + 0.5F);
                   // e.setHealth(e.getHealth() - 0.5F);
                    //te.addPower(50);
                    ItemSoulGem.entityToNBT(nbt.getCompound(ITEM), e);
                    //System.out.println("\n\n" + "HP:ENTITY" + e.getHealth() + "\n\n");
                }
                else
                {
                    nbt.putBoolean(CLAIM, true);
                    nbt.remove(ITEM);
                }
            }
        }
    }

    @Override
    public Block[] getNeededBlocks()
    {
        Block e = ModBlocks.END_RUNE.get();
        Block o = ModBlocks.OBSIDIAN_RUNE.get();
        Block s = ModBlocks.SOUL_RUNE.get();
        Block n = null;

        return new Block[]{
                o,o,o,o,o,
                o,n,s,n,o,
                o,s,e,s,o,
                o,n,s,n,o,
                o,o,o,o,o
        };
    }

    @Override
    public boolean isGenerating()
    {
        return true;
    }

    @Override
    public int[] getFinalBlockStates()
    {
        return new int[]{
                1,5,5,5,2,
                8,0,1,0,6,
                8,1,8,1,6,
                8,0,1,0,6,
                3,7,7,7,4
        };
    }

    @Override
    public int getPowerCapacity()
    {
        return 8000;
    }

    @Override
    public int getTransferRate()
    {
        return 50;
    }

    @Override
    public String getName()
    {
        return LibMisc.MODID + ".soul.tap.effect";
    }

    @Override
    public ActionResultType onRightClick(World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit, CompoundNBT nbt)
    {
        ItemStack is = player.getHeldItem(Hand.MAIN_HAND);

        if(!nbt.contains(ITEM) && !nbt.getBoolean(CLAIM) && is.getItem() == ModItems.SOUL_GEM.get() && ItemSoulGem.hasEntity(is))
        {
            nbt.put(ITEM, is.getTag());
            if(!worldIn.isRemote())
                player.setHeldItem(handIn, ItemStack.EMPTY);

            return ActionResultType.CONSUME;
        }
        else if(nbt.getBoolean(CLAIM))
        {
           if(is.isEmpty())
           {
                if(!worldIn.isRemote())
                    player.setHeldItem(Hand.MAIN_HAND, new ItemStack(ModItems.SOUL_GEM.get(), 1, new CompoundNBT()));

                nbt.putBoolean(CLAIM, false);
                nbt.remove(ITEM);

                return ActionResultType.SUCCESS;
            }
        }

        return ActionResultType.PASS;
    }

    @Override
    public boolean hasSpecialRender()
    {
        return true;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void render(TileEndRune tileEntityIn, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn, CompoundNBT nbt)
    {
        if (nbt.contains(ITEM))
        {
            LivingEntity e = ItemSoulGem.getEntity(nbt.getCompound(ITEM), Minecraft.getInstance().world);
            e.setPositionAndRotation(0,0,0,0,0);


            matrixStackIn.push();
            matrixStackIn.translate(0,1.25D + (Math.sin((double)tileEntityIn.ticks / 16D) / 2),0);
            matrixStackIn.rotate(new Quaternion(0, tileEntityIn.ticks % 360,0,true));

            EntityRenderer s = Minecraft.getInstance().getRenderManager().getRenderer(e);
            s.render(e, e.rotationYaw, partialTicks, matrixStackIn,  bufferIn, combinedLightIn);

            ItemSoulGem.entityToNBT(nbt.getCompound(ITEM), e);
            matrixStackIn.pop();
        }
    }

}
