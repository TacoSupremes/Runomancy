package com.tacosupremes.runomancy.common.item;

import com.tacosupremes.runomancy.common.block.tile.INode;
import com.tacosupremes.runomancy.common.utils.BlockUtils;
import com.tacosupremes.runomancy.common.utils.Vector3;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.RedstoneParticleData;
import net.minecraft.util.DamageSource;

import java.util.Set;

public class ItemSharpSword extends SwordItem implements IModItem
{
    @Override
    public boolean hitEntity(ItemStack stack, LivingEntity target, LivingEntity attacker)
    {
        return super.hitEntity(stack, target, attacker);
    }

    public ItemSharpSword(Properties builder)
    {
        super(ItemTier.STONE, 1, -1.8F, builder);
    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, PlayerEntity player, Entity entity)
    {
        LivingEntity e = (LivingEntity)entity;

        entity.attackEntityFrom(DamageSource.causePlayerDamage(player), getAttackDamage(stack));
        if(!e.isAlive())
        {
            if(!stack.hasTag())
                stack.setTag(new CompoundNBT());

            BlockUtils.drawLine(entity.world, Vector3.fromEntityCenter(entity), Vector3.fromEntityCenter(player), RedstoneParticleData.REDSTONE_DUST);

            stack.getTag().putInt("KILLS", stack.getTag().getInt("KILLS") + 1);
            return true;
        }

        stack.damageItem(2, player, (playerEntity) -> {});
        return true;
    }

    private float getAttackDamage(ItemStack stack)
    {
        return getAttackDamage() + (stack.hasTag() ? stack.getTag().getInt("KILLS") : 0F);
    }

    @Override
    public String getItemRegistryName()
    {
        return "sharp_sword";
    }
}
