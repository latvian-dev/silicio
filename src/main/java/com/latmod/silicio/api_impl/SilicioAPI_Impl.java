package com.latmod.silicio.api_impl;

import com.feed_the_beast.ftbl.util.EmptyCapStorage;
import com.latmod.lib.math.BlockDimPos;
import com.latmod.silicio.api.SilicioAPI;
import com.latmod.silicio.api.module.IModuleContainer;
import com.latmod.silicio.api.tile.ISilNetController;
import com.latmod.silicio.api.tile.ISilNetTile;
import com.latmod.silicio.api.tile.ISocketBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.capabilities.CapabilityManager;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by LatvianModder on 26.08.2016.
 */
public class SilicioAPI_Impl extends SilicioAPI
{
    private static SilicioAPI_Impl INST;

    public static SilicioAPI_Impl get()
    {
        if(INST == null)
        {
            INST = new SilicioAPI_Impl();
        }

        return INST;
    }

    private final Map<BlockDimPos, TileEntity> NET = new HashMap<>();

    public void init()
    {
        CapabilityManager.INSTANCE.register(IModuleContainer.class, new EmptyCapStorage<>(), () -> null);
        CapabilityManager.INSTANCE.register(ISilNetTile.class, new EmptyCapStorage<>(), () -> null);
        CapabilityManager.INSTANCE.register(ISocketBlock.class, new EmptyCapStorage<>(), () -> null);
    }

    public void clear()
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
        if(tile.hasWorldObj() && !tile.getWorld().isRemote && tile.hasCapability(SilicioAPI.SILNET_TILE, null))
        {
            NET.put(new BlockDimPos(tile.getPos(), tile.getWorld().provider.getDimension()), tile);
            //FTBLib.DEV_LOGGER.info("Added " + tile.getPos());
        }
    }

    @Override
    public void removeSilNetTile(TileEntity tile)
    {
        if(tile.hasWorldObj() && !tile.getWorld().isRemote && tile.hasCapability(SilicioAPI.SILNET_TILE, null))
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
            if(!tile.isInvalid() && tile.hasCapability(SilicioAPI.SILNET_TILE, null))
            {
                ISilNetTile tile1 = tile.getCapability(SilicioAPI.SILNET_TILE, null);

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
            if(!tile.isInvalid() && tile.hasCapability(SilicioAPI.SILNET_TILE, null))
            {
                ISilNetTile tile1 = tile.getCapability(SilicioAPI.SILNET_TILE, null);

                if(tile1 instanceof ISilNetController && tile1.getControllerID().equals(controllerID))
                {
                    return (ISilNetController) tile1;
                }
            }
        }

        return null;
    }
}