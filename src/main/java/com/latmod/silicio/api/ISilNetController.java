package com.latmod.silicio.api;

import net.minecraft.tileentity.TileEntity;

import java.util.Collection;

/**
 * Created by LatvianModder on 05.03.2016.
 */
public interface ISilNetController extends ISilNetTile
{
    ISignalBus getSignalBus();

    Collection<TileEntity> getNetwork();
}