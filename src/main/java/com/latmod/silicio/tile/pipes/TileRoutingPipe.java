package com.latmod.silicio.tile.pipes;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;

import javax.annotation.Nullable;

/**
 * Created by LatvianModder on 04.01.2017.
 */
public class TileRoutingPipe extends TilePipeBase
{
    @Override
    public boolean onRightClick(EntityPlayer player, @Nullable EnumFacing part, EnumFacing facing)
    {
        return true;
    }
}