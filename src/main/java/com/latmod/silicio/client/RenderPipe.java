package com.latmod.silicio.client;

import com.latmod.silicio.api.pipes.TransportedItem;
import com.latmod.silicio.tile.pipes.TilePipeBase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSkull;

/**
 * Created by LatvianModder on 04.01.2017.
 */
public class RenderPipe extends TileEntitySpecialRenderer<TilePipeBase>
{
    private static final Minecraft MC = Minecraft.getMinecraft();

    @Override
    public void renderTileEntityAt(TilePipeBase te, double x, double y, double z, float partialTicks, int destroyStage)
    {
        if(te.isInvalid() || te.transportedItems.isEmpty())
        {
            return;
        }

        GlStateManager.pushMatrix();
        GlStateManager.translate(x + 0.5F, y + 0.5F, z + 0.5F);

        for(TransportedItem transportedItem : te.transportedItems)
        {
            if(transportedItem.stack == null || transportedItem.stack.stackSize <= 0)
            {
                continue;
            }

            transportedItem.getPos(TilePipeBase.TEMP_POS, partialTicks);
            GlStateManager.pushMatrix();
            GlStateManager.translate(TilePipeBase.TEMP_POS[0], TilePipeBase.TEMP_POS[1], TilePipeBase.TEMP_POS[2]);

            Item item = transportedItem.stack.getItem();
            GlStateManager.pushMatrix();
            GlStateManager.disableLighting();

            GlStateManager.scale(0.5F, 0.5F, 0.5F);

            if(!MC.getRenderItem().shouldRenderItemIn3D(transportedItem.stack) || item instanceof ItemSkull)
            {
                GlStateManager.rotate(180F, 0F, 1F, 0F);
            }

            RenderHelper.enableStandardItemLighting();
            MC.getRenderItem().renderItem(transportedItem.stack, ItemCameraTransforms.TransformType.FIXED);
            RenderHelper.disableStandardItemLighting();

            GlStateManager.enableLighting();
            GlStateManager.popMatrix();

            GlStateManager.popMatrix();
        }

        GlStateManager.popMatrix();
    }
}