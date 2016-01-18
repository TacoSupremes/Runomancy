package com.tacosupremes.runomancy.common.power.item.tool;

import java.util.List;

import com.tacosupremes.runomancy.common.Runomancy;
import com.tacosupremes.runomancy.common.item.ModItems;
import com.tacosupremes.runomancy.common.power.PowerHelper;
import com.tacosupremes.runomancy.common.power.item.IRunicBattery;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemRunicHoe extends ItemHoe {

	public ItemRunicHoe() {
		super(ModItems.runic);
		this.setUnlocalizedName("runicHoe");
		this.setCreativeTab(Runomancy.tab);
		this.setHasSubtypes(true);
		GameRegistry.registerItem(this, this.getUnlocalizedName().substring(5));
		ModItems.nitems.add(this);
		this.setNoRepair();
	}

	

	@Override
	public float getStrVsBlock(ItemStack stack, Block block) {
	
		return stack.getItemDamage() == stack.getMaxDamage()-1 ? super.getStrVsBlock(stack, block) / 12F :super.getStrVsBlock(stack, block);
	}

	
	

	@Override
	public boolean isItemTool(ItemStack stack) {
		
		return true;
	}

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing side,
			float hitX, float hitY, float hitZ) {
		
		if(stack.getItemDamage() == stack.getMaxDamage()-1)
			return false;
		
		return super.onItemUse(stack, playerIn, worldIn, pos, side, hitX, hitY, hitZ);
	}



	@Override
	public float getDigSpeed(ItemStack stack, IBlockState state) {
		
		return this.getStrVsBlock(stack, state.getBlock());
	}



	


	@Override
	public void onUpdate(ItemStack stack, World worldIn, Entity e, int itemSlot, boolean isSelected) {
		
	if(stack.getItemDamage() == 1)
		return;
	
	if(!(e instanceof EntityPlayer))
		return;
	
	if(PowerHelper.getBattery(((EntityPlayer)e).inventory,false) == null)
		return;
	
	ItemStack battery = PowerHelper.getBattery(((EntityPlayer)e).inventory,false);
	
	IRunicBattery bat = (IRunicBattery)battery.getItem();
		
	if(bat.removePower(battery, 450, false) == 450){
		stack.setItemDamage(stack.getItemDamage()-1);
		bat.removePower(battery, 450, true);
	}
		
		
		
	}



	@Override
	public void getSubItems(Item item, CreativeTabs tab, List<ItemStack> l) {
		
		
		l.add(new ItemStack(item,1,1));
		l.add(new ItemStack(item,1,item.getMaxDamage()-1));
		
		
	}



	@Override
	public boolean onBlockDestroyed(ItemStack stack, World worldIn, Block blockIn, BlockPos pos,
			EntityLivingBase playerIn) {
		
			if(stack.getItemDamage() == stack.getMaxDamage()-1)
				return true;
		
		
		return super.onBlockDestroyed(stack, worldIn, blockIn, pos, playerIn);
	}



	
	
	
	

}
