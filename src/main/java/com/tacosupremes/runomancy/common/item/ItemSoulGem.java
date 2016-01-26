package com.tacosupremes.runomancy.common.item;

import java.util.List;

import com.tacosupremes.runomancy.common.Runomancy;
import com.tacosupremes.runomancy.common.lib.LibMisc;
import com.tacosupremes.runomancy.gui.Categories;
import com.tacosupremes.runomancy.gui.IPageGiver;
import com.tacosupremes.runomancy.gui.ItemPage;
import com.tacosupremes.runomancy.gui.Page;

import net.minecraft.block.BlockFence;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemMonsterPlacer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.MobSpawnerBaseLogic;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.StatCollector;
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
			l.add(StatCollector.translateToLocal(LibMisc.MODID + "." + "empty"));
			return;
		}
		
		EntityLiving e = (EntityLiving)EntityList.createEntityByName(is.getTagCompound().getString("NAME"), player.worldObj);
		e.readFromNBT(is.getSubCompound("ENBT", false));
		
		String n = StatCollector.translateToLocal("entity." + is.getTagCompound().getString("NAME")+".name");
		
		
		l.add(StatCollector.translateToLocal("runomancy.contains") + " : "+(e.hasCustomName() ? e.getCustomNameTag() + " (" + n + ")" : n));
		
	if(Runomancy.proxy.isShiftDown()){
		l.add(StatCollector.translateToLocal("runomancy.health")+" : "+ e.getHealth() + " / " + e.getMaxHealth());
	}else
		l.add(StatCollector.translateToLocal("runomancy.press")+" "+EnumChatFormatting.GREEN+StatCollector.translateToLocal("runomancy." + "shift")+" "+EnumChatFormatting.GRAY+StatCollector.translateToLocal("runomancy." + "moreInfo"));
				
	
		
	}

	@Override
	public boolean itemInteractionForEntity(ItemStack is, EntityPlayer player, EntityLivingBase e) {
		
		if(!is.hasTagCompound())
			is.setTagCompound(new NBTTagCompound());
		
		
		if(is.getTagCompound().hasKey("NAME"))
			return false;
		
		is.getTagCompound().setString("NAME", EntityList.getEntityString(e));
		
		NBTTagCompound nbt = new NBTTagCompound();
		
		e.writeToNBT(nbt);
		
		is.getTagCompound().setTag("ENBT", nbt);
	
		e.setDead();
		
		
		return true;
		
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
	            MovingObjectPosition movingobjectposition = this.getMovingObjectPositionFromPlayer(w, player, true);

	            if (movingobjectposition == null)
	            {
	                return is;
	            }
	            else
	            {
	                if (movingobjectposition.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK)
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

	
	
	
	 

}
