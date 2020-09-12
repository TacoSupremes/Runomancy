package com.tacosupremes.runomancy.common.item;

public class ItemRunicWand extends ItemMod  {
	@Override
	public String getItemRegistryName()
	{
		return "runic_wand";
	}


/*
	@Override
	public ItemStack getContainerItem(ItemStack is)
	{
		return is.getItemDamage() == is.getMaxDamage()-1 ? null : new ItemStack(is.getItem(), 1, is.getItemDamage()+1);
	}




	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World w, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		
		
	

		
		
		if(w.getTileEntity(pos) instanceof IPowerNode){
			
			
		
		
			
			if(!player.getHeldItem(hand).hasTagCompound()){
				player.getHeldItem(hand).setTagCompound(new NBTTagCompound());
			}
			
			if(player.getHeldItem(hand).getTagCompound().getBoolean("ACTIVE")){
				
				IPowerNode n = (IPowerNode)w.getTileEntity(pos);
				
				n.getLinkedBlocks().add(BlockPos.fromLong(player.getHeldItem(hand).getTagCompound().getLong("LINK")));
				IPowerNode n2 = (IPowerNode)w.getTileEntity(BlockPos.fromLong(player.getHeldItem(hand).getTagCompound().getLong("LINK")));
				n2.getLinkedBlocks().add(pos);
				
				player.getHeldItem(hand).getTagCompound().removeTag("LINK");
				player.getHeldItem(hand).getTagCompound().setBoolean("ACTIVE", false);
			}else{
				player.getHeldItem(hand).getTagCompound().setBoolean("ACTIVE", true);
				player.getHeldItem(hand).getTagCompound().setLong("LINK", pos.toLong());
			}
			
		//	PowerHelper.drawTorchLines(w, pos, 5, false);
			IPowerNode n = (IPowerNode)w.getTileEntity(pos);
			
			System.out.println(n.getLinkedBlocks().toString());
			
			
		}
		
		
		
		
		return super.onItemUse(player, w, pos, hand, facing, hitX, hitY, hitZ);
		
	}

	@Override
	public void onUpdate(ItemStack is, World w, Entity e, int itemSlot, boolean isSelected) {
		
		if(!is.hasTagCompound())
			return;
		
		
		
		if(is.getTagCompound().getBoolean("ACTIVE")){
			
			BlockPos bp = BlockPos.fromLong(is.getTagCompound().getLong("LINK"));
			
			 RayTraceResult rr = this.rayTrace(w, (EntityPlayer) e, true);
			    
			if(rr == null)
			{
				System.out.print("WHY");
				return;
			}
			double distance = Math.sqrt(bp.distanceSq(rr.hitVec.xCoord, rr.hitVec.yCoord, rr.hitVec.zCoord));
			
			BlockUtils.drawLine(w, Vector3.fromBlockPos(bp).add(0.5D), new Vector3(rr.hitVec), distance > 7.65D ? EnumParticleTypes.REDSTONE : EnumParticleTypes.VILLAGER_HAPPY);
			
			
		}
		
	}
	
	*/
	
}
