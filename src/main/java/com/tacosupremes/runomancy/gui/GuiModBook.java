package com.tacosupremes.runomancy.gui;

import java.io.IOException;
import java.util.List;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.translation.I18n;

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
		
		if(Pages.pages.get(s) == null){
			System.out.println("RIP : " + s);
			return;
		}
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
		
		
		if(item == null)
			return false;
		
		String s = "runomancy."+item.getUnlocalizedName().substring(5)+".entry";
		
		return I18n.translateToLocal(s) != s;
		
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
	                    guibutton = event.getButton();
	                  //  this.selectedButton = guibutton;
	                    guibutton.playPressSound(this.mc.getSoundHandler());
	                    this.actionPerformed(guibutton);
	                    if (this.equals(this.mc.currentScreen))
	                        net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.client.event.GuiScreenEvent.ActionPerformedEvent.Post(this, event.getButton(), this.buttonList));
	                
	                return;
	                }
	            }
	        }
	    }
	
	 
	 public void setBL(List<GuiButton> bl){
		 this.buttonList = bl;
	 }
	

	
}
