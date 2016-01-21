package com.tacosupremes.runomancy.gui;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import com.tacosupremes.runomancy.common.lib.LibMisc;
import com.tacosupremes.runomancy.gui.buttons.TextButton;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.item.Item;
import net.minecraft.util.StatCollector;

public class ListPage extends Page {
	
	
	private List<String> l;

	public ListPage(List<String> l2, String s){
		super(s);
		
		if(l2.size() > 8){
			
			List<String> tl = new ArrayList<String>();
			List<String> sfl = new ArrayList<String>();
			int ai=0;
			int index = 0;
			boolean tla = false;
			for(String i : l2){
				
				if(index < 8){
				
				
				tl.add(i);
			
				
				
				index++;
				ai++;
				if(ai != l2.size())
				continue;
				}
					if(!tla){
						
					this.l = this.copyL(tl);
					
					tl.removeAll(tl);
					
					tla = true;
					
					}else{
						
					sfl.add(i);
					
					
						
					}
					
					
					
				
				ai++;
			}
			
		
			
			boolean validName = false;
			int k = 1;
			
			while(!validName){
				
				if(!Pages.pages.containsKey(this.name+k))
					break;
				
				k+=1;
				
			}
				Page p = new ListPage(sfl, this.name+k);
				p.setReturnPage(name);
			
				Pages.addPage(this.name+k, p);
				sp = (this.name + k);
				
			
			
			
			
		}else
		this.l = l2;
		
		
		
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
			
			if(gb.id == LibMisc.GuiIDs.Buttons.BACK || gb.id == LibMisc.GuiIDs.Buttons.NEXT)
				continue;
			
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
		
		if(b.id != LibMisc.GuiIDs.Buttons.BACK && b.id != LibMisc.GuiIDs.Buttons.NEXT)
			g.changePage(l.get(b.id));
		
		
		
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
	




	
	
	

public List<String> copyL(List<String> ol){
	
	
	
	
	List<String> nl = new ArrayList<String>();
	
	
	for(String o : ol){
		
		nl.add(o.copyValueOf(o.toCharArray()));
	}
	
	
	
	return nl;
}





	
	

}
