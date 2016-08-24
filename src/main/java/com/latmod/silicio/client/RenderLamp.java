package com.latmod.silicio.client;

import com.feed_the_beast.ftbl.api.client.CubeRenderer;
import com.feed_the_beast.ftbl.api.client.FTBLibClient;
import com.latmod.silicio.tile.TileLamp;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

/**
 * Created by LatvianModder on 23.08.2016.
 */
@SideOnly(Side.CLIENT)
public class RenderLamp extends TileEntitySpecialRenderer<TileLamp>
{
    private static final CubeRenderer LAMP_MASK = new CubeRenderer(false, true);

    @Override
    public void renderTileEntityAt(@Nonnull TileLamp te, double x, double y, double z, float partialTicks, int destroyStage)
    {
        GlStateManager.disableTexture2D();
        FTBLibClient.pushMaxBrightness();
        LAMP_MASK.setTessellator(Tessellator.getInstance());
        LAMP_MASK.color.set(te.getCurrentColor(), 1F);
        LAMP_MASK.setSize(TileLamp.LAMP_BOX.offset(x, y, z));
        LAMP_MASK.renderAll();
        FTBLibClient.popMaxBrightness();
        GlStateManager.enableTexture2D();
    }
}