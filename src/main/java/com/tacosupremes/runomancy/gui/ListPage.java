package com.tacosupremes.runomancy.gui;

import java.util.List;

import org.lwjgl.opengl.GL11;

import com.tacosupremes.runomancy.common.lib.LibMisc;
import com.tacosupremes.runomancy.gui.buttons.TextButton;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.StatCollector;

public class ListPage extends Page {
	
	
	private List<String> l;

	public ListPage(List<String> l){
		super();
		
		this.l = l;
		
	}
	
	

	@Override
	public void init() {
		
		
		
		if(hasInit){
			
			return;
		}
		int in = 0;
		//final int end = Pages.getAvailableID()+3;
		for(int i = 0; i< l.size();i++){
		this.buttons.add(new TextButton(i, x+16, y+32+16*(in), StatCollector.translateToLocal(l.get(i))));
		in++;
		}
		
		super.init();
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
		
		if(b.id != LibMisc.GuiIDs.Buttons.BACK){
			
				System.out.println(l.get(b.id));
		
		
			
			g.changePage(l.get(b.id));
		}
		
		
		super.handleButtons(b);
	}









	@Override
	public String returnPage() {
		
		return "HOME";
	}


	@Override
	public Categories getCategory() {
		
		return null;
	}
	




	
	
	







	
	

}
