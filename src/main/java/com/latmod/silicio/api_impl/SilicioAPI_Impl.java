package com.latmod.silicio.api_impl;

import com.latmod.lib.math.BlockDimPos;
import com.latmod.lib.util.LMUtils;
import com.latmod.silicio.api.SilicioAPI;
import com.latmod.silicio.api.SilicioAddon;
import com.latmod.silicio.api.tile.ISilNetController;
import com.latmod.silicio.api.tile.ISilNetTile;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.common.discovery.ASMDataTable;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by LatvianModder on 26.08.2016.
 */
public enum SilicioAPI_Impl implements SilicioAPI
{
    INSTANCE;

    public void init(ASMDataTable table)
    {
        LMUtils.findAnnotatedObjects(table, SilicioAPI.class, SilicioAddon.class, (obj, data) -> this);
    }

    private static final Map<BlockDimPos, TileEntity> NET = new HashMap<>();

    public static void clear()
    {
        NET.clear();
    }

    @Override
    public TileEntity getSilNetTile(BlockDimPos pos)
    {
        return NET.get(pos);
    }

    @Override
    public void addSilNetTile(TileEntity tile)
    {
        if(tile.hasWorldObj() && !tile.getWorld().isRemote && tile.hasCapability(SilCaps.SILNET_TILE, null))
        {
            NET.put(new BlockDimPos(tile.getPos(), tile.getWorld().provider.getDimension()), tile);
            //FTBLib.DEV_LOGGER.info("Added " + tile.getPos());
        }
    }

    @Override
    public void removeSilNetTile(TileEntity tile)
    {
        if(tile.hasWorldObj() && !tile.getWorld().isRemote && tile.hasCapability(SilCaps.SILNET_TILE, null))
        {
            NET.remove(new BlockDimPos(tile.getPos(), tile.getWorld().provider.getDimension()));
            //FTBLib.DEV_LOGGER.info("Removed " + tile.getPos());
        }
    }

    @Override
    public void findSilNetTiles(Collection<TileEntity> tiles, UUID uuid)
    {
        for(TileEntity tile : NET.values())
        {
            if(!tile.isInvalid() && tile.hasCapability(SilCaps.SILNET_TILE, null))
            {
                ISilNetTile tile1 = tile.getCapability(SilCaps.SILNET_TILE, null);

                if(tile1.getControllerID() != null && tile1.getControllerID().equals(uuid))
                {
                    tiles.add(tile);
                }
            }
        }
    }

    @Override
    @Nullable
    public ISilNetController findSilNetController(@Nullable UUID controllerID)
    {
        if(controllerID == null)
        {
            return null;
        }

        for(TileEntity tile : NET.values())
        {
            if(!tile.isInvalid() && tile.hasCapability(SilCaps.SILNET_TILE, null))
            {
                ISilNetTile tile1 = tile.getCapability(SilCaps.SILNET_TILE, null);

                if(tile1 instanceof ISilNetController && tile1.getControllerID().equals(controllerID))
                {
                    return (ISilNetController) tile1;
                }
            }
        }

        return null;
    }
}