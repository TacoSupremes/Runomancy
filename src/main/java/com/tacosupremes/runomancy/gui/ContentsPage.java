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
		
		
		
		if(hasInit){
			
			return;
		}
		int in = 0;
		//final int end = Pages.getAvailableID()+3;
		for(int i = 0; i<=4;i++){
		this.buttons.add(new TextButton(i, x+16, y+32+16*(in), StatCollector.translateToLocal("runomancy.cat"+(i == 0 ? "" : i))));
		in++;
		}
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
		
		
		//System.out.println(b.name);
		//if(b.name == "runomancy.cat"){
		this.g.changePage("RUNES");
	
	//	return;
	//	
	//	}
		
		
	}



	@Override
	public boolean needsBackButton() {
	
		return false;
	}



	@Override
	public void goBack() {}




	@Override
	public String returnPage() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Categories getCategory() {
		// TODO Auto-generated method stub
		return Categories.Home;
	}
	




	
	
	







	
	

}
