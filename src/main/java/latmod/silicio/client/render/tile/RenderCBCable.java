package latmod.silicio.client.render.tile;

import latmod.core.client.*;
import latmod.silicio.*;
import latmod.silicio.tile.TileCBCable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.*;

import org.lwjgl.opengl.*;

import cpw.mods.fml.relauncher.*;

@SideOnly(Side.CLIENT)
public class RenderCBCable extends TileRenderer<TileCBCable>
{
	public static final ResourceLocation texture_off = Silicio.mod.getLocation("textures/blocks/cable_offline.png");
	public static final ResourceLocation texture_on = Silicio.mod.getLocation("textures/blocks/cable_online.png");
	public static final RenderCBCable instance = new RenderCBCable();
	
	public static RenderBlocksCustom renderBlocks;
	public static ModelCBCable model;
	
	public RenderCBCable()
	{
		renderBlocks = new RenderBlocksCustom();
		model = new ModelCBCable();
	}
	
	public void renderTile(TileCBCable t, double rx, double ry, double rz, float f)
	{
		GL11.glPushMatrix();
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		
		GL11.glTranslated(rx, ry + 1D, rz + 1D);
		GL11.glScaled(1D, -1D, -1D);
		GL11.glTranslated(0.5D, 0.5D, 0.5D);
		
		Minecraft.getMinecraft().getTextureManager().bindTexture(t.isOnline() ? texture_on : texture_off);
		
		float s = 0.0625F;
		
		model.center.render(s);
		
		for(int i = 0; i < 6; i++)
		{
			if(t.renderCableSide[i])
			{
				model.cable[i].render(s);
				if(t.boards[i] != null)
					model.board[i].render(s);
			}
		}
		
		GL11.glPopMatrix();
		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		
		if(t.hasCover)
		{
			GL11.glPushMatrix();
			GL11.glTranslated(rx + 0.5D, ry + 0.5D, rz + 0.5D);
			GL11.glDisable(GL11.GL_LIGHTING);
			
			Minecraft.getMinecraft().getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
			
			IIcon[] icons = new IIcon[6];
			
			for(int i = 0; i < 6; i++)
			{
				icons[i] = SilItems.b_cbcable.icon_cover;
				
				if(t.paint[i] != null && t.paint[i].block != null)
					icons[i] = t.paint[i].block.getBlockTextureFromSide(i);
			}
			
			renderBlocks.blockAccess = t.getWorldObj();
			renderBlocks.renderAllFaces = true;
			renderBlocks.setRenderBounds(renderBlocks.fullBlock);
			renderBlocks.renderStandardBlockIcons(t.getBlockType(), t.xCoord, t.yCoord, t.zCoord, icons, true);
			
			GL11.glEnable(GL11.GL_LIGHTING);
			GL11.glPopMatrix();
		}
	}
}