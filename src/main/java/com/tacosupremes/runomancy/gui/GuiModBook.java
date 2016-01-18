package com.tacosupremes.runomancy.gui;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Queue;

import org.lwjgl.opengl.GL11;

import com.tacosupremes.runomancy.common.block.rune.IRune;
import com.tacosupremes.runomancy.common.lib.LibMisc;
import com.tacosupremes.runomancy.common.runelogic.IRuneEffect;
import com.tacosupremes.runomancy.common.runelogic.RuneEffectRepair;
import com.tacosupremes.runomancy.proxy.ClientProxy;

import net.java.games.input.Keyboard;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatAllowedCharacters;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

public class GuiModBook extends GuiScreen {


	
	public static final ResourceLocation texture = new ResourceLocation("runomancy:/textures/gui/book.png");

		
	
	int guiWidth = 146;
	int guiHeight = 180;
	int left, top;
	
	public Page cp;
	
	public boolean canInput = false;
	
	public static int HOME = 0;
	
	
	public int level = 0;
	
	
	@Override
	public final void initGui() {
	

		left = width / 2 - guiWidth / 2;
		top = height / 2 - guiHeight / 2;
		
		
		cp =  Pages.pages.get("HOME");
		cp.initPage(left, top, guiWidth, guiHeight, this);
		cp.init();
		cp.initButtons();
		this.buttonList = cp.buttons;
		this.allowUserInput = true;
		
	}

	//public List<GuiButtonBase> mb = new ArrayList<GuiButtonBase>();
	
	
	@Override
	public void updateScreen() {
	
		
		
	}

	@Override
	public void drawScreen(int par1, int par2, float par3) {
	
		
		

		
		
		GL11.glColor4f(1F, 1F, 1F, 1F);
		mc.renderEngine.bindTexture(texture);
		drawTexturedModalRect(left, top, 0, 0, guiWidth, guiHeight);

		cp.draw(par1,par2, par3);

	
			
			

		//drawFormation(new RuneEffectRepair());

	
		super.drawScreen(par1, par2, par3);

		
	}

	
	
	

	
	void drawFormation(IRuneEffect re){
		
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
	

	
	



	
	protected void actionPerformed(GuiButton gb) {
	
	
		
		cp.handleButtons(gb);
		
	}

	

	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}




	boolean closeScreenOnInvKey() {
		return true;
	}



	public void changePage(String s){
		this.cp = Pages.pages.get(s);
		System.out.print(s);
		
		this.canInput = false;
		this.allowUserInput = false;
		this.cp.initPage(left, top, guiWidth, guiHeight, this);
		this.cp.init();
		this.cp.initButtons();
		this.buttonList = cp.buttons;
		
		
		this.canInput = true;
		this.allowUserInput = true;
	}

	
	public void drawTooltip(ItemStack stack, int x, int y){
		this.renderToolTip(stack, x, y);
	}
	
	
	public static boolean hasEntry(ItemStack item){
		
		String s = "runomancy."+item.getUnlocalizedName().substring(5)+".entry";
		
		return StatCollector.translateToLocal(s) != s;
		
	}
	
	 protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException
	    {
		 
		 cp.mouseClicked(mouseX, mouseY, mouseButton);
     	
	        if (mouseButton == 0)
	        {
	        	
	        	
	            for (int i = 0; i < this.buttonList.size(); ++i)
	            {
	                GuiButton guibutton = (GuiButton)this.buttonList.get(i);

	                if (guibutton.mousePressed(this.mc, mouseX, mouseY))
	                {
	                    net.minecraftforge.client.event.GuiScreenEvent.ActionPerformedEvent.Pre event = new net.minecraftforge.client.event.GuiScreenEvent.ActionPerformedEvent.Pre(this, guibutton, this.buttonList);
	                    if (net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(event))
	                        break;
	                    guibutton = event.button;
	                  //  this.selectedButton = guibutton;
	                    guibutton.playPressSound(this.mc.getSoundHandler());
	                    this.actionPerformed(guibutton);
	                    if (this.equals(this.mc.currentScreen))
	                        net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.client.event.GuiScreenEvent.ActionPerformedEvent.Post(this, event.button, this.buttonList));
	                
	                return;
	                }
	            }
	        }
	    }
	
	

	
}
