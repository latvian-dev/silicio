package com.latmod.silicio.api.tile;

import net.minecraft.tileentity.TileEntity;

import javax.annotation.Nullable;
import java.util.UUID;

/**
 * Created by LatvianModder on 28.08.2016.
 */
public interface ISilNetConnector extends ISilNetTile
{
    UUID getConnectorID();

    String getConnectorName();

    @Nullable
    TileEntity getConnectedTile();
}