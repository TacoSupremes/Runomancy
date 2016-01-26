package com.tacosupremes.runomancy.client.render;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class RenderPowerStorage extends TileEntitySpecialRenderer {

	
	private ModelCube model = new ModelCube();
	private static final ResourceLocation texture = TextureMap.LOCATION_MISSING_TEXTURE;
	
	
	@Override
	public void renderTileEntityAt(TileEntity te, double d0, double d1, double d2, float partialTicks, int destroyStage) {
	
		GL11.glPushMatrix();
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		GL11.glColor4f(1F, 1F, 1F, 1F);
		GL11.glTranslated(d0, d1, d2);

		Minecraft.getMinecraft().renderEngine.bindTexture(texture);
		
		GL11.glTranslatef(0.5F, 1.4F, 0.5F);
		GL11.glScalef(1F, -1F, -1F);
		float i = 0.1f;
		
		GL11.glScalef(0.99F, 0.9F * i, 0.99F);
		model.render();
		GL11.glColor3f(1F, 1F, 1F);
		
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		GL11.glPopMatrix();
	
	}

}
