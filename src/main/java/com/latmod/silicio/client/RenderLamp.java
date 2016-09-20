package com.latmod.silicio.client;

import com.feed_the_beast.ftbl.api.client.CubeRenderer;
import com.feed_the_beast.ftbl.api.client.FTBLibClient;
import com.latmod.lib.util.LMColorUtils;
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
        GlStateManager.disableTexture2D();
        GlStateManager.color(1F, 1F, 1F, 1F);
        GlStateManager.disableLighting();
        FTBLibClient.pushMaxBrightness();
        LAMP_MASK.setTessellator(Tessellator.getInstance());
        LAMP_MASK.color.set(LMColorUtils.getColorFromID(te.getCurrentColor()), 1F);
        LAMP_MASK.setSize(TileLamp.LAMP_BOX);
        LAMP_MASK.offset(x, y, z);
        LAMP_MASK.renderAll();
        FTBLibClient.popMaxBrightness();
        GlStateManager.enableLighting();
        GlStateManager.enableTexture2D();
    }
}