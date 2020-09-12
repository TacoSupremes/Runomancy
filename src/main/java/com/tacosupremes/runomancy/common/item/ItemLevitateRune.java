package com.tacosupremes.runomancy.common.item;

import net.minecraft.client.particle.Particle;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.world.World;

import java.util.Random;

public class ItemLevitateRune extends ItemMod {

	private Random rand;


	public ItemLevitateRune()
	{
		super();
		this.rand = new Random();
	}

	@Override
	public void inventoryTick(ItemStack stack, World w, Entity e, int itemSlot, boolean isSelected) {
		
		
		if(e instanceof PlayerEntity){
			
			PlayerEntity player = (PlayerEntity)e;
			
			if(player.isSneaking() && player.isAirBorne && Math.abs(player.getMotion().y) > 0 && !player.collided){
				
				//player.motionY /= 1.25D;

				player.setMotion(player.getMotion().mul(1,1D / 1.25D,1));


				double mX = player.getMotion().x;
				double mY = player.getMotion().y;
				double mZ = player.getMotion().z;


				for(int i = 0; i< 4; i++){



				w.addParticle(ParticleTypes.FIREWORK, player.getPosX() + rand.nextDouble()/2-rand.nextDouble()/2, player.getPosY()-0.2D, player.getPosZ()+rand.nextDouble()/2-rand.nextDouble()/2, -mX, -mY, -mZ);
				}
				
			}
			
			if(isSelected && player.fallDistance > 0)
				player.fallDistance = 0;
					
			if(player.fallDistance > 0 && player.fallDistance < 5 && player.getMotion().y < 0)
				player.fallDistance = 0;

						
					
					
					
					
				
				
				
				
			
			
			
		}
		
		
		
		
	}


	@Override
	public String getItemRegistryName()
	{
		return "levitate_rune";
	}
}
