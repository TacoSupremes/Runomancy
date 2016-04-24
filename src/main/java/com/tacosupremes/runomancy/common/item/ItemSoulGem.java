package com.tacosupremes.runomancy.common.item;

import java.util.List;

import com.mojang.realmsclient.gui.ChatFormatting;
import com.tacosupremes.runomancy.common.Runomancy;
import com.tacosupremes.runomancy.common.lib.LibMisc;
import com.tacosupremes.runomancy.gui.Categories;
import com.tacosupremes.runomancy.gui.IPageGiver;
import com.tacosupremes.runomancy.gui.ItemPage;
import com.tacosupremes.runomancy.gui.Page;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFence;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemSoulGem extends ItemMod implements IPageGiver {

	public ItemSoulGem() {
		super("soulGem");
		
	}

	@Override
	public Page getPage() {

		return new ItemPage(new ItemStack(this));

	}

	@Override
	public Categories getCategories() {

		return null;

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
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack is, EntityPlayer player, List<String> l, boolean advanced) {
		
		if(!is.hasTagCompound() || !is.getTagCompound().hasKey("NAME")){
			l.add(I18n.translateToLocal(LibMisc.MODID + "." + "empty"));
			return;
		}
		
		EntityLiving e = (EntityLiving)EntityList.createEntityByName(is.getTagCompound().getString("NAME"), player.worldObj);
		e.readFromNBT(is.getSubCompound("ENBT", false));
		
		String n = I18n.translateToLocal("entity." + is.getTagCompound().getString("NAME")+".name");
		
		
		l.add(I18n.translateToLocal("runomancy.contains") + " : "+(e.hasCustomName() ? e.getCustomNameTag() + " (" + n + ")" : n));
		
	if(Runomancy.proxy.isShiftDown()){
		l.add(I18n.translateToLocal("runomancy.health")+" : "+ e.getHealth() + " / " + e.getMaxHealth());
	}else
		l.add(I18n.translateToLocal("runomancy.press")+" "+ChatFormatting.GREEN+I18n.translateToLocal("runomancy." + "shift")+" "+ChatFormatting.GRAY+I18n.translateToLocal("runomancy." + "moreInfo"));
				
	
		
	}
	
	
	 @Override
	public boolean itemInteractionForEntity(ItemStack is, EntityPlayer playerIn, EntityLivingBase e,
			EnumHand hand) {
		
		 
		 
		 if(!is.hasTagCompound())
				is.setTagCompound(new NBTTagCompound());
			
			
			if(is.getTagCompound().hasKey("NAME"))
				return false;
			
			is.getTagCompound().setString("NAME", EntityList.getEntityString(e));
			
			NBTTagCompound nbt = new NBTTagCompound();
			
			e.writeToNBT(nbt);
			
			is.getTagCompound().setTag("ENBT", nbt);
		
			e.setDead();
			
		return super.itemInteractionForEntity(is, playerIn, e, hand);
		
	}

	public boolean onItemUse(ItemStack is, EntityPlayer player, World w, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ)
	    {
	        if (w.isRemote)
	        {
	            return true;
	        }
	        else if (!player.canPlayerEdit(pos.offset(side), side, is))
	        {
	            return false;
	        }
	        else
	        {
	            IBlockState iblockstate = w.getBlockState(pos);

	            

	            pos = pos.offset(side);
	            double d0 = 0.0D;

	            if (side == EnumFacing.UP && iblockstate.getBlock() instanceof BlockFence) //Forge: Fix Vanilla bug comparing state instead of block
	            {
	                d0 = 0.5D;
	            }

	            Entity entity = spawnCreature(w, is, (double)pos.getX() + 0.5D, (double)pos.getY() + d0, (double)pos.getZ() + 0.5D);
	           
	            if (entity != null)
	            {
	                if (entity instanceof EntityLivingBase && is.hasDisplayName())
	                {
	                    entity.setCustomNameTag(is.getDisplayName());
	                }

	                
	            }

	            return true;
	        }
	    }

	    /**
	     * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
	     */
	    public ItemStack onItemRightClick(ItemStack is, World w, EntityPlayer player)
	    {
	        if (w.isRemote)
	        {
	            return is;
	        }
	        else
	        {
	            RayTraceResult movingobjectposition = this.getMovingObjectPositionFromPlayer(w, player, true);

	            if (movingobjectposition == null)
	            {
	                return is;
	            }
	            else
	            {
	                if (movingobjectposition.typeOfHit == RayTraceResult.Type.BLOCK)
	                {
	                    BlockPos blockpos = movingobjectposition.getBlockPos();

	                    if (!w.isBlockModifiable(player, blockpos))
	                    {
	                        return is;
	                    }

	                    if (!player.canPlayerEdit(blockpos, movingobjectposition.sideHit, is))
	                    {
	                        return is;
	                    }

	                    if (w.getBlockState(blockpos).getBlock() instanceof BlockLiquid)
	                    {
	                        Entity entity = spawnCreature(w, is, (double)blockpos.getX() + 0.5D, (double)blockpos.getY() + 0.5D, (double)blockpos.getZ() + 0.5D);
	                       
	                        if (entity != null)
	                        {
	                            if (entity instanceof EntityLivingBase && is.hasDisplayName())
	                            {
	                                ((EntityLiving)entity).setCustomNameTag(is.getDisplayName());
	                            }

	                           

	                            
	                        }
	                    }
	                }

	                return is;
	            }
	        }
	    }

	   

	    public static Entity spawnCreature(World w, ItemStack is, double x, double y, double z)
	    {
	        if (!EntityList.stringToClassMapping.containsKey(is.getTagCompound().getString("NAME")))
	        {
	            return null;
	        }
	        else
	        {
	            Entity entity = null;

	            for (int i = 0; i < 1; ++i)
	            {
	                entity = EntityList.createEntityByName(is.getTagCompound().getString("NAME"), w);
	                entity.readFromNBT(is.getSubCompound("ENBT",false));
	                is.getTagCompound().removeTag("NAME");
	                is.getTagCompound().removeTag("ENBT");

	                if (entity instanceof EntityLivingBase)
	                {
	                    EntityLiving entityliving = (EntityLiving)entity;
	                    entity.setLocationAndAngles(x, y, z, MathHelper.wrapAngleTo180_float(w.rand.nextFloat() * 360.0F), 0.0F);
	                    entityliving.rotationYawHead = entityliving.rotationYaw;
	                    entityliving.renderYawOffset = entityliving.rotationYaw;
	                    w.spawnEntityInWorld(entity);
	                    entityliving.playLivingSound();
	                }
	            }

	            return entity;
	        }
	    }

		@Override
		public boolean hasEffect(ItemStack stack) {
			
			return stack.hasTagCompound() ? stack.getTagCompound().hasKey("NAME") : false;
			
		}

	
	
	public static ItemStack gemWithEntity(Entity e){
		
		ItemStack is = new ItemStack(ModItems.soulGem, 1,0);
		
		is.setTagCompound(new NBTTagCompound());
		
		is.getTagCompound().setString("NAME", EntityList.getEntityString(e));
		
		return is;
	}

	public static ItemStack gemWithEntity(Class <? extends Entity > c) {
		
		ItemStack is = new ItemStack(ModItems.soulGem, 1,0);
		
		is.setTagCompound(new NBTTagCompound());
		
		is.getTagCompound().setString("NAME", EntityList.func_188430_a(c));
		
		return is;
		
	}
	 

}
