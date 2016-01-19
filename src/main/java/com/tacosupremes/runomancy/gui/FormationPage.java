package com.tacosupremes.runomancy.gui;

import org.lwjgl.opengl.GL11;

import com.tacosupremes.runomancy.common.block.rune.IRune;
import com.tacosupremes.runomancy.common.runelogic.IRuneEffect;
import com.tacosupremes.runomancy.common.runelogic.RuneFormations;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;

public class FormationPage extends Page {

	private IRuneEffect effect;

	public FormationPage(IRuneEffect i) {
		this.effect = i;
	}

	@Override
	public void draw(int mx, int my, float ticks) {
	
		int s = (int)Math.sqrt(effect.getNeededBlocks().length);
		
		this.drawFormation(x + w / 2 - ( s * 12) / 2 , y+20, effect);
		
		
	}
	
	
	
void drawFormation(int left, int top, IRuneEffect re){
		
		GL11.glPushMatrix();
		GL11.glColor4f(1F, 1F, 1F, 1F);
		int index = 0;
		int x = 0;
		int y = 0;
		
		int d =(int) Math.sqrt(re.getNeededBlocks().length);
		
		for(Block b : re.getNeededBlocks()){
			
			IRune i = (IRune)b;
			
		Minecraft.getMinecraft().getRenderItem().renderItemIntoGUI(new ItemStack(b), left+8+x, top+40+y);
		
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
