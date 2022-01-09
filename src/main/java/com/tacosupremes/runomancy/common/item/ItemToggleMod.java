package com.tacosupremes.runomancy.common.item;

import com.tacosupremes.runomancy.common.lib.LibMisc;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.*;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

public abstract class ItemToggleMod extends ItemMod
{
    public static final String ACTIVE = "ACTIVE";

    public ItemToggleMod(int maxDamage)
    {
        this(ItemMod.getDefaultProps().maxStackSize(1).maxDamage(maxDamage));
    }

    public ItemToggleMod()
    {
        this(ItemMod.getDefaultProps().maxStackSize(1));
    }

    public ItemToggleMod(Properties props)
    {
        super(props);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn)
    {
        boolean active = isActive(stack);

        String status = active ? "active" : "inactive";

        String message = LibMisc.MODID + "." + status;

        ITextComponent comp = new TranslationTextComponent(message);
        comp.setStyle(comp.getStyle().setColor(active ? TextFormatting.GREEN : TextFormatting.RED));

        tooltip.add(comp);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World w, PlayerEntity player, Hand hand)
    {
        if(!player.isSneaking())
            return super.onItemRightClick(w, player, hand);

        ItemStack is = player.getHeldItem(hand);

        if(!is.hasTag())
        {
            is.setTag(new CompoundNBT());
            is.getTag().putBoolean(ACTIVE, false);
        }

        is.getTag().putBoolean(ACTIVE, !is.getTag().getBoolean(ACTIVE));

        return super.onItemRightClick(w, player, hand);
    }

    public boolean hasEffect(ItemStack stack)
    {
        return isActive(stack);
    }

    @Override
    public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected)
    {
        if(isActive(stack))
            activeTick(stack, worldIn, entityIn, itemSlot, isSelected);
    }

    public abstract void activeTick(ItemStack is, World w, Entity e, int itemSlot, boolean isSelected);

    public boolean isActive(ItemStack stack)
    {
        return stack.hasTag() && stack.getTag().getBoolean(ACTIVE);
    }
}
