package com.tacosupremes.runomancy.common.power.item;

import java.util.Random;

import com.tacosupremes.runomancy.common.item.ItemMod;
import com.tacosupremes.runomancy.common.power.PowerHelper;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;

public class ItemLevitateRune extends ItemMod {

	private Random rand;

	public ItemLevitateRune() {
		super("levitateRune", 1);
		this.setMaxDamage(0);
		this.setMaxStackSize(1);
		this.rand = new Random();
		
	}

	

	@Override
	public void onUpdate(ItemStack is, World w, Entity e, int itemSlot, boolean isSelected) {
		
		
		if(e instanceof EntityPlayer){
			
			EntityPlayer player = (EntityPlayer)e;
			
			if(player.isSneaking() && player.isAirBorne && Math.abs(player.motionY) > 0){
				
				player.motionY /= 1.25D;
				if(player.moveForward > 0)
					 player.moveFlying(0.0F, 1.0F,  0.085F);
				for(int i = 0; i< 4; i++){
					
				w.spawnParticle(EnumParticleTypes.FIREWORKS_SPARK, player.posX+rand.nextDouble()/2-rand.nextDouble()/2, player.posY-0.2D, player.posZ+rand.nextDouble()/2-rand.nextDouble()/2, -player.motionX, -player.motionY, -player.motionZ);
				}
				
			}
			
			if(isSelected && player.fallDistance > 0)
				player.fallDistance = 0;
				
			
			
			if(PowerHelper.getBattery(player.inventory, false) != null){
				
				ItemStack bat = PowerHelper.getBattery(player.inventory, false);
				
				int pow = ((IRunicBattery)bat.getItem()).getPower(bat);
				
				if(pow > 0){
					
					if(is.getItemDamage() != 1)
						is.setItemDamage(1);
					
					
				
					
					if(player.fallDistance > 0 && player.fallDistance < 5 && player.motionY < 0){
						
						if(pow > (int)player.fallDistance*80){
							
							((IRunicBattery)bat.getItem()).removePower(bat, (int)player.fallDistance*80, true);
							player.fallDistance = 0;
							
							
						}else{
							
							int avail = pow / 80;
							
							player.fallDistance -= avail;
							((IRunicBattery)bat.getItem()).setPower(bat, 0);
							
						}
						
					}
					
					
					
				}else
					is.setItemDamage(0);
				
				
				
			}else
				is.setItemDamage(0);
			
			
			
		}
		
		
		
		
	}
	
	
	
	public boolean onDroppedByPlayer(ItemStack item, EntityPlayer player)
    {
        return true;
    }
	
	

}
