package com.latmod.silicio.api;

import com.latmod.lib.math.BlockDimPos;
import net.minecraft.tileentity.TileEntity;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by LatvianModder on 06.08.2016.
 */
public class SilNet
{
    private static final Map<BlockDimPos, TileEntity> NET = new HashMap<>();

    public static void clear()
    {
        NET.clear();
    }

    public static TileEntity get(BlockDimPos pos)
    {
        return NET.get(pos);
    }

    public static void add(TileEntity tile)
    {
        if(tile.hasWorldObj() && !tile.getWorld().isRemote && tile.hasCapability(SilCapabilities.SILNET_TILE, null))
        {
            NET.put(new BlockDimPos(tile.getPos(), tile.getWorld().provider.getDimension()), tile);
            //FTBLib.DEV_LOGGER.info("Added " + tile.getPos());
        }
    }

    public static void remove(TileEntity tile)
    {
        if(tile.hasWorldObj() && !tile.getWorld().isRemote && tile.hasCapability(SilCapabilities.SILNET_TILE, null))
        {
            NET.remove(new BlockDimPos(tile.getPos(), tile.getWorld().provider.getDimension()));
            //FTBLib.DEV_LOGGER.info("Removed " + tile.getPos());
        }
    }

    public static void findNetwork(Collection<TileEntity> tiles, UUID uuid)
    {
        for(TileEntity tile : NET.values())
        {
            ISilNetTile tile1 = tile.getCapability(SilCapabilities.SILNET_TILE, null);

            if(tile1.getControllerID() != null && tile1.getControllerID().equals(uuid))
            {
                tiles.add(tile);
            }
        }
    }

    public static ISilNetController findController(UUID controllerID)
    {
        for(TileEntity tile : NET.values())
        {
            ISilNetTile tile1 = tile.getCapability(SilCapabilities.SILNET_TILE, null);

            if(tile1 instanceof ISilNetController && tile1.getControllerID().equals(controllerID))
            {
                return (ISilNetController) tile1;
            }
        }

        return null;
    }
}