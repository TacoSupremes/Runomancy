package com.tacosupremes.runomancy.common.item;

import com.tacosupremes.runomancy.common.lib.LibMisc;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.List;

public class ItemSoulGem extends ItemMod
{
    public static final String ID = "id";
    public static final String ENTITY_TAG = "EntityTag";
    public static final String NAME = "name";
    public static final String ENAME = "CUSTOMNAME";

    @Override
    public String getItemRegistryName()
    {
        return "soul_gem";
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext context)
    {
        ItemStack is = context.getItem();

        World w = context.getWorld();

        BlockPos pos = context.getPos().add(context.getFace().getDirectionVec());

        if(hasEntity(is))
        {
            String[] id = is.getTag().getString(ID).split(":");

            Entity e = ForgeRegistries.ENTITIES.getValue(new ResourceLocation(id[0], id[1])).create(w);

            e.deserializeNBT(is.getTag().getCompound(ENTITY_TAG));

            e.setUniqueId(MathHelper.getRandomUUID(w.rand));

            e.setLocationAndAngles((double) pos.getX() + 0.5D, (double) pos.getY(), (double) pos.getZ() + 0.5D, MathHelper.wrapDegrees(w.rand.nextFloat() * 360.0F), 0.0F);

            w.addEntity(e);

            is.setTag(new CompoundNBT());
        }
        else
            return ActionResultType.PASS;

        return super.onItemUse(context);
    }

    private boolean hasEntity(ItemStack is)
    {
        return is.hasTag() && is.getTag().contains(ID);
    }

    public boolean hasEffect(ItemStack stack) {
        return hasEntity(stack);
    }

    @Override
    public boolean itemInteractionForEntity(ItemStack is, PlayerEntity player, LivingEntity e, Hand hand)
    {
        if(!is.hasTag())
            is.setTag(new CompoundNBT());

        if(!hasEntity(is))
        {
            is.getTag().putString(ID, EntityType.getKey(e.getType()).toString());
            is.getTag().put(ENTITY_TAG, e.serializeNBT());
            is.getTag().putString(NAME, e.getName().getString());

            if(e.hasCustomName())
                is.getTag().putString(ENAME, e.getType().getName().getString());

            e.remove(false);
        }

        return super.itemInteractionForEntity(is, player, e, hand);
    }

    @Override
    public void addInformation(ItemStack is, @Nullable World w, List<ITextComponent> tooltip, ITooltipFlag flagIn)
    {
        if(hasEntity(is))
        {
            String text = is.getTag().getString(NAME);

            if(is.getTag().contains(ENAME))
                text += String.format(" (%s)", is.getTag().getString(ENAME));

            tooltip.add(new StringTextComponent(I18n.format("runomancy.contains") + ": " + text).applyTextStyle(TextFormatting.GRAY));
        }
        else
        {
            ITextComponent comp = new TranslationTextComponent(LibMisc.MODID + "." + "empty").applyTextStyle(TextFormatting.GRAY);
            tooltip.add(comp);
        }
    }
}
