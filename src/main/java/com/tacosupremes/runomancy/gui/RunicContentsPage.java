package com.tacosupremes.runomancy.gui;

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
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

public class RunicContentsPage extends Page {
	
	
	


	
	int maxID = ModBlocks.runes.size();
	
	public RunicContentsPage() {
		super();
	}

	
	

	@Override
	public void init() {
		
		if(hasInit)
			return;
		
		
		for(int i = 0; i<maxID;i++){
		this.buttons.add(new ItemButton(i, x+16, y+32+16*i, ModBlocks.runes.get(i).getLocalizedName(), ModBlocks.runes.get(i)));
		
		}
		
		
		buttons.add(new TextButton(LibMisc.GuiIDs.Buttons.BACK, x+w/2 - Minecraft.getMinecraft().fontRendererObj.getStringWidth(StatCollector.translateToLocal("runomancy.back"))/2, y+(h-24), StatCollector.translateToLocal("runomancy.back")));
		
		
	}
	
	
	@Override
	public void initButtons() {
		
		if(hasInit)
			return;
		
		int in = 0;
		
		for(GuiButton gb : buttons){
			
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
		
	
		
		
			
			if(b.id != LibMisc.GuiIDs.Buttons.BACK)
			g.changePage(ModBlocks.runes.get(b.id).getUnlocalizedName());
	
			
		
		super.handleButtons(b);
	}



	@Override
	public String returnPage() {
	
		return "HOME";
	}
	
	
	


	@Override
	public Categories getCategory() {
		// TODO Auto-generated method stub
		return Categories.Runes;
	}


	



	

}
