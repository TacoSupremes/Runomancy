package com.tacosupremes.runomancy.common.item;

import java.util.ArrayList;
import java.util.List;

import com.tacosupremes.runomancy.common.block.ModBlocks;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;

public class ItemBuilderScroll extends ItemMod{

	public ItemBuilderScroll() {
		super("builderScroll", 1);
		this.setMaxStackSize(1);
	}
	
	
	
	
	@Override
	public EnumActionResult onItemUse(ItemStack stack, EntityPlayer player, World w, BlockPos pos,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		
		if(w.getBlockState(pos).getBlock() != ModBlocks.marker)
		buildStructure(w,pos.up(), stack);
		
		return super.onItemUse(stack, player, w, pos, hand, facing, hitX, hitY, hitZ);
		
	}




	public void buildStructure(World w, BlockPos pos, ItemStack is){
		
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
							z_ = zO < 0 ? -1 : 1;;
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
			
			t.add(I18n.translateToLocal("runomancy.blank"));
			
		}else{
			
			List<ItemStack> bl = new ArrayList<ItemStack>();
			List<String> bl2 = new ArrayList<String>();
			List<String> bl3 = new ArrayList<String>();
				
			for(int i = 0; i<= stack.getTagCompound().getCompoundTag("BLOCKS").getSize(); i++){
				int iD = stack.getTagCompound().getCompoundTag("BLOCKS").getInteger("BLOCK"+i);
				if(iD <= 0)
					continue;
				
				int meta = stack.getTagCompound().getCompoundTag("META").getInteger("META"+i);
				ItemStack bo = new ItemStack(Block.getBlockById(iD).getStateFromMeta(meta).getBlock(), 1, meta);
				
				if(bo.getItem() == null)
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
					
					

	

}


