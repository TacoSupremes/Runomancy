package com.tacosupremes.runomancy.gui;

import org.lwjgl.opengl.GL11;

import com.tacosupremes.runomancy.common.runelogic.IFunctionalRuneEffect;
import com.tacosupremes.runomancy.common.runelogic.IRuneEffect;
import com.tacosupremes.runomancy.gui.buttons.TextButton;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.text.translation.I18n;

public class EffectPage extends Page {

	private IRuneEffect effect;

	public EffectPage(IRuneEffect i) {
		super(i.getName());
		this.effect  = i;
	}

	@Override
	public void draw(int mx, int my, float ticks) {
	
		GL11.glPushMatrix();
		
		FontRenderer f = Minecraft.getMinecraft().fontRendererObj;
	
	
	String s = I18n.translateToLocal(effect.getName() + ".entry");

	ItemPage.drawTextSplit(s, f, x+16, y+32, w-32, 0);
	
	String n = I18n.translateToLocal(effect.getName());
	f.drawString(n, x + w / 2 - f.getStringWidth(n) / 2, y+16, 0);
	
	
	GL11.glPopMatrix();
		

	}
	
	

	
	@Override
	public void handleButtons(GuiButton gb) {
		
		if(gb.id == 0)
			this.g.changePage(effect.getName()+".formation");
		
		super.handleButtons(gb);
		
	}

	@Override
	public void init() {
		
		String s = I18n.translateToLocal("runomancy.viewFormation");
		
		if(!this.hasInit)
		this.buttons.add(new TextButton(0, x+w/2-Minecraft.getMinecraft().fontRendererObj.getStringWidth(s) / 2, y+h-30, s));
		
		super.init();
	}

	@Override
	public void initButtons() {
		
		String s = I18n.translateToLocal("runomancy.viewFormation");
	
		this.buttons.get(0).xPosition = x+w/2-Minecraft.getMinecraft().fontRendererObj.getStringWidth(s) / 2;
		this.buttons.get(0).yPosition = y+h-40;
		
		
		super.initButtons();
		
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
