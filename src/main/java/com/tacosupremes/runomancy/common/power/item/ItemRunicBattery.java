package com.tacosupremes.runomancy.common.power.item;

import java.awt.Color;
import java.util.List;

import com.tacosupremes.runomancy.common.item.ItemMod;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemRunicBattery extends ItemMod implements IRunicBattery{

	public ItemRunicBattery() {
		super("runicBattery",0);
		this.setHasSubtypes(true);
		
		
	}

	@Override
	public int getPower(ItemStack is) {
		
		if(!is.hasTagCompound()){
			is.setTagCompound(new NBTTagCompound());
			is.getTagCompound().setInteger("POWER", 0);
			return 0;
		}
		return is.getTagCompound().getInteger("POWER");
	}

	@Override
	public int addPower(ItemStack is, int a, boolean doit) {
		if(!is.hasTagCompound()){
			is.setTagCompound(new NBTTagCompound());
			is.getTagCompound().setInteger("POWER", 0);
			
		}
		int powerAdded = Math.min(this.getCapacity(is)-this.getPower(is), a);
		if(doit)
		is.getTagCompound().setInteger("POWER", this.getPower(is)+powerAdded);
		
		
		return powerAdded;
	}

	@Override
	public int removePower(ItemStack is, int a, boolean doit) {
		if(!is.hasTagCompound()){
			is.setTagCompound(new NBTTagCompound());
			is.getTagCompound().setInteger("POWER", 0);
			
		}
		int powerRemoved = Math.min(this.getPower(is), a);
		if(doit)
		is.getTagCompound().setInteger("POWER", this.getPower(is)-powerRemoved);
		
		
		return powerRemoved;
	}

	@Override
	public int getCapacity(ItemStack is) {
		
		return 8000;
	}
	
	@Override
    @SideOnly(Side.CLIENT)
	public int getColorFromItemStack(ItemStack is, int pass) {
		if(pass == 1)
			return super.getColorFromItemStack(is, pass);
		
		if(!is.hasTagCompound() || is.getTagCompound().getInteger("POWER") < 1600)
			return Color.HSBtoRGB(0.75F,  0.2F, 1F);
		    
	        return Color.HSBtoRGB(0.75F, ((float) is.getTagCompound().getInteger("POWER")) /(float) this.getCapacity(is), 0.8F);
	    }
	
	

	@Override
	public void getSubItems(Item item, CreativeTabs tab, List<ItemStack> subItems) {
		
		subItems.add(new ItemStack(item));
		ItemStack is = new ItemStack(item);
		
		this.setPower(is, this.getCapacity(is));
		
		subItems.add(is);
		
	}

	@Override
	public void setPower(ItemStack is, int a) {
			
		if(!is.hasTagCompound()){
			is.setTagCompound(new NBTTagCompound());
			is.getTagCompound().setInteger("POWER", a);
			return;
			
		}
		is.getTagCompound().setInteger("POWER", a);
	}

	@Override
	public void addInformation(ItemStack is, EntityPlayer player, List<String> l, boolean advanced) {
		
		if(!is.hasTagCompound() || is.getTagCompound().getInteger("POWER") == 0){
			
			l.add(StatCollector.translateToLocal("runomancymisc.empty"));
			return;
		}
		
		l.add(StatCollector.translateToLocal("runomancymisc.contains")+": "+is.getTagCompound().getInteger("POWER"));
			
		
	}

}
