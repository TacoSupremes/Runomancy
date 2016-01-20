package com.tacosupremes.runomancy.gui;

import org.lwjgl.opengl.GL11;

import com.tacosupremes.runomancy.gui.buttons.TextButton;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.StatCollector;

public class ContentsPage extends Page{
	
	

	
	
	public ContentsPage(){
		super();
	}
	
//	public String[] n = new String[]{}

	@Override
	public void init() {
		
		
		
		
		int in = 0;
		//final int end = Pages.getAvailableID()+3;
		if(!hasInit){
		for(int i = 0; i<=5;i++){
		this.buttons.add(new TextButton(i, x+16, y+32+16*(in), StatCollector.translateToLocal("runomancy.cat"+(i == 0 ? "" : i))));
		in++;
		}
		}
		hasInit = true;
		this.initButtons();
	}

	@Override
	public void initButtons() {
	
		
		int in = 0;
		
		for(GuiButton gb : buttons){
			
			gb.xPosition = x+16;
			gb.yPosition = y+32+16*in;
			in++;
			
		}
		
		this.hasInit = true;
	}



	@Override
	public void draw(int mx, int my, float tick) {
		
		GL11.glPushMatrix();
		
		FontRenderer f = Minecraft.getMinecraft().fontRendererObj;
		String s = StatCollector.translateToLocal("runomancy.table");
		
		f.drawString(s, x + w / 2 - f.getStringWidth(s) / 2 , y+16, 0);
		
		GL11.glPopMatrix();
		
	}
	
	@Override
	public void handleButtons(GuiButton b) {
		
		
		
		if(b.id == 0)
			this.g.changePage("RUNES");
		
		if(b.id == 1)
			this.g.changePage("BASICS");
	
		if(b.id == 2)
			this.g.changePage("GENERATING");
		
		if(b.id == 3)
			this.g.changePage("FUNCTIONAL");
		
		if(b.id == 4)
			this.g.changePage("RUNICITEMS");
		
		if(b.id == 5)
			this.g.changePage("RUNICBLOCKS");
			
	
		
		
	}



	@Override
	public boolean needsBackButton() {
	
		return false;
	}



	@Override
	public void goBack() {}




	@Override
	public String returnPage() {
		
		return null;
	}


	@Override
	public Categories getCategory() {
		
		return Categories.Home;
	}
	




	
	
	







	
	

}
