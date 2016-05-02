package latmod.silicio.client;

import ftb.lib.MathHelperMC;
import ftb.lib.api.client.CubeRenderer;
import ftb.lib.api.client.FTBLibClient;
import latmod.silicio.tile.TileLaserTX;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Created by LatvianModder on 08.03.2016.
 */
@SideOnly(Side.CLIENT)
public class RenderLaserTX extends TileEntitySpecialRenderer<TileLaserTX>
{
	public static final CubeRenderer laserBeam = new CubeRenderer();
	
	@Override
	public void renderTileEntityAt(TileLaserTX te, double x, double y, double z, float partialTicks, int destroyStage)
	{
		if(te.laserPath.isEmpty()) { return; }
		
		GlStateManager.disableTexture2D();
		GlStateManager.disableLighting();
		GlStateManager.disableCull();
		FTBLibClient.pushMaxBrightness();
		GlStateManager.enableRescaleNormal();
		
		GlStateManager.pushMatrix();
		GlStateManager.translate(x, y, z);
		
		GlStateManager.color(1F, 0F, 0F, 1F);
		
		laserBeam.setTessellator(Tessellator.getInstance());
		double ls = 1D / 16D * 7D;
		
		BlockPos p0 = new BlockPos(0, 0, 0);
		
		for(BlockPos p1 : te.laserPath)
		{
			EnumFacing dir = MathHelperMC.getDirection(p0, p1);
			
			if(dir != null)
			{
				double x0 = p0.getX() + ls;
				double y0 = p0.getY() + ls;
				double z0 = p0.getZ() + ls;
				double x1 = p1.getX() + 1D - ls;
				double y1 = p1.getY() + 1D - ls;
				double z1 = p1.getZ() + 1D - ls;
				
				laserBeam.setSize(x0, y0, z0, x1, y1, z1);
				laserBeam.renderAll();
			}
			
			p0 = p1;
		}
		
		GlStateManager.disableRescaleNormal();
		GlStateManager.enableTexture2D();
		GlStateManager.enableLighting();
		GlStateManager.enableCull();
		GlStateManager.color(1F, 1F, 1F, 1F);
		GlStateManager.popMatrix();
		FTBLibClient.popMaxBrightness();
	}
}
