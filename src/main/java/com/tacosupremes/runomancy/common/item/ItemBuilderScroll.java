package com.tacosupremes.runomancy.common.item;

import java.util.ArrayList;
import java.util.List;

import com.tacosupremes.runomancy.common.block.ModBlocks;
import com.tacosupremes.runomancy.common.lib.LibMisc;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.wrappers.FluidBucketWrapper;

public class ItemBuilderScroll extends ItemMod{

	public ItemBuilderScroll() {
		super("builderScroll", 1);
		this.setMaxStackSize(1);
	}
	
	
	
	
	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World w, BlockPos pos,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		
		if(w.getBlockState(pos).getBlock() != ModBlocks.marker)
			buildStructure2(w,pos.up(), player.getHeldItem(player.getActiveHand()), player);
		
		return super.onItemUse(player, w, pos, hand, facing, hitX, hitY, hitZ);
		
	}


public void buildStructure2(World w, BlockPos pos, ItemStack is, EntityPlayer player){
		
		if(is.getItemDamage() != 1)
			return;
		
		
		
		
		
		boolean xN =  is.getTagCompound().getCompoundTag("DIM").getInteger("xD") < 0;
		boolean yN =  is.getTagCompound().getCompoundTag("DIM").getInteger("yD") < 0;
		boolean zN =  is.getTagCompound().getCompoundTag("DIM").getInteger("zD") < 0;
		
		int x = xN ? -1 : 1;
		int y = 0;
		int z = zN ? -1 : 1;
		for(int i = 0; i<= is.getTagCompound().getCompoundTag("BLOCKS").getSize(); i++){
			
			int iD = is.getTagCompound().getCompoundTag("BLOCKS").getInteger("BLOCK"+i);
			
			BlockPos bp = pos.add(x, y, z);
			
			
			if(iD != -1){
				
				w.setBlockState(bp, Block.getStateById(iD));
				
			}
			
			z += zN ? -1 : 1;
			
			if(z == is.getTagCompound().getCompoundTag("DIM").getInteger("zD")){
				z = zN ? -1 : 1;
				x += xN ? -1 : 1;
			}
			
			if(x == is.getTagCompound().getCompoundTag("DIM").getInteger("xD")){
				x = xN ? -1 : 1;
				y += yN ? -1 : 1;
			}
			

		}
}

	public void buildStructure(World w, BlockPos pos, ItemStack is, EntityPlayer player){
		
		if(is.getItemDamage() != 1)
			return;
		
		int index = 0;
		
		
					
					int xO = is.getTagCompound().getCompoundTag("DIM").getInteger("xD");
					int yO = is.getTagCompound().getCompoundTag("DIM").getInteger("yD");
					int zO = is.getTagCompound().getCompoundTag("DIM").getInteger("zD");
					
			
					int x_ = xO < 0 ? -1 : 1;
					int y_ = 0;
					int z_ = zO < 0 ? -1 : 1;
					
					while(y_ != yO){
						while(x_ != xO){
							while(z_ != zO){
								
								
								
						if(is.getTagCompound().getCompoundTag("BLOCKS").getInteger("BLOCK"+index) != -1)
							w.setBlockState(pos.add(x_, y_, z_), Block.getBlockById(is.getTagCompound().getCompoundTag("BLOCKS").getInteger("BLOCK"+index)).getStateFromMeta(is.getTagCompound().getCompoundTag("META").getInteger("META"+index)));		
						
								if(zO > 0)	
									z_++;
								else
									z_--;
								
								index++;
								}
							z_ = zO < 0 ? -1 : 1;
									if(xO > 0)	
										x_++;
									else
										x_--;
							}
						x_= xO < 0 ? -1 : 1;;
						z_= zO < 0 ? -1 : 1;;
								if(yO > 0)	
									y_++;
								else
									y_--;
						}
								
								
								
								
							}




	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List<String> t, boolean advanced) {
		
		if(stack.getItemDamage() == 0){
			
			t.add(I18n.translateToLocal(LibMisc.MODID+"." + "blank"));
			
		}else{
			
			List<ItemStack> bl = new ArrayList<ItemStack>();
			List<String> bl2 = new ArrayList<String>();
			List<String> bl3 = new ArrayList<String>();
				
			for(int i = 0; i<= stack.getTagCompound().getCompoundTag("BLOCKS").getSize(); i++){
				int iD = stack.getTagCompound().getCompoundTag("BLOCKS").getInteger("BLOCK"+i);
				if(iD <= 0)
					continue;
				
				
				IBlockState state = Block.getStateById(iD);
				ItemStack bo = new ItemStack(state.getBlock(),1,state.getBlock().getMetaFromState(state));
				
				if(FluidRegistry.lookupFluidForBlock(Block.getBlockById(iD)) != null){
					bo = new ItemStack(Items.BUCKET);
				
					FluidBucketWrapper f = new FluidBucketWrapper(bo);
					f.fill(new FluidStack(FluidRegistry.lookupFluidForBlock(Block.getBlockById(iD)), 1000), true);
			
				}
				
				if(bo == null || bo.getItem() == null)
					continue;
				
				bl.add(bo);
				
				
				
				if(!bl2.contains(bo.getDisplayName()))
				bl2.add(bo.getDisplayName());
				bl3.add(bo.getDisplayName());
				
			}
			
			if(bl.isEmpty())
				return;
			
			
			
			for(String s : bl2){
				int count = 0;
				for(String m : bl3){
					
					if(s.equals(m))
						count ++;
					
					
					
					
				}
				
				t.add(count +"x" + s);
				
				
				
			}
			
			
			
		}
		
	}
					
	
	public static boolean playerHasItemsInList(List<ItemStack> is, EntityPlayer player){
		outer:
		for(ItemStack n : is){
			
			int s = n.getCount();
			int cn = 0;
			
			for(ItemStack isp : player.inventory.mainInventory){
				
				
				if(isp.getItem() == n.getItem() && isp.getItemDamage() == n.getItemDamage()){
					
					if(isp.getCount() >= cn)
						continue outer;
					else
						cn += isp.getCount();
				}
				
			}
			
			if(cn < s)
				return false;
			
		}
		
		
		return true;
	}
	
	
	
	public static boolean playerHasItemstack(ItemStack is, EntityPlayer player){
		
		int s = is.getCount();
		int cn = 0;
		
		for(ItemStack isp : player.inventory.mainInventory){
			
			
			if(isp.getItem() == is.getItem() && isp.getItemDamage() == is.getItemDamage()){
				
				if(isp.getCount() >= cn)
					return true;
				else
					cn += isp.getCount();
			}
			
		}
		
		if(cn < s)
			return false;
		
	
		
		return true;
	}
	
	public static void removeItems(ItemStack is, EntityPlayer player){
		
		
		int i = is.getCount();
		
		for(ItemStack is2 : player.inventory.mainInventory){
			
			if(is2.getItem() == is.getItem() && is2.getItemDamage() == is.getItemDamage()){
				if(is2.getCount() >= i){
					is2.shrink(i);
					return;
				}else{
					
					i -= is2.getCount();
					
					is2 = null;
					
				}
			}
			
		}
		
		
	}

	

}


