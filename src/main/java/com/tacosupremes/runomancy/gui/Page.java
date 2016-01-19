package com.tacosupremes.runomancy.gui;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import com.tacosupremes.runomancy.common.lib.LibMisc;
import com.tacosupremes.runomancy.gui.buttons.TextButton;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.StatCollector;

public abstract class Page{
	
	

	public int x,y,w,h;
	protected GuiModBook g;
	protected Page lastPage;
	
	public boolean hasInit = false;

	

	public Page(){
		
	}
	
	public void initPage(int x, int y, int w, int h, GuiModBook g){
		
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.g = g;
		
		this.init();
	}
	
	
	
	public List<GuiButton> buttons = new ArrayList<GuiButton>();
	
	public abstract void draw(int mx, int my, float ticks);
		
	public void handleButtons(GuiButton gb){
		
		
			
		if(gb.id == LibMisc.GuiIDs.Buttons.BACK)
			this.goBack();
		
		
		
	}
	
	public void init(){
		
	
		
		
		if(needsBackButton() && !hasInit)
			this.buttons.add(new TextButton(LibMisc.GuiIDs.Buttons.BACK, x+w/2 - Minecraft.getMinecraft().fontRendererObj.getStringWidth(StatCollector.translateToLocal("runomancy.back"))/2, y+(h-24), StatCollector.translateToLocal("runomancy.back")));
		hasInit = true;
		this.initButtons();
	}
		
		
	public boolean needsBackButton(){
		
		return true;
	}
	

	public void goBack() {
		g.changePage(this.returnPage());
		
	}
	
	

	public Page getLastPage() {
		return lastPage;
	}

	public void mouseClicked(int mouseX, int mouseY, int mouseButton) {}
		
		
	
	
	public void initButtons(){
		
		
		for(GuiButton g : this.buttons){
			
			if(g.id == LibMisc.GuiIDs.Buttons.BACK){
				g.xPosition =  x+w/2 - Minecraft.getMinecraft().fontRendererObj.getStringWidth(StatCollector.translateToLocal("runomancy.back"))/2;
				g.yPosition = y+(h-24);
				
				return;
			}
		}
		
		
	}
	 
	 public abstract String returnPage();
	 
	 public abstract Categories getCategory();
	 


}
