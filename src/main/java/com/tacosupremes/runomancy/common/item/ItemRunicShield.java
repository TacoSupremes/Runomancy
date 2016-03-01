package com.tacosupremes.runomancy.common.item;

import com.tacosupremes.runomancy.common.Runomancy;
import com.tacosupremes.runomancy.gui.Categories;
import com.tacosupremes.runomancy.gui.IPageGiver;
import com.tacosupremes.runomancy.gui.ItemPage;
import com.tacosupremes.runomancy.gui.Page;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;

public class ItemRunicShield extends ItemMod implements IPageGiver {

	public ItemRunicShield() {
		super("mobRepeller");
		this.setMaxDamage(1000);
		
	}

	@Override
	public Page getPage() {

		return new ItemPage(new ItemStack(this));

	}

	@Override
	public Categories getCategories() {

		return Categories.Item;

	}

	@Override
	public boolean hasNormalRecipe() {

		return true;

	}

	@Override
	public Page getSubPages() {

		return null;

	}

	@Override
	public void onUpdate(ItemStack is, World w, Entity e, int itemSlot, boolean isSelected) {
	
		Entity e2 = w.findNearestEntityWithinAABB(EntityLivingBase.class, new AxisAlignedBB(e.getPosition().add(-3, -2, -3), e.getPosition().add(3, 2, 3)), e);
		
		if(e2 != null){
			
		BlockPos bp = e2.getPosition().subtract(e.getPosition());
			
			e2.motionX = bp.getX() * 0.75D;
			//e2.motionY = -bp.getY();
			e2.motionZ = bp.getZ() * 0.75D;
			is.setItemDamage(is.getItemDamage() + 1);
			
			for(int i = 0; i<= 8; i++){
				w.spawnParticle(EnumParticleTypes.CRIT_MAGIC, e2.posX+Runomancy.rand.nextGaussian()/4 - Runomancy.rand.nextGaussian()/4, e2.posY+1, e2.posZ+Runomancy.rand.nextGaussian()/4 - Runomancy.rand.nextGaussian()/4, 0, 0, 0, 0);
			}
			return;
		}
		
	}
	
	

}
