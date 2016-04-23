package com.tacosupremes.runomancy.gui;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import com.tacosupremes.runomancy.common.lib.LibMisc;
import com.tacosupremes.runomancy.gui.buttons.ItemButton;
import com.tacosupremes.runomancy.gui.buttons.TextButton;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.translation.I18n;

public class ItemListPage extends Page {

	private List<Item> l;
	
	private List<Block> bl;
	
	public ItemListPage(List<Item> l2, String s) {
		super(s);
		
		if(l2.size() > 8){
			
			List<Item> tl = new ArrayList<Item>();
			List<Item> sfl = new ArrayList<Item>();
			
			
			boolean tla = false;
			
			
			for(Item i : l2){
				
				if(tl.size() < 7 && !tla){
				
				tl.add(i);
				
				}else{
					if(!tla){
						tl.add(i);
					this.l = this.copyL(tl);
					
					
					tl.removeAll(tl);
					
					tla = true;
					
					}else{
					
					sfl.add(i);
					
					
						
					}
					
					
				}
				
				
			}
			
		String tempName = ListPage.removeNumbers(name);
			
			boolean validName = false;
			int k = 1;
			
			while(!validName){
				
				if(!Pages.pages.containsKey(tempName+k) && !Pages.taken.contains(tempName+k))
					break;
				
				k+=1;
				
			}
			
			Pages.taken.add(tempName+k);
			
			for(Item b : this.l){
				
				IPageGiver ip = ((IPageGiver)b);
				
				Page p = ((IPageGiver)b).getPage();
				p.setReturnPage(name);
				
				Pages.addPage(b.getUnlocalizedName(), p);
				
				if(ip.getSubPages() != null)
					Pages.addPage(b.getUnlocalizedName()+1,(ip.getSubPages()));
				
				if(ip.hasNormalRecipe())
					Pages.addPage(b.getUnlocalizedName().substring(5)+"REC", new RecipePage(new ItemStack(b)));
				
				
			}
			
			
				Page p = new ItemListPage(sfl, tempName+k);
				p.setReturnPage(name);
			
				Pages.addPage(tempName+k, p);
				sp = (tempName + k);
				
			
			
			
			
		}else{
		this.l = l2;
		for(Item b : this.l){
			
			IPageGiver ip = ((IPageGiver)b);
			
			Page p = ((IPageGiver)b).getPage();
			p.setReturnPage(name);
			
			Pages.addPage(b.getUnlocalizedName(), p);
			
			if(ip.getSubPages() != null)
				Pages.addPage(b.getUnlocalizedName()+1,(ip.getSubPages()));
			
			if(ip.hasNormalRecipe())
				Pages.addPage(b.getUnlocalizedName().substring(5)+"REC", new RecipePage(new ItemStack(b)));
			
			
		}
		this.bl = null;
		
		}
		
		
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
			
			boolean tla = false;
			
			for(Block i : l2){
				
				if(tl.size() < 7 && !tla){
				
				tl.add(i);
				
				}else{
					if(!tla){
						tl.add(i);
					this.bl = this.copyBL(tl);
					
					
					tl.removeAll(tl);
					
					tla = true;
					
					}else{
					
					sfl.add(i);
					
					
						
					}
					
					
				}
				
				
			}
			
		String tempName = ListPage.removeNumbers(name);
			
			boolean validName = false;
			int k = 1;
			
			while(!validName){
				
				if(!Pages.pages.containsKey(tempName+k) && !Pages.taken.contains(tempName+k))
					break;
				
				k+=1;
				
			}
			
			Pages.taken.add(tempName+k);
			
			for(Block b : this.bl){
				
				IPageGiver ip = ((IPageGiver)b);
				
				Page p = ((IPageGiver)b).getPage();
				p.setReturnPage(name);
				
				Pages.addPage(b.getUnlocalizedName(), p);
				
				if(ip.getSubPages() != null)
					Pages.addPage(b.getUnlocalizedName()+1,(ip.getSubPages()));
				
				if(ip.hasNormalRecipe())
					Pages.addPage(b.getUnlocalizedName().substring(5)+"REC", new RecipePage(new ItemStack(b)));
				
				
			}
			
			
			
				Page p = new ItemListPage(sfl, tempName+k,0);
				p.setReturnPage(name);
			
				Pages.addPage(tempName+k, p);
				sp = (tempName + k);
				
			
			
			
			
		}else{
		this.bl = l2;
		this.l = null;
		
		for(Block b : this.bl){
			
			IPageGiver ip = ((IPageGiver)b);
			
			Pages.addPage(b.getUnlocalizedName(), ((IPageGiver)b).getPage());
			
			if(ip.getSubPages() != null)
				Pages.addPage(b.getUnlocalizedName()+1,(ip.getSubPages()));
			
			if(ip.hasNormalRecipe())
				Pages.addPage(b.getUnlocalizedName().substring(5)+"REC", new RecipePage(new ItemStack(b)));
			
			
		}
		
		}
	}

	
	

	@Override
	public void init() {
		
	
		
		
		if(!hasInit){
			
			
		if(bl == null){
		for(int i = 0; i<l.size();i++){
		this.buttons.add(new ItemButton(i, x+16, y+32+16*i, I18n.translateToLocal(l.get(i).getUnlocalizedName() +".name"), l.get(i)));
		
		}
		}else{
			
			for(int i = 0; i<bl.size();i++){
				this.buttons.add(new ItemButton(i, x+16, y+32+16*i, I18n.translateToLocal(bl.get(i).getUnlocalizedName() +".name"), bl.get(i)));
				
				}
			
		}
		
		buttons.add(new TextButton(LibMisc.GuiIDs.Buttons.BACK, x+w/2 - Minecraft.getMinecraft().fontRendererObj.getStringWidth(I18n.translateToLocal("runomancy.back"))/2, y+(h-24), I18n.translateToLocal("runomancy.back")));
		
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
		String s = I18n.translateToLocal(LibMisc.MODID +"."+ ListPage.removeNumbers(this.name).toLowerCase());
		
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
