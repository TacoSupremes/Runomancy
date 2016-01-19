package com.tacosupremes.runomancy.gui;

import org.lwjgl.opengl.GL11;

import com.tacosupremes.runomancy.common.runelogic.IFunctionalRuneEffect;
import com.tacosupremes.runomancy.common.runelogic.IRuneEffect;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.StatCollector;

public class EffectPage extends Page {

	private IRuneEffect effect;

	public EffectPage(IRuneEffect i) {
		this.effect  = i;
	}

	@Override
	public void draw(int mx, int my, float ticks) {
	
		GL11.glPushMatrix();
		
		FontRenderer f = Minecraft.getMinecraft().fontRendererObj;
	
	
	String s = StatCollector.translateToLocal(effect.getName() + ".entry");

	ItemPage.drawTextSplit(s, f, x+16, y+32, w-32, 0);
	
	String n = StatCollector.translateToLocal(effect.getName());
	f.drawString(n, x + w / 2 - f.getStringWidth(n) / 2, y+16, 0);
	
	
	GL11.glPopMatrix();
		

	}

	
	@Override
	public String returnPage() {
		
		
		return getCategory().getName();
	}

	@Override
	public Categories getCategory() {
		
		return effect instanceof IFunctionalRuneEffect ? Categories.Functional : Categories.Generating;
	}

}
