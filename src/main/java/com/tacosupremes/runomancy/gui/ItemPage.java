package com.tacosupremes.runomancy.gui;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import com.tacosupremes.runomancy.common.block.rune.IRune;
import com.tacosupremes.runomancy.common.lib.LibMisc;
import com.tacosupremes.runomancy.common.recipes.ModRecipes;
import com.tacosupremes.runomancy.gui.buttons.TextButton;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ItemPage extends Page {

	private ItemStack item;
	
	
	
	

	
	
	public ItemPage(ItemStack is){
		super();
		this.item = is;
	}
	

	


	

	@Override
	public void init() {
		String s = "Recipe";
		this.buttons.add(new TextButton(0, x + w / 2 - Minecraft.getMinecraft().fontRendererObj.getStringWidth(s) , y+h-40, s));
		
		buttons.add(new TextButton(LibMisc.GuiIDs.Buttons.BACK, x+w/2 - Minecraft.getMinecraft().fontRendererObj.getStringWidth(StatCollector.translateToLocal("runomancy.back"))/2, y+(h-24), StatCollector.translateToLocal("runomancy.back")));
		
		
	}

	@Override
	public void handleButtons(GuiButton b) {
	
		
		
		if(b.id == 0){
			g.changePage(item.getUnlocalizedName().substring(5)+"REC");//new RecipePage(x, y, w, h, g, this, item));
			return;
		}
	
		
		super.handleButtons(b);
	}



	

	@Override
	public boolean needsBackButton() {
		
		return true;
	}

	@Override
	public void draw(int mx, int my, float tick) {
		
		GL11.glPushMatrix();
		
			FontRenderer f = Minecraft.getMinecraft().fontRendererObj;
		
		
		String s = StatCollector.translateToLocal("runomancy."+item.getUnlocalizedName().substring(5) + ".entry");
	
		drawTextSplit(s, f, x+16, y+32, w-32, 0);
		
		String n = StatCollector.translateToLocal(item.getUnlocalizedName() + ".name");
		f.drawString(n, x + w / 2 - f.getStringWidth(n) / 2, y+16, 0);
		
		
		GL11.glPopMatrix();
		

	}
	
	

	public static void drawTextSplit(String s, FontRenderer f, int x, int y,  int trim,  int color){
		
		if(f.getStringWidth(s) < trim){
			f.drawString(s, x, y, color);
			return;
		}
		
		List<String> toDraw = new ArrayList<String>();
		
		String cs = "";
		char[] ca = s.toCharArray();
		int space = -1;
	
		for(int i = 0; i<s.length(); i++){
			
			cs +=ca[i];
			
			if(ca[i] == ' ')
				space = i;
			
			if(f.getStringWidth(cs) >= trim){
				if(ca[i] != ' '){
					
					for(int d = i; d> space; d--){
					StringBuilder sb = new StringBuilder(cs);
					sb.deleteCharAt(cs.length()-1);
					cs = sb.toString();
					
					}
					
				}
					
				toDraw.add(cs);
				cs = "";
				i=space;
			}
			
			
			
			
		
		}
		
		toDraw.add(cs);
		
		for(int i = 0; i< toDraw.size();i++){
			f.drawString(toDraw.get(i), x, y+i*f.FONT_HEIGHT, color);
			
		}
		
		
		
		
	}


	@Override
	public String returnPage() {
		// TODO Auto-generated method stub
		return this.getCategory().getName();
	}

	@Override
	public Categories getCategory() {
		// TODO Auto-generated method stub
		Item i  = item.getItem();
		Block b = Block.getBlockFromItem(i);
		if(b != null){
			
		return ((IPageGiver)b).getCategories();	
			
		}
		
		return ((IPageGiver)i).getCategories();	
	}
	


}
