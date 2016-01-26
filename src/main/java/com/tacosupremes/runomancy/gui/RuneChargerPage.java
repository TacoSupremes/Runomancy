package com.tacosupremes.runomancy.gui;

import com.tacosupremes.runomancy.common.lib.LibMisc;
import com.tacosupremes.runomancy.common.recipes.RuneChargerRecipe;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.util.StatCollector;

public class RuneChargerPage extends Page {

	
	
	
	private RuneChargerRecipe r;

	public RuneChargerPage(RuneChargerRecipe r) {
		super(r.getOut().getUnlocalizedName().substring(5)+ "REC");
		this.r = r;
	}

	@Override
	public void draw(int mx, int my, float ticks) {
		String str = StatCollector.translateToLocal(LibMisc.MODID+"."+"chargerRecipe");
		Minecraft.getMinecraft().fontRendererObj.drawString(str, x + w/2 - Minecraft.getMinecraft().fontRendererObj.getStringWidth(str)/2,y+16, 0);
		
		RenderHelper.enableGUIStandardItemLighting();
		
		Minecraft.getMinecraft().getRenderItem().renderItemAndEffectIntoGUI(r.getIn(),  x+w/2 - 10 - 30, y+h / 2 - 10);
		
	
		
		Minecraft.getMinecraft().getRenderItem().renderItemAndEffectIntoGUI(r.getOut(), x+w/2 - 10 + 30, y+h / 2 - 10);
		
		
		
	}

	@Override
	public String returnPage() {

		return r.getOut().getUnlocalizedName();

	}

	@Override
	public Categories getCategory() {

		return null;

	}

}
