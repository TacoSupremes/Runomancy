package com.tacosupremes.runomancy.gui;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import com.tacosupremes.runomancy.common.block.ModBlocks;
import com.tacosupremes.runomancy.common.lib.LibMisc;
import com.tacosupremes.runomancy.gui.buttons.ItemButton;
import com.tacosupremes.runomancy.gui.buttons.TextButton;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

public class ItemListPage extends Page {
	
	
	
	//TODO CREATE SUBPAGE SYSTEM FOR WHEN LIST OVERFLOWS

	

	private List<Item> l;
	private List<Block> bl;
	
	public ItemListPage(List<Item> l) {
		super();
		this.l = l;
		this.bl = null;
	}
	
	public ItemListPage(List<Block> l, String s) {
		super();
		this.bl = l;
		this.l = null;
		
		
		
		
	
	}

	
	

	@Override
	public void init() {
		
	
		
		
		if(!hasInit){
			
			
		if(bl == null){
		for(int i = 0; i<l.size();i++){
		this.buttons.add(new ItemButton(i, x+16, y+32+16*i, StatCollector.translateToLocal(l.get(i).getUnlocalizedName() +".name"), l.get(i)));
		
		}
		}else{
			
			for(int i = 0; i<bl.size();i++){
				this.buttons.add(new ItemButton(i, x+16, y+32+16*i, StatCollector.translateToLocal(bl.get(i).getUnlocalizedName() +".name"), bl.get(i)));
				
				}
			
		}
		
		buttons.add(new TextButton(LibMisc.GuiIDs.Buttons.BACK, x+w/2 - Minecraft.getMinecraft().fontRendererObj.getStringWidth(StatCollector.translateToLocal("runomancy.back"))/2, y+(h-24), StatCollector.translateToLocal("runomancy.back")));
		
		}
		
		hasInit = true;
		this.initButtons();
		
	}
	
	
	@Override
	public void initButtons() {
		
	
		
		int in = 0;
		
		for(GuiButton gb : buttons){
			
			if(gb.id == LibMisc.GuiIDs.Buttons.BACK)
				continue;
			
			gb.xPosition = x+16;
			gb.yPosition = y+32+16*in;
			in++;
			
		}
		
		this.hasInit = true;
		
		super.initButtons();
	}
	

	@Override
	public void draw(int mx, int my, float tick) {
		
		
		GL11.glPushMatrix();
		
		FontRenderer f = Minecraft.getMinecraft().fontRendererObj;
		String s = StatCollector.translateToLocal("runomancy.cat");
		
		f.drawString(s, x + w / 2 - f.getStringWidth(s) / 2 , y+16, 0);
		
		GL11.glPopMatrix();
		

	}


	@Override
	public void handleButtons(GuiButton b) {
		
	
		
		
			
			if(b.id != LibMisc.GuiIDs.Buttons.BACK){
				
				if(l != null)
					g.changePage(l.get(b.id).getUnlocalizedName());
				else
					g.changePage(bl.get(b.id).getUnlocalizedName());
			}
			
		
		super.handleButtons(b);
	}



	@Override
	public String returnPage() {
	
		return "HOME";
	}
	
	
	


	@Override
	public Categories getCategory() {
		
		return Categories.Runes;
	}


	



	

}
