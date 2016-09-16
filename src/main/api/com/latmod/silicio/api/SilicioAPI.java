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
public abstract class SilicioAPI
{
    private static SilicioAPI API;

    public static void setAPI(SilicioAPI api)
    {
        API = api;
    }

    public static SilicioAPI get()
    {
        return API;
    }

    public abstract TileEntity getSilNetTile(BlockDimPos pos);

    public abstract void addSilNetTile(TileEntity tile);

    public abstract void removeSilNetTile(TileEntity tile);

    public abstract void findSilNetTiles(Collection<TileEntity> tiles, UUID uuid);

    @Nullable
    public abstract ISilNetController findSilNetController(@Nullable UUID controllerID);
}