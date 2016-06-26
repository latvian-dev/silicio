package com.latmod.silicio.client;

import com.latmod.silicio.tile.TileAntimatter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.nio.FloatBuffer;
import java.util.Random;

/**
 * Created by LatvianModder on 11.03.2016.
 */
@SideOnly(Side.CLIENT)
public class RenderAntimatter extends TileEntitySpecialRenderer<TileAntimatter>
{
    private static final ResourceLocation END_SKY_TEXTURE = new ResourceLocation("textures/environment/end_sky.png");
    private static final ResourceLocation END_PORTAL_TEXTURE = new ResourceLocation("textures/entity/end_portal.png");
    private static final Random random = new Random(31100L);
    private static final FloatBuffer floatBuffer = GLAllocation.createDirectFloatBuffer(16);
    private static boolean[] renderSide = new boolean[6];

    private static FloatBuffer func_147525_a(float a, float b, float c, float d)
    {
        floatBuffer.clear();
        floatBuffer.put(a).put(b).put(c).put(d);
        floatBuffer.flip();
        return floatBuffer;
    }

    @Override
    public void renderTileEntityAt(@Nonnull TileAntimatter te, double x, double y, double z, float partialTicks, int destroyStage)
    {
        //TODO: Check config

        Tessellator tessellator = Tessellator.getInstance();
        VertexBuffer worldrenderer = tessellator.getBuffer();
        double f3 = 1D / 256D;

        double entityX = rendererDispatcher.entityX;
        double entityY = rendererDispatcher.entityY;
        double entityZ = rendererDispatcher.entityZ;
        GlStateManager.disableLighting();

        double posX = ActiveRenderInfo.getPosition().xCoord;
        double posY = ActiveRenderInfo.getPosition().yCoord;
        float time = (float) (Minecraft.getSystemTime() % 200000L) / 200000F;

        random.setSeed(31100L);

        for(int i = 0; i < 16; ++i)
        {
            GlStateManager.pushMatrix();
            float f4 = 16F - i;
            float f5 = 0.0625F;
            float f6 = 1F / (f4 + 1F);

            if(i == 0)
            {
                bindTexture(END_SKY_TEXTURE);
                f6 = 0.1F;
                f4 = 65F;
                f5 = 0.125F;
                GlStateManager.enableBlend();
                GlStateManager.blendFunc(770, 771);
            }
            else
            {
                bindTexture(END_PORTAL_TEXTURE);

                if(i == 1)
                {
                    GlStateManager.enableBlend();
                    GlStateManager.blendFunc(1, 1);
                    f5 = 0.5F;
                }
            }

            double f7 = -(y + f3);
            double f8 = f7 + posY;
            double f9 = f7 + f4 + posY;
            double f10 = f8 / f9;
            f10 = y + f3 + f10;

            GlStateManager.translate(entityX, f10, entityZ);
            GlStateManager.texGen(GlStateManager.TexGen.S, 9217);
            GlStateManager.texGen(GlStateManager.TexGen.T, 9217);
            GlStateManager.texGen(GlStateManager.TexGen.R, 9217);
            GlStateManager.texGen(GlStateManager.TexGen.Q, 9216);
            GlStateManager.texGen(GlStateManager.TexGen.S, 9473, func_147525_a(1F, 0F, 0F, 0F));
            GlStateManager.texGen(GlStateManager.TexGen.T, 9473, func_147525_a(0F, 0F, 1F, 0F));
            GlStateManager.texGen(GlStateManager.TexGen.R, 9473, func_147525_a(0F, 0F, 0F, 1F));
            GlStateManager.texGen(GlStateManager.TexGen.Q, 9474, func_147525_a(0F, 1F, 0F, 0F));
            GlStateManager.enableTexGenCoord(GlStateManager.TexGen.S);
            GlStateManager.enableTexGenCoord(GlStateManager.TexGen.T);
            GlStateManager.enableTexGenCoord(GlStateManager.TexGen.R);
            GlStateManager.enableTexGenCoord(GlStateManager.TexGen.Q);
            GlStateManager.popMatrix();
            GlStateManager.matrixMode(5890);
            GlStateManager.pushMatrix();
            GlStateManager.loadIdentity();
            GlStateManager.translate(0F, time, 0F);
            GlStateManager.scale(f5, f5, f5);
            GlStateManager.translate(0.5F, 0.5F, 0F);
            GlStateManager.rotate((i * i * 4321F + i * 9F) * 2F, 0F, 0F, 1F);
            GlStateManager.translate(-0.5F, -0.5F, 0F);
            GlStateManager.translate(-entityX, -entityZ, -entityY);

            f8 = f7 + posY;
            GlStateManager.translate(posX * f4 / f8, posY * f4 / f8, -entityY);

            float redCol = (random.nextFloat() * 0.5F + 0.1F) * f6;
            float greenCol = (random.nextFloat() * 0.5F + 0.4F) * f6;
            float blueCol = (random.nextFloat() * 0.5F + 0.5F) * f6;

            if(i == 0)
            {
                redCol = greenCol = blueCol = 1F * f6;
            }

            worldrenderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
            worldrenderer.pos(x, y + f3, z).color(redCol, greenCol, blueCol, 1F).endVertex();
            worldrenderer.pos(x, y + f3, z + 1D).color(redCol, greenCol, blueCol, 1F).endVertex();
            worldrenderer.pos(x + 1D, y + f3, z + 1D).color(redCol, greenCol, blueCol, 1F).endVertex();
            worldrenderer.pos(x + 1D, y + f3, z).color(redCol, greenCol, blueCol, 1F).endVertex();
            tessellator.draw();

            GlStateManager.popMatrix();
            GlStateManager.matrixMode(5888);
            bindTexture(END_SKY_TEXTURE);
        }

        GlStateManager.disableBlend();
        GlStateManager.disableTexGenCoord(GlStateManager.TexGen.S);
        GlStateManager.disableTexGenCoord(GlStateManager.TexGen.T);
        GlStateManager.disableTexGenCoord(GlStateManager.TexGen.R);
        GlStateManager.disableTexGenCoord(GlStateManager.TexGen.Q);
        GlStateManager.enableLighting();
        GlStateManager.color(1F, 1F, 1F, 1F);
    }
}
