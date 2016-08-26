package com.latmod.silicio.api;

import com.latmod.lib.math.BlockDimPos;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

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

    @CapabilityInject(IModuleProvider.class)
    public static Capability<IModuleProvider> MODULE_PROVIDER = null;

    @CapabilityInject(ISilNetTile.class)
    public static Capability<ISilNetTile> SILNET_TILE = null;

    public abstract TileEntity getSilNetTile(BlockDimPos pos);

    public abstract void addSilNetTile(TileEntity tile);

    public abstract void removeSilNetTile(TileEntity tile);

    public abstract void findSilNetTiles(Collection<TileEntity> tiles, UUID uuid);

    public abstract ISilNetController findSilNetController(UUID controllerID);
}