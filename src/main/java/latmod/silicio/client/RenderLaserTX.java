package latmod.silicio.client;

import ftb.lib.MathHelperMC;
import ftb.lib.api.client.*;
import latmod.silicio.tile.TileLaserTX;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.*;
import net.minecraftforge.fml.relauncher.*;

/**
 * Created by LatvianModder on 08.03.2016.
 */
@SideOnly(Side.CLIENT)
public class RenderLaserTX extends TileEntitySpecialRenderer<TileLaserTX>
{
	public static final CubeRenderer laserBeam = new CubeRenderer();
	
	public void renderTileEntityAt(TileLaserTX te, double x, double y, double z, float partialTicks, int destroyStage)
	{
		if(te.laserPath.isEmpty()) return;
		
		GlStateManager.disableTexture2D();
		GlStateManager.disableLighting();
		GlStateManager.disableCull();
		FTBLibClient.pushMaxBrightness();
		
		GlStateManager.pushMatrix();
		GlStateManager.translate(x, y, z);
		BlockPos p0 = te.getPos();
		GlStateManager.translate(-p0.getX(), -p0.getY(), -p0.getZ());
		
		GlStateManager.color(1F, 0F, 0F, 1F);
		
		laserBeam.setTessellator(Tessellator.getInstance());
		double ls = 1D / 16D * 7D;
		
		for(BlockPos p : te.laserPath)
		{
			EnumFacing dir = MathHelperMC.getDirection(p0, p);
			
			if(dir != null)
			{
				Vec3i dirVec = dir.getDirectionVec();
				
				laserBeam.setSize(p0.getX() + ls, p0.getY() + ls, p0.getZ() + ls, p.getX() + 1D - ls, p.getY() + 1D - ls, p.getZ() + 1D - ls);
				//laserBeam.setSize(0D, 0D, 0D, 1D, 1D, 1D);
				laserBeam.renderAll();
				
				laserBeam.setSize(p0.getX() + ls, p0.getY() + ls, p0.getZ() + ls, p.getX() + 1D - ls, p.getY() + 1D - ls, p.getZ() + 1D - ls);
			}
			
			p0 = p;
		}
		
		GlStateManager.enableTexture2D();
		GlStateManager.enableLighting();
		GlStateManager.enableCull();
		GlStateManager.color(1F, 1F, 1F, 1F);
		GlStateManager.popMatrix();
		FTBLibClient.popMaxBrightness();
	}
}
