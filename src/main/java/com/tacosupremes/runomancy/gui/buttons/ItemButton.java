package com.tacosupremes.runomancy.gui.buttons;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemButton extends TextButton {

	private ItemStack item;
	private boolean isItem = false;


	public ItemButton(int buttonId, int x, int y, String buttonText, ItemStack item) {
		super(buttonId, x, y, 16+Minecraft.getMinecraft().fontRendererObj.getStringWidth(buttonText)+ 15, Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT, buttonText);
		this.item = item;
		if(Block.getBlockFromItem(item.getItem()) == null)
			isItem = true;
		
	}

	public ItemButton(int buttonId, int x, int y, String buttonText, Item item) {
		super(buttonId, x, y, 16+Minecraft.getMinecraft().fontRendererObj.getStringWidth(buttonText)+ 15, Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT, buttonText);
		this.item = new ItemStack(item);
		this.isItem = true;
		
	}
	
	public ItemButton(int buttonId, int x, int y, String buttonText, Block block) {
		super(buttonId, x, y, 16+Minecraft.getMinecraft().fontRendererObj.getStringWidth(buttonText)+ 15, Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT, buttonText);
		this.item = new ItemStack(block);
		this.isItem = false;
		
	}
	
	@Override
	public void drawButton(Minecraft mc, int mouseX, int mouseY) {
		
		if (this.visible)
        {
			
			
			
			GL11.glPushMatrix();
			GL11.glColor4f(1F, 1F, 1F, 1F);
			
			RenderHelper.enableGUIStandardItemLighting();
			mc.getRenderItem().renderItemIntoGUI(item, xPosition - 1, yPosition - (isItem ? 5 : 8));
			RenderHelper.disableStandardItemLighting();
			
			GL11.glPopMatrix();
			
            FontRenderer fontrenderer = mc.fontRendererObj;
            
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            this.hovered = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + fontrenderer.getStringWidth(displayString)+16 - 1 && mouseY < this.yPosition + fontrenderer.FONT_HEIGHT;
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
            fontrenderer.drawString(this.displayString, this.xPosition+16, this.yPosition, j);
           // fontrenderer.drawSplitString(this.displayString, this.xPosition, this.yPosition,0, j);
           
        }
    
	}

	public ItemStack getItem() {
		return item;
	}
	
	
}
