package com.tacosupremes.runomancy.gui;

import org.lwjgl.opengl.GL11;

import com.tacosupremes.runomancy.common.Runomancy;
import com.tacosupremes.runomancy.common.lib.LibMisc;
import com.tacosupremes.runomancy.common.recipes.ModRecipes;
import com.tacosupremes.runomancy.gui.buttons.TextButton;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.oredict.OreDictionary;

public class RecipePage extends Page {

	private ItemStack item;


	public RecipePage(ItemStack is){
		super(is.getUnlocalizedName().substring(5)+"REC");
		this.item = is;
	}

	public static final ResourceLocation texture = new ResourceLocation("runomancy:/textures/gui/grid.png");

	int sx,sy;
	
	@Override
	public void initPage(int x, int y, int w, int h, GuiModBook g) {
		
		super.initPage(x, y, w, h, g);
	
		sx = x+ w / 2 - 27;
		
		sy = y+40;
	
	}

	@Override
	public void draw(int mx, int my, float tick) {
		
		GL11.glPushMatrix();
		
		this.drawRecipe(sx, sy, mx, my);
		
		int index = 0;
		
		int wx = 1;
		
		int wy = 1;
		
		int wd =3;
		
		for(ItemStack[] wis : ModRecipes.getRecipe(item)){
			
			ItemStack ac;
			
			if(wis != null){
			if(wis.length == 1)
				ac = wis[0];
			else{
				
				ac = wis[ore[index]];
			}
			
			}else
				ac = null;
			
			
			index ++;
			wx+=18;
			
			int ssx = index == 1 || index == 4 || index == 7 ? sx : sx+wx-18;
			
			int ex =index == 1 || index == 4 || index == 7 ? sx+wx : sx+ wx;
			
			if((ssx < mx && mx < ex) && (my > wy+sy && my <wy+19+sy) && ac != null)
				this.g.drawTooltip(ac, mx, my);
			
			if(index % wd == 0){
			
			wx = 1;
			wy+=16;
			
			}
			
		}
		
		GL11.glPopMatrix();
		
	}
	
	int[] m = new int[]{0,0,0,
						0,0,0,
						0,0,0};
	
	int[] ore = new int[]{0,0,0,
						  0,0,0,
						  0,0,0};
	
	int ticks = 39;
	
	private void drawRecipe(int s, int t, int mx, int my) {
		
		ticks++;
		
		ItemStack[][] l = ModRecipes.getRecipe(item);
		
		GL11.glColor4f(1F, 1F, 1F, 1F);
		
		Minecraft.getMinecraft().renderEngine.bindTexture(texture);
		
		g.drawModalRectWithCustomSizedTexture(s, t, 0, 0, 54, 54, 54, 54);

		
		if(l != null){
			
			int index = 0;
			
			int x = 1;
			
			int y = 1;
			
			int d =3;
			
			RenderHelper.enableGUIStandardItemLighting();
			
			for(ItemStack[] isa : l){
				
				boolean isString = false;
				
				ItemStack is2;
				if(isa != null){
					
				if(isa.length == 1)
					is2 = isa[0];
				else{
					
					isString = true;
					
					is2 = isa[ore[index]];
				}
				
				}else
					is2 = null;
			
				if(is2 != null){
				ItemStack is = new ItemStack(is2.getItem(),1,is2.getItemDamage() != OreDictionary.WILDCARD_VALUE ? is2.getItemDamage() : m[index]);
				
				
			
			Minecraft.getMinecraft().getRenderItem().renderItemAndEffectIntoGUI(is, s+x, t+y);
			
			Minecraft.getMinecraft().getRenderItem().renderItemOverlays(Minecraft.getMinecraft().fontRendererObj, is, s+x, t+y);
			
			if(is2.getItemDamage() == OreDictionary.WILDCARD_VALUE){
				if(m[index] == is2.getMaxDamage())
					m[index] = 0;
				else	
					m[index]++;
				
			}
			
			if(isString && ticks %40 == 0){
				
				if(isa.length > 1){
				
					
					ore[index] = Runomancy.randInt(isa.length, ore[index]);
		
			
			}
				
		}
			
		}
			
			index ++;
			x += 18;
				
			if(index % d == 0){
			
			x = 1;
			y += 18;
			
			}
			
			
				
				
			}
			RenderHelper.disableStandardItemLighting();;
			
		}else
			System.out.println("FML");
		
		
	}
	
	


	

	@Override
	public void mouseClicked(int mx, int my, int mouseButton) {
		
		
	
		int index = 0;
		int wx = 0;
		int wy = 1;
		
		int wd =3;
		
		for(ItemStack[] wis : ModRecipes.getRecipe(item)){
			
			ItemStack ac;
			
			if(wis != null){
			if(wis.length == 1)
				
				ac = wis[0];
			else{
				
				ac = wis[ore[index]];
			}
			}else
				ac = null;
			
			index ++;
			wx+=19;
			int ssx = index == 1 || index == 4 || index == 7 ? sx : sx+wx-19;
			int ex =index == 1 || index == 4 || index == 7 ? sx+wx : sx+ wx;
			
			//int ssx = sx;
			//int ex = sx+wx;
			if((ssx < mx && mx < ex) && (my > wy+sy && my <wy+19+sy)){
			
				if(GuiModBook.hasEntry(ac)){
					g.changePage(ac.getUnlocalizedName());
				}
				//	System.out.println(wis.getUnlocalizedName());
			}
			if(index % wd == 0){
			
			wx = 0;
			wy+=16;
			
			}
				
		}
		
	}

	@Override
	public String returnPage() {
		
		return item.getUnlocalizedName();
	}

	@Override
	public Categories getCategory() {
		
		return Categories.Recipe;
	}


	@Override
	public void init() {
		
		buttons.add(new TextButton(LibMisc.GuiIDs.Buttons.BACK, x+w/2 - Minecraft.getMinecraft().fontRendererObj.getStringWidth(I18n.translateToLocal("runomancy.back"))/2, y+(h-24), I18n.translateToLocal("runomancy.back")));
		
		
	}
	
	 

}
