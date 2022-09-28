package com.tacosupremes.runomancy.common.item;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.Potions;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.datafix.fixes.EntityId;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

public class ItemPetBinder extends ItemMod
{
    public static final String ID = "ID";
    public static final String NAME = "NAME";

    @Override
    public String getItemRegistryName()
    {
        return "pet_binder";
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn)
    {
        if(isActive(stack))
        {
            if (!stack.hasTag() || !stack.getTag().contains(NAME))
                return;

            tooltip.add(new StringTextComponent(stack.getTag().getString(NAME)).applyTextStyle(TextFormatting.GRAY));
        }

    }

    @Override
    public boolean itemInteractionForEntity(ItemStack stack, PlayerEntity playerIn, LivingEntity target, Hand hand)
    {
        if(target instanceof TameableEntity)
        {
            TameableEntity te = (TameableEntity) target;

            if(te.isOwner(playerIn))
            {
                if(!stack.hasTag())
                    stack.setTag(new CompoundNBT());

                stack.getTag().putString(ID, te.getUniqueID().toString());

            }
        }

        return super.itemInteractionForEntity(stack, playerIn, target, hand);
    }

    @Override
    public void inventoryTick(ItemStack stack, World worldIn, Entity e2, int itemSlot, boolean isSelected)
    {
        if (isActive(stack))
        {

            LivingEntity e = getE(worldIn, e2, stack);

            if(e == null) {
                System.out.println("BRB");
                return;
            }

            if(e.getHealth() < e.getMaxHealth() && ((LivingEntity)e2).getHealth() > 1)
            {
                e.heal(0.5F);
                e2.attackEntityFrom(DamageSource.MAGIC, 0.5F);
            }

            if(e.isBurning() || e.isInLava())
            {
                e.addPotionEffect(Potions.FIRE_RESISTANCE.getEffects().get(0));
                e.extinguish();
            }
        }
    }

    public LivingEntity getE(World w, Entity e, ItemStack is)
    {
        List<TameableEntity> te = w.getEntitiesWithinAABB(TameableEntity.class, new AxisAlignedBB(e.getPosition().add(-10,-10,-10), e.getPosition().add(10,10,10)), (el) -> el.getUniqueID().toString() == is.getTag().getString(ID));

        if(te.isEmpty())
            return null;

        is.getTag().putString(NAME, te.get(0).getDisplayName().getString());

        return te.get(0);
    }

    public boolean hasEffect(ItemStack stack)
    {
        return isActive(stack);
    }

    private boolean isActive(ItemStack stack)
    {
        return stack.hasTag() && stack.getTag().contains(ID);
    }
}
