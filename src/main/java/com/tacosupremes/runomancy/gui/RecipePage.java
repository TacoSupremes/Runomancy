package com.tacosupremes.runomancy.gui;

import java.util.List;

import org.lwjgl.opengl.GL11;

import com.tacosupremes.runomancy.common.lib.LibMisc;
import com.tacosupremes.runomancy.common.recipes.ModRecipes;
import com.tacosupremes.runomancy.gui.buttons.TextButton;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

public class RecipePage extends Page {

	private ItemStack item;


	public RecipePage(ItemStack is){
		super();
		this.item = is;
	}

	public static final ResourceLocation texture = new ResourceLocation("runomancy:/textures/gui/grid.png");

	int sx,sy;
	
	
	@Override
	public void initPage(int x, int y, int w, int h, GuiModBook g) {
		
		super.initPage(x, y, w, h, g);
	
		sx = x+ w / 2 - 27;
		sy = y+40;
	
		
		
		
	}

	

	@Override
	public void draw(int mx, int my, float tick) {
		
		
		GL11.glPushMatrix();
		
		
				
		
		this.drawRecipe(sx, sy, mx, my);
		
		
		int index = 0;
		int wx = 1;
		int wy = 1;
		
		int wd =3;
		
		for(ItemStack wis : ModRecipes.getRecipe(item)){
			
			
			
			
			index ++;
			wx+=18;
			int ssx = index == 1 || index == 4 || index == 7 ? sx : sx+wx-18;
			int ex =index == 1 || index == 4 || index == 7 ? sx+wx : sx+ wx;
			
			//int ssx = sx;
			//int ex = sx+wx;
			if((ssx < mx && mx < ex) && (my > wy+sy && my <wy+19+sy) && wis != null)
				this.g.drawTooltip(wis, mx, my);
			
			if(index % wd == 0){
			
			wx = 1;
			wy+=16;
			
			}
			
			
			
			
			
			
		}
		
		GL11.glPopMatrix();
		
	}
	

	private void drawRecipe(int s, int t, int mx, int my) {
		ItemStack[] l = ModRecipes.getRecipe(item);
		
		GL11.glColor4f(1F, 1F, 1F, 1F);
		Minecraft.getMinecraft().renderEngine.bindTexture(texture);
		
		g.drawModalRectWithCustomSizedTexture(s, t, 0, 0, 54, 54, 54, 54);

		
		if(l != null){
			
			int index = 0;
			int x = 1;
			int y = 1;
			
			int d =3;
			RenderHelper.enableGUIStandardItemLighting();
			for(ItemStack is : l){
				
				
				
			
			Minecraft.getMinecraft().getRenderItem().renderItemAndEffectIntoGUI(is, s+x, t+y);
			
			Minecraft.getMinecraft().getRenderItem().renderItemOverlays(Minecraft.getMinecraft().fontRendererObj, is, s+x, t+y);
			
			
			
			index ++;
			x+=18;
			
			
			if(index % d == 0){
			
			x = 1;
			y+=18;
			
			}
			
			
				
				
			}
			RenderHelper.disableStandardItemLighting();;
			
		}else
			System.out.println("FML");
		
		
	}
	
	


	

	@Override
	public void mouseClicked(int mx, int my, int mouseButton) {
		
		
	
		int index = 0;
		int wx = 0;
		int wy = 1;
		
		int wd =3;
		
		for(ItemStack wis : ModRecipes.getRecipe(item)){
			
			
			
			index ++;
			wx+=19;
			int ssx = index == 1 || index == 4 || index == 7 ? sx : sx+wx-19;
			int ex =index == 1 || index == 4 || index == 7 ? sx+wx : sx+ wx;
			
			//int ssx = sx;
			//int ex = sx+wx;
			if((ssx < mx && mx < ex) && (my > wy+sy && my <wy+19+sy)){
			
				if(GuiModBook.hasEntry(wis)){
					g.changePage(wis.getUnlocalizedName());
				}
				//	System.out.println(wis.getUnlocalizedName());
			}
			if(index % wd == 0){
			
			wx = 0;
			wy+=16;
			
			}
			
			
			
		
			
			
			
		}
		
		
		
		
	}


	

	@Override
	public String returnPage() {
		
		return item.getUnlocalizedName();
	}

	@Override
	public Categories getCategory() {
		
		return Categories.Recipe;
	}


	@Override
	public void init() {
		
		buttons.add(new TextButton(LibMisc.GuiIDs.Buttons.BACK, x+w/2 - Minecraft.getMinecraft().fontRendererObj.getStringWidth(StatCollector.translateToLocal("runomancy.back"))/2, y+(h-24), StatCollector.translateToLocal("runomancy.back")));
		
		
	}
	
	 

}
