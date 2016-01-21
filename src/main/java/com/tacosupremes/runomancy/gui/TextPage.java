package com.tacosupremes.runomancy.gui;

import java.util.List;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.StatCollector;

public class TextPage extends Page {

	public TextPage(String s, String returnString) {
		super(s);
		this.returnName = returnString;
		Pages.taken.add(s);
		
	}
	
	public TextPage(String s, String returnString, int totalPages) {
		this(s,returnString);
		this.returnName = returnString;
		
		if(totalPages > 1){
		String tempName = ListPage.removeNumbers(name);
		
		boolean validName = false;
		int k = 1;
		
		while(!validName){
			
			if(!Pages.pages.containsKey(tempName+k) && !Pages.taken.contains(tempName+k))
				break;
			
			k+=1;
			
		}
		
		Pages.taken.add(tempName+k);
		this.sp = (tempName+k);
		Pages.addPage(tempName+k, new TextPage(tempName+k, this.name, totalPages-1));
		
		}
			
	}

	@Override
	public void draw(int mx, int my, float ticks) {
		
		GL11.glPushMatrix();
		
		FontRenderer f = Minecraft.getMinecraft().fontRendererObj;
	
	String s = StatCollector.translateToLocal("runomancy."+ this.name + ".entry");
	
	//ItemPage.drawTextSplit(s, f, x+16, y+32, w-32, 0);
	
	String n = StatCollector.translateToLocal("runomancy."+ListPage.removeNumbers(name));
	f.drawString(n, x + w / 2 - f.getStringWidth(n) / 2, y+16, 0);
	
	
	GL11.glPopMatrix();
		
		
		
	}

	@Override
	public String returnPage() {

		return this.returnName;

	}

	@Override
	public Categories getCategory() {

		return null;

	}

}
