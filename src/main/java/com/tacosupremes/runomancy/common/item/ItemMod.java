package com.tacosupremes.runomancy.common.item;

import com.tacosupremes.runomancy.common.Runomancy;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ItemMod extends Item{
	
	public int meta;
	
	
	
	public ItemMod(String s, int meta){
		this.setUnlocalizedName(s);
		this.setRegistryName(s);
		this.setCreativeTab(Runomancy.tab);
		GameRegistry.register(this);
		ModItems.items.add(this);
		this.meta = meta;
	}

	
	public ItemMod(String s){
		this(s,0);
	}
	
	
	@Override
	public String getUnlocalizedName(ItemStack stack) {
		
		if(stack.getItemDamage() > 0 && this.meta > 0)
			return super.getUnlocalizedName()+stack.getItemDamage();
		
		
		return super.getUnlocalizedName(stack);
	}



	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		
		if(this.meta == 0 || needsDifferentNames())
			return super.getItemStackDisplayName(stack);
		
		
		
		
		return I18n.translateToLocal(this.getUnlocalizedName(stack).replace(String.valueOf(stack.getMetadata()), "")+".name");
	}
	
	
	public boolean needsDifferentNames(){
		
		return false;
	}
	
	
	
}
