package com.tacosupremes.runomancy.gui;

import com.tacosupremes.runomancy.common.recipes.RuneChargerRecipe;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderHelper;

public class RuneChargerPage extends Page {

	
	
	
	private RuneChargerRecipe r;

	public RuneChargerPage(RuneChargerRecipe r) {
		
		this.r = r;
	}

	@Override
	public void draw(int mx, int my, float ticks) {
		
		
		
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
