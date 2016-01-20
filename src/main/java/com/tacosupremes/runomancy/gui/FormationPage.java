package com.tacosupremes.runomancy.gui;

import org.lwjgl.opengl.GL11;

import com.tacosupremes.runomancy.common.block.rune.IRune;
import com.tacosupremes.runomancy.common.runelogic.IRuneEffect;
import com.tacosupremes.runomancy.common.runelogic.RuneFormations;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

public class FormationPage extends Page {

	private IRuneEffect effect;

	public FormationPage(IRuneEffect i) {
		this.effect = i;
	}

	@Override
	public void draw(int mx, int my, float ticks) {
	
		int s = (int)Math.sqrt(effect.getNeededBlocks().length);
		
		String str = StatCollector.translateToLocal(effect.getName());
		
		Minecraft.getMinecraft().fontRendererObj.drawString(str, x +w/2 - Minecraft.getMinecraft().fontRendererObj.getStringWidth(str) / 2, y+20, 0);
		
		this.drawFormation(x + w / 2 - ( s * 13 + s) / 2 , y+40, effect);
		
		
	}
	
	
	
void drawFormation(int left, int top, IRuneEffect re){
		
		GL11.glPushMatrix();
		GL11.glColor4f(1F, 1F, 1F, 1F);
		
		RenderHelper.enableGUIStandardItemLighting();
		int index = 0;
		int x = 0;
		int y = 0;
		
		int d =(int) Math.sqrt(re.getNeededBlocks().length);
		
		for(Block b : re.getNeededBlocks()){
			
			IRune i = (IRune)b;
			if(b != null)
		Minecraft.getMinecraft().getRenderItem().renderItemIntoGUI(new ItemStack(b), left+x, top+y);
		
		index ++;
		x+=12;
		
		
		if(index % d == 0){
		
		x = 0;
		y+=12;
		
		}
		}
	
		
		GL11.glPopMatrix();
	
	}

	
	@Override
	public String returnPage() {
		
		return effect.getName();
	}

	@Override
	public Categories getCategory() {
		
		return null;
	}

}
