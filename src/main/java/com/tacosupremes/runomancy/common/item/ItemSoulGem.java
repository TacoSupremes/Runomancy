package com.tacosupremes.runomancy.common.item;

import com.tacosupremes.runomancy.common.block.rune.tile.TileEndRune;
import com.tacosupremes.runomancy.common.lib.LibMisc;
import com.tacosupremes.runomancy.common.runelogic.RuneEffectSoulGen;
import com.tacosupremes.runomancy.common.runelogic.RuneFormations;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
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

    public ItemSoulGem()
    {
        super(ItemMod.getDefaultProps().maxStackSize(1));
    }

    public static LivingEntity getEntity(ItemStack is, World w)
    {
        return  getEntity(is.getTag(), w);
    }

    public static LivingEntity getEntity(CompoundNBT nbt, World w)
    {
        String[] id = nbt.getString(ID).split(":");

        LivingEntity e = (LivingEntity) ForgeRegistries.ENTITIES.getValue(new ResourceLocation(id[0], id[1])).create(w);

        e.deserializeNBT(nbt.getCompound(ENTITY_TAG));

        e.setUniqueId(MathHelper.getRandomUUID(w.rand));

        return e;
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext context)
    {
        ItemStack is = context.getItem();

        World w = context.getWorld();

        BlockPos pos = context.getPos().add(context.getFace().getDirectionVec());

        if(hasEntity(is))
        {
            if(w.getTileEntity(context.getPos()) instanceof TileEndRune)
            {
                TileEndRune te = (TileEndRune) w.getTileEntity(context.getPos());

                if(te.getEffect() == RuneFormations.runeEffectByName(RuneEffectSoulGen.EFFECT_NAME))
                {
                    return ActionResultType.PASS;
                }
            }

            Entity e = getEntity(is.getTag(), w);

            e.setLocationAndAngles((double) pos.getX() + 0.5D, (double) pos.getY(), (double) pos.getZ() + 0.5D, MathHelper.wrapDegrees(w.rand.nextFloat() * 360.0F), 0.0F);

            w.addEntity(e);

            is.setTag(new CompoundNBT());
        }
        else
            return ActionResultType.FAIL;

        return super.onItemUse(context);
    }

    public static boolean hasEntity(ItemStack is)
    {
        return is.hasTag() && is.getTag().contains(ID);
    }

    public boolean hasEffect(ItemStack stack) {
        return hasEntity(stack);
    }

    public static void entityToNBT(CompoundNBT nbt, LivingEntity e)
    {
        nbt.putString(ID, EntityType.getKey(e.getType()).toString());
        nbt.put(ENTITY_TAG, e.serializeNBT());
        nbt.putString(NAME, e.getName().getString());

        if(e.hasCustomName())
            nbt.putString(ENAME, e.getType().getName().getString());
    }

    @Override
    public boolean itemInteractionForEntity(ItemStack is, PlayerEntity player, LivingEntity e, Hand hand)
    {
        if(!is.hasTag())
            is.setTag(new CompoundNBT());

        if(!hasEntity(is))
        {
            entityToNBT(is.getTag(), e);

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
