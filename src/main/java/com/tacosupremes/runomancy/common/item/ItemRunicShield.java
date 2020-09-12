package com.tacosupremes.runomancy.common.item;


import com.tacosupremes.runomancy.common.Runomancy;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public class ItemRunicShield extends ItemMod  {

	@Override
	public void inventoryTick(ItemStack is, World w, Entity e, int itemSlot, boolean isSelected)
	{
		List<Entity> l = w.getEntitiesInAABBexcluding(e, new AxisAlignedBB(e.getPosition().add(-3, -2, -3), e.getPosition().add(3, 2, 3)), null);

		for (Entity e2 : l)
		{
			if (e2 != null)
			{
				BlockPos bp = e2.getPosition().subtract(e.getPosition());

				e2.setMotion(bp.getX() * 0.75D, e2.getMotion().y, bp.getZ() * 0.75D);

				for (int i = 0; i <= 8; i++)
				{
					w.addParticle(ParticleTypes.CRIT, e2.getPosX() + Runomancy.rand.nextGaussian() / 4 - Runomancy.rand.nextGaussian() / 4, e2.getPosY() + 1, e2.getPosZ() + Runomancy.rand.nextGaussian() / 4 - Runomancy.rand.nextGaussian() / 4, 0, 0, 0);
				}
				return;
			}

		}
	}


	@Override
	public String getItemRegistryName()
	{
		return null;
	}
}
