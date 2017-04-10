package com.tacosupremes.runomancy.common.item;

import java.util.List;
import java.util.UUID;

import javax.annotation.Nullable;

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
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.MobSpawnerBaseLogic;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
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
		
		if(!is.hasTagCompound() || !is.getTagCompound().hasKey("EntityTag")){
			l.add(I18n.translateToLocal(LibMisc.MODID + "." + "empty"));
			return;
		}
		
		EntityLiving e = (EntityLiving)EntityList.createEntityFromNBT(is.getSubCompound("EntityTag"), player.world);
		e.readFromNBT(is.getSubCompound("EntityTag"));
		
		String n = e.getName();
	
		l.add(I18n.translateToLocal("runomancy.contains") + " : "+(e.hasCustomName() ? e.getCustomNameTag() + " (" + n + ")" : n));
		
	if(Runomancy.proxy.isShiftDown())
		l.add(I18n.translateToLocal("runomancy.health")+" : "+ e.getHealth() + " / " + e.getMaxHealth());
	else
		l.add(I18n.translateToLocal("runomancy.press")+" "+ChatFormatting.GREEN+I18n.translateToLocal("runomancy." + "shift")+" "+ChatFormatting.GRAY+I18n.translateToLocal("runomancy." + "moreInfo"));
		
	}
	
	
	 @Override
	public boolean itemInteractionForEntity(ItemStack is, EntityPlayer playerIn, EntityLivingBase e,
			EnumHand hand) {
		 
		 if(!is.hasTagCompound())
				is.setTagCompound(new NBTTagCompound());
						
	
		   
		 if(!is.getTagCompound().hasKey("EntityTag")){
			applyEntityIdToItemStack(is, EntityList.getKey(e));
			
			
			NBTTagCompound nbt = new NBTTagCompound();
			
			nbt.setString("id", EntityList.getKey(e).toString());
		
			is.getTagCompound().setTag("EntityTag", nbt);
			e.setDead();
			
		 }
			
		return super.itemInteractionForEntity(is, playerIn, e, hand);
		
	}

	  public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	    {
	        ItemStack itemstack = player.getHeldItem(hand);

	        itemstack.getItem().updateItemStackNBT(itemstack.getTagCompound());
	        if (worldIn.isRemote)
	        {
	        	
	            return EnumActionResult.SUCCESS;
	        }
	        else if (!player.canPlayerEdit(pos.offset(facing), facing, itemstack))
	        {
	            return EnumActionResult.FAIL;
	        }
	        else
	        {
	            IBlockState iblockstate = worldIn.getBlockState(pos);
	            Block block = iblockstate.getBlock();

	            if (block == Blocks.MOB_SPAWNER)
	            {
	                TileEntity tileentity = worldIn.getTileEntity(pos);

	                if (tileentity instanceof TileEntityMobSpawner)
	                {
	                    MobSpawnerBaseLogic mobspawnerbaselogic = ((TileEntityMobSpawner)tileentity).getSpawnerBaseLogic();
	                    mobspawnerbaselogic.setEntityId(getNamedIdFrom(itemstack));
	                    tileentity.markDirty();
	                    worldIn.notifyBlockUpdate(pos, iblockstate, iblockstate, 3);

	                    if (!player.capabilities.isCreativeMode)
	                    {
	                        itemstack.shrink(1);
	                    }

	                    return EnumActionResult.SUCCESS;
	                }
	            }

	            BlockPos blockpos = pos.offset(facing);
	            double d0 = this.getYOffset(worldIn, blockpos);
	            Entity entity = spawnCreature(worldIn, getNamedIdFrom(itemstack), (double)blockpos.getX() + 0.5D, (double)blockpos.getY() + d0, (double)blockpos.getZ() + 0.5D);

	            if (entity != null)
	            {
	                if (entity instanceof EntityLivingBase && itemstack.hasDisplayName())
	                {
	                    entity.setCustomNameTag(itemstack.getDisplayName());
	                }

	                applyItemEntityDataToEntity(worldIn, player, itemstack, entity);

	                if (!player.capabilities.isCreativeMode)
	                {
	                    itemstack.getTagCompound().removeTag("EntityTag");
	                }
	            }

	            return EnumActionResult.SUCCESS;
	        }
	    }

	    protected double getYOffset(World p_190909_1_, BlockPos p_190909_2_)
	    {
	        AxisAlignedBB axisalignedbb = (new AxisAlignedBB(p_190909_2_)).addCoord(0.0D, -1.0D, 0.0D);
	        List<AxisAlignedBB> list = p_190909_1_.getCollisionBoxes((Entity)null, axisalignedbb);

	        if (list.isEmpty())
	        {
	            return 0.0D;
	        }
	        else
	        {
	            double d0 = axisalignedbb.minY;

	            for (AxisAlignedBB axisalignedbb1 : list)
	            {
	                d0 = Math.max(axisalignedbb1.maxY, d0);
	            }

	            return d0 - (double)p_190909_2_.getY();
	        }
	    }

	    /**
	     * Applies the data in the EntityTag tag of the given ItemStack to the given Entity.
	     */
	    public static void applyItemEntityDataToEntity(World entityWorld, @Nullable EntityPlayer player, ItemStack stack, @Nullable Entity targetEntity)
	    {
	        MinecraftServer minecraftserver = entityWorld.getMinecraftServer();

	        if (minecraftserver != null && targetEntity != null)
	        {
	            NBTTagCompound nbttagcompound = stack.getTagCompound();

	            if (nbttagcompound != null && nbttagcompound.hasKey("EntityTag", 10))
	            {
	                if (!entityWorld.isRemote && targetEntity.ignoreItemEntityData() && (player == null || !minecraftserver.getPlayerList().canSendCommands(player.getGameProfile())))
	                {
	                    return;
	                }

	                NBTTagCompound nbttagcompound1 = targetEntity.writeToNBT(new NBTTagCompound());
	                UUID uuid = targetEntity.getUniqueID();
	                nbttagcompound1.merge(nbttagcompound.getCompoundTag("EntityTag"));
	                targetEntity.setUniqueId(uuid);
	                targetEntity.readFromNBT(nbttagcompound1);
	            }
	        }
	    }

	    /**
	     * Called when the equipped item is right clicked.
	     */
	    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
	    {
	        ItemStack itemstack = playerIn.getHeldItem(handIn);
	        itemstack.getItem().updateItemStackNBT(itemstack.getTagCompound());

	        if (worldIn.isRemote)
	        {
	            return new ActionResult(EnumActionResult.PASS, itemstack);
	        }
	        else
	        {
	            RayTraceResult raytraceresult = this.rayTrace(worldIn, playerIn, true);

	            if (raytraceresult != null && raytraceresult.typeOfHit == RayTraceResult.Type.BLOCK)
	            {
	                BlockPos blockpos = raytraceresult.getBlockPos();

	                if (!(worldIn.getBlockState(blockpos).getBlock() instanceof BlockLiquid))
	                {
	                    return new ActionResult(EnumActionResult.PASS, itemstack);
	                }
	                else if (worldIn.isBlockModifiable(playerIn, blockpos) && playerIn.canPlayerEdit(blockpos, raytraceresult.sideHit, itemstack))
	                {
	                    Entity entity = spawnCreature(worldIn, getNamedIdFrom(itemstack), (double)blockpos.getX() + 0.5D, (double)blockpos.getY() + 0.5D, (double)blockpos.getZ() + 0.5D);

	                    if (entity == null)
	                    {
	                        return new ActionResult(EnumActionResult.PASS, itemstack);
	                    }
	                    else
	                    {
	                        if (entity instanceof EntityLivingBase && itemstack.hasDisplayName())
	                        {
	                            entity.setCustomNameTag(itemstack.getDisplayName());
	                        }

	                        applyItemEntityDataToEntity(worldIn, playerIn, itemstack, entity);

	                        if (!playerIn.capabilities.isCreativeMode)
	                        {
	                            itemstack.getTagCompound().removeTag("EntityTag");
	                        }

	                        playerIn.addStat(StatList.getObjectUseStats(this));
	                        return new ActionResult(EnumActionResult.SUCCESS, itemstack);
	                    }
	                }
	                else
	                {
	                    return new ActionResult(EnumActionResult.FAIL, itemstack);
	                }
	            }
	            else
	            {
	                return new ActionResult(EnumActionResult.PASS, itemstack);
	            }
	        }
	    }

	    /**
	     * Spawns the creature specified by the egg's type in the location specified by the last three parameters.
	     * Parameters: world, entityID, x, y, z.
	     */
	    @Nullable
	    public static Entity spawnCreature(World worldIn, @Nullable ResourceLocation entityID, double x, double y, double z)
	    {
	        if (entityID != null && EntityList.ENTITY_EGGS.containsKey(entityID))
	        {
	            Entity entity = null;

	            for (int i = 0; i < 1; ++i)
	            {
	                entity = EntityList.createEntityByIDFromName(entityID, worldIn);

	                if (entity instanceof EntityLiving)
	                {
	                    EntityLiving entityliving = (EntityLiving)entity;
	                    entity.setLocationAndAngles(x, y, z, MathHelper.wrapDegrees(worldIn.rand.nextFloat() * 360.0F), 0.0F);
	           
	                    entityliving.rotationYawHead = entityliving.rotationYaw;
	                    entityliving.renderYawOffset = entityliving.rotationYaw;
	                    entityliving.onInitialSpawn(worldIn.getDifficultyForLocation(new BlockPos(entityliving)), (IEntityLivingData)null);
	                    worldIn.spawnEntity(entity);
	                    entityliving.playLivingSound();
	                }
	            }

	            return entity;
	        }
	        else
	        {
	            return null;
	        }
	    }

	    /**
	     * returns a list of items with the same ID, but different meta (eg: dye returns 16 items)
	     */
	    @SideOnly(Side.CLIENT)
	    public void getSubItems(Item itemIn, CreativeTabs tab, NonNullList<ItemStack> subItems)
	    {
	    	
	    	subItems.add(new ItemStack(this));
	        for (EntityList.EntityEggInfo entitylist$entityegginfo : EntityList.ENTITY_EGGS.values())
	        {
	            ItemStack itemstack = new ItemStack(itemIn, 1);
	            applyEntityIdToItemStack(itemstack, entitylist$entityegginfo.spawnedID);
	            subItems.add(itemstack);
	        }
	    }

	    /**
	     * APplies the given entity ID to the given ItemStack's NBT data.
	     */
	    @SideOnly(Side.CLIENT)
	    public static void applyEntityIdToItemStack(ItemStack stack, ResourceLocation entityId)
	    {
	        NBTTagCompound nbttagcompound = stack.hasTagCompound() ? stack.getTagCompound() : new NBTTagCompound();
	        NBTTagCompound nbttagcompound1 = new NBTTagCompound();
	        nbttagcompound1.setString("id", entityId.toString());
	        nbttagcompound.setTag("EntityTag", nbttagcompound1);
	        stack.setTagCompound(nbttagcompound);
	    }
	    
	   
	    public static void applyEntityIdToItemStacks(ItemStack stack, ResourceLocation entityId)
	    {
	        NBTTagCompound nbttagcompound = stack.hasTagCompound() ? stack.getTagCompound() : new NBTTagCompound();
	        NBTTagCompound nbttagcompound1 = new NBTTagCompound();
	        nbttagcompound1.setString("id", entityId.toString());
	        nbttagcompound.setTag("EntityTag", nbttagcompound1);
	        stack.setTagCompound(nbttagcompound);
	    }

	    @Nullable
	    public static ResourceLocation getNamedIdFrom(ItemStack p_190908_0_)
	    {
	        NBTTagCompound nbttagcompound = p_190908_0_.getTagCompound();

	        if (nbttagcompound == null)
	        {
	            return null;
	        }
	        else if (!nbttagcompound.hasKey("EntityTag", 10))
	        {
	            return null;
	        }
	        else
	        {
	            NBTTagCompound nbttagcompound1 = nbttagcompound.getCompoundTag("EntityTag");

	            if (!nbttagcompound1.hasKey("id", 8))
	            {
	                return null;
	            }
	            else
	            {
	                String s = nbttagcompound1.getString("id");
	                ResourceLocation resourcelocation = new ResourceLocation(s);

	                if (!s.contains(":"))
	                {
	                    nbttagcompound1.setString("id", resourcelocation.toString());
	                }

	                return resourcelocation;
	            }
	        }
	    }
	
	public static ItemStack gemWithEntity(Entity e){
		
		ItemStack is = new ItemStack(ModItems.soulGem, 1,0);
		
		is.setTagCompound(new NBTTagCompound());
		
		is.getTagCompound().setString("NAME", EntityList.getEntityString(e));
		
		return is;
	}

	
	 

}
