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
	
	
	
	

	

	private List<Item> l;
	private List<Block> bl;
	
	public ItemListPage(List<Item> l2, String s) {
		super(s);
		
		if(l2.size() > 8){
			
			List<Item> tl = new ArrayList<Item>();
			List<Item> sfl = new ArrayList<Item>();
			int ai=0;
			int index = 0;
			boolean tla = false;
			for(Item i : l2){
				
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
				Page p = new ItemListPage(sfl, this.name+k);
				p.setReturnPage(name);
			
				Pages.addPage(this.name+k, p);
				sp = (this.name + k);
				
			
			
			
			
		}else
		this.l = l2;
		
		this.bl = null;
	}
	
	private List<Item> copyL(List<Item> ol) {
		
		
		
		List<Item> nl = new ArrayList<Item>();
		
		
		for(Item i : ol){
			nl.add(i);
		}
		
		return nl ;
		
	}
	
private List<Block> copyBL(List<Block> ol) {
		
		
		
		List<Block> nl = new ArrayList<Block>();
		
		
		for(Block i : ol){
			nl.add(i);
		}
		
		return nl ;
		
	}

	public ItemListPage(List<Block> l2, String s, int useless) {
		super(s);
		
		if(l2.size() > 8){
			
			List<Block> tl = new ArrayList<Block>();
			List<Block> sfl = new ArrayList<Block>();
			int ai=0;
			int index = 0;
			boolean tla = false;
			for(Block i : l2){
				
				if(index < 8){
				
				
				tl.add(i);
			
				
				
				index++;
				ai++;
				if(ai != l2.size())
				continue;
				}
					if(!tla){
						
					this.bl = this.copyBL(tl);
					
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
				Page p = new ItemListPage(sfl, this.name+k,0);
				p.setReturnPage(name);
			
				Pages.addPage(this.name+k, p);
				sp = (this.name + k);
				
			
			
			
			
		}else
		this.bl = l2;
		
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
		String s = StatCollector.translateToLocal(this.name);
		
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
