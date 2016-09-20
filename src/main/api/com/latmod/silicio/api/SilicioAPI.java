package com.latmod.silicio.api;

import com.latmod.lib.math.BlockDimPos;
import com.latmod.silicio.api.tile.ISilNetController;
import net.minecraft.tileentity.TileEntity;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.UUID;

/**
 * Created by LatvianModder on 26.08.2016.
 */
public interface SilicioAPI
{
    TileEntity getSilNetTile(BlockDimPos pos);

    void addSilNetTile(TileEntity tile);

    void removeSilNetTile(TileEntity tile);

    void findSilNetTiles(Collection<TileEntity> tiles, UUID uuid);

    @Nullable
    ISilNetController findSilNetController(@Nullable UUID controllerID);
}