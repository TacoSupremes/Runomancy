package com.tacosupremes.runomancy.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.IStringSerializable;

public class BlockBatteryPortal extends BlockMod
{
   // public static final IntegerProperty mode = IntegerProperty.create("mode",0, 4);


    public static final EnumProperty<ColorEnum> color = EnumProperty.create("color", ColorEnum.class);
    public static BlockState PURPLE;
    public static BlockState RED;
    public static BlockState GREEN;
    public static BlockState BLUE;



    public BlockBatteryPortal()
    {
        super(Properties.create(Material.PORTAL));
        this.setDefaultState(this.stateContainer.getBaseState().with(color, ColorEnum.purple));
        PURPLE = this.getDefaultState().with(color, ColorEnum.purple);
        RED = this.getDefaultState().with(color, ColorEnum.red);
        GREEN = this.getDefaultState().with(color, ColorEnum.green);
        BLUE = this.getDefaultState().with(color, ColorEnum.blue);
    }

    @Override
    public String getName()
    {
        return "battery_portal";
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder)
    {
        builder.add(color);
    }



    private enum ColorEnum implements IStringSerializable
    {
        purple, red, green, blue
        ;

        @Override
        public String getName()
        {
            return this.name();
        }
    }

}
