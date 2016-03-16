package latmod.silicio.client;

import ftb.lib.api.client.FTBLibClient;
import latmod.lib.MathHelperLM;
import latmod.silicio.tile.TileTurret;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.*;

/**
 * Created by LatvianModder on 08.03.2016.
 */
@SideOnly(Side.CLIENT)
public class RenderTurret extends TileEntitySpecialRenderer<TileTurret>
{
	public static final ResourceLocation TEXTURE = new ResourceLocation("silicio", "textures/tile/turret_beam.png");
	
	public void renderTileEntityAt(TileTurret te, double x, double y, double z, float partialTicks, int destroyStage)
	{
		if(te.target == null || te.target.isDead/* || te.getWorld().getTotalWorldTime() % 3 != 0*/) return;
		
		double s = 0.08D;
		double startX = x + 0.5D;
		double startY = y + 0.5D;
		double startZ = z + 0.5D;
		double endX = x + (te.target.posX - te.getPos().getX() + 0.5D);// + MathHelperLM.randomDouble(MathHelperLM.rand, -s, s);
		double endY = y + (te.target.posY - te.getPos().getY() + 0.5D) + te.target.getEyeHeight();// + MathHelperLM.randomDouble(MathHelperLM.rand, -s, s);
		double endZ = z + (te.target.posZ - te.getPos().getZ() + 0.5D);// + MathHelperLM.randomDouble(MathHelperLM.rand, -s, s);
		
		GlStateManager.enableTexture2D();
		GlStateManager.disableLighting();
		GlStateManager.disableCull();
		FTBLibClient.pushMaxBrightness();
		GlStateManager.enableBlend();
		GlStateManager.blendFunc(770, 771);
		GlStateManager.color(0F, 1F, 1F, 1F);//MathHelperLM.rand.nextFloat() * 0.5F + 0.5F);
		
		bindTexture(TEXTURE);
		s = 1D / 8D;
		double textureX0 = 0D;
		double textureX1 = textureX0 + MathHelperLM.dist(startX, startY, startZ, endX, endY, endZ);
		
		Tessellator tessellator = Tessellator.getInstance();
		WorldRenderer worldRenderer = tessellator.getWorldRenderer();
		worldRenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
		worldRenderer.pos(startX, startY - s, startZ).tex(textureX0, 0D).endVertex();
		worldRenderer.pos(endX, endY - s, endZ).tex(textureX1, 0D).endVertex();
		worldRenderer.pos(endX, endY + s, endZ).tex(textureX1, 1D).endVertex();
		worldRenderer.pos(startX, startY + s, startZ).tex(textureX0, 1D).endVertex();
		tessellator.draw();
		
		GlStateManager.enableTexture2D();
		GlStateManager.enableLighting();
		GlStateManager.enableCull();
		GlStateManager.color(1F, 1F, 1F, 1F);
		FTBLibClient.popMaxBrightness();
	}
}
