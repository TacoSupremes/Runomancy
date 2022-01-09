package com.tacosupremes.runomancy.common.item;


import com.tacosupremes.runomancy.common.Runomancy;
import net.minecraft.entity.Entity;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;
import java.util.function.Predicate;

public class ItemRunicShield extends ItemToggleMod
{
	public ItemRunicShield()
	{
		super(3500);
	}

	@Override
	public void activeTick(ItemStack is, World w, Entity e, int itemSlot, boolean isSelected)
	{
		int r = 4;

		List<Entity> l = w.getEntitiesInAABBexcluding(e, new AxisAlignedBB(e.getPosition().add(-r, -r, -r), e.getPosition().add(r, r, r)), shouldReflect);

		for (Entity e2 : l)
		{
			if(is.getDamage() < (is.getMaxDamage() - 1) - 2)
			{
				BlockPos bp = e2.getPosition().subtract(e.getPosition());

				e2.setMotion(bp.getX() * 0.75D, e2.getMotion().y * 0.75D, bp.getZ() * 0.75D);
				is.setDamage(is.getDamage() + 2);

				w.addParticle(ParticleTypes.CRIT, e2.getPosX() + Runomancy.rand.nextGaussian() / 4 - Runomancy.rand.nextGaussian() / 4, e2.getPosY() + 1, e2.getPosZ() + Runomancy.rand.nextGaussian() / 4 - Runomancy.rand.nextGaussian() / 4, 0, 0, 0);

			}
		}


	}

	public Predicate<Entity> shouldReflect = (e) -> e instanceof LivingEntity || e instanceof  IProjectile;

	@Override
	public String getItemRegistryName()
	{
		return "runic_shield";
	}
}
