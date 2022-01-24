package com.tacosupremes.runomancy.common.item;

import com.tacosupremes.runomancy.common.lib.LibMisc;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class ItemReturnCharm extends ItemMod
{
    public static final String LASTSKYPOS = "LASTSKYPOS";

    @Override
    public String getItemRegistryName()
    {
        return "return_charm";
    }

    @Override
    public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected)
    {
        if(worldIn.canBlockSeeSky(entityIn.getPosition().up()))
        {
            if(!stack.hasTag())
                stack.setTag(new CompoundNBT());

            stack.getTag().putLong(LASTSKYPOS, entityIn.getPosition().up().toLong());
        }
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn)
    {
        if(stack.hasTag() && stack.getTag().contains(LASTSKYPOS))
        {
            BlockPos dest = BlockPos.fromLong(stack.getTag().getLong(LASTSKYPOS));
            tooltip.add(new TranslationTextComponent(LibMisc.MODID + ".pos").appendText(String.format("x:{0} y:{1} z:{2}", dest.getX(), dest.getY(), dest.getZ())));
        }
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn)
    {
        BlockPos pos = playerIn.getPosition();

        if(!worldIn.canBlockSeeSky(pos))
        {
            BlockPos dest = BlockPos.fromLong(playerIn.getHeldItem(handIn).getTag().getLong(LASTSKYPOS));
            playerIn.attemptTeleport(dest.getX(), dest.getY(), dest.getZ(), worldIn.isRemote);

            if(!worldIn.isRemote())
                playerIn.setHeldItem(handIn, ItemStack.EMPTY);
        }

        return super.onItemRightClick(worldIn, playerIn, handIn);
    }
}
