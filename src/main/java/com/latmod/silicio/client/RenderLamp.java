package com.latmod.silicio.client;

import com.feed_the_beast.ftbl.lib.client.CubeRenderer;
import com.feed_the_beast.ftbl.lib.client.FTBLibClient;
import com.feed_the_beast.ftbl.lib.util.LMColorUtils;
import com.latmod.silicio.tile.TileLamp;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;

/**
 * Created by LatvianModder on 23.08.2016.
 */
public class RenderLamp extends TileEntitySpecialRenderer<TileLamp>
{
    private static final CubeRenderer LAMP_MASK = new CubeRenderer(false, true);

    @Override
    public void renderTileEntityAt(TileLamp te, double x, double y, double z, float partialTicks, int destroyStage)
    {
        byte colID = te.getCurrentColor();

        GlStateManager.disableTexture2D();
        GlStateManager.color(1F, 1F, 1F, 1F);

        if(colID != 0)
        {
            GlStateManager.disableLighting();
            FTBLibClient.pushMaxBrightness();
            LAMP_MASK.color.set(LMColorUtils.getColorFromID(colID), 255);
        }
        else
        {
            LAMP_MASK.color.set(0, 0, 0, 255);
        }

        LAMP_MASK.setTessellator(Tessellator.getInstance());
        LAMP_MASK.setSize(TileLamp.LAMP_BOX);
        LAMP_MASK.offset(x, y, z);
        LAMP_MASK.renderAll();

        if(colID != 0)
        {
            FTBLibClient.popMaxBrightness();
            GlStateManager.enableLighting();
        }

        GlStateManager.enableTexture2D();
    }
}