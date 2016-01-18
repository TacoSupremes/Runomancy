package com.tacosupremes.runomancy.gui.buttons;

import java.awt.Color;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;

public class TextButton extends GuiButton {

	public TextButton(int buttonId, int x, int y, String buttonText) {
		super(buttonId, x, y, Minecraft.getMinecraft().fontRendererObj.getStringWidth(buttonText) - 1, Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT, buttonText);
	
	}
	
	public TextButton(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText) {
		super(buttonId, x, y, widthIn, heightIn, buttonText);
	
	}

	@Override
	public void drawButton(Minecraft mc, int mouseX, int mouseY) {
		
		if (this.visible)
        {
            FontRenderer fontrenderer = mc.fontRendererObj;
            
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            this.hovered = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + fontrenderer.getStringWidth(displayString) - 1 && mouseY < this.yPosition + fontrenderer.FONT_HEIGHT;
            int i = this.getHoverState(this.hovered);
            this.mouseDragged(mc, mouseX, mouseY);
            int j = Color.black.getRGB();

            if (packedFGColour != 0)
            {
                j = packedFGColour;
            }
            else
            if (!this.enabled)
            {
                j = 10526880;
            }
            else if (this.hovered)
            {
                j = 16777120;
            }
            fontrenderer.drawString(this.displayString, this.xPosition, this.yPosition, j);
           // fontrenderer.drawSplitString(this.displayString, this.xPosition, this.yPosition,0, j);
           
        }
		
		
		
    
	}

	
	

}
