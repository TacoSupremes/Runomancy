package com.tacosupremes.runomancy.common.block;

public class BlockMarker
{
	/*
	public static final PropertyBool active = PropertyBool.create("active");
	

	public BlockMarker() {
		super(Material.CIRCUITS, "marker");
		this.setDefaultState(this.getDefaultState().withProperty(active, false));
		
		
	}


	@SideOnly(Side.CLIENT)
    public IBlockState getStateForEntityRender(IBlockState state)
    {
        return this.getDefaultState().withProperty(active, false);
    }

    /**
     * Convert the given metadata into a BlockState for this Block

    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(active, meta == 1);
    }

    /**
     * Convert the BlockState into the correct metadata value

    public int getMetaFromState(IBlockState state)
    {
       

        return state.getValue(active) ? 1 : 0;
    }

    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {active});
    }


	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		
		return new TileMarker();
		
	}


	@Override
	protected Class<? extends TileEntity> tile() {
		
		return TileMarker.class;
		
	}


	@Override
	 public boolean onBlockActivated(World w, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
		
		ItemStack heldItem = playerIn.getHeldItem(hand);
		if(heldItem == null || heldItem.getItem() != ModItems.builderScroll){
		w.setBlockState(pos, state.withProperty(active, true));
		
		TileMarker te = (TileMarker)w.getTileEntity(pos);
		
		
		BlockPos xF = null;
		BlockPos yF = null;
		BlockPos zF = null;
		
		
	int r2 = 64;
			for(int r = 0;r <=64;r++){
		for(int x = -r; x<= r; x++){
			
			if(x == 0)
				continue;
			
			if(Math.abs(x) != r)
				continue;
			
			if(xF != null)
				break;
			
			if(w.getBlockState(pos.add(x, 0, 0)).getBlock() == ModBlocks.marker){
				xF = pos.add(x, 0, 0);
				break;
			}
				
		}
		
		for(int y = -r; y<= r; y++){
			
			if(y == 0)
				continue;
			
			if(Math.abs(y) != r)
				continue;
			
			if(yF != null)
				break;
			
			
			if(w.getBlockState(pos.add(0, y, 0)).getBlock() == ModBlocks.marker){
				yF = pos.add(0, y, 0);
				break;
			}
				
		}

		for(int z = -r; z<= r; z++){
			
			if(z == 0)
				continue;
			
			if(Math.abs(z) != r)
				continue;
			
			
			if(zF != null)
				break;
			
			
if(w.getBlockState(pos.add(0, 0, z)).getBlock() == ModBlocks.marker){
	zF = pos.add(0, 0, z);
	break;
}

		}
}
			
			te.xF = xF;
			te.yF = yF;
			te.zF = zF;
			
			return true;
		
		}
		
		
		if(heldItem.getItem() == ModItems.builderScroll && state.getValue(active)){
			
			TileMarker te = (TileMarker)w.getTileEntity(pos);
			heldItem.setItemDamage(1);
			heldItem.setTagCompound(new NBTTagCompound());
			
			
			NBTTagCompound b = new NBTTagCompound();
			
			NBTTagCompound meta = new NBTTagCompound();
			
			NBTTagCompound dimensions = new NBTTagCompound();
			
			if(!te.isArea())
				return false;
			
			int xO = te.xF == null ? 0 : te.xF.getX()-pos.getX();
			int yO = te.yF == null ? 0 : te.yF.getY()-pos.getY();
			int zO = te.zF == null ? 0 : te.zF.getZ()-pos.getZ();
			
			yO += yO == 0 ? 0 : yO < 0 ? -1 : 1;
			
			dimensions.setInteger("xD", xO);
			dimensions.setInteger("yD", yO);
			dimensions.setInteger("zD", zO);
			
			heldItem.getTagCompound().setTag("DIM", dimensions);
			int x_ = xO < 0 ? -1 : 1;
			int y_ = 0;
			int z_ = zO < 0 ? -1 : 1;
			int index = 0;
			while(y_ != yO){
				while(x_ != xO){
					while(z_ != zO){
						System.out.println(w.getBlockState(pos.add(x_, y_, z_)).toString() + ":::" + x_ + ":" + y_ + ":" + z_);
						if(!w.getBlockState(pos.add(x_, y_, z_)).getBlock().isAir(w.getBlockState(pos.add(x_, y_, z_)), w, pos.add(x_, y_, z_)))
							b.setInteger("BLOCK"+index, Block.getStateId(w.getBlockState(pos.add(x_, y_, z_))));
						else{
							b.setInteger("BLOCK"+index, -1);			
						}
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
			
			heldItem.getTagCompound().setTag("BLOCKS", b);			
	}
		
		return super.onBlockActivated(w, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
		
	}

	
	*/
	

}
