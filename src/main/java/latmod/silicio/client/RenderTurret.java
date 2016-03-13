package latmod.silicio.client;

import ftb.lib.api.client.FTBLibClient;
import latmod.lib.MathHelperLM;
import latmod.silicio.tile.TileTurret;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.*;
import org.lwjgl.opengl.GL11;

/**
 * Created by LatvianModder on 08.03.2016.
 */
@SideOnly(Side.CLIENT)
public class RenderTurret extends TileEntitySpecialRenderer<TileTurret>
{
	public static final ResourceLocation TEXTURE = new ResourceLocation("silicio", "textures/tile/turret_beam.png");
	
	public void renderTileEntityAt(TileTurret te, double x, double y, double z, float partialTicks, int destroyStage)
	{
		if(te.target == null || te.target.isDead || te.getWorld().getTotalWorldTime() % 3 != 0) return;
		
		double s = 0.08D;
		double endX = x + te.target.posX - (te.getPos().getX() + 0.5D) + MathHelperLM.randomDouble(MathHelperLM.rand, -s, s);
		double endY = y + te.target.posY - (te.getPos().getY() + 0.5D) + te.target.getEyeHeight() + MathHelperLM.randomDouble(MathHelperLM.rand, -s, s);
		double endZ = z + te.target.posZ - (te.getPos().getZ() + 0.5D) + MathHelperLM.randomDouble(MathHelperLM.rand, -s, s);
		
		GlStateManager.enableTexture2D();
		GlStateManager.disableTexture2D();
		GlStateManager.disableLighting();
		GlStateManager.disableCull();
		FTBLibClient.pushMaxBrightness();
		GlStateManager.color(0F, 1F, 1F, MathHelperLM.rand.nextFloat() * 0.5F + 0.5F);
		
		bindTexture(TEXTURE);
		s = 1D / 8D;
		
		Tessellator tessellator = Tessellator.getInstance();
		WorldRenderer worldRenderer = tessellator.getWorldRenderer();
		worldRenderer.begin(GL11.GL_LINES, DefaultVertexFormats.POSITION);
		worldRenderer.pos(x + 0.5D, y + 0.5D, z + 0.5D).endVertex();
		worldRenderer.pos(endX, endY, endZ).endVertex();
		//worldRenderer.pos(0.5D, 0.5D, 0.5D).tex(1D, 1D).endVertex();
		//worldRenderer.pos(0.5D, 0.5D, 0.5D).tex(0D, 1D).endVertex();
		tessellator.draw();
		
		GlStateManager.enableTexture2D();
		GlStateManager.enableLighting();
		GlStateManager.enableCull();
		GlStateManager.color(1F, 1F, 1F, 1F);
		FTBLibClient.popMaxBrightness();
	}
}
