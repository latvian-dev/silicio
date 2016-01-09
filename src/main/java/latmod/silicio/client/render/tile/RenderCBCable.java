package latmod.silicio.client.render.tile;

import cpw.mods.fml.relauncher.*;
import ftb.lib.client.*;
import latmod.ftbu.util.client.*;
import latmod.silicio.*;
import latmod.silicio.tile.cb.TileCBCable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.*;
import org.lwjgl.opengl.GL11;

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
		//if(true) return;
		
		GlStateManager.pushMatrix();
		GlStateManager.enableRescaleNormal();
		GlStateManager.color(1F, 1F, 1F, 1F);
		
		GlStateManager.translate(rx, ry + 1F, rz + 1F);
		GlStateManager.scale(1F, -1F, -1F);
		GlStateManager.translate(0.5F, 0.5F, 0.5F);
		
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture_off);
		
		float s = 0.0625F;
		
		model.center.render(s);
		
		for(int i = 0; i < 6; i++)
		{
			if(t.renderCableSide[i])
			{
				model.cable[i].render(s);
				if(t.boards[i] != null) model.board[i].render(s);
			}
		}
		
		if(t.getCBNetwork().hasController())
		{
			int c = 0;
			
			for(int i = 0; i < 6; i++)
				if(t.renderCableSide[i] || t.boards[i] != null) c++;
			
			if(c == 2)
			{
				if(!((t.renderCableSide[0] && t.renderCableSide[1] && !t.renderCableSide[2] && !t.renderCableSide[3] && !t.renderCableSide[4] && !t.renderCableSide[5]) || (!t.renderCableSide[0] && !t.renderCableSide[1] && t.renderCableSide[2] && t.renderCableSide[3] && !t.renderCableSide[4] && !t.renderCableSide[5]) || (!t.renderCableSide[0] && !t.renderCableSide[1] && !t.renderCableSide[2] && !t.renderCableSide[3] && t.renderCableSide[4] && t.renderCableSide[5])))
					c = 1;
			}
			
			if(c > 0)
			{
				float sc = 1.01F;
				GlStateManager.scale(sc, sc, sc);
				FTBLibClient.pushMaxBrightness();
				GlStateManager.disableLighting();
				
				Minecraft.getMinecraft().getTextureManager().bindTexture(texture_on);
				
				if(t.getCBNetwork().hasConflict()) GlStateManager.color(1F, 0F, 0F, 0.5F);
				else GlStateManager.color(0F, 1F, 1F, 0.5F);
				
				GlStateManager.enableBlend();
				GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
				
				if(c != 2) model.center.render(s);
				
				for(int i = 0; i < 6; i++)
				{
					if(t.boards[i] != null || t.getBlock(i) == SilItems.b_cbcontroller) model.cable[i].render(s);
				}
				
				GlStateManager.disableBlend();
				
				for(int i = 0; i < 6; i++)
				{
					if(t.boards[i] != null) model.board[i].render(s);
				}
				
				GlStateManager.color(1F, 1F, 1F, 1F);
				FTBLibClient.popMaxBrightness();
				GlStateManager.enableLighting();
			}
		}
		
		GlStateManager.popMatrix();
		GlStateManager.disableRescaleNormal();
		
		if(t.hasCover)
		{
			GlStateManager.pushMatrix();
			GlStateManager.translate(rx + 0.5D, ry + 0.5D, rz + 0.5D);
			GlStateManager.disableLighting();
			GlStateManager.disableCull();
			
			FTBLibClient.setTexture(TextureMap.locationBlocksTexture);
			
			IIcon[] icons = new IIcon[6];
			
			for(int i = 0; i < 6; i++)
			{
				if(t.renderCover[i])
				{
					icons[i] = SilItems.b_cbcable.icon_cover;
					
					if(t.paint[i] != null && t.paint[i].block != null)
						icons[i] = t.paint[i].block.getBlockTextureFromSide(i);
				}
				else
				{
					icons[i] = FTBLibClient.blockNullIcon;
				}
			}
			
			double d = 0D;//0.001D;
			renderBlocks.fullBlock.setBounds(d, d, d, 1D - d, 1D - d, 1D - d);
			renderBlocks.setInst(t.getWorldObj());
			renderBlocks.renderAllFaces = true;
			renderBlocks.setRenderBounds(renderBlocks.fullBlock);
			renderBlocks.renderStandardBlockIcons(t.getBlockType(), t.xCoord, t.yCoord, t.zCoord, icons, true);
			
			GlStateManager.enableCull();
			GlStateManager.enableLighting();
			GlStateManager.popMatrix();
		}
	}
}